/*******************************************************************************
 * Copyright (C) 2020 Edge Hill University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package typhon.nlae.rest.api.endpoint;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import typhon.nlae.rest.api.ConfigureRabbitMq;
import typhon.nlae.rest.api.models.Delete;
import typhon.nlae.rest.api.models.Process;
import typhon.nlae.rest.api.models.ProcessedEntity;
import typhon.nlae.rest.api.utils.QueryUtils;
import io.swagger.annotations.ApiOperation;
import typhon.nlae.rest.api.models.QueryResponse;
import typhon.nlae.rest.api.models.Query;

@RestController
public class Endpoint {

	protected Logger logger = LogManager.getLogger(Endpoint.class);
	
	private final RabbitTemplate rabbitTemplate;
	
	public Endpoint(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
	
	@Autowired
	RestHighLevelClient RESTclient;

	static class Config {

		protected Logger logger = LogManager.getLogger(Config.class);

		@Value("${elasticsearch.host}")
		private String elasticsearchHost;

		@Value("${elasticsearch.port}")
		private int elasticsearchPort;

		@Bean(destroyMethod = "close")
		RestHighLevelClient client() {
			
			int count = 0;
			
			logger.info("Attempting to create a High Level Rest Client");

			while (count < 20) {
				
				try {

					boolean flag = pingHost(elasticsearchHost, elasticsearchPort, 5000);

					if (flag == true) {
						
						ClientConfiguration clientConfiguration = ClientConfiguration.builder()
								.connectedTo(elasticsearchHost + ":" + elasticsearchPort).build();

						logger.info("Elasticsearch Client has been successfully created");

						return RestClients.create(clientConfiguration).rest();

					} else {

						count = count + 1;

					}

					Thread.sleep(5000);

				} catch (InterruptedException e) {
				
					e.printStackTrace();
				
				}
			}

			logger.error("Exiting....After several attempts I'm unable to establish connection to Elasticsearch");
			
			System.exit(0);
		
			return null;
		}

		public static boolean pingHost(String host, int port, int timeout) {
			try (Socket socket = new Socket()) {
				socket.connect(new InetSocketAddress(host, port), timeout);
				return true;
			} catch (IOException e) {
				return false;
			}
		}

	}

	@PostMapping("/processText")
	@Async
	@ApiOperation("Prepares entity for Natural Language Processing")
	public void process(@RequestBody Process process) {

		ProcessedEntity processedEntity = new ProcessedEntity(process);
		
		List<String> nlpFeatures = processedEntity.getNlpFeatures();
		List<String> workflows = processedEntity.getWorkflowNames();
		
		if(nlpFeatures.size() != workflows.size())
			logger.error("WorkflowName missing");
		else {
			for(int i = 0;i<nlpFeatures.size();i++) {
				List<String> tmpNlpFeature = new ArrayList<String>();
				List<String> tmpworkflowName = new ArrayList<String>();
				tmpNlpFeature.add(nlpFeatures.get(i));
				tmpworkflowName.add(workflows.get(i));
				ProcessedEntity tmpEntity = new ProcessedEntity();
				tmpEntity.setEntityType(processedEntity.getEntityType().toLowerCase());
				tmpEntity.setFieldName(processedEntity.getFieldName());
				tmpEntity.setId(processedEntity.getId());
				tmpEntity.setNlpFeatures(tmpNlpFeature);
				tmpEntity.setWorkflowNames(tmpworkflowName);
				tmpEntity.setText(processedEntity.getText());
				
				rabbitTemplate.convertAndSend(ConfigureRabbitMq.EXCHANGE_NAME,
		                ConfigureRabbitMq.QUEUE_NAME, tmpEntity);
			}
		}
		
	}
	
	@PostMapping("/queryTextAnalytics")
	@ApiOperation("Retrieve entities from Text Analytics Backend")
	@Async
	public QueryResponse query(@RequestBody Query query) throws IOException  {
	
		String nlpExpression = query.toString();
		List<String> resultString = new ArrayList<String>();
		String dslQuery = "";
		JSONObject jsonResult = null;
		JSONObject hits = null;
		JSONArray hitsArray = null;
		
		//parse Nlp Expression
		resultString = QueryUtils.parseNlpExpression(nlpExpression);
		
		String includeFields = "\"includes\":[";
		for(int k =1;k<resultString.size()-1; k++) {
			includeFields = includeFields + "\"" + resultString.get(k).substring(resultString.get(k).indexOf(".")+1) + "\",";
		}
		includeFields = includeFields.substring(0, includeFields.length()-1) + "]";
		
		//generate Elasticsearch DSL query
		dslQuery = QueryUtils.generateDSLQuery(RESTclient, resultString.get(resultString.size()-1));
		dslQuery = dslQuery.replaceAll("\"includes\":\\[\"\\w+(.\\w+)*\"(,\"\\w+(.\\w+)*\")*\\]", includeFields);
		
		//Run Elasticsearch DSL query
		Request request = new Request("POST", "/"+ resultString.get(0) +"/_search");
		request.setJsonEntity(dslQuery);
		Response response;
		
		QueryResponse queryResponse = new QueryResponse();
		
		response = RESTclient.getLowLevelClient().performRequest(request);
		jsonResult = new JSONObject(EntityUtils.toString(response.getEntity()));
		
		
		List<String> headerList = new ArrayList<String>();
		
		for(int i =1; i<resultString.size()-1;i++)
		{
			headerList.add(resultString.get(i).toString());
		}
		queryResponse.setHeader(headerList);
		
		
		List<String> records = new ArrayList<String>();
		hits = jsonResult.getJSONObject("hits");
		hitsArray = hits.getJSONArray("hits");
		
		for (Object hit : hitsArray) {
			JSONObject obj = (JSONObject) hit;
			records.add(obj.get("_source").toString());
		}
		if(records.size()>0) {
			List<String> headerValueList = new ArrayList<String>();
			for(String hTag : headerList) {
				String headerValue = hTag;
				headerValue = headerValue.substring(headerValue.indexOf(".")+1);
				if(headerValue.indexOf('.', headerValue.indexOf('.')) != -1) {
					headerValue = headerValue.substring(headerValue.indexOf(".")+1);
					headerValue = headerValue.substring(0,headerValue.indexOf("."));
				}
				if(!headerValueList.contains(headerValue))
					headerValueList.add(headerValue);
			}
			
			List<String> headerSequence = new ArrayList<String>();
			for(String headerTag : headerList ) {
				headerTag = headerTag.substring(headerTag.indexOf(".")+1);
				headerTag = headerTag.substring(headerTag.indexOf(".")+1);
				headerSequence.add(headerTag);
			}
			
			List<String> result = new ArrayList<String>();
			for(String record : records) {
					result = QueryUtils.compileResult(record, headerValueList, headerSequence);
					queryResponse.addRecordsItem(result);
			}
		}
		return queryResponse;
	}

	@PostMapping("/deleteDocument")
	@ApiOperation("Deletes document from Text Analytics Backend")
	@Async
	public void delete(@RequestBody Delete delete) throws IOException {

		// Bool Query
		BoolQueryBuilder boolQuery = new BoolQueryBuilder();
		boolQuery.must(new MatchQueryBuilder("id", delete.getId()));
		
		// Delete by Query
		DeleteByQueryRequest request = new DeleteByQueryRequest(delete.getEntityType().toLowerCase());
		request.setQuery(boolQuery);

		ActionListener<BulkByScrollResponse> listener;
		listener = new ActionListener<BulkByScrollResponse>() {
			@Override
			public void onResponse(BulkByScrollResponse bulkResponse) {
				logger.info(bulkResponse.getStatus().getDeleted());
			}

			@Override
			public void onFailure(Exception e) {
				logger.error(e);
			}
		};

		RESTclient.deleteByQueryAsync(request, RequestOptions.DEFAULT, listener);
		

	}

}

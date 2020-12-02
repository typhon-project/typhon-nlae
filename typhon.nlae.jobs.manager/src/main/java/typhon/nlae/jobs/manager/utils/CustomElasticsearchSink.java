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

package typhon.nlae.jobs.manager.utils;

import java.util.Properties;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import typhon.nlae.jobs.manager.JobManager;

public class CustomElasticsearchSink extends RichSinkFunction<String> {
	
	private static final long serialVersionUID = 9156453251316873855L;
	
	private RestHighLevelClient esClient;
	
	private static final Logger logger = LoggerFactory.getLogger(JobManager.class);
	
	String esType = "";
	
	@Override
	public void open(Configuration parameters) throws Exception {
		// Declare properties fields
		String fileName = "/application.properties";
		Properties appProps = new Properties();
		appProps.load(JobManager.class.getResourceAsStream(fileName));
		 
		String esHost = appProps.getProperty("elasticsearch.host");
		int esPort = Integer.parseInt(appProps.getProperty("elasticsearch.port"));
		String esProtocol = appProps.getProperty("elasticsearch.protocol");
		esType = appProps.getProperty("elasticsearch.type");
				
		esClient = new RestHighLevelClient(
				RestClient.builder(new HttpHost(esHost, esPort, esProtocol)));
	}

	@Override
	public void close() throws Exception {
		this.esClient.close();
	}

	@Override
	public void invoke(String value) throws Exception {
		
		String indexName = "";
		
		JsonElement inputEntity =  JsonParser.parseString(value);
		JsonObject inputObject = inputEntity.getAsJsonObject();
		
		// Check if index already exists in Elasticsearch
		indexName = inputObject.get("entityType").getAsString().toLowerCase();
		inputObject.remove("entityType");
		String jsonString = inputObject.toString();
		GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        boolean exists = esClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (!exists) { // index doesn't exist
			
			//Create Index and ADD Document
			IndexRequest indexRequest = new IndexRequest();
			indexRequest.index(indexName).type(esType).source(jsonString,
					XContentType.JSON);

			ActionListener<IndexResponse> listener;
			listener = new ActionListener<IndexResponse>() {

				@Override
				public void onResponse(IndexResponse response) {
					logger.info("Document Indexed Successfully");
				}

				@Override
				public void onFailure(Exception e) {
					logger.error("Exception occurred while indexing document: "+e.getMessage());
				}

			};

			esClient.indexAsync(indexRequest, RequestOptions.DEFAULT, listener);
			
		}else {
			//Index exists
			String id = inputObject.get("id").getAsString();
			
			//Check if Document exists
			SearchRequest searchRequest = new SearchRequest(indexName);
			
			QueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery("id", id);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.query(matchQueryBuilder);
			searchRequest.source(sourceBuilder);
			
			SearchResponse searchResponse = esClient.search(searchRequest,RequestOptions.DEFAULT);
			SearchHit[] hits = searchResponse.getHits().getHits();
			
			IndexRequest indexRequest = new IndexRequest();
			if(hits.length>0) {
				//Document exists, Append result
				String esId = hits[0].getId();
				
				//Update nlpFeatures array
				JSONObject updateObject = new JSONObject(jsonString);
				jsonString = updateObject.toString();
				
				UpdateRequest updateRequest = new UpdateRequest();
				updateRequest.index(indexName);
				updateRequest.type(esType);
				updateRequest.id(esId);
				updateRequest.doc(jsonString, XContentType.JSON);
				
				ActionListener<UpdateResponse> listener;
				listener = new ActionListener<UpdateResponse>() {

					@Override
					public void onResponse(UpdateResponse response) {
						logger.info("Document Indexed Successfully");
					}

					@Override
					public void onFailure(Exception e) {
						logger.error("Exception occurred while indexing document: "+e.getMessage());
					}

				};

				esClient.updateAsync(updateRequest,RequestOptions.DEFAULT,listener);
				
			}else {
				indexRequest.index(indexName).type(esType).source(jsonString,
						XContentType.JSON);
				ActionListener<IndexResponse> listener;
				listener = new ActionListener<IndexResponse>() {

					@Override
					public void onResponse(IndexResponse response) {
						logger.info("Document Indexed Successfully");
					}

					@Override
					public void onFailure(Exception e) {
						logger.error("Exception occurred while indexing document: "+e.getMessage());
					}

				};

				esClient.indexAsync(indexRequest,RequestOptions.DEFAULT,listener);
			}
			
			
		}
	}
}

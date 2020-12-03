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

package typhon.nlae.jobs.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import typhon.nlae.jobs.manager.utils.CustomElasticsearchSink;

import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSource;
import org.apache.flink.streaming.connectors.rabbitmq.common.RMQConnectionConfig;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@SuppressWarnings("deprecation")
public class JobManager {

	private static final Logger logger = LoggerFactory.getLogger(JobManager.class);
	
	public static void main(String[] args) throws Exception {

		// Declare properties fields
		String fileName = "/application.properties";
		Properties appProps = new Properties();
		appProps.load(JobManager.class.getResourceAsStream(fileName));
		 
		//RabbitMQ Server properties
		String rmqHost = appProps.getProperty("rabbitmq.host");
		int rmqPort = Integer.parseInt(appProps.getProperty("rabbitmq.port"));
		String rmqUName = appProps.getProperty("rabbitmq.username");
		String rmqPass = appProps.getProperty("rabbitmq.password");
		String rmqVHost = appProps.getProperty("rabbitmq.virtualhost");
		String rmqQueue = appProps.getProperty("rabbitmq.queue");
		
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		env.enableCheckpointing(300000L); //start a checkpoint every 5 seconds
		env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
				10, // number of restart attempts 
				Time.seconds(5).toMilliseconds() //delay
		)); 
		
		CustomElasticsearchSink esSink = new CustomElasticsearchSink();
		
		final RMQConnectionConfig connectionConfig = new RMQConnectionConfig.Builder()
			    .setHost(rmqHost)
			    .setPort(rmqPort)
			    .setUserName(rmqUName)
                .setPassword(rmqPass)
                .setVirtualHost(rmqVHost)
			    .build();
		
		DataStream<String> dataStream = env.addSource(new RMQSource<String>(connectionConfig,
                rmqQueue,
                new SimpleStringSchema()));

        DataStream<String> input = dataStream.flatMap(new NlpProcessingMapper());
        
        input.addSink(esSink);
		env.execute("Typhon NLAE Job Manager");
	}

	/**
	 * NlpProcessingMapper associates HTTP requests to NLP functions via document attribute checking  
	 */
	public static class NlpProcessingMapper extends RichFlatMapFunction<String,  String> {
    	/**
		 * Default Serial Version
		 */
		private static final long serialVersionUID = 1L;
		private NlpProcessor nlpProcessor;
		
		@Override
		public void open(Configuration p) throws Exception {
    	
			nlpProcessor = new NlpProcessor();
		}
    	
    	@Override
        public void flatMap(String value, Collector<String> out) {
    		String nlpTask = "";
    		String workflowName = "";
    		new ObjectMapper();
        	try {
       		
        		JsonElement inputEntity =  new JsonParser().parse(value);
        		JsonObject inputObject = inputEntity.getAsJsonObject();
        		nlpTask = inputObject.get("nlpFeatures").getAsString().toLowerCase();
        		workflowName = inputObject.get("workflowNames").getAsString();
        		String documentText = inputObject.get("text").getAsString();
    			String result ="";
    			
    			switch (nlpTask) {
				case "sentimentanalysis":
					result = nlpProcessor.getSentiment(documentText, workflowName);
					result = "\t\"SentimentAnalysis\" : " + result;
					break;
				case "namedentityrecognition":
					result = nlpProcessor.getNer(documentText, workflowName);
					result =  "\t\"NamedEntityRecognition\" : " + result;
					break;
				case "tokenisation":
					result = nlpProcessor.getTokenise(documentText, workflowName);
					result = "\t\"Tokenisation\" : " + result;
					break;
				case "sentencesegmentation":
					result = nlpProcessor.getSentence(documentText, workflowName);
					result = "\t\"SentenceSegmentation\" : " + result;
					break;
				case "paragraphsegmentation":
					result = nlpProcessor.getParagraph(documentText, workflowName);
				result = "\t\"ParagraphSegmentation\" : " + result;
					break;
				case "phraseextraction":
					result = nlpProcessor.getPhrase(documentText, workflowName);
					result = "\t\"PhraseExtraction\" : " + result;
					break;
				case "termextraction":
					result = nlpProcessor.getTerm(documentText, workflowName);
					result = "\t\"TermExtraction\" : " + result;
					break;
				case "ngramextraction":
					result = nlpProcessor.getNgram(documentText, workflowName);
					result = "\t\"nGramExtraction\" : " + result;
					break;
				case "postagging":
					result = nlpProcessor.getPos(documentText, workflowName);
					result = "\t\"POSTagging\" : " + result;
					break;
				case "lemmatisation":
					result = nlpProcessor.getLemma(documentText, workflowName);
					result = "\t\"Lemmatisation\" : " + result;
					break;
				case "stemming":
					result = nlpProcessor.getStemming(documentText, workflowName);
					result = "\t\"Stemming\" : " + result;
					break;
				case "dependencyparsing":
					result = nlpProcessor.getDependency(documentText, workflowName);
					result = "\t\"DependencyParsing\" : " + result;
					break;
				case "coreferenceresolution":
					result = nlpProcessor.getCoreference(documentText, workflowName);
					result = "\t\"CoreferenceResolution\" : " + result;
					break;
				case "relationextraction":
					result = nlpProcessor.getRelation(documentText, workflowName);
					result = "\t\"RelationExtraction\" : " + result;
					break;
				case "chunking":
					result = nlpProcessor.getChunking(documentText, workflowName);
					result = "\t\"Chunking\" : " + result;
					break;
				default:
					break;
				}
    		
    			//Generate result Json String
    			String jsonString = "";
    			jsonString = "{\n" + "\t\"id\" : \"" + inputObject.get("id").getAsString() + "\",\n";
    			jsonString = jsonString + "\t\"entityType\" : \"" + inputObject.get("entityType").getAsString() + "\",\n";
    			jsonString = jsonString + "\t\"" + inputObject.get("fieldName").getAsString() + "\" : {\n";
    			jsonString = jsonString + result;
    			jsonString = jsonString.substring(0,jsonString.length() - 2);
    			jsonString = jsonString + "\t}\n}";
    			
    			out.collect(jsonString);
    			
    		}catch (Exception e) {
    			e.printStackTrace();
				logger.error("Exception occurred while processing entity "+e.getMessage());
				
				
			}    
        }
    }
}

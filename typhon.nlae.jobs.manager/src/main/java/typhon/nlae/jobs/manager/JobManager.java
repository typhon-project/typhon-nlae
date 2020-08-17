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

/**
 * This JobManager class encapsulates a Flink Queue for NLAE job processing
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
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

        DataStream<JsonObject> input = dataStream.flatMap(new NlpProcessingMapper());
        
        input.addSink(esSink);
		env.execute("Typhon NLAE Job Manager");
	}

	/**
	 * NlpProcessingMapper associates HTTP requests to NLP functions via document attribute checking  
	 */
	public static class NlpProcessingMapper extends RichFlatMapFunction<String,  JsonObject> {
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
        public void flatMap(String value, Collector<JsonObject> out) {
    		String nlpTask = "";
    		String workflowName = "";
    		String key = "";
    		new ObjectMapper();
        	try {
       		
        		JsonElement jsonEntity =  new JsonParser().parse(value);
        		JsonObject processedEntity = jsonEntity.getAsJsonObject();
        		nlpTask = processedEntity.get("nlpFeatures").getAsString();
        		workflowName = processedEntity.get("workflowNames").getAsString();
        		logger.info("WORKFLOWNAME: "+ workflowName);
        		String documentText = processedEntity.get("text").getAsString();
    			String result ="";
    			
    			switch (nlpTask) {
				case "sentimentanalysis":
					result = nlpProcessor.getSentiment(documentText, workflowName);
					key = "SentimentAnalysis";
					break;
				case "namedentityrecognition":
					result = nlpProcessor.getNer(documentText, workflowName);
					key = "NamedEntityRecognition";
					break;
				case "tokenisation":
					result = nlpProcessor.getTokenise(documentText, workflowName);
					key = "Tokenisation";
					break;
				case "sentencesegmentation":
					result = nlpProcessor.getSentence(documentText, workflowName);
					key = "SentimentSegmentation";
					break;
				case "paragraphsegmentation":
					result = nlpProcessor.getParagraph(documentText, workflowName);
					key = "ParagraphSegmentation";
					break;
				case "phraseextraction":
					result = nlpProcessor.getPhrase(documentText, workflowName);
					key = "PhraseExtraction";
					break;
				case "termextraction":
					result = nlpProcessor.getTerm(documentText, workflowName);
					key = "TermExtraction";
					break;
				case "ngramextraction":
					result = nlpProcessor.getNgram(documentText, workflowName);
					key = "nGramExtraction";
					break;
				case "postagging":
					result = nlpProcessor.getPos(documentText, workflowName);
					key = "POSTagging";
					break;
				case "lemmatisation":
					result = nlpProcessor.getLemma(documentText, workflowName);
					key = "Lemmatisation";
					break;
				case "stemming":
					result = nlpProcessor.getStemming(documentText, workflowName);
					key = "Stemming";
					break;
				case "dependencyparsing":
					result = nlpProcessor.getDependency(documentText, workflowName);
					key = "DependencyParsing";
					break;
				case "coreferenceresolution":
					result = nlpProcessor.getCoreference(documentText, workflowName);
					key = "CoreferenceResolution";
					break;
				case "relationextraction":
					result = nlpProcessor.getRelation(documentText, workflowName);
					key = "RelationExtraction";
					break;
				case "chunking":
					result = nlpProcessor.getChunking(documentText, workflowName);
					key = "Chunking";
					break;
				default:
					break;
				}
    			
    			JsonElement jsonElement = new JsonParser().parse(result);
    			jsonEntity.getAsJsonObject().add(key,jsonElement);
    			
    			processedEntity = jsonEntity.getAsJsonObject();
    	        out.collect(processedEntity);
    		}catch (Exception e) {
				logger.error("Exception occurred while processing entity "+e.getMessage());
				
				
			}    
        }
    }
}

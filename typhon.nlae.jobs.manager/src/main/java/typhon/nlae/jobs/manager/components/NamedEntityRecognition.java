package typhon.nlae.jobs.manager.components;

import java.util.Properties;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * This NamedEntityRecognition class provides Named-Entity Recognition Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class NamedEntityRecognition {
	
	private StanfordCoreNLP pipeline;
	private static StanfordCoreNLP atbPipeline;
	
	private CoreDocument document;
	
	public NamedEntityRecognition() {
		try {
			//Default NER pipeline
			Properties props = new Properties();
	        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
	        pipeline = new StanfordCoreNLP(props);	
	        
	        atbPipeline = null;
	   } catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}
	}
	
	public String getNer(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			document = new CoreDocument(input);
	        
			//ATB Custom Weather NER Annotator
			if(workflowName.equalsIgnoreCase("atb_weather_ner")) {
				//ATB Weather NER pipelines
		        String modelName = "/opt/flink/usrlib/ATB-ner-model-de.ser.gz";
				if(atbPipeline == null) {
		        	Properties propsatb = new Properties();
			        propsatb.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
					propsatb.setProperty("ner.model", modelName);
			        atbPipeline = new StanfordCoreNLP(propsatb);
			    }
		        atbPipeline.annotate(document);
		    }
			else {//Default NER Tagger
				pipeline.annotate(document);
		   }
			
			result = "[";
	        
	        for (CoreEntityMention em : document.entityMentions()) {
	            result = result +"{\"begin\" : " + em.charOffsets().first + ",\"end\" : " + em.charOffsets().second + ",\"NamedEntity\" : \"" + em.entityType() + "\",\"WordToken\" : \""+ em.text() + "\" },";
	            hasResult = true;
	        }
			
	        if(hasResult)
	        	result = result.substring(0,result.length()-1) + "]";
	        else
	        	result = result+"]";
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Named Entity Recognition Task : "+e.getMessage());
		}
        return result;
	}

}

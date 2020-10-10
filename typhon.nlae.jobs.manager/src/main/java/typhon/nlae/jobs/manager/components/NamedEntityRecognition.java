package typhon.nlae.jobs.manager.components;

import java.util.Properties;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import typhon.nlae.jobs.manager.utils.AlphaBankNER;

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
		result = "[";
		
		try {
			document = new CoreDocument(input);
	        
			//ATB Custom Weather NER Annotator
			if(workflowName.equalsIgnoreCase("atb_weather_ner")) {
				//ATB Weather NER model
		        String modelName = "/opt/flink/plugins/ATB-ner-model-de.ser.gz";
				if(atbPipeline == null) {
		        	Properties propsatb = new Properties();
			        propsatb.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
					propsatb.setProperty("ner.model", modelName);
			        atbPipeline = new StanfordCoreNLP(propsatb);
			    }
		        atbPipeline.annotate(document);
		    }
			else if(workflowName.equalsIgnoreCase("alpha_bank_ner")) { //Alhpa Bank Custom Annotator
				AlphaBankNER alphaNER = new AlphaBankNER(input);
				result = result + alphaNER.getResult();
			}
			else {//Default NER Tagger
				pipeline.annotate(document);
			}
			
			//Results for Default and ATB Classifiers
			if(!workflowName.equalsIgnoreCase("alpha_bank_ner")) {
				for (CoreEntityMention em : document.entityMentions()) {
		            result = result +"{\"begin\" : " + em.charOffsets().first + ",\"end\" : " + em.charOffsets().second + ",\"NamedEntity\" : \"" + em.entityType() + "\",\"WordToken\" : \""+ em.text() + "\" },";
		        }
			}
			
	        
			if(result.length()>10)
	        	result = result.substring(0,result.length()-1) + "]";
	        else
	        	result = result+"]";
			
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Named Entity Recognition Task : "+e.getMessage());
		}
        return result;
	}

}

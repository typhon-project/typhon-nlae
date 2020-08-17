/**
 * 
 */
package typhon.nlae.jobs.manager.components;

import java.util.Properties;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

/**
 * This Lemmatisation class provides document lemmatisation Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class Lemmatisation {
	private StanfordCoreNLP pipeline;
	private CoreDocument document;
	
	public Lemmatisation() {
		try {
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
	        pipeline = new StanfordCoreNLP(props);
			
		} catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}
	}
	
	public String getLemma(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			document = new CoreDocument(input);
	        pipeline.annotate(document);
	        result = "[";
	        
	        for(CoreSentence sentence: document.sentences()) {
	        	for (CoreLabel token : sentence.tokens()) {
		            result = result +"{\"begin\" : " + token.beginPosition() + ",\"end\": " + token.endPosition() + ",\"Token\": \"" + token.get(CoreAnnotations.LemmaAnnotation.class) + "\"},";
		            hasResult = true;
		        }
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

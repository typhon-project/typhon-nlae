/**
 * 
 */
package typhon.nlae.jobs.manager.components;

import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * This SentimentAnalysis class provides sentiment socring Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class SentimentAnalysis {

	private StanfordCoreNLP pipeline;
	private Annotation annotation;
	
	public SentimentAnalysis() {
		try {
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
			props.setProperty("parse.binaryTrees", "true");
			props.setProperty("enforceRequirements", "false");
			pipeline = new StanfordCoreNLP(props);
		} catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}	
	}
	
	public String getSentiment(String input, String workflowName) {
		int sentenceSentiment = 0;
        int count = 0;
        double overallSentiment = 0;
        String result = "";
        try {
        	annotation = pipeline.process(input);
        	Tree tree = null;
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            	
            	tree =  sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            	sentenceSentiment = sentenceSentiment + RNNCoreAnnotations.getPredictedClass(tree);
            	count++;
            }
            if(count>0)
            	overallSentiment = Math.ceil((double)sentenceSentiment/count);
            
			result = result+"[{\"Sentiment\": \"" + (int)overallSentiment  + "\"}]";
			
			
        }catch(Exception e) {
    	   
        }
		return result;
	}
}

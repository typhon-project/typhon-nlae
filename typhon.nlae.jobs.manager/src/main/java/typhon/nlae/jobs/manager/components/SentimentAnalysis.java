/**
 * 
 */
package typhon.nlae.jobs.manager.components;

import java.util.Properties;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Paragraph;
import de.tudarmstadt.ukp.dkpro.core.tokit.ParagraphSplitter;
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
		int mainSentiment = 0;
        int longest = 0;
        String result = "";
        boolean hasResult = false;
        try {
        	annotation = pipeline.process(input);
        	CoreMap mainSentence = null;
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            	
            	String partText = sentence.toString();
	            if (partText.length() > longest) {
	                mainSentence = sentence;
	                hasResult = true;
	                //longest = partText.length();
	            }
            }
            Tree tree = mainSentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
			result = result+"[{\"Sentiment\": \"" + sentiment  + "\"}]";
			
			
        }catch(Exception e) {
    	   
        }
		return result;
	}
}

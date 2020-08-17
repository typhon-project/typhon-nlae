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
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * This SentenceSegmentation class provides sentence splitting Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class SentenceSegmentation {

	private StanfordCoreNLP pipeline;
	private CoreDocument document;
	
	public SentenceSegmentation() {
		try {
			Properties props = new Properties();
	        props.setProperty("annotators", "tokenize,ssplit");
	        pipeline = new StanfordCoreNLP(props);
		} catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}	
	}
	
	public String getSentence(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			document = new CoreDocument(input);
	        pipeline.annotate(document);
	        result = "[";
	        
	        for(CoreSentence sentence: document.sentences()) {
	            result = result +"{\"begin\" : " + sentence.charOffsets().first + ",\"end\": " + sentence.charOffsets().second + ",\"Sentence\": \"" + sentence.text() + "\"},";
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

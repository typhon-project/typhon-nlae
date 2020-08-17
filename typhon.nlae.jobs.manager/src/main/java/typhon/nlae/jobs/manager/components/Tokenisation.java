/**
 * 
 */
package typhon.nlae.jobs.manager.components;

import java.io.BufferedReader;
import java.io.StringReader;
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
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * This Tokenisation class provides document tokenisation Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class Tokenisation {

	public Tokenisation() {
	}
	
	public String getToken(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			PTBTokenizer<Word> ptb = PTBTokenizer.newPTBTokenizer(new BufferedReader(new StringReader(input)));
			result = "[";
	        
			while (ptb.hasNext()) {
				Word w = ptb.next();
	            result = result +"{\"begin\" : " + w.beginPosition() + ",\"end\": " + w.endPosition() + ",\"Token\": \"" + w.value() + "\"},";
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

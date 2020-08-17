/**
 * 
 */
package typhon.nlae.jobs.manager.components;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Paragraph;
import de.tudarmstadt.ukp.dkpro.core.ngrams.util.NGramStringIterable;
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
import typhon.nlae.jobs.manager.NlpProcessor;
import typhon.nlae.jobs.manager.utils.Rake;

/**
 * This NgramExtraction class provides Ngram Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class NgramExtraction {
	
	public NgramExtraction() {
	}
	
	public String getNgram(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			result = "[";
			String[] tokens = StringUtils.split(input);
			Iterable<String> bigram = new NGramStringIterable(tokens, 1, 2);
			Iterable<String> trigram = new NGramStringIterable(tokens, 1, 3);
			
			// Process Bigram results
			for(String s: bigram) {
				result = result +"{\"begin\" : " + input.indexOf(s) + ",\"end\": " + input.lastIndexOf(s) + ",\"Token\": \"" + s + "\",\"NgramType\": \"" + "Bigram" + "\"},";
	        	hasResult = true;
			}
			
			// Process Trigram results
			for(String s: trigram) {
				result = result +"{\"begin\" : " + input.indexOf(s) + ",\"end\": " + input.lastIndexOf(s) + ",\"Token\": \"" + s + "\",\"NgramType\": \"" + "Trigram" + "\"},";
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

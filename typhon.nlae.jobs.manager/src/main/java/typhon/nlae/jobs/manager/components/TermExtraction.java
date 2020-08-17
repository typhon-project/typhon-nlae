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
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import typhon.nlae.jobs.manager.NlpProcessor;
import typhon.nlae.jobs.manager.utils.Rake;

/**
 * This TermExtraction class provides Term-score Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class TermExtraction {
	private Rake rake;
	private StanfordCoreNLP pipeline;
	private CoreDocument document;
	
	public TermExtraction() {
		try {
			rake = new Rake();
			Properties props = new Properties();
	        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
	        pipeline = new StanfordCoreNLP(props);
		} catch (Exception e) {
			System.out.println("Exception in initialization");
		}
	}
	
	public String getTerm(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			result = "[";
			
			document = new CoreDocument(input);
	        pipeline.annotate(document);
	        
			LinkedHashMap<String, Double> results = rake.getKeywordsFromText(input);
			Set<Map.Entry<String, Double>> keys = results.entrySet();
			for (CoreEntityMention em : document.entityMentions()) {
		        
			for(Map.Entry<String, Double> k: keys){
	        	String phrase = k.getKey();
	            
		        	for(CoreLabel token: em.tokens()) {
		        		if(token.originalText().equals(phrase)) {
		        			result = result +"{\"begin\" : " + input.indexOf(phrase) + ",\"end\" : " + input.lastIndexOf(phrase) + ",\"TargetEntity\" : [{\"begin\" : " + token.beginPosition() + ",\"end\" : " + token.endPosition() + ",\"NamedEntity\" : \"" + em.entityType() + "\"}],\"WeightedToken\" : " + Math.round(k.getValue()) + "},";
				            hasResult = true;
		        		}
		        	}
		        }
	        	
	        }
			
	        if(hasResult)
	        	result = result.substring(0,result.length()-1) + "]"; 
	        else
	        	result = result+"]";
	        
	        System.out.println(result);
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Named Entity Recognition Task : "+e.getMessage());
		}
        return result;
	}
	
}

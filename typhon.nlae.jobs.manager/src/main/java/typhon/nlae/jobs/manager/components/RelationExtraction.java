/**
 * 
 */
package typhon.nlae.jobs.manager.components;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
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
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;
import typhon.nlae.jobs.manager.NlpProcessor;
import typhon.nlae.jobs.manager.utils.Rake;

/**
 * This RelationExtraction class provides relation extraction Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class RelationExtraction {
	private StanfordCoreNLP pipeline;
	private Annotation document;
	
	public RelationExtraction() {
		
		try {
			Properties props = new Properties();
	        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse,natlog,openie");
	        pipeline = new StanfordCoreNLP(props);	
			
		} catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}
	}
	
	public String getRelation(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
		        document = new Annotation(input);
		        pipeline.annotate(document);
	        result = "[";
	        
	        for(CoreMap sentence: document.get(CoreAnnotations.SentencesAnnotation.class)) {
	            Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
	            for(RelationTriple rel: triples){
	            	result = result +"{\"begin\" : " + rel.relationTokenSpan().first + ",\"end\" : " + rel.relationTokenSpan().second() + ",\"SourceEntity\" : [{\"begin\" : " + rel.subjectHead().beginPosition() + ",\"end\" : " + rel.subjectHead().endPosition() + ",\"NamedEntity\" : \"" + rel.subjectHead().ner() + "\"}]," + "\"TargetEntity\" : [{\"begin\" : " + rel.objectHead().beginPosition() + ",\"end\" : " + rel.objectHead().endPosition() + ",\"NamedEntity\" : \"" + rel.objectHead().ner() + "\"}], \"RelationName\" : \"" + rel.relationGloss() + "\"},";
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

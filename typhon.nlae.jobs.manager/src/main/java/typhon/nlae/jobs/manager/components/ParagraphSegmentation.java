/**
 * 
 */
package typhon.nlae.jobs.manager.components;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Paragraph;
import de.tudarmstadt.ukp.dkpro.core.tokit.ParagraphSplitter;

/**
 * This ParagraphSegmentation class provides paragraph spliting Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class ParagraphSegmentation {

	private AnalysisEngine ae;
	
	public ParagraphSegmentation() {
	}
	
	public String getParagraph(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			result = "[";
			
			// Define Paragraph Splitting criteria
						AnalysisEngine ae =
						        UIMAFramework.produceAnalysisEngine(AnalysisEngineFactory.createEngineDescription(ParagraphSplitter.class,
						                ParagraphSplitter.PARAM_SPLIT_PATTERN,
						                ParagraphSplitter.DOUBLE_LINE_BREAKS_PATTERN));
			// Declare UIMA object
			JCas source = JCasFactory.createJCas();
	        source.setDocumentText(input);
	        
	        // Annotate UIMA object
	        ae.process(source);
	        for (Paragraph p : JCasUtil.select(source, Paragraph.class)) {
	        	result = result +"{\"begin\" : " + p.getBegin() + ",\"end\" : " + p.getEnd() + ",\"Paragraph\": \"" + p.getCoveredText() + "\"},";
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

/**
 * 
 */
package typhon.nlae.jobs.manager.components;

import java.util.List;
import edu.illinois.cs.cogcomp.annotation.AnnotatorService;
import edu.illinois.cs.cogcomp.core.datastructures.ViewNames;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.Constituent;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.SpanLabelView;
import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TextAnnotation;
//import edu.illinois.cs.cogcomp.core.datastructures.textannotation.TokenLabelView;
import edu.illinois.cs.cogcomp.pipeline.main.PipelineFactory;

/**
 * This Chunking class provides shallow parsing Nlp functionality  
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class Chunking {
	// Declare NLP pipeline object
	private AnnotatorService pipeline;
	private TextAnnotation ta;
	
	public Chunking() {
	}
	
	public String getChunking(String input, String workflowName) throws Exception {
		String result = "";
		boolean hasResult = false;
		try {
			
			String docId = "text"; // arbitrary string identifier
	        //String textId = "body"; // arbitrary string identifier
	        result = "[";
	        // Define the desired NLP View
	        
	        pipeline = PipelineFactory.buildPipeline(ViewNames.POS, ViewNames.SHALLOW_PARSE);
	        
	        // Annotate the input
	        ta = getChunkingTA(docId, input, pipeline);

	        // Retreive constituents
	        SpanLabelView shallowParseView = (SpanLabelView) ta
	                .getView(ViewNames.SHALLOW_PARSE);
	        
	        SpanLabelView pos = (SpanLabelView) ta.getView(ViewNames.POS);
	        
	        /*TokenLabelView token =
	                (TokenLabelView) ta.getView(ViewNames.TOKENS_GOLD);*/
	        List<Constituent> shallowParseConstituents = shallowParseView
	                .getConstituents();
	        for (Constituent c : shallowParseConstituents) {
	        	//TokenLabelView posView = (TokenLabelView) ta.getView(ViewNames.POS);

	        	for (int i = 0; i < ta.size(); i++) {
	        		result = result +"{\"begin\" : " + c.getStartCharOffset() + ",\"end\" : " + c.getEndCharOffset() + ",\"TokenAnnotation\" : [{\"begin\" : " + input.indexOf(ta.getToken(i)) + ",\"end\" : " + input.lastIndexOf(ta.getToken(i)) + ",\"Token\" : \"" + ta.getToken(i) + "\"}]," + "\"PosAnnotation\" : [{\"begin\" : " + c.getStartSpan() + ",\"end\" : " + c.getEndSpan() + ",\"PosValue\" : \"" + ta.getToken(i) + "\",\"PosTag\" : \"" + pos.getLabel(i) +"\"}], \"Label\" : \"" + c.getLabel() + "\"},";
	        		hasResult = true;
	        	}
	        }
	        
			if(hasResult)
	        	result = result.substring(0,result.length()-1) + "]";
	        else
	        	result = result+"]";
			
			System.out.println(result);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	/**
	 * Known issue: Initializing multiple instances of PipelineFactory in a single run will lead to an exception in MapDB (http://cogcomp.github.io/cogcomp-nlp/pipeline/)
	 * 
	 * This internal static method reuses the NLP pipeline object in order to prevent pipeline locking on reuse for the Annotation process, therefore the annotation method is encapsulated within a seperate method and called each time the annotation is required. 
	 * @param id is the Document Id
	 * @param text is the Document
	 * @param prep is the pre-existing pipeline used in annotations
	 * @return TextAnnotation is the document marked-up with annotations
	 * @throws Exception could return pipeline locked or uninitalised
	 */
	public static TextAnnotation getChunkingTA(String id, String text, AnnotatorService prep) throws Exception{
        TextAnnotation rec = prep.createAnnotatedTextAnnotation(id, "", text);
        return rec;
    }
	
}

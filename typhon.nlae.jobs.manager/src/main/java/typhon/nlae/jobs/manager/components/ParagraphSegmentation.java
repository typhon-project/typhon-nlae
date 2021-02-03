/*******************************************************************************
 * Copyright (C) 2020 Edge Hill University
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package typhon.nlae.jobs.manager.components;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Paragraph;
import de.tudarmstadt.ukp.dkpro.core.tokit.ParagraphSplitter;

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
	        	result = result.substring(0,result.length()-1) + "],\n";
	        else
	        	result = result+"],\n";
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Paragraph Segmentation Task : "+e.getMessage());
		}
	    return result;
	}
}

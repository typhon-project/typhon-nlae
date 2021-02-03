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

import java.util.Properties;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.coref.data.Mention;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class CoreferenceResolution {
	private StanfordCoreNLP pipeline;
	private Annotation document;
	
	public CoreferenceResolution() {
		
		try {
			Properties props = new Properties();
	        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, coref");
	        pipeline = new StanfordCoreNLP(props);
			
		} catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}
	}
	
	public String getCoreference(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			
			document = new Annotation(input);
	        pipeline.annotate(document);
	        result = "[";
	        
	        // For every Head Antecedent
	        for (CorefChain cc : document.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
	            for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
		            for (Mention m : sentence.get(CorefCoreAnnotations.CorefMentionsAnnotation.class)) {
		            	// If the mention is an anaphor
		            	if(m.isPronominal()) {
		            		result = result +"{\"begin\" : " + cc.getRepresentativeMention().startIndex + ",\"end\" : " + cc.getRepresentativeMention().endIndex + ",\"Antecedent\" : [{\"begin\" : " + cc.getRepresentativeMention().startIndex + ",\"end\" : " + cc.getRepresentativeMention().endIndex + ",\"Token\" : \"" + cc.getRepresentativeMention().mentionSpan + "\"}]," + "\"Anaphor\" : [{\"begin\" : " + m.startIndex + ",\"end\" : " + m.endIndex + ",\"Token\" : \"" + m.toString() + "\"}]},";
				        	hasResult = true;
		            	}
		            }
		        }
	        }
	        
	        if(hasResult)
	        	result = result.substring(0,result.length()-1) + "],\n";
	        else
	        	result = result+"],\n";
	        
	    }catch(Exception e) {
			System.out.println("Excpetion occurred while performing Coreference Resolution Task : "+e.getMessage());
		}
        return result;
	}
	
}

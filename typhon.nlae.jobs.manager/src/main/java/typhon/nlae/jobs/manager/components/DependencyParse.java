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

import java.util.List;
import java.util.Properties;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.TypedDependency;

public class DependencyParse {
	private StanfordCoreNLP pipeline;
	private CoreDocument document;
	
	public DependencyParse() {
		try {
			Properties props = new Properties();
	        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,depparse");
	        pipeline = new StanfordCoreNLP(props);		
			
		} catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}
	}
	
	public String getDependency(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			document = new CoreDocument(input);
	        pipeline.annotate(document);
	        result = "[";
	        
	        List<CoreSentence> sentences = document.sentences();
	        for(CoreSentence sentence: sentences) {
	            SemanticGraph dependencyParse = sentence.dependencyParse();
	            for (TypedDependency dep : dependencyParse.typedDependencies()) {
	            	result = result +"{\"begin\" : " + sentence.charOffsets().first().intValue() + ",\"end\" : " + sentence.charOffsets().second().intValue() + ",\"SourceEntity\" : [{\"begin\" : " + dep.gov().beginPosition() + ",\"end\" : " + dep.gov().endPosition() + ",\"NamedEntity\" : \"" + dep.gov().ner() + "\"}]," + "\"TargetEntity\" : [{\"begin\" : " + dep.dep().beginPosition() + ",\"end\" : " + dep.dep().endPosition() + ",\"NamedEntity\" : \"" + dep.dep().ner() + "\"}], \"DependencyName\" : \"" + dep.reln().getLongName() + "\"},";
		        	hasResult = true;
	            }
	        }
	        
	        if(hasResult)
	        	result = result.substring(0,result.length()-1) + "],\n";
	        else
	        	result = result+"],\n";
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Dependency Parsing Task : "+e.getMessage());
		}
        return result;
	}
	
}

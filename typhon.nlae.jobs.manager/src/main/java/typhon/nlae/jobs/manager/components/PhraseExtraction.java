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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import typhon.nlae.jobs.manager.utils.Rake;

public class PhraseExtraction {
	// Declare RAKE NLP object
	private Rake rake;
	
	public PhraseExtraction() {
		try {
			rake = new Rake();
		} catch (Exception e) {
			System.out.println("Exception in initialization");
		}
	}
	
	public String getPhrase(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			result = "[";
			LinkedHashMap<String, Double> results = rake.getKeywordsFromText(input);
			Set<Map.Entry<String, Double>> keys = results.entrySet();
	        for(Map.Entry<String, Double> k: keys){
	        	String phrase = k.getKey();
	            result = result +"{\"begin\" : " + input.indexOf(phrase) + ",\"end\": " + input.lastIndexOf(phrase) + ",\"Token\": \"" + phrase + "\"},";
	            hasResult = true;
	        }
			
	        if(hasResult)
	        	result = result.substring(0,result.length()-1) + "],\n";
	        else
	        	result = result+"],\n";
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Phrase Extraction Task : "+e.getMessage());
		}
        return result;
	}
	
}

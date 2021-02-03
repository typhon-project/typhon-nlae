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

import org.apache.commons.lang.StringUtils;
import de.tudarmstadt.ukp.dkpro.core.ngrams.util.NGramStringIterable;

public class NgramExtraction {
	
	public NgramExtraction() {
	}
	
	public String getNgram(String input, String workflowName) {
		String result = "";
		int begin = 0;
		int end = 0;
		boolean hasResult = false;
		try {
			result = "[";
			String[] tokens = StringUtils.split(input);
			Iterable<String> bigram = new NGramStringIterable(tokens, 1, 2);
			Iterable<String> trigram = new NGramStringIterable(tokens, 1, 3);
			
			// Process Bigram results
			for(String s: bigram) {
				if(s.contains(" ")) {
					begin = input.indexOf(s);
					end = begin + s.length();
					result = result +"{\"begin\" : " + begin + ",\"end\": " + end + ",\"Token\": \"" + s + "\",\"NgramType\": \"" + "Bigram" + "\"},";
		        	hasResult = true;
				}
			}
			
			// Process Trigram results
			for(String s: trigram) {
				String[] splitTokens = s.split(" ");
				if(splitTokens.length == 3) {
					begin = input.indexOf(s);
					end = begin + s.length();
					result = result +"{\"begin\" : " + begin + ",\"end\": " + end + ",\"Token\": \"" + s + "\",\"NgramType\": \"" + "Trigram" + "\"},";
					hasResult = true;
				}
			}
			
	        if(hasResult)
	        	result = result.substring(0,result.length()-1) + "],\n";
	        else
	        	result = result+"],\n";
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing NGram Extraction Task : "+e.getMessage());
		}
        return result;
	}
	
}

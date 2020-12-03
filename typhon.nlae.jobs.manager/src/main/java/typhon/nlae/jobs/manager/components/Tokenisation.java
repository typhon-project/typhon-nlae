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

import java.io.BufferedReader;
import java.io.StringReader;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;

public class Tokenisation {

	public Tokenisation() {
	}
	
	public String getToken(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			PTBTokenizer<Word> ptb = PTBTokenizer.newPTBTokenizer(new BufferedReader(new StringReader(input)));
			result = "[";
	        
			while (ptb.hasNext()) {
				Word w = ptb.next();
	            result = result +"{\"begin\" : " + w.beginPosition() + ",\"end\": " + w.endPosition() + ",\"Token\": \"" + w.value() + "\"},";
	            hasResult = true;
	        }
	        if(hasResult)
	        	result = result.substring(0,result.length()-1) + "],\n";
	        else
	        	result = result+"],\n";
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Tokenisation Task : "+e.getMessage());
		}
        return result;
	}
	
}

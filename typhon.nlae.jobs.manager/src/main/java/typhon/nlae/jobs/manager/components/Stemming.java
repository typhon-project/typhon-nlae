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
import java.util.Iterator;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.process.PTBTokenizer;
import typhon.nlae.jobs.manager.utils.Stemmer;

public class Stemming {
	private Stemmer stem;
	
	public Stemming() {
		try {
			stem = new Stemmer();
			
		} catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}
	}
	
	public String getStem(String input, String workflowName) {
		String result = "";
		boolean hasResult = false;
		try {
			Stemmer stemmer = new Stemmer();
			result = "[";
	        
			Iterator<Word> it = PTBTokenizer.newPTBTokenizer(new BufferedReader(new StringReader(input)));
			while (it.hasNext()) {
				Word token = it.next();
	            String stem = stemmer.stem(token.word());
				result = result +"{\"begin\" : " + token.beginPosition() + ",\"end\": " + token.endPosition() + ",\"Stem\": \"" + stem + "\"},";
		        hasResult = true;
	        }
	        if(hasResult)
	        	result = result.substring(0,result.length()-1) + "],\n";
	        else
	        	result = result+"],\n";
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Named Entity Recognition Task : "+e.getMessage());
		}
        return result;
	}
	
}

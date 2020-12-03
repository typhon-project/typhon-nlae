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

package typhon.nlae.jobs.manager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlphaBankNER {

	public String result = "";
	public String entityType = "";
	public String regexPattern = "";
	public AlphaBankNER(String input) {
		String tmpResult = "";
		
		//AccountNumber
		entityType = "AccountNumber";
		String[] accIdentifiers = new String[] {"ΧΡΕΟΥΜΕΝΟΣ ΛΟΓ/ΣΜΟΣ", "Αριθμός λογαριασμού", "ΛΟΓ/ΣΜΟΥ", "ΛΟΓΑΡΙΑΣΜΟΥ", "Χρ.Λογαριασμός"};
		for (String accIdentifier: accIdentifiers) {
			regexPattern = accIdentifier + ": ([\\.\\d]+) ";
			tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
			regexPattern = accIdentifier + "[ :]([\\.\\d]+) ";
			tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
			regexPattern = accIdentifier + "([\\.\\d]+) ";
			tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
		}
		regexPattern = "[ :]([\\d]{15}) ";
		tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
		regexPattern = "[ :]([\\d]{3}.[\\d]{2}.[\\d]{4}.[\\d]{6}) ";
		tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
						
		
		//CreditAmount
		entityType = "CreditAmount";
		regexPattern = "(([1-9]\\d{0,2}(\\.\\d{3})*)(\\,\\d\\d)?)( *)Π[ $]";
		tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
		
		//CreditCard 
		entityType = "CreditCard";
		regexPattern = "ΑΡ\\.ΚΑΡΤΑΣ[ :]*(([\\d]+[-\\. ]?)*[\\d]+)";
		tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
		
		
		//CustomerNumber
		String[] identifiers = new String[] {"ΣΥΝΑΛΛΑΣΣΟΜΕΝΟΣ", "Επωνυμία", "ΠΕΛΑΤΗ", "ΠΕΛΑΤΗΣ", "Επωνυμία Καταθέτη", "Κάτοχος"};
		entityType = "CustomerName";
		for (String identifier: identifiers) {
			regexPattern = identifier + "[ :]*(([a-zA-Z]+[ -.])*[a-zA-Z]+)";
			tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
		}
		
		
		//CustomerName
		entityType = "CustomerNumber";
		for (String identifier: identifiers) {
			regexPattern = identifier + "[ :]*([\\d]{10}) ";
			tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
		}
		
		
		//DebitAmount 
		entityType = "DebitAmount";
		regexPattern = "(([1-9]\\d{0,2}(\\.\\d{3})*)(\\,\\d\\d)?)( *)Χ ";
		tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
						
		
		//TransactionAmount 
		entityType = "TransactionAmount";
		//Transaction Amount Pattern 1
		regexPattern = "ΠΟΣΟ *(([1-9]\\d{0,2}(\\.\\d{3})*)(\\,\\d\\d)?)";
		tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
		//Transaction Amount Pattern 2
		regexPattern = "Ποσό[ :]*(([1-9]\\d{0,2}(\\.\\d{3})*)(\\,\\d\\d)?)";
		tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
		
					
		//VALEUR
		regexPattern = "VALEUR[ :]*(\\d{2}\\/\\d{2}\\/\\d{4})";
		entityType = "VALEUR";
		tmpResult = tmpResult + getAlphaBankRegexNER(input, regexPattern, entityType);
				
		//Set Result Parameter
		this.result = tmpResult;
		
	}
	
	public String getResult() {
		return result;
	}

	
	public String getAlphaBankRegexNER(String input, String regexPattern, String entityType ) {
		
		String resultString = "";
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(input);
		int count = 0;
        while(matcher.find()) {
            count++;
            resultString = resultString +"{\"begin\" : " + matcher.start() + ",\"end\" : " + matcher.end() + ",\"NamedEntity\" : \"" 
            + entityType + "\",\"WordToken\" : \""+ matcher.group(count) + "\" },";
        }
		
		return resultString;
	}
}

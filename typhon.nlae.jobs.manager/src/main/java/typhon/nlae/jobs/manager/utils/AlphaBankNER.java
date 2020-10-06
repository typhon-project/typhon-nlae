package typhon.nlae.jobs.manager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This AlphaBankNER class provides custom Named-Entity Recognition functionality for Alpha Bank Use Case  
 * @author Yannis Korkontzelos
 * @author Raja Muhammad Suleman
 * @version 1.0
 */
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

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

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import au.com.bytecode.opencsv.CSVReader;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import typhon.nlae.jobs.manager.utils.AlphaBankNER;

public class NamedEntityRecognition {
	
	private StanfordCoreNLP pipeline;
	private static StanfordCoreNLP atbPipeline;
	
	private CoreDocument document;
	private Map<String,String> geocodes = new HashMap<String, String>();
	public NamedEntityRecognition() {
		try {
			//Default NER pipeline
			Properties props = new Properties();
	        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
	        pipeline = new StanfordCoreNLP(props);	
	        
	        atbPipeline = null;
	        
	        CSVReader reader = new CSVReader(new FileReader("/opt/flink/plugins/DE.txt"), '\t');
	        
	        String[] record;
	        
	        while ((record = reader.readNext()) != null) {
	        	geocodes.put(record[1], record[2]+","+record[3]);
	        }
	        
	   } catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}
	}
	
	public String getNer(String input, String workflowName) {
		String result = "";
		result = "[";
		
		try {
			document = new CoreDocument(input);
	        
			//ATB Custom Weather NER Annotator
			if(workflowName.equalsIgnoreCase("atb_weather_ner")) {
				//ATB Weather NER model
				String modelName = "/opt/flink/plugins/ATB-ner-model-de.ser.gz";
				if(atbPipeline == null) {
		        	Properties propsatb = new Properties();
			        propsatb.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
					propsatb.setProperty("ner.model", modelName);
			        atbPipeline = new StanfordCoreNLP(propsatb);
			    }
		        atbPipeline.annotate(document);
		    }
			else if(workflowName.equalsIgnoreCase("alpha_bank_ner")) { //Alhpa Bank Custom Annotator
				AlphaBankNER alphaNER = new AlphaBankNER(input);
				result = result + alphaNER.getResult();
			}
			else {//Default NER Tagger
				pipeline.annotate(document);
			}
			
			String lastVal = "";
	        String lastTag = "";
	        int lastBegin = 0;
	        int lastEnd = 0;
			
	        //Results for Default and ATB Classifiers
			if(!workflowName.equalsIgnoreCase("alpha_bank_ner")) {
				if(workflowName.equalsIgnoreCase("atb_weather_ner")) {
					for (CoreEntityMention em : document.entityMentions()) {
						if(em.entityType().equalsIgnoreCase("LOCATION") && geocodes.containsKey(em.text())) {
							result = result +"{\"begin\" : " + em.charOffsets().first + ",\"end\" : " + em.charOffsets().second + ",\"NamedEntity\" : \"" + em.entityType() + "\",\"WordToken\" : \""+ em.text() + "\",\"GeoCode\" : \"" + geocodes.get(em.text()) + "\" },";
						}
						else {//Handling Date Format	
							if(em.entityType().equalsIgnoreCase("DATE")) {
								if(lastTag.equalsIgnoreCase("")) {
									lastTag = "DATE";
									lastVal = em.text();
									lastBegin = em.charOffsets().first;
									lastEnd = em.charOffsets().second;
								}
								else {
									if(lastTag.equalsIgnoreCase("DATE")) {
										result = result +"{\"begin\" : " + lastBegin + ",\"end\" : " + em.charOffsets().second + ",\"NamedEntity\" : \"" + em.entityType() + "\",\"WordToken\" : \""+ lastVal + " " + em.text() + "\",\"GeoCode\" : \"0.00,0.00\" },";
										lastTag = "";
										lastVal = "";
									}
								}
	
							}
							else {
								if(lastTag.equalsIgnoreCase("DATE")) {
									result = result +"{\"begin\" : " + lastBegin + ",\"end\" : " + lastEnd + ",\"NamedEntity\" : \"" + lastTag + "\",\"WordToken\" : \""+ lastVal + "\",\"GeoCode\" : \"0.00,0.00\" },";
									lastTag = "";
									lastVal = "";
								}
								result = result +"{\"begin\" : " + em.charOffsets().first + ",\"end\" : " + em.charOffsets().second + ",\"NamedEntity\" : \"" + em.entityType() + "\",\"WordToken\" : \""+ em.text() + "\",\"GeoCode\" : \"0.00,0.00\" },";
							}
	
						}
					}
				}
				else {
					for (CoreEntityMention em : document.entityMentions()) {
				    		result = result +"{\"begin\" : " + em.charOffsets().first + ",\"end\" : " + em.charOffsets().second + ",\"NamedEntity\" : \"" + em.entityType() + "\",\"WordToken\" : \""+ em.text() + "\" },";
					}
				}
			}


			if(result.length()>10)
				result = result.substring(0,result.length()-1) + "],\n";
		        else
		        	result = result+"],\n";
			
		}catch(Exception e) {
			System.out.println("Excpetion occurred while performing Named Entity Recognition Task : "+e.getMessage());
		}
        return result;
	}

}

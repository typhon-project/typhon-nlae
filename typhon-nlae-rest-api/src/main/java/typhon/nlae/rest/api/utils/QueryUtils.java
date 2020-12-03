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

package typhon.nlae.rest.api.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import typhon.nlae.rest.api.models.Anaphor;
import typhon.nlae.rest.api.models.Antecedent;
import typhon.nlae.rest.api.models.Chunking;
import typhon.nlae.rest.api.models.CoreferenceResolution;
import typhon.nlae.rest.api.models.DependencyParsing;
import typhon.nlae.rest.api.models.Lemmatisation;
import typhon.nlae.rest.api.models.NGramExtraction;
import typhon.nlae.rest.api.models.NamedEntityRecognition;
import typhon.nlae.rest.api.models.POSTagging;
import typhon.nlae.rest.api.models.ParagraphSegmentation;
import typhon.nlae.rest.api.models.PhraseExtraction;
import typhon.nlae.rest.api.models.PosAnnotation;
import typhon.nlae.rest.api.models.Query;
import typhon.nlae.rest.api.models.Record;
import typhon.nlae.rest.api.models.RelationExtraction;
import typhon.nlae.rest.api.models.SentenceSegmentation;
import typhon.nlae.rest.api.models.SourceEntity;
import typhon.nlae.rest.api.models.Stemming;
import typhon.nlae.rest.api.models.TargetEntity;
import typhon.nlae.rest.api.models.TermExtraction;
import typhon.nlae.rest.api.models.TokenAnnotation;
import typhon.nlae.rest.api.models.Tokenisation;

public class QueryUtils {

	public static List<String> parseNlpExpression(String jsonString) throws JsonMappingException, JsonProcessingException {
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		Query query = jsonMapper.readValue(jsonString, Query.class);
	    
		String queryString = "";
		String entityType = "";
		String alias = "";
		List<String> selectList;
		List<String> resultString = new ArrayList<String>();
		entityType = query.getFrom().getEntity().toLowerCase();
		resultString.add(entityType);
		alias = query.getFrom().getNamed();
		
		queryString = queryString+"SELECT ";
		selectList = query.getSelect();
		if(selectList.size() == 1 && selectList.get(0) == "*") {
        	queryString = queryString + "*";
        }
        else {
        	for (int i = 0; i < selectList.size(); i++){
        		String selectItem = selectList.get(i).toString();
            	queryString = queryString + selectItem.replace("@", "") + ", ";
            	resultString.add(selectItem.replace("@", ""));
            }
        	queryString = queryString.substring(0,queryString.length()-2);
        }
	    
		queryString = queryString + " ";
        queryString = queryString +  "FROM " + entityType.toLowerCase() + " AS " + alias + " ";
		
        queryString = queryString+"WHERE ";
        
        query.getWhere();
        String whereClauseJson;
        String whereClauseString = "";
		try {
			
			whereClauseJson = query.getWhere().toString();
			final JsonNode rootNode = jsonMapper.readTree(whereClauseJson);
			Stack<String> queryStack = new Stack<String>();
		    parseNestedJson(rootNode,queryStack);
		    String value = "";
		    List<String> valList = new ArrayList<String>();
		    List<String> pathList = new ArrayList<String>();
		    List<String> opList = new ArrayList<String>();
		    
		    String whereCondition = "(";
		    String qOperator = "";
		    while(!queryStack.isEmpty()) {
		    	qOperator = "";
		    	value = queryStack.pop();
		    	String[] explodedValue = value.split(":");
		    	if(explodedValue[0].equalsIgnoreCase("value"))
		    		valList.add(explodedValue[1]);
		    	else if(explodedValue[0].equalsIgnoreCase("path")){
		    		String path = explodedValue[1];
		    		pathList.add(path);
		    	}
		    	else if(explodedValue[0].equalsIgnoreCase("op")) {
		    		if(explodedValue[1].equals("&&") || explodedValue[1].equals("||")) {
		    			if(explodedValue[1].equals("&&"))
		    				qOperator = "AND";
		    			if(explodedValue[1].equals("||"))
		    				qOperator = "OR";	
		    			if(valList.size()>1) {
		    				for(int i = valList.size()-1;i>=0;i--) {
		    					whereCondition = whereCondition + pathList.get(i) + " " + opList.get(i) + " " + valList.get(i);
		    					if(i != 0)
		    						whereCondition = whereCondition + " " + qOperator + " ";
		    					else
		    						whereCondition = whereCondition + " ";
		    				}
		    				whereCondition = whereCondition + ")";
		    				whereClauseString = whereCondition + whereClauseString;
		    			}
		    			else {
		    				if(whereClauseString.isEmpty())
		    					whereClauseString = qOperator + "("+ pathList.get(0) + " " + opList.get(0) + " " + valList.get(0)+ ")";
		    				else if(whereClauseString.startsWith("AND") || whereClauseString.startsWith("OR")) {
		    					whereCondition = whereCondition + pathList.get(0) + " " + opList.get(0) + " " + valList.get(0);
		    					whereClauseString = qOperator + " " + whereCondition + whereClauseString + ")";
		    				}
		    				else {
		    					whereCondition = whereCondition + pathList.get(0) + " " + opList.get(0) + " " + valList.get(0);
		    					whereClauseString = whereCondition + " " + qOperator  + " " + whereClauseString + ")";
		    				}
		    			}
		    			valList.clear();
						pathList.clear();
						opList.clear();
						whereCondition = "(";
		    		}
		    		else if(explodedValue[1].equals("-")) {
		    			String tmpValue = valList.get(valList.size()-1);
		    			tmpValue = "-" + tmpValue;
		    			valList.set(valList.size()-1, tmpValue);
		    		}
		    		else
		    			opList.add(explodedValue[1]);
		    	}
		    }
		    if(opList.size()>0) {
		    	whereClauseString = "("+ pathList.get(0) + " " + opList.get(0) + " " + valList.get(0)+ ")";
		    }
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error: "+e.getMessage());
			
		}
		
		queryString = queryString + whereClauseString;
		queryString = "{\"query\": \""+ queryString + "\"}";
        resultString.add(queryString);
        
		return resultString;
	}
	
	private static void parseNestedJson(final JsonNode node, Stack<String> qStack) throws IOException {
	    Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();
	    
	    while (fieldsIterator.hasNext()) {
	        Map.Entry<String, JsonNode> field = fieldsIterator.next();
	        final String key = field.getKey();
	        final JsonNode value = field.getValue();
	        if (value.isContainerNode()) {
	            parseNestedJson(value, qStack); // RECURSIVE CALL
	        } else {
	        	if(!key.equalsIgnoreCase("type"))
	        		qStack.push(key+":"+value.asText());
	        }
	    }
	}
	
	public static String generateDSLQuery(RestHighLevelClient esClient, String jsonQuery) {
		String dslQuery = "";
		JSONObject jsonObj = null;
		Request request = new Request("POST", "/_xpack/sql/translate"); // For Elasticsearhc V 7.x use "/_sql/translate"
		request.setJsonEntity(jsonQuery);
		Response response;
		try {
			response = esClient.getLowLevelClient().performRequest(request);
			jsonObj = new JSONObject(EntityUtils.toString(response.getEntity()));
			dslQuery = jsonObj.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return dslQuery;
	}

	public static List<String> compileResult(String record, List<String> headerValueList, List<String> headerSequence) {
		// TODO Auto-generated method stub
		List<String> result = new ArrayList<String>();
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		Record recordJ = null;
		for(String headertag : headerValueList) {
			try {
				JsonNode rootNode = mapper.readTree(record);  
				Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
				while (fieldsIterator.hasNext()) {
					Map.Entry<String,JsonNode> field = fieldsIterator.next();
					if(field.getKey().equals("id")) {
						if(headertag.equalsIgnoreCase("id")) {
							result.add(field.getValue().asText());
						}
					}
					else {
						recordJ = mapper.readValue(field.getValue().toString(), Record.class);
						if(headertag.equalsIgnoreCase("SentimentAnalysis")) {
							result.add(recordJ.getSentimentAnalysis().get(0).getSentiment());
							result.add(recordJ.getSentimentAnalysis().get(0).getSentimentLabel());
						}
						else if(headertag.equalsIgnoreCase("NamedEntityRecognition")) {
							String namedEntities = "";
							String beginStr = "";
							String endStr = "";
							String wordTokens = "";
							String geoCodes = "";
							List<NamedEntityRecognition> nerValues = recordJ.getNamedEntityRecognition();
							if(null != nerValues) {
								for(int i=0;i<nerValues.size();i++) {
									if(null != nerValues.get(i).getNamedEntity())
										namedEntities = namedEntities + nerValues.get(i).getNamedEntity() + ",";
								}
								
								for(int i=0;i<nerValues.size();i++) {
									if(null != nerValues.get(i).getWordToken())
										wordTokens = wordTokens + "\"" + nerValues.get(i).getWordToken() + "\",";
								}
								
								for(int i=0;i<nerValues.size();i++) {
									if(null != nerValues.get(i).getGeoCode())
										geoCodes = geoCodes + "\"" + nerValues.get(i).getGeoCode() + "\",";
								}
								
								for(int i=0;i<nerValues.size();i++) {
									if(null != nerValues.get(i).getBegin()) {
										beginStr = beginStr + nerValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<nerValues.size();i++) {
									if(null != nerValues.get(i).getEnd())
										endStr = endStr + nerValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence) {
									if(tokenTag.equalsIgnoreCase("NamedEntityRecognition.NamedEntity") && !namedEntities.equals(""))
										result.add("{" + namedEntities.substring(0,namedEntities.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("NamedEntityRecognition.WordToken") && !wordTokens.equals(""))
										result.add("{" + wordTokens.substring(0,wordTokens.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("NamedEntityRecognition.GeoCode") && !geoCodes.equals(""))
										result.add("{" + geoCodes.substring(0,geoCodes.length()-1) + "}");
							
									if(tokenTag.equalsIgnoreCase("NamedEntityRecognition.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("NamedEntityRecognition.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("Tokenisation")) {
							String tokens = "";
							String beginStr = "";
							String endStr = "";
							List<Tokenisation> tokenValues = recordJ.getTokenisation();
							if(null != tokenValues) {
								for(int i=0;i<tokenValues.size();i++) {
									if(null != tokenValues.get(i).getToken())
										tokens = tokens + tokenValues.get(i).getToken() + ",";
								}
								
								for(int i=0;i<tokenValues.size();i++) {
									if(null != tokenValues.get(i).getBegin()) {
										beginStr = beginStr + tokenValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<tokenValues.size();i++) {
									if(null != tokenValues.get(i).getEnd())
										endStr = endStr + tokenValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("Tokenisation.Token") && !tokens.equals(""))
										result.add("{" + tokens.substring(0,tokens.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Tokenisation.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("Tokenisation.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("ParagraphSegmentation")) {
							String paragraphs = "";
							String beginStr = "";
							String endStr = "";
							List<ParagraphSegmentation> paragraphValues = recordJ.getParagraphSegmentation();
							if(null != paragraphValues) {
								for(int i=0;i<paragraphValues.size();i++) {
									if(null != paragraphValues.get(i).getParagraph())
										paragraphs = paragraphs + paragraphValues.get(i).getParagraph() + ",";
								}
								
								for(int i=0;i<paragraphValues.size();i++) {
									if(null != paragraphValues.get(i).getBegin()) {
										beginStr = beginStr + paragraphValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<paragraphValues.size();i++) {
									if(null != paragraphValues.get(i).getEnd())
										endStr = endStr + paragraphValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("ParagraphSegmentation.Paragraph") && !paragraphs.equals(""))
										result.add("{" + paragraphs.substring(0,paragraphs.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("ParagraphSegmentation.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("ParagraphSegmentation.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("SentenceSegmentation")) {
							String sentences = "";
							String beginStr = "";
							String endStr = "";
							List<SentenceSegmentation> sentenceValues = recordJ.getSentenceSegmentation();
							if(null != sentenceValues) {
								for(int i=0;i<sentenceValues.size();i++) {
									if(null != sentenceValues.get(i).getSentence())
										sentences = sentences + sentenceValues.get(i).getSentence() + ",";
								}
								
								for(int i=0;i<sentenceValues.size();i++) {
									if(null != sentenceValues.get(i).getBegin()) {
										beginStr = beginStr + sentenceValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<sentenceValues.size();i++) {
									if(null != sentenceValues.get(i).getEnd())
										endStr = endStr + sentenceValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("SentenceSegmentation.Sentence") && !sentences.equals(""))
										result.add("{" + sentences.substring(0,sentences.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("SentenceSegmentation.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("SentenceSegmentation.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("PhraseExtraction")) {
							String phrases = "";
							String beginStr = "";
							String endStr = "";
							List<PhraseExtraction> phraseValues = recordJ.getPhraseExtraction();
							if(null != phraseValues) {
								for(int i=0;i<phraseValues.size();i++) {
									if(null != phraseValues.get(i).getToken())
										phrases = phrases + phraseValues.get(i).getToken() + ",";
								}
								
								for(int i=0;i<phraseValues.size();i++) {
									if(null != phraseValues.get(i).getBegin()) {
										beginStr = beginStr + phraseValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<phraseValues.size();i++) {
									if(null != phraseValues.get(i).getEnd())
										endStr = endStr + phraseValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("PhraseExtraction.Token") && !phrases.equals(""))
										result.add("{" + phrases.substring(0,phrases.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("PhraseExtraction.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("PhraseExtraction.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("NGramExtraction")) {
							String nGrams = "";
							String tokens = "";
							String beginStr = "";
							String endStr = "";
							List<NGramExtraction> nGramValues = recordJ.getNGramExtraction();
							if(null != nGramValues) {
								for(int i=0;i<nGramValues.size();i++) {
									if(null != nGramValues.get(i).getNgramType())
										nGrams = nGrams + nGramValues.get(i).getNgramType() + ",";
								}
								
								for(int i=0;i<nGramValues.size();i++) {
									if(null != nGramValues.get(i).getToken())
										tokens = tokens + nGramValues.get(i).getToken() + ",";
								}
								
								for(int i=0;i<nGramValues.size();i++) {
									if(null != nGramValues.get(i).getBegin()) {
										beginStr = beginStr + nGramValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<nGramValues.size();i++) {
									if(null != nGramValues.get(i).getEnd())
										endStr = endStr + nGramValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("nGramExtraction.NgramType") && !nGrams.equals(""))
										result.add("{" + nGrams.substring(0,nGrams.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("nGramExtraction.Token") && !tokens.equals(""))
										result.add("{" + tokens.substring(0,tokens.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("nGramExtraction.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("nGramExtraction.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("POSTagging")) {
							String posValues = "";
							String posTags = "";
							String beginStr = "";
							String endStr = "";
							List<POSTagging> posTaggingValues = recordJ.getPOSTagging();
							if(null != posTaggingValues) {
								for(int i=0;i<posTaggingValues.size();i++) {
									if(null != posTaggingValues.get(i).getPosValue())
										posValues = posValues + posTaggingValues.get(i).getPosValue() + ",";
								}
								
								for(int i=0;i<posTaggingValues.size();i++) {
									if(null != posTaggingValues.get(i).getPosTag())
										posTags = posTags + posTaggingValues.get(i).getPosTag() + ",";
								}
								
								for(int i=0;i<posTaggingValues.size();i++) {
									if(null != posTaggingValues.get(i).getBegin()) {
										beginStr = beginStr + posTaggingValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<posTaggingValues.size();i++) {
									if(null != posTaggingValues.get(i).getEnd())
										endStr = endStr + posTaggingValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("POSTagging.PosValue") && !posValues.equals(""))
										result.add("{" + posValues.substring(0,posValues.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("POSTagging.PosTag") && !posTags.equals(""))
										result.add("{" + posTags.substring(0,posTags.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("POSTagging.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("POSTagging.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("Lemmatisation")) {
							String lemmas = "";
							String beginStr = "";
							String endStr = "";
							List<Lemmatisation> lemmaValues = recordJ.getLemmatisation();
							if(null != lemmaValues) {
								for(int i=0;i<lemmaValues.size();i++) {
									if(null != lemmaValues.get(i).getToken())
										lemmas = lemmas + lemmaValues.get(i).getToken() + ",";
								}
								
								for(int i=0;i<lemmaValues.size();i++) {
									if(null != lemmaValues.get(i).getBegin()) {
										beginStr = beginStr + lemmaValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<lemmaValues.size();i++) {
									if(null != lemmaValues.get(i).getEnd())
										endStr = endStr + lemmaValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("Lemmatisation.Token") && !lemmas.equals(""))
										result.add("{" + lemmas.substring(0,lemmas.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Lemmatisation.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("Lemmatisation.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("Stemming")) {
							String stems = "";
							String beginStr = "";
							String endStr = "";
							List<Stemming> stemmingValues = recordJ.getStemming();
							if(null != stemmingValues) {
								for(int i=0;i<stemmingValues.size();i++) {
									if(null != stemmingValues.get(i).getStem())
										stems = stems + stemmingValues.get(i).getStem() + ",";
								}
								
								for(int i=0;i<stemmingValues.size();i++) {
									if(null != stemmingValues.get(i).getBegin()) {
										beginStr = beginStr + stemmingValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<stemmingValues.size();i++) {
									if(null != stemmingValues.get(i).getEnd())
										endStr = endStr + stemmingValues.get(i).getEnd() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("Stemming.Stem") && !stems.equals(""))
										result.add("{" + stems.substring(0,stems.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Stemming.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("Stemming.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("DependencyParsing")) {
							String sourceNamedEntities = "";
							String targetNamedEntities = "";
							String sBeginStr = "";
							String sEndStr = "";
							String tBeginStr = "";
							String tEndStr = "";
							String dependencyNames = "";
							List<SourceEntity> sEntities = new ArrayList<SourceEntity>();
							List<TargetEntity> tEntities = new ArrayList<TargetEntity>();
							String beginStr = "";
							String endStr = "";
							List<DependencyParsing> dependencyParsingValues = recordJ.getDependencyParsing();
							if(null != dependencyParsingValues) {
								for(int i=0;i<dependencyParsingValues.size();i++) {
									if(null != dependencyParsingValues.get(i).getSourceEntity())
										sEntities = dependencyParsingValues.get(i).getSourceEntity();
										for(int k =0;k<sEntities.size();k++) {
											if(null != sEntities.get(k).getNamedEntity())
												sourceNamedEntities = sourceNamedEntities + sEntities.get(k).getNamedEntity() + ",";
										}
										
										for(int k =0;k<sEntities.size();k++) {
											if(null != sEntities.get(k).getBegin())
												sBeginStr = sBeginStr + sEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<sEntities.size();k++) {
											if(null != sEntities.get(k).getEnd())
												sEndStr = sEndStr + sEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<dependencyParsingValues.size();i++) {
									if(null != dependencyParsingValues.get(i).getTargetEntity())
										tEntities = dependencyParsingValues.get(i).getTargetEntity();
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getNamedEntity())
												targetNamedEntities = targetNamedEntities + tEntities.get(k).getNamedEntity() + ",";
										}
										
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getBegin())
												tBeginStr = tBeginStr + tEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getEnd())
												tEndStr = tEndStr + tEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<dependencyParsingValues.size();i++) {
									if(null != dependencyParsingValues.get(i).getBegin()) {
										beginStr = beginStr + dependencyParsingValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<dependencyParsingValues.size();i++) {
									if(null != dependencyParsingValues.get(i).getEnd())
										endStr = endStr + dependencyParsingValues.get(i).getEnd() + ",";
								}
								
								for(int i=0;i<dependencyParsingValues.size();i++) {
									if(null != dependencyParsingValues.get(i).getDependencyName())
										dependencyNames = dependencyNames + dependencyParsingValues.get(i).getDependencyName() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("DependencyParsing.SourceEntity.NamedEntity") && !sourceNamedEntities.equals(""))
										result.add("{" + sourceNamedEntities.substring(0,sourceNamedEntities.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("DependencyParsing.SourceEntity.begin") && !sBeginStr.equals(""))
										result.add("{" + sBeginStr.substring(0,sBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("DependencyParsing.SourceEntity.end") && !sEndStr.equals(""))
										result.add("{" + sEndStr.substring(0,sEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("DependencyParsing.TargetEntity.NamedEntity") && !targetNamedEntities.equals(""))
										result.add("{" + targetNamedEntities.substring(0,targetNamedEntities.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("DependencyParsing.TargetEntity.begin") && !tBeginStr.equals(""))
										result.add("{" + tBeginStr.substring(0,tBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("DependencyParsing.TargetEntity.end") && !tEndStr.equals(""))
										result.add("{" + tEndStr.substring(0,tEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("DependencyParsing.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("DependencyParsing.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("DependencyParsing.DependencyName") && !dependencyNames.equals(""))
										result.add("{" + dependencyNames.substring(0,dependencyNames.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("TermExtraction")) {
							String targetNamedEntities = "";
							String tBeginStr = "";
							String tEndStr = "";
							String weightedTokens = "";
							List<TargetEntity> tEntities = new ArrayList<TargetEntity>();
							String beginStr = "";
							String endStr = "";
							List<TermExtraction> termExtractionValues = recordJ.getTermExtraction();
							if(null != termExtractionValues) {
								for(int i=0;i<termExtractionValues.size();i++) {
									if(null != termExtractionValues.get(i).getTargetEntity())
										tEntities = termExtractionValues.get(i).getTargetEntity();
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getNamedEntity())
												targetNamedEntities = targetNamedEntities + tEntities.get(k).getNamedEntity() + ",";
										}
										
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getBegin())
												tBeginStr = tBeginStr + tEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getEnd())
												tEndStr = tEndStr + tEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<termExtractionValues.size();i++) {
									if(null != termExtractionValues.get(i).getBegin()) {
										beginStr = beginStr + termExtractionValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<termExtractionValues.size();i++) {
									if(null != termExtractionValues.get(i).getEnd())
										endStr = endStr + termExtractionValues.get(i).getEnd() + ",";
								}
								
								for(int i=0;i<termExtractionValues.size();i++) {
									if(null != termExtractionValues.get(i).getWeightedToken())
										weightedTokens = weightedTokens + termExtractionValues.get(i).getWeightedToken() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									
									if(tokenTag.equalsIgnoreCase("TermExtraction.TargetEntity.NamedEntity") && !targetNamedEntities.equals(""))
										result.add("{" + targetNamedEntities.substring(0,targetNamedEntities.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("TermExtraction.TargetEntity.begin") && !tBeginStr.equals(""))
										result.add("{" + tBeginStr.substring(0,tBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("TermExtraction.TargetEntity.end") && !tEndStr.equals(""))
										result.add("{" + tEndStr.substring(0,tEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("TermExtraction.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("TermExtraction.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("TermExtraction.WeightedToken") && !weightedTokens.equals(""))
										result.add("{" + weightedTokens.substring(0,weightedTokens.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("RelationExtraction")) {
							String sourceNamedEntities = "";
							String targetNamedEntities = "";
							String sBeginStr = "";
							String sEndStr = "";
							String tBeginStr = "";
							String tEndStr = "";
							String relationNames = "";
							List<SourceEntity> sEntities = new ArrayList<SourceEntity>();
							List<TargetEntity> tEntities = new ArrayList<TargetEntity>();
							String beginStr = "";
							String endStr = "";
							List<RelationExtraction> relationExtractionValues = recordJ.getRelationExtraction();
							if(null != relationExtractionValues) {
								for(int i=0;i<relationExtractionValues.size();i++) {
									if(null != relationExtractionValues.get(i).getSourceEntity())
										sEntities = relationExtractionValues.get(i).getSourceEntity();
										for(int k =0;k<sEntities.size();k++) {
											if(null != sEntities.get(k).getNamedEntity())
												sourceNamedEntities = sourceNamedEntities + sEntities.get(k).getNamedEntity() + ",";
										}
										
										for(int k =0;k<sEntities.size();k++) {
											if(null != sEntities.get(k).getBegin())
												sBeginStr = sBeginStr + sEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<sEntities.size();k++) {
											if(null != sEntities.get(k).getEnd())
												sEndStr = sEndStr + sEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<relationExtractionValues.size();i++) {
									if(null != relationExtractionValues.get(i).getTargetEntity())
										tEntities = relationExtractionValues.get(i).getTargetEntity();
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getNamedEntity())
												targetNamedEntities = targetNamedEntities + tEntities.get(k).getNamedEntity() + ",";
										}
										
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getBegin())
												tBeginStr = tBeginStr + tEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<tEntities.size();k++) {
											if(null != tEntities.get(k).getEnd())
												tEndStr = tEndStr + tEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<relationExtractionValues.size();i++) {
									if(null != relationExtractionValues.get(i).getBegin()) {
										beginStr = beginStr + relationExtractionValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<relationExtractionValues.size();i++) {
									if(null != relationExtractionValues.get(i).getEnd())
										endStr = endStr + relationExtractionValues.get(i).getEnd() + ",";
								}
								
								for(int i=0;i<relationExtractionValues.size();i++) {
									if(null != relationExtractionValues.get(i).getRelationName())
										relationNames = relationNames + relationExtractionValues.get(i).getRelationName() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("RelationExtraction.SourceEntity.NamedEntity") && !sourceNamedEntities.equals(""))
										result.add("{" + sourceNamedEntities.substring(0,sourceNamedEntities.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("RelationExtraction.SourceEntity.begin") && !sBeginStr.equals(""))
										result.add("{" + sBeginStr.substring(0,sBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("RelationExtraction.SourceEntity.end") && !sEndStr.equals(""))
										result.add("{" + sEndStr.substring(0,sEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("RelationExtraction.TargetEntity.NamedEntity") && !targetNamedEntities.equals(""))
										result.add("{" + targetNamedEntities.substring(0,targetNamedEntities.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("RelationExtraction.TargetEntity.begin") && !tBeginStr.equals(""))
										result.add("{" + tBeginStr.substring(0,tBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("RelationExtraction.TargetEntity.end") && !tEndStr.equals(""))
										result.add("{" + tEndStr.substring(0,tEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("RelationExtraction.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("RelationExtraction.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("RelationExtraction.RelationName") && !relationNames.equals(""))
										result.add("{" + relationNames.substring(0,relationNames.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("CoreferenceResolution")) {
							String antecedentTokens = "";
							String anaphorTokens = "";
							String sBeginStr = "";
							String sEndStr = "";
							String tBeginStr = "";
							String tEndStr = "";
							List<Antecedent> antecedentEntities = new ArrayList<Antecedent>();
							List<Anaphor> anaphorEntities = new ArrayList<Anaphor>();
							String beginStr = "";
							String endStr = "";
							List<CoreferenceResolution> corefValues = recordJ.getCoreferenceResolution();
							if(null != corefValues) {
								for(int i=0;i<corefValues.size();i++) {
									if(null != corefValues.get(i).getAntecedent())
										antecedentEntities = corefValues.get(i).getAntecedent();
										for(int k =0;k<antecedentEntities.size();k++) {
											if(null != antecedentEntities.get(k).getToken())
												antecedentTokens = antecedentTokens + antecedentEntities.get(k).getToken() + ",";
										}
										
										for(int k =0;k<antecedentEntities.size();k++) {
											if(null != antecedentEntities.get(k).getBegin())
												sBeginStr = sBeginStr + antecedentEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<antecedentEntities.size();k++) {
											if(null != antecedentEntities.get(k).getEnd())
												sEndStr = sEndStr + antecedentEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<corefValues.size();i++) {
									if(null != corefValues.get(i).getAnaphor())
										anaphorEntities = corefValues.get(i).getAnaphor();
										for(int k =0;k<anaphorEntities.size();k++) {
											if(null != anaphorEntities.get(k).getToken())
												anaphorTokens = anaphorTokens + anaphorEntities.get(k).getToken() + ",";
										}
										
										for(int k =0;k<anaphorEntities.size();k++) {
											if(null != anaphorEntities.get(k).getBegin())
												tBeginStr = tBeginStr + anaphorEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<anaphorEntities.size();k++) {
											if(null != anaphorEntities.get(k).getEnd())
												tEndStr = tEndStr + anaphorEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<corefValues.size();i++) {
									if(null != corefValues.get(i).getBegin()) {
										beginStr = beginStr + corefValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<corefValues.size();i++) {
									if(null != corefValues.get(i).getEnd())
										endStr = endStr + corefValues.get(i).getEnd() + ",";
								}
								
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("CoreferenceResolution.Antecedent.Token") && !antecedentTokens.equals(""))
										result.add("{" + antecedentTokens.substring(0,antecedentTokens.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("CoreferenceResolution.Antecedent.begin") && !sBeginStr.equals(""))
										result.add("{" + sBeginStr.substring(0,sBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("CoreferenceResolution.Antecedent.end") && !sEndStr.equals(""))
										result.add("{" + sEndStr.substring(0,sEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("CoreferenceResolution.Anaphor.Token") && !anaphorTokens.equals(""))
										result.add("{" + anaphorTokens.substring(0,anaphorTokens.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("CoreferenceResolution.Anaphor.begin") && !tBeginStr.equals(""))
										result.add("{" + tBeginStr.substring(0,tBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("CoreferenceResolution.Anaphor.end") && !tEndStr.equals(""))
										result.add("{" + tEndStr.substring(0,tEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("CoreferenceResolution.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("CoreferenceResolution.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
								}
							}
						}
						else if(headertag.equalsIgnoreCase("Chunking")) {
							String tokenAnnotationTokens = "";
							String posAnnotationPosValues = "";
							String posAnnotationPosTags = "";
							String taBeginStr = "";
							String taEndStr = "";
							String paBeginStr = "";
							String paEndStr = "";
							String labels = "";
							List<TokenAnnotation> tokenAnnotationEntities = new ArrayList<TokenAnnotation>();
							List<PosAnnotation> posAnnotationEntities = new ArrayList<PosAnnotation>();
							String beginStr = "";
							String endStr = "";
							List<Chunking> chunkingValues = recordJ.getChunking();
							if(null != chunkingValues) {
								for(int i=0;i<chunkingValues.size();i++) {
									if(null != chunkingValues.get(i).getTokenAnnotation())
										tokenAnnotationEntities = chunkingValues.get(i).getTokenAnnotation();
										for(int k =0;k<tokenAnnotationEntities.size();k++) {
											if(null != tokenAnnotationEntities.get(k).getToken())
												tokenAnnotationTokens = tokenAnnotationTokens + tokenAnnotationEntities.get(k).getToken() + ",";
										}
										
										for(int k =0;k<tokenAnnotationEntities.size();k++) {
											if(null != tokenAnnotationEntities.get(k).getBegin())
												taBeginStr = taBeginStr + tokenAnnotationEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<tokenAnnotationEntities.size();k++) {
											if(null != tokenAnnotationEntities.get(k).getEnd())
												taEndStr = taEndStr + tokenAnnotationEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<chunkingValues.size();i++) {
									if(null != chunkingValues.get(i).getPosAnnotation())
										posAnnotationEntities = chunkingValues.get(i).getPosAnnotation();
										for(int k =0;k<posAnnotationEntities.size();k++) {
											if(null != posAnnotationEntities.get(k).getPosValue())
												posAnnotationPosValues = posAnnotationPosValues + posAnnotationEntities.get(k).getPosValue() + ",";
										}
										
										for(int k =0;k<posAnnotationEntities.size();k++) {
											if(null != posAnnotationEntities.get(k).getPosTag())
												posAnnotationPosTags = posAnnotationPosTags + posAnnotationEntities.get(k).getPosTag() + ",";
										}
										
										for(int k =0;k<posAnnotationEntities.size();k++) {
											if(null != posAnnotationEntities.get(k).getBegin())
												paBeginStr = paBeginStr + posAnnotationEntities.get(k).getBegin() + ",";
										}
										
										for(int k =0;k<posAnnotationEntities.size();k++) {
											if(null != posAnnotationEntities.get(k).getEnd())
												paEndStr = paEndStr + posAnnotationEntities.get(k).getEnd() + ",";
										}
										
								}
								
								for(int i=0;i<chunkingValues.size();i++) {
									if(null != chunkingValues.get(i).getBegin()) {
										beginStr = beginStr + chunkingValues.get(i).getBegin() + ",";
									}
								}
								
								for(int i=0;i<chunkingValues.size();i++) {
									if(null != chunkingValues.get(i).getEnd())
										endStr = endStr + chunkingValues.get(i).getEnd() + ",";
								}
								
								for(int i=0;i<chunkingValues.size();i++) {
									if(null != chunkingValues.get(i).getLabel())
										labels = labels + chunkingValues.get(i).getLabel() + ",";
								}
								
								for(String tokenTag : headerSequence ) {
									if(tokenTag.equalsIgnoreCase("Chunking.TokenAnnotation.Token") && !tokenAnnotationTokens.equals(""))
										result.add("{" + tokenAnnotationTokens.substring(0,tokenAnnotationTokens.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Chunking.TokenAnnotation.begin") && !taBeginStr.equals(""))
										result.add("{" + taBeginStr.substring(0,taBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Chunking.TokenAnnotation.end") && !taEndStr.equals(""))
										result.add("{" + taEndStr.substring(0,taEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Chunking.PosAnnotation.PosValue") && !posAnnotationPosValues.equals(""))
										result.add("{" + posAnnotationPosValues.substring(0,posAnnotationPosValues.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Chunking.PosAnnotation.PosTag") && !posAnnotationPosTags.equals(""))
										result.add("{" + posAnnotationPosTags.substring(0,posAnnotationPosTags.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Chunking.PosAnnotation.begin") && !paBeginStr.equals(""))
										result.add("{" + paBeginStr.substring(0,paBeginStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Chunking.PosAnnotation.end") && !paEndStr.equals(""))
										result.add("{" + paEndStr.substring(0,paEndStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Chunking.begin") && !beginStr.equals(""))
										result.add("{" + beginStr.substring(0,beginStr.length()-1) + "}");
				
									if(tokenTag.equalsIgnoreCase("Chunking.end") && !endStr.equals(""))
										result.add("{" + endStr.substring(0,endStr.length()-1) + "}");
									
									if(tokenTag.equalsIgnoreCase("Chunking.Label") && !labels.equals(""))
										result.add("{" + labels.substring(0,labels.length()-1) + "}");
								}
							}
						}
					}
				
				}
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return result;
	}
}
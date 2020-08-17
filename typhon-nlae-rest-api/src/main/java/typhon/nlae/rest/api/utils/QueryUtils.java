package typhon.nlae.rest.api.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class QueryUtils {

	public static List<String> parseNlpExpression(String nlpExpression) {
		
		String queryString = "";
		List<String> resultString = new ArrayList<String>(); 
		JsonObject jsonObject = JsonParser.parseString(nlpExpression).getAsJsonObject();
		
		String entityType = jsonObject.getAsJsonObject("from").get("entity").getAsString();
		resultString.add(entityType.toLowerCase());
		String alias = jsonObject.getAsJsonObject("from").get("named").getAsString();
        
        JsonArray cols = jsonObject.getAsJsonArray("select");
        queryString = queryString+"SELECT ";
        String[] exploded;
        
        if(cols.size() == 1 && cols.get(0).getAsString() == "*") {
        	queryString = queryString + "*";
        }
        else {
        	for (int i = 0; i < cols.size(); i++){
        		String field = "";
            	String tmp = cols.get(i).getAsString();
            	exploded = tmp.split("[.]");
            	if(exploded.length == 1) {
            		queryString = queryString + alias + "." + tmp.replace("@", "") + ", ";
            		resultString.add(tmp.replace("@", ""));
            	}
            	else if(exploded.length<=2) {
            		queryString = queryString + alias + "." + exploded[1].replace("@", "") + ", ";
            		resultString.add(tmp);
            	}else {
            		for(int j = 2; j<exploded.length; j++)
            			field = field + "." + exploded[j];
            		queryString = queryString + alias + field + ", ";
            		resultString.add(tmp);
               }
            }
        	queryString = queryString.substring(0,queryString.length()-2);
        }
        
       
        queryString = queryString + " ";
        
        queryString = queryString +  "FROM " + entityType.toLowerCase() + " AS " + alias + " ";
        
        JsonArray whereClause = jsonObject.getAsJsonArray("where");
        queryString = queryString+"WHERE ";
        ArrayList<String> queries = new ArrayList<String>();
        ArrayList<String> queryOps = new ArrayList<String>();
        String[] tmpLhs;
        String newLhs = "";
        boolean multiQuery = false;
        if(whereClause.size()>2)
        	multiQuery = true;
        for(int i =0;i< whereClause.size();i++) {
	    	if(multiQuery) {
	    		if(i%2 != 0) {
	    			queryOps.add(whereClause.get(i).getAsJsonObject().get("query-op").getAsString());
	    			continue;
	    		}
	    	}
	    	
			newLhs = alias;
			String op = whereClause.get(i).getAsJsonObject().get("op").getAsString();
	    	String lhs = whereClause.get(i).getAsJsonObject().get("lhs").getAsJsonObject().get("attr").getAsString();
	    	tmpLhs = lhs.split("[.]");
	    	for(int j=2;j<tmpLhs.length;j++) {
	    		newLhs = newLhs + "." + tmpLhs[j];
	    	}
	    	
	    	String rhs = whereClause.get(i).getAsJsonObject().get("rhs").getAsJsonObject().get("lit").getAsString();
	    	String tmpQuery = newLhs + " " + op + " " + rhs;
	    	
	    	queries.add(tmpQuery);
	    }
		
        String mergedQueries = "";
        int count =0;
        if(queryOps.size()>0) {
        	for(int i = 0;i<queries.size()-1;i++) {
        		mergedQueries = mergedQueries + queries.get(i) + " " + queryOps.get(count++) + " ";
        	}
        	mergedQueries = mergedQueries + queries.get(queries.size()-1);
        	queryString = queryString + mergedQueries;
        }
        else {
        	if(queries.size()>1){
            	for(int i = 0;i<queries.size()-1;i++) {
            		mergedQueries = mergedQueries + queries.get(i) + " AND ";
            	}
            	mergedQueries = mergedQueries + queries.get(queries.size()-1);
            	queryString = queryString + mergedQueries;
            }
            else
            	queryString = queryString + queries.get(0);
        }
		
        queryString = "{\"query\": \""+ queryString + "\"}";
        resultString.add(queryString);

        return resultString;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dslQuery = jsonObj.toString();
		
		return dslQuery;
	}
}

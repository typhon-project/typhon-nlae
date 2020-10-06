package typhon.nlae.rest.api.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import typhon.nlae.rest.api.models.NlpExpression;
import typhon.nlae.rest.api.models.NlpExpressionLhs;
import typhon.nlae.rest.api.models.NlpExpressionQuery;
import typhon.nlae.rest.api.models.NlpExpressionRhs;
import typhon.nlae.rest.api.models.NlpExpressionWhere;

public class QueryUtils {

public static List<String> parseNlpExpression(String jsonString) throws JsonMappingException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		NlpExpression nlpExpression= mapper.readValue(jsonString, NlpExpression.class);
	    
		String queryString = "";
		String entityType = "";
		String alias = "";
		List<String> selectList;
		List<String> resultString = new ArrayList<String>();
		
		entityType = nlpExpression.getFrom().getEntity().toLowerCase();
		resultString.add(entityType);
		alias = nlpExpression.getFrom().getNamed();
		
		queryString = queryString+"SELECT ";
		selectList = nlpExpression.getSelect();
		if(selectList.size() == 1 && selectList.get(0) == "*") {
        	queryString = queryString + "*";
        }
        else {
        	for (int i = 0; i < selectList.size(); i++){
            	String selectItem = selectList.get(i);
            	queryString = queryString + selectItem.replace("@", "").replace(".text.", ".")  + ", ";
            	resultString.add(selectItem.replace("@", ""));
            }
        	queryString = queryString.substring(0,queryString.length()-2);
        }
	    
		queryString = queryString + " ";
        queryString = queryString +  "FROM " + entityType.toLowerCase() + " AS " + alias + " ";
		
        queryString = queryString+"WHERE ";
        
        List<NlpExpressionWhere> queryList = null;
        queryList = nlpExpression.getWhere();
        for(int i=0;i<queryList.size();i++) {
        	NlpExpressionWhere tmp = queryList.get(i);
        	List<NlpExpressionQuery> tmpQuery = tmp.getQuery();
        	if(null != tmpQuery) {
        		if(tmpQuery.size()>1)
        			queryString = queryString + "(";
        		for(int j=0;j<tmpQuery.size();j++) {
        			String queryOp = tmpQuery.get(j).getOp();
        			NlpExpressionLhs queryLhs = tmpQuery.get(j).getLhs();
        			NlpExpressionRhs queryRhs = tmpQuery.get(j).getRhs();
        			
        			
        			if(null != tmpQuery.get(j).getcompoundConditionOp())
        				queryString = queryString + queryLhs.getAttr().replace(".text.", ".") + " " + queryOp + " " + queryRhs.getLit() + " " + tmpQuery.get(j).getcompoundConditionOp() + " ";
        			else
        				queryString = queryString + queryLhs.getAttr().replace(".text.", ".") + " " + queryOp + " " + queryRhs.getLit();
        			
        		}
        		if(tmpQuery.size()>1)
        			queryString = queryString + ")";
        	}else {
        		if(null != tmp.getMultiCondition()) {
        			queryString = queryString + " " + tmp.getMultiCondition().getMultiConditionOp() + " ";
        		}
        	}
        		
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

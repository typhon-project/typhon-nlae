package typhon.nlae.jobs.manager;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "entityType", "fieldName", "text", "nlpFeatures", "workflowNames", "result" })
/**
 * This ProcessedEntity class represents a document object used in NLP processing 
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 */
public class ProcessedEntity implements Serializable {

	private static final long serialVersionUID = -6650584511910957426L;
	
	// Document Attribute schema
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("entityType")
	private String entityType;
	
	@JsonProperty("fieldName")
	private String fieldName;
	
	@JsonProperty("text")
	private String text;
	
	@JsonProperty("nlpFeatures")
	private String nlpFeatures;
	
	@JsonProperty("workflowNames")
	private String workflowNames;
	
	@JsonProperty("result")
	private String result;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	// Document field accessors
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("entityType")
	public String getEntityType() {
		return entityType;
	}

	@JsonProperty("entityType")
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	@JsonProperty("fieldName")
	public String getFieldName() {
		return fieldName;
	}

	@JsonProperty("fieldName")
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@JsonProperty("text")
	public String getText() {
		return text;
	}

	@JsonProperty("text")
	public void setText(String text) {
		this.text = text;
	}

	@JsonProperty("nlpFeatures")
	public String getNlpFeatures() {
		return nlpFeatures;
	}

	@JsonProperty("nlpFeatures")
	public void setNlpFeatures(String nlpFeatures) {
		this.nlpFeatures = nlpFeatures;
	}

	@JsonProperty("workflowNames")
	public String getWorkflowNames() {
		return workflowNames;
	}

	@JsonProperty("workflowNames")
	public void setWorkflowNames(String workflowNames) {
		this.workflowNames = workflowNames;
	}

	public String getResult() {
		return result;
	}
	@JsonProperty("result")
	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ProcessedEntity [id=" + id + ", entityType=" + entityType + ", fieldName=" + fieldName + ", text="
				+ text + ", nlpFeatures=" + nlpFeatures + ", workflowNames=" + workflowNames + ", result=" + result + "]";
	}
	
	/**
	 * Simple method which converts the object into a JSON representation of type String
	 * 
	 * @return String (Json Structure)
	 */
	public String toJson() {
			
		String json = "{\n" +
			  "\t\"id\" : \"" + id + "\",\n" +
			  "\t\"entityType\" : \"" + entityType + "\",\n" +
			  "\t\"fieldName\" : \""  + fieldName  + "\",\n" +
			  "\t\"text\" : \""  + text + "\",\n" +
			  "\t\"nlpFeatures\" : \"" +  nlpFeatures + "\",\n" +
			  "\t\"workflowName\" : \"" + workflowNames + "\",\n" +
			  "\t\"result\" : " + result + "\n" + 
			  "}";
		
		return json;
	}

}


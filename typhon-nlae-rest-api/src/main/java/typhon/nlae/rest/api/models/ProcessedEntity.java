package typhon.nlae.rest.api.models;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "entityType", "fieldName", "text", "nlpFeatures", "workflowNames", "result" })
public class ProcessedEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6650584511910957426L;

	public ProcessedEntity(Process process) {
		this.id = process.getId();
		this.entityType = process.getEntityType();
		this.fieldName = process.getFieldName();
		this.nlpFeatures = process.getNlpFeatures();
		this.text = process.getText();
		this.workflowNames = process.getWorkflowNames();
		
	}

	public ProcessedEntity() {}
	
	@JsonProperty("id")
	@ApiModelProperty(notes = "Entity Id", example = "\"12345\"", required = true, position = 1)
	private String id;

	@JsonProperty("entityType")
	@ApiModelProperty(notes = "type of entity", example = "\"review\"", required = true, position = 2)
	private String entityType;

	@JsonProperty("fieldName")
	@ApiModelProperty(notes = "name of field", example = "\"comment\"", required = true, position = 3)
	private String fieldName;

	@JsonProperty("text")
	@ApiModelProperty(notes = "text", example = "\"Do I really need to provide an example?\"", required = true, position = 4)
	private String text;

	@JsonProperty("nlpFeatures")
	@ApiModelProperty(notes = "NLP features", example = "[\"sentimentanalysis\",\"namedentityrecognition\"]", required = true, position = 5)
	private List<String> nlpFeatures;

	@JsonProperty("workflowNames")
	@ApiModelProperty(notes = "The name of workflows", example = "[\"product_review_sentiment\",\"product_review_ner\"]", required = true, position = 6)
	private List<String> workflowNames;

	@JsonProperty("result")
	@ApiModelProperty(notes = "\"Result from proceesing of the entity by the workflow. Note this has to be a String in Json format\"", example = "product_review_emotion", required = true, position = 7)
	private String result;

	private String nlpFeature;
	
	private String workflowName;

	
	@JsonProperty("id")
	public String getId() {
		return id;
	}

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
	public List<String> getNlpFeatures() {
		return nlpFeatures;
	}

	@JsonProperty("nlpFeatures")
	public void setNlpFeatures(List<String> nlpFeatures) {
		this.nlpFeatures = nlpFeatures;
	}

	@JsonProperty("workflowNames")
	public List<String> getWorkflowNames() {
		return workflowNames;
	}

	@JsonProperty("workflowNames")
	public void setWorkflowNames(List<String> workflowNames) {
		this.workflowNames = workflowNames;
	}

	public String getResult() {
		return result;
	}

	@JsonProperty("result")
	public void setResult(String result) {
		this.result = result;
	}

	public String getNlpFeature() {
		return nlpFeature;
	}

	public void setNlpFeature(String nlpFeature) {
		this.nlpFeature = "[\""+nlpFeature+"\"]";
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = "[\""+workflowName+"\"]";
	}
	/**
	 * Simple method which converts the object into a JSON representation of type
	 * String
	 * 
	 * @return String (Json Structure)
	 */
	public String toJson() {


		String json = "{\n" + "\t\"id\" : \"" + id + "\",\n" + "\t\"entityType\" : \"" + entityType + "\",\n"
				+ "\t\"fieldName\" : \"" + fieldName + "\",\n" + "\t\"text\" : \"" + text + "\",\n"
				+ "\t\"nlpFeatures\" : [\"" + nlpFeature + "\"],\n" + "\t\"workflowName\" : [\"" + workflowName + "\"],\n"
				+ "\t\"result\" : \"" + result + "\"\n" + "}";

		return json;
	}

}

package typhon.nlae.rest.api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

@JsonPropertyOrder({
		"id", 
		"entityType", 
		"fieldName", 
		"text",
		"nlpFeatures",
		"workflowNames"
})

public class Process {
	
	@JsonProperty("id")
	@ApiModelProperty(notes = "Entity Id", example = "\"12345\"", required = true, position = 1)
	protected String id;
	
	@JsonProperty("entityType")
	@ApiModelProperty(notes = "type of entity", example = "\"review\"", required = true, position = 2)
	protected String entityType;
	
	@JsonProperty("fieldName")
	@ApiModelProperty(notes = "name of field", example = "\"comment\"", required = true, position = 3)
	protected String fieldName;
	
	@JsonProperty("text")
	@ApiModelProperty(notes = "text", example = "\"text to be processed\"", required = true, position = 4)
	protected String text;
	
	@JsonProperty("nlpFeatures")
	@ApiModelProperty(notes = "1 or more NLP features", example = "[\"sentimentanalysis\",\"namedentityrecognition\"]", required = true, position = 5)
	protected List<String> nlpFeatures;
	
	@JsonProperty("workflowNames")
	@ApiModelProperty(notes = "1 or more Workflow names corresponding to NLP Features above", example = "[\"workflow1\",\"workflow2\"]", required = true, position = 6)
	private List<String> workflowNames;

	public List<String> getWorkflowNames() {
		return workflowNames;
	}

	public void setWorkflowNames(List<String> workflowNames) {
		this.workflowNames = workflowNames;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getNlpFeatures() {
		return nlpFeatures;
	}

	public void setNlpFeatures(List<String> nlpFeatures) {
		this.nlpFeatures = nlpFeatures;
	}


}

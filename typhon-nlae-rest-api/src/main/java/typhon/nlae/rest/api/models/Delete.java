package typhon.nlae.rest.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class Delete {

	@JsonProperty("id")
	@ApiModelProperty(notes = "Entity Id", example = "\"12345\"", required = true, position = 1)
	protected String id;

	@JsonProperty("entityType")
	@ApiModelProperty(notes = "type of entity", example = "\"review\"", required = true, position = 2)
	private String entityType;
	
	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

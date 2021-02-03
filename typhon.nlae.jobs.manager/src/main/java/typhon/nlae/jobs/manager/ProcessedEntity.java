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

package typhon.nlae.jobs.manager;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "entityType", "fieldName", "text", "nlpFeatures", "workflowNames", "result" })

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


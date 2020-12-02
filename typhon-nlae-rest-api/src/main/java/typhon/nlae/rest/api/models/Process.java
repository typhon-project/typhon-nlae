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

package typhon.nlae.rest.api.models;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;


public class Process {
  @SerializedName("id")
  private String id = null;

  @SerializedName("entityType")
  private String entityType = null;

  @SerializedName("fieldName")
  private String fieldName = null;

  @SerializedName("text")
  private String text = null;

  @SerializedName("nlpFeatures")
  private List<String> nlpFeatures = new ArrayList<String>();

  @SerializedName("workflowNames")
  private List<String> workflowNames = new ArrayList<String>();

  public Process id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Entity Id
   * @return id
  **/
  @ApiModelProperty(example = "12345", required = true, value = "Entity Id", position = 1)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Process entityType(String entityType) {
    this.entityType = entityType;
    return this;
  }

   /**
   * type of entity
   * @return entityType
  **/
  @ApiModelProperty(example = "review", required = true, value = "type of entity", position = 2)
  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public Process fieldName(String fieldName) {
    this.fieldName = fieldName;
    return this;
  }

   /**
   * name of field
   * @return fieldName
  **/
  @ApiModelProperty(example = "comment", required = true, value = "name of field", position = 3)
  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public Process text(String text) {
    this.text = text;
    return this;
  }

   /**
   * text
   * @return text
  **/
  @ApiModelProperty(example = "text to be processed", required = true, value = "text", position = 4)
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Process nlpFeatures(List<String> nlpFeatures) {
    this.nlpFeatures = nlpFeatures;
    return this;
  }

  public Process addNlpFeaturesItem(String nlpFeaturesItem) {
    this.nlpFeatures.add(nlpFeaturesItem);
    return this;
  }

   /**
   * 1 or more NLP features
   * @return nlpFeatures
  **/
  @ApiModelProperty(example = "[\"sentimentanalysis\"]", required = true, value = "1 or more NLP features", position = 5)
  public List<String> getNlpFeatures() {
    return nlpFeatures;
  }

  public void setNlpFeatures(List<String> nlpFeatures) {
    this.nlpFeatures = nlpFeatures;
  }

  public Process workflowNames(List<String> workflowNames) {
    this.workflowNames = workflowNames;
    return this;
  }

  public Process addWorkflowNamesItem(String workflowNamesItem) {
    this.workflowNames.add(workflowNamesItem);
    return this;
  }

   /**
   * 1 or more Workflow names corresponding to NLP Features above
   * @return workflowNames
  **/
  @ApiModelProperty(example = "[\"workflow1\"]", required = true, value = "1 or more Workflow names corresponding to NLP Features above", position = 6)
  public List<String> getWorkflowNames() {
    return workflowNames;
  }

  public void setWorkflowNames(List<String> workflowNames) {
    this.workflowNames = workflowNames;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Process process = (Process) o;
    return Objects.equals(this.id, process.id) &&
        Objects.equals(this.entityType, process.entityType) &&
        Objects.equals(this.fieldName, process.fieldName) &&
        Objects.equals(this.text, process.text) &&
        Objects.equals(this.nlpFeatures, process.nlpFeatures) &&
        Objects.equals(this.workflowNames, process.workflowNames);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, entityType, fieldName, text, nlpFeatures, workflowNames);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Process {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    entityType: ").append(toIndentedString(entityType)).append("\n");
    sb.append("    fieldName: ").append(toIndentedString(fieldName)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    nlpFeatures: ").append(toIndentedString(nlpFeatures)).append("\n");
    sb.append("    workflowNames: ").append(toIndentedString(workflowNames)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}


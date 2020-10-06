package typhon.nlae.rest.api.models;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


public class Query {
  @SerializedName("entityType")
  private String entityType = null;

  @SerializedName("fieldName")
  private String fieldName = null;

  @SerializedName("nlpExpression")
  private NlpExpression nlpExpression = null;

  public Query entityType(String entityType) {
    this.entityType = entityType;
    return this;
  }

   /**
   * type of entity
   * @return entityType
  **/
  @ApiModelProperty(example = "review", required = true, value = "type of entity")
  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }

  public Query fieldName(String fieldName) {
    this.fieldName = fieldName;
    return this;
  }

   /**
   * name of field
   * @return fieldName
  **/
  @ApiModelProperty(example = "comment", required = true, value = "name of field")
  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public Query nlpExpression(NlpExpression nlpExpression) {
    this.nlpExpression = nlpExpression;
    return this;
  }

   /**
   * NLP expression to generate query
   * @return nlpExpression
  **/
  @ApiModelProperty(required = true, value = "NLP expression to generate query")
  public NlpExpression getNlpExpression() {
    return nlpExpression;
  }

  public void setNlpExpression(NlpExpression nlpExpression) {
    this.nlpExpression = nlpExpression;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Query query = (Query) o;
    return Objects.equals(this.entityType, query.entityType) &&
        Objects.equals(this.fieldName, query.fieldName) &&
        Objects.equals(this.nlpExpression, query.nlpExpression);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entityType, fieldName, nlpExpression);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Query {\n");
    
    sb.append("    entityType: ").append(toIndentedString(entityType)).append("\n");
    sb.append("    fieldName: ").append(toIndentedString(fieldName)).append("\n");
    sb.append("    nlpExpression: ").append(toIndentedString(nlpExpression)).append("\n");
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
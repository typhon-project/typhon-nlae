package typhon.nlae.rest.api.models;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


public class NlpExpressionFrom {
  @SerializedName("entity")
  private String entity = null;

  @SerializedName("named")
  private String named = null;

  public NlpExpressionFrom entity(String entity) {
    this.entity = entity;
    return this;
  }

   /**
   * Get entity
   * @return entity
  **/
  @ApiModelProperty(example = "Review", required = true, value = "")
  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public NlpExpressionFrom named(String named) {
    this.named = named;
    return this;
  }

   /**
   * Get named
   * @return named
  **/
  @ApiModelProperty(example = "r", required = true, value = "")
  public String getNamed() {
    return named;
  }

  public void setNamed(String named) {
    this.named = named;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NlpExpressionFrom nlpExpressionFrom = (NlpExpressionFrom) o;
    return Objects.equals(this.entity, nlpExpressionFrom.entity) &&
        Objects.equals(this.named, nlpExpressionFrom.named);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entity, named);
  }


  @Override
  public String toString() {
	  StringBuilder sb = new StringBuilder();
	    sb.append("{\n");
	    
	    sb.append("    \"entity\": \"").append(toIndentedString(entity)).append("\",\n");
	    sb.append("    \"named\": \"").append(toIndentedString(named)).append("\"\n");
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
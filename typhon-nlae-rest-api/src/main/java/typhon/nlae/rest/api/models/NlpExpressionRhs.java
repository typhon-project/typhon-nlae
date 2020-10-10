package typhon.nlae.rest.api.models;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;



public class NlpExpressionRhs {
  @SerializedName("lit")
  private String lit = null;

  @SerializedName("type")
  private String type = null;

  public NlpExpressionRhs lit(String lit) {
    this.lit = lit;
    return this;
  }
  
  /**
   * Get lit
   * @return lit
  **/
  @ApiModelProperty(example = "1", required = true, value = "", position = 1)
  public String getLit() {
    return lit;
  }

  public void setLit(String lit) {
    this.lit = lit;
  }

  public NlpExpressionRhs type(String type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(example = "int", required = true, value = "", position = 2)
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NlpExpressionRhs nlpExpressionRhs = (NlpExpressionRhs) o;
    return Objects.equals(this.lit, nlpExpressionRhs.lit) &&
        Objects.equals(this.type, nlpExpressionRhs.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lit, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    \"lit\": \"").append(toIndentedString(lit)).append("\",\n");
    sb.append("   \"type\": \"").append(toIndentedString(type)).append("\"\n");
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
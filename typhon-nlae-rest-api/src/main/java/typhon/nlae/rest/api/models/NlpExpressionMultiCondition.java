package typhon.nlae.rest.api.models;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


public class NlpExpressionMultiCondition {
  @SerializedName("multiConditionOp")
  private String multiConditionOp = null;

  public NlpExpressionMultiCondition multiConditionOp(String multiConditionOp) {
    this.multiConditionOp = multiConditionOp;
    return this;
  }

   /**
   * Get multiConditionOp
   * @return multiConditionOp
  **/
  @ApiModelProperty(example = "AND", value = "")
  public String getMultiConditionOp() {
    return multiConditionOp;
  }

  public void setMultiConditionOp(String multiConditionOp) {
    this.multiConditionOp = multiConditionOp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NlpExpressionMultiCondition nlpExpressionMultiCondition = (NlpExpressionMultiCondition) o;
    return Objects.equals(this.multiConditionOp, nlpExpressionMultiCondition.multiConditionOp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(multiConditionOp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    \"multiConditionOp\": \"").append(toIndentedString(multiConditionOp)).append("\"\n");
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


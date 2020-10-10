package typhon.nlae.rest.api.models;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


public class NlpExpressionQuery {
  @SerializedName("op")
  private String op = null;

  @SerializedName("lhs")
  private NlpExpressionLhs lhs = null;

  @SerializedName("rhs")
  private NlpExpressionRhs rhs = null;

  @SerializedName("compoundConditionOp")
  private String compoundConditionOp = null;

  public NlpExpressionQuery op(String op) {
    this.op = op;
    return this;
  }

   /**
   * Get op
   * @return op
  **/
  @ApiModelProperty(example = ">", value = "", position = 1)
  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public NlpExpressionQuery lhs(NlpExpressionLhs lhs) {
    this.lhs = lhs;
    return this;
  }

   /**
   * Get lhs
   * @return lhs
  **/
  @ApiModelProperty(value = "", position = 2)
  public NlpExpressionLhs getLhs() {
    return lhs;
  }

  public void setLhs(NlpExpressionLhs lhs) {
    this.lhs = lhs;
  }

  public NlpExpressionQuery rhs(NlpExpressionRhs rhs) {
    this.rhs = rhs;
    return this;
  }

   /**
   * Get rhs
   * @return rhs
  **/
  @ApiModelProperty(value = "", position = 3)
  public NlpExpressionRhs getRhs() {
    return rhs;
  }

  public void setRhs(NlpExpressionRhs rhs) {
    this.rhs = rhs;
  }

  public NlpExpressionQuery compoundConditionOp(String compoundConditionOp) {
    this.compoundConditionOp = compoundConditionOp;
    return this;
  }

  /**
  * Get compoundConditionOp
  * @return compoundConditionOp
 **/
 @ApiModelProperty(example = "null", value ="", position = 4)
 public String getcompoundConditionOp() {
   return compoundConditionOp;
 }

 public void setcompoundConditionOp(String compoundConditionOp) {
   this.compoundConditionOp = compoundConditionOp;
 }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NlpExpressionQuery nlpExpressionQuery = (NlpExpressionQuery) o;
    return Objects.equals(this.op, nlpExpressionQuery.op) &&
        Objects.equals(this.lhs, nlpExpressionQuery.lhs) &&
        Objects.equals(this.rhs, nlpExpressionQuery.rhs) &&
        Objects.equals(this.compoundConditionOp, nlpExpressionQuery.compoundConditionOp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(op, lhs, rhs, compoundConditionOp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    \"op\": \"").append(toIndentedString(op)).append("\",\n");
    sb.append("    \"lhs\": ").append(toIndentedString(lhs)).append(",\n");
    sb.append("    \"rhs\": ").append(toIndentedString(rhs)).append("\n");
    if(null != compoundConditionOp)
    	sb.append(",    \"compoundConditionOp\": \"").append(toIndentedString(compoundConditionOp)).append("\"\n");
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

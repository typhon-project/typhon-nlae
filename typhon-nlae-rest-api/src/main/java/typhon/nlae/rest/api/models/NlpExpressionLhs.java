package typhon.nlae.rest.api.models;


import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


public class NlpExpressionLhs {
  @SerializedName("attr")
  private String attr = null;

  public NlpExpressionLhs attr(String attr) {
    this.attr = attr;
    return this;
  }

   /**
   * Get attr
   * @return attr
  **/
  @ApiModelProperty(example = "r.text.SentimentAnalysis.Sentiment", required = true, value = "")
  public String getAttr() {
    return attr;
  }

  public void setAttr(String attr) {
    this.attr = attr;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NlpExpressionLhs nlpExpressionLhs = (NlpExpressionLhs) o;
    return Objects.equals(this.attr, nlpExpressionLhs.attr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attr);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    \"attr\": \"").append(toIndentedString(attr)).append("\"\n");
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


package typhon.nlae.rest.api.models;


import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


public class NlpExpressionWith {
  @SerializedName("path")
  private String path = null;

  @SerializedName("wflow")
  private String wflow = null;

  public NlpExpressionWith path(String path) {
    this.path = path;
    return this;
  }

   /**
   * Get path
   * @return path
  **/
  @ApiModelProperty(example = "r.text.SentimentAnaylsis", value = "")
  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public NlpExpressionWith wflow(String wflow) {
    this.wflow = wflow;
    return this;
  }

   /**
   * Get wflow
   * @return wflow
  **/
  @ApiModelProperty(example = "wflow1", value = "")
  public String getWflow() {
    return wflow;
  }

  public void setWflow(String wflow) {
    this.wflow = wflow;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NlpExpressionWith nlpExpressionWith = (NlpExpressionWith) o;
    return Objects.equals(this.path, nlpExpressionWith.path) &&
        Objects.equals(this.wflow, nlpExpressionWith.wflow);
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, wflow);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    \"path\": \"").append(toIndentedString(path)).append("\",\n");
    sb.append("    \"wflow\":\"").append(toIndentedString(wflow)).append("\"\n");
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


package typhon.nlae.rest.api.models;


import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;



public class NlpExpressionWhere {
  @SerializedName("query")
  private List<NlpExpressionQuery> query = null;
  
  @SerializedName("multiCondition")
  private NlpExpressionMultiCondition multiCondition = null;

  public NlpExpressionWhere query(List<NlpExpressionQuery> query) {
    this.query = query;
    return this;
  }

  public NlpExpressionWhere addQueryItem(NlpExpressionQuery queryItem) {
    if (this.query == null) {
      this.query = new ArrayList<NlpExpressionQuery>();
    }
    this.query.add(queryItem);
    return this;
  }

   /**
   * Get query
   * @return query
  **/
  @ApiModelProperty(value = "", position = 1)
  public List<NlpExpressionQuery> getQuery() {
    return query;
  }

  public void setQuery(List<NlpExpressionQuery> query) {
    this.query = query;
  }


  public NlpExpressionWhere multiCondition(NlpExpressionMultiCondition multiCondition) {
    this.multiCondition = multiCondition;
    return this;
  }

   /**
   * Get multiCondition
   * @return multiCondition
  **/
  @ApiModelProperty(value = "", position = 2)
  public NlpExpressionMultiCondition getMultiCondition() {
    return multiCondition;
  }

  public void setMultiCondition(NlpExpressionMultiCondition multiCondition) {
    this.multiCondition = multiCondition;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NlpExpressionWhere nlpExpressionWhere = (NlpExpressionWhere) o;
    return Objects.equals(this.query, nlpExpressionWhere.query) &&
        Objects.equals(this.multiCondition, nlpExpressionWhere.multiCondition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(query, multiCondition);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    if(null != query)
    	sb.append("    \"query\": ").append(toIndentedString(query)).append("\n");
    if(null != multiCondition)
    	sb.append("    \"multiCondition\": ").append(toIndentedString(multiCondition)).append("\n");
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
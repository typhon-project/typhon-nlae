package typhon.nlae.rest.api.models;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class NlpExpression {
  @SerializedName("from")
  private NlpExpressionFrom from = null;

  @SerializedName("with")
  private List<NlpExpressionWith> with = new ArrayList<NlpExpressionWith>();

  @SerializedName("select")
  private List<String> select = new ArrayList<String>();

  @SerializedName("where")
  private List<NlpExpressionWhere> where = new ArrayList<NlpExpressionWhere>();

  public NlpExpression from(NlpExpressionFrom from) {
    this.from = from;
    return this;
  }

   /**
   * Get from
   * @return from
  **/
  @ApiModelProperty(required = true, value = "", position = 1)
  public NlpExpressionFrom getFrom() {
    return from;
  }

  public void setFrom(NlpExpressionFrom from) {
    this.from = from;
  }

  public NlpExpression with(List<NlpExpressionWith> with) {
    this.with = with;
    return this;
  }

  public NlpExpression addWithItem(NlpExpressionWith withItem) {
    this.with.add(withItem);
    return this;
  }

   /**
   * Get with
   * @return with
  **/
  @ApiModelProperty(required = true, value = "", position = 2)
  public List<NlpExpressionWith> getWith() {
    return with;
  }

  public void setWith(List<NlpExpressionWith> with) {
    this.with = with;
  }

  public NlpExpression select(List<String> select) {
    this.select = select;
    return this;
  }

  public NlpExpression addSelectItem(String selectItem) {
    this.select.add(selectItem);
    return this;
  }

   /**
   * Get select
   * @return select
  **/
  @ApiModelProperty(example = "[\"r.@id\",\"r.text.SentimentAnalysis.Sentiment\",\"r.text.NamedEntityRecognition.NamedEntity\"]", required = true, value = "", position = 3)
  public List<String> getSelect() {
    return select;
  }

  public void setSelect(List<String> select) {
    this.select = select;
  }

  public NlpExpression where(List<NlpExpressionWhere> where) {
    this.where = where;
    return this;
  }

  public NlpExpression addWhereItem(NlpExpressionWhere whereItem) {
    this.where.add(whereItem);
    return this;
  }

   /**
   * Get where
   * @return where
  **/
  @ApiModelProperty(required = true, value = "", example = "[{\"query\":[{\"op\":\">\",\"lhs\":{\"attr\":\"r.text.SentimentAnalysis.Sentiment\"},\"rhs\":{\"lit\":1,\"type\":\"int\"}}]},{\"multiCondition\":{\"multiConditionOp\":\"AND\"}},{\"query\":[{\"op\":\"<\",\"lhs\":{\"attr\":\"r.text.SentimentAnalysis.Sentiment\"},\"rhs\":{\"lit\":4,\"type\":\"int\"}}]}]" , position = 4)
  public List<NlpExpressionWhere> getWhere() {
    return where;
  }

  public void setWhere(List<NlpExpressionWhere> where) {
    this.where = where;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NlpExpression nlpExpression = (NlpExpression) o;
    return Objects.equals(this.from, nlpExpression.from) &&
        Objects.equals(this.with, nlpExpression.with) &&
        Objects.equals(this.select, nlpExpression.select) &&
        Objects.equals(this.where, nlpExpression.where);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, with, select, where);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("NlpExpression {\n");
    
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    with: ").append(toIndentedString(with)).append("\n");
    sb.append("    select: ").append(toIndentedString(select)).append("\n");
    sb.append("    where: ").append(toIndentedString(where)).append("\n");
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
  
  public String toJson() {
	String withStr = "";
	ListIterator<NlpExpressionWith> withIterator = with.listIterator();
	 
	while(withIterator.hasNext()) {
		 withStr = withStr + withIterator.next() + ",";
	}
	withStr = withStr.substring(0, withStr.length()-1);
	
	ListIterator<String> selectIterator = select.listIterator();
	 
	String selectStr = "";
	while(selectIterator.hasNext()) {
		 selectStr = selectStr + "\"" + selectIterator.next() + "\",";
	}
	selectStr = selectStr.substring(0, selectStr.length()-1);
	  
	StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    
    sb.append("    \"from\": ").append(from).append(",\n");
    sb.append("   \"with\": [").append(withStr).append("],\n");
    sb.append("    \"select\": [").append(selectStr).append("],\n");
    sb.append("    \"where\": ").append(where).append("\n");
    sb.append("}");
    return sb.toString();
  }

}
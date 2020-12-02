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
import java.util.ListIterator;

public class Query {
  @SerializedName("from")
  private QueryFrom from = null;

  @SerializedName("with")
  private List<QueryWith> with = new ArrayList<QueryWith>();

  @SerializedName("select")
  private List<String> select = new ArrayList<String>();

  @SerializedName("where")
  private NlpExpression where = null;

  public Query from(QueryFrom from) {
    this.from = from;
    return this;
  }

   /**
   * Get from
   * @return from
  **/
  @ApiModelProperty(required = true, value = "", position = 1)
  public QueryFrom getFrom() {
    return from;
  }

  public void setFrom(QueryFrom from) {
    this.from = from;
  }

  public Query with(List<QueryWith> with) {
    this.with = with;
    return this;
  }

  public Query addWithItem(QueryWith withItem) {
    this.with.add(withItem);
    return this;
  }

   /**
   * Get with
   * @return with
  **/
  @ApiModelProperty(required = true, value = "", position = 2)
  public List<QueryWith> getWith() {
    return with;
  }

  public void setWith(List<QueryWith> with) {
    this.with = with;
  }

  public Query select(List<String> select) {
    this.select = select;
    return this;
  }

  public Query addSelectItem(String selectItem) {
    this.select.add(selectItem);
    return this;
  }

   /**
   * Get select
   * @return select
  **/
  @ApiModelProperty(example = "[\"r.@id\",\"r.text.SentimentAnalysis.Sentiment\",\"r.text.NamedEntityRecognition.NamedEntity\"]", required = true, value = "",position = 3)
  public List<String> getSelect() {
    return select;
  }

  public void setSelect(List<String> select) {
    this.select = select;
  }

  public Query where(NlpExpression where) {
    this.where = where;
    return this;
  }

   /**
   * Get where
   * @return where
  **/
  @ApiModelProperty(example = "",required = true, value = "", position = 4)
  public NlpExpression getWhere() {
    return where;
  }

  public void setWhere(NlpExpression where) {
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
    Query query = (Query) o;
    return Objects.equals(this.from, query.from) &&
        Objects.equals(this.with, query.with) &&
        Objects.equals(this.select, query.select) &&
        Objects.equals(this.where, query.where);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, with, select, where);
  }


  @Override
  public String toString() {
	
	ListIterator<String> selectIterator = select.listIterator();
	 
	String selectStr = "";
	while(selectIterator.hasNext()) {
		 selectStr = selectStr + "\"" + selectIterator.next() + "\",";
	}
	selectStr = selectStr.substring(0, selectStr.length()-1);
	
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    \"from\": ").append(toIndentedString(from)).append(",\n");
    sb.append("    \"with\": ").append(toIndentedString(with)).append(",\n");
    sb.append("    \"select\": [").append(selectStr).append("],\n");
    sb.append("    \"where\": ").append(toIndentedString(where)).append("\n");
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


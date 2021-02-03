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

public class QueryResponse {
	  @SerializedName("header")
	  private List<String> header = new ArrayList<String>();

	  @SerializedName("records")
	  private List<List<String>> records = new ArrayList<List<String>>();

	  public QueryResponse header(List<String> header) {
	    this.header = header;
	    return this;
	  }

	  public QueryResponse addHeaderItem(String headerItem) {
	    this.header.add(headerItem);
	    return this;
	  }

	   /**
	   * Get header
	   * @return header
	  **/
	  @ApiModelProperty(example = "[\"r.id\",\"r.text.SentimentAnalysis.Sentiment\",\"r.text.NamedEntityRecognition.NamedEntity\"]", required = true, value = "")
	  public List<String> getHeader() {
	    return header;
	  }

	  public void setHeader(List<String> header) {
	    this.header = header;
	  }

	  public QueryResponse records(List<List<String>> records) {
	    this.records = records;
	    return this;
	  }

	  public QueryResponse addRecordsItem(List<String> recordsItem) {
	    this.records.add(recordsItem);
	    return this;
	  }

	   /**
	   * Get records
	   * @return records
	  **/
	  @ApiModelProperty(example = "[[\"e757fd4f-edc4-3e82-9bb8-1b1b466a0901\",\"3\",\"{ORGANIZATION, DURATION, PERSON}\"],[\"e757fd4f-edc4-3e82-9bb8-1b1b466a0902\",\"2\",\"{DURATION, PERSON}\"]]", required = true, value = "")
	  public List<List<String>> getRecords() {
	    return records;
	  }

	  public void setRecords(List<List<String>> records) {
	    this.records = records;
	  }


	  @Override
	  public boolean equals(java.lang.Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    QueryResponse queryResponse = (QueryResponse) o;
	    return Objects.equals(this.header, queryResponse.header) &&
	        Objects.equals(this.records, queryResponse.records);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(header, records);
	  }


	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("{\n");
	    
	    sb.append("    header: ").append(toIndentedString(header)).append("\n");
	    sb.append("    records: ").append(toIndentedString(records)).append("\n");
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

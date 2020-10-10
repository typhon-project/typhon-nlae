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
  private List<Object> records = new ArrayList<Object>();

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
  @ApiModelProperty(required = true, value = "", example = "[\"r.id\",\"r.text.SentimentAnalysis.Sentiment\",\"r.text.NamedEntityRecognition.NamedEntity\"]", position = 1)
  public List<String> getHeader() {
    return header;
  }

  public void setHeader(List<String> header) {
    this.header = header;
  }

  public QueryResponse records(List<Object> records) {
    this.records = records;
    return this;
  }

  public QueryResponse addRecordsItem(Object recordsItem) {
    this.records.add(recordsItem);
    return this;
  }

   /**
   * Get records
   * @return records
  **/
  @ApiModelProperty(required = true, value = "", example = "[{\"id\":\"A1bb45K44\",\"SentimentAnalysis\":[{\"Sentiment\":\"3\"}],\"NamedEntityRecognition\":[{\"NamedEntity\":\"ORGANIZATION\"},{\"NamedEntity\":\"DURATION\"},{\"NamedEntity\":\"PERSON\"}]}]", position = 2)
  public List<Object> getRecords() {
    return records;
  }

  public void setRecords(List<Object> records) {
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
    QueryResponse qresponse = (QueryResponse) o;
    return Objects.equals(this.header, qresponse.header) &&
        Objects.equals(this.records, qresponse.records);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, records);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    \"header\": ").append(toIndentedString(header)).append("\n");
    sb.append("    \"records\": ").append(toIndentedString(records)).append("\n");
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

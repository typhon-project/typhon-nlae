package typhon.nlae.rest.api.models;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;


public class Delete {
  @SerializedName("id")
  private String id = null;

  @SerializedName("entityType")
  private String entityType = null;

  public Delete id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Entity Id
   * @return id
  **/
  @ApiModelProperty(example = "12345", required = true, value = "Entity Id", position = 1)
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Delete entityType(String entityType) {
    this.entityType = entityType;
    return this;
  }

   /**
   * type of entity
   * @return entityType
  **/
  @ApiModelProperty(example = "review", required = true, value = "type of entity", position = 2)
  public String getEntityType() {
    return entityType;
  }

  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Delete delete = (Delete) o;
    return Objects.equals(this.id, delete.id) &&
        Objects.equals(this.entityType, delete.entityType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, entityType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Delete {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    entityType: ").append(toIndentedString(entityType)).append("\n");
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
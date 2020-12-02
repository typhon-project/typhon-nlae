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
import java.io.Serializable;


public class BinaryExpression implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@SerializedName("op")
  private String op = null;

  @SerializedName("lhs")
  private NlpExpression lhs = null;

  @SerializedName("rhs")
  private NlpExpression rhs = null;

  public BinaryExpression op(String op) {
    this.op = op;
    return this;
  }

   /**
   * Get op
   * @return op
  **/
  @ApiModelProperty(example = ">", required = true, value = "")
  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public BinaryExpression lhs(NlpExpression lhs) {
    this.lhs = lhs;
    return this;
  }

   /**
   * Get lhs
   * @return lhs
  **/
  @ApiModelProperty(required = true, value = "")
  public NlpExpression getLhs() {
    return lhs;
  }

  public void setLhs(NlpExpression lhs) {
    this.lhs = lhs;
  }

  public BinaryExpression rhs(NlpExpression rhs) {
    this.rhs = rhs;
    return this;
  }

   /**
   * Get rhs
   * @return rhs
  **/
  @ApiModelProperty(required = true, value = "")
  public NlpExpression getRhs() {
    return rhs;
  }

  public void setRhs(NlpExpression rhs) {
    this.rhs = rhs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BinaryExpression binaryExpression = (BinaryExpression) o;
    return Objects.equals(this.op, binaryExpression.op) &&
        Objects.equals(this.lhs, binaryExpression.lhs) &&
        Objects.equals(this.rhs, binaryExpression.rhs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(op, lhs, rhs);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    
    sb.append("    \"op\": \"").append(toIndentedString(op)).append("\",\n");
    sb.append("    \"lhs\": ").append(toIndentedString(lhs)).append(",\n");
    sb.append("    \"rhs\": ").append(toIndentedString(rhs)).append("\n");
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


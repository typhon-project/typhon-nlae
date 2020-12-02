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


public class NlpExpression implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@SerializedName("binaryExpression")
  private BinaryExpression binaryExpression = null;

  @SerializedName("unaryExpression")
  private UnaryExpression unaryExpression = null;

  @SerializedName("attribute")
  private Attribute attribute = null;

  @SerializedName("literal")
  private Literal literal = null;

  public NlpExpression binaryExpression(BinaryExpression binaryExpression) {
    this.binaryExpression = binaryExpression;
    return this;
  }

   /**
   * Get binaryExpression
   * @return binaryExpression
  **/
  @ApiModelProperty(value = "")
  public BinaryExpression getBinaryExpression() {
    return binaryExpression;
  }

  public void setBinaryExpression(BinaryExpression binaryExpression) {
    this.binaryExpression = binaryExpression;
  }

  public NlpExpression unaryExpression(UnaryExpression unaryExpression) {
    this.unaryExpression = unaryExpression;
    return this;
  }

   /**
   * Get unaryExpression
   * @return unaryExpression
  **/
  @ApiModelProperty(value = "")
  public UnaryExpression getUnaryExpression() {
    return unaryExpression;
  }

  public void setUnaryExpression(UnaryExpression unaryExpression) {
    this.unaryExpression = unaryExpression;
  }

  public NlpExpression attribute(Attribute attribute) {
    this.attribute = attribute;
    return this;
  }

   /**
   * Get attribute
   * @return attribute
  **/
  @ApiModelProperty(value = "")
  public Attribute getAttribute() {
    return attribute;
  }

  public void setAttribute(Attribute attribute) {
    this.attribute = attribute;
  }

  public NlpExpression literal(Literal literal) {
    this.literal = literal;
    return this;
  }

   /**
   * Get literal
   * @return literal
  **/
  @ApiModelProperty(value = "")
  public Literal getLiteral() {
    return literal;
  }

  public void setLiteral(Literal literal) {
    this.literal = literal;
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
    return Objects.equals(this.binaryExpression, nlpExpression.binaryExpression) &&
        Objects.equals(this.unaryExpression, nlpExpression.unaryExpression) &&
        Objects.equals(this.attribute, nlpExpression.attribute) &&
        Objects.equals(this.literal, nlpExpression.literal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(binaryExpression, unaryExpression, attribute, literal);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    if(null != binaryExpression)
    	sb.append("    \"binaryExpression\": ").append(toIndentedString(binaryExpression)).append("\n");
    if(null != unaryExpression)
    	sb.append("    \"unaryExpression\": ").append(toIndentedString(unaryExpression)).append("\n");
    if(null != attribute)
    	sb.append("    \"attribute\": ").append(toIndentedString(attribute)).append("\n");
    if(null != literal)
    	sb.append("    \"literal\": ").append(toIndentedString(literal)).append("\n");
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


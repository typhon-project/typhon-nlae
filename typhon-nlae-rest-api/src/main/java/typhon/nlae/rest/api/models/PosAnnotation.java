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

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"begin",
"end",
"PosValue",
"PosTag"
})
public class PosAnnotation implements Serializable
{

	@JsonProperty("begin")
	private Long begin;
	@JsonProperty("end")
	private Long end;
	@JsonProperty("PosValue")
	private String posValue;
	@JsonProperty("PosTag")
	private String posTag;
	private final static long serialVersionUID = -3846245758921813015L;
	
	@JsonProperty("begin")
	public Long getBegin() {
	return begin;
	}
	
	@JsonProperty("begin")
	public void setBegin(Long begin) {
	this.begin = begin;
	}
	
	@JsonProperty("end")
	public Long getEnd() {
	return end;
	}
	
	@JsonProperty("end")
	public void setEnd(Long end) {
	this.end = end;
	}
	
	@JsonProperty("PosValue")
	public String getPosValue() {
	return posValue;
	}
	
	@JsonProperty("PosValue")
	public void setPosValue(String posValue) {
	this.posValue = posValue;
	}
	
	@JsonProperty("PosTag")
	public String getPosTag() {
	return posTag;
	}
	
	@JsonProperty("PosTag")
	public void setPosTag(String posTag) {
	this.posTag = posTag;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("begin", begin).append("end", end).append("posValue", posValue).append("posTag", posTag).toString();
	}

}

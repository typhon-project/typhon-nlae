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
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"begin",
"end",
"SourceEntity",
"TargetEntity",
"DependencyName"
})
public class DependencyParsing implements Serializable
{

	@JsonProperty("begin")
	private Long begin;
	@JsonProperty("end")
	private Long end;
	@JsonProperty("SourceEntity")
	private List<SourceEntity> sourceEntity = null;
	@JsonProperty("TargetEntity")
	private List<TargetEntity> targetEntity = null;
	@JsonProperty("DependencyName")
	private String dependencyName;
	private final static long serialVersionUID = 4581150048103914308L;
	
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
	
	@JsonProperty("SourceEntity")
	public List<SourceEntity> getSourceEntity() {
	return sourceEntity;
	}
	
	@JsonProperty("SourceEntity")
	public void setSourceEntity(List<SourceEntity> sourceEntity) {
	this.sourceEntity = sourceEntity;
	}
	
	@JsonProperty("TargetEntity")
	public List<TargetEntity> getTargetEntity() {
	return targetEntity;
	}
	
	@JsonProperty("TargetEntity")
	public void setTargetEntity(List<TargetEntity> targetEntity) {
	this.targetEntity = targetEntity;
	}
	
	@JsonProperty("DependencyName")
	public String getDependencyName() {
	return dependencyName;
	}
	
	@JsonProperty("DependencyName")
	public void setDependencyName(String dependencyName) {
	this.dependencyName = dependencyName;
	}
	
	@Override
	public String toString() {
	return new ToStringBuilder(this).append("begin", begin).append("end", end).append("sourceEntity", sourceEntity).append("targetEntity", targetEntity).append("dependencyName", dependencyName).toString();
	}

}

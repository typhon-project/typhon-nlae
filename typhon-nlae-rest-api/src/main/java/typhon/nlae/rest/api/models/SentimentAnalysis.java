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
"Sentiment",
"SentimentLabel"
})
public class SentimentAnalysis implements Serializable
{

@JsonProperty("Sentiment")
private String sentiment;
@JsonProperty("SentimentLabel")
private String sentimentLabel;
private final static long serialVersionUID = -2712220480159714124L;

@JsonProperty("Sentiment")
public String getSentiment() {
return sentiment;
}

@JsonProperty("Sentiment")
public void setSentiment(String sentiment) {
this.sentiment = sentiment;
}

@JsonProperty("SentimentLabel")
public String getSentimentLabel() {
return sentimentLabel;
}

@JsonProperty("SentimentLabel")
public void setSentimentLabel(String sentimentLabel) {
this.sentimentLabel = sentimentLabel;
}

@Override
public String toString() {
return new ToStringBuilder(this).append("Sentiment", sentiment).append("SentimentLabel", sentimentLabel).toString();
}

}

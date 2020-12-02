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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
//"id",
"SentimentAnalysis",
"NamedEntityRecognition",
"Tokenisation",
"SentenceSegmentation",
"ParagraphSegmentation",
"PhraseExtraction",
"TermExtraction",
"nGramExtraction",
"POSTagging",
"Lemmatisation",
"Stemming",
"DependencyParsing",
"CoreferenceResolution",
"RelationExtraction",
"Chunking"
})
public class Record implements Serializable
	{
	
	@JsonProperty("SentimentAnalysis")
	private List<SentimentAnalysis> sentimentAnalysis = null;
	@JsonProperty("NamedEntityRecognition")
	private List<NamedEntityRecognition> namedEntityRecognition = null;
	@JsonProperty("Tokenisation")
	private List<Tokenisation> tokenisation = null;
	@JsonProperty("SentenceSegmentation")
	private List<SentenceSegmentation> sentenceSegmentation = null;
	@JsonProperty("ParagraphSegmentation")
	private List<ParagraphSegmentation> paragraphSegmentation = null;
	@JsonProperty("PhraseExtraction")
	private List<PhraseExtraction> phraseExtraction = null;
	@JsonProperty("TermExtraction")
	private List<TermExtraction> termExtraction = null;
	@JsonProperty("nGramExtraction")
	private List<NGramExtraction> nGramExtraction = null;
	@JsonProperty("POSTagging")
	private List<POSTagging> pOSTagging = null;
	@JsonProperty("Lemmatisation")
	private List<Lemmatisation> lemmatisation = null;
	@JsonProperty("Stemming")
	private List<Stemming> stemming = null;
	@JsonProperty("DependencyParsing")
	private List<DependencyParsing> dependencyParsing = null;
	@JsonProperty("CoreferenceResolution")
	private List<CoreferenceResolution> coreferenceResolution = null;
	@JsonProperty("RelationExtraction")
	private List<RelationExtraction> relationExtraction = null;
	@JsonProperty("Chunking")
	private List<Chunking> chunking = null;
	private final static long serialVersionUID = 2972790386989053338L;

	@JsonProperty("SentimentAnalysis")
	public List<SentimentAnalysis> getSentimentAnalysis() {
	return sentimentAnalysis;
	}
	
	@JsonProperty("SentimentAnalysis")
	public void setSentimentAnalysis(List<SentimentAnalysis> sentimentAnalysis) {
	this.sentimentAnalysis = sentimentAnalysis;
	}
	
	@JsonProperty("NamedEntityRecognition")
	public List<NamedEntityRecognition> getNamedEntityRecognition() {
	return namedEntityRecognition;
	}
	
	@JsonProperty("NamedEntityRecognition")
	public void setNamedEntityRecognition(List<NamedEntityRecognition> namedEntityRecognition) {
	this.namedEntityRecognition = namedEntityRecognition;
	}
	
	@JsonProperty("Tokenisation")
	public List<Tokenisation> getTokenisation() {
	return tokenisation;
	}

	@JsonProperty("Tokenisation")
	public void setTokenisation(List<Tokenisation> tokenisation) {
	this.tokenisation = tokenisation;
	}
	
	@JsonProperty("SentenceSegmentation")
	public List<SentenceSegmentation> getSentenceSegmentation() {
	return sentenceSegmentation;
	}

	@JsonProperty("SentenceSegmentation")
	public void setSentenceSegmentation(List<SentenceSegmentation> sentenceSegmentation) {
	this.sentenceSegmentation = sentenceSegmentation;
	}

	@JsonProperty("ParagraphSegmentation")
	public List<ParagraphSegmentation> getParagraphSegmentation() {
	return paragraphSegmentation;
	}

	@JsonProperty("ParagraphSegmentation")
	public void setParagraphSegmentation(List<ParagraphSegmentation> paragraphSegmentation) {
	this.paragraphSegmentation = paragraphSegmentation;
	}

	@JsonProperty("PhraseExtraction")
	public List<PhraseExtraction> getPhraseExtraction() {
	return phraseExtraction;
	}

	@JsonProperty("PhraseExtraction")
	public void setPhraseExtraction(List<PhraseExtraction> phraseExtraction) {
	this.phraseExtraction = phraseExtraction;
	}

	@JsonProperty("TermExtraction")
	public List<TermExtraction> getTermExtraction() {
	return termExtraction;
	}

	@JsonProperty("TermExtraction")
	public void setTermExtraction(List<TermExtraction> termExtraction) {
	this.termExtraction = termExtraction;
	}

	@JsonProperty("nGramExtraction")
	public List<NGramExtraction> getNGramExtraction() {
	return nGramExtraction;
	}

	@JsonProperty("nGramExtraction")
	public void setNGramExtraction(List<NGramExtraction> nGramExtraction) {
	this.nGramExtraction = nGramExtraction;
	}

	@JsonProperty("POSTagging")
	public List<POSTagging> getPOSTagging() {
	return pOSTagging;
	}

	@JsonProperty("POSTagging")
	public void setPOSTagging(List<POSTagging> pOSTagging) {
	this.pOSTagging = pOSTagging;
	}

	@JsonProperty("Lemmatisation")
	public List<Lemmatisation> getLemmatisation() {
	return lemmatisation;
	}

	@JsonProperty("Lemmatisation")
	public void setLemmatisation(List<Lemmatisation> lemmatisation) {
	this.lemmatisation = lemmatisation;
	}

	@JsonProperty("Stemming")
	public List<Stemming> getStemming() {
	return stemming;
	}

	@JsonProperty("Stemming")
	public void setStemming(List<Stemming> stemming) {
	this.stemming = stemming;
	}

	@JsonProperty("DependencyParsing")
	public List<DependencyParsing> getDependencyParsing() {
	return dependencyParsing;
	}

	@JsonProperty("DependencyParsing")
	public void setDependencyParsing(List<DependencyParsing> dependencyParsing) {
	this.dependencyParsing = dependencyParsing;
	}

	@JsonProperty("CoreferenceResolution")
	public List<CoreferenceResolution> getCoreferenceResolution() {
	return coreferenceResolution;
	}

	@JsonProperty("CoreferenceResolution")
	public void setCoreferenceResolution(List<CoreferenceResolution> coreferenceResolution) {
	this.coreferenceResolution = coreferenceResolution;
	}

	@JsonProperty("RelationExtraction")
	public List<RelationExtraction> getRelationExtraction() {
	return relationExtraction;
	}

	@JsonProperty("RelationExtraction")
	public void setRelationExtraction(List<RelationExtraction> relationExtraction) {
	this.relationExtraction = relationExtraction;
	}

	@JsonProperty("Chunking")
	public List<Chunking> getChunking() {
	return chunking;
	}

	@JsonProperty("Chunking")
	public void setChunking(List<Chunking> chunking) {
	this.chunking = chunking;
	}

	@Override
	public String toString() {
	  StringBuilder sb = new StringBuilder();
	  sb.append("{\n");

	  if(null != sentimentAnalysis)
		  sb.append("SentimentAnalysis").append(sentimentAnalysis.toString());
	  if(null != namedEntityRecognition)
		  sb.append("NamedEntityRecognition").append(namedEntityRecognition.toString());
	  if(null != "tokenisation")
		  sb.append("Tokenisation").append(tokenisation.toString());
	  if(null != "sentenceSegmentation")
		  sb.append("SentenceSegmentation").append(sentenceSegmentation.toString());
	  if(null != "paragraphSegmentation")
		  sb.append("ParagraphSegmentation").append(paragraphSegmentation.toString());
	  if(null != "phraseExtraction")
		  sb.append("PhraseExtraction").append(phraseExtraction.toString());
	  if(null != "termExtraction")
		  sb.append("TermExtraction").append(termExtraction.toString());
	  if(null != "nGramExtraction")
		  sb.append("NGramExtraction").append(nGramExtraction.toString());
	  if(null != "pOSTagging")
		  sb.append("POSTagging").append(pOSTagging.toString());
	  if(null != "lemmatisation")
		  sb.append("Lemmatisation").append(lemmatisation.toString());
	  if(null != "stemming")
		  sb.append("Stemming").append(stemming.toString());
	  if(null != "dependencyParsing")
		  sb.append("DependencyParsing").append(dependencyParsing.toString());
	  if(null != "coreferenceResolution")
		  sb.append("CoreferenceResolution").append(coreferenceResolution.toString());
	  if(null != "relationExtraction")
		  sb.append("RelationExtraction").append(relationExtraction.toString());
	  if(null != "chunking")
		  sb.append("Chunking").append(chunking.toString());
	
	  sb.append("}");
	  return sb.toString();
	}
}
/*
@Override
public String toString() {
return new ToStringBuilder(this).append("id", id).append("SentimentAnalysis", sentimentAnalysis).append("NamedEntityRecognition", namedEntityRecognition).toString();
}
/*
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"SentimentAnalysis",
"NamedEntityRecognition"
})
public class Record implements Serializable
{

@JsonProperty("id")
private String id;
@JsonProperty("SentimentAnalysis")
private List<SentimentAnalysis> sentimentAnalysis = null;
@JsonProperty("NamedEntityRecognition")
private List<NamedEntityRecognition> namedEntityRecognition = null;
private final static long serialVersionUID = 2972790386989053338L;

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("SentimentAnalysis")
public List<SentimentAnalysis> getSentimentAnalysis() {
return sentimentAnalysis;
}

@JsonProperty("SentimentAnalysis")
public void setSentimentAnalysis(List<SentimentAnalysis> sentimentAnalysis) {
this.sentimentAnalysis = sentimentAnalysis;
}

@JsonProperty("NamedEntityRecognition")
public List<NamedEntityRecognition> getNamedEntityRecognition() {
return namedEntityRecognition;
}

@JsonProperty("NamedEntityRecognition")
public void setNamedEntityRecognition(List<NamedEntityRecognition> namedEntityRecognition) {
this.namedEntityRecognition = namedEntityRecognition;
}

@Override
public String toString() {
  StringBuilder sb = new StringBuilder();
  sb.append("{\n");
  if(!id.isEmpty())
	  sb.append("    \"id\": ").append(id);
  if(null != sentimentAnalysis)
	  sb.append("SentimentAnalysis").append(sentimentAnalysis.toString());
  if(null != namedEntityRecognition)
	  sb.append("NamedEntityRecognition").append(namedEntityRecognition.toString());
  sb.append("}");
  return sb.toString();
}
/*
@Override
public String toString() {
return new ToStringBuilder(this).append("id", id).append("SentimentAnalysis", sentimentAnalysis).append("NamedEntityRecognition", namedEntityRecognition).toString();
}

}*/
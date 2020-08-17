package typhon.nlae.jobs.manager;

import typhon.nlae.jobs.manager.components.*;

/**
 * This NlpProcesser class integrates and executes NLP functionality pertaining to the TYPHON type system 
 * @author Raja Muhammad Suleman
 * @author Mostafa Alwash 
 * @version 1.0
 * @see ParagraphSegmentation, SentenceSegmentation, Tokenisation, PhraseExtraction, NgramExtraction, PosTagging, Lemmatisation, Stemming, DependencyParsing, Chunking, SentimentAnalysis, TermExtraction, NamedEntityRecognition, RelationExtraction, CoreferenceResolution
 */
public class NlpProcessor {

	//NLP task-processors
	private ParagraphSegmentation paragraphProcessor;
	private SentenceSegmentation sentenceProcessor;
	private Tokenisation tokeniseProcessor;
	private PhraseExtraction phraseProcessor;
	private NgramExtraction ngramProcessor;
	private PosTagging posProcessor;
	private Lemmatisation lemmaProcessor;
	private Stemming stemProcessor;
	private DependencyParse depProcessor;
	private Chunking chunkProcessor;
	private SentimentAnalysis saProcessor;
	private TermExtraction termProcessor;
	private NamedEntityRecognition nerProcessor;
	private RelationExtraction relationProcessor;
	private CoreferenceResolution corefProcessor;
	
	/**
	 * Construction method to initialise NLP functions 
	 */
	public NlpProcessor() {
		paragraphProcessor = new ParagraphSegmentation();
		sentenceProcessor = new SentenceSegmentation();
		tokeniseProcessor = new Tokenisation();
		phraseProcessor = new PhraseExtraction();
		ngramProcessor = new NgramExtraction();
		posProcessor = new PosTagging();
		lemmaProcessor = new Lemmatisation();
		stemProcessor = new Stemming();
		depProcessor = new DependencyParse();
		chunkProcessor = new Chunking();
		saProcessor = new SentimentAnalysis();
		termProcessor = new TermExtraction();
		nerProcessor = new NamedEntityRecognition();
		relationProcessor = new RelationExtraction();
		corefProcessor = new CoreferenceResolution();
	}
	
	/**
	 * Access method for ParagraphSegmentation
	 * 
	 * @param documentText the input document
	 * @return Json syntax of Paragraph split result 
	 */
	
	public String getParagraph(String documentText, String workflowName) {
		return paragraphProcessor.getParagraph(documentText, workflowName);
	}
	
	/**
	 * Access method for SentenceSegmentation
	 * @param documentText the input document
	 * @return Json syntax of Sentence split result
	 */
	public String getSentence(String documentText, String workflowName) {
		return sentenceProcessor.getSentence(documentText, workflowName);
	}
	
	/**
	 * Access method for Tokenisation
	 * @param documentText the input document
	 * @return Json syntax of tokenised result
	 */
	public String getTokenise(String documentText, String workflowName) {
		return tokeniseProcessor.getToken(documentText, workflowName);
	}
	
	/**
	 * Access method for PhraseExtraction
	 * @param documentText the input document
	 * @return Json syntax of Phrase extracted result
	 */
	public String getPhrase(String documentText, String workflowName) {
		return phraseProcessor.getPhrase(documentText, workflowName);
	}
	
	/**
	 * Access method for NgramExtraction
	 * @param documentText the input document
	 * @return Json syntax of Ngram result
	 */
	public String getNgram(String documentText, String workflowName) {
		return ngramProcessor.getNgram(documentText, workflowName);
	}
	
	/**
	 * Access method for POS-tagging
	 * @param documentText the input document
	 * @return Json syntax of POS result
	 */
	public String getPos(String documentText, String workflowName) {
		return posProcessor.getPos(documentText, workflowName);
	}
	
	/**
	 * Access method for Lemmatisation
	 * @param documentText the input document
	 * @return Json syntax of Lemma result
	 */
	public String getLemma(String documentText, String workflowName) {
		return lemmaProcessor.getLemma(documentText, workflowName);
	}
	
	/**
	 * Access method for Stemming
	 * @param documentText the input document
	 * @return Json syntax of stem result
	 */
	public String getStemming(String documentText, String workflowName) {
		return stemProcessor.getStem(documentText, workflowName);
	}
	
	/**
	 * Access method for Dependency Parsing
	 * @param documentText the input document
	 * @return Json syntax of dependency parse result
	 */
	public String getDependency(String documentText, String workflowName) {
		return depProcessor.getDependency(documentText, workflowName);
	}
	
	/**
	 * Access method for Chunking
	 * @param documentText the input document
	 * @return Json syntax of chunking result
	 * @throws Exception could return initialization error
	 */
	public String getChunking(String documentText, String workflowName) throws Exception {
		return chunkProcessor.getChunking(documentText, workflowName);
	}
	
	/**
	 * Access method for Sentiment Analysis
	 * @param documentText the input document
	 * @return Json syntax of Sentiment result
	 */
	public String getSentiment(String documentText, String workflowName) {
		return saProcessor.getSentiment(documentText, workflowName);
	}
	
	/**
	 * Access method for TermExtraction
	 * @param documentText the input document
	 * @return Json syntax of Term-score result
	 */
	public String getTerm(String documentText, String workflowName) {
		return termProcessor.getTerm(documentText, workflowName);
	}
	
	/**
	 * Access method for Named-Entity Recognition
	 * @param documentText the input document
	 * @return Json syntax of NER result
	 */
	public String getNer(String documentText, String workflowName) {
		return nerProcessor.getNer(documentText, workflowName);
	}
	
	/**
	 * Access method for RelationExtraction
	 * @param documentText the input document
	 * @return Json syntax of Relation result
	 */
	public String getRelation(String documentText, String workflowName) {
		return relationProcessor.getRelation(documentText, workflowName);
	}
	
	/**
	 * Access method for Coreference Resolution
	 * @param documentText the input document
	 * @return Json syntax of Coreference result
	 */
	public String getCoreference(String documentText, String workflowName) {
		return corefProcessor.getCoreference(documentText, workflowName);
	}

}

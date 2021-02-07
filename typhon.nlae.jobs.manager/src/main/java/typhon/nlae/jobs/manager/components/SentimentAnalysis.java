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

package typhon.nlae.jobs.manager.components;

import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SentimentAnalysis {

	private StanfordCoreNLP pipeline;
	private Annotation annotation;
	
	public SentimentAnalysis() {
		try {
			Properties props = new Properties();
			props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
			props.setProperty("parse.binaryTrees", "true");
			props.setProperty("enforceRequirements", "false");
			pipeline = new StanfordCoreNLP(props);
		} catch (Exception e) {
			System.out.println("Error in initialization");
			e.printStackTrace();
		}	
	}
	
	public String getSentiment(String input, String workflowName) {
		int sentenceSentiment = 0;
        int count = 0;
        double overallSentiment = 0;
        int tmpScore = 0;
        String result = "";
        try {
        	annotation = pipeline.process(input);
        	Tree tree = null;
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            	tmpScore = 0;
            	tree =  sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            	tmpScore = RNNCoreAnnotations.getPredictedClass(tree);
            	if(tmpScore == 0)
            		sentenceSentiment = sentenceSentiment - 2;
            	else if(tmpScore == 1)
            		sentenceSentiment = sentenceSentiment - 1;
            	else
            		sentenceSentiment = sentenceSentiment + tmpScore + 1; 
            	
            	if(tmpScore != 2)
            		count++;
            }
            if(count>0)
            	overallSentiment = Math.ceil((double)sentenceSentiment/count);
            
            String sentimentLabel = "";
            switch ((int)overallSentiment) {
            case 0:
              sentimentLabel = "Very Negative";
              break;
            case 1:
              sentimentLabel = "Negative";
              break;
            case 2:
            	sentimentLabel = "Neutral";
              break;
            case 3:
            	sentimentLabel = "Postive";
              break;
            case 4:
            	sentimentLabel = "Very Positive";
              break;
            }
            
			result = result+"[{\"Sentiment\": " + (int)overallSentiment  + ",\"SentimentLabel\": \"" + sentimentLabel + "\"}],\n";
			
        }catch(Exception e) {
        	System.out.println("Excpetion occurred while performing Sentiment Analysis Task : "+e.getMessage());
        }
		return result;
	}
}

# Natural Language Analytics Engine : REST API

REST API for NLAE to communicate with Typhon.

## Features

There are 3 Endpoints:

- **Typhon Endpoints**:
  - **/processText** : send an object to be processed by the Text Analytics Engine 
  - **/queryTextAnalytics** : request entities that have been processed by the Text Analytics Engine 
  - **/deleteDocument** : deletes object (document) from the Text Analytics Engine store.

## Models

The models associated with the **Endpoints** are presented below.

### Process

```json
{
        "id": "12345",
        "entityType": "review",
        "fieldName": "comment",
        "text": "text to be processed",
        "nlpFeatures": [
            "sentimentanalysis"
        ],
        "workflowNames": [
            "workflow1"
        ]
    }
```

### Query

```json
{
        "entityType" : "review",
        "fieldName" : "comment",
        "nlpExpression" : { 
            "from": { 
                "entity": "Review", 
                "named": "r" 
            }, 
            "with": [{ 
                "path": "r.text.SentimentAnaylsis", 
                "wflow": "wflow1" 
            }, { 
                "path": "r.text.NamedEntityRecognition", 
                "wflow": "wflow2" 
            }], 
            "select": ["r.@id", "r.text.SentimentAnalysis.Sentiment", "r.text.NamedEntityRecognition.NamedEntity"], 
            "where": [{}] //WHERE Clause* 
        }
    }
```
* The "where" property allows to define different types of sql where clauses as follows:
1. Single WHERE condition: [SQL Example: WHERE r.text.SentimentAnalysis.Sentiment > 1]

```json
    "where": [
      {
        "query": [
          {
            "op": ">",
            "lhs": {
              "attr": "r.text.SentimentAnalysis.Sentiment"
            },
            "rhs": {
              "lit": 1,
              "type": "int"
            }
          }
        ]}
  ]
```
2. Multiple WHERE conditions: [SQL Example: WHERE r.text.SentimentAnalysis.Sentiment > 1 AND r.text.SentimentAnalysis.Sentiment < 4]

```json
"where": [{     
        "query": [{
            "op": ">",
            "lhs": {
                "attr": "r.text.SentimentAnalysis.Sentiment"
            },
            "rhs": {
                "lit": 1,
                "type": "int"
            }
        }]
    }, {
        "multiCondition": { 
            "multiConditionOp": "AND"
        }
    }, {
        "query": [{     
            "op": "<",
            "lhs": {
                "attr": "r.text.SentimentAnalysis.Sentiment"
            },
            "rhs": {
                "lit": 4,
                "type": "int"
            }
        }]
    }]
```
3. Compound WHERE conditions: [SQL Example: (WHERE r.text.SentimentAnalysis.Sentiment > 1 OR r.text.SentimentAnalysis.Sentiment < 3)]

```json
"where": [
      {
        "query": [
          {
            "op": ">",
            "lhs": {
              "attr": "r.text.SentimentAnalysis.Sentiment"
            },
            "rhs": {
              "lit": 1,
              "type": "int"
            },
            "compoundConditionOp": "OR"
          },
          {
            "op": "<",
            "lhs": {
              "attr": "r.text.SentimentAnalysis.Sentiment"
            },
            "rhs": {
              "lit": 3,
              "type": "int"
            }
          }
        ]}
    ]
```
4. Multiple WHERE conditions including Compound conditions: [SQL Example: (WHERE r.text.SentimentAnalysis.Sentiment > 1 AND r.text.SentimentAnalysis.Sentiment < 3) OR r.text.NamedEntityRecognition.NamedEntity in ('TITLE','PERSON')]

```json
"where": [
      {
        "query": [
          {
            "op": ">",
            "lhs": {
              "attr": "r.text.SentimentAnalysis.Sentiment"
            },
            "rhs": {
              "lit": 1,
              "type": "int"
            },
            "compoundConditionOp": "AND"
          },
          {
            "op": "<",
            "lhs": {
              "attr": "r.text.SentimentAnalysis.Sentiment"
            },
            "rhs": {
              "lit": 3,
              "type": "int"
            }
          }

        ]}, {
        "multiCondition": { 
            "multiConditionOp": "OR"
            }
        },
        {
        "query": [
          {
            "op": "in",
            "lhs": {
              "attr": "r.text.NamedEntityRecognition.NamedEntity"
            },
            "rhs": {
              "lit": "('TITLE', 'PERSON')",
              "type": "string"
            }
          }
        ]}
    ]
```
### Delete

```json
 {
        "id": "12345",
        "entityType": "review"
 }
```



## Nlp Task Types

Below is the current list of `NlpTaskTypes` supported in Typhon.

- ParagraphSegmentation

- SentenceSegmentation

- Tokenisation

- PhraseExtraction

- nGramExtraction

- POSTagging

- Lemmatisation

- Stemming

- DependencyParsing

- Chunking

- SentimentAnalysis

- TermExtraction

- NamedEntityRecognition

- RelationExtraction

- CoreferenceResolution

## Use Case specific Workflow Names
- ATB Weather NER -> "atb_weather_ner"
- AlphaBank NER   -> "alpha_bank_ner"

## Documentation

- __**UI Endpoint Documentation**__ :
  More inforamtion see `<hostname>:port/swagger-ui.html` when deployed.

- __**Open API Specification**__ : More information see `<hostname>:port/v2/api-docs` when deployed.

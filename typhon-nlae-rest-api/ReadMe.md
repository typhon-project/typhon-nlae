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
            "sentimentanalysis",
            "namedentityrecognition",
            ....
        ],
        "workflowNames": [
            "workflow1",
            "workflow2",
            ....
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
            "where": [{ 
                "op": ">", 
                "lhs": { 
                    "attr": "r.text.SentimentAnalysis.Sentiment" 
                }, 
            "rhs": { 
                    "lit": "1", 
                    "type": "int" 
                } 
            }, { 
                "op": "in", 
                "rhs": { 
                    "lit": "('TITLE','MISC')", 
                    "type": "String" 
                }, 
                "lhs": { 
                    "attr": "r.text.NamedEntityRecognition.NamedEntity" 
                } 
            }] 
        }
    }
```
* For more than 2 queries add a "query-op" property between the query conditions:

```json
....
"where": [{
                    "op": ">",
                    "lhs": {
                        "attr": "r.text.SentimentAnalysis.Sentiment"
                    },
                    "rhs": {
                        "lit": "1",
                        "type": "int"
                    }
                }, {
                    "query-op": "AND"
                },
                {
                    "op": "in",
                    "rhs": {
                        "lit": "('TITLE','ORGANIZATION')",
                        "type": "String"
                    },
                    "lhs": {
                        "attr": "r.text.NamedEntityRecognition.NamedEntity"
                    }
                }, {
                    "query-op": "OR"
                },
                {
                    "op": "in",
                    "rhs": {
                        "lit": "('Doctor','Tesla')",
                        "type": "String"
                    },
                    "lhs": {
                        "attr": "r.text.NamedEntityRecognition.WordToken"
                    }
                }
            ]
.....
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

- TextClassification

- TopicModelling

- TermExtraction

- NamedEntityRecognition

- RelationExtraction

- CoreferenceResolution

  

## Documentation

- __**UI Endpoint Documentation**__ :
  More inforamtion see `<hostname>:port/swagger-ui.html` when deployed.

- __**Open API Specification**__ : More information see `<hostname>:port/v2/api-docs` when deployed.

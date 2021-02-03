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
    "from": {
        "entity": "Company",
        "named": "c"
    },
    "with": [{
        "path": "c.mission.SentimentAnalysis",
        "wflow": "eng_spa"
    }],
    "select": ["c.@id", "c.mission.SentimentAnalysis.Sentiment"],
    "where": {
        "binaryExpression": {
            "op": "&&",
            "lhs": {
                "binaryExpression": {
                    "op": ">",
                    "lhs": {
                        "attribute": {
                            "path": "c.mission.SentimentAnalysis.Sentiment"
                        }
                    },
                    "rhs": {
                        "literal": {
                            "value": 1,
                            "type": "int"
                        }
                    }
                }
            },
            "rhs": {
                "binaryExpression": {
                    "op": "<",
                    "lhs": {
                        "attribute": {
                            "path": "c.mission.SentimentAnalysis.Sentiment"
                        }
                    },
                    "rhs": {
                        "literal": {
                            "value": 4,
                            "type": "int"
                        }
                    }
                }
            }
        }
    }
}
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

# Natural Language Analytics Engine : Simulation

Simulation of the NLAE in Typhon.

## Features

There are 4 Endpoints group into two categories:

- **Typhon Endpoints**:
  - **/process** : send an object to be processed by the Text Analytics Engine 
  - **/query** : request entities that have been processed by the Text Analytics Engine 
  - **/delete** : deletes object (document) from the Text Analytics Engine store.

## Models

The models associated with the **Endpoints** are presented below.

### Process

```json
{
  "entityType": "type : String",
  "fieldName": "type : String",
  "id": "type : String",
  "nlpFeatures": "type : [String]",
  "text": "type : String"
}
```

### Query

```json
{
  "entityType": "type : String",
  "fieldName": "type : String",
  "nlpExpression": "type : NlpExpression"
}
```

### Delete

```json
{
  "id": "type : String"
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


## Build

To build the api package navigate to `.../Simulation/simulation` directory and run the following command.

```
mvn clean package spring-boot:repackage
```

If successful the command will generate a `NLAE-Simulation.jar` within the `.../Simulation/simulation/target/` directory.

## Deployment

Ensure you have `docker` and `docker-compose` installed on your system and you have increased the `vm.max_map_count` to at least `262144`. For information on how to increase `vm.max_map_count` on your system visit [link.](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#_set_vm_max_map_count_to_at_least_262144)



**Docker-compose.yml example**
Below is an example docker-compose used to deploy the simulation:

```yml
version: "2.2"

services:

  elasticsearch:
    container_name: elasticsearch
    restart: always
    environment:
      - cluster.name=docker-cluster
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
    image: "docker.elastic.co/elasticsearch/elasticsearch:6.8.1"
    networks:
      nlae:
        aliases:
          - elasticsearch
    ports:
      - "9200:9200"
    ulimits:
      memlock:
        hard: -1
        soft: -1
    volumes:
      - "esdata1:/usr/share/elasticsearch/data"

  simulation:
    image: "danny2097/nlae-simulation:latest"
    container_name: simulation
    depends_on:
      - elasticsearch
    networks:
      nlae:
        aliases:
          - nlp
    ports:
      - "8080:8080"


volumes:
  esdata1:
    driver: local

networks:
  nlae:
    name: nlae
    driver: bridge

```

---

Simply create a `docker-compose.yml` file on your system. Using terminal navigate to the directory where the `docker-compose.yml` file is stored and  run the one of the following commands:

For immediate console output from the containers

```
docker-compose up --build --force-recreate
```

For detached mode (with no immediate console output)

```
docker-compose up --build --force-recreate -d
```


The product of this will result in the creation of two containers with the names `simulation` and `elasticsearch`


**NOTE :** Please allow for upto **40 - 60 seconds** for the Elasticsearch cluster and Simulation Api to load and configure themselves before using. This is a result of a delay in the exposure of the host and port that Elasticsearch is bound to when cluster is starting. This then requires the API to essentially wait so it can create a client.   

## Contact

Dan at `campbeld@edgehill.ac.uk`

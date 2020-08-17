# typhon-nlae
### Natural Language Analysis Engine
The NLAE provides a Text Processing backend for the Typhon project. Below is an overview of the main components of NLAE: 

- **NLAE Developed Components**:
  - **REST API** : Communication end point for QL
  - **Job Manager** : Apache Flink Stream Processing Application to perform text processing tasks
- **Other NLAE Components**:
  - **Elasticsearch** : Stores *processed* data
  - **RabbitMQ** : Messaging broker to receive data from REST API and send it to the Job Manager

# NLAE Deployment
  - NLAE is deployed as a Docker Stack on a Docker Swarm with at least 1 *Manager* and 1 *Worker* node
  - NLAE Deployment folder contains the Docker Compose file and the deployment script that deploys the services for the NLAE and starts the Flink job
  - Configurations for the Flink cluster can be updated using the */conf/flink-conf.yaml* file
  - Configurations for the services can be updated using */NLAE-Deployment/nlae-compose.yml file

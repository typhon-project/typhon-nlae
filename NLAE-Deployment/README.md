# NLAE Deployment

## Prerequisites:

1. The NLAE can be run on a single machine with a minimum of 8GB RAM (This setup can only support 1 replica for the Task Manager).  

2. Docker Swarm configured with (at least) 1 MANAGER node.

3. Flink Job Manager runs on a Manager node and requires at least 4GB RAM

4. Flink Task Manager(s) run on Worker node(s) only (in production) and on Manager node (for testing) and require at least 4GB RAM. This allocation can be edited in the nlae-compose.yml file.

5. The remaining 3 services i.e. Elasticsearch, RabbitMQ and REST API are deployed on any available node. 

6. Create a *models* folder on the Master node and configure a shared file system (e.g. NFS) for the cluster. Share/mount */path/to/models* so that this folder is visible to all nodes in the cluster. This folder will include the custom trained classifier models.

## Configurable Entities:

The following values in the nlae-compose.yml can be edited to configure the job manager:

1. External Port for REST API. Since REST API is the only service visible outside the NLAE Docker Swarm, therefore the Ports for the rest of the services don’t need to be updated as these will only be visible within the NLAE cluster.

2. Flink Job Manager properties in nlae-compose.yml file. (overrides properties set in /conf/flink-conf.yaml).

    a. “FLINK_PROPERTIES=prop1,prop2…”
        
        1. jobmanager.heap.size <-- update according to RAM available/Job Manager
        
        2. taskmanager.numberOfTaskSlots <-- update according to number of Task Managers (replicas) deployed

    b. deploy->replicas <-- number of Job Manager replicas to deploy (default 1)
    
    c. Update */path/to/models/* under volumes

3. Flink Task Manager properties in nlae-compose.yml: (overrides properties set in /conf/flink-conf.yaml).

    a. “FLINK_PROPERTIES=prop1,prop2…”

        1. taskmanager.heap.size

        2. parallelism.default

    b. deploy->replicas <-- number of Task Manager (Worker Nodes) replicas to deploy (default 2) 

    c. Update */path/to/models/* under volumes


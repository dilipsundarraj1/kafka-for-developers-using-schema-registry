# Kafka For Developers - Data Contracts using Schema Registry

This repository has the content to interact with Kafka using AVRO and Schema Registry.

## Set up Kafka Environment using Docker

- This should set up the Zookeeper and Kafka Broker in your local environment

```aidl
docker-compose up
```

### Verify the Local Kafka Environment

- Run this below command

```
docker ps
```

- You should be below containers up and running in local

```
CONTAINER ID   IMAGE                                   COMMAND                  CREATED          STATUS          PORTS                                            NAMES
fd305f78339a   confluentinc/cp-schema-registry:7.1.0   "/etc/confluent/dock…"   49 seconds ago   Up 48 seconds   0.0.0.0:8081->8081/tcp                           schema-registry
fb28f7f91b0e   confluentinc/cp-server:7.1.0            "/etc/confluent/dock…"   50 seconds ago   Up 49 seconds   0.0.0.0:9092->9092/tcp, 0.0.0.0:9101->9101/tcp   broker
d00a0f845a45   confluentinc/cp-zookeeper:7.1.0         "/etc/confluent/dock…"   50 seconds ago   Up 49 seconds   2888/tcp, 0.0.0.0:2181->2181/tcp, 3888/tcp       zookeeper
```

### Interacting with Kafka

#### Produce Messages

- This  command should take care of logging in to the Kafka container.

```
docker exec -it broker bash
```

- Command to produce messages in to the Kafka topic.

```
kafka-console-producer --broker-list localhost:9092 --topic test-topic
```

#### Consume Messages

- This  command should take care of logging in to the Kafka container.

```
docker exec -it broker bash
```
- Command to produce messages in to the Kafka topic.

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic test-topic
```

### Interacting with Kafka using AVRO Records

#### Produce AVRO Messages

- This  command should take care of logging in to the Schema Registry container.

```
docker exec -it schema-registry bash
```

- Run the **kafka-avro-console-producer** with the Schema

```
kafka-avro-console-producer --broker-list broker:29092 --topic greetings --property value.schema='{"type": "record","name":"Greeting","fields": [{"name": "greeting","type": "string"}]}'
```

- Publish the **Greeting** message

```
{"greeting": "Good Morning!, AVRO"}
```

```
{"greeting": "Good Evening!, AVRO"}
```

```
{"greeting": "Good Night!, AVRO"}
```

### Consume AVRO Messages

- This  command should take care of logging in to the Schema Registry container.

```
docker exec -it schema-registry bash

```

- Run the kafka-avro-console-consumer

```
kafka-avro-console-consumer --bootstrap-server broker:29092 --topic greetings --from-beginning
```

## Data Evolution using Schema Registry

### Backward Compatibility

Let's follow the steps given in this section :

1. Let's make sure version **1.0** has **store** field in it
2. Delete the **store** field and update the version in the **schemas** module to **2.0**, and publish a 2.0 to our local maven repo.
   - gradle
     - Execute clean gradle task
     - Execute the **generateAvro** gradle task
     - Execute **publishToMavenLocal** gradle task
   - maven
     - Execute clean task
     - Execute **install**  task
3. Update the consumer to use schemas module **2.0** version
4. publish the coffee-order record still with version 1.0
    - Consumer using 2.0 version should consume fine without any issues
5. Update the producer to use schemas module **2.0** version  
6. publish the coffee-order record with schemas  module **2.0** version
     - Consumer should consume fine without any issues
7. The subject in **coffee-orders-sr-value** should have a newer version **2.0**
   1. Only the producer can create newer versions in **Schema Registry**.


### Forward Compatibility

1) Add the new field **pick_up**
   1) This is going to be of type **enum** with two values
      1) IN_STORE
      2) CURBSIDE
2) Add the **Store** field too
3) Build 3.0 and publish the new version of AVRO classes
   1) Change the version in schemas module to **3.0**
   2) gradle
      1) Execute clean gradle task
      2) Execute the **generateAvro** gradle task
      3) Execute **publishToMavenLocal** gradle task
   3) maven
      1) Execute clean task
      2) Execute **install**  task
4) Update the producer to use **schemas** module new version **3.0** and publish the message
   1) Error in producer observed and then go ahead update the compatibility to **FORWARD**
   2) Publish the record with **pick_up** field
5) Consumer should process the records fine with **schemas** module version 2.0
6) Upgrade the consumer's **schemas** module version 3.0
   1) Publish the record with **3.0** and the consumer should process the records successfully.
7) The subject in **coffee-orders-sr-value** should have a newer version **3.0**

### FULL Compatibility
1) Add the new optional field **nickName1**
   1) This is an optional field named which represents other name
2) Build 4.0 and publish the new version of AVRO classes
    1) Change the version in schemas module to **4.0**
    2) gradle
        1) Execute clean gradle task
        2) Execute the **generateAvro** gradle task
        3) Execute **publishToMavenLocal** gradle task
    3) maven
        1) Execute clean task
        2) Execute **install**  task
3) Update the producer to use **schemas** module new version **4.0** and publish the message
   1) **Note:** We can either upgrade the producer or consumer first.
4) Let's go ahead and update the Compatibility Level to **FULL**
5) Upgrade the producer version to **4.0** and publish the record with the new **schemas** module version.
   1) This should register a newer version **4.0** for the subject **coffee-orders-sr-value** in the schema registry
6) The consumer that uses the **3.0** version should process the records with **4.0** with no issues.
7) Upgrade the consumer's **schemas** module version 4.0
    1) Publish the record with **4.0** and the consumer should process the records successfully.
8) Upgrade the producer's **schemas** module version 3.0
    2) Publish the record with **3.0** and the consumer should process the records successfully.

### NONE Compatibility
1) Rename the field from **name** to **full_name**
2) Build 5.0 and publish the new version of AVRO classes
    1) Change the version in schemas module to **5.0**
    2) gradle
        1) Execute clean gradle task
        2) Execute the **generateAvro** gradle task
        3) Execute **publishToMavenLocal** gradle task
    3) maven
        1) Execute clean task
        2) Execute **install**  task
3) Update the producer to use **schemas** module new version **5.0** and the update the consumer to **5.0**  
4) Start the consumer and publish the message
   1) Error is observed, update the compatibility level to **NONE**
5) Publish the message and the message will be published successfully
6) Consumer should read the message successfully
7) Now lets revert the producer version to **4.0** and then publish the message
8) Consumer will observer an error with the missing field **full_name**


### Handling Failures in Schema Incompatibility

1) Let's start with the clean slate. Delete the **schemas** folder from the **.m2/repository/com/learnavro**
   1) This will also delete all the versions that we have been using so far.
   2) Change the version to 1.0 in **schemas** build.gradle file.
2) Build 1.0 and publish the new version of AVRO classes
    1) Change the version in schemas module to **1.0**
    2) gradle
        1) Execute clean gradle task
        2) Execute the **generateAvro** gradle task
        3) Execute **publishToMavenLocal** gradle task
    3) maven
        1) Execute clean task
        2) Execute **install**  task
3) Restart the kafka set up by running the below commands
   1) docker-compose dowm
   2) docker-compose up
   3) This will clear out all the different schemas that was registered and we will have a clean slate at this point.
4) Update the consumer to use the **schemas** module version to 1.0
   1) Start the consumer and the consumer should start up successfully
5) Update the producer to use the **schemas** module version to 1.0
   1) Publish a new message and this should register the new schema with the version 1.0
   2) Consumer processed the message successfully
6) Delete the pick_up Field and update the **schemas** version to 2.0
7) Build 2.0 and publish the new version of AVRO classes
    1) Change the version in schemas module to **2.0**
    2) gradle
        1) Execute clean gradle task
        2) Execute the **generateAvro** gradle task
        3) Execute **publishToMavenLocal** gradle task
    3) maven
        1) Execute clean task
        2) Execute **install**  task
8) Update the consumer and producer to use schemas module **2.0** version and the restart both the apps
9) Publish the message from the producer and this should register the new schema version 2.0
   1) Consumer should process the record successfully
10) Add the new field named pick_up_type
    1) This field is very similar to pick_up
    2) Build 3.0 and publish the new version of AVRO classes
       1) Change the version in schemas module to **3.0**
       2) gradle
           1) Execute clean gradle task
           2) Execute the **generateAvro** gradle task
           3) Execute **publishToMavenLocal** gradle task
       3) maven
           1) Execute clean task
           2) Execute **install**  task
11) Change the compatibility to **FORWARD** using the INSOMNIA collection since we are adding a new field
    1) Update the consumer and producer to use schemas module **2.0** version and the restart both the apps
12)

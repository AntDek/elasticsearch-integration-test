## elasticsearch-integration-test

This application shows how to run ElasticSearch integration test over local or remote server.

**Test Server Implementation**
[`folder`](https://github.com/AntDek/elasticsearch-integration-test/blob/master/src/main/java/cz/adek/elasticsearch/test/)


**Test Example**
[`ExampleIntegrationTest`](https://github.com/AntDek/elasticsearch-integration-test/blob/master/src/test/java/cz/adek/elasticsearch/test/ExampleIntegrationTest.java)

**Run Test**
```
mvn clean test
```

#### The Local Server Configuration
The default configuration uses a in-memory store type. For a large number of insert operations insure that you have sufficient memory on your test system otherwise you will run out of memory.
Local server can be configured here: [`LocalNode`](https://github.com/AntDek/elasticsearch-integration-test/blob/master/src/main/java/cz/adek/elasticsearch/test/LocalNode.java)


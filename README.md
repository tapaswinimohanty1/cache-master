SC Coding Challange
================================
The project demonstrates below aspects:
1. Spring AOP.
2. In-memory to maintain keep count of number of hits
8. Unit testing
9. Integration testing.


The project Hierarchy
=======================
- util - contains all required classes for inmemory cache.
- test - contains all junit test cases for respective function.

Running project
======================
just run ./mvn clean install to build the project

Please find my understanding below.
 
 1>created an cache which is Map of linkedList nodes.Each node contains a key and Value and forms a doubly linkedlist.
 2>Run all Junit test cases for add,remove,get
 3>Regular time interval,take a snapshot of the cache to a file.In case system crashes due to any un aviodable scenario, we can reload these values from the 
 persisted file.
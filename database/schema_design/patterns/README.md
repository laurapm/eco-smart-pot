# Introduction

A Mongo Database has to take into account several factors:
- **DB Objective**
- **Relationships**
- **Patterns** 

## Database Objective

As every IoT application, the data read from the devices is vastly greater than
the data the users request of the information that is obtained from it. 

That being said, it is clear that the main objective is to reduce the load of 
the write operations as result of the parameters obtained from the Ecø devices.
That should be one of the main aspects to take into account in order to create 
a good model for the database. 

_Logically_, space occupied in disk should also be another aspect to look at. 
As well as the development and maintenance difficulty of the code.

In the next sections it will be discussed the design decisions carried out by
the developers of this project.

## Relationships

Despite MongoDB being a document based database, it still has relationships
among collections. To create a relationship between two documents, it is 
crucial to decide whether to embed or link - it may affect performance.

#### One-to-One

#### One-to-Many

#### Many-to-Many

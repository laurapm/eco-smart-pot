# Introduction

A Mongo Database has to take into account several factors:
- DB Objective
- Relationships
- Patterns 

## Database Objective

As every IoT application, the data read from the devices is bastly greater than
the data the users request of the information that is obtained from it. 

That being said, it is clear that the main objective is to reduce the load of 
the write operations as result of the parameters obtained from the Ecø devices.

In order to achive that, even though some information should _logically_ be 
stored in just one collection, it will be replicated accross other collections,
or referenced; or even separated in different collections.

Also, to avoid documents occuping too much space, or having to join different
collections (not recommended in MongoDB) some other data will also be 
encapsulated in collections that are not entirely related to the data (such as 
the `owner` and `device` collections).

## Relationships

#### One-to-One

#### One-to-Many

#### Many-to-Many

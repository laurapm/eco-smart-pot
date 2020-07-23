# CRUD Operations

MongoDB accepts different type of operations, by the name of 
[CRUD](https://docs.mongodb.com/manual/crud/).

There are several forms to operate with information in MongoDB. One of them is 
to use [MongoDB Compass](https://www.mongodb.com/products/compass) or by the 
aforementioned [Mongo Shell](https://docs.mongodb.com/manual/mongo/). The first 
of them does not belong to the scope of this manual, so only the Mongo Shell 
method will be explained.

_**Disclaimer:** Should be taken into account that the information here 
displayed does not refer to any language driver, but for the mongo shell 
method._

## Read Operations

Retrieve information documents (information) from a given collection. Can use
two methods: `findOne()` or simply `find()`. The first method retrieves only 
one document (the first encountered) that matches a certain condition; the 
second operation finds any document matching a condition.

The query uses a JSON document as filter. It can contain the parameters:
- `query` (_document_): _Optional_. Specifies query selection criteria using 
[query operators](https://docs.mongodb.com/manual/reference/operator/).
- `projection` (_document_): _Optional_. Specifies the fields to return using 
[projection operators](https://docs.mongodb.com/manual/reference/operator/projection/). 
Omit this parameter to return all fields in the matching document.

## Create Operations

After being connected, two different operations can be used: `insertOne` and 
`insertMany`. Their names are self-explanatory, not much detail is needed 
regarding that point.

In both cases, if the `_id` is not specified for a document, MongoDB will 
automatically asign one. Meaning, writing an `_id` manually for each document
inserted is not mandatory.

Also, if the _collection_ where the document is inserted does not exists, 
MongoDB will automatically create one, just as with the `_id`. Therefore, a 
collection does not need to be created before a document insertion.

They both return:
- `acknowledged` (_boolean_): **true** if the operation ran with write concern 
or **false** if write concern was disabled.
- `insertedId` (_ObjectId_): the **_id** value of the inserted document.

**db.collection.insertOne()**

Takes 2 parameters:
- `document` (_document_): A JSON document to insert into the specified 
collection. 
- `writeConcern` (_document_): _Optional_. A document expressing the 
[write concern](https://docs.mongodb.com/manual/reference/write-concern/). Omit
to use the default write concern.

**db.collection.insertMany()**

Takes 3 parameters:
- `document` (_document_): An array of JSON documents to insert into the 
specified collection. 
- `writeConcern` (_document_): _Optional_. A document expressing the 
[write concern](https://docs.mongodb.com/manual/reference/write-concern/). Omit
to use the default write concern.
- `ordered` (_boolean_): _Optional_. A boolean specifying whether the 
[mongod](https://docs.mongodb.com/manual/reference/program/mongod/#bin.mongod)
instance should perform an ordered or unordered insert. Defaults to **true**. 
Executing an 
[ordered](https://docs.mongodb.com/manual/reference/method/db.collection.initializeOrderedBulkOp/#db.collection.initializeOrderedBulkOp) 
list of operations on a sharded collection will generally be slower than executing an 
[unordered](https://docs.mongodb.com/manual/reference/method/db.collection.initializeUnorderedBulkOp/#db.collection.initializeUnorderedBulkOp) 
list since with an ordered list, each operation must wait for the previous 
operation to finish. In **ordered** operations, if an error occurs during the 
processing of one of the write operations, MongoDB will return without processing any remaining write operations in the list. On the other hand, in 
**unordered** if an error occurs during the processing of one of the write 
operations, MongoDB will continue to process remaining write operations in the 
list.

It can also be used a bulk write operation to insert documents into the DB. 
Please refer to the section **Bulk Operations** in this same page.

## Update Operations

## Delete Operations

## Bulk Operations
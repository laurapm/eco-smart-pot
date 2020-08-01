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

**db.collection.findOne()**

The query uses a JSON document as filter. It can contain the parameters:
- `query` (_document_): _Optional_. Specifies query selection criteria using 
[query operators](https://docs.mongodb.com/manual/reference/operator/).
- `projection` (_document_): _Optional_. Specifies the fields to return using 
[projection operators](https://docs.mongodb.com/manual/reference/operator/projection/). 
Omit this parameter to return all fields in the matching document.

It returns a single document that satisfies the criteria specified as the first 
argument to this method.

Although similar to the 
[find()](https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find) 
method, the 
[findOne()](https://docs.mongodb.com/manual/reference/method/db.collection.findOne/#db.collection.findOne)
method returns a document rather than a cursor.

**db.collection.find()**

Just as before, the query uses a JSON document. It can be used with the 
following parameters:
- `query` (_document_): _Optional_. Specifies query selection criteria using 
[query operators](https://docs.mongodb.com/manual/reference/operator/).
To return all documents in a collection, omit this parameter or pass an empty 
document `({})`.
- `projection` (_document_): _Optional_. Specifies the fields to return using 
[projection operators](https://docs.mongodb.com/manual/reference/operator/projection/). 
Omit this parameter to return all fields in the matching document.

It returns a 
[cursor](https://docs.mongodb.com/manual/reference/glossary/#term-cursor) to 
the documents that match the query criteria. When the 
[find()](https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find) 
method “returns documents,” the method is actually returning a cursor to the documents.

## Create Operations

After being connected, two different operations can be used: `insertOne` and
`insertMany`. Their names are self-explanatory, not much detail is needed 
regarding that point.

In both cases, if the `_id` is not specified for a document, MongoDB will 
automatically assign one. Meaning, writing an `_id` manually for each document
inserted is not mandatory.

Also, if the _collection_ where the document is inserted does not exists, 
MongoDB will automatically create one, just as with the `_id`. Therefore, a 
collection does not need to be created before a document insertion.

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

---

They both return:
- `acknowledged` (_boolean_): **true** if the operation ran with write concern 
or **false** if write concern was disabled.
- `insertedId` (_ObjectId_): the **_id** value of the inserted document.

## Update Operations

Written information can also be altered. In order to do that, MongoDB counts with 3 
operations: `updataOne`, `updateMany`, `replaceOne`.

**db.collection.updateOne()**

Takes the following parameters:
- `filter` (_document_): The selection criteria for the update. The same 
[query selectors](https://docs.mongodb.com/manual/reference/operator/query/#query-selectors) 
as in the 
[find()](https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find) 
method are available. Specify an empty document `{ }` to update the first 
document returned in the collection.
- `update` (_document/pipeline_): The modification to apply. Can be one of the 
following:
  - `Update document`: Contains only 
  [update operator expressions](https://docs.mongodb.com/manual/reference/operator/update/#id1).
  - `Aggregation pipeline`: Contains only the following aggregation stages:
    - [$addFields](https://docs.mongodb.com/manual/reference/operator/aggregation/addFields/#pipe._S_addFields) 
    and its alias 
    [$set](https://docs.mongodb.com/manual/reference/operator/aggregation/set/#pipe._S_set).
    - [$project](https://docs.mongodb.com/manual/reference/operator/aggregation/project/#pipe._S_project) 
    and its alias 
    [$unset](https://docs.mongodb.com/manual/reference/operator/aggregation/unset/#pipe._S_unset).
    - [$replaceRoot](https://docs.mongodb.com/manual/reference/operator/aggregation/replaceRoot/#pipe._S_replaceRoot) 
    and its alias 
    [$replaceWith](https://docs.mongodb.com/manual/reference/operator/aggregation/replaceWith/#pipe._S_replaceWith).
- `upsert` (_boolan_): _Optional_. When **true**, updateOne() either:
  - Creates a new document if no documents match the `filter`. For more details
  see 
  [upsert behavior](https://docs.mongodb.com/manual/reference/method/db.collection.update/#upsert-behavior).
  - Updates a single document that matches the `filter`.
- `writeConcern` (_document_): _Optional_. A document expressing the 
[write concern](https://docs.mongodb.com/manual/reference/write-concern/). 
Omit to use the default write concern.
- `collation` (_document_): _Optional_. 
[Collation](https://docs.mongodb.com/manual/reference/collation/) allows users
to specify language-specific rules for string comparison, such as rules for 
letter-case and accent marks.
- `arrayFilters` (_array_): _Optional_. An array of filter documents that 
determine which array elements to modify for an update operation on an array 
field.
- `hint` (_document/string_): _Optional_. A document or string that specifies the 
[index](https://docs.mongodb.com/manual/indexes/) to use to support the query 
predicate.

**db.collection.updateMany()**

[updateMany()](https://docs.mongodb.com/manual/reference/method/db.collection.updateMany/#db.collection.updateMany) 
updates all documents matching the `filter` parameter.

- `filter` (_document_): The selection criteria for the update. The same 
[query selectors](https://docs.mongodb.com/manual/reference/operator/query/#query-selectors) 
as in the 
[find()](https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find) 
method are available. Specify an empty document `{ }` to update all documents 
in the collection.
- `update` (_document/pipeline_): The modification to apply. Can be one of the 
following:
  - `Update document`: Contains only 
  [update operator expressions](https://docs.mongodb.com/manual/reference/operator/update/#id1).
  - `Aggregation pipeline`: Contains only the following aggregation stages:
    - [$addFields](https://docs.mongodb.com/manual/reference/operator/aggregation/addFields/#pipe._S_addFields) 
    and its alias 
    [$set](https://docs.mongodb.com/manual/reference/operator/aggregation/set/#pipe._S_set).
    - [$project](https://docs.mongodb.com/manual/reference/operator/aggregation/project/#pipe._S_project) 
    and its alias 
    [$unset](https://docs.mongodb.com/manual/reference/operator/aggregation/unset/#pipe._S_unset).
    - [$replaceRoot](https://docs.mongodb.com/manual/reference/operator/aggregation/replaceRoot/#pipe._S_replaceRoot) 
    and its alias 
    [$replaceWith](https://docs.mongodb.com/manual/reference/operator/aggregation/replaceWith/#pipe._S_replaceWith).
- `upsert` (_boolan_): _Optional_. When **true**, updateMany() either:
  - Creates a new document if no documents match the `filter`. For more details
  see 
  [upsert behavior](https://docs.mongodb.com/manual/reference/method/db.collection.update/#upsert-behavior).
  - Updates a single document that matches the `filter`.
- `writeConcern` (_document_): _Optional_. A document expressing the 
[write concern](https://docs.mongodb.com/manual/reference/write-concern/). 
Omit to use the default write concern.
- `collation` (_document_): _Optional_. 
[Collation](https://docs.mongodb.com/manual/reference/collation/) allows users
to specify language-specific rules for string comparison, such as rules for 
letter-case and accent marks.
- `arrayFilters` (_array_): _Optional_. An array of filter documents that 
determine which array elements to modify for an update operation on an array 
field.
- `hint` (_document/string_): _Optional_. A document or string that specifies the 
[index](https://docs.mongodb.com/manual/indexes/) to use to support the query 
predicate.


**db.collection.replaceOne()**

The function 
[replaceOne()](https://docs.mongodb.com/manual/reference/method/db.collection.replaceOne/#db.collection.replaceOne) 
it replaces the first matching document in the collection that matches the 
`filter`:

- `filter` (_document_): The selection criteria for the update. The same 
[query selectors](https://docs.mongodb.com/manual/reference/operator/query/#query-selectors) 
as in the 
[find()](https://docs.mongodb.com/manual/reference/method/db.collection.find/#db.collection.find) 
method are available. Specify an empty document `{ }` to update all documents 
in the collection.
`replacement` (_document_): The replacement document. Cannot contain 
[update operators](https://docs.mongodb.com/manual/reference/operator/update/).
- `upsert` (_boolan_): _Optional_. When **true**, replaceOne() either:
  - Inserts the document from the `replacement` parameter if no document 
  matches the `filter`.
  - Replaces the document that matches the `filter` with the `replacement` 
  document.
- `writeConcern` (_document_): _Optional_. A document expressing the 
[write concern](https://docs.mongodb.com/manual/reference/write-concern/). 
Omit to use the default write concern.
- `collation` (_document_): _Optional_. 
[Collation](https://docs.mongodb.com/manual/reference/collation/) allows users
to specify language-specific rules for string comparison, such as rules for 
letter-case and accent marks.
- `hint` (_document/string_): _Optional_. A document or string that specifies the 
[index](https://docs.mongodb.com/manual/indexes/) to use to support the query 
predicate.

---

All three operations return a document with the following information:
- `acknowledged` (_boolean_): **true** if the operation ran with 
[write concern](https://docs.mongodb.com/manual/reference/glossary/#term-write-concern)
or **false** if write concern was disabled.
- `matchedCount` (_int_): number of matched documents. 
- `modifiedCount` (_int_): number of modified documents.
- `upsertedId` (_ObjectId_): `_id` for upserted document.

It can also be used a bulk write operation to insert documents into the DB. 
Please refer to the section **Bulk Operations** in this same page.

## Delete Operations

The documents can also be deleted (careful, you will need a backup to recover 
the deleted data). There are two possible operations: `deleteOne` and 
`deleteMany`.

**db.collection.deleteOne()**

[deleteOne](https://docs.mongodb.com/manual/reference/method/db.collection.deleteOne/#db.collection.deleteOne)
removes a single document from a collection. It has the following parameters:
- `filter` (_document_): Specifies deletion criteria using 
[query operators](https://docs.mongodb.com/manual/reference/operator/). Specify
an empty document `{ }` to delete the first document returned in the 
collection.
- `writeConcern`  (_document_): _Optional_. A document expressing the 
[write concern](https://docs.mongodb.com/manual/reference/write-concern/). 
Omit to use the default write concern.
- `collation` (_document_): _Optional_. 
[Collation](https://docs.mongodb.com/manual/reference/collation/) allows users
to specify language-specific rules for string comparison, such as rules for 
letter-case and accent marks.

**db.collection.deleteMany()**

[deleteMany](https://docs.mongodb.com/manual/reference/method/db.collection.deleteMany/#db.collection.deleteMany)
removes all documents that match the `filter` from a collection. Counts with 
the following parameters:
- `filter` (_document_): Specifies deletion criteria using 
[query operators](https://docs.mongodb.com/manual/reference/operator/). To 
delete all documents in a collection, pass in an empty document `({ })`.
- `writeConcern`  (_document_): _Optional_. A document expressing the 
[write concern](https://docs.mongodb.com/manual/reference/write-concern/). 
Omit to use the default write concern.
- `collation` (_document_): _Optional_. 
[Collation](https://docs.mongodb.com/manual/reference/collation/) allows users
to specify language-specific rules for string comparison, such as rules for 
letter-case and accent marks.

---

Both functions return a document containing the same parameters:
- `acknowledged` (_boolean_): **true** if the operation ran with 
[write concern](https://docs.mongodb.com/manual/reference/glossary/#term-write-concern) 
or **false** if write concern was disabled.
- `deletedCount` (_int_): number of deleted documents.

## Bulk Operations

Allows to perform write 
[operations in bulk](https://docs.mongodb.com/manual/core/bulk-write-operations/). 
This, of course, also includes the delete operations. It highly increases 
performance, as making a bulk operations means less round trips between the 
client and the database (refer to the external 
[Bulk Operations in MongoDB](https://medium.com/dbkoda/bulk-operations-in-mongodb-ed49c109d280)
article for more information).

The operations inside the bulk can be performed 
[ordered](https://docs.mongodb.com/manual/reference/method/db.collection.bulkWrite/#bulkwrite-example-bulk-write-operation)
or 
[unordered](https://docs.mongodb.com/manual/reference/method/db.collection.bulkWrite/#bulkwrite-example-unordered-bulk-write). 
It means that ordered operations are executed serially. Therefore, if an error 
occurs during the execution of the bulk the remaining operations will not be 
executed and MongoDB will return an error.

On the other hand, executing an unordered list of operations means that they
will be executed in parallel. If an error occurs, MongoDB will continue 
executing the remaining operations.

In addition, it supports the following operations:
- [insertOne](https://docs.mongodb.com/manual/reference/method/db.collection.bulkWrite/#bulkwrite-write-operations-insertone)
- [updateOne](https://docs.mongodb.com/manual/reference/method/db.collection.bulkWrite/#bulkwrite-write-operations-updateonemany)
- [updateMany](https://docs.mongodb.com/manual/reference/method/db.collection.bulkWrite/#bulkwrite-write-operations-updateonemany)
- [replaceOne](https://docs.mongodb.com/manual/reference/method/db.collection.bulkWrite/#bulkwrite-write-operations-replaceone)
- [deleteOne](https://docs.mongodb.com/manual/reference/method/db.collection.bulkWrite/#bulkwrite-write-operations-deleteonemany)
- [deleteMany](https://docs.mongodb.com/manual/reference/method/db.collection.bulkWrite/#bulkwrite-write-operations-deleteonemany)

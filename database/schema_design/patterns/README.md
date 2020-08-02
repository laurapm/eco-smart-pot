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

It is commonly represented as a single table in a 
[tabular](https://www.techopedia.com/definition/26181/tabular-database) 
database (for more information go to 
[one-to-one relationships documentation](https://docs.mongodb.com/manual/tutorial/model-embedded-one-to-one-relationships-between-documents/)). 

###### Embed

Prefer embedding over referencing for simplicity. If the fields are at the 
same level, it is very similar to a tabular database. It can be used 
subdocuments to organize fields, preserve simplicity and make overall documents
clearer.

###### Reference

It is used for optimization purposes (like in the 
[extended reference pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-extended-reference-pattern) 
or the 
[subset pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-subset-pattern), 
that are going to be explained on the patterns section). It uses the same 
identifier in both documents.

##### Examples

It would be the case of the `owner` and its private information (such as the 
`phone` or the `email` and `password`). In this case it could be separated the 
information into separate collections if it would be considered as a high 
security risk, but it would be as a future enhancement. For now the private 
and common information from the `owner` are going to be kept together for 
simplicity reasons.

Another example would be the case of `plant` and the _safety measures_ (that 
are inside the `lifesafe_range` field - should be `temperature`, `humidity` 
and `light_exposition_time`).

#### One-to-Many

An object of a given type is associated with **N** objects of a second type; 
while in the other direction, an object of the second type can only be 
associated with **one** object of the _one_ side. 

In IoT it can also be identified the _one-to-zillions_ subcase. It is the case 
of a relationship where the _many_ side is identified as 10000 or more. It is 
suggested to be mindful of this relationship in every place that the 
relationship is used in the code. The last thing that you want to do is to 
retrieve a document and its zillions associated documents and then process the 
complete result set.

This gives two options:

###### Embed

Using the same collection. Usually embedding in the entity most queried. 
Preferred over referencing for simplicity, or when there is a small number
of referenced documents, as all related information is kept together. For more
information go to 
[one-to-many relationships documentation](https://docs.mongodb.com/manual/tutorial/model-embedded-one-to-many-relationships-between-documents/)
for embedded documents.

###### Reference

Use two separate collections to keep the documents. Usually referencing in the 
_many_ side. Preferred when the associated documents are not always needed
within the most often queried documents For more information go to 
[one-to-many relationships documentation](https://docs.mongodb.com/manual/tutorial/model-referenced-one-to-many-relationships-between-documents/) 
for referenced documents.

In the subcase of the _one-to-zillions_, there is only one option left: 
**reference in the _many/zillions_ side**
- sole representation for a _one-to-zillions_ relationship, reference the
document on the _one_ side of the relationship from the _zillion_ side
- quantify the relationship to understand the maximum N value

##### Examples

The cases of **1-to-N** relationships using **reference** are easily observed 
in the schema, as they have a line joining the two collections, indicating the
_one_ as a strip, and the _many_ side as an inverted arrow:

- `owner` and `device`
- `device` and `reminder`
- `device` and `measurements` (mind _one-to-zillions_)
- `plant` and `device`
- `plant` and `measurements` (mind _one-to-zillions_)

In all the previous cases, embedding mechanism could have been used, but it 
would mean that the size of the documents would have grown exponentially as new
subdocuments were added (specially with the `device`-`reminder` and the 
`device`/`plant`-`measurements` cases).

In the cases of the _one-to-zillions_ relationships, a pattern has been 
applied to reduce the number of entries, although it is still probable for it 
to reach the _zillions_ state.

On the other hand, the **embedding** mechanism has been used in:

- `treatment.action`
- `measurements.humidity_int`
- `measurements.humidity_ext`
- `measurements.luminosity_ext`
- `measurements.temperature_ext`

In a [tabular](https://www.techopedia.com/definition/26181/tabular-database)
database (such as MySQL), it would be necessary referencing 
[tables](https://www.w3schools.com/sql/sql_create_table.asp) to achieve a 
similar structure.

In all of the **embedding** cases, it has been done in the most queried 
collections, in both cases they would represent the _one_ side of the 
relationship (`treatment` and `measurement`).

#### Many-to-Many

In a normalized model, it can not be linked two tables as a _many-to-many_
relationship. Under the hood, an additional relationship table needs to be 
created to define this relationship - _jump_ table. It splits the relationship
into two _one-to-many_ relationships. 

###### Embed

Usually, only the most queried side is considered. Should prefer embedding on 
the most queried side, specially over the information that is primarily static 
over time and may profit from duplication.

###### Reference

Prefer referencing over embedding to avoid managing.

##### Examples

In this situation, there is no implicit case where an N-to-N relationship 
appears. The cases where two collections have a _many-to-many_ relationship
thanks to a common intermediate table can not be considered as such. Some of
those cases would be:

- `owner` and `reminder`
- `owner` and `plant`
- `owner` and `treatment`
- `owner` and `measurements`
- `device` and `treatment`

## Patterns

Patterns are the most powerful tool for designing database schemas (and any 
code, and web content, etc. - anything that is done as it should, let's be 
honest). Despite their usefulness, patterns are not full solutions to 
problems. Patterns are smaller sections of full solutions. They are reusable 
unit of knowledge. They bring solutions that can scale and perform under 
stress.

It should also be taken into account that applying patterns may lead to:
- _Duplication_: Duplicating data across documents.
- _Data Staleness_: Accepting staleness in some pieces of data.
- _Data integrity issues_: Writing extra application side logic to ensure 
referential integrity.

The known patterns by the developers (considering MongoDB and NoSQL databases) 
are:

 | | catalog | content management | IoT | mobile | personalization | real-time analytics | single view |
 | :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
 | **approximation**      | ✅ |    | ✅ | ✅ |    | ✅  |    |
 | **attribute**          | ✅ | ✅ |    |     |    |     | ✅ |
 | **bucket**             |    |     | ✅ |    |    | ✅  |    |
 | **computed**           | ✅ |    | ✅ | ✅ | ✅ | ✅ | ✅ |
 | **extended reference** | ✅ |    |     | ✅ |    | ✅ |     |
 | **outlier**            |    |     | ✅ | ✅ | ✅ |    |     |
 | **pre-allocated**      |    |     | ✅ |    |     | ✅ |    |
 | **polymorphic**        | ✅ | ✅ |    | ✅ |     |    | ✅ |
 | **schema versioning**  | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
 | **subset**             | ✅ | ✅ |    | ✅ | ✅ |     |    |
 | **tree**               | ✅ | ✅ |    |     |    |     |    |
 | **graph**              | ✅ | ✅ |    |     |    |     |    |

The patterns from the previous table are just recommendations, but some of the 
patterns that are not ticked in the table could also be considered for the 
project at hand, but it is not usually the case (according to 
[MongoDB documentation on pattern](https://www.mongodb.com/blog/post/building-with-patterns-a-summary)). 
The patterns used for this project and that should be considered in the future 
of the project are:

### Attribute Pattern

The 
[attribute pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-attribute-pattern) 
is suitable for big documents, that have many similar fields but there is a 
subset of fields that share common characteristics and it is sorted or queried 
on that subset of fields; or the fields needed to sort are only found in a 
small subset of documents; or both of the previous conditions are met within 
the documents.

**Pros:**
- Fewer indexes are needed.
- Queries become simpler to write and are generally faster.

#### Study Case

This is the case of `treatment.action` field. The original idea was to use 
separated fields for each type of treatment, or even a separated collection
to reference each treatment. But that would mean either unnecessary fields or 
joining collections (inefficient in most cases). So it was decided to join all 
the actions performed by the device into a single well-structured and 
standardized field. All the fields or reference were substituted by a
simulating a _key-value pair_ field `action`. 

For this occasion, instead of using the standard two-field structure: 
`k: "<field>", v: "<value>"`, it has been expanded to use to use more intuitive
names such as:

```json
"action": [
    {
        "perform": "action name",
        "params": [ "list of parameters" ]
    },
    { ... }
]
```

This structure also allows concatenating actions to create more elaborated 
treatments (right now the available treatments are not many, but it would 
necessary in a future expansion).

### Bucket Pattern

The 
[bucket pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-bucket-pattern)
is useful with data coming in as a stream over a period of time (time series 
data). This might suggest, at first hand, each measurement in its own 
document. This approach would be fit for a tabular database way of handling 
data. 

This is going to lead to application scalability and index size issues. 
Instead, data can be bucketed by time, so documents hold measurements from a
particular time span.

**Pros:**
- Reduces the overall number of documents in a collection.
- Improves index performance.
- Can simplify data access by leveraging pre-aggregation.

#### Study Case

In `measurements` collection, it is stored the measures from the humidity 
inside the smart pot, and the humidity, light and temperature outside the pot.

The aforementioned structure typical of a tabular database was discarded and 
substituted by a single collection to store all four measures, with a time span
of one hour. 

As the minimum common period to read the is 5 minutes, the document is created 
at the beginning of each hour, and then updated every five minutes. There is a 
special case for the interior humidity of the pot, which happens when the pot 
is being watered - the measurement period is drastically reduced from 5 minutes
to measures every 5 to 15 seconds.

### Pre-Allocation Pattern

The 
[pre-allocation pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-preallocation-pattern)
simply dictates to create an initial empty structure to be filled later. 

If the code of the application is much easier to write and maintain if it uses 
a structure that is not completely filled, it may easily outweigh the cost of 
the RAM. 

**Pros:**
- Design simplification when the document structure is known in advance.

**Cons:**
- Simplicity versus performance.

#### Study Case

It has been used in `measurements` collection. At the  beginning of each hour
it is initialized the structure that is going to contain the measures for that 
hour. It does so for the four measures read by the device.

It does not have a great cost on performance, as the structure created is not 
complex nor takes a lot of space in RAM. But it does simplify the 
implementation of the write operations performed on those fields.

### Polymorphic Pattern

The 
[polymorphic pattern](https://developer.mongodb.com/how-to/polymorphic-pattern)
is used when  all documents in a collection are of similar, but not identical, 
structure. It is useful when wanted to access documents from a single 
collection. Grouping documents together based on the queries helps improve
performance.

It is the solution when there are a variety of documents that have more than 
similarities than differences and the documents need to be kept in a single
collection.

**Pros:**
- Easy to implement.
- Queries can run across a single collection.

#### Study Case

It has been implemented in the `plant` collection, in the `lifesafe_range`. 
Before implementing this pattern, there were 6 different fields to specify the 
ranges between which the plant was safe to live in (minimum and maximum 
humidity, temperature and light exposition - last one indicating time in 
hours). 

This was then changed for three fields containing
documents (called `min_max`) that had a `min` and `max` field, in order to 
indicate the span of the three parameters. 

Finally, it was implemented a single field (`plant.lifesafe_range`), that 
simply specifies the parameters and its span. This allows not to standardize the
different spans into one single field. This also allows the option of not 
setting any range in case they are unknown to a certain plant. It also allows 
to set a new one in case something else needs to be considered for a certain 
plant.

### Schema Versioning Pattern

The 
[schema versioning pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-schema-versioning-pattern)
is used to support changes between versions in the data schema.

An application stars with an original schema which eventually needs to be 
altered. When that occurs it can be created and save the new schema to the 
database with a `schema_version` or simply `version`. 

This field will allow to know how to handle a particular document. 
Alternatively, the application can deduce the version based on the presence
or absence of some fields, but the former method is preferred.

It can be assumed that documents that _don't_ have this field are version 1.
Each new schema version would then increment the `schema_version` field value
and could be handled accordingly in the application.

**Pros:**
- No downtime needed to update version.
- Control of schema migration.
- Reduced future technical debt.

**Cons:**
- Might need two indexes for the same field during migration.

#### Study Case

It has been created a collection called `model_version`. This helps on creating
a model that documents the changes suffered in collection when the version 
changes.

Therefore, the `version` field on each document will be related to the 
`model_version` entrance.

Greatly reduces the possible technical debt of migrating the whole data stored 
so far - should take into account that Ecø is an IoT application, and therefore
the amount of data stored could easily reach the _zillions_ relationship. 
Therefore, changing the structure, fields content, adding new information, etc.
to all the documents could take days, months or even years depending on the 
change required.

### Subset Pattern

The 
[subset pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-subset-pattern)
addresses the issues associated with a working set that exceeds RAM, resulting
in information being removed from memory. This is frequently caused by large 
documents which have a lot of data that isn't actually used by the application.

**Pros:**
- Reduction in the overall size of the 
[working set](https://docs.mongodb.com/manual/reference/glossary/#term-working-set).
- Shorter disk access time for the most frequently used data.

**Cons:**
- Must manage the subset.
- Pulling in additional data requires additional trips to the database.

#### Study case

The `measurements` collection itself could easily be part of the `device` 
documents. But that would mean that the documents would grow exponentially in 
no time, even reaching the 
[16MB limit per document](https://docs.mongodb.com/manual/reference/limits/)

To avoid that, what should be the `measurements` field was transformed into a 
new collection entirely.

It was also taken into account to decide the size (actually, the time period 
span) in the
[bucket pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-bucket-pattern).

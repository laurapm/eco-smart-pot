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
database. 

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
of referenced documents, as all related information is kept together.

###### Reference

Use two separate collections to keep the documents. Usually referencing in the 
_many_ side. Preferred when the associated documents are not always needed
within the most often queried documents.

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
 | **approximation** | ✅ | | ✅ | ✅ | | ✅ | |
 | **attribute** | ✅ | ✅ |  |  | |  | ✅ |
 | **bucket** |  |  | ✅ |  |  | ✅ |  |
 | **computed** | ✅ |  | ✅ | ✅ | ✅ | ✅ | ✅ |
 | **extended reference** | ✅ |  |  | ✅ |  | ✅ |  |
 | **outlier** |  |  | ✅ | ✅ | ✅ |  |  |
 | **pre-allocated** |  |  | ✅ |  |  | ✅ |  |
 | **polymorphic** | ✅ | ✅ |  | ✅ |  |  | ✅ |
 | **schema versioning** | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
 | **subset** | ✅ | ✅ |  | ✅ | ✅ |  |  |
 | **tree** | ✅ | ✅ |  |  |  |  |  |
 | **graph** | ✅ | ✅ |  |  |  |  |  |

The patterns from the previous table are just recommendations, but some of the 
patterns that are not ticked in the table could also be considered for the 
project at hand, but it is not usually the case (according to 
[MongoDB documentation on pattern](https://www.mongodb.com/blog/post/building-with-patterns-a-summary)). 
The patterns used for this project and that should be considered in the future 
of the project are:

### Approximation Pattern

Measures taken every 5min as it is the minimum common reading period

### Attribute Pattern

In `treatment.action`

### Bucket Pattern

In `measurements`

### Outlier Pattern

Nowhere so far, but it might be useful in the future.

### Pylymorphic Pattern

In `plant.lifesafe_ranfe`. At first 6 different fields, then 3 with subdocument
of `min_max`, then one document with 3 fields that allow to specify all data

### Schema Versioning Pattern

Thanks to `model_version` collection

### Subset Pattern

The `measurement` collection itself



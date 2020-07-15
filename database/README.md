# Introduction

This project uses [MongoDB](https://www.mongodb.com/) as database engine, to 
store and manipulate data at will.

### Why MongoDB

One of the reasons to use MongoDB, instead of other database is because it is 
[open-source](https://github.com/mongodb). The developers of this project 
deeply believe in the open-source community, having the content being publicly 
available allows developers to collaborate with us and bring free, secure and 
private applications: anybody can suggest improvements, modify, or fix bugs 
in the application.

Other reason would be that, compared to other SQL solutions, is the 
[advantages](https://www.integrant.com/when-to-use-sql-vs-nosql/) for these 
type of projects, IoT:
- SQL solutions are designed to 
[vertically](https://www.techopedia.com/definition/9912/vertical-scaling).
This is not suitable for IoT applications, where replication and sharding 
are, in most cases, mandatory - and therefore, 
[horizontal scaling](https://www.techopedia.com/definition/7594/horizontal-scaling)
is best.
- The flexibility brough by a NoSQL solution allows for a faster development:
  - Model does not need to be as detailed, it admits fast adaptation of data 
  and model through the different development phases of the project.
  - Allows different data types even for same field, so data types do not need
  to be specified in advanced.
  - Those data types can be redifined, without the need of changing the whole 
  schema or the information already stored in the database.
- Also, having so much data coming from so many different devices all at ones
means that, probably, data consistency is not one of the main pillars of the 
application - it might be more important to be able to use that information 
(despite outdated) to use analytic queries, predict behaviours, etc. Once 
again, NoSQL is more suited for this task.

# Servers Setup

Please refer to 
[server creation documentation](https://github.com/laurapm/UBICUA/tree/master/database/config_scripts)
of this project. If more 
information is needed, it is recommended to use the 
[MongoDB official documentation](https://docs.mongodb.com/) - some links have 
already been included in the guide done by the developers of this project.

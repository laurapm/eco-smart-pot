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

To store data it must be used a MongoDB server. With that objective, it can be 
used a server such as the ones provided by the 
[Atlas Cluster](https://www.mongodb.com/cloud/atlas) (more info 
[here](https://docs.atlas.mongodb.com/cluster-configuration/)) or create a 
private MongoDB server in a personal computer/server.

In case of using a private server, please refer to 
[server creation documentation](https://github.com/laurapm/UBICUA/tree/master/database/config_scripts)
of this project. If more 
information is needed, it is recommended to use the 
[MongoDB official documentation](https://docs.mongodb.com/) - some links have 
already been included in the guide done by the developers of this project.

_**Disclaimer**: Please be advised, if you want to connect from outside your 
LAN and you use 
[Port Forwarding](https://en.wikipedia.org/wiki/Port_forwarding), 
consider changing the default ports for every type of connection you need when
accessing from a remote source, otherwise some security risks might appear in 
your application._

## How to Connect

Once the servers are running, it is used the 
[mongo](https://docs.mongodb.com/manual/reference/program/mongo/#bin.mongo)
interactive shell as a command interface to those servers.

If the server is local, just specifying the port is sufficient to connect:

```bash
mongo --port <port>
```

But, in case it is not a local instance, or you want to connect from outside 
the LAN, the host address must be specified. In which case, it might be 
necessary to activate 
[Port Forwarding](https://www.portforwarding.org/) in your 
[router](https://portforward.com/) - **in case of doubt, please refer to your  Telcom provider to ensure you do not break anything**.

```bash
mongo --host <ip-address>:<port>
```

This also allows connections in replica sets, it is specially useful as in this
cases it does not connect to the Primary Node, but to the node that has been 
specified.

To always connect to the primary node, a step forward must be taken. Besided 
the host IP Address and Port on where MongoDB server is running, it is also 
needed the name of the replica set:

```bash
mongo --host <repl-set-name>/<ip-address-1>:<port-1>,<ip-address-2>:<port-2>,...
```

As shown before, it admits a list of nodes (pair of IP Address and Port), so in 
case the first node is not available, it tries the next one.

Finally, the 
[authentication](https://docs.mongodb.com/manual/core/authentication/) process 
can be done inside the Mongo Shell:

```javascript
use <database>
db.auth(<username>, <password>)
```

But it can also be done before connecting to the server. In order to do that, 
it must be specified the username and password of the user that is going to 
connect to the database, and also, the database on which to authenticate it 
([authorization](https://docs.mongodb.com/manual/reference/configuration-options/#security.authorization) 
must be enabled, and some 
[user created](https://docs.mongodb.com/manual/reference/method/db.createUser/)).

```bash
mongo --host <host> -u <username> -p <password> --authenticationDatabase <database>
```

The 
[authentication database](https://docs.mongodb.com/manual/reference/program/mongo/#cmdoption-mongo-authenticationdatabase) 
must be the database where the user was created.

# Operations

Once the servers are up and running, several operations can be performed on 
data stored. These operations are given the name of 
[CRUD](https://docs.mongodb.com/manual/crud/):
- [Create Operations](https://docs.mongodb.com/manual/crud/#create-operations)
- [Read Operations](https://docs.mongodb.com/manual/crud/#read-operations)
- [Update Operations](https://docs.mongodb.com/manual/crud/#update-operations)
- [Delete Operations](https://docs.mongodb.com/manual/crud/#delete-operations)
- [Bulk Write](https://docs.mongodb.com/manual/crud/#bulk-write)

Each of those types of operations are explained in a different documentation. 
Refer to 
[Operations Documentation](https://github.com/laurapm/UBICUA/tree/master/database/operations) 
to know more about this topic.



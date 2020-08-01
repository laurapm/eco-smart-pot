# Setting Up the Cluster

First thing is first, in order to have a cluster, it is necessary to create a 
single server.

### Server Configuration

Each server has its own configuration file, which are simple text files that 
allow to encode the different options of each server, does not matter if it is 
and `mongod` instance or a `mongos` instance. Each configuration file must end 
with the extension `.conf`. They use **YAML format** - _YAML does not support 
tab characters for indentation: use spaces instead_.

The usage of this kind of files simplifies **large-scale deployments**. For more
information refer to the 
[MongoDB Configuration file documentation](https://docs.mongodb.com/manual/reference/configuration-options/).

Using them is simple, just use the one of the following commands:
```bash
mongod --config <path-to-file>
mongod -f <path-to-file>
```

### Config File Parameters

There are more parameters than the ones used in these files, but the ones used 
for this project are:

**systemLog**
- **`path`** (_string_): The path of the log file to which `mongod` or `mongos` 
should send all diagnostic logging information, rather than the standard output
or the host’s syslog. MongoDB creates the log file at the specified path.
- **`logAppend`** (_boolean_): When true, `mongos` or `mongod` appends new 
entries to the end of the existing log file when the `mongos` or `mongod` 
instance restarts. Without this option, `mongod` will back up the existing log 
and create a new file.
- **`destination`** (file | syslog): If you specify _file_, you must also 
specify `systemLog.path`. If you do not specify `systemLog.destination`, 
MongoDB sends all log output to standard output.

**processManagement**
- **`fork`** (_boolean_): Enable a daemon mode that runs the `mongos` or 
`mongod` process in the background. By default `mongos` or `mongod` does not 
run as a daemon.

**net**
- **`port`** (_integer_): The TCP port on which the MongoDB instance listens 
for client connections. Default:
  - **27017** for `mongod` (if not a shard member or a config server member) or 
  `mongos` instance.
  - **27018** if `mongod` is a shard member.
  - **27019** if `mongod` is a config server member.
- **`bindIp`** (_string_): The hostnames and/or IP addresses and/or full Unix 
domain socket paths on which `mongos` or `mongod` should listen for client 
connections. You may attach `mongos` or `mongod` to any interface. To bind to 
multiple addresses, enter a list of comma-separated values.

**security**
- **`authorization`** (_string_): Enable or disable Role-Based Access Control 
(RBAC) to govern each user’s access to database resources and operations. Set 
this option to one of the following:
  - **enabled**: A user can access only the database resources and actions for 
  which they have been granted privileges.
  - **disabled**: A user can access any database and perform any action.
- **`keyFile`** (_string_): The path to a key file that stores the shared 
secret that MongoDB instances use to authenticate to each other in a sharded 
cluster or replica set. keyFile implies `security.authorization`. See 
[Internal/Membership Authentication](https://docs.mongodb.com/manual/core/security-internal-authentication/#inter-process-auth) 
for more information.
- a single key string (same as in earlier versions),
- multiple key strings (each string must be enclosed in quotes), or
- sequence of key strings.

**storage**
- **`dbPath`** (_string_): The directory where the `mongod` instance stores its
data.
- **`directoryPerDB`** (_boolean_): When true, MongoDB uses a separate 
directory to store data for each database. The directories are under the 
`storage.dbPath` directory, and each subdirectory name corresponds to the 
database name.

**replication**
- **`replSetName`** (_string_): The name of the replica set that the `mongod` 
is part of. All hosts in the replica set must have the same set name.
If your application connects to more than one replica set, each set should have
a distinct name. Some drivers group replica set connections by replica set 
name.

## Replication

Database [replication](https://docs.mongodb.com/manual/replication/) is a 
methodology that allows to have multiple servers at the same time, one of them 
being the main server, with several secondary servers connected. 

This brings the possibility of having the same data separated in different 
servers (that can be even in different locations). This means that if the main 
server fails, the application service will not be interrupted, it will be 
redirected to other of the secondary servers (which will become primary by a 
process called 
[election](https://docs.mongodb.com/manual/core/replica-set-elections/)). There
will always be service as long as a primary can be elected.

There are different type of nodes:
- [Primary node](https://docs.mongodb.com/manual/core/replica-set-primary/): 
Receives **all write operations**. Can only be one primary per replica set.
- [Secondary node](https://docs.mongodb.com/manual/core/replica-set-secondary/): 
Maintains a copy of the primary's data. Data is replicated via an asynchronous process that copies the operations from the primary 
[oplog](https://docs.mongodb.com/manual/core/replica-set-oplog/).
- [Hidden Node](https://docs.mongodb.com/manual/core/replica-set-hidden-member/#replica-set-hidden-members):
It acts just like a secondary, but it is hidden from the application. Used for
reporting and backup tasks. Needs to have its 
[priority set to 0](https://docs.mongodb.com/manual/core/replica-set-priority-0-member/#replica-set-secondary-only-members), 
it cannot become primary. 
- [Delayed Node](https://docs.mongodb.com/manual/core/replica-set-delayed-member/#replica-set-delayed-members):
Contain a copy of the data, but that data can be an earlier of delayed copy of
the dataset. They are mainly used for recoverability purposes, specially for 
unsuccessful application upgrades and operator errors. They have a [priority of 0](https://docs.mongodb.com/manual/core/replica-set-priority-0-member/#replica-set-secondary-only-members) 
and it is advisable for them to be hidden. They 
[can vote](https://docs.mongodb.com/manual/reference/replica-configuration/#rsconf.members[n].votes) 
in elections (if their vote is set to 1).
- [Arbiter Node](https://docs.mongodb.com/manual/core/replica-set-arbiter/): 
They cannot become primary, they do not have a copy of the data, but they can 
vote. It is a solution when another secondary node cannot be added to the 
replica set. They have exactly **1** election vote.

Reading operations can be done against any primary or secondary node of the 
cluster, which is handy for, for example, analytical queries, where it does not
matter if data is a little outdated.

On the other hand, write operations can only be done against the primary node, 
for consistency reasons.

## DIY

Some of you may want to create your own cluster, here you have the steps to 
achieve your goal. (_DISCLAIMER: you might want to change the paths, addresses, 
etc. you are going to find tin the specific documents._)

1. Get [MongoDB Enterprise](https://docs.mongodb.com/manual/installation/) 
installed, of course (just follow the steps).
2. Make sure MongoDB has been included to you PATH, if not, we highly recommend 
for you to do it.
3. To avoid messing with the system, create a new folder:
```bash
mkdir /var/mongodb
sudo chown pace:pace -R /var/mongodb
```
3. Run the `mongod` with the 
[standalone server configuration](https://github.com/laurapm/UBICUA/blob/master/database/config_scripts/servers/standalone-server.conf) 
file.
  - May need to configure the paths:
```bash
mkdir -pv /var/mongodb/db/1
chmod -Rv 700 /var/mongodb/db/1
```
4. Now, connect to the server by using `mongo` and the necessary parameters
 - May need the `--host` or `--port` parameters.
5. Run the 
[initial configurations steps](https://github.com/laurapm/UBICUA/blob/master/database/config_scripts/init_config.js)
using the command:

```javascript
load("init_config.js")
```
- Might need to specify the path where that file is stored in your PC.

Now that the users and _eco_ database have been created, it is time to create 
the replication cluster.

6. Connect to the standalone server and shut it down, this will allow to set a 
new configuration (change the previously set configuration) and maintain the
databases and users created.

```javascript
use admin
db.shutDownServer()
```

7. To enforce the security of the new replica set is convenient to use a 
[keyFile](https://docs.mongodb.com/manual/reference/configuration-options/#security.keyFile).
To set up this file it is necessary to:
  - Create the file and give the necessary permissions (specifying the path to 
  the file).
  - Give the appropriate permissions to the file.

```bash
sudo mkdir -pv /var/mongodb/pki
openssl rand -base64 741 > /var/mongodb/pki/eco-keyfile
chmod 600 /var/mongodb/pki/eco-keyfile
mkdir -pv /var/mongodb/db/{2,3}
```

8. It is necessary to create different files for every node in the server (need
to change the port, the dbPath, logPath):

| type                | PRIMARY                      | SECONDARY                    | SECONDARY                    |
| :------------------ | :--------------------------- | :--------------------------- | :--------------------------- |
| **config filename** | mongo-repl-1.conf            | mongo-repl-2.conf            | mongo-repl-3.conf            |
| **port**            | 27001                        | 27002                        | 27003                        |
| **dbPath**          | /var/mongodb/db/1            | /var/mongodb/db/2            | /var/mongodb/db/3            |
| **logPath**         | /var/mongodb/db/mongod1.log  | /var/mongodb/db/mongod2.log  | /var/mongodb/db/mongod3.log  |
| **replSet**         | eco-repl                     | eco-repl                     | eco-repl                     |
| **keyFile**         | /var/mongodb/pki/eco-keyfile | /var/mongodb/pki/eco-keyfile | /var/mongodb/pki/eco-keyfile |

  - One again, some new folder will be necessary before launching the servers.
  
```bash 
mkdir -pv /var/mongodb/db/{2,3}
chmod -Rv 700 /var/mongodb/db/{2,3}
```
  
9. After connecting to the server that is going to act as the primary of the 
cluster (in this case the `mongo-repl-1.conf`), it must be initiated the 
replica set and added the rest of the members (if the replica set is not local,
change the host ip address):

```javascript
rs.initiate(
   {
      _id: "eco-repl",
      version: 1,
      members: [
         { _id: 0, host : "localhost:27001" },
         { _id: 1, host : "localhost:27002" },
         { _id: 2, host : "localhost:27003" }
      ]
   }
)
```

or use:

```javascript
rs.initiate()
rs.add( { host: "localhost:27002" } )
rs.add( { host: "localhost:27003" } )
```

- The first option is more advisable. Remember, if using different host, 
replace `localhost` for the appropriate `ip-address`

10. To check that everything is running as it should run the command:

```javascript
rs.status()
```

The users and databases previously created in the standalone node should now be
available in the rest of the nodes.

## Reconfigure a Node

As it might have been suggested earlier, to reconfigure a running node it is 
needed to first stop the node, and the launch the new configuration using the 
same paths and ports. The rest of the values can usually be changed.

In order to avoid problems while 
[shutting down a node](https://docs.mongodb.com/manual/tutorial/manage-mongodb-processes/#terminate-mongod-processes), 
use one of the following options, in order of preference:

- Use **shutdownserver**:

```javascript
use admin
db.shutdownServer()
```

- Use **--shutdown**:

It is not mandatory, but it is recommended to specify the `--dbpath`.

```bash
mongod --shutdown --dbpath <path-to-running-instance>
```

- Use **Ctrl+C**:

Needs to have the process running on a terminal, without the `--fork` option.

- Use **kill**:

Needs to know the PID (in Linux):

```bash
ps -ef | grep mongo
kill <mongod-PID>
```

After that, just run the new configuration (use one of the next options):

```bash
mongod --config <path-to-new-config>
mongod -f <path-to-new-config>
```

## Reconfigure a Running Replica Set

Being connected to the mongo shell, to the Primary node, there is a command 
that returns the current configuration of the replica set.

```javascript
rs.config()
```

The thing is, the JSON that represents the configuration returned in that 
function call, can be stored in a variable as a simple text. This allows to 
edit the content of the configuration (i.e: change the IP:Port pair of a given 
host). All the elements of the JSON can be accessed using dot notation.

```javascript
cfg = rs.config()
cfg.members[1].host = "localhost:27017"
```

But it does not mean that by changing the variable with the configuration 
actually affects the configuration of the replica set. In order to do that, a 
reconfiguration must be performed.

```javascript
rs.reconfig(cfg)
```

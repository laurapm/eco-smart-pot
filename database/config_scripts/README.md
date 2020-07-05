# Setting Up the Cluster

First thing is first, in order to have a cluster, it is necessary to create a 
single server.

### Server Configuration

Each server has its own configuration file, which are simple text files that 
allow to encode the different options of each server, does not matter if it is 
and `mongod` instance or a `mongos` instance. Each configuration file must end 
with the extention `.conf`. They use **YAML format** - _YAML does not support 
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

**storage**
- `dbPath` (_string_): The directory where the `mongod` instance stores its data.

**systemLog**
- **`path`** (_string_): The path of the log file to which `mongod` or `mongos` 
should send all diagnostic logging information, rather than the standard output
or the host’s syslog. MongoDB creates the log file at the specified path.
- **`destination`** (file | syslog): If you specify _file_, you must also 
specify `systemLog.path`. If you do not specify `systemLog.destination`, 
MongoDB sends all log output to standard output.
- **`logAppend`** (_boolean_): When true, `mongos` or `mongod` appends new 
entries to the end of the existing log file when the `mongos` or `mongod` 
instance restarts. Without this option, `mongod` will back up the existing log 
and create a new file.

**net**
- **`bindIp`** (_string_): The hostnames and/or IP addresses and/or full Unix 
domain socket paths on which `mongos` or `mongod` should listen for client 
connections. You may attach `mongos` or `mongod` to any interface. To bind to 
multiple addresses, enter a list of comma-separated values.
- **`port`** (_integer_): The TCP port on which the MongoDB instance listens 
for client connections. Default:
  - **27017** for `mongod` (if not a shard member or a config server member) or 
  `mongos` instance.
  - **27018** if `mongod` is a shard member.
  - **27019** if `mongod` is a config server member.

**security**
- **`authorization`** (_string_): Enable or disable Role-Based Access Control 
(RBAC) to govern each user’s access to database resources and operations. Set 
this option to one of the following:
  - **enabled**: A user can access only the database resources and actions for 
  which they have been granted privileges.
  - **disabled**: A user can access any database and perform any action.

**processManagement**
- **`fork`** (_boolean_): Enable a daemon mode that runs the `mongos` or 
`mongod` process in the background. By default `mongos` or `mongod` does not 
run as a daemon.

## Do it yourself

Some of you may want to create your own cluster, here you have the steps to 
achieve your goal. (_DISCLAIMER: you might want to change the paths, addresses, 
etc. you are going to find tin the specific documents._)

1. Get [MongoDB Enterprise](https://docs.mongodb.com/manual/installation/) 
installed, of course (just follow the steps).
2. Make sure MongoDB has been incleded to you PATH, if not, we highly recommend 
for you to do it.
3. Run the `mongod` with the configuration file 
[main_server.conf]().


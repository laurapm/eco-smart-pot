# Schema

No free or open-source data modeller has been found that would fit the design 
of the database schema. Instead, the 
[Moon Modeller](https://www.datensen.com/download.html) free trial version can
be a substitute.

![schema design documentation](https://github.com/laurapm/UBICUA/tree/master/database/schema_design/design/eco-light.png)

## Plant

The main objective of the device is to take care of plants, That is why, all 
the convenient information must be stored. The behaviour of the device will be 
affected by parameters like the acceptable values of temperature, humidity, 
etc.

The collection used goes as follows:

```json
plant
{
    "_id": "<objectId>",
    "common_name": "<string>",
    "scientific_name": "<string>",
    "family": "<string>",
    "temperature": {
        "min": "<double>",
        "max": "<double>"
    },
    "humidity": {
        "min": "<double>",
        "max": "<double>"
    },
    "luminosity": {
        "min": "<double>",
        "max": "<double>"
    }
}
```

The range of values that a plant can resist without endangering its safety are
represented as subdocuments containing the edge values (minimum and maximum 
values) of that range. 

## Device

The device stores each Ecø device information. That means knowing which plant 
it is tracking or the latest software update being installed. This last piece
of information allows to notificate the owner of the device to receive and 
install the latest firmware update:

```json
device
{
    "_id": "<objectId>",
    "plant": "<objectId>",
    "firmware_update": "<string>"
}
```

## Measurements

One of the most complex structures to understand in this context. It uses 
[patters](https://www.mongodb.com/blog/post/building-with-patterns-a-summary) 
to simplify and reduce the develpment and maintenance complexity (for more 
information go to 
[patterns documentation](https://github.com/laurapm/UBICUA/tree/master/database/schema_design/patterns/)
from this project). It uses the 
[bucket pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-bucket-pattern)
by grouping the data into chunks of information of one hour in total. That 
way, by specifying the current day (`date`) and then the `hour`, it is certain 
that all the information that is going to be loaded into the database has a 
certain order and structe.

The documents of this collection would have the following shape:

```json
measurements
{
    "_id": "<objectId>",
    "plant": "<objectId>",
    "device": "<objectId>",
    "date": "<date>",
    "hour": "<int>",
    "humidity_int": [{
        "minute": "<int>",
        "measure": {
            "watered": "<bool>",
            "value": ["<int>"]
        }
    }],
    "humidity_ext": [{
        "minute": "<int>",
        "measure": "<double>"
    }],
    "luminosity_ext": [{
        "minute": "<int>",
        "measure": "<int>"
    }],
    "temperature_ext": [{
        "minute": "<int>",
        "measure": "<double>"
    }]
}
```

Also, the developers have decided that all measures are to be taken at time 
(`minute`) multiples of 5, as it is the least common multiple of all 
parameters - that helps of reading and organizing the information. This 
prevents exceeding the database workload and loosing data, as the total amount 
of write and read operations is greatly reduced.

## Treatment



## Reminder



## Owner



## Model Version

It simply helps tracking the changes through versions. It is self-explainatory.
It also should help implementing the 
[schema versioning pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-schema-versioning-pattern).

```json
model_version
{
    "_id": "<objectId>",
    "version": "<string>", 
    "changes": "<string>"
}
```

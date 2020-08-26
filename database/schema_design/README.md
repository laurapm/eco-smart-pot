# Schema

The schema has been designed using relationships and pattern optimization
for MongoDB (see
[patterns documentation](https://github.com/laurapm/UBICUA/tree/master/database/schema_design/patterns/) 
to learn more). Also, the collections have been optimized to reduce the 
workload on write operations, as an IoT application tends to suffer more from 
that type of operations.

No free or open-source data modeler has been found that would fit the design 
of the database schema. Instead, the 
[Moon Modeler](https://www.datensen.com/download.html) free trial version can
be a substitute.

The resulting schema is:

![schema design documentation](https://github.com/laurapm/UBICUA/tree/master/database/schema_design/design/eco-light.png)

## Plant

The main objective of the device is to take care of plants, That is why, all 
the convenient information must be stored. The behavior of the device will be 
affected by parameters like the acceptable values of temperature, humidity, 
etc.

The collection used goes as follows:

```json
plant
{
    "_id": "<objectId>",
    "commonName": "<string>",
    "scientificName": "<string>",
    "family": "<string>",
    "lifesafeRange": [ {
        "type": "<string>",
        "min": "<double>",
        "max": "<double>"
    } ]
}
```

The range of values that a plant can resist without endangering its safety are
represented as subdocuments containing the edge values (minimum and maximum 
values) of that range. 

## Device

The device stores each Ecø device information. That means knowing which plant 
it is tracking or the latest software update being installed. This last piece
of information allows to notify the owner of the device to receive and 
install the latest firmware update:

```json
device
{
    "_id": "<objectId>",
    "plant": "<objectId>",
    "owner": "<objectId>",
    "model": "<string>",
    "firmwareUpdate": "<string>",
    "registryDate": "<date>"
}
```

## Measurements

One of the most complex structures to understand in this context. It uses 
[patters](https://www.mongodb.com/blog/post/building-with-patterns-a-summary) 
to simplify and reduce the development and maintenance complexity (for more 
information go to 
[patterns documentation](https://github.com/laurapm/UBICUA/tree/master/database/schema_design/patterns/)
from this project). It uses the 
[bucket pattern](https://www.mongodb.com/blog/post/building-with-patterns-the-bucket-pattern)
by grouping the data into chunks of information of one hour in total. That 
way, by specifying the current day (`date`) and then the `hour`, it is certain 
that all the information that is going to be loaded into the database has a 
certain order and structure.

The documents of this collection would have the following shape:

```json
measurements
{
    "_id": "<objectId>",
    "plant": "<objectId>",
    "device": "<objectId>",
    "date": "<date>",
    "hour": "<int>",
    "humidityInt": [{
        "minute": "<int>",
        "measure": {
            "watered": "<bool>",
            "value": ["<int>"]
        }
    }],
    "humidityExt": [{
        "minute": "<int>",
        "measure": "<double>"
    }],
    "luminosityExt": [{
        "minute": "<int>",
        "measure": "<int>"
    }],
    "temperatureExt": [{
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

In order to take care of the plant, some treatments are necessary. For the 
moment, the only available treatment in this project is watering the plant,
although some others might be included in the near future.

Information such us when the request was made, or the type of action carried 
out is what this collection stores. For the action, it is used an standardize 
JSON file through all the interactions of the project. That being said, the 
structure used by those documents just store the function that is going to be
performed and the parameters that function takes.

The overall document look like this:

```json
treatment
{
    "_id": "<objectId>",
    "plant": "<objectId>",
    "device": "<objectId>",
    "type": "<string>",
    "title": "<string>",
    "action": {
        "perform": "<string>",
        "params": [
            {
                "key": "<string>",
                "value": "<string>"
            }
        ]
    },
    "requestTime": "<date>",
    "actionTime": "<date>",
    "comment": "<string>"
}
```

Each parameter from the function may have different data type. 

## Reminder

Reminders are linked to each device, so a reminder has to be set manually for 
all devices. The reminders can repeat the notification, or not. In order to 
ease having to take into account the dates, it also takes a certain number of 
repetitions.

Not all the fields are necessary. For example, if the reminder does not repeat,
the date or the number of repetitions do not need to be set. It can be more 
easily understood by taking into a look at the 
[schema](https://github.com/laurapm/UBICUA/tree/master/database/schema_design/design/eco-dark.png).
The last column of the collection (`NN`) indicates if that key needs to be set.

```json
reminder
{
    "_id": "<objectId>",
    "device": "<objectId>",
    "title": "<string>",
    "message": "<string>",
    "requestTime": "<date>",
    "remindingTime": "<date>"
}
```

## User

The Ecø service users have an account linked to the webpage and the device (in
case they have one). 

In this collection there is a One-To-Many relationship from `user` to the 
`device` collection. Out of the several available options (_embed_ or 
_reference_ in the _one_ or in the _many_ side), an array of devices has been 
referenced in the _one side_. This is usually not the preferred behavior, 
although it is the most suitable for this occasion. For more information 
regarding this design decision refer to the **relationships section** in the 
[patterns documentation](https://github.com/laurapm/UBICUA/tree/master/database/schema_design/patterns/) .

```json
user
{
    "_id": "<objectId>",
    "username": "<string>",
    "name": "<string>",
    "surname": "<string>",
    "phone": "<string>",
    "email": "<string>",
    "password": "<string>"
}
```

## Product

In the _rainforest_ store there are several products available (and more will 
be in the future). To store their information, it is kept a very simple 
structure of the most basic information.

```json
product
{
    "_id": "<objectId>",
    "name": "<string>",
    "price": "<double>",
    "description": "<string>"
}
```

## Ticket

It is a way to simplify a _many-to-many_ relationship between `product` and
`user`. It does not use the MongoDB standard, but there is a reason behind this
behaviour. This information could make the size of both the `product` and 
`user` collections exponentially. To avoid that, it is created a different 
collection where only the data from the _"transactions"_ is kept.

```json
ticket
{
    "_id": "<objectId>",
    "owner": "<objectId>",
    "product": [ 
        {
            "item": "<objectId>",
            "quantity": "<int>",
            "priceUnit": "<double>"
        }
     ],
    "date": "<date>"
}
```

## Model Version

It simply helps tracking the changes through versions. It is self-explanatory.
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

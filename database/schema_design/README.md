# Schema

No free or open-source data modeller has been found that would fit the design 
of the database schema. Instead, the 
[Moon Modeller](https://www.datensen.com/download.html) free trial version can
be a substitute.

[schema design documentation](https://github.com/laurapm/UBICUA/tree/master/database/schema_design/design/eco-light.png).

## Plant

The main objective of the device is to take care of plants, That is why, all 
the convenient information must be stored. The behaviour of the device will be 
affected by parameters like the acceptable values of temperature, humidity, 
etc.

The collection used goes as follows:

```json
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

## Device



## Measurement



## Treatment



## Reminder



## Owner



## Model Version





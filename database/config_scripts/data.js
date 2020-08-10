db.auth({
    user: "gardener",
    pwd: "eco-app-plant"
})

db = db.getSiblingDB('eco')

plant_id  = ObjectId()
owner_id  = ObjectId()
device_id = ObjectId()

db.plant.bulkWrite(
    [
        { insertOne: {
            "_id": plant_id,
            "common_name": "Kalanchoe",
            "scientific_name": "Kalanchoe Blossfeldiana",
            "family": "Asteraceae",
            "lifesafe_range": [
                {
                    "type": "temperature",
                    "min": 10.0,
                    "max": 28.0
                },
                {
                    "type": "humidiyu",
                    "min": 40.0,
                    "max": 80.0
                },
            ]
        } }
    ],
    { "ordered": false }
)

db.owner.bulkWrite(
    [
        { insertOne: {
            "username": "pablo.acereda",
            "name": "Pablo",
            "surname": "Acereda García",
            "courtesy_title": "Mr.",
            "phone": "000000001",
            "email": "pablo.acereda@eco.com",
            "password": "3410a597eaaa5894e92b2b51bc3934aa80d58c30f3efcc802826ad9ea1506992" // acereda
        } },
        { insertOne: {
            "username": "javier.albert",
            "name": "Javier",
            "surname": "Albert Segui",
            "courtesy_title": "Mr.",
            "phone": "000000002",
            "email": "javier.albert@eco.com",
            "password": "72d0166b5707d129dc321e56692fe454c034552ee9e2b38f5a7f1c1306a632ea" // albert
        } },
        { insertOne: {
            "username": "ana.castillo",
            "name": "Ana",
            "surname": "Castillo Martínez",
            "courtesy_title": "Dra.",
            "phone": "000000003",
            "email": "ana.castillo@eco.com",
            "password": "b6e81b71ac210e45b216fccb3302f1ebc798947ba9ddca6b83ed1ddc63b2ff70" // castillo
        } },
        { insertOne: {
            "username": "dave.craciunescu",
            "name": "Dave",
            "surname": "Craciunescu",
            "courtesy_title": "Mr.",
            "phone": "000000004",
            "email": "dave.craciunescu@eco.com",
            "password": "0653076bc4dd143b243b5c7d896bd31c53450ec06dfe4053a2d1710056f011fa" // craciunescu
        } },
        { insertOne: {
            "_id": owner_id,
            "username": "laura.perez",
            "name": "Laura",
            "surname": "Perez Medeiro",
            "courtesy_title": "Mrs.",
            "phone": "000000005",
            "email": "laura.perez@eco.com",
            "password": "a5ed602ee512bda8b2b18d6d4b06d6f176e7e3fb15a0cf5b23028b9849bd0d62" // perez
        } }
    ],
    { "ordered": false }
)

db.device.bulkWrite(
    [ 
        { insertOne: {
            "_id": device_id,
            "plant": plant_id, // Kalanchoe
            "owner": owner_id, // laura.perez
            "model": "eco-pot-2020",
            "firmware_update": "1.0",
            "registry_date": new Timestamp()
        } }
    ],
    { "ordered": false }
)

db.treatment.bulkWrite(
    [
        { insertOne: {
            "plant": plant_id,
            "device": device_id,
            "type": "scheduled",
            "title": "irrigation",
            "action": [
                {
                    "perform": "watering",
                    "params": {
                        "time": "5 s",
                        "flow": "min" // can be: min, max, percentage or a number
                    }
                }
            ],
            "request_time": new Date("<2020-08-06T12:00:00Z>"), // Z means UTC time
            "action_time": new Date("<2020-08-07T12:00:00Z>"),
            "comment": "Water the plant due to low pot humidity."
        } }
    ],
    { "ordered": false }
)

db.model_version.insertOne(
    { version: "0.3", date: new Date(), changes: "Initial data insertion (users, plants and first device registry)."}
)

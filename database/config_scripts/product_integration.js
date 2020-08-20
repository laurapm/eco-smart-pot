db = db.getSiblingDB('admin')

db.auth({
    user: "gardener",
    pwd: "eco-app-plant"
})

db = db.getSiblingDB('eco')

db.owner.renameCollection('user')

db.user.update(
    {},
    { $set: { "role": "user" } },
    false,
    true
)

db.model_version.insert(
    [
        { version: "0.3.1", date: new Date(), changes: "OWNER is now USER collection."},
        { version: "0.3.2", date: new Date(), changes: "Added field USER.ROLE. Update USER documents to fit new field."},
    ],
    { "ordered": true }
)

var aprobado_id = ObjectId()
var eco_id      = ObjectId()

db.product.insert(
    [
        {
            "_id": eco_id,
            "name": "eco smart pot",
            "price": 60.0,
            "description": "Rainforest best product. A little something so you don't have to worry about your plants"
        },
        {
            "name": "red pot",
            "price": 10.0,
            "description": "Phones have covers, we have colorful pots"
        },
        {
            "name": "blue pot",
            "price": 10.0,
            "description": "Phones have covers, we have colorful pots"
        },
        {
            "name": "green pot",
            "price": 10.0,
            "description": "Phones have covers, we have colorful pots"
        },
        {
            "name": "pink pot",
            "price": 10.0,
            "description": "Phones have covers, we have colorful pots"
        },
        {
            "_id": aprobado_id,
            "name": "aprobado",
            "price": 0,
            "description": "Profes, mirad que pedazo de proyecto os estamos haciendo. Esto se merece un 10."
        }
    ],
    { "ordered": false }
)

pablo_id = ObjectId("5f3184c0ede1e7f42f37c62b")
david_id = ObjectId("5f3184c0ede1e7f42f37c62e")
laura_id = ObjectId("5f3184bfede1e7f42f37c629")

db.ticket.insert(
    [
        {
            "owner": pablo_id,
            "item": [
                {
                    "product": aprobado_id,
                    "quantity": 2
                }
            ],
            "date": new Date()
        },
        {
            "owner": david_id,
            "item": [
                {
                    "product": aprobado_id,
                    "quantity": 1
                }
            ],
            "date": new Date()
        },
        {
            "owner": laura_id,
            "item": [
                {
                    "product": aprobado_id,
                    "quantity": 5
                },
                {
                    "product": eco_id,
                    "quantity": 1
                }
            ],
            "date": new Date()
        }
    ],
    { "ordered": false }
)

db.model_version.insert(
    [
        { version: "0.3.1", date: new Date(), changes: "OWNER is now USER collection. Also added field ROLE."},
        { version: "0.3.2", date: new Date(), changes: "Update USER documents to fit new field."},
        { version: "0.4", date: new Date(), changes: "Include PRODUCT and TICKET collections."},
        { version: "0.4.1", date: new Date(), changes: "TICKET.DATE field added."},
        { version: "0.4.2", date: new Date(), changes: "Bug fixes. Incorrect data type for PRODUCT.DESCRIPTION"},
        { version: "0.4.3", date: new Date(), changes: "Sub-document for items in TICKET.{ITEM, QUANTITY}."},
        { version: "0.4.1", date: new Date(), changes: "Bug fixes. Incorrect data type for PRODUCT.DESCRIPTION"}
    ],
    { "ordered": true }
)

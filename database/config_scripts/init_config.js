use admin

db.createUser({
  user: "root",
  pwd: "root",
  roles : [ { db: "admin", role: "root" } ]
})

db.auth({
  user: "root",
  pwd: "root"
})

db.createUser({
  user: "data-analyst",
  pwd: "analyst",
  roles : [
    { db: "admin", role: "clusterAdmin" }, 
	{ db: "admin", role: "backup" },
	{ db: "admin", role: "restore" },
	{ db: "admin", role: "readAnyDatabase" }
  ]
})

db.createUser({
  user: "eco-manager",
  pwd: "manager",
  roles : [ { db:"eco", role:"dbOwner" } ]
})

db.createUser({
  user: "gardener",
  pwd: "eco-app-plant",
  roles : [ { db:"eco", role:"readWrite" } ]
})

use eco

db.model_version.insert(
   [
     { version: "0", changes: "Creation of eco database and model_version and a way to track changes between versions." },
     { version: "0.1", date: new Date(), changes: "Created users." }
   ]
)

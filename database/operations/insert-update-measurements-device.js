db.measurements.update( 
  // Match Filter
  { "_id": "<objectId>", "plant": "<objectId>", "device": "<objectId>", "date": "<Date>", "hour": "<int>" }, 
  // Insert new element into   
  { $push: { // Adds (appends) the arrays the values specified 
    "humidityInt": { $each: [ 
	  { 
	    "minute": "<int>", 
	    "measure": { 
	      "watered": "<bool>", 
		  "value": [ 
		    "<int>"
		  ] 
		} 
	  } 
	] },
	"humidityExt": { $each: [ // Appends the documents inside the array to the 
	  {                       // specified field
	    "minute": "<int>", 
	    "measure": "<double>"
	  } 
	] },
	"luminosityExt": { $each: [ 
	  { 
	    "minute": "<int>", 
	    "measure": "<int>"
	  } 
	] },
	"temperatureExt": { $each: [ 
	  { 
	    "minute": "<int>", 
	    "measure": "<double>"
	  } 
	] } 
  } }, 
  // If the document does not exist to update, it creates it
  // Also includes the fields at the filter
  { upsert: true } 
)
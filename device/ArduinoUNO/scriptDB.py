import time
import datetime
import serial
import json
from pymongo import MongoClient
from bson.objectid import ObjectId

# CONNECTION TO MONGODB
uri = "mongodb://gardener:eco-app-plant@ch1r0n.duckdns.org:10072,ch1r0n.duckdns.org:20072,ch1r0n.duckdns.org:30072/admin"
client = MongoClient(uri)

mongodb_db  = "eco"                                      # Database's name
db          = client[mongodb_db]                         # Use database
collection  = db['measurements']                         # Select collection

# DEFINE SERIAL PORT
serial_port  = "/dev/ttyACM0"                            # If ESP32 is used, it migth be /dev/ttyUSB0
ser = serial.Serial(serial_port, 9600, timeout=0)        # Using an ESP32 the frecuency must be 115200


id = ObjectId()                                          # Document identification
generationHour = datetime.datetime.today().hour          # Time when a new document is created

while True:
    # Data for define the match filter date and hour
    dt = datetime.datetime.today()
    year    = dt.year
    month   = dt.month
    day     = dt.day
    hour    = dt.hour


    date    = datetime.datetime(year, month, day, hour)  # Python uses datetime as equivalen to Mongo Date.
                                                         # Seconds and minutes are removed due to a standard in the application

    # Every hour is generated a new id
    if generationHour != hour:
        id =  ObjectId()
        generationHour = hour

    match_filter = {
        "_id":    id,
        "plant":  "",
        "device": "",
        "date":   date,
        "hour":   hour
    }

    try:
        serial_info = ser.readline()                    # Read from serial port

        if serial_info:

            info = json.loads(serial_info)              # Convert to json

            # Setting id for device and plant
            match_filter["device"] = ObjectId(info["device"])
            info.pop("device")

            dev = client["eco"]["device"].find_one(
                    {"_id":match_filter["device"]},
                    {"_id":0, "plant":1}
                )
            match_filter["plant"] = ObjectId(dev["plant"])   # Adding plant inside the match filter



            minute = (int) (5* round(dt.minute/5))           # Measurements are taken every 5 minutes,
                                                             # for that it is used a 5-based time system
            info["humidityInt"][0]["minute"]    = minute
            info["humidityExt"][0]["minute"]    = minute
            info["luminosityExt"][0]["minute"]  = minute
            info["temperatureExt"][0]["minute"] = minute

            info["humidityInt"] = {"$each":info["humidityInt"]}
            info["humidityExt"] = {"$each":info["humidityExt"]}
            info["luminosityExt"] = {"$each":info["luminosityExt"]}
            info["temperatureExt"] = {"$each":info["temperatureExt"]}

            # Measurements to add
            values = { "$push": info}

            # Update operation
            doc = collection.update(match_filter,values,upsert=True)    # The upsert value is used to generate a new
                                                                        # document when it doesn't exist a file that match
                                                                        # with match_filter value's

            print(info)

        else:
            print("Waiting\n")

    except serial.SerialTimeoutException:
        print("Error! Could not read data from serial port")

    finally:
        time.sleep(60)



import time
import datetime
import serial
import json
from pymongo import MongoClient
from bson.objectid import ObjectId

# COONECTION TO MONGODB
uri = "mongodb://root:root@ch1r0n.duckdns.org:10072,ch1r0n.duckdns.org:20072,ch1r0n.duckdns.org:30072/admin"

client = MongoClient(uri)
mongodb_db  = "bezkoder_db"
db          = client[mongodb_db]
collection  = db['tutorials']

# DEFINE SERIAL PORT
serial_port  = "/dev/ttyACM0"
ser = serial.Serial(serial_port, 9600, timeout=0)

id = ObjectId()
generationHour = datetime.datetime.today().hour

while True:
    # Data for define the match filter
    dt = datetime.datetime.today()
    year    = dt.year
    month   = dt.month
    day     = dt.day
    hour    = dt.hour

    d    = datetime.datetime(year, month, day, hour)
    date = time.mktime(d.timetuple()) * 1000

    dev = client["eco"]["device"].find_one({"_id":ObjectId("5f4d3798d0df9a7447ca25e2")}, {"_id":0, "plant":1})


    plant = ObjectId(dev["plant"])


    if generationHour != hour:
        id =  ObjectId()
        generationHour = hour

    match_filter = {
        "_id": id,
        "plant": plant,
        "device": "",
        "date":  date,
        "hour": hour
    }

    try:
        serial_info = ser.readline()

        if serial_info:
            info = json.loads(serial_info)

            match_filter["device"] = ObjectId(info["device"])
            info.pop("device")

            print(info)

            values = { "$push": info}

            minute = (int) (5* round(dt.minute/5))

            info["humidityInt"][0]["minute"]    = minute
            info["humidityExt"][0]["minute"]    = minute
            info["luminosityExt"][0]["minute"]  = minute
            info["temperatureExt"][0]["minute"] = minute

            doc = collection.update(match_filter,values,upsert=True)

            print("Everything is gonna be alright")

        else:
            print("Waiting\n")

    except serial.SerialTimeoutException:
        print("Error! Could not read data from serial port")

    finally:
        time.sleep(15)



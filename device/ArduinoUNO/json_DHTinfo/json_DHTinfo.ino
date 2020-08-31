#include "DHT.h"
#include "SimpleTimer.h"
#include "ArduinoJson.h"

#define DHTPIN 2
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);
SimpleTimer extTimer;
SimpleTimer sendJSON;

double extTem;
double extHumidity;
/*------------------------- */
/*   F U N C T I O N
 *   D E C L A R A T I O N  */
/*------------------------- */
void takeExternalData();

double getExtHumidity();
double getExtTemp();

void createJSON();
void merge(JsonObject dest, JsonObjectConst src);


/*            *
 * MAIN CODE  *
 *            */    

void setup() {
  Serial.begin(9600);

  dht.begin();

  extTimer.setInterval(5000, takeExternalData);
  sendJSON.setInterval(15000, createJSON);
}

void loop() {
  extTimer.run();
  sendJSON.run();
}


/************  A U X I L I A R       F U N C T I O N S  ************/
void merge(JsonObject dest, JsonObjectConst src) {
   for (auto kvp : src) {
     dest[kvp.key()] = kvp.value();
   }
}

void createJSON(){
  
    StaticJsonDocument<480> doc1, doc2;
    
    doc1["_id"]="1";
    doc1["_plant"]="ficus";
    doc1["device"]="Arduino UNO";
    doc1["date"]= "30/08/2020";
    doc1["hour"]="0";

    // JsonArray array = doc2.to<JsonArray>();
    JsonArray datos = doc2.createNestedArray("humidityInt");
    JsonObject root = datos.createNestedObject();
    root["minute"]="46";
    JsonObject measure = root.createNestedObject("measure");
    measure["watered"]="123";
    measure["value"]="[]";
    /*JsonArray value = measure.createNestedArray("value");

    if (irrigate){
      for (int i=0; i < 5 ; i++){
        value.add(arrayHumidity[i]);
      }
    }
    else{
      value.add(arrayHumidity[0]);
    }   */  

   JsonArray datosExt = doc2.createNestedArray("humidityExt");
   JsonObject infoExt = datosExt.createNestedObject();
   infoExt["minute"]="112";
   infoExt["measure"]=extHumidity; 

   JsonArray lumExt = doc2.createNestedArray("luminosityExt");
   JsonObject infoLum = lumExt.createNestedObject();
   infoLum["minute"]="223";
   infoLum["measure"]="213";

   JsonArray tempExt = doc2.createNestedArray("temperatureExt");
   JsonObject infoTemExt = tempExt.createNestedObject();
   infoTemExt["minute"]="335";
   infoTemExt["measure"]=extTem;
    
   merge(doc1.as<JsonObject>(), doc2.as<JsonObject>());
    
   serializeJsonPretty(doc1, Serial);
}

void takeExternalData(){
  extTem = getExtTemp();
  extHumidity = getExtHumidity();
}

double getExtTemp()
{
  // By default the temperature is given in Celsius
  double const temperature = dht.readTemperature();
  if (isnan(temperature) ) {
    return -9898;
  }
  return temperature;
}

double getExtHumidity()
{
  double const humidity = dht.readHumidity();

  // Check if the read failed and exit early (to try again).
  if (isnan(humidity) ) {
    return -99999;
  }
    return humidity;
}

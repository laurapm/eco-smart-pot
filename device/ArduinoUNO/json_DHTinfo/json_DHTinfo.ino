#include "DHT.h"
#include "SimpleTimer.h"
#include "ArduinoJson.h"

#define YL69 A0

#define DHTPIN 2
#define DHTTYPE DHT22

#define LDR A2

DHT dht(DHTPIN, DHTTYPE);
SimpleTimer extTimer;
SimpleTimer sendJSON;
SimpleTimer humidity;
SimpleTimer ligth;

double extTem;
double extHumidity;
double intHumidity;
double luminosity;
double* arrayHumidity;

bool irrigate = true;

size_t count;
size_t capacity;
/*------------------------- */
/*   F U N C T I O N
 *   D E C L A R A T I O N  */
/*------------------------- */
void takeExternalData();
void takeHumidity();
void takeLigth();

double getExtHumidity();
double getExtTemp();
double getHumidity();
double getLigth();

void createJSON();
void merge(JsonObject dest, JsonObjectConst src);


/*            *
 * MAIN CODE  *
 *            */    

void setup() {

  Serial.begin(9600);
  dht.begin();

  extTimer.setInterval(6000, takeExternalData);
  humidity.setInterval(6000, takeHumidity);
  sendJSON.setInterval(15000, createJSON);
}

void loop() {
  extTimer.run();
  sendJSON.run();
  humidity.run();
}


/************  A U X I L I A R       F U N C T I O N S  ************/
void merge(JsonObject dest, JsonObjectConst src) {
   for (auto kvp : src) {
     dest[kvp.key()] = kvp.value();
   }
}

void createJSON(){
  
    // DynamicJsonDocument doc2(220);

     StaticJsonDocument<480>  doc2;

    // JsonArray array = doc2.to<JsonArray>();
    JsonArray datos = doc2.createNestedArray("humidityInt");
    JsonObject root = datos.createNestedObject();
    root["minute"]="46";
    JsonObject measure = root.createNestedObject("measure");
    measure["watered"]="123";
    //measure["value"]="[]";
    JsonArray value = measure.createNestedArray("value");
    value.add(intHumidity);
     // value.add(arrayHumidity[0]);
    

   if (extHumidity != -99999){
     JsonArray datosExt = doc2.createNestedArray("humidityExt");
     JsonObject infoExt = datosExt.createNestedObject();
     infoExt["minute"]="112";
     infoExt["measure"]=extHumidity; 
   }

   JsonArray lumExt = doc2.createNestedArray("luminosityExt");
   JsonObject infoLum = lumExt.createNestedObject();
   infoLum["minute"]="213";
   infoLum["measure"]=luminosity;

   if (extTem != -9898){
      JsonArray tempExt = doc2.createNestedArray("temperatureExt");
      JsonObject infoTemExt = tempExt.createNestedObject();
      infoTemExt["minute"]="335";
      infoTemExt["measure"]=extTem;
   }
   
    
  // merge(doc1.as<JsonObject>(), doc2.as<JsonObject>());
    
   serializeJsonPretty(doc2, Serial);
   Serial.println("");
   delay(500);
}

void takeExternalData(){
  //Serial.println("HOLA, HAGO COSAS");
  extTem = getExtTemp();
  extHumidity = getExtHumidity();
}

void takeHumidity(){
  intHumidity = getHumidity();
}

void takeLigth(){
 luminosity = getLigth();
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

double getHumidity(){
  double const readValueYL69 = analogRead(YL69);
  double const convertedPercentage = map(readValueYL69, 0, 1023, 100, 0);

  return convertedPercentage;
}

double getLigth(){
  int const readLDRvalue = analogRead(LDR);
  int ligth = map(readLDRvalue, 0, 370, 0, 100);

  return ligth;
}

#include "DHT.h"
#include "SimpleTimer.h"
#include "ArduinoJson.h"

#define YL69 A0         // Analog pin for moisture sensor

#define DHTPIN 2       // Digital pin for external temperature and humidity 
#define DHTTYPE DHT22

#define LDR A2        // Analog pin for LDR

#define waterPump A5  // Analog pin for waterpum

// Init DHT sensor
DHT dht(DHTPIN, DHTTYPE);

// Create timers
SimpleTimer extTimer;
SimpleTimer sendJSON;
SimpleTimer humidity;
SimpleTimer ligth;

// Varuiables for measurements
double extTem;
double extHumidity;
double intHumidity;
double luminosity;
double* arrayHumidity;

bool irrigate;

/*------------------------- */
/*   F U N C T I O N
 *   D E C L A R A T I O N  */
/*------------------------- */
void takeExternalData();
void takeHumidity();
void takeLigth();

void createJSON();
void merge(JsonObject dest, JsonObjectConst src);

double getExtHumidity();
double getExtTemp();
double getHumidity();
double getLigth();

bool isIrrigating();

/*            *
 * MAIN CODE  *
 *            */    

void setup() {

  Serial.begin(9600);
  dht.begin();

  extTimer.setInterval(10000, takeExternalData);
  humidity.setInterval(10000, takeHumidity);
  sendJSON.setInterval(20000, createJSON);
}

void loop() {
  extTimer.run();
  sendJSON.run();
  humidity.run();
}


/************  A U X I L I A R       F U N C T I O N S  ************/

// Funcion that diven two JSON return another with the combination of both
void merge(JsonObject dest, JsonObjectConst src) {
   for (auto kvp : src) {
     dest[kvp.key()] = kvp.value();
   }
}

// Returns the JSON file which contains device and measurements data
void createJSON(){
    // Init JSON files as Static elements which 480 of size
    StaticJsonDocument<480> doc1, doc2;

    // First document that only contains id device info
    JsonObject dev=doc1.to<JsonObject>();
    doc1["device"] = "5f4d3798d0df9a7447ca25e2";
     
    // Second document which contains all  the measurements
    // Minute value is replaced before in the Python script
    JsonArray datos = doc2.createNestedArray("humidityInt");
    JsonObject root = datos.createNestedObject();
    root["minute"]="46";
    JsonObject measure = root.createNestedObject("measure");
    measure["watered"]= irrigate;
    JsonArray value = measure.createNestedArray("value");
    value.add(intHumidity);
    

  // In case DHT returns an allowed value, the information 
  // is add to the document, otherwise it is omited
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
   
    // Both documents are merged  
    merge(doc1.as<JsonObject>(), doc2.as<JsonObject>());

   // Send JSON to Serial port
   serializeJson(doc1, Serial);
   Serial.println("");
   delay(500);
}

// Take Functions are only functions that call other functions
// that returns the data

void takeExternalData(){
  extTem = getExtTemp();
  extHumidity = getExtHumidity();
}

void takeHumidity(){
  intHumidity = getHumidity();
  isIrrigating();
}

void takeLigth(){
 luminosity = getLigth();
}


// Get measurements values 
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


// Function that activate waterpump in case the plant need water
bool isIrrigating(){
  if (intHumidity <= 35){
    digitalWrite(waterPump, LOW);
    humidity.setInterval(5000, takeHumidity);
    irrigate =  true;
  }
  else{
    digitalWrite(waterPump, HIGH);
    humidity.setInterval(10000, takeHumidity);
    irrigate = false;
  }

  return irrigate;
}

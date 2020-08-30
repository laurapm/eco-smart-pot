/* ------------------------- */
/*     L I B R A R I E S     */
/* ------------------------- */
// Timer function
#include <SimpleTimer.h>
#include "ArduinoJson.h"
#include "Time.h"

// Temperature & humidity sensor (Library, pin connected and type)
#include "DHT.h" 
#define DHTPIN 2 
#define DHTTYPE DHT22 

// YL 69 Internal humidity
#define YL69 A0

// LDR sensor
#define LDR A2

// Waterpump
#define waterPump A5

/* ------------------------- */
/*     V A R I A B L E S     */
/* ------------------------- */
DHT dht(DHTPIN, DHTTYPE);

SimpleTimer extTimer;
SimpleTimer humTimer;
SimpleTimer ligth;
SimpleTimer json;


/*  G L O B A L     V A R I A B L E S*/
int timeControl;

double intHumidity;
double extHumidity;
double luminosity;
double extTemp;

double* arrayHumidity;
size_t count;
size_t capacity;

bool irrigate;


/*------------------------- */
/*   F U N C T I O N
 *   D E C L A R A T I O N  */
/*------------------------- */
void takeExternalData();
void takeHumidity();
void takeAmLigth();
bool doAction();

void createJSON();
void merge(JsonObject dest, JsonObjectConst src);

void createList(size_t capacity);
void addItem(double item);
void trimR();
void resize(size_t _capacity);
void removeTail();
void clearArrayValues();

double getExtHumidity();
double getExtTemp();

/*------------------------- */
/*    F U N C T I O N S     */
/*------------------------- */
void setup() 
{
  Serial.begin(9600);         // Serial Port with frecuency
  pinMode(waterPump, OUTPUT); // Waterpump
  dht.begin();                // DHT
  createList(5);

  Serial.println("Init values \n");


  takeHumidity();
  takeExternalData();
  takeAmLigth();
  
  Serial.print("INTERNAL HUM: ");
  Serial.println(intHumidity);
  Serial.println("EXTERNAL HUM: ");
  Serial.println(extHumidity);
  Serial.println("LUM: ");
  Serial.println(luminosity);
  Serial.println("TEM: ");
  Serial.println(extTemp);
  Serial.println("----Array-----");

  for ( int i = 0; i < 5; i++){
    Serial.println(arrayHumidity[i]);
  }
  Serial.println("---");
  
  
  createJSON();

  // Warning!: Check you are using the correct library for Simple Timer
  extTimer.setInterval(60000,      takeExternalData);
  humTimer.setInterval(timeControl, takeHumidity);
  ligth.setInterval   (60000,      takeAmLigth);
  json.setInterval    (30000,      createJSON);  

}

void loop() 
{
  extTimer.run();
  humTimer.run();
  ligth.run();
  json.run();
}

void createList(size_t _capacity)
{
  arrayHumidity = new double[capacity];
  capacity = _capacity;
  count = 0;
}

void addItem(double item){
  ++count;

  if(count > capacity)
  {
    size_t newSize = capacity * 2;
    resize(newSize);
  }

  arrayHumidity[count -1] = item;
}

// Eliminar ultimo elemento de la lista
void removeTail()
{
  --count;
}

void trimR()
{
  resize(count);
}


// Reescalar lista
void resize(size_t newCapacity)
{
  double* newList = new double[newCapacity];
  memmove(newList, arrayHumidity, count  * sizeof(int));
  delete[] arrayHumidity;
  capacity = newCapacity;
  arrayHumidity = newList;
}

void merge(JsonObject dest, JsonObjectConst src) {
   for (auto kvp : src) {
     dest[kvp.key()] = kvp.value();
   }
}

void createJSON()
{
  // Define memory pool for JSON object tree

 // Serial.print("JSON TIME: ");
 // Serial.println(timeControl);

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
    measure["watered"]=irrigate;
    // measure["value"]=intHumidity;
    JsonArray value = measure.createNestedArray("value");

    for (int i=0; i < sizeof(arrayHumidity); i++){
      value.add(arrayHumidity[i]);
    }
    

   JsonArray datosExt = doc2.createNestedArray("humidityExt");
   JsonObject infoExt = datosExt.createNestedObject();
   infoExt["minute"]="112";
   infoExt["measure"]=extHumidity;

   JsonArray lumExt = doc2.createNestedArray("luminosityExt");
   JsonObject infoLum = lumExt.createNestedObject();
   infoLum["minute"]="223";
   infoLum["measure"]=luminosity;

   JsonArray tempExt = doc2.createNestedArray("temperatureExt");
   JsonObject infoTemExt = tempExt.createNestedObject();
   infoTemExt["minute"]="335";
   infoTemExt["measure"]=extTemp;
    
   merge(doc1.as<JsonObject>(), doc2.as<JsonObject>());
    
   serializeJsonPretty(doc1, Serial);

   //Serial.println("-------------");
   clearArrayValues();
}

void clearArrayValues()
{
  for (int i = 0; i < sizeof(arrayHumidity); i++){
    removeTail();
  }
}

double getExtHumidity()
{
  double const humidity = dht.readHumidity();

  // Check if the read failed and exit early (to try again).
  if (isnan(humidity) ) {
     Serial.println("Failed to read from DHT sensor!");
    //delay(1000); // wait a bit
    return 0;
  }
  else{
    return humidity;
  }
}

double getExtTemp()
{
  // By default the temperature is given in Celsius
  double const temperature = dht.readTemperature();
  if (isnan(temperature) ) {
    Serial.println("Failed to read from DHT sensor!");
    //delay(1000); // wait a bit
    return -0;
  }
  else{
    return temperature;
  }
}

double getHumidity()
{
  double const readValueYL69 = analogRead(YL69);
  double const convertedPercentage = map(readValueYL69, 0, 1023, 100, 0);

  return convertedPercentage;
}

bool doAction()
{
  // Serial.println("*********************************");
  // Serial.println("Estoy aqui ");
  
  if (intHumidity <= 35){
    digitalWrite(waterPump, LOW);
    timeControl = 5000;
    humTimer.setInterval(5000, takeHumidity);

    irrigate = true;
   // Serial.println(" y riego");
  }
  else{
     digitalWrite(waterPump, HIGH);
     timeControl = 60000;
     humTimer.setInterval(60000, takeHumidity);

     irrigate = false;
     // Serial.println(" y soy feliz");

  }
  // Serial.print("CONTROL TIME" );
  //  Serial.println("*********************************");

  return irrigate;
}



double getAmoungLigth()
{
  int const readLDRvalue = analogRead(LDR);
  int ligth = map(readLDRvalue, 0, 370, 0, 100);

  return ligth;
}

void takeExternalData()
{
  extTemp = getExtTemp();
  extHumidity = getExtHumidity();
}

void takeHumidity()
{
  intHumidity = getHumidity();
  addItem(intHumidity);
  doAction(); 
}

void takeAmLigth()
{
  luminosity = getAmoungLigth(); 
}

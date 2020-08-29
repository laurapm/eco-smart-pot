/* ------------------------- */
/*     L I B R A R I E S     */
/* ------------------------- */
// Timer function
#include <SimpleTimer.h>
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
int timeControl = 6000;
SimpleTimer extTimer;
SimpleTimer humTimer;
SimpleTimer ligth;

/*------------------------- */
/*   F U N C T I O N
 *   D E C L A R A T I O N  */
/*------------------------- */
void takeExternalData();
void takeHumidity();
void takeAmLigth();
void irrigate();

/*------------------------- */
/*    F U N C T I O N S     */
/*------------------------- */
void setup() {
  Serial.begin(9600); //Se inicia la comunicación serial 
  // Initializes the water pump.
  pinMode(waterPump, OUTPUT);
  dht.begin(); //Se inicia el sensor
  extTimer.setInterval(300000, takeExternalData);
  humTimer.setInterval(timeControl, takeHumidity);
  ligth.setInterval(300000, takeAmLigth);
}
void loop() {
  extTimer.run();
  humTimer.run();
  ligth.run();
}

void takeExternalData(){
  // *** DHT22 measurement ***
  //use the functions which are supplied by library.
  double const humidity = dht.readHumidity();
  // Read temperature as Celsius (the default)
  double const temperature = dht.readTemperature();
  
  // Check if any reads failed and exit early (to try again).
  if (isnan(humidity) || isnan(temperature)) {
    Serial.println("Failed to read from DHT sensor!");
    delay(1000); // wait a bit
    return;
  }
  
  else { // print the result to Terminal
    Serial.print("Humidity (DHT22): ");
    Serial.print(humidity);
    Serial.print(" %\t");
    Serial.print("Temperature (DHT22): ");
    Serial.print(temperature);
    Serial.println(" °C ");

  }
}

void takeHumidity()
{
  int const readValueYL69 = analogRead(YL69);
  Serial.print(readValueYL69);
  int const convertedPercentage = map(readValueYL69, 0, 1023, 100, 0);
  Serial.print("Moisture (YL-69): ");
  Serial.print(convertedPercentage);
  Serial.println("%");

  if (readValueYL69 >= 670){
    digitalWrite(waterPump, HIGH);
    timeControl = 1000;
    humTimer.setInterval(timeControl, takeHumidity);
    Serial.print("START ");
  }
  else{
     digitalWrite(waterPump, LOW);
     timeControl = 6000;
     humTimer.setInterval(timeControl, takeHumidity);
     Serial.print("STOP");
  }

   Serial.print("CONTROL TIME" );
   Serial.println(timeControl);
}
void irrigate(){
  digitalWrite(waterPump, HIGH);
  humTimer.setInterval(3500, takeHumidity);
  
}
void takeAmLigth()
{
  int const readLDRvalue = analogRead(LDR);
  int ligth = map(readLDRvalue, 0, 370, 0, 100);
  Serial.print(readLDRvalue);
  Serial.print("Amoung of ligth: ");
  Serial.print(ligth);
  Serial.println("% \n");
  
  /*int const readLDRvalue = analogRead(LDR);
  //double ilum =((int)readLDRvalue*10000) / ((int)150*(1024-readLDRvalue));
  double ilum = ((long)(1024-readLDRvalue)*10000)/((long)150*readLDRvalue);
  Serial.print("Amoung of ligth: ");
  Serial.print(ilum);
  Serial.println("% \n");*/
  
}

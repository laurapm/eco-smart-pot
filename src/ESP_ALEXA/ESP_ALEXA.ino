#include <WiFi.h>

#include <Espalexa.h>

int LED = 5;
int ECOWATER = 0;

/* NETWORK DATA*/ 

const char* ssid = "MiFibra-C646";
const char* password = "9AkavUxV";

/* OBJECT THAT WILL BE CONNECTED
in this case, Alexa */

Espalexa alexaConection; //alexita

void funcionRegar(uint8_t brigthness);

void setup() {
  /* Init serial comunication*/
  Serial.begin(9600);
  pinMode(LED, OUTPUT);
  pinMode(ECOWATER, OUTPUT);
  WifiConnection();
  alexaConection.addDevice("ECOWATER", funcionRegar);
  alexaConection.begin();
}

void loop() {
  /* We call the WiFi Connection function just in case
  is there is sometime that the WiFi is lost, it will 
  try to reconnect by itself.
  */ 
  WifiConnection();
  alexaConection.loop();
  delay(1);
}

/* Routine for activating ESP's wifi, in case it is 
swich off, just for avoying restarting the program
ESP's internal led will be used to notify if it is 
working or not */

void WifiConnection(){
  if(WiFi.status()!= WL_CONNECTED){
      WiFi.mode(WIFI_STA);
      WiFi.begin(ssid, password);
      Serial.println("");
      Serial.println("Connecting to WiFi");
      while(WiFi.status()!= WL_CONNECTED){
        digitalWrite(LED, 1); // Turn off
        delay(500);
        digitalWrite(LED, 0); // Turn on
        delay(500);
        Serial.print(".");
    }
    /* RETURN NETWORK DATA CONNECTION IF EVERYTHING IS OK*/
    Serial.print("Connected to ");
    Serial.println(ssid);
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
  }
}

void funcionRegar(uint8_t brigthness){
  Serial.println("Funcion Regar -");
  if(brigthness){
    digitalWrite(ECOWATER, 1);
    Serial.println(" REGANGO... ");
    delay(5);
    Serial.println(" OFF ");
  }
  else{
    digitalWrite(ECOWATER, 0);
    Serial.println(" OFF ");
  }
}

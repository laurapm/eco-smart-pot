/*--------------------------*/
/*    L I B R A R I E S     */
/*--------------------------*/
#include <WiFi.h>
//#include <PubSubClient.h>
//#include <WiFiClient.h>
#include "HTTPClient.h"

#include <DHT.h>

extern "C"{
#include "sh2lib.h"
}


/*--------------------------*/
/*    W I F I   D A T A     */
/*--------------------------*/
#define SSID_NAME "MiFibra-C646"            // Change to your Wifi Network name
#define SSID_PASSWORD "9AkavUxV"           // Change to your Wifi network password


/*--------------------------*/
/*    V A R I A B L E S     */
/*--------------------------*/
struct sh2lib_handle hd;
HTTPClient http;

 
/*--------------------------*/
/*     F U N C T I O N 
 *     D E C L A R A T I O N
/*--------------------------*/
void WiFiConnection();
void ServerConnection();
void ServerDisconnection();

void setup() {
  Serial.begin(115200);
  WiFiConnection();
  ServerConnection();
  //ServerDisconnection();  
}
 
void loop() {
  post_req();
  delay(1000);
  put_req();
  delay(1000);
  }

void WiFiConnection(){
  if(WiFi.status() != WL_CONNECTED){
     WiFi.mode(WIFI_STA);
     WiFi.begin(SSID_NAME, SSID_PASSWORD);
     Serial.println("Connecting ...");
     while (WiFi.status() != WL_CONNECTED) {
       delay(500);
       Serial.print(".");
     }
  } 
  Serial.println('\n');
  Serial.print("Connected to ");
  Serial.println(WiFi.SSID());             
  Serial.print("IP address:\t");
  Serial.println(WiFi.localIP());
  delay(500);  
}

void ServerConnection(){
  if (sh2lib_connect(&hd, "https://http2.golang.org") != ESP_OK) {
      Serial.println("Error connecting to HTTP2 server");
      return;
  }
 
  Serial.println("Connected");
  Serial.println(hd.hostname);  
}

void ServerDisconnection(){
  sh2lib_free(&hd);
 
  Serial.println("Disconnected");
  }

void put_req(){
  const char* data = "PUT sent from ESP32"; // HERE CHANGE WITH THE DATA THAT MUST BE SEND
  
   http.begin("http://jsonplaceholder.typicode.com/posts/1"); // CHANGE WITH THE CORRESPONDING SERVER'S URL 
   http.addHeader("Content-Type", "text/plain");            
 
   int httpResponseCode = http.PUT(data);   // PUT request passing as a paramenter the data to sebd
 
   if(httpResponseCode>0){
 
    String response = http.getString();   
 
    Serial.println(httpResponseCode);
    Serial.println(response);          
 
   }else{
 
    Serial.print("Error on sending PUT Request: ");
    Serial.println(httpResponseCode);
 
   }
 
   http.end();
}

void post_req(){
   http.begin("http://jsonplaceholder.typicode.com/posts");  //Specify destination for HTTP request
   http.addHeader("Content-Type", "text/plain");             //Specify content-type header
 
   int httpResponseCode = http.POST("TRYING TO POST STH");   //Send the actual POST request
 
   if(httpResponseCode>0){
 
    String response = http.getString();                       //Get the response to the request
 
    Serial.println(httpResponseCode);   //Print return code
    Serial.println(response);           //Print request answer
 
   }else{
 
    Serial.print("Error on sending POST: ");
    Serial.println(httpResponseCode);
 
   }
 
   http.end();  //Free resources  
}


#include <DHT.h>
#include <WiFi.h>
#ifdef __cplusplus
extern "C" {
#endif
uint8_t temprature_sens_read();
#ifdef __cplusplus
}
#endif  
uint8_t temprature_sens_read();

const int DHTPin = 23;

//our sensor is DHT11 type
#define DHTTYPE DHT22

//create an instance of DHT sensor
DHT dht(DHTPin, DHTTYPE);

const char* SSID_NAME="MiFibra-C646";
const char* SSID_PASSWORD = "9AkavUxV";
//const char* host = "localhost";
String strurl = "/eco/conectar.php";
char host[48]="192.168.1.*";


void WiFiConnection()
{
  // Connect in case it has not been connected already.
  if(WiFi.status() != WL_CONNECTED)
  {
    WiFi.mode(WIFI_STA);
    WiFi.begin(SSID_NAME, SSID_PASSWORD);
  // Tries to connect
    Serial.println("Connecting ...");
    while (WiFi.status() != WL_CONNECTED)
  {
      delay(500);
      Serial.print(".");
    }
  }

  // Connected Successfully.
  Serial.println('\n');
  Serial.print  ("Connected to ");
  Serial.println(WiFi.SSID());
  Serial.print  ("IP address:\t");
  Serial.println(WiFi.localIP());
  delay(500);
}

void readData(){

}

void sendData(){
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

  // print the result to Terminal
  Serial.print("Humidity (DHT22): ");
  Serial.print(humidity);
  Serial.print(" %\t");
  Serial.print("Temperature (DHT22): ");
  Serial.print(temperature);
  Serial.println(" °C ");
  
  WiFiClient client;
  const int httpPort = 5555;

  if(!client.connect(srhost, httpPort)){
    Serial.println("Connection failed");
    return;
  }

  client.print(String("GET http://localhost/eco/connect.php?")+
                      ("&temperature=") + temperature +
                      ("&humidity") + humidity +
                      "HTTP/1.1\r\n" +
                      "Host: " + strhost +"\r\n" +
                      "Connection: clore\r\n\r\n");
        //client.print(String("POST ") + strurl + " HTTP/1.1" + "\r\n" + 
          //     "Host: " + strhost + "\r\n" +
          //     "Accept: */*" + "*\r\n" +
          //     "Content-Length: " + datos.length() + "\r\n" +
          //     "Content-Type: application/x-www-form-urlencoded" + "\r\n" +
          //     "\r\n" + datos);  
                      
   unsigned long timeout = millis();
   while(client.available() == 0){
      if (millis() - timeout > 1000){
        Serial.println(">>> Client Timeout !");
        client.stop();
        return;
      }
   }

   while(client.available()) {
      String line = client.readStringUntil('\r');
      Serial.print(line);
   }

   Serial.println();
   Serial.println("Closing connection");
}

void setup() {
  Serial.begin(115200);
  dht.begin();
  WiFiConnection();
}

void loop() {
  readData();

}

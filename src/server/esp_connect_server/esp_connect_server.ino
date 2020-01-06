#include "WiFi.h"
 
extern "C"{
#include "sh2lib.h"
}
 
const char* ssid = "+++++";
const char* password =  "+++++";
 
void setup() {
  Serial.begin(115200);
 
  WiFi.begin(ssid, password);
 
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi..");
  }
 
  struct sh2lib_handle hd;
 
  if (sh2lib_connect(&hd, "https://http2.golang.org") != ESP_OK) {
      Serial.println("Error connecting to HTTP2 server");
      return;
  }
 
  Serial.println("Connected");
  Serial.println(hd.hostname);
 
  sh2lib_free(&hd);
 
  Serial.println("Disconnected");
}
 
void loop() {}

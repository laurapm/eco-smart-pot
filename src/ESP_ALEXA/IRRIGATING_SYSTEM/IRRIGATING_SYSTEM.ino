/*--------------------------*/
/*    L I B R A R I E S     */
/*--------------------------*/
#include <WiFi.h>
#include <PubSubClient.h>
#include <WiFiClient.h>

/*--------------------------*/
/*    W I F I   D A T A     */
/*--------------------------*/
#define SSID_NAME "****"            // Changue to your Wifi Network name
#define SSID_PASSWORD "******"     // Change to your Wifi network password

/*--------------------------*/
/*     S M A R T N E S T
 *     M Q T T  B R O K E R 
 *     D A T A 
 /*--------------------------*/
#define MQTT_BROKER "smartnest.cz" //
#define MQTT_PORT 1883 //
#define MQTT_USERNAME "l.perezm" //
#define MQTT_PASSWORD "Ubicua2019" //
#define MQTT_CLIENT "5e0f5b6f4ff3c4137d21ef73" //Info provided by smartnest.cz/mynestV2 


/*--------------------------*/
/*    V A R I A B L E S     */
/*--------------------------*/

WiFiClient espClient;
PubSubClient client(espClient); 
int waterPump=2;


/*--------------------------*/
/*     F U N C T I O N 
 *     D E C L A R A T I O N
/*--------------------------*/
void WiFiConnection();
void initMQTT();
void checkMqtt();
int splitTopic(char* topic, char* tokens[] ,int tokensNumber);
void callback(char* topic, byte* payload, unsigned int length);



/*--------------------------*/
void setup() {
  pinMode(waterPump, OUTPUT);
  Serial.begin(115200);
  WiFiConnection();
  initMQTT();
}

void loop() {
  client.loop();
  checkMqtt();
}
/*--------------------------*/

// Function to execute when a new msg has been received
/*
 * CALLBACK FUNCTION IMPORTANT INFO:
 * MQTT HAS 3 BASIC CONCEPTS:
 *    1. TOPICS: is like the address for a msg.
 *         In our case, it woul look like Home/bedroom/waterpumpAlexa/Turn
 *         Where the msg is ON or OFF
 *    2. PUBLISH/SUBSCRIBE SYSTEM: Every device can subscribe to topics, and
 *       also can receive all the msg sent with the topics they are subscibe.
 *       ! WHEN THE DEVICE WANT TO PUBLISH A MSG THE TOPIC MUST BE PROVIDED
 *    3. BROKER: Is the central device who allows and restricts connections 
 *       and also, receives, filters, redirects and publishes msg to 
 *       all devices connected. In this case, the broker is Smartnest Server
 *
 */
void callback(char* topic, byte* payload, unsigned int length) { 
  Serial.print("Topic:");
  Serial.println(topic);
   int tokensNumber=10;
   char *tokens[tokensNumber];
   char message[length+1];
   splitTopic(topic, tokens, tokensNumber);
   sprintf(message,"%c",(char)payload[0]);
   for (int i = 1; i < length; i++) {
    sprintf(message,"%s%c",message,(char)payload[i]);
   }
    Serial.print("Message:");
    Serial.println(message);
    
     char reportChange[100];
     sprintf(reportChange,"%s/report/powerState",MQTT_CLIENT);
 
//------------------ACTIONS HERE---------------------------------

  if(strcmp(tokens[1],"directive")==0 && strcmp(tokens[2],"powerState")==0){
    if(strcmp(message,"ON")==0){
      digitalWrite(waterPump, LOW); 
      client.publish(reportChange, "ON") ;            // Sends the new status to the Server, Do not put this in a for loop or your device will be blocked 
      }
    else if(strcmp(message,"OFF")==0){
      digitalWrite(waterPump, HIGH);                   
      client.publish(reportChange, "OFF");           // Sends the new status to the Server, Do not put this in a for loop or your device will be blocked 
      }
  }
}

// Connect ESP to WIFI
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

// Starting MQTT service
  void initMQTT(){
  client.setServer(MQTT_BROKER, MQTT_PORT);
  client.setCallback(callback);

  while (!client.connected()) {
    Serial.println("Connecting to MQTT...");
    if (client.connect(MQTT_CLIENT, MQTT_USERNAME, MQTT_PASSWORD )) {
      Serial.println("connected");
      } else {
      Serial.print("failed with state ");
      Serial.print(client.state());
      delay(0x7530);
    }
  }
  
  char reportTopic[100];
  char publishTopic[100];
  sprintf(reportTopic,"%s/report/online",MQTT_CLIENT);
  sprintf(publishTopic,"%s/#",MQTT_CLIENT);
  client.subscribe(publishTopic);
  client.publish(reportTopic, "true");
  }

// SPLIT THE TOPIC. Eg: Home/bedroom/waterpumpAlexa/Turn --> [Home, bedroom, waterpumpAlexa, Turn]
  int splitTopic(char* topic, char* tokens[],int tokensNumber ){
    const char s[2] = "/";
    int pos=0;
    tokens[0] = strtok(topic, s);
    while(pos<tokensNumber-1 && tokens[pos] != NULL ) {
        pos++;
      tokens[pos] = strtok(NULL, s);
    }
    return pos;
  }

// IF MQTT HASN'T BEEN INIT BEFORE, TRY IT AGAIN
  void checkMqtt(){
    if(!client.connected()){
    initMQTT();
    }
  }

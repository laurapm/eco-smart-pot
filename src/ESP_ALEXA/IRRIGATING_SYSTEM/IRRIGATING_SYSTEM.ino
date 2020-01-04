/* ------------------------- */
/*     L I B R A R I E S     */
/* ------------------------- */
#include <WiFi.h>
#include <PubSubClient.h>
#include <WiFiClient.h>

/* ------------------------- */
/*     W I F I   D A T A     */
/* ------------------------- */
#define SSID_NAME "NETWORK_NAME"      	// Wifi Network name
#define SSID_PASSWORD "NETWOK_PASS"		// Wifi network password

/* ------------------------- */
/*     S M A R T N E S T
 *     M Q T T  B R O K E R 
 *     D A T A 				 */
/* ------------------------- */
#define MQTT_BROKER "smartnest.cz" //
#define MQTT_PORT 1883 //
#define MQTT_USERNAME "l.perezm" //
#define MQTT_PASSWORD "Ubicua2019" //
#define MQTT_CLIENT "5e0f5b6f4ff3c4137d21ef73" 	// Info provided by 
											    // smartnest.cz/mynestV2 


/* ------------------------- */
/*     V A R I A B L E S     */
/* ------------------------- */
WiFiClient   espClient;
PubSubClient client(espClient); 
int          waterPump = 2;


/*------------------------- */
/*   F U N C T I O N 
 *   D E C L A R A T I O N  */
/*------------------------- */
void WiFiConnection();
void initMQTT      ();
void checkMqtt     ();
int  splitTopic    (char* topic, char* tokens[] ,int tokensNumber);
void callback      (char* topic, byte* payload,  unsigned int length);



/* ------------------------ */
void setup() 
{
  // Initializes the water pump.
  pinMode(waterPump, OUTPUT);
  Serial.begin(115200);
  // Connects to the WiFi Network.
  WiFiConnection();
  // Starts the MQTT Service.
  initMQTT();
}

void loop() 
{
  // Must be kept alive to process incoming messages and maintain connection to
  // the server.
  client.loop();
  // In case MQTT is closed, it starts the service.
  checkMqtt();
}

/* ------------------------ */

/*
 * Executes when a new message is received.
 *
 * CALLBACK FUNCTION IMPORTANT INFO:
 * MQTT HAS 3 BASIC CONCEPTS:
 *    1. TOPICS: 
 *       Similar to the address of a message.
 *       In this case, it should look like Home/bedroom/waterpumpAlexa/Turn.
 *       Where the msg is ON or OFF.
 *    2. PUBLISH/SUBSCRIBE SYSTEM: 
 *       Devices can subscribe to any topic and, of course, receove the 
 *       messages from those topics they are subscribed to.
 *       In order to publish a message, a topic must be provided!
 *    3. BROKER: 
 *       Central device. It allows and restricts the connections. Its job is 
 *       also to receive, filter, redirect and publish messages to all the 
 *       devices connected. 
 *       In this case, the broker is Smartnest Server.
 */
void callback(char* topic, byte* payload, unsigned int length) 
{ 
  // Representation of the connected topic.
  Serial.print  ("Topic:");
  Serial.println(topic);
  
  // Obtains the topic and prints the message.
  int tokensNumber = 10;
  char *tokens [tokensNumber];
  char  message[length + 1];
  splitTopic(topic, tokens, tokensNumber);
  
  sprintf(message, "%c", (char) payload[0]);
  for (int i = 1; i < length; i++) 
  {
    sprintf(message, "%s%c", message, (char) payload[i]);
  }
  
  // Prints message.
  Serial.print  ("Message:");
  Serial.println(message);
    
  // Notifies the experienced state alteration due to the received message.
  char reportChange[100];
  sprintf(reportChange, "%s/report/powerState", MQTT_CLIENT);

  // Sends the new status to the Server.
  // If this were to be placed in the loop, the device would get locked. 
  if(strcmp(tokens[1], "directive")  == 0 && 
     strcmp(tokens[2], "powerState") == 0)
  {
	// Starts the water pump.
    if(strcmp(message, "ON") == 0)
	{
      digitalWrite(waterPump, LOW); 
      client.publish(reportChange, "ON") ;            
    }
	// Stops the water pump.
    else if(strcmp(message, "OFF") == 0)
	{
      digitalWrite(waterPump, HIGH);                   
      client.publish(reportChange, "OFF");           
	}
  }
}

/*
 * Connection to the WiFi Network.
 */
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

/*
 * Initialization of MQTT service.
 */
void initMQTT()
{
  client.setServer  (MQTT_BROKER, MQTT_PORT);
  client.setCallback(callback);

  while (!client.connected()) 
  {
    Serial.println("Connecting to MQTT...");
	// Conected
    if (client.connect(MQTT_CLIENT, MQTT_USERNAME, MQTT_PASSWORD )) 
	{
      Serial.println("Connected");
    } 
	// Failed Connection
	else 
	{
      Serial.print("Failed with state ");
      Serial.print(client.state());
      delay(0x7530);
    }
  }
  
  // Initialization of the Topic and Subscriber.
  // Allows to show in the Serial any request to the service.
  char reportTopic [100];
  char publishTopic[100];
  
  sprintf(reportTopic,  "%s/report/online", MQTT_CLIENT);
  sprintf(publishTopic, "%s/#",             MQTT_CLIENT);
  client.subscribe(publishTopic);
  client.publish  (reportTopic, "true");
}

/*
 * Splits a string by the character "/". 
 * Can be specifically used to split topics.
 * Eg.: 
 * input:  Home/bedroom/waterpumpAlexa/Turn
 * output: [Home, bedroom, waterpumpAlexa, Turn]
 */
int splitTopic(char* topic, char* tokens[], int tokensNumber)
{
  const char s[2] = "/";
  int pos         = 0;
  
  // Breaks the input into tokens 
  tokens[0] = strtok(topic, s);
  
  while(pos         <  tokensNumber - 1 && 
        tokens[pos] != NULL) 
  {
    pos++;
    tokens[pos] = strtok(NULL, s); 
  }
  
  return pos;
}

/* 
 * Starts the MQTT service if it has not been initialized yet.
 */
void checkMqtt ()
  {
    if(!client.connected())
	{
      initMQTT();
    }
}

# UBICUA
Proyecto de UBICUA: Sistema de control inteligente de plantas con Alexa

- ESP32 DevKit (Microcontrolador):
  *VELOCIDAD DE RELOJ: 160-240 MHz
  *RAM: 520Kb
  *BLUETOOTH
  *TARJETA WIFI
  *36 GP10 PIN
  *16 ADC de 16 bits + 2 DAC de 8 bits
  *16 canales PWM
  *Puertos serie (com3)
 
�� TENED ESTAS CARACTERÍSITICAS MUY EN CUENTA A LA HORA DE CREAR CÓDIGO QUE 
   VAYA A CORRER SOBRE EL MICROCONTROLADOR!!

src/server --> servidor lanzado con JAVA Spring para tener un servicio WEB 
               (estoy barajando el quitarlo por tiempo, y hacerlo directamente 
			   sobre HTML usando el IDE de ARDUINO, y que el servidor corra 
			   directamente sobre la IP de la microcontroladora como si fuera 
			   un LOCALHOST)

src/ESPAlexa --> Dentro está la carpeta IRRIGATION_SYSTEM con el código que 
               permite controlar el sistema de riego tanto por un comando de 
			   voz, como desde el panel de smartnest.cz. Además, con esto 
		       permitimos que el usuario pueda crear rutinas (es decir, activar 
			   el riego cada 24 horas, por ejemplo). ECHARLE UN OJO, dentro del 
			   código tenéis los datos de conexión a smartnest.cz para que 
			   veáis también cosas del proyecto.

src\dsb18b20_to_WebServer --> Lectura del sensor DSB18b20 mostrando los datos 
               en una web a la que nos conectamos usando la IP de red del ESP. 
			   Los datos en la web se muestran en Celsius y Farenhein y el 
			   diseño es muy básico, es una prueba de concepto más bien.

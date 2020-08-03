## Install mosquitto:

The tutorial followed could be found in https://dominicm.com/install-mosquitto-mqtt-broker-on-arch-linux/

As the OS used in the server is Arch, it is necessary the following steps:
	1. Fist step, download wget:  
> sudo pacman -S wget

	2. Then, download the latest archive: 
> wget http://mosquitto.org/files/source/mosquitto-1.6.10.tar.gz

	3. Unpack the archive:
>  tar xzf mosquitto-1.6.10.tar.gz

	4. Change the current directory to the newly created directory:  
> cd mosquitto-1.6.10

	5. Build the package: 
> make

	6. Install the package: 
> sudo make install

	7. Remove the leftover installation files and the package archive: 
> sudo rm -Rd mosquitto-1.6.10 
>  sudo rm mosquitto-1.6.10.tar.gz

	
## Configure mosquitto

	1. Create a system account and group: 
> sudo useradd -r -s /bin/false mosquitto

	2. Copy configuration file from the example directory: 
> sudo cp /etc/mosquitto/mosquitto.conf.example /etc/mosquitto/mosquitto

	3. Open the configuration file: 
> sudo vim /etc/mosquitto/mosquitto.conf

	4. Search the pid_file line an add 
> /var/run/mosquitto.conf

	5. Create a systemd script: 
> sudo vim /etc/systemd/system/mosquitto.service

	6. Copy the script and save:
	
> 	[Unit] 		Description=Mosquitto MQTT Broker daemon
> 		ConditionPathExists=/etc/mosquitto/mosquitto.conf
> 		Requires=network.target 		[Service] 		User=mosquitto
> 		Group=mosquitto 		Type=simple 		ExecStartPre=/usr/bin/rm -f
> /var/run/mosquitto.pid 		ExecStart=/usr/local/sbin/mosquitto -c
> /etc/mosquitto/mosquitto.conf -d 		ExecReload=/bin/kill -HUP $MAINPID
> 		PIDFile=/run/mosquitto.pid 		Restart=on-failure 		[Install]
> 		WantedBy=multi-user.target
	
The following steps has been done in order to subsane the error with the mosquitto_sub command, following the information obtain
in https://stackoverflow.com/questions/30861974/mosquitto-pub-error-while-loading-shared-libraries-libmosquitto-so-1-cannot-o

	7. Open the dynamic linker configuration file: 
> sudo vim /etc/ld.so.conf

	8. Add the following lines:
> 	include ld.so.conf.d/*.conf 		
> include /usr/local/lib 	
> 	/usr/lib
> 	/usr/local/lib

	9. Execute 
> /sbin/ldconfig

	10. Create the following soft link: 
> ln -s /usr/local/lib/libmosquitto.so.1 /usr/lib/libmosquitto.so.1

	
## How to run mosquitto

In order to start it manually use the command mosquitto or if you want to start the systemd service use

>  sudo systemctl start mosquitto

and then enable the systemd service to run on boot with 

> sudo systemctl enable mosquitto.

		
 

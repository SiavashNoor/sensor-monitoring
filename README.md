# sensor-monitoring
We have really expensive sattelite communication devices at our office which are in different places ,outdoor and indoor ,
we had to track the teperature and humidity for each device keep them in the recommeded ranges . so I started to develope a software
for that to meet our demands  : Monitoring Temperature and Humidity of crucial ones.
we have a  Http server developed using Arduino to gather data of each sensor (DHT22 sensor )and send  data as a table  to the client for each GET request 
the  application is packaged using jpackage and could be installed as a standalone application .You need to install Wix toolset for packaging .

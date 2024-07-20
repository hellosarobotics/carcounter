java -jar carcounter-0.3.jar NOISE_THRESHOLD=0.005 DETECTION_PAUSE=2000 COUNTER_ID=65 > log.txt 2>&1 &


NOISE_THRESHOLD è la sensibilità del microfono da tenere in considerazione. Più tende a zero 0, più è sensibile.

DETECTION_PAUSE sono i millisecondi di pausa del campionamento tra una rilevazione e l'altra. Per esempio se abbiamo un suono continuo che supera la soglia di noise_threshold, verrà campionato 1 record ogni 2 secondi

COUNTER_ID, è l'id del contatore del Database da aggiornare.


## Di seguito le curl per usare il sistema

curl http://localhost:8080/counter

curl -X POST -H "Content-Type: application/json" -d '{"contatore":1}' http://localhost:8080/counter

curl -X PUT http://localhost:8080/counter/1/increment

curl -X PUT http://localhost:8080/counter/1/reset/0

curl http://localhost:8080/counter/eventiPerOra?data=2024-07-06

## Link utili

http://localhost:8080/h2-console

jdbc:h2:~/data/testdb

sa

password

http://localhost:8080/swagger-ui/

https://queenliest-panther-3570.dataplicity.io/

https://queenliest-panther-3570.dataplicity.io/swagger-ui/

https://queenliest-panther-3570.dataplicity.io/h2-console/

## Per il deploy

1) Buildare il pacchetto con maven: goal clean package

2) cd "C:\Users\Andrea Santopietro\Downloads\carcounter\carcounter\target"

3) scp carcounter-0.7.jar alessia@192.168.1.104:/home/alessia

4) ssh alessia@192.168.1.104

5) crontab -e 
	
	@reboot java -jar carcounter-[version].jar NOISE_THRESHOLD=0.05 DETECTION_PAUSE=2500 COUNTER_ID=1 > log.txt 2>&1 &

5) pkill -f java

6) java -jar carcounter-0.7.jar NOISE_THRESHOLD=0.05 DETECTION_PAUSE=2500 COUNTER_ID=1 > log.txt 2>&1 &

7) tail -f log.txt



## Release 0.10 (2024-07-15)

- Rimosso il viewport per i device

- Tolto il crontab e messo come service

	sudo nano /etc/systemd/system/carcounter.service
	[Unit]
	Description=Car Counter Service
	After=network.target
	
	[Service]
	ExecStart=/usr/bin/java -jar /home/alessia/carcounter.jar NOISE_THRESHOLD=0.05 DETECTION_PAUSE=2500 COUNTER_ID=1
	Restart=always
	User=alessia
	WorkingDirectory=/home/alessia
	StandardOutput=append:/var/log/carcounter.log
	StandardError=append:/var/log/carcounter.err
	
	[Install]
	WantedBy=multi-user.target
	
	-------------
	
	sudo systemctl daemon-reload
	sudo systemctl restart carcounter.service
	
	



## Release 0.8 (2024-07-08)

- Rilasciata interfaccia grafica

- Tolti alcuni endpoint non necessari

- Rimosso accesso ad H2 DB da remoto

## Release 0.7 (2024-07-08)
  
- Eventi per giorno
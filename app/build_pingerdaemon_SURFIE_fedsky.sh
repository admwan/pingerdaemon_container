cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-client -f ./dockerfile_pingerdaemon_SURFIE .
docker run -d 	--name pingerdaemon-SURFIE  \
		--network=fedsky \
		--ip=192.168.50.233 \
		pingerdaemon-rabbitmq-client 


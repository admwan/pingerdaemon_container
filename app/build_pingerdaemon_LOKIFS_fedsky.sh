cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-client -f ./dockerfile_pingerdaemon_LOKIFS .
docker run -d 	--name pingerdaemon-LOKIFS  \
		--network=fedsky \
		--ip=192.168.50.229 \
		pingerdaemon-rabbitmq-client 


cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-client -f ./dockerfile_pingerdaemon_CAPTUW .
docker run -d 	--name pingerdaemon-CAPTUW  \
		--network=skylake \
		--ip=192.168.50.227 \
		pingerdaemon-rabbitmq-client 


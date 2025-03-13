cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-client -f ./dockerfile_pingerdaemon_THORFW .
docker run -d 	--name pingerdaemon-THORFW  \
		--network=skylake \
		--ip=192.168.50.228 \
		pingerdaemon-rabbitmq-client 


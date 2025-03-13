cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-client -f ./dockerfile_pingerdaemon_HYDRFS .
docker run -d 	--name pingerdaemon-HYDRFS  \
		--network=fedsky \
		--ip=192.168.50.231 \
		pingerdaemon-rabbitmq-client 


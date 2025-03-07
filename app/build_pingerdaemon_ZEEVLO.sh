cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-zeevlo -f ./dockerfile_pingerdaemon_ZEEVLO .
docker run -d 	--name "WITOOG"  \
		--network=fedsky \
		--ip=192.168.50.233 \
		pingerdaemon-rabbitmq-zeevlo


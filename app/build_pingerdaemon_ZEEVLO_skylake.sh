cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-zeevlo -f ./dockerfile_pingerdaemon_ZEEVLO .
docker run -d 	--name "ZEEVLO"  \
		--network=skylake \
		--ip=192.168.50.234 \
		pingerdaemon-rabbitmq-zeevlo


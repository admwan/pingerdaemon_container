cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-witoog -f ./dockerfile_pingerdaemon_WITOOG .
docker run -d 	--name "WITOOG"  \
		--network=skylake \
		--ip=192.168.50.233 \
		pingerdaemon-rabbitmq-witoog


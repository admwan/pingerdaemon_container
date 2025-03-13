cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-belyta -f ./dockerfile_pingerdaemon_BELYTA .
docker run -d 	--name BELYTA  \
		--network=fedsky \
		--ip=192.168.50.228 \
		pingerdaemon-rabbitmq-belyta


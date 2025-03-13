cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-cicade -f ./dockerfile_pingerdaemon_CICADE .
docker run -d 	--name="CICADE"  \
		--network=fedsky \
		--ip=192.168.50.229 \
		pingerdaemon-rabbitmq-cicade


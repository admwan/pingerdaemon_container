cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-krekel -f ./dockerfile_pingerdaemon_KREKEL .
docker run -d 	--name "KREKEL"  \
		--network=fedsky \
		--ip=192.168.50.231 \
		pingerdaemon-rabbitmq-krekel


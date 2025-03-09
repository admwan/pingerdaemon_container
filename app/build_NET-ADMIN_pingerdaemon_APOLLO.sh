cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-apollo -f ./dockerfile_pingerdaemon_APOLLO .
docker run -d 	--name="APOLLO"  \
		--cap-add=NET_RAW \
		--cap-add=NET_ADMIN  \
		--network=fedsky \
		--ip=192.168.50.227 \
		pingerdaemon-rabbitmq-apollo


cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-apollo -f ./dockerfile_pingerdaemon_APOLLO .
docker run -d 	--name="APOLLO"  \
		--network=skylake \
		--ip=192.168.50.227 \
		pingerdaemon-rabbitmq-apollo


cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-hommel -f ./dockerfile_pingerdaemon_HOMMEL .
docker run -d 	--name="HOMMEL"  \
		--network=fedsky \
		--ip=192.168.50.230 \
		pingerdaemon-rabbitmq-hommel


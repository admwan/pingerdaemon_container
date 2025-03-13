cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-client -f ./dockerfile_pingerdaemon_ASGAFS .
docker run -d 	--name pingerdaemon-ASGAFS  \
		--network=fedsky \
		--ip=192.168.50.232 \
		pingerdaemon-rabbitmq-client 


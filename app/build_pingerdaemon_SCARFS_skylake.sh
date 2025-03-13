cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-client -f ./dockerfile_pingerdaemon_SCARFS .
docker run -d 	--name pingerdaemon-SCARFS  \
		--network=skylake \
		--ip=192.168.50.230 \
		pingerdaemon-rabbitmq-client 


cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t pingerdaemon-rabbitmq-oermot -f ./dockerfile_pingerdaemon_OERMOT .
docker run -d 	--name "OERMOT"  \
		--network=skylake \
		--ip=192.168.50.232 \
		pingerdaemon-rabbitmq-oermot


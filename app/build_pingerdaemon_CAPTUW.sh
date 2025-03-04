cp ../target/pingerdaemon-rabbitmq-client-0.0.1-SNAPSHOT.jar ./
docker build -t my-pingerdaemon-rabbitmq-client .
docker run -d 	--name pingerdaemon-CAPTUW  \
				--network=fedsky \
				--ip=192.168.50.227 \
				pingerdaemon-rabbitmq-client 


./mvnw clean package -DskipTest=true
docker build -t training/configuration-server configuration-server
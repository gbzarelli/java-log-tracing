build:
	mvn clean install -f quarkus-app/pom.xml
	mvn clean install -f spring-app/pom.xml
	mvn clean install -f spring-app-jersey/pom.xml

install:
	docker build -f quarkus-app/src/main/docker/Dockerfile.jvm -t helpdev/quarkus-log-tracing .
	docker build -f spring-app/src/main/docker/Dockerfile.jvm -t helpdev/spring-log-tracing .
	docker build -f spring-app-jersey/src/main/docker/Dockerfile.jvm -t helpdev/spring-jersey-log-tracing .
	
run:
	docker-compose up
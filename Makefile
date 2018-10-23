.DEFAULT_GOAL := build-run

run:
	java -jar ./target/project-lvl1-s432-1.0-SNAPSHOT-jar-with-dependencies.jar

clean:
	rm -rf ./target

build-run: build run

build:
	./mvnw clean package

update:
	./mvnw versions:update-properties versions:display-plugin-updates


# Ristorante Menu Reservation
Easy to use Menu Reservation with Fulltext-Search.

![Alt text](https://github.com/mbuchser/ristores/blob/master/src/main/resources/screenshot.jpg?raw=true "Overview")


Requirements for Development:
- JDK 8+
- Maven 3.6.x
- Docker

### Start PostgresSQL and ElasticSerach with Docker-compose

Change into the subdirectory /src/main/docker and enter:

> docker-compose up -d

This will start a Postgres Db and a Elasticsearch single node instance


Connection properties for the Agroal datasource are defined in the standard Quarkus configuration file,
`src/main/resources/application.properties`.


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvn  quarkus:dev
```


# Getting Started

## How to Deploy

Create a docker network for the containers:

```bash
docker network create crud-app
```

## Database

You can launch the database with the following docker command:

```bash
docker run -d --name crud-database \
 --network crud-app --network-alias db \
 -e POSTGRES_USER=crud -e POSTGRES_PASSWORD=crud \
 -e POSTGRES_DB=crud -p 5432:5432 postgres:latest

```

## Backend
> Run all commands from <root>/back

Create and image form the source code (2-3min):

```bash
mvn spring-boot:build-image
```

Execute it in a docker container:

```bash
docker run -d --name crud-back -p 8080:8080 \
 --network crud-app --network-alias back \
 -e POSTGRESQL_HOST=crud-database crud-back-app:0.0.1-SNAPSHOT
```

Alternatively, you can always run this application locally:

```bash
./mvnw spring-boot:run
```

## Frontend
> Run all commands from <root>/front

You can generate the image of the frontend with the following commands.

1. Compile the angular app:

```bash
ng build
```

2. Use the Dockerfile to build the image itself

```bash
docker build --pull --rm -f "Dockerfile" -t front:latest "."
```

3. Run the image

```bash
docker run -d --name crud-front \
 --network crud-app --network-alias front \
 -p 4200:4200 front:latest

```

### Interact with the backend

In this section you can find the CRUD operations to interact with the provided backend:

#### Create

```bash
curl -X POST http://localhost:8080/person \
  -H "Content-Type: application/json" \
  -d '{
      "name":"John",
      "surname":"Doe",
      "email":"jdoe@mail.com",
      "password":"Sneaky123?",
      "birthday":"2003-12-03"
  }'
```

#### Get by Id

```bash
curl -X GET http://localhost:8080/person/<id> \
  -H "Content-Type: application/json"
```

#### Get By Email

```bash
curl -X GET http://localhost:8080/person/byemail/<email> \
  -H "Content-Type: application/json"
```

#### Get All

```bash
curl -X GET http://localhost:8080/person/all \
  -H "Content-Type: application/json"
```

#### Delete

```bash
curl -X DELETE http://localhost:8080/person/<id> \
  -H "Content-Type: application/json"
```

#### Delete By Email

```bash
curl -X DELETE http://localhost:8080/person/byemail/<email> \
  -H "Content-Type: application/json"
```

#### Update

```bash
curl -X PUT http://localhost:8080/person/email/<email> \
    -H "Content-Type: application/json" \
    -d '{
        "name":"Johnny",
        "surname":"Doe Updated",
        "email":"jdoe@mail2.com"
    }'
```





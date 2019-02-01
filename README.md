 
#### Introduction
A demo of Spring WebFlux Rest API using kotlin and reactive technology stacks, including Spring WebFlux, spring-boot-starter-webflux, spring-boot-starter-data-mongodb-reactive, and MongoDB reactive streams.
This branch migrates to support Java 11.
 
#### Technology Stacks
* Java 11
* Kotlin
* Spring framework & Spring Boot
  * Spring WebFlux
  * Spring-boot 2.1.*
  * spring-boot-starter-data-mongodb-reactive
* MongoDB, MongoDB Reactive Stream
* Embedded Mongo
* Maven, Gradle 5.X
* Junit 5, Mokito
#### Instruction
* To run the project, make sure the MongoDB Community Server 4.0+ installed and running locally.
```
maven spring-boot:run
or
./gradlew bootRun
```
* curl command for quick testing
```
curl -i 'http://localhost:9080/products' -H 'Content-Type:application/json' -d '
{
  "sku": "sku8585",
  "title": "Galaxy 32S32 Smart LED TV",
  "description": "1080 Full HD TV with streaming built in",
  "type": "TV",
  "shipping": [{
    "weight": "28",
    "dimensions": {
      "width": 100,
      "height": 60,
      "depth": 50
    }
  }],
  "details": {
    "title": "HD TV with streaming built in",
    "type": "HDTV",
    "tracks": ["Smart TV", "HDTV", "Streaming"]
  }
}
'

curl -i 'http://localhost:9080/products?type=TV&details.type=HDTV'

curl -i 'http://localhost:9080/products/sku1'
```

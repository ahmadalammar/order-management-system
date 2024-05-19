# Orders Managment Microservices

## Intro
In this example we will design a simple event-driven distrbuted system that simulate order managment system using spring-cloud, spring-native, apache-camel and saga design pattern.
This is just a show case on how to use apache camel with saga in microservices.

## System design
![Screenshot 2024-01-20 at 6 02 54 PM](https://github.com/ahmadalammar/orders-managment/assets/17546520/12b88c1f-bc0f-47a4-ad56-0f10edf9a6b3)


## System Install
* Start management servers
  `cd management`
  `docker-compose up -d`

* Run `discovery-server`, `customer-service`, `gateway-service`, `order-service`

* Run simple API test by calling `http://localhost:9902/api/order`
```
curl --location 'http://localhost:9901/api/v1/order' \
--header 'Content-Type: application/json' \
--data '{
    "id":"123",
    "itemName": "new item"
}'
```

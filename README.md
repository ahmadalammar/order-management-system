# Orders Managment Microservices

## Intro
In this example we will design a simple event-driven distrbuted system that simulate order managment system using springboot, apache-camel and saga design pattern.
This is just a show case on how to use apache camel with saga in microservices.

## System design
![Screenshot 2024-01-20 at 6 02 54 PM](https://github.com/ahmadalammar/orders-managment/assets/17546520/12b88c1f-bc0f-47a4-ad56-0f10edf9a6b3)


## System Install
* Start management servers (kafka-server and LRA coordinator)
  `cd management`
  `docker-compose up -d`

* Run `order-service`, `customer-service`, `payment-service`.

* Create a new order :
```
curl --location 'http://localhost:9901/api/v1/order' \
--header 'Content-Type: application/json' \
--data '{
    "id":"123",
    "itemName": "new item"
}'
```
* Get Order details :
```
curl --location 'http://localhost:9901/api/v1/order/123'
```
## Step-by-step implementation can be found here:
https://medium.com/@ahmadalammar/developing-order-management-system-oms-using-spring-apache-camel-and-saga-pattern-part-2-aba83bc12a65

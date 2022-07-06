## Simple Currency Account App

Application allows to:
- create bank accounts with sub-accounts in PLN and USD;
- enables the exchange in pair: PLN <-> USD at the current exchange rate taken from public NBP API;
- change in the balances on sub-accounts after the currency exchange.

Validated data: user data and his personal ID (PESEL), user age, initial amount, exchange currencies codes, exchange amounts, available funds.

The application downloads the data of current exchange rates from the public NBP API (https://api.nbp.pl/)

### Tech/framework used

Application code: <b>JAVA 17</b>

##### Built with:
- [IntelliJ IDEA 2022.1.3 (Community Edition)](https://www.jetbrains.com/idea/)
- [Spring Boot v2.7.1](https://spring.io/)
- [Hibernate v6.2.3](http://hibernate.org/)
- [Maven v3.8.6](https://maven.apache.org)
- [Lombok v1.18.24](https://projectlombok.org/)
- [H2 Database](https://http://www.h2database.com/)

#### Application uses:
- [NBP API](https://api.nbp.pl/)


## Installation instructions
Import the project as a maven application to your favorite IDE.

## To run the application
Run the app in IDE as 'Java application'

## To test the application
The REST API to the example app is described below:

### 1. Create a new account

#### Request:

POST /api/accounts/

    curl --location --request POST 'localhost:8080/api/accounts' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name": "Adam",
    "surname": "Test",
    "pesel": "02070803628",
    "amountPln": 1000
    }'

#### Response:

    HTTP/1.1 201 Created
    Status: 201 Created
    Content-Type: application/json
    Location: /api/accounts/02070803628

    {
        "id": 1,
        "name": "Adam",
        "surname": "Test",
        "pesel": "02070803628",
        "subaccountList": [
            {
                "id": 2,
                "currencyCode": "PLN",
                "balance": 1000
            },
            {
                "id": 3,
                "currencyCode": "USD",
                "balance": 0
            }
        ]
    }


### 2. Exchange PLN to USD

#### Request:

POST /api/exchange/

    curl --location --request POST 'localhost:8080/api/exchange/' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "pesel": "02070803628", 
    "amountToExchange": 600, 
    "fromCurrency": "PLN", 
    "toCurrency": "USD"
    }'

#### Response:

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
        "success": true,
        "amountToExchange": 600,
        "fromCurrency": "PLN",
        "toCurrency": "USD",
        "rate": 0.2197,
        "result": 131.82
    }

### 3. Exchange USD to PLN

#### Request:

POST /api/exchange/

    curl --location --request POST 'localhost:8080/api/exchange/' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "pesel": "02070803628", 
    "amountToExchange": 100.00, 
    "fromCurrency": "PLN", 
    "toCurrency": "USD"
    }'

#### Response:

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
        "success": true,
        "amountToExchange": 100.00,
        "fromCurrency": "USD",
        "toCurrency": "PLN",
        "rate": 4.4622,
        "result": 446.22
    }

### 4. Get account data

#### Request:

GET /api/accounts/{pesel}

    curl --location --request GET 'localhost:8080/api/accounts/02070803628'

#### Response:

    HTTP/1.1 200 OK
    Status: 200 OK
    Content-Type: application/json

    {
        "id": 1,
        "name": "Adam",
        "surname": "Test",
        "pesel": "02070803628",
        "subaccountList": [
            {
                "id": 2,
                "currencyCode": "PLN",
                "balance": 846.22
            },
            {
                "id": 3,
                "currencyCode": "USD",
                "balance": 31.82
            }
        ]
    }
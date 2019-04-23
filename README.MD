# Exchange Service
---
## Run service
    java -jar exchangeservice.jar
---
## API
    1. Create User:
      curl -d "{\"id\":\"1\", \"name\": \"User 1\"}" -H "Content-Type: application/json" -X POST localhost:80/createuser
    2. Update balance for user:
      curl -d "{\"id\":\"1\", \"amount\": \"10\"}" -H "Content-Type: application/json" -X PUT localhost:80/updatebalance
    3. Make exchange between two users:
      curl -d "{\"fromId\":\"1\", \"toId\":\"2\", \"amount\": \"1\"}" -H "Content-Type: application/json" -X PUT localhost:80/exchange
    4. Get balance by user id:
      curl -d "{\"id\":\"1\"}" -H "Content-Type: application/json" -X GET localhost:80/getbalance
    5. Get name by user id:
      curl -d "{\"id\":\"2\"}" -H "Content-Type: application/json" -X GET localhost:80/getname
    6. Stop service:
      curl -X POST localhost:80/stop
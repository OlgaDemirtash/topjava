# MealRestController cURL examples

## Get by id

curl -L 'http://localhost:8080/topjava/rest/meals/100003'

## Get all

curl -L 'http://localhost:8080/topjava/rest/meals/'

## Get all with filter

curl -L 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30'

## Delete by id

curl -X DELETE 'http://localhost:8080/topjava/rest/meals/100003'

## Update by id

curl -L -X PUT 'http://localhost:8080/topjava/rest/meals/100003' -H 'Content-Type: application/json' --data-raw '{"id":
100003,"dateTime":"2020-01-30T10:02:00","description":"Обновленный завтрак","calories":200}'

## Create

curl -L -X POST 'http://localhost:8080/topjava/rest/meals/' -H  'Content-Type: application/json' --data-raw '{"
dateTime":"2020-02-01T18:00:00","description":"Созданный ужин","calories":300}'
while true; do sleep 1; \
  curl --header "Content-Type: application/json" \
       --header "Accept: application/json" \
       --request POST \
       --data '{  "clientId": 1, "products": [ { "id": 1 } ] }' \
        http://localhost:8080/api/shop/orders; \
  echo "Sending request"; \
done
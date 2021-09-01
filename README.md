# Acmebank account manager
### H2 Console URL
http://localhost:8080/h2-console

### APIs
GET http://localhost:8080/account/balance/12345678

POST http://localhost:8080/account/transfer-amount

{
"fromAccountNumber": "88888888",
"toAccountNumber": "12345678",
"amount": 999830.00,
"currency": "HKD"
}
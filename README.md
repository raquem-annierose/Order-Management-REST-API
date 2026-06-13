# Order Management REST API

**Submitted by:**
Capilitan, Mikee C. | Niones, Zyra Joy O. | Raquem, Annie Rose S. | Villarta, Keith B.

BSIT 3-1 — Submitted to: Prof. Angelo Joshua San Luis

---

## About

A REST API built with Spring Boot and MySQL for managing customers, products, and orders.

**Tech used:** Java 25, Spring Boot 3.4.13, Spring Data JPA, MyBatis, MySQL, Maven, Lombok, Log4j2

---

## How to Run

**Prerequisites:** Java 25, Maven 3.6+

```bash
# Build
mvn clean package

# Run
mvn spring-boot:run
```

Server runs at `http://localhost:8080`

---

## API Endpoints

### Epic 1: Customer API

**User Story 1 — Register Customer**
```
POST /api/customers
```
```json
{
  "name": "string",
  "email": "string",
  "phone": "string"
}
```
- Email must be unique
- Returns the created customer

**User Story 2 — Get Customer Details**
```
GET /api/customers/{id}
```
- Returns 404 if customer not found

---

### Epic 2: Product API

**User Story 3 — Create Product**
```
POST /api/products
```
```json
{
  "name": "string",
  "description": "string",
  "price": 99.99,
  "stock": 10
}
```
- Price must be > 0
- Stock must be >= 0

**User Story 4 — List All Products**
```
GET /api/products
```

**User Story 5 — Get Product by ID**
```
GET /api/products/{id}
```

---

### Epic 3: Order API

**User Story 6 — Create Order**
```
POST /api/orders
```
```json
{
  "customerId": 1,
  "items": [
    { "productId": 10, "quantity": 2 },
    { "productId": 15, "quantity": 1 }
  ]
}
```
- Product must exist
- Total price is calculated automatically
- Stock is reduced after order is placed

**User Story 7 — Get Order by ID**
```
GET /api/orders/{id}
```
- Returns order details with items and total price

**User Story 8 — Get Customer Orders**
```
GET /api/customers/{id}/orders
```

**User Story 9 — Cancel Order**
```
DELETE /api/orders/{id}
```
- Only allowed if order status is `PENDING`
- Restores product stock on cancellation

---

### Epic 4: Reporting API

**User Story 10 — Total Sales Report**
```
GET /api/reports/sales
```
```json
{
  "totalOrders": 120,
  "totalRevenue": 45000
}
```

**User Story 11 — Top Selling Products**
```
GET /api/reports/top-products
```
- Returns products sorted by total quantity sold (descending)

---

## Data Models

**Customer** — id, name, email (unique), phone, createdAt

**Product** — id, name, description, price, stock

**Order** — id, customerId, status (`PENDING` / `COMPLETED` / `CANCELLED`), totalPrice, createdAt

**OrderItem** — id, orderId, productId, quantity, unitPrice

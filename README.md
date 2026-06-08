# Order-Management-REST-API

A minimal in-memory Order Management REST API implemented with Python's standard library.

## Run the API

```bash
python app.py
```

The API listens on `http://localhost:8000`.

## Endpoints

- `GET /orders` - list all orders
- `POST /orders` - create an order
- `GET /orders/{id}` - retrieve an order
- `PUT /orders/{id}` - update an order
- `DELETE /orders/{id}` - delete an order

Order payload format:

```json
{
  "customer_name": "Alice",
  "item": "Keyboard",
  "quantity": 2,
  "price": 49.99
}
```

## Run tests

```bash
python -m unittest discover -s tests -v
```

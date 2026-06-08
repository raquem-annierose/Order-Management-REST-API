import json
from wsgiref.simple_server import make_server


class OrderAPI:
    def __init__(self):
        self._orders = {}
        self._next_id = 1

    def __call__(self, environ, start_response):
        method = environ.get("REQUEST_METHOD", "GET")
        path = environ.get("PATH_INFO", "")

        if path == "/orders":
            if method == "GET":
                return self._respond(start_response, "200 OK", list(self._orders.values()))
            if method == "POST":
                payload, error = self._read_payload(environ)
                if error:
                    return self._respond(start_response, "400 Bad Request", {"error": error})

                validation_error = self._validate_order_payload(payload)
                if validation_error:
                    return self._respond(start_response, "400 Bad Request", {"error": validation_error})

                order = {
                    "id": self._next_id,
                    "customer_name": payload["customer_name"],
                    "item": payload["item"],
                    "quantity": payload["quantity"],
                    "price": float(payload["price"]),
                }
                self._orders[self._next_id] = order
                self._next_id += 1
                return self._respond(start_response, "201 Created", order)
            return self._respond(start_response, "405 Method Not Allowed", {"error": "Method not allowed"})

        if path.startswith("/orders/"):
            order_id_str = path.rsplit("/", 1)[-1]
            if not order_id_str.isdigit():
                return self._respond(start_response, "404 Not Found", {"error": "Order not found"})

            order_id = int(order_id_str)
            order = self._orders.get(order_id)
            if not order:
                return self._respond(start_response, "404 Not Found", {"error": "Order not found"})

            if method == "GET":
                return self._respond(start_response, "200 OK", order)
            if method == "PUT":
                payload, error = self._read_payload(environ)
                if error:
                    return self._respond(start_response, "400 Bad Request", {"error": error})

                validation_error = self._validate_order_payload(payload)
                if validation_error:
                    return self._respond(start_response, "400 Bad Request", {"error": validation_error})

                updated_order = {
                    "id": order_id,
                    "customer_name": payload["customer_name"],
                    "item": payload["item"],
                    "quantity": payload["quantity"],
                    "price": float(payload["price"]),
                }
                self._orders[order_id] = updated_order
                return self._respond(start_response, "200 OK", updated_order)
            if method == "DELETE":
                del self._orders[order_id]
                start_response("204 No Content", [("Content-Length", "0")])
                return [b""]

            return self._respond(start_response, "405 Method Not Allowed", {"error": "Method not allowed"})

        return self._respond(start_response, "404 Not Found", {"error": "Not found"})

    @staticmethod
    def _read_payload(environ):
        content_length = environ.get("CONTENT_LENGTH")
        if not content_length:
            return None, "Request body is required"

        try:
            body = environ["wsgi.input"].read(int(content_length)).decode("utf-8")
            payload = json.loads(body)
            if not isinstance(payload, dict):
                return None, "JSON body must be an object"
            return payload, None
        except (ValueError, json.JSONDecodeError):
            return None, "Invalid JSON payload"

    @staticmethod
    def _validate_order_payload(payload):
        required_fields = ["customer_name", "item", "quantity", "price"]
        for field in required_fields:
            if field not in payload:
                return f"Missing required field: {field}"

        if not isinstance(payload["customer_name"], str) or not payload["customer_name"].strip():
            return "customer_name must be a non-empty string"
        if not isinstance(payload["item"], str) or not payload["item"].strip():
            return "item must be a non-empty string"
        if isinstance(payload["quantity"], bool) or not isinstance(payload["quantity"], int) or payload["quantity"] <= 0:
            return "quantity must be a positive integer"
        if isinstance(payload["price"], bool) or not isinstance(payload["price"], (int, float)) or payload["price"] < 0:
            return "price must be a non-negative number"

        return None

    @staticmethod
    def _respond(start_response, status, payload):
        body = json.dumps(payload).encode("utf-8")
        headers = [
            ("Content-Type", "application/json"),
            ("Content-Length", str(len(body))),
        ]
        start_response(status, headers)
        return [body]


app = OrderAPI()


if __name__ == "__main__":
    with make_server("0.0.0.0", 8000, app) as server:
        print("Serving Order Management REST API on http://0.0.0.0:8000")
        server.serve_forever()

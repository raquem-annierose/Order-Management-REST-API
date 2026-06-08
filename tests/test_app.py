import io
import json
import unittest

from app import OrderAPI


class OrderAPITestCase(unittest.TestCase):
    def setUp(self):
        self.app = OrderAPI()

    def _request(self, method, path, payload=None):
        body = b""
        if payload is not None:
            body = json.dumps(payload).encode("utf-8")

        environ = {
            "REQUEST_METHOD": method,
            "PATH_INFO": path,
            "CONTENT_LENGTH": str(len(body)),
            "wsgi.input": io.BytesIO(body),
        }

        response = {}

        def start_response(status, headers):
            response["status"] = status
            response["headers"] = dict(headers)

        chunks = self.app(environ, start_response)
        raw_body = b"".join(chunks)
        parsed = None
        if raw_body:
            parsed = json.loads(raw_body.decode("utf-8"))

        return response["status"], response["headers"], parsed

    def test_create_and_get_order(self):
        status, _, body = self._request(
            "POST",
            "/orders",
            {
                "customer_name": "Alice",
                "item": "Keyboard",
                "quantity": 2,
                "price": 49.99,
            },
        )
        self.assertEqual(status, "201 Created")
        self.assertEqual(body["id"], 1)

        status, _, body = self._request("GET", "/orders/1")
        self.assertEqual(status, "200 OK")
        self.assertEqual(body["customer_name"], "Alice")

    def test_list_orders(self):
        self._request(
            "POST",
            "/orders",
            {
                "customer_name": "Bob",
                "item": "Mouse",
                "quantity": 1,
                "price": 19.99,
            },
        )

        status, _, body = self._request("GET", "/orders")
        self.assertEqual(status, "200 OK")
        self.assertEqual(len(body), 1)

    def test_update_order(self):
        self._request(
            "POST",
            "/orders",
            {
                "customer_name": "Cathy",
                "item": "Monitor",
                "quantity": 1,
                "price": 189.50,
            },
        )

        status, _, body = self._request(
            "PUT",
            "/orders/1",
            {
                "customer_name": "Cathy",
                "item": "Monitor",
                "quantity": 2,
                "price": 189.50,
            },
        )
        self.assertEqual(status, "200 OK")
        self.assertEqual(body["quantity"], 2)

    def test_delete_order(self):
        self._request(
            "POST",
            "/orders",
            {
                "customer_name": "Dan",
                "item": "Laptop Stand",
                "quantity": 1,
                "price": 29.99,
            },
        )

        status, _, body = self._request("DELETE", "/orders/1")
        self.assertEqual(status, "204 No Content")
        self.assertIsNone(body)

        status, _, body = self._request("GET", "/orders/1")
        self.assertEqual(status, "404 Not Found")
        self.assertEqual(body["error"], "Order not found")

    def test_reject_invalid_payload(self):
        status, _, body = self._request(
            "POST",
            "/orders",
            {
                "customer_name": "",
                "item": "Phone",
                "quantity": 1,
                "price": 199.00,
            },
        )
        self.assertEqual(status, "400 Bad Request")
        self.assertIn("customer_name", body["error"])

        status, _, body = self._request(
            "POST",
            "/orders",
            {
                "customer_name": "Ed",
                "item": "Chair",
                "quantity": True,
                "price": 49.00,
            },
        )
        self.assertEqual(status, "400 Bad Request")
        self.assertIn("quantity", body["error"])


if __name__ == "__main__":
    unittest.main()

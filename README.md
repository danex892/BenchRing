# BenchRing

This project is created to compare the performance of microservices implemented in different programming languages with the same API and PostgreSQL interaction.

---

## Task Description

Each service implements two endpoints:
- `POST /contacts` — contact creation
- `GET /contacts` — get a list of contacts with filters and pagination

Benchmark client:
- performs **1,000,000 POST requests** to generate data
- performs **1,000,000 GET requests** according to a specific filtering scheme
- measures total and average execution time for each type of request

---

## API Specification

### `POST /contacts`

**Request:**
```json
{
  "external_id": 12345,
  "phone_number": "+1234567890"
}
```

**Response:**
```json
{
  "id": "uuid",
  "external_id": 12345,
  "phone_number": "+1234567890",
  "date_created": "ISO datetime",
  "date_updated": "ISO datetime"
}
```

### `GET /contacts`

Query parameters:

- external_id — filter by external_id (optional)
- phone_number — filter by phone number (optional)
- limit — number of records (default 10000, maximum 10000)
- offset — selection offset (default 0)

**Response:**
```json
[
  {
    "id": "uuid",
    "external_id": 12345,
    "phone_number": "+1234567890",
    "date_created": "ISO datetime",
    "date_updated": "ISO datetime"
  }
]
```

## Benchmark Client

### Steps:

1. Sends 1,000,000 POST requests:

   - `external_id` — random integer between 0 and 1,000,000
   - `phone_number` — random valid number in the format +79999999999
   - all created `external_id` and `phone_number` are saved for subsequent GET requests.

2. Executes 1,000,000 GET requests:

   - 300,000 requests with `phone_number` filter (existing in the database)
   - 300,000 requests with `external_id` filter (existing in the database)
   - 400,000 requests with `external_id` filter in the range `0 <= external_id <= 100000` (may or may not exist)

### Response Limit:

- `limit` = 10000
- `offset` = 0

### We Measure:

- Total time for all POST requests
- Total time for all GET requests
- Average time for one POST request
- Average time for one GET request

## Project Structure

```
/
├── docker-compose.yml
├── python_aiohttp/
│   ├── Dockerfile
│   ├── requirements.txt
│   └── app/
│       ├── main.py
│       ├── models.py
│       ├── db.py
│       └── routes.py
├── benchmark_client/
│   └── benchmark.py (coming later)
└── README.md
```

## How to Run

Run the Makefile, which will do the following:

- docker-compose down -v
- build containers
- spin up database and web services
- wait 5 seconds (just in case, for the database to start up)
- run benchmark

Command:

```bash
make benchmark-python
make benchmark-php
make benchmark-java
make benchmark-rust
make benchmark-go
make benchmark-node
```

### Other Useful Targets:

- make run-python — just spin up services without benchmarking
- make logs-python — view Python service logs
- make stop — stop all containers and remove volumes

## Planned Implementations:

- [x] Python (aiohttp)
- [x] Java (Spring Boot)
- [x] Golang
- [x] Rust
- [x] PHP
- [x] Node.js

## License

See `LICENSE`.

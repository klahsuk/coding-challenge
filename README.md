# Transaction CSV Batch Processor

This project is a small Spring Boot batch application that reads payment transactions, validates them, classifies their risk, persists valid transactions to H2, and writes the outcome of every processed transaction to a CSV file.

## Requirements

- Java 25
- Maven, or Docker

## How it works

At startup, the application runs once as a `CommandLineRunner`:

1. Reads `payments-to-process.csv` from `src/main/resources/input`.
2. Skips the CSV header and blank rows.
3. Parses each transaction into its six input fields.
4. Validates the transaction:
   - The payment ID must not be negative.
   - The amount must be greater than zero.
   - The IBAN must be present and valid.
   - The currency must be `EUR` or `USD`.
5. Assigns a risk profile based on the amount:
   - `NORMAL`: up to 500.00
   - `FORMAL_APPROVAL_REQUIRED`: above 500.00 and up to 2,000.00
   - `HIGH_RISK_REVIEW`: above 2,000.00
6. Saves valid transactions to the in-memory H2 database.
7. Writes one result row for every non-empty input transaction.

A failed transaction does not stop the batch. Its original input row is retained and written with `FAIL` and the validation or parsing reason.

## Running locally

Run the application with the Maven wrapper:

```bash
./mvnw spring-boot:run
```

Or build and run the packaged application:

```bash
./mvnw clean package
java -jar target/coding-challenge-0.0.1-SNAPSHOT.jar
```

The result is written to:

```text
output/transaction-results.csv
```

The output path can be changed with the `PROCESSING_OUTPUT_FILE` environment variable:

```bash
PROCESSING_OUTPUT_FILE=/tmp/results.csv ./mvnw spring-boot:run
```

## Running with Docker Compose

Build and start the application:

```bash
docker compose up --build
```

The generated file is available on the host at:

```text
output/transaction-results.csv
```

The Docker image uses a multi-stage build: Maven compiles the application in the first stage, and the final image contains only the Java runtime and packaged application.

## Input and output formats

The input CSV must contain these columns:

```text
payment_id,recipient_name,recipient_iban,amount,currency,payment_reference
```

The output CSV contains the original columns plus processing information:

```text
payment_id,recipient_name,recipient_iban,amount,currency,payment_reference,status,risk_profile,reason
```

Successful rows contain `PASS` and a risk profile. Failed rows contain `FAIL`, an empty risk profile, and the reason for failure.
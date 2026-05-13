# Library Management System

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-required-blue?logo=docker)

A REST API built with Java 21 and Spring Boot for managing a digital library system.

The application supports two main roles:
- **Admin** — full control over books, users, and loans
- **User** — can browse, borrow, and manage their own loans

The system integrates with the Open Library API, allowing admins to search for books externally before adding them to the local database.

---

## Features

### Admin
- Search books using the Open Library API
- Add books to the library database
- Update a book's `totalCopies`
- View all books with detailed information
- View details of a specific book
- Add and delete users
- View complete loan history (active and returned)
- View details of a specific loan

### User
- Register a new account
- Search for books available in the library
- Borrow and return books
- View active loan history
- View details of a specific active loan

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.x |
| Database | PostgreSQL |
| Infrastructure | Docker |
| API Docs | Swagger / OpenAPI |

---

## Getting Started

### Prerequisites

Make sure you have installed:
- [Java 21](https://adoptium.net/)
- [Docker](https://www.docker.com/)

### 1. Clone the repository

```bash
git clone https://github.com/your-username/library-management-system.git
cd library-management-system
```

### 2. Configure environment variables

Create a `.env` file in the project root based on `.env.example`.

### 3. Set application environment variables

Set the required environment variables in your shell or IDE run configuration. See `application-example.properties` for reference.

### 4. Start the database

```bash
docker-compose up -d
```

### 5. Run the application

```bash
./mvnw spring-boot:run
```

---

## API Documentation

The API is documented with Swagger UI, which is the recommended way to explore and test all available endpoints. Once the application is running, open:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Ports

| Service | Port |
|---|---|
| Spring Boot API | 8080 |
| PostgreSQL | 5234 |
| pgAdmin | 8081 |

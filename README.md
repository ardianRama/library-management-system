# Library Management System

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-required-blue?logo=docker)

A REST API built with Java 21 and Spring Boot for managing a digital library system.

The application supports two main roles:
- **Admin** — full control over books, users and loans
- **User** — can browse, borrow and manage their own loans

The system integrates with the Open Library API, allowing admins to search for books externally before adding them to the local database.

---

## ✨ Features

### Admin
- Search books using the Open Library API
- Add books to the library database
- Update a book's `totalCopies`
- View books with detailed information
- Add and delete users
- View users with detailed information
- View complete loan history (active and returned)

### User
- Register a new account
- Search for books available in the library
- Borrow and return books
- View active loans

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.x |
| Database | PostgreSQL |
| Container | Docker |
| API Docs | Swagger / OpenAPI |

---

## 🚀 Getting Started

### Prerequisites

Make sure you have installed:
- [Java 21](https://adoptium.net/)
- [Docker](https://www.docker.com/)

### 1. Clone the repository

```bash
git clone https://github.com/ardianRama/library-management-system.git
cd library-management-system
```

### 2. Configure Docker environment variables

Create a `.env` file in the project root based on `.env.example`.

### 3. Set application environment variables

Set the required application environment variables in your shell or IDE run configuration. See `application-example.properties` for reference.

### 4. Start the database

```bash
docker-compose up -d
```

### 5. Run the application

```bash
./mvnw spring-boot:run
```

> **💡 Tip:** The database starts empty except for the admin account. Use the `/api/books/search/external` endpoint to search for books via the Open Library API, then import them using `/api/books/import`.

---

## 📖 API Documentation

The API is documented with Swagger UI, which is the recommended way to explore and test all available endpoints. Once the application is running, open:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🔐 Authentication

The API uses JWT-based authentication via Spring Security. See `application-example.properties` for instructions on how to configure all required environment variables, including the JWT secret key.

### Authenticating in Swagger UI

1. Use the `/api/auth/login` endpoint to log in and copy the token from the response.
2. Click the **Authorize** button at the top of the page, paste your token and click **Authorize**.

All subsequent requests will now automatically include your token.

---

## 🔗 Endpoints

### Books
| Method | Endpoint | Description | Admin | User |
|---|---|---|---|---|
| `POST` | `/api/books/import` | Import a book from Open Library | ✅ | ❌ |
| `PATCH` | `/api/books/{bookId}/copies` | Update total copies of a book | ✅ | ❌ |
| `GET` | `/api/books` | View all books | ✅ | ✅ |
| `GET` | `/api/books/search` | Search books in the library | ✅ | ✅ |
| `GET` | `/api/books/search/external` | Search books via Open Library API | ✅ | ❌ |
| `GET` | `/api/books/detailed` | View all books in the library with detailed information | ✅ | ❌ |
| `GET` | `/api/books/detailed/{bookId}` | View a specific book with detailed information | ✅ | ❌ |
| `DELETE` | `/api/books/{bookId}` | Delete a book | ✅ | ❌ |

### Loans
| Method | Endpoint | Description | Admin | User |
|---|---|---|---|---|
| `POST` | `/api/loans/borrow` | Borrow a book | ✅ | ✅ |
| `POST` | `/api/loans/return` | Return a book | ✅ | ✅ |
| `GET` | `/api/loans` | View loans | ✅ | ✅ |
| `GET` | `/api/loans/{loanId}` | View a specific loan | ✅ | ✅ |

### Auth
| Method | Endpoint | Description | Admin | User |
|---|---|---|---|---|
| `POST` | `/api/auth/register` | Register a new user account | ✅ | ❌ |
| `POST` | `/api/auth/login` | Log in and receive a JWT token | ✅ | ✅ |

> Both endpoints are publicly accessible — no authentication required. Note that `/api/auth/register` is intended for unregistered users to create a new account.

### Users
| Method | Endpoint | Description | Admin | User |
|---|---|---|---|---|
| `GET` | `/api/users/detailed` | Get all users with detailed information | ✅ | ❌ |
| `GET` | `/api/users/detailed/{userId}` | Get a specific user with detailed information | ✅ | ❌ |
| `DELETE` | `/api/users/{userId}` | Delete a user | ✅ | ❌ |

---

## 🔌 Ports

| Service | Port |
|---|---|
| Spring Boot API | 8080 |
| PostgreSQL | 5234 |
| pgAdmin | 8081 |

---

## License

MIT © Ardian Rama

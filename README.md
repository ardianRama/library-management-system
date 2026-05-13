# Library Management System

A REST API built with Java 21 and Spring Boot for managing a digital library system.

The application supports two main roles:

- **Admin** — full control over books, users, and loans
- **User** — can browse, borrow, and manage their own loans

The system integrates with the Open Library API, allowing admins to search for books externally before adding them to the local database.

---

# Features

## Admin Features

- Search books using the Open Library API
- Add books to the library database
- Update a book's `totalCopies`
- View all books with detailed information
- View details of a specific book
- Add users
- Delete users
- View complete loan history
  - Active loans
  - Returned loans
- View details of a specific loan

---

## User Features

- Register a new account
- Search for books available in the library
- Borrow books
- Return books
- View active loan history
- View details of a specific active loan

---

# Tech Stack

- Java 21
- Spring Boot
- PostgreSQL
- Docker

---

# Security & Roles

The system uses role-based access control.

## ADMIN

Has full access to:
- Manage books (create, update, view all, view by ID)
- Manage users (create, delete)
- Manage loans (view all, view specific, active & returned)

## USER

Has limited access to:
- Browse books
- Borrow and return books
- View their own active loans and loan details
- Register an account

---

# Getting Started

## Prerequisites

Make sure you have installed:

- Java 21
- Docker

---

## Environment Configuration

### 1. Create a `.env` file

Create a `.env` file in the project root based on `.env.example`.

This file is required for Docker (PostgreSQL and pgAdmin) configuration.

---

### 2. Configure `application.properties`

Use `application-example.properties` as a reference for which values need to be provided:

- Database credentials
- Admin credentials (optional override via environment variables)

## Run the application

### 1. Start the database

Run Docker Compose:

```bash
docker-compose up -d

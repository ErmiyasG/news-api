# News API
RESTful News Management API built with Spring Boot. The system supports user authentication, role-based access control, article management, and article read tracking.

---

## Features

### Authentication & Authorization

- User registration
- User login with JWT authentication
- BCrypt password encryption
- Role-based access control (RBAC)
- Two user roles:
  - AUTHOR
  - READER

### Article Management

Authors can:

- Create articles
- Update their own articles
- Delete their own articles
- View their own articles

Readers and public users can:

- View published articles
- View article details

### Read Tracking

- Tracks article views
- Stores read history in a dedicated table
- Supports authenticated readers
- Read logging is performed asynchronously to avoid impacting API performance

### Validation

- Request validation using Jakarta Validation
- Email validation
- Password validation
- Article content validation
- Global exception handling

---

## Technology Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Programming Language |
| Spring Boot 3 | Application Framework |
| Spring Web | REST API Development |
| Spring Security | Authentication & Authorization |
| JWT (JSON Web Tokens) | Stateless Authentication |
| Spring Data JPA | Database Access Layer |
| PostgreSQL | Relational Database |
| Hibernate | ORM Framework |
| Maven | Dependency Management |
| Lombok | Boilerplate Code Reduction |
| Jakarta Validation | Request Validation |

---

## Technology Choices

### Spring Boot

### Spring Security + JWT

### PostgreSQL

### Spring Data JPA

### Lombok

---

# Project Structure

```text
src
├── main
│   ├── java
│   │   └── org.afrolink.er.news_api
│   │       ├── auth
│   │       ├── article
│   │       ├── readlog
│   │       ├── security
│   │       ├── user
│   │       ├── common
│   │       └── config
│   └── resources
│       ├── application.yml
│       └── db
│           └── migration
└── test
```

---

# Prerequisites

Before running the application, ensure the following are installed:

- Java 21 or later
- Maven 3.9+
- PostgreSQL 14+
- Git

Verify installation:

```bash
java --version
mvn --version
psql --version
```

---

# Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE news_api;
```

Create a database user:

```sql
CREATE USER news_user WITH PASSWORD 'password';

GRANT ALL PRIVILEGES ON DATABASE news_api TO news_user;
```

---

# Configure Application

Open:

```text
src/main/resources/application.yml
```

Update database configuration:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/news_api
    username: news_user
    password: password

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true

jwt:
  secret: your-super-secret-jwt-key-that-is-at-least-32-characters-long
```

---

# Build the Project

Clone the repository:

```bash
git clone git@github.com:ErmiyasG/news-api.git
cd news-api
```

Build using Maven:

```bash
mvn clean install
```

---

# Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

Or run the generated JAR:

```bash
java -jar target/news-api-0.0.1-SNAPSHOT.jar
```

The application will start on:

```text
http://localhost:8070
```

---

# API Endpoints

## Authentication

### Register

```http
POST /auth/register
```

### Login

```http
POST /auth/login
```

---

## Articles

### Create Article

```http
POST /articles
```

Requires AUTHOR role.

### Get My Articles

```http
GET /articles/me
```

Requires AUTHOR role.

### Update Article

```http
PUT /articles/{id}
```

Requires AUTHOR role.

### Delete Article

```http
DELETE /articles/{id}
```

Requires AUTHOR role.

### Get Published Articles

```http
GET /articles
```

Public endpoint.

### Get Article Details

```http
GET /articles/{id}
```

Public endpoint.

---

# Authentication

Protected endpoints require a JWT token.

Example:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---


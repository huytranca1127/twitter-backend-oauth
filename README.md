# Twitter-like Backend with OAuth2 and Token Authentication

## Overview
This project is a Spring Boot backend for a Twitter-like application that demonstrates:
- **OAuth2 login** (GitHub provider).
- **Token-based authentication** (UUID tokens with expiry).
- **Role-based access control** (Producer / Subscriber).
- **Protected API endpoints**.

It was developed for **CST8277 - Enterprise Application Development, Assignment 4**.

## Features
- **Login via GitHub OAuth2**: Generates a UUID token for API access.
- **Token expiry**: Default expiry is 15 minutes.
- **Roles**: Users have either `Producer` or `Subscriber` roles.
- **Protected endpoints**: Accessible only with valid token.
- **Spring Boot WebFlux**: Reactive backend architecture.
- **MySQL integration**: Persistent user storage.

## Technologies
- Java 17
- Spring Boot 3.x (WebFlux, Security, OAuth2 Client, JPA)
- MySQL 8.x
- Maven
- Docker (optional for MySQL setup)

## Setup Instructions

### 1. Clone Repository
```bash
git clone 
cd ~/assignment3/twitter-backend

```

### 2. Configure Database
Create MySQL database and table:
```sql
CREATE DATABASE twitterbackend;
USE twitterbackend;

CREATE TABLE `user` (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(100),
  password VARCHAR(100) NOT NULL,
  token VARCHAR(255),
  username VARCHAR(50) UNIQUE NOT NULL,
  token_expiry DATETIME(6),
  role ENUM('Producer','Subscriber') DEFAULT 'Subscriber'
);
```

### 3. Configure `application.properties`
```properties
# server
server.address=0.0.0.0
server.port=8080

# MySQL via Docker Compose
spring.datasource.url=jdbc:mysql://localhost:3307/twitter_db?serverTimezone=America/Toronto
spring.datasource.username=twitter_user
spring.datasource.password=CST8277
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# === GitHub OAuth2 ===
spring.security.oauth2.client.registration.github.client-id=Ov23liYQTeKwP3Cmzr6u
spring.security.oauth2.client.registration.github.client-secret=e9efb1e4bc7a2e9296cbfb4bfca5ad4d1f80b69e
spring.security.oauth2.client.registration.github.scope=read:user
spring.security.oauth2.client.registration.github.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}
spring.security.oauth2.client.registration.github.client-authentication-method=client_secret_post

```

### 4. Run Application
```bash
mvn spring-boot:run
```

### 5. Endpoints

#### 5.1 Login & Token
- **URL:** `/token`
- **Method:** GET
- **Description:** After OAuth2 login, generates and returns a UUID token.

#### 5.2 Protected Hello
- **URL:** `/protected/hello`
- **Method:** GET
- **Header:** `Authorization: Bearer <your-token>`
- **Description:** Returns a role-based greeting.

### 6. Example Token Flow
1. Login via GitHub OAuth2 → get token from `/token`.
2. Call `/protected/hello` with token.

## Author
Thai Huy Tran  
CST8277 Assignment 4

## License
This project is for educational purposes only.

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
git clone https://github.com/huytranca1127/twitter-backend-oauth.git 
cd twitter-backend-oauth

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

## Testing Instructions for Professor (Token Expiry Consideration)

**Important:** Tokens expire **15 minutes** after they are issued.  
If you try to use an expired token, the API will return `401 Unauthorized`.

### Steps to Test:
1. Start the application with `mvn spring-boot:run`.
2. Open browser → go to [http://localhost:8080/token](http://localhost:8080/token).
3. Login with your GitHub account.
4. Copy the generated token shown in the browser (example: `ff4bcfa8-7738-4e18-95c7-06da1033fe1c`).
5. Open Postman → create a **GET** request to:  
   `http://localhost:8080/protected/hello`
6. Add header:
   ```
   Authorization: Bearer <your-token>
   ```
7. Send the request and verify you get a **role-based greeting**.
8. If the token expires, repeat from **Step 2** to get a new token.

## Author
Thai Huy Tran  
CST8277 Assignment 4

## License
This project is for educational purposes only.


# 🛒 SmartCart - E-Commerce Backend

A production-ready **E-Commerce Backend** built using **Spring Boot**, following a layered architecture with secure JWT authentication, role-based authorization, RESTful APIs, and MySQL database integration.

---

## 🚀 Features

- 🔐 JWT Authentication & Authorization
- 👤 Role-Based Access Control (ADMIN / USER)
- 📦 Product Management
- 🛒 Shopping Cart Management
- 📋 Order Management
- 👥 User Management
- ✅ Request Validation
- ⚠️ Global Exception Handling
- 📄 Swagger API Documentation
- 🗄️ MySQL Database Integration
- 🔑 BCrypt Password Encryption
- 🏗️ Layered Architecture (Controller → Service → Repository)

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Database
- MySQL

### Authentication
- JWT (JSON Web Token)
- BCrypt Password Encoder

### Documentation
- Swagger / OpenAPI

### Build Tool
- Maven

---

## 📂 Project Structure

```
src
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── exception
 ├── repository
 ├── security
 │    ├── jwt
 │    └── service
 └── service
```

---

## 📌 API Modules

### Authentication
- Login

### User
- Create User
- Get User
- Update User
- Delete User

### Product
- Add Product
- Update Product
- Delete Product
- Search Products
- Get Products by Category
- Get Products by Brand

### Cart
- Add Product to Cart
- Update Quantity
- Remove Product
- View Cart

### Order
- Place Order
- Get Order
- Get User Orders

---

## 🔒 Security

- JWT Authentication
- Role-Based Authorization
- BCrypt Password Encryption
- Stateless Session Management
- Protected REST APIs

---

## 📄 API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

## ⚙️ Getting Started

### Clone Repository

```bash
git clone https://github.com/niteshsharma6245-bot/SmartCart-ECommerce-Backend.git
```

### Navigate

```bash
cd SmartCart-ECommerce-Backend
```

### Configure

Create:

```
src/main/resources/application.properties
```

Add your database credentials and JWT configuration.

### Run

```bash
mvn spring-boot:run
```

---

## 📸 Screenshots

### Swagger UI

> Add Swagger screenshots here.

---

## 🔮 Future Enhancements

- Payment Gateway Integration
- Email Notifications
- Docker Support
- Redis Caching
- Kafka Integration
- Microservices Architecture
- Product Reviews & Ratings
- Wishlist
- Inventory Dashboard
- Frontend (React)

---

## 👨‍💻 Author

**Nitesh Sharma**

- GitHub: https://github.com/niteshsharma6245-bot
- LinkedIn: *(Add your LinkedIn profile)*

---

## ⭐ If you like this project

Give this repository a ⭐ on GitHub.

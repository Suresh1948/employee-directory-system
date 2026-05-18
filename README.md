# Employee Directory System

A full-stack secure Employee Directory application built using Spring Boot and Angular with JWT authentication and Google OAuth2 login.

---

## 🚀 Tech Stack

### Backend
- Java 17+
- Spring Boot 3+
- Spring Security (JWT + OAuth2)
- JPA / Hibernate
- MySQL
- Maven

### Frontend
- Angular 16+
- Angular Material
- TypeScript
- RxJS

---

## 🔐 Features

### Authentication
- Email & Password login (JWT-based)
- Google OAuth2 login (Google Identity)
- Role-based access (ADMIN / USER)
- Secure password hashing (BCrypt)
- JWT token expiration (60 min)

### Employee Management
- Create Employee (ADMIN only)
- Update Employee (ADMIN only)
- Delete Employee (ADMIN only)
- View Employee list (USER/ADMIN)
- Search, Pagination

### Security
- Spring Security 6
- JWT Authentication Filter
- Role-based authorization (RBAC)
- CORS enabled

---

## 🌍 Environment Variables

Create the following environment variables before running the backend:

DB_USERNAME=root  
DB_PASSWORD=root  
GOOGLE_CLIENT_ID=905237510600-5sc43n0mp8okmdn4hv9oi6bmhj8kv8cs.apps.googleusercontent.com

## 🚀 How to Run the Project

Follow the steps below to run the backend and frontend applications locally.

---

# 🔧 Backend (Spring Boot)

### 1️⃣ Navigate to backend folder using command "cd backend"

### 2️⃣ Set the above environment variables

### 3️⃣ Run the application using command "mvn spring-boot:run"

### 4️⃣ Backend will start at:http://localhost:8080

---

# 🎨 Frontend (Angular)

### 1️⃣ Navigate to frontend folder using command "cd frontend/employee-directory-frontend"

### 2️⃣ Install dependencies using commmand "npm install"

### 3️⃣ Run Angular application using command "ng serve"

### 4️⃣ Frontend will run at:http://localhost:4200


---

# ⚠️ Important Notes

- Backend must run before frontend
- Ensure MySQL is running
- Ensure environment variables are set correctly
- Use correct Google Client ID in frontend & backend

---

## 1️⃣ Register User

### ➤ Post Endpoint : http://localhost:8080/api/auth/register

### ➤ Request Body
{
  "name": "Suresh",
  "email": "Suresh@test.com",
  "password": "123456",
  "role": "ADMIN"
}

### ➤ Response
{
    "message": "User registered successfully"
}

## 2️⃣ Login (Email + Password)

### ➤ Post Endpoint : http://localhost:8080/api/auth/login

### ➤ Request Body
{
  "email": "Suresh@test.com",
  "password": "123456",
}

### ➤ Response
{
    "token": "jwt-token",
    "email": "Suresh@test.com",
    "role": "ADMIN"
}

3️⃣ Google OAuth Login

### ➤ Post Endpoint : http://localhost:8080/api/auth/google

### ➤ Request Body
{
  "token": "google-id-token-from-frontend"
}

### ➤ Response
{
  "token": "jwt-token-here",
  "email": "user@gmail.com",
  "role": "USER"
}
## 🏗️ Project Structure

frontend/
 └── src/app/
      ├── core/
      │    ├── services
      │    ├── guards
      │    ├── interceptors
      ├── shared/
      ├── features/
      │    ├── auth/
      │    ├── employees/
      │    ├── admin/

  backend/
 └── src/main/java/com/employeeDirectory
      ├── controller
      ├── service
      ├── repository
      ├── entity
      ├── dto
      ├── security
      └── config    
 
---

## 🧠 Architecture Flow

Frontend (Angular)
↓
Auth Service (JWT / Google Login)
↓
Spring Boot Controller
↓
Service Layer (Business Logic)
↓
Repository (Database Access)
↓
MySQL Database



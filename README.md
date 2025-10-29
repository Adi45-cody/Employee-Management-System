# 🌐 Employee Management System

A **Spring Boot-based web application** for managing employee data with secure login, REST APIs, file management, and a user-friendly web interface built using **Thymeleaf**.  

The system supports **both MySQL and PostgreSQL**, offers **role-based access (USER / ADMIN)**, and includes **Swagger** for API documentation and testing.

---

## 🚀 Project Overview
The **Employee Management System** allows organizations to efficiently handle employee records with features like adding, updating, viewing, and deleting employees.  
It also provides file upload and download functionalities, RESTful APIs, and secure access control.

---

## ⚙️ Tech Stack
| Category | Technology |
|-----------|-------------|
| Backend | Java, Spring Boot |
| Frontend | Thymeleaf, HTML, CSS |
| Security | Spring Security (In-memory users) |
| Database | MySQL / PostgreSQL |
| Build Tool | Maven |
| API Documentation | Swagger (Springdoc OpenAPI) |

---

## 🧭 API & Web Endpoints

### 🔓 Public (No Login Required)
| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/` | GET | Home page (optional redirect to `/main`) |
| `/login` | GET | Login page (Thymeleaf form) |
| `/logout` | GET / POST | Logout endpoint |
| `/swagger-ui/index.html` | GET | Swagger UI for API testing |
| `/v3/api-docs` | GET | Swagger API Docs (JSON) |

---

### 🔐 After Login (Web Pages)
| Endpoint | Method | Roles | Description |
|-----------|---------|--------|-------------|
| `/main` | GET | USER, ADMIN | Dashboard page |
| `/employeeForm` | GET | USER, ADMIN | Add employee form |
| `/saveEmployee` | POST | USER, ADMIN | Add new employee |
| `/viewEmployees` | GET | USER, ADMIN | List of all employees |
| `/viewEmp/{id}` | GET | USER, ADMIN | View single employee |
| `/files/**` | Any | ADMIN | File upload/download/update/delete |

---

### 🧰 REST APIs (Swagger / Postman)
| Endpoint | Method | Roles | Description |
|-----------|---------|--------|-------------|
| `/api/employees` | GET | USER, ADMIN | Fetch all employees |
| `/api/employees/{id}` | GET | USER, ADMIN | Fetch employee by ID |
| `/api/employees` | POST | USER, ADMIN | Create new employee |
| `/api/employees/{id}` | DELETE | ADMIN | Delete employee (Admin only) |

---

## 🔑 Authentication Details
| Username | Password | Role |
|-----------|-----------|------|
| `admin` | `admin123` | ADMIN |
| `user` | `user123` | USER |

---

## 🖼️ Features
- 👥 Manage Employees (Add / Update / Delete / View)
- 🧾 RESTful APIs with JSON responses
- 🗂️ File Upload, Download, Update, and Delete
- 🔒 Role-Based Access using Spring Security
- 💾 Works with both MySQL and PostgreSQL
- 🧩 Thymeleaf UI for web users
- 📄 Swagger UI for API testing

---

## ⚡ How to Run Locally

### 1️⃣ Clone the repository
```bash
git clone https://github.com/Adi45-cody/Employee-Management-System.git
cd Employee-Management-System
````

---
### 2️⃣ Configure the Database
Open the `application.properties` file and update your database credentials for **MySQL** or **PostgreSQL**:

```properties
# For MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=root
spring.datasource.password=Aditya@6418

# OR For PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/Employee_DB
spring.datasource.username=postgres
spring.datasource.password=1234
````

---

### 3️⃣ Build and Run the Application

Use Maven to compile and start the Spring Boot app:

```bash
mvn spring-boot:run
```

Once started, your application will be available on port `8080`.

---

### 4️⃣ Access the Application

| Platform               | URL                                           | Description                   |
| ---------------------- | --------------------------------------------- | ----------------------------- |
| 🌍 Web App (Thymeleaf) | `http://localhost:8080/main`                  | Dashboard (requires login)    |
| 🔑 Login Page          | `http://localhost:8080/login`                 | Enter admin/user credentials  |
| 🧾 Swagger UI          | `http://localhost:8080/swagger-ui/index.html` | Interactive API documentation |
| 🧪 REST API            | `http://localhost:8080/api/employees`         | Use with Postman (Basic Auth) |

---

### 5️⃣ Project Structure

```
Employee-Management-System/
 ├── src/main/java/com/example/demo/
 │   ├── Controller/        # Web & REST controllers
 │   ├── Model/             # Entity classes
 │   ├── Repository/        # Data access layer
 │   ├── Service/           # Business logic
 │   └── Security/          # Security configuration
 ├── src/main/resources/
 │   ├── templates/         # Thymeleaf templates
 │   ├── static/            # CSS / JS / Images
 │   └── application.properties
 └── pom.xml                # Maven dependencies
```

---

## 💡 Future Enhancements

* 🔐 JWT-based Authentication
* 🗃️ Database Migration with Flyway
* 📊 REST API Pagination & Sorting
* 📧 Email Notifications for Employee Updates
* 📱 Responsive Frontend using Bootstrap or React

---

## 🧑‍💻 Author

**Aditya Dhamal**
💼 Java Developer Intern — Vidya Online Services Pune Pvt. Ltd.
📧 [Contact via GitHub Issues](https://github.com/Adi45-cody)

```




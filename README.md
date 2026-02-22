
#  Employee Management System

A **Spring Boot-based web application** for managing employee data with secure login, REST APIs, file management, and a user-friendly web interface built using **Thymeleaf**.

The system supports **both MySQL and PostgreSQL**, offers **role-based access (USER / ADMIN)**, and includes **Swagger** for API documentation and testing.

---

##  Project Overview
The **Employee Management System** allows organizations to efficiently handle employee records with features like adding, updating, viewing, and deleting employees.  
It also provides file upload and download functionalities, RESTful APIs, and secure access control using **Spring Security** with role-based authorization.

---

##  Tech Stack
| Category | Technology |
|---|---|
| Backend | Java, Spring Boot |
| Frontend | Thymeleaf, HTML, CSS, Bootstrap |
| Security | Spring Security (Role-Based Authentication) |
| Database | MySQL / PostgreSQL |
| Build Tool | Maven |
| API Documentation | Swagger (Springdoc OpenAPI) |

---

##  API & Web Endpoints

###  Public (No Login Required)
| Endpoint | Method | Description |
|---:|:---:|---|
| `/` | GET | Home page (optional redirect to `/main`) |
| `/login` | GET | Login page (Thymeleaf form) |
| `/logout` | GET / POST | Logout endpoint |
| `/swagger-ui/index.html` | GET | Swagger UI for API testing |
| `/v3/api-docs` | GET | Swagger API Docs (JSON) |

---

###  After Login (Web Pages)
| Endpoint | Method | Roles | Description |
|---:|:---:|:---:|---|
| `/main` | GET | USER, ADMIN | Dashboard page |
| `/employeeForm` | GET | ADMIN | Add employee form |
| `/saveEmployee` | POST | ADMIN | Add new employee |
| `/viewEmployees` | GET | USER, ADMIN | List all employees |
| `/viewEmp/{id}` | GET | USER, ADMIN | View single employee |
| `/files/**` | Any | ADMIN | File upload, download, update, delete |

---

###  REST APIs (Swagger / Postman)
| Endpoint | Method | Roles | Description |
|---:|:---:|:---:|---|
| `/api/employees` | GET | USER, ADMIN | Fetch all employees |
| `/api/employees/{id}` | GET | USER, ADMIN | Fetch employee by ID |
| `/api/employees` | POST | ADMIN | Create new employee |
| `/api/employees/{id}` | DELETE | ADMIN | Delete employee (Admin only) |

---

##  Authentication Details (Testing)
> Use these default credentials for quick local testing only. In production, create proper users and never commit real credentials.

| Username | Password | Role |
|---:|:---:|:---:|
| `admin` | `admin123` | ADMIN |
| `user`  | `user123`  | USER |

---
````
##  Features
-  Manage Employees (Add / Update / Delete / View)  
-  RESTful APIs with JSON responses  
-  File Upload, Download, Update, and Delete  
-  Role-Based Access (Admin & User)  
-  Supports both MySQL and PostgreSQL  
-  Thymeleaf UI for web users  
-  Swagger UI for API documentation and testing

---

##  How to Run Locally
````
### 1 Clone the Repository
```bash
git clone https://github.com/Adi45-cody/Employee-Management-System.git
cd Employee-Management-System
````

---

### 2 Configure the Database

Open `src/main/resources/application.properties` and update with your database credentials.

**MySQL example**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=YOUR_MYSQL_USER
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

**PostgreSQL example**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Employee_DB
spring.datasource.username=YOUR_PG_USER
spring.datasource.password=YOUR_PG_PASSWORD
```

---

### 3 Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

Application will run on port `8080` by default.

---

### 4 Access the Application

| Platform               | URL                                           | Description                   |
| ---------------------- | --------------------------------------------- | ----------------------------- |
|  Web App (Thymeleaf) | `http://localhost:8080/main`                  | Dashboard (requires login)    |
|  Login Page          | `http://localhost:8080/login`                 | Enter admin/user credentials  |
|  Swagger UI          | `http://localhost:8080/swagger-ui/index.html` | Interactive API documentation |
|  REST API            | `http://localhost:8080/api/employees`         | Use with Postman (Basic Auth) |

---

##  Project Structure

```
Employee-Management-System/
 ├── src/main/java/com/example/demo/
 │   ├── config/             # SecurityConfig, app config
 │   ├── controller/         # Web & REST controllers
 │   ├── entity/             # Employee, User, Role entities
 │   ├── repository/         # JPA repositories
 │   ├── service/            # Business & Security services
 │   └── EmployeeManagementSystemApplication.java
 ├── src/main/resources/
 │   ├── templates/          # Thymeleaf HTML templates
 │   ├── static/             # CSS / JS / Images
 │   └── application.properties
 ├── pom.xml                 # Maven dependencies
 └── README.md
```

---

##  Notes

* Use **Postman with Basic Auth** to test APIs without the browser login.
* Swagger will prompt for credentials when security is enabled.
* File uploads are stored under the path defined by `file.upload-dir` in `application.properties` (default: `Uploads`).
* Passwords in DB are encrypted with **BCrypt** — use `BCryptPasswordEncoder` when you create users so logins will work.

---

##  Future Enhancements

*  JWT-based Authentication
*  Database Migration with Flyway
*  Admin Dashboard with Analytics
*  Email Notifications for Employee Updates
*  Responsive Frontend with React or Angular

---

##  Author

**Aditya Dhamal**
 *Java Developer Intern — Vidya Online Services Pune Pvt. Ltd.*
 [Contact via GitHub Issues](https://github.com/Adi45-cody)

```


::contentReference[oaicite:0]{index=0}
```

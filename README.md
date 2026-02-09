# NSBM Student Hub

A Spring Boot Student Management System with Role-Based Authentication, pagination, and sorting capabilities.

## Features

-**Student CRUD Operations** - Create, Read, Update, Delete students
-**Pagination & Sorting** - Paginated results with customizable sorting
-**Role-Based Authentication** - USER, MODERATOR, ADMIN roles
-**Password Encryption** - BCrypt password hashing
-**JWT Authentication** - Secure token-based authentication
-**MySQL Database** - Persistent data storage
-**RESTful API** - Clean REST API endpoints

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Security**
- **Spring Data JPA**
- **MySQL Database**
- **JWT (JSON Web Tokens)**
- **Lombok**
- **Maven**

## Project Structure

```
src/main/java/com/nsbm/studenthub/
├── NsbmStudentHubApplication.java
├── config/
│   ├── SecurityConfig.java
│   └── DataInitializer.java
├── controller/
│   ├── AuthController.java
│   ├── StudentController.java
│   └── TestController.java
├── dto/
│   ├── StudentDTO.java
│   ├── LoginRequest.java
│   ├── SignupRequest.java
│   ├── JwtResponse.java
│   └── MessageResponse.java
├── entity/
│   ├── Student.java
│   ├── User.java
│   ├── Role.java
│   └── ERole.java
├── exception/
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java
├── repository/
│   ├── StudentRepository.java
│   ├── UserRepository.java
│   └── RoleRepository.java
├── security/
│   ├── UserDetailsImpl.java
│   ├── UserDetailsServiceImpl.java
│   └── jwt/
│       ├── JwtUtils.java
│       ├── AuthEntryPointJwt.java
│       └── AuthTokenFilter.java
└── service/
    ├── StudentService.java
    └── impl/
        └── StudentServiceImpl.java
```

## Database Schema

### Students Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated primary key |
| name | VARCHAR(100) | Student name |
| email | VARCHAR(255) | Unique email address |
| batch | VARCHAR(50) | Batch/Year |
| gpa | DOUBLE | GPA (0.0 - 4.0) |

### Users Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated primary key |
| username | VARCHAR(50) | Unique username |
| email | VARCHAR(100) | Unique email |
| password | VARCHAR(255) | BCrypt encrypted password |

### Roles Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated primary key |
| name | VARCHAR(20) | Role name (ROLE_USER, ROLE_MODERATOR, ROLE_ADMIN) |

## Setup Instructions

### Prerequisites
1. Java 17 or higher
2. MySQL Server
3. Maven

### Database Setup
1. Create a MySQL database named `nsbm_student_hub`:
```sql
CREATE DATABASE nsbm_student_hub;
```

2. Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
```bash
# Navigate to project directory
cd nsbm-student-hub

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Default Users

On first run, the following users are created:

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN, MODERATOR, USER |
| moderator | mod123 | MODERATOR, USER |
| user | user123 | USER |

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |

### Students (Requires Authentication)
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/students` | USER+ | Get all students (paginated) |
| GET | `/api/students/{id}` | USER+ | Get student by ID |
| GET | `/api/students/email/{email}` | USER+ | Get student by email |
| GET | `/api/students/batch/{batch}` | USER+ | Get students by batch |
| GET | `/api/students/search?name=` | USER+ | Search students by name |
| GET | `/api/students/gpa/{minGpa}` | USER+ | Get students by min GPA |
| POST | `/api/students` | MOD+ | Create new student |
| PUT | `/api/students/{id}` | MOD+ | Update student |
| DELETE | `/api/students/{id}` | ADMIN | Delete student |

### Pagination & Sorting Parameters
- `page` - Page number (0-indexed, default: 0)
- `size` - Number of records per page (default: 10)
- `sortBy` - Field to sort by (default: id)
- `sortDir` - Sort direction: asc/desc (default: asc)

### Test Endpoints
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/test/public` | PUBLIC | Test public access |
| GET | `/api/test/user` | USER+ | Test user access |
| GET | `/api/test/mod` | MOD+ | Test moderator access |
| GET | `/api/test/admin` | ADMIN | Test admin access |

## API Usage Examples

### Register a New User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "password123",
    "roles": ["user"]
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

### Get Students (with pagination & sorting)
```bash
curl -X GET "http://localhost:8080/api/students?page=0&size=5&sortBy=name&sortDir=asc" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Create Student
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "name": "New Student",
    "email": "newstudent@nsbm.ac.lk",
    "batch": "2024",
    "gpa": 3.5
  }'
```

## Security Notes

- All passwords are encrypted using **BCrypt** before storing in the database
- JWT tokens are used for stateless authentication
- Role-based access control restricts API access
- CORS is enabled for all origins (configure for production)

## Author

NSBM Student Hub - Spring Boot Application

## License

This project is for educational purposes.

# JWT Authentication & User Management API

## Dependencies

Add the following dependencies in `pom.xml`:

### Spring Boot
- spring-boot-starter-web
- spring-boot-starter-security
- spring-boot-starter-data-jpa
- spring-boot-starter-validation

### Database
- mysql-connector-j

### JWT
- jjwt-api
- jjwt-impl
- jjwt-jackson

### Utilities
- lombok
- mapstruct
- mapstruct-processor

---

## Security Flow

1. **Register**
   - User submits registration details
   - Password is encrypted using BCrypt
   - Role is assigned (USER by default)
   - User is saved as active

2. **Login**
   - Username and password are validated
   - If valid, a JWT token is generated
   - JWT contains:
     - username
     - role(s)

3. **JWT Authentication**
   - Client sends JWT in request header:
     ```
     Authorization: Bearer <TOKEN>
     ```
   - `JwtAuthenticationFilter` validates token
   - SecurityContext is populated

4. **Authorization**
   - Access is controlled using `@PreAuthorize`
   - Role-based access:
     - ADMIN
     - USER

---

## API Endpoints

### Public APIs

#### Register

POST /api/auth/register


#### Login

POST /api/auth/login

---

### Secured APIs (JWT Required)

#### Fetch All Users (ADMIN)


GET /api/auth/users


#### Fetch User by ID (ADMIN, USER)


GET /api/auth/users/{id}


#### Fetch User by Username (ADMIN, USER)


GET /api/auth/users/username/{username}


#### Update User (ADMIN, USER)


PUT /api/auth/users/{id}


#### Patch User (ADMIN, USER)


PATCH /api/auth/users/{id}


#### Delete User (ADMIN)


DELETE /api/auth/users/{id}


#### Deactivate User (ADMIN)


PATCH /api/auth/users/{id}/deactivate


---

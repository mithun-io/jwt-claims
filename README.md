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

## Build & Run the Application (JAR)

### Prerequisites
- Java 17+ installed
- Maven installed (or Eclipse Embedded Maven)
- MySQL running
- Database credentials configured in `application.properties`

---

### Step 1: Build the JAR using Eclipse

1. Open the project in Eclipse
2. Right-click the project
3. Select **Run As → Maven build...**
4. In **Goals**, enter: clean package

5. Click **Run**

If the build is successful, you will see:
BUILD SUCCESS

---

### Step 2: Locate the Generated JAR

After a successful build, Maven generates the JAR file in:

target/
└── jwt-claims-0.0.1-SNAPSHOT.jar

---

### Step 3: Run the JAR from Command Line (CMD)

1. Open Command Prompt
2. Navigate to the `target` directory:
   ```cmd
   cd path\to\your-project\target
### Example:

### cd C:\Users\mithu\eclipse-workspace\jwt-claims\target
### Run the application:
java -jar jwt-claims-0.0.1-SNAPSHOT.jar

### Step 4: Verify Application
#### If the application starts successfully, you will see:
Tomcat started on port 8080
Started JwtClaimsApplication
### The API will be available at: http://localhost:8080

### Common Issues
#### Build Failure – Unable to Delete target Folder, Stop any running application in Eclipse

### Close Eclipse, Delete the target folder manually, Pause OneDrive syncing if the project is inside OneDrive
### Re-run clean package

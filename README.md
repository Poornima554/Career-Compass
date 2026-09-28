# CareerCompass

CareerCompass is a simple Spring Boot + MySQL + JSP web application that helps students discover internships and entry-level opportunities across multiple sectors instead of restricting recommendations to software roles.

## Sectors

- Technology
- Core Engineering
- Design & Creative
- Sales & Marketing
- Business & Operations

## Backend concepts adapted from the Journal App style

The project uses the simple, interview-friendly concepts documented for the Journal App: Spring Boot layered architecture, Spring Security, JWT authentication, role-based authorization, REST CRUD APIs and Swagger/OpenAPI. The original Journal App overview also mentions Redis and Kafka, but they are intentionally not included here because the goal for CareerCompass is a simple, easy-to-run project with no extra infrastructure.

## Stack

- Java 17
- Spring Boot 4.1.1
- Spring MVC
- Spring Data JPA / Hibernate
- MySQL 8
- Spring Security
- JWT (JJWT)
- Maven
- JSP / JSTL
- HTML / CSS / JavaScript
- Swagger / OpenAPI

## Project flow

Browser -> JSP -> fetch() -> Spring REST Controller -> Service -> Repository -> MySQL

JWT is stored in sessionStorage by the JSP frontend and sent as a Bearer token for protected REST requests.

## Recommendation logic

Recommendations are deliberately rule-based and explainable:

- 45 points: same sector
- up to 25 points: required-skill overlap
- 20 points: interest/tag match
- 10 points: CGPA eligibility

The response also shows matched skills, missing skills and a short reason. Opportunities with past deadlines or unmet minimum CGPA are skipped.

## Default admin

Email: admin@careercompass.com
Password: Admin@123

Change the password before using the project outside local development.

## Setup

1. Install JDK 17 and Maven.
2. Install MySQL 8 and start the MySQL service.
3. Create the database:

   ```sql
   CREATE DATABASE career_compass;
   ```

4. Open `src/main/resources/application.properties` and set:

   ```properties
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```

5. Keep `spring.jpa.hibernate.ddl-auto=update` for the first run. Hibernate creates the tables automatically.
6. Import the project into Eclipse as an Existing Maven Project.
7. Run `Maven -> Update Project`.
8. Run `CareerCompassApplication` as a Spring Boot application.
9. Open `http://localhost:8080/`.
10. Register a student account or use the default admin credentials.

## Common checks if startup fails

### MySQL connection error

Check that MySQL is running and the username/password in `application.properties` are correct.

### Port 8080 already in use

Stop the other process or change `server.port`.

### JSP pages do not render

Keep the project packaged as `war`. JSP is placed under `src/main/webapp/WEB-INF/jsp`.

### 401 / 403 in the browser

Login first. The JSP frontend stores the JWT in `sessionStorage`. If a role is wrong or a token expired, use Logout and sign in again.

## Main URLs

- `/` -> login
- `/register` -> student registration
- `/student` -> student dashboard
- `/admin` -> admin dashboard
- `/swagger-ui.html` -> Swagger UI

## APIs

### Authentication

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`

### Student

- `GET /api/students/me`
- `PUT /api/students/me`
- `GET /api/students/me/skills`
- `POST /api/students/me/skills/{skillId}`
- `DELETE /api/students/me/skills/{skillId}`

### Opportunities

- `GET /api/opportunities`
- `GET /api/opportunities/{id}`
- `POST /api/opportunities` (ADMIN)
- `DELETE /api/opportunities/{id}` (ADMIN)

### Applications

- `POST /api/applications/{opportunityId}` (STUDENT)
- `GET /api/applications/me` (STUDENT)
- `GET /api/applications` (ADMIN)
- `PUT /api/applications/{applicationId}/status` (ADMIN)

### Recommendations

- `GET /api/recommendations` (STUDENT)

### Skills

- `GET /api/skills`
- `POST /api/skills?name=Figma` (ADMIN)
- `DELETE /api/skills/{id}` (ADMIN)

### Admin

- `GET /api/admin/stats` (ADMIN)

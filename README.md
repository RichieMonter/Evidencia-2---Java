# IAC App (Spring Boot)

This project implements a RESTful service for managing IAC (Body Adiposity Index) records.

Features:
 - Spring Boot (Maven)
 - REST CRUD endpoints: POST, GET (list and by id), PUT, DELETE
 - Accepts JSON and XML representations
 - Filter that validates incoming POST/PUT requests and computes IAC. The computed value is attached as a request attribute `computedIac`.
 - Service layer, in-memory repository (ConcurrentHashMap)
 - Validation and global exception handler

IAC calculation used (BAI-like):
    IAC = (hip_cm / (height_m ^ 1.5)) - 18

How to run:
1. Build: `mvn clean package`
2. Run: `java -jar target/iac-app-1.0.0.jar`
3. Endpoints:
   - POST /api/iacs
   - GET /api/iacs
   - GET /api/iacs/{id}
   - PUT /api/iacs/{id}
   - DELETE /api/iacs/{id}

Notes for the evidence / rubric:
 - Create GitHub repo with README.md and .gitignore
 - Use branches `develop` and `master`. Each feature should be developed in its branch and merged to `develop`.
 - Document in Wiki the endpoints and guides.


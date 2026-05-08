# Customer Document Management System

This project is implemented using **Spring Boot**, following the principles of **Domain-Driven Design (DDD)** and **Hexagonal Architecture (Ports and Adapters)**.

---

## Task Requirements

Build a Spring Boot application for managing XML documents with the following features:

### 1. Upload XML files:
- File naming format: `customer_type_date.xml`
- File name validation
- Saving the result to the file system
- Throwing an exception if a file with the same name already exists

### 2. Replace file:
- Same logic as upload
- If the file already exists, it must be replaced

### 3. Delete file:
- Delete a file by its name

### 4. Get file content:
- Retrieve file content by name

### 5-7. Search files:
- By date
- By customer
- By type

---

## Build and Run Commands

Since this is a multi-module Maven project, all commands must be executed from the root directory (where the main `pom.xml` is located).

---

### 1. Build the project

```bash
./mvnw clean install
```

### 2. Run the project

The application is started via the infrastructure module, as it contains the @SpringBootApplication main class.

```bash
java -jar infrastructure/target/infrastructure-0.0.1-SNAPSHOT.jar
```

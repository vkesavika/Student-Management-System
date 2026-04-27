# Student Management System
### Java + MySQL Console Application

---

## Project Structure

```
StudentManagement/
├── src/
│   └── com/student/
│       ├── db/
│       │   └── DatabaseConnection.java   ← JDBC connection helper
│       ├── model/
│       │   └── Student.java              ← Student POJO
│       ├── dao/
│       │   ├── StudentDAO.java           ← Interface
│       │   └── StudentDAOImpl.java       ← JDBC implementation (CRUD)
│       └── main/
│           └── MainApp.java              ← Console menu entry point
├── sql/
│   └── schema.sql                        ← DB schema + sample data
├── lib/
│   └── (put mysql-connector-java jar here)
└── README.md
```

---

## Prerequisites

| Requirement | Version |
|-------------|---------|
| Java JDK    | 11 +    |
| MySQL       | 8.x     |
| MySQL Connector/J | 8.x |

---

## Setup Steps

### 1. Database
```sql
-- Run in MySQL Workbench or mysql CLI:
source sql/schema.sql
```

### 2. MySQL Connector JAR
Download from https://dev.mysql.com/downloads/connector/j/  
Place the `.jar` file inside the **`lib/`** folder.

### 3. Update DB credentials
Open `src/com/student/db/DatabaseConnection.java` and set:
```java
private static final String URL      = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
private static final String USER     = "root";         // ← your MySQL username
private static final String PASSWORD = "your_password"; // ← your MySQL password
```

---

## Compile

```bash
# From the StudentManagement/ directory
mkdir -p out

javac -cp "lib/mysql-connector-java-8.x.x.jar" \
      -d out \
      src/com/student/db/DatabaseConnection.java \
      src/com/student/model/Student.java \
      src/com/student/dao/StudentDAO.java \
      src/com/student/dao/StudentDAOImpl.java \
      src/com/student/main/MainApp.java
```

> **Windows:** Replace `:` with `;` in `-cp` values.

---

## Run

```bash
java -cp "out:lib/mysql-connector-java-8.x.x.jar" com.student.main.MainApp
```

---

## Features

| # | Operation | Description |
|---|-----------|-------------|
| 1 | Add Student | Insert a new student record |
| 2 | View All Students | Display all records in a table |
| 3 | View by ID | Fetch one student by primary key |
| 4 | Update Student | Edit any field (keep blank to retain current) |
| 5 | Delete Student | Remove a record with confirmation |
| 6 | Exit | Close the app |

---

## Sample Output

```
===========================================
     Student Management System v1.0
===========================================

-------------------------------------------
  1. Add Student
  2. View All Students
  ...
-------------------------------------------
Enter choice: 2

-- All Students --
+------+----------------------+---------------------------+------+----------------------+
| ID   | Name                 | Email                     | Age  | Course               |
+------+----------------------+---------------------------+------+----------------------+
| 1    | Alice Johnson        | alice@example.com         | 20   | Computer Science     |
| 2    | Bob Smith            | bob@example.com           | 22   | Mathematics          |
...
```

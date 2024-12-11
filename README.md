# Student Management System

This project is a **Student Management System** implemented in Java using the Swing framework for the graphical user interface and MySQL for the backend database. The application allows users to perform CRUD (Create, Read, Update, Delete) operations on student records.

## Features

- **Add Student**: Insert a new student record into the database.
- **Update Student**: Modify existing student details.
- **Delete Student**: Remove a student record using their ID.
- **Search Student**: Search for students by name or ID.
- **Reset Fields**: Clear all input fields for new data entry.
- **Table View**: View all student records in a tabular format.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java JDK** (Version 8 or higher)
- **MySQL Server**
- **JDBC Driver** (MySQL Connector/J)

## Database Setup

1. Create a MySQL database named `student_management`.
2. Create a table `students` with the following structure:

```sql
CREATE TABLE students (
    name VARCHAR(100),
    student_id VARCHAR(50) PRIMARY KEY,
    grade VARCHAR(10),
    dob DATE,
    gender VARCHAR(10),
    contact VARCHAR(15),
    email VARCHAR(100)
);
```

3. Update the database connection details in the code:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
```

Replace `your_password` with the actual password for your MySQL `root` user.

## How to Run

1. Clone the repository:

```bash
git clone https://github.com/yourusername/student-management-system.git
```

2. Compile the code:

```bash
javac StudentManagementSystem.java
```

3. Run the application:

```bash
java StudentManagementSystem
```

## Technologies Used

- **Java Swing**: For building the graphical user interface.
- **MySQL**: For storing student data.
- **JDBC**: For database connectivity.

## Project Structure

```
StudentManagementSystem/
|— StudentManagementSystem.java
|— README.md
```

## Screenshots

### Main UI
![Main UI](![image](https://github.com/user-attachments/assets/17beb319-727a-4e59-a1e2-ceba625a1953)
)

## Known Issues

- Ensure the MySQL server is running before launching the application.
- Provide valid inputs in all required fields to avoid runtime errors.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Java Swing Documentation: [Oracle Java Swing Guide](https://docs.oracle.com/javase/tutorial/uiswing/)
- MySQL Documentation: [MySQL Reference Manual](https://dev.mysql.com/doc/)

---

Feel free to contribute to this project by submitting pull requests or reporting issues!


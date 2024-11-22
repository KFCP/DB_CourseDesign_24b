# DB_CourseDesign_24b
# Personnel Management System

## Overview

The **Personnel Management System** is a desktop-based Java application designed for managing employee records. It features a graphical user interface (GUI) to streamline operations such as adding employees, handling personnel changes, and managing employee credentials with enhanced security mechanisms. This application also integrates robust encryption methods to safeguard sensitive employee data.

## Features

1. **Main Window**:
   - Central hub for navigating to different functionalities of the application.

2. **Add Employee Window**:
   - Allows adding new employees to the database.
   - Supports secure password storage using salted SHA-256 hashing.

3. **Edit Employee Information**:
   - Enables viewing and updating employee records.
   - Includes password updates with re-encryption.

4. **Personnel Change Management**:
   - Handles department transfers and position changes.
   - Tracks changes with detailed logging.

5. **Login Functionality**:
   - Secure authentication using encrypted passwords.

6. **Encryption Module**:
   - Integrated module for hashing passwords with salts.
   - Validates input passwords against stored hashes.

7. **Database Integration**:
   - Utilizes MySQL for data persistence.
   - Manages tables such as `PERSON` and `PERSONNEL` for employees and change logs.

## Technology Stack

- **Programming Language**: Java
- **GUI Framework**: Swing
- **Database**: MySQL
- **Encryption**: Java Security (SHA-256, SecureRandom, Base64)

## Prerequisites

1. Java Development Kit (JDK) 8 or higher.
2. MySQL Database Server with the following configuration:
   - Database Name: `employee_management_db`
   - Tables:
     - `PERSON` for storing employee details.
     - `PERSONNEL` for logging personnel changes.
3. Required Java libraries for JDBC and Swing.

## How to Use

1. **Setup Database**:
   - Create the MySQL database `employee_management_db`.
   - Define necessary tables using the SQL schema provided.

2. **Run the Application**:
   - Compile and run `PersonnelManagementSystem.java` as the entry point.

3. **Navigate Features**:
   - Use the main window to access various functionalities, such as adding employees, viewing/modifying records, and managing personnel changes.

4. **Encryption Integration**:
   - Passwords are hashed with a salt before storage to ensure security.
   - Use the `EncryptionModule` class for additional encryption-related tasks.

## Classes and Responsibilities

### Main Classes:

- `PersonnelManagementSystem`: Launches the application.
- `MainWindow`: Provides access to core functionalities.

### Windows:

- `AddEmployeeWindow`: Adds new employees.
- `EditEmployeeWindow`: Queries and modifies employee records.
- `LoginWindow`: Handles user authentication.
- `PersonnelChangeWindow`: Processes department and position changes.

### Encryption:

- `EncryptionModule`: Implements SHA-256 password hashing and verification.

## Security Features

1. **Password Encryption**:
   - Implements salted hashing for secure password storage.
2. **Data Validation**:
   - Ensures input validation across all forms.
3. **Transaction Management**:
   - Uses SQL transactions for critical operations to maintain data integrity.

## Limitations and Future Enhancements

1. **Limitations**:
   - Lacks support for role-based access control.
   - Basic GUI design with limited customization options.

2. **Future Enhancements**:
   - Add multi-language support.
   - Introduce advanced analytics for personnel data.
   - Implement role-based access for administrators and users.

## Acknowledgements

This system was developed as a course project to explore practical applications of Java programming, GUI design, and database integration.

## License

This project is open-source and available under the Apache-2.0 License.

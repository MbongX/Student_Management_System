# Student Management System

A comprehensive Java-based educational institution management system that provides tools for managing students, teachers, courses, and administrative tasks.

## 📋 Table of Contents
- [Features](#features)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Project Structure](#project-structure)
- [Usage](#usage)
- [User Roles](#user-roles)
- [Database Configuration](#database-configuration)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)
- [Contributors](#contributors)

## ✨ Features

### Core Functionality
- Multi-user authentication system
- Role-based access control
- Course management
- Student enrollment tracking
- Attendance monitoring
- Assignment management
- Performance tracking
- Administrative tools

### User Management
- Secure login system with password encryption
- Session management
- Profile management
- Access level control

### Academic Features
- Course creation and modification
- Student enrollment system
- Attendance tracking
- Assignment submission and grading
- Performance monitoring
- Teacher-course assignment

## 💻 System Requirements

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- JDBC-compatible database
- Minimum 4GB RAM
- 500MB available disk space

### Development Tools
- Java IDE (IntelliJ IDEA recommended)
- Git for version control
- Maven or similar build tool

## 🚀 Installation

1. Clone the repository:
```bash
git clone https://github.com/MbongX/Student_Management_System.git
```

2. Navigate to the project directory:
```bash
cd Student_Management_System
```

3. Configure the database connection in `src/User/Admin/Database/Constants.java`

4. Build the project:
```bash
javac src/Main.java
```

5. Run the application:
```bash
java src/Main
```

## 📁 Project Structure

```
src/
├── User/               # User management and authentication
│   ├── Admin/         # Administrative functions
│   │   ├── Database/  # Database management
│   │   └── ...
│   ├── Person/        # Core user types
│   │   ├── Student/   # Student-specific functionality
│   │   ├── Teacher/   # Teacher-specific functionality
│   │   └── ...
│   └── ...
├── commands/          # System commands
├── data/             # Data management
├── util/             # Utility functions
└── Main.java         # Application entry point
```

## 🎯 Usage

### First-time Setup
1. Launch the application
2. Log in as administrator using default credentials:
   - Username: admin
   - Password: admin123
3. Change the default password
4. Begin setting up users and courses

### Common Tasks
- **Adding a New Student:**
  1. Log in as administrator
  2. Select "User Management"
  3. Choose "Add New Student"
  4. Fill in required information

- **Creating a Course:**
  1. Log in as administrator
  2. Select "Course Management"
  3. Choose "Create New Course"
  4. Enter course details and assign teacher

- **Taking Attendance:**
  1. Log in as teacher
  2. Select course
  3. Choose "Attendance Management"
  4. Mark present/absent students

## 👥 User Roles

### Administrator
- System configuration
- User management
- Course creation
- Teacher assignment
- System monitoring

### Teacher
- Course management
- Attendance tracking
- Assignment creation
- Grade management
- Student performance monitoring

### Student
- Course enrollment
- Assignment submission
- Grade viewing
- Attendance history
- Personal information management

## 🗄️ Database Configuration

### Connection Setup
1. Open `src/User/Admin/Database/Constants.java`
2. Configure the following parameters:
   - Database URL
   - Username
   - Password
   - Connection pool size

### Database Schema
The system requires the following main tables:
- Users
- Courses
- Assignments
- Attendance
- Grades

## 🔒 Security

### Password Management
- Passwords are encrypted using SHA-256
- Failed login attempts are tracked
- Session timeout after inactivity

### Access Control
- Role-based access control
- Session management
- Input validation
- SQL injection prevention

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch:
```bash
git checkout -b feature/YourFeature
```
3. Commit changes:
```bash
git commit -m 'Add YourFeature'
```
4. Push to the branch:
```bash
git push origin feature/YourFeature
```
5. Submit a pull request

## 👨‍💻 Contributors

This project exists thanks to all the people who contribute:

### Core Development Team

- [MbongX](https://github.com/MbongX)
  - Project Lead
  - Core System Architecture
  - Database Implementation

- [SergiuPavel2804](https://github.com/SergiuPavel2804)
  - User Authentication System
  - Security Implementation
  - Backend Development

- [Guillaume1060](https://github.com/Guillaume1060)
  - Course Management System
  - Frontend Development
  - User Interface Design

- [LeVlad](https://github.com/LeVlad)
  - Student Management Module
  - Testing and Quality Assurance
  - Documentation

We thank these dedicated developers for their valuable contributions to making this project a success!

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Thanks to all contributors who have helped shape this project
- Special thanks to the open-source community for their valuable tools and libraries

---

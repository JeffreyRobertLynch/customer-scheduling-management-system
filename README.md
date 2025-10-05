# Customer-Scheduling-Management-System

## Context
This student project was completed 4/14/2024, as part of Software 2: Advanced Java Concepts. It is preserved here to demonstrate full-stack development and foundational software engineering skills with historical accuracy.

## Awards

**Academic Excellence Award - Software 2: Advanced Java Concepts**  
Awarded by the Vice President of Evaluation Operations for outstanding performance in "Software 2: Advanced Java Concepts". The evaluator shared the following:

>“Overall, the student's project submission is excellent in that it is an example of quality in work, considering the provided requirements. The backend is informative and organized, while the frontend is easy to use and functional. Excellent job!”

![Award (PDF)](docs/Software_2_Java_Award.pdf)

---

## Features

This Java application allows users to maintain a robust database of customers and appointments — **with support for all time zones and 15 languages**

Additional features include:

- **CRUD Functionality**: Create, Read, Update, and Delete Appointments and Customers in a MySQL database  
- **Multi-language Support**: UI elements auto-translate based on system language settings  
- **Time Zone Conversion**: Automatically adjusts appointment times based on user’s system time zone  
- **Login Security**: Basic password-protected login screen to prevent unauthorized access  
- **Login Activity Log**: Records all log-in attempts with timestamp, user, language, time zone, and result (success/failure)  
- **User-Friendly UI**: Interactive and responsive JavaFX interface with confirmation messages, error handling, and alerts  
- **Dashboard & Reports**:
  - Filterable appointment table by week/month
  - Four distinct real-time reports for business insights:
    - **Contact Schedule**
    - **Appointments by Type and Month**
    - **Appointments by Contact and Month**
    - **Appointments by Customer and Month**

---

## Navigation Overview

### Login Screen
- Enter valid username/password (stored in the MySQL database)
- System logs each attempt and notifies users of upcoming appointments within 15 minutes

### Appointment Main Screen
- View filterable table of all appointments
- Add, update, or delete appointments
- Navigate to Customer Main, Reports, or Exit

### Customer Main Screen
- View and manage customer records
- Navigate to Appointment Main, Reports, or Exit

### Reports Main Screen
Generate four types of reports:
1. **Contact Schedule** – View appointments per contact  
2. **Appointments by Type and Month** – Understand customer trends  
3. **Appointments by Contact and Month** – Evaluate contact performance  
4. **Appointments by Customer and Month** – Track customer activity  

---

## Tech Stack

- **Java Version**: Oracle OpenJDK 17.0.8  
- **IDE**: IntelliJ IDEA Community Edition 2023.2  
- **UI Framework**: JavaFX 17.0.8  
- **Database**: MySQL  
- **Connector**: mysql-connector-java-8.0.26  

---

## Setup Instructions

> This application requires an active MySQL database to run. Without it, login authentication and functionality will not work.

1. Clone or download the repo  
2. Ensure your database schema matches the app's structure  
3. Run the application via IntelliJ or terminal  
4. Enter credentials on the Login screen to begin

---

## Author

**Jeffrey Robert Lynch**  [LinkedIn](https://www.linkedin.com/in/jeffrey-lynch-350930348)

---

## License

This project is for educational and demonstration purposes only. For commercial use, please contact the author.

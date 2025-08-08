Pure Healthy Eats - Full-Stack E-commerce Web Application
Welcome to Pure Healthy Eats, a complete full-stack e-commerce web application designed for a modern healthy food delivery service. This project provides a seamless online ordering experience for customers, from browsing a dynamic menu to making secure online payments.

Live Demo: [Your Render URL Here] (e.g., https://pure-healthy-eats-app.onrender.com)

Features
User Authentication: Secure user registration and login system using Spring Security. Passwords are encrypted using BCrypt.

Dynamic Product Catalog: Menu items are loaded dynamically from the database. Users can browse and filter products by category.

Product Details & Customization: Detailed view for each product and a dedicated page for customizing bowl-based meals.

Persistent Shopping Cart: User carts are saved to the database, allowing sessions to persist between logins. Users can add, update quantities, and remove items.

Order Management: A complete checkout flow that converts a user's cart into a permanent, non-editable order in the database.

Real Payment Gateway Integration: Secure payments are handled via the Razorpay Payment Gateway, supporting all major payment methods including UPI, Credit/Debit Cards, and Net Banking.

Subscription & Tracking Pages: Static pages for subscription plans and a temporary order tracking page.

Custom Error Page: A user-friendly 404 "Not Found" page.

Technology Stack
This project is built using a modern, robust technology stack, containerized with Docker, and deployed to the cloud.

Backend:

Java 21

Spring Boot 3

Spring Security (for authentication & authorization)

Spring Data JPA / Hibernate (for database interaction)

Maven (for dependency management)

Frontend:

HTML5

Tailwind CSS (for utility-first styling)

Thymeleaf (for server-side template rendering)

JavaScript (for client-side interactivity)

Database:

PostgreSQL (for the live deployment on Render)

MySQL (for local development)

Payment Gateway:

Razorpay

Deployment:

Docker (for containerization)

Render (for cloud hosting and CI/CD)

Git & GitHub (for version control)

Project Structure
The backend is organized into a standard, feature-based package structure for maintainability and scalability.

└── com.purehealthyeats
    ├── auth            # User authentication (login/signup)
    ├── cart            # Shopping cart logic
    ├── config          # Spring Security and other configurations
    ├── controller      # General page controllers
    ├── order           # Order creation and management
    ├── payment         # Payment gateway integration
    ├── product         # Product/menu logic
    ├── security        # Custom security components
    └── user            # User entity and repository

Local Setup and Installation
To run this project on your local machine, please follow these steps.

Prerequisites
Java JDK 21 or later

Apache Maven

MySQL Workbench (or any MySQL server)

A code editor (like IntelliJ IDEA or VS Code)

Git

1. Clone the Repository
git clone [Your GitHub Repository URL]
cd pure-healthy-eats

2. Configure the Database
Open MySQL Workbench and create a new database schema. You can name it purehealthyeats_db.

Open the src/main/resources/application.properties file.

Update the following lines with your MySQL username and password, and the database name you just created:

spring.datasource.url=jdbc:mysql://localhost:3306/purehealthyeats_db?createDatabaseIfNotExist=true
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

3. Configure API Keys
Sign up for a free account at Razorpay.

In the Razorpay dashboard, switch to Test Mode.

Go to Settings -> API Keys and generate a new Test Key.

Copy the Key ID and Key Secret.

In the application.properties file, add the following lines:

razorpay.key.id=YOUR_TEST_KEY_ID
razorpay.key.secret=YOUR_TEST_KEY_SECRET

4. Run the Application
Open your terminal in the root project directory (where pom.xml is located).

Run the application using the Maven wrapper:

./mvnw spring-boot:run

The application will start on http://localhost:8080. The first time it runs, Spring Boot will automatically create all the necessary tables in your database.

5. Populate the Database
Once the application is running, open MySQL Workbench.

Run the INSERT commands for the products to populate your menu. You can find these commands in the project files.

Your application is now fully functional on your local machine.
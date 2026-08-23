# Deepa Mart 🛒

Deepa Mart is a simple e-commerce web application developed using Java.

## Features

- User Signup
- User Login
- Product Listing
- Product Images
- Add to Cart
- Remove from Cart
- Checkout
- Place Order
- Order Success Page
- My Orders / Order History

## Technologies Used

- Java
- JSP
- Servlet
- MySQL
- HTML
- CSS
- JavaScript
- Maven
- Apache Tomcat

## Database

Database Name: `deepa_mart`

Main tables:

- users
- products
- orders
- order_items

## Project Flow

Signup / Login  
↓  
View Products  
↓  
Add Products to Cart  
↓  
Checkout  
↓  
Select Payment Method  
↓  
Place Order  
↓  
Order Success  
↓  
View My Orders

## How to Run

1. Create the MySQL database using the SQL file.
2. Update database username and password in `DBConnection.java`.
3. Run:

```bash
mvn clean package
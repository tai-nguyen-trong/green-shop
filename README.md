# GreenShop

Green Shop is an online plant store specializing in selling ornamental plants. The website offers a large variety of beautiful and high-quality plants at reasonable prices. Customers can easily browse and purchase different types of plants through the website's intuitive and user-friendly interface.


## Features

Displaying product listings and shopping cart.

Adding products to the shopping cart and proceeding to checkout.

Managing information of placed orders.

Admin management: products, users, orders, etc.


## Technical

Backend: [Springboot](https://spring.io/projects/spring-boot).

Frontend: [Thymeleaf](https://www.thymeleaf.org/), [AngularJS](https://angularjs.org/).

Database: Microsoft SQL Server.


## Installation

```git
git clone https://github.com/tai-nguyen-trong/green-shop.git
```

Afterwards, run the project and open the browser, then access the address http://localhost:8080/ to experience the service.

## Notes

You need to adjust some configuration settings to match your computer's local environment.

In the `application.properties` file, you can find it at the path `green-shop\src\main\resources`.

```properties
spring.datasource.url=jdbc:sqlserver://localhost;database=greenshop3
spring.datasource.username=sa
spring.datasource.password=12345678
```

You need to change the name of the database name, username account and the password to match your computer's local environment.

Here, My database account information:

  - Database name `greenshop3`
  
  - Username account `sa`
  
  - Password `12345678`


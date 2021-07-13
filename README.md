# -Task_University

This app is a tutorial Foxminded project and contains the following tasks:

[Task 9] - Decompose university DONE

Analyze and decompose University (create UML class diagram for application).
You should make a research and describe university structure.
The main feature of the application is Class Timetable for students and teachers. 
Students or teachers can get their timetable for a day or for a month.
Add png image to the separate GitLab project. 
After this task, all other tasks will be in this repository so give it a meaningful name.

[Task 10] - Models DONE

Create a JAVA project based on the University UML class diagram from the previous task. 
This task requires only models implementation but additional requirements could be 
provided by your mentor.

[Task 11] - DAO layer IN PROGRESS 

Create Spring JDBC based DAO for your application. 

[Task 12] - Service Layer CLOSE

Create a service layer and implement business logic 
(add/remove entities to other entities and save them to DB, etc). 
A mentor can provide additional business rules.
You should use Spring IoC.

[Task 13] - Exceptions and Logging CLOSE

Add custom exceptions and logging. Use slf4j + logback.

[Task 14] - User Interface-1 CLOSE

Create status pages (read data from DB - show it in HTML). 
Use Spring MVC and Thymeleaf, Bootstrap. 

[Task 15] - User Interface-2 CLOSE

Create full CRUD pages for models that were used in the previous task.

[Task 16] - Data Source CLOSE

Create DataSource in web-project configuration files. 
Switch DAO layer to lookup DataSource by JNDI name.

[Task 17] - Hibernate CLOSE

Rewrite the DAO layer. Use Hibernate instead of Spring JDBC.
The Hibernate should be used as a provider that implements JPA specification, 
the Service layer should use and depend on the JPA interfaces, 
not the Hibernate ones.

[Task 18] - Spring Boot CLOSE

Convert application to Spring Boot. 

[Task 19] - Spring Data JPA CLOSE

Rewrite the DAO layer. Use Spring Data JPA instead of Hibernate.

[Task 20] - Validation CLOSE

Add validation to your models. It could be name validation, time validation, number of students 
in groups, etc. Ask your mentor for validations that should be implemented in your code.

[Task 21] - REST CLOSE

Add REST Endpoints to your project. All UI functionality should be available in the REST endpoints.

[Task 22] - Swagger CLOSE

Add Swagger documentation to your project. You can use 2 or 3 versions or ask your mentor.

[Task 23] - Spring Boot Tests CLOSE

Write Integration(or/and System) tests using Spring Boot Test, H2 DB (it is possible to use Database Rider)

![Image](src\main\resources\static\img\ImagePoseidon.png "Poseidon")
# Poseidon Capital Solutions

Poseidon is an application for generating financial transactions.

## Getting Started

These instructions allow you to run a copy of the project locally on your workstation for development and testing. Refer to the "Deployment" section for the steps to follow to deploy the project in production.

### Prerequisites

To run the Poseidon project locally, you must first install :

```
1. Install Java :
https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html
The JAVA_HOME variable must be set.

2. Install MysSql :
https://downloads.mysql.com/archives/get/p/25/file/mysql-installer-community-8.0.27.1.msi
```

### Installation

Here are the steps to follow to have a working development and test environment :


```
1. Clone the Git repository to your local machine.
   * git clone https://github.com/MichelCat/JavaDA_PROJECT7_RESTAPI.git

2. Place yourself in the directory.
   * cd JavaDA_PROJECT7_RESTAPI/Poseiden-skeleton

3. Configure MySql database connection information
   * spring.datasource.url=jdbc:mysql://localhost:3306/PoseidonDev
   * spring.datasource.username -> Username for the MySQL database
   * spring.datasource.password -> Password for the MySQL database
Development environment in file "src/main/resources/application-dev.properties".
Test environment in file "src/main/resources/application-test.properties".
Production environment in file "src/main/resources/application-prod.properties".

4. Run Maven commands to generate Poseidon packages.
   * mvn clean install
   * mvn package

4. Run Maven commands for Surefire and Jacoco reports in directory "target/site".
   * mvn verify
   * mvn surefire-report:report
   * mvn site

5. Run Maven commands for Javadod in directory "target/site/apidocs".
   * mvn javadoc:javadoc
   
6. Configure MySql
Development environment
   * Database configuration script : "src\main\resources\schema-dev.sql"
   * Script to add data : "src\main\resources\data.sql"
Test environment
   * Database configuration script : "src\test\ressources\schema-test.sql"
Production environment
```

## Deployment

Here are the steps to deploy to production :

```
1. Run the Maven command to run the application.
   * mvn spring-boot:run

2. Once the application is running, it can be accessed by default at http://localhost:8080/app/login

```

## Use

**User**

```
1. Register
    * Click on the Register button. Register with your email address.
    * Validate the activation email received in the mailbox.
    * Connect with his email.

2. Once connected.
    * You have the choice of several tabs.
```

**Administrator**

```
Two accounts created.

|    E-mail       | Password |   Definition   |
|-----------------|----------|----------------|
| admin@gmail.com |   test   | Administrateur |
| user@gmail.com  |   test   | User           |
```

## Technologies

* [Java JDK 17.0.8](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
* [Maven 3.9.4](https://maven.apache.org/download.cgi)
* [MysSql 8.0.27](https://dev.mysql.com/downloads/installer/)
* [SpringBoot 3.1.0](https://spring.io/projects/spring-boot)
* Mapstruct 1.5.5.Final
* Lombok 1.18.28
* Spring Security 6
* Spring Data JPA
* Hibernate
* Thymeleaf 3.1.0
* [Bootstrap  v5.3.1](https://getbootstrap.com/)
* [Bootstrap-icons](https://cdn.jsdelivr.net/npm/bootstrap-icons@latest/)
* [jQuery v3.6.4](https://jquery.com/download/)
* HTML5/CSS3
* Jacoco
* SureFire
* Slf4j
* Javadoc
* JUnit 5

## Auteurs

* **OpenClassrooms student**

# Pet Project Multi-Service App

A service-oriented web application showcasing multiple independent services. This project serves as a demonstration of various skills in solving different problems (integrating various services, working with security, and more).

## Features

- **Weather Service**: Fetches weather data using the Open-Meteo API.
- **End-to-End Encrypted Chat**: Implements AES and RSA encryption for secure messaging.
- **Currency Converter**: Uses the open REST API provided by RBC for currency conversion.
- **Interpreted Calculator**: A simple, functional interpreted calculator.

This project aims to demonstrate the integration of several key services in a single application, highlighting various technical skills in backend, frontend, and security.

## Technologies

- **Backend**:
    - **Spring Boot** (Security, MVC, Data)
    - **JWT** (JSON Web Tokens for authentication)
    - **Hibernate** (ORM for database interaction)
    - **JDBC** (Database connectivity)
    - **Log4j** (Logging)
    - **PostgreSQL** (Database for persistent storage)
    - **Simple Message Broker** (For chat message communication)

- **Frontend**:
    - **Vue.js** (For dynamic rendering of pages)
    - **SCSS** (For styling the frontend)

- **Third-party Integrations**:
    - **Open-Meteo API** (Weather data)
    - **RBC Currency API** (Currency conversion)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- Docker (optional, for containerized deployment)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/defix-dev/pet-project-multi-service-app.git
   cd pet-project-multi-service-app
   ```

2. **Build the project** (using Maven):

   ```bash
   mvn clean package
   ```

3. **Run the application**:

    - Using the `.jar` file:

      ```bash
      java -jar target/multi-service-build.jar
      ```

    - Or using Docker Compose (navigate to the `ms_docker` folder first):

      ```bash
      docker compose up
      ```

### Accessing the Application

Once the application is running, you can access it via the following endpoints:

- **Home Page**: [http://localhost:8080/](http://localhost:8080/)
- **Weather**: Accessible through the weather service API.
- **Chat**: End-to-end encrypted messaging system.
- **Currency Converter**: Access via the currency conversion API.

## Testing

To run the tests, use the following command:

```bash
mvn clean test
```

This will execute all the tests in the project.

## Project Structure

- **Weather Service**: Fetches real-time weather data using Open-Meteo.
- **Chat Service**: Implements secure messaging with AES and RSA encryption.
- **Currency Converter Service**: Fetches and converts currency data from the RBC API.
- **Calculator Service**: A simple interpreted calculator service.

## Special Features

- **End-to-End Encryption**: The chat service uses AES and RSA encryption to ensure that user messages are securely transmitted.
- **Real-time Data**: Weather and currency data are fetched in real-time from external APIs.
- **Spring Boot Security**: The project leverages Spring Security for handling authentication and authorization using JWTs.

## License

No license.
# banking-service

Microservice for banking entities

## Features

- Create, update, delete, and retrieve banking entities
- RESTful API with Spring Boot
- In-memory H2 database for development and testing
- Swagger UI for API documentation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/banking-service.git
   
2. Navigate to the project directory:
   ```sh
   cd banking-service
3. Build the project:
	```sh
	mvn clean install

### Running the Aplication

1. Run the application:
   ```sh
   mvn spring-boot:run
2. Access the Swagger UI for API documentation:
	   ```
   http://localhost:8080/swagger-ui.html

### API Endpoints

#### Bank

- GET /api/banks/getAllBanks - Retrieve all banks
- GET /api/banks/findBankById/{id} - Retrieve a bank by ID
- GET /api/banks/findBankByName/{name} - Retrieve a bank by name
- POST /api/banks/createBank - Create a new bank
- PUT /api/banks/updateBank/{id} - Update a bank
- DELETE /api/banks/deleteBank/{id} - Delete a bank


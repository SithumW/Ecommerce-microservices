# E-Commerce Microservices

A Spring Boot microservices-based e-commerce application with independent services for order management, product catalog, and user management.

## Project Structure

```
├── order/           - Order management service
├── product/         - Product catalog service
├── user/            - User management service
└── Additional/      - Additional resources and configurations
```

## Services

### Order Service
Handles order processing and management.
- **Port**: Configured in application.properties
- **Build**: `mvn clean install`

### Product Service
Manages product catalog and inventory.
- **Port**: Configured in application.properties
- **Build**: `mvn clean install`

### User Service
Handles user management and authentication.
- **Port**: Configured in application.properties
- **Build**: `mvn clean install`

## Prerequisites

- Java 8 or higher
- Maven 3.6+
- Docker & Docker Compose (for containerized deployment)
- MySQL/PostgreSQL (database)

## Getting Started

### Build All Services

```bash
cd order && mvn clean install
cd ../product && mvn clean install
cd ../user && mvn clean install
```

### Run with Docker Compose

From the root directory:

```bash
docker-compose up
```

### Run Individual Service

Navigate to the service directory and run:

```bash
./mvnw spring-boot:run
```

## Configuration

Each service has its own `application.properties` file located in `src/main/resources/`. Update the database connection details, port numbers, and other configurations as needed.

## API Documentation

Once the services are running, API documentation can be accessed through:
- Swagger/Springfox (if configured)
- Check individual service README files for endpoint details

## Testing

Run tests for a specific service:

```bash
mvn test
```

## Contributing

1. Create a feature branch
2. Make your changes
3. Commit with clear messages
4. Push to the repository
5. Create a pull request

## License

This project is licensed under the MIT License.

## Support

For issues or questions, please create an issue in the repository.

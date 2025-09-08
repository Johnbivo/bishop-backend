## E‑Commerce Bishop (Microservices)

### Project status
- ⚠️ This project is a work in progress and is **not completed**. Expect breaking changes, missing features, and evolving APIs.

### Overview
A microservices-based e‑commerce backend built with Spring Boot and gRPC. Each service is independently deployable and communicates via REST and/or gRPC where applicable.

### Architecture
- **User Service** (`user-service`)
  - Purpose: User accounts, profiles, and addresses
  - Tech: Spring Boot, JPA, MySQL, gRPC, MapStruct, Lombok, Springdoc
  - Ports: REST `4001`
- **Billing Service** (`billing-service`)
  - Purpose: Billing domain (invoices, payments metadata)
  - Tech: Spring Boot, JPA, MySQL, gRPC, MapStruct, Lombok, Springdoc
  - Ports: REST `4002`, gRPC `8001`
- **Product Service** (`product-service`)
  - Purpose: Product catalog, categories, inventory
  - Tech: Spring Boot, MongoDB, gRPC, MapStruct, Lombok, Springdoc
  - Ports: REST `4003`, gRPC `8002`
- **Order Service** (`order-service`)
  - Purpose: Order creation, line items, order status; integrates with Product via proto
  - Tech: Spring Boot, JPA, MySQL, Redis, gRPC, MapStruct, Lombok, Springdoc
  - Ports: REST `4004`, gRPC `8002`
- **Integration Tests** (`integration-testing`)
  - Purpose: Cross-service tests (e.g., `UserTests`, `UserAddressTests`)

### Technology stack
- Java 21, Spring Boot 3.5.x
- Data: MySQL (Users, Billing, Orders), MongoDB (Products), Redis (Orders cache)
- API: REST (Spring Web), OpenAPI UI (Springdoc), gRPC (net.devh Spring Boot starter)
- Build: Maven; Codegen: Protobuf + gRPC
- Utilities: Lombok, MapStruct
- Containerization: Dockerfiles per service

### Local development
#### Prerequisites
- JDK 21
- Maven 3.9+
- Local databases as needed (MySQL, MongoDB, Redis)
  - MySQL databases for `user-service`, `billing-service`, and `order-service`
  - MongoDB for `product-service`
  - Redis for `order-service`

#### Run services
In separate terminals from the repo root, run the desired service directory:

```bash
cd user-service && mvn spring-boot:run
```
```bash
cd billing-service && mvn spring-boot:run
```
```bash
cd product-service && mvn spring-boot:run
```
```bash
cd order-service && mvn spring-boot:run
```

Services will start on the ports listed above. Adjust `server.port` in each service `application.yml` if needed.

#### gRPC
- gRPC servers run on the configured `grpc.server.port` in each service.
- Protobuf definitions are under each service at `src/main/proto`.
- Code generation is managed by the Maven `protobuf-maven-plugin` during build.

#### API documentation
- Springdoc OpenAPI UI is enabled per service. Once a service is running, visit:
  - `http://localhost:<rest-port>/swagger-ui/index.html`

### Testing
- Run integration tests from the `integration-testing` module:

```bash
cd integration-testing && mvn test
```

### Docker
Each service has a `Dockerfile`. Example build and run:

```bash
cd user-service
mvn -DskipTests package
docker build -t user-service:local .
docker run --rm -p 4001:4001 user-service:local
```

Repeat for other services, mapping ports accordingly.

### Notes & assumptions
- Ports and dependencies are sourced from each service `application.yml` and `pom.xml`.
- Some features, integrations, and data models are incomplete or subject to change.

### Contributing
- Given the WIP nature, please open an issue before larger changes.
- Use feature branches and small, focused pull requests.

### License
- Add your license of choice (e.g., MIT/Apache-2.0) at the repo root as `LICENSE`. 

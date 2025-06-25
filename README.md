# 🐾 PetStore

PetStore is a simple Spring Boot application that simulates a pet shop system. It allows you to:

- Create and list users (owners)
- Create and list pets (cats and dogs)
- Simulate users buying pets based on budget and availability
- Track the history of purchases
- Interact with the system via **REST API** or **GraphQL UI**

---

## 🚀 Getting Started

### 📦 Prerequisites

- Java 17+
- Maven
- PostgreSQL
- Postman or GraphQL client (like [GraphiQL](https://www.electronjs.org/apps/graphiql) or the [Spring Boot GraphQL UI](http://localhost:8080/graphiql))

---

## ⚙️ Configuration

1. Open the `application.properties` file and configure your PostgreSQL credentials:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.url=jdbc:postgresql://localhost:5432/petstore_db
```
2. Create a database table in pgAdmin based on the name of the table in your application properties
3. Now you can run the app and test it via Postamn or the GraphQlUI on http://localhost:8080/graphiql?path=/graphql

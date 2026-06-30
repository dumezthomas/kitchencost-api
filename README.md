# KitchenCost API

> A backend REST API for restaurant cost management and profitability analysis.

KitchenCost API is a backend REST API designed to help restaurants manage recipes, monitor food costs, and analyze the
financial impact of ingredient price changes.

The project was developed in Java using Jakarta EE and follows a layered architecture (Resources → Services → DAOs).

Its main feature is the automatic analysis of how ingredient price changes affect recipe costs, margins, and
profitability.

---

## Features

- JWT Authentication
- Role-based authorization (CHEF / COOK)
- Public menu consultation
- Recipe consultation
- Financial reports
- Food cost calculation
- Margin calculation
- Profitability analysis
- Ingredient price management
- Automatic impact analysis after ingredient price updates

---

## Main Showcase Feature

The centerpiece of the project is:

```http
PATCH /ingredients/{id}/price
```

When an ingredient price is updated, the API automatically:

- updates the ingredient price;
- finds every affected menu item;
- recalculates recipe costs;
- recalculates margins;
- recalculates food cost percentage;
- determines whether each menu item remains profitable;
- returns a complete before/after comparison.

Example response:

```json
{
  "ingredientName": "Flour",
  "oldUnitPrice": 0.002,
  "newUnitPrice": 0.013,
  "priceChangePercentage": 550,
  "affectedMenuItems": [
    {
      "menuItemName": "Classic Pancakes",
      "oldRecipeCost": 0.40,
      "newRecipeCost": 2.60,
      "oldMargin": 8.10,
      "newMargin": 5.90,
      "oldFoodCostPercentage": 4.71,
      "newFoodCostPercentage": 30.59,
      "profitableBefore": true,
      "profitableAfter": false
    }
  ]
}
```

---

## Technologies

- Java 25
- Jakarta EE
- JAX-RS
- CDI
- Hibernate / JPA
- PostgreSQL
- Apache Tomcat 10
- JWT (JJWT)
- BCrypt
- OpenAPI / Swagger

---

## Architecture

The project follows a classic layered architecture to clearly separate HTTP endpoints, business logic and data access.

```
Resources
      │
      ▼
Services
      │
      ▼
DAOs
      │
      ▼
Database
```

### Resources

Expose REST endpoints and validate incoming requests.

### Services

Contain the business logic and orchestrate application workflows.

### DAOs

Handle database access using JPA.

---

## Persistence Strategy

Unlike Spring applications, this project intentionally uses manual `EntityManager` management.

Each DAO creates and closes its own persistence context.

```java
try(var em = emf.createEntityManager()){
        ...
        }
```

Transactions are managed manually inside DAO update methods.

This approach was chosen to match the architectural constraints of the course while demonstrating explicit control over
the persistence lifecycle.

---

## Security

Authentication is based on stateless JWT tokens.

Two application roles are available:

- CHEF
- COOK

Role-based authorization protects sensitive endpoints.

Examples:

- **CHEF** can update ingredient prices and access financial reports.
- **COOK** can consult recipes.
- **Public users** can browse the menu.

---

## API Endpoints

### Authentication

| Method | Endpoint      |
|--------|---------------|
| POST   | `/auth/login` |

### Menu

| Method | Endpoint     |
|--------|--------------|
| GET    | `/menu`      |
| GET    | `/menu/{id}` |

### Recipes

| Method | Endpoint        |
|--------|-----------------|
| GET    | `/recipes`      |
| GET    | `/recipes/{id}` |

### Financial Reports

| Method | Endpoint                       |
|--------|--------------------------------|
| GET    | `/financial-reports/menu`      |
| GET    | `/financial-reports/menu/{id}` |

### Ingredients

| Method | Endpoint                  |
|--------|---------------------------|
| PATCH  | `/ingredients/{id}/price` |

---

## Project Structure

```
src
├── annotations
├── config
├── daos
├── enums
├── exceptions
├── filters
├── models
├── pojos
├── resources
├── services
└── utils
```

---

## Getting Started

### Requirements

- Java 25
- Maven
- PostgreSQL
- Apache Tomcat 10

### Installation

Clone the repository:

```bash
git clone https://github.com/yourusername/KitchenCost-API.git
```

Configure your PostgreSQL database and update the persistence configuration with your connection settings.

Build the project:

```bash
mvn clean package
```

Deploy the generated WAR file to Apache Tomcat.

---

## Future Improvements

Possible future improvements include:

- **Optimize the ingredient price impact endpoint** by returning only the IDs of affected menu items instead of complete
  financial reports. This would significantly reduce the response payload, especially when many menu items are impacted.

- **Retrieve detailed financial reports through dedicated endpoints** once the client has received the list of affected
  menu item IDs. This approach would reduce network traffic while allowing additional information to be requested only
  when needed.

---

## Author

Thomas Dumez - Java Developer
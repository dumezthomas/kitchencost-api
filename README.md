# KitchenCost API

A backend REST API designed to help restaurants manage recipes, monitor food costs, and analyze the financial impact of
ingredient price changes.

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

- Java
- Jakarta EE
- JAX-RS
- CDI
- Hibernate / JPA
- Tomcat
- MySQL
- JWT
- BCrypt
- OpenAPI / Swagger

---

## Architecture

The project follows a classic layered architecture.

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

Contain the business logic.

### DAOs

Handle database access using JPA.

---

## Persistence Strategy

Unlike Spring applications, this project intentionally uses manual `EntityManager` management.

Each DAO creates and closes its own persistence context:

```java
try(var em = emf.createEntityManager()){
        ...
        }
```

Transactions are handled manually inside DAO update methods.

This approach was chosen to match the architectural constraints of the course and demonstrates explicit control over the
persistence lifecycle.

---

## Security

Authentication is based on JWT.

Two roles are available:

- CHEF
- COOK

Protected endpoints use role-based authorization.

Example:

- CHEF can update ingredient prices.
- COOK can consult recipes.
- Public users can browse the menu.

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
- MySQL
- Apache Tomcat 10

### Installation

```bash
git clone https://github.com/yourusername/KitchenCost-API.git
```

Configure the database connection in the persistence configuration.

Build the project:

```bash
mvn clean package
```

Deploy the generated WAR to Tomcat.

---

## Future Improvements

Possible future improvements include:

- **Optimize the ingredient price impact endpoint** by returning only the IDs of affected menu items instead of complete
  financial reports. This would significantly reduce the response payload, especially when many menu items are impacted.

- **Retrieve detailed financial reports on demand.** After receiving the list of affected menu item IDs, the client
  could request the updated financial report for a specific menu item through a dedicated endpoint. This approach would
  improve scalability while keeping the API flexible.

---

## Author

Thomas Dumez - Java Developer
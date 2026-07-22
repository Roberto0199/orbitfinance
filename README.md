# OrbitFinance

> Real-time transaction engine with geolocated security alerts — built with Java & Spring Boot

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen?style=flat-square&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.9-red?style=flat-square&logo=apachemaven)
![MaxMind](https://img.shields.io/badge/MaxMind-GeoLite2-yellow?style=flat-square)

---

## About

OrbitFinance is a robust backend system that simulates the operational core of a Fintech or digital banking platform. The system manages secure money movement between accounts and asynchronously processes a business rules engine to emit security alerts based on transaction amount and real geographic location.

---

## Features

- ✅ **Account management** with UUID-based identification
- ✅ **ACID-compliant transaction engine** with balance validation
- ✅ **Asynchronous alerts engine** running on a separate thread
- ✅ **Real geolocation** via MaxMind GeoLite2 database
- ✅ **Risk classification** (NORMAL vs CRITICAL) based on transaction amount
- ✅ **Immutable audit system** — nothing is deleted, everything is tracked
- ✅ **Global exception handler** with structured JSON error responses
- ✅ **Persistent storage** with MySQL

---

## Tech Stack

| Technology | Purpose |
|---|---|
| **Java 21** | Core language |
| **Spring Boot 3.2.x** | Backend framework |
| **Spring Data JPA / Hibernate** | Database ORM |
| **MySQL** | Persistent relational database |
| **MaxMind GeoLite2** | IP geolocation |
| **Lombok** | Boilerplate reduction |
| **Maven** | Dependency management |

---

## Architecture

```text
com/orbitfinance/core/
├── account/         → Account entity, repository, service, controller
├── transaction/     → Transaction engine with ACID guarantees
├── alert/           → Async alerts engine + GeoIP service
├── audit/           → Immutable audit trail
└── exception/       → Custom exceptions + global error handler
```
## How It Works 

```text
[Client]
│
│  POST /api/transactions/transfer
▼
[TransactionController]
│
▼
[TransactionService] ── @Transactional ──► Debit source, Credit target
│
├── Returns success response to client (milliseconds)
│
└── Fires @Async event
│
▼
[AlertsEngineService] (background thread)
│
├── Resolves IP → Real City/Country (MaxMind GeoLite2)
├── Classifies risk: NORMAL or CRITICAL
└── Saves immutable alert to database
```

## Getting Started

### Prerequisites
- Java 21
- MySQL 8.0
- Maven 3.9+
- MaxMind GeoLite2-City database ([free signup](https://www.maxmind.com/en/geolite2/signup))

### Installation

1. **Clone the repository**
```bash
  git clone https://github.com/Roberto0199/orbitfinance.git
cd orbitfinance
```

2. **Create the database**
```sql
CREATE DATABASE orbitfinance_db;
```

3. **Configure your environment**
```bash
  cp src/main/resources/application.properties.example src/main/resources/application.properties
```
Then edit `application.properties` and fill in your MySQL credentials.

4. **Add GeoLite2 database**

Download `GeoLite2-City.mmdb` from MaxMind and place it in:

5. **Run the application**
```bash
  mvn spring-boot:run
```

## API Endpoints

### Accounts
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/accounts` | Create a new account |
| GET | `/api/accounts` | Get all accounts |
| GET | `/api/accounts/{accountNumber}` | Get account by number |

### Transactions
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/transactions/transfer` | Execute a transfer |
| GET | `/api/transactions` | Get all transactions |

### Audit
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/audit/{accountNumber}` | Get full audit trail for an account |

## Error Handling

All errors return a consistent JSON structure:

```json
{
    "status": 422,
    "error": "Insufficient Funds",
    "message": "Account ORB-001 has insufficient funds. Available: $800.00, Requested: $99999.00",
    "timestamp": "2026-07-05T20:30:45"
}
```

## 👨Author

**Roberto** — Java Backend Developer
- GitHub: [@Roberto0199](https://github.com/Roberto0199)
# Mesto Tests (UI + API + DB) — Selenide + RestAssured + JDBC + JUnit 5 + Allure


Full-stack test automation pet project covering:

- **UI**: Selenide
- **API**: Rest Assured
- **Test framework**: JUnit 5
- **Reporting**: Allure
- **CI**: GitHub Actions (Remote Selenium)


---

## Tech Stack

- Java 17+
- Maven
- JUnit 5
- Selenide
- Rest Assured
- Allure (JUnit5 + Selenide + RestAssured)
- GitHub Actions 
- Docker (Selenium / PostgreSQL in CI)

---

## Project Structure

`src/test/java/com/company/`

- `mesto/`
  - `api/`
    - `clients/` — API clients (`AuthClient`, `CardsClient`, `UsersClient`)
    - `config/` — API config (`ApiConfig`)
    - `data/` — API test data (`ApiTestData`)
    - `models/` — POJOs (`Card`, `UserMe`, `UpdateProfileRequest`, `CreateCardRequest`, `ApiResponse`)
    - `specs/` — RestAssured specs (`ApiSpecs`)
    - `tests/` — API tests (`ApiTestBase`, `ApiTests`, `ApiNegativeTests`)
    - `utils/` — API utilities
  - `ui/`
    - `pages/` — Page Objects (`LoginPage`, `HomePage`, `RegistrationPage`)
    - `components/` — UI components (`PostCardComponent`)
    - `config/` — UI configuration (`UiConfig`)
    - `data/` — UI test data (`UiTestData`)
    - `tests/` — UI tests (`LoginTests`, `AuthorizedTests`, `RegistrationTests`)
    - `utils/` — utilities (`AllureAttachments`, `Html5Validation`)
  - `config/` — shared config (`TestConfig`)
  - `testdata/` — shared test data (`CommonTestData`)

- `db/`
  - `DbClient` — JDBC helper
  - `DbConfig` — DB configuration (System properties / ENV)
  - `tests/` — DB tests (`DbTests`)

---

## Configuration

The project supports overriding settings via **System properties** (`-D...`) or **Environment variables** (ENV).

### Base URL

Priority:
1. System property `baseUrl`
2. ENV `BASE_URL`
3. Default `https://qa-mesto.praktikum-services.ru`

Example:
```bash
mvn clean test -DbaseUrl=https://qa-mesto.praktikum-services.ru
```

### Credentials

Priority:
1. System properties `-DTEST_EMAIL`, `-DTEST_PASSWORD`
2. ENV `TEST_EMAIL`, `TEST_PASSWORD`

Examples:

**PowerShell**
```powershell
mvn clean test "-DTEST_EMAIL=mail@example.com" "-DTEST_PASSWORD=12345"
```

**bash**
```bash
mvn clean test -DTEST_EMAIL=mail@example.com -DTEST_PASSWORD=12345
```

---

## Run Tests

### Run all tests
```bash
mvn clean test
```

### Run only API tests (by tag)
```powershell
mvn clean test "-Dgroups=api"
```

### Run only UI tests (by tag)
```powershell
mvn clean test "-Dgroups=ui"
```

### Run only DB tests (by tag)
```powershell
mvn clean test "-Dgroups=db"
```

> Tags are defined using `@Tag("ui")` / `@Tag("api")` / `@Tag("db")` in test classes.

---

## Run UI Tests with Remote Selenium (CI / Docker)

Example:
```powershell
mvn clean test "-Dgroups=ui" "-Dselenide.remote=http://localhost:4444/wd/hub" "-Dselenide.headless=true"
```

---

## DB Tests (JDBC + PostgreSQL)

**Self-contained database tests demonstrating JDBC usage in test automation:**

- Connection via DriverManager
- `INSERT` / `SELECT` / `DELETE`
- `EXISTS` / `COUNT` / `ORDER BY` / `LIMIT`
- Test data cleanup via `finally`
- Configurable connection via System properties / ENV

**Start Postgres locally:**
```powershell
docker compose up -d
```

**DB configuration priority:**

System properties:
```powershell
-Ddb.url=
-Ddb.user=
-Ddb.pass=
```

Environment variables:
```powershell
DB_URL
DB_USER
DB_PASS
```

Defaults:
```powershell
jdbc:postgresql://localhost:5432/mesto  
user: mesto  
pass: mesto
```

---

## Allure Report

### Generate the report
```bash
mvn allure:report
```
### Output folders
- Raw results: target/allure-results/ 
- HTML report: target/site/allure-maven-plugin/ 

### Open the report locally 
Open this file in your browser: 
- target/site/allure-maven-plugin/index.html 

(Optionally, you can use Allure CLI if installed separately.)

---

## GitHub Actions (CI)

UI workflow:
- Starts selenium/standalone-chrome container
- Runs UI tests (remote + headless)
- Generates Allure report
- Uploads artifacts:
  - target/surefire-reports/
  - target/allure-results/
  - target/site/allure-maven-plugin/

API workflow:
- Runs REST Assured tests
- Generates Allure report
- Uploads artifacts

DB workflow:
- Located at `.github/workflows/db-tests.yml`
- Runs manually (workflow_dispatch)
- Starts PostgreSQL container
- Creates test table automatically
- Executes only @Tag("db") tests
- Generates Allure report
- Uploads artifacts:
  - db-allure-report
  - db-surefire-reports

---
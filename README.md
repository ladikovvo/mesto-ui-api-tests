# Mesto Tests (UI + API) — Selenide + RestAssured + JUnit 5 + Allure

A pet project with automated tests for the **Mesto** service.

- **UI**: Selenide
- **API**: Rest Assured
- **Test framework**: JUnit 5
- **Reporting**: Allure
- **CI**: GitHub Actions (Remote Selenium)

---

## Tech Stack

- Java 24
- Maven
- JUnit 5
- Selenide
- Rest Assured
- Allure (JUnit5 + Selenide + RestAssured)
- GitHub Actions (Remote Selenium)

---

## Project Structure

`src/test/java/com/company/mesto/`

- `api/`
    - `clients/` — API clients (`AuthClient`, `CardsClient`, `UsersClient`)
    - `config/` — API config (`ApiConfig`)
    - `data/` — API test data (optional, `ApiTestData`)
    - `models/` — POJOs (`Card`, `UserMe`, `UpdateProfileRequest`)
    - `specs/` — RestAssured specs (`ApiSpecs`)
    - `tests/` — API tests (`ApiTestBase`, `MestoApiLesson1Tests`)
    - `utils/` — API utilities (optional)
- `ui/`
    - `pages/` — Page Objects (`LoginPage`, `HomePage`, `RegistrationPage`)
    - `components/` — UI components (`PostCardComponent`)
    - `config/` — UI configuration (`UiConfig`)
    - `data/` — UI test data (`UiTestData`)
    - `tests/` — UI tests (`LoginTests`, `AuthorizedTests`, `RegistrationTests`)
    - `utils/` — utilities (`AllureAttachments`, `Html5Validation`)
- `config/` — shared config (`TestConfig`)
- `testdata/` — shared test data (`CommonTestData`)

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
1. System properties `TEST_EMAIL`, `TEST_PASSWORD`
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

> Tags are defined using `@Tag("ui")` / `@Tag("api")` in test classes.

---

## Run UI Tests with Remote Selenium (CI / Docker)

Example:
```powershell
mvn clean test "-Dgroups=ui" "-Dselenide.remote=http://localhost:4444/wd/hub" "-Dselenide.headless=true"
```

---

## Allure Report

### Generate the report
```bash
mvn allure:report
```

### Output folders
- Raw results: `target/allure-results/`
- HTML report: `target/site/allure-maven-plugin/`

### Open the report locally
Open this file in your browser:
- `target/site/allure-maven-plugin/index.html`

(Optionally, you can use Allure CLI if installed separately.)

---

## GitHub Actions (CI)

Workflow file: `.github/workflows/ui-tests.yml`

In CI:
- a `selenium/standalone-chrome` container is started
- tests are executed (remote + headless)
- Allure report is generated
- artifacts are uploaded:
    - `target/surefire-reports/`
    - `target/allure-results/`
    - `target/site/allure-maven-plugin/`

---

## TODO / Improvements

- Move credentials to GitHub Secrets and read them from ENV
- Pin Selenium image version + add healthcheck + increase `/dev/shm` size
- Improve anti-flake waits in UI (likes / popups)
- Split CI into separate workflows/jobs for `api` and `ui`

---
name: feature-pr
description: >-
  Autonomous Feature-to-PR Protocol for the Spring Boot Backend. Use this skill whenever the user requests implementing, enhancing, or fixing a backend API, service, or database feature (e.g., "Implement feature: ...", "Fix bug: ..."). Guides creating a semantic feature branch off development, writing Spring Boot code with JUnit/Mockito tests, verifying build and test suite, committing, pushing, and creating a Pull Request on GitHub for human review.
---

# Autonomous Feature-to-PR Protocol (Backend)

This skill guides the automated end-to-end lifecycle of implementing a feature or bugfix in `se327-mouse-jerry-backend`, adhering to the SE327 Git Workflow and Quality Gate standards.

## Workflow Steps

### Step 1: Branch Preparation
1. Ensure the working directory is `se327-mouse-jerry-backend`.
2. Check git status to ensure working directory is clean.
3. Switch to the `development` branch and pull latest changes:
   ```bash
   git checkout development
   git pull origin development
   ```
4. Create and checkout a semantic feature branch off `development`:
   ```bash
   git checkout -b feature/<short-descriptive-kebab-name>
   # Or for bugfixes: git checkout -b fix/<short-descriptive-kebab-name>
   ```

### Step 2: Implementation & Testing
1. **Strict 5-Tier Architecture**: All features must strictly implement the enterprise layered pattern defined in `spring-boot-layered-architecture`:
   - **Controller Layer** (`@RestController`): Accepts requests, handles pagination headers (`x-total-count`), converts Entities to DTOs via `LabMapper.INSTANCE`. Never calls DAOs or Repositories directly.
   - **Service Layer** (`<Entity>Service` interface + `<Entity>ServiceImpl` with `@Service`): Orchestrates DAOs, contains business logic, and annotates mutations with `@Transactional`.
   - **DAO Layer** (`<Entity>Dao` interface + `<Entity>DaoDbImpl` with `@Repository` and `@Profile("db")`): Abstracts persistence operations and calls repositories.
   - **Repository Layer** (`<Entity>Repository` extending `JpaRepository`): Spring Data JPA query interface.
   - **DTO / MapStruct Layer**: Always map JPA `@Entity` to DTOs using MapStruct before returning from controllers. Never expose raw JPA entities.
   - **Lombok Conventions**: Use constructor injection with `@RequiredArgsConstructor`. Exclude IDs from `@EqualsAndHashCode`: `@EqualsAndHashCode.Exclude`.
2. **Mandatory Test Coverage**: Always write corresponding JUnit 5 and Mockito tests (e.g., `@WebMvcTest`, `@SpringBootTest`, or unit tests) to satisfy **Statement and Branch coverage** for SonarQube quality gates.
3. Run local verification before committing:
   ```bash
   ./mvnw clean test
   ```
4. Verify that:
   - All tests pass with zero failures and zero errors.
   - Flyway migrations and Spring Security filters compile cleanly.
   - If any test fails, resolve the issue immediately before proceeding.

### Step 3: Atomic Commits & Remote Push (DevOps Principle)
1. **Enforce Atomic Commits (One Purpose Per Commit)**:
   - **Never bulk commit unrelated files together (`git add .`)**. Stage and commit files by individual concern:
     - **Entity / Migration / DTO Layer**:
       ```bash
       git add src/main/resources/db/migration/* src/main/java/com/mousejerry/backend/entity/* src/main/java/com/mousejerry/backend/dto/*
       git commit -m "feat(<scope>): implement domain entity, DTO definitions, and Flyway migrations" -m "Co-authored-by: Antigravity Agent <agent@antigravity.google.com>"
       ```
     - **DAO & Repository Layer**:
       ```bash
       git add src/main/java/com/mousejerry/backend/repository/* src/main/java/com/mousejerry/backend/dao/*
       git commit -m "feat(<scope>): implement DAO layer with @Profile(\"db\") and repositories" -m "Co-authored-by: Antigravity Agent <agent@antigravity.google.com>"
       ```
     - **Service & Controller Layer**:
       ```bash
       git add src/main/java/com/mousejerry/backend/service/* src/main/java/com/mousejerry/backend/controller/*
       git commit -m "feat(<scope>): implement service logic and REST controller endpoints" -m "Co-authored-by: Antigravity Agent <agent@antigravity.google.com>"
       ```
     - **Unit & Integration Tests**:
       ```bash
       git add src/test/java/com/mousejerry/backend/*
       git commit -m "test(<scope>): add unit tests and Testcontainers integration tests" -m "Co-authored-by: Antigravity Agent <agent@antigravity.google.com>"
       ```
     - **Bug Fixes**:
       ```bash
       git add <fixed-file>
       git commit -m "fix(<scope>): <description of bugfix>" -m "Co-authored-by: Antigravity Agent <agent@antigravity.google.com>"
       ```
2. **Push the Branch**:
   ```bash
   git push -u origin <branch-name>
   ```

### Step 4: Pull Request Handling (Strict Template Compliance)
1. **If updating an EXISTING open PR**:
   - Pushing the branch in Step 3 **automatically updates the existing PR on GitHub**.
   - **DO NOT run `gh pr create`**.
   - Output the existing PR link confirming new commits were pushed.
2. **If creating a NEW Pull Request**:
   - **Strict Adherence to Repository PR Template**: When generating the PR description, **NEVER bypass or alter** the sections defined in `.github/pull_request_template.md`. You MUST fill out every single section thoroughly:
     - `## Summary`: Explain what changes were made, architectural motivation, and link related issues (e.g., `Closes #123`).
     - `## Changes`: Comprehensive bulleted list of all changes across entities, DAOs, services, controllers, and tests.
     - `## How it was tested`: Full breakdown of test suites (`./mvnw clean test`), coverage metrics, and build status.
     - `## Checklist`: Complete all checklist items from `.github/pull_request_template.md`.
   - Write the formatted markdown description to a temporary file `pr_body.md`:
     ```bash
     cat << 'EOF' > pr_body.md
     ## Summary
     Comprehensive implementation of <feature-name> according to Spring Boot 5-tier architecture standards.
     Closes #<issue-number>

     ## Changes
     - **Entity / DTO / Migration**: Added JPA entity, DTO definitions, and Flyway migration script.
     - **DAO & Repository Layer**: Added repository interface and DAO implementation with `@Profile("db")`.
     - **Service & Controller Layer**: Implemented transactional service logic and REST controller endpoints.
     - **Testing**: Added JUnit 5 and Mockito unit/integration tests with Testcontainers.

     ## How it was tested
     - **Build & Tests**: `./mvnw clean test` passed with zero failures and zero errors.
     - **Test Coverage**: Satisfies SonarCloud Statement and Branch coverage threshold (>80%).

     ## Checklist
     - [x] Branch is up to date with `main`/`development` and the build is green
     - [x] Tests added or updated for new behavior
     - [x] SonarCloud quality gate passes
     - [ ] Reviewed by the other team member
     EOF
     ```
   - Create the Pull Request targeting `development` with the `agent` label using GitHub CLI (`gh`):
     ```bash
     branch=$(git branch --show-current)
     case "$branch" in
       feature/*) pr_type=feat ;;
       fix/*)     pr_type=fix ;;
       *)         pr_type=chore ;;
     esac
     gh pr create --base development --head "$branch" --label "agent" --title "${pr_type}(<scope>): <Title>" --body-file pr_body.md
     rm pr_body.md
     ```
     *(If `gh` CLI is not installed or authenticated, provide the direct GitHub PR URL output by git push)*

### Step 5: Review Gate (STOP)
- Output the created Pull Request URL clearly to the user.
- **DO NOT** auto-merge the PR into `development`.
- Halt execution and prompt the user to conduct human code review on GitHub.

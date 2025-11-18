# Loan Project — Upgraded (Java 21, VS Code ready)

This upgraded project includes:
- Java 21
- JUnit5 parameterized tests
- JaCoCo code coverage reporting
- PIT mutation testing configuration
- PlantUML class diagram (.puml)
- GitHub Actions CI workflow

## Run locally (VS Code)
1. Ensure JDK 21 is installed and `JAVA_HOME` points to it.
2. Open folder in VS Code (Java extension recommended).
3. Run tests:
   ```
   mvn test
   ```
4. Generate JaCoCo report (HTML):
   ```
   mvn test
   # report generated at target/site/jacoco/index.html
   ```
5. Run PIT mutation testing:
   ```
   mvn org.pitest:pitest-maven:mutationCoverage
   # report at target/pit-reports
   ```

## Notes
- CI workflow config is in `.github/workflows/ci.yml`.
- PlantUML source is in `uml/loan_class_diagram.puml`. You can render it with any PlantUML tool.

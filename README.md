🌟 Overview

This is a Java Selenium TestNG automation framework for OpenCart, implemented using a hybrid framework approach with Page Object Model (POM). It is designed for:

Maintainable and reusable automation scripts

Data-driven and keyword-driven testing

CI/CD integration with Jenkins

Generating detailed test reports with Allure Report

OpenCartFramework/
├── src/
│   ├── main/java/com/opencart/pages/        # Page classes (POM)
│   ├── test/java/com/opencart/tests/       # Test classes
│   └── test/resources/                     # Test data (Excel/JSON)
├── Jenkinsfile                             # Pipeline for CI/CD
├── pom.xml                                 # Maven project file
└── README.md


⚙️ Prerequisites

Java 11+

Maven 3.x

Selenium WebDriver

TestNG

Jenkins (for CI/CD)

Browser drivers: ChromeDriver / GeckoDriver

🚀 Installation

Clone the repository:

git clone https://github.com/vanik642/OpenCartFramework.git


Navigate to the project folder:

cd OpenCartFramework


Install dependencies using Maven:

mvn clean install


Configure browser driver paths in config.properties.

🧪 Running Tests
Using Maven
mvn test

Using TestNG XML
mvn test -DsuiteXmlFile=testng.xml

Using Jenkins

The Jenkinsfile automates the CI/CD pipeline with stages:

Checkout – Pull code from GitHub

Build – Compile and install dependencies

Run Tests – Execute TestNG tests

Reports – Generate ExtentReports and archive test results

Pipeline triggers: GitHub push or pull request

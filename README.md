# Tealium Ecommerce Selenium Automation Framework

A comprehensive Selenium automation framework built with Java, TestNG, and Page Object Model (POM) for testing the Tealium Ecommerce Demo application.

## Project Structure

```
projekt-selenium/
├── src/
│   ├── main/
│   │   ├── java/com/tealium/ecommerce/
│   │   │   ├── base/
│   │   │   │   └── BasePage.java              # Base class for all page objects
│   │   │   ├── pages/
│   │   │   │   ├── HomePage.java              # Home page object
│   │   │   │   ├── ProductListingPage.java    # Product listing page object
│   │   │   │   ├── ProductDetailPage.java     # Product detail page object
│   │   │   │   ├── CartPage.java              # Cart page object
│   │   │   │   └── CheckoutPage.java          # Checkout page object
│   │   │   └── utils/
│   │   │       ├── ConfigReader.java          # Configuration file reader
│   │   │       ├── WaitHelper.java            # Explicit wait utilities
│   │   │       ├── ScreenshotUtil.java        # Screenshot capture utility
│   │   │       ├── ExtentReportManager.java   # Test reporting manager
│   │   │       └── ExcelReader.java           # Excel data reader
│   │   └── resources/
│   │       ├── config.properties              # Application configuration
│   │       └── log4j2.xml                     # Logging configuration
│   └── test/
│       ├── java/com/tealium/ecommerce/
│       │   ├── base/
│       │   │   └── BaseTest.java              # Base test class with setup/teardown
│       │   └── tests/
│       │       ├── HomePageTest.java          # Home page test cases
│       │       ├── ProductSearchTest.java     # Product search test cases
│       │       ├── AddToCartTest.java         # Add to cart test cases
│       │       └── CheckoutTest.java          # Checkout test cases
│       └── resources/
│           ├── testng.xml                     # TestNG suite configuration
│           └── testdata/                      # Test data files
├── test-output/
│   ├── screenshots/                           # Test failure screenshots
│   ├── reports/                               # Extent HTML reports
│   └── logs/                                  # Application logs
├── pom.xml                                    # Maven configuration
└── README.md                                  # Project documentation
```

## Technologies Used

- **Java 11** - Programming language
- **Selenium WebDriver 4.16.1** - Browser automation
- **TestNG 7.8.0** - Testing framework
- **Maven** - Build and dependency management
- **WebDriverManager 5.6.3** - Automatic driver management
- **ExtentReports 5.1.1** - Test reporting
- **Log4j 2.22.0** - Logging framework
- **Apache POI 5.2.5** - Excel file handling

## Prerequisites

Before running the tests, ensure you have the following installed:

- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome/Firefox/Edge browser

## Setup Instructions

1. **Clone or download the project**
   ```bash
   cd projekt-selenium
   ```

2. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Configure test settings** (optional)
   Edit `src/main/resources/config.properties` to customize:
   - Browser type (chrome, firefox, edge)
   - Base URL
   - Timeout values
   - Headless mode
   - Screenshot settings

## Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test suite
```bash
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml
```

### Run tests with specific browser
```bash
mvn clean test -Dbrowser=chrome
```

### Run smoke tests only
```bash
mvn clean test -Dgroups=smoke
```

### Run tests in headless mode
Update `config.properties`:
```properties
headless.mode=true
```

## Test Suites

### Smoke Test Suite
Quick validation tests covering:
- Home page load
- Logo display

### Regression Test Suite
Comprehensive tests covering:
- Home page functionality
- Product search
- Add to cart operations
- Checkout process

## Test Reports

After test execution, reports are generated in:

1. **Extent Reports**
   - Location: `test-output/reports/extent-report.html`
   - Interactive HTML report with test details, screenshots, and logs

2. **TestNG Reports**
   - Location: `test-output/index.html`
   - Default TestNG HTML report

3. **Screenshots**
   - Location: `test-output/screenshots/`
   - Automatically captured on test failures

4. **Logs**
   - Location: `test-output/logs/app.log`
   - Detailed application logs

## Page Object Model Structure

Each page in the application has a corresponding Page Object class:

- **BasePage**: Common methods inherited by all page objects
- **HomePage**: Home page elements and actions
- **ProductListingPage**: Product listing and filtering
- **ProductDetailPage**: Product details and add to cart
- **CartPage**: Cart management operations
- **CheckoutPage**: Checkout form and order placement

## Configuration

### config.properties
```properties
base.url=https://ecommerce.tealiumdemo.com/
browser=chrome
headless.mode=false
maximize.window=true
implicit.wait=10
explicit.wait=20
page.load.timeout=30
screenshot.on.failure=true
```

### TestNG XML Configuration
```xml
<suite name="Tealium Ecommerce Test Suite">
    <parameter name="browser" value="chrome"/>
    <test name="Regression Tests">
        <classes>
            <class name="com.tealium.ecommerce.tests.HomePageTest"/>
            <class name="com.tealium.ecommerce.tests.ProductSearchTest"/>
            <class name="com.tealium.ecommerce.tests.AddToCartTest"/>
            <class name="com.tealium.ecommerce.tests.CheckoutTest"/>
        </classes>
    </test>
</suite>
```

## Best Practices Implemented

1. **Page Object Model (POM)** - Separation of test logic and page elements
2. **WebDriverManager** - Automatic browser driver management
3. **Explicit Waits** - Reliable element synchronization
4. **ExtentReports** - Rich test reporting with screenshots
5. **Configuration Management** - Externalized test configurations
6. **Logging** - Comprehensive logging with Log4j2
7. **Maven** - Dependency and build management
8. **BaseTest** - Centralized test setup and teardown
9. **Screenshot on Failure** - Automatic screenshot capture for failed tests

## Utility Classes

- **ConfigReader**: Reads properties from config.properties
- **WaitHelper**: Provides explicit wait methods
- **ScreenshotUtil**: Captures screenshots on test failures
- **ExtentReportManager**: Manages test reporting
- **ExcelReader**: Reads test data from Excel files (for data-driven testing)

## Troubleshooting

### Tests fail to start browser
- Ensure WebDriverManager can download drivers (check internet connection)
- Try manually specifying driver path in BaseTest class

### Element not found errors
- Verify the website locators haven't changed
- Increase wait timeouts in config.properties
- Check if the website is accessible

### Maven build failures
- Run `mvn clean install -U` to force update dependencies
- Check Java version: `java -version` (should be 11+)
- Verify Maven installation: `mvn -version`

## Contributing

1. Follow existing code structure and naming conventions
2. Add appropriate logging statements
3. Update page objects when UI changes
4. Maintain test data in config files
5. Document new test cases

## Future Enhancements

- Parallel test execution
- Cross-browser testing with Selenium Grid
- Integration with CI/CD pipeline (Jenkins, GitHub Actions)
- API testing integration
- Database validation
- Performance testing integration

## Contact

For questions or issues, please create an issue in the project repository.

---

**Happy Testing!**

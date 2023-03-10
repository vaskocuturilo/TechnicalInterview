package com.example.demo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SimpleUITest {
    private static WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private static final int DELAY = 10;
    private static By TITLE = By.xpath("//input[@id='title_name_input']");
    private static By DESCRIPTION = By.xpath("//input[@id='description_input']");
    private static By ADD_QUESTION_BUTTON = By.xpath("//input[@id='button_add_question']");
    private static By DELETE_LINK = By.xpath("//table[@class='table table-striped table-bordered']//td[contains(text(),'Question for delete')]/following-sibling::td//button[@id='cell_delete']");
    private static By DELETE_BUTTON = By.xpath("//input[@id='delete_button']");
    private static By RESET_ALL_BUTTON = By.xpath("//input[@id='reset_button']");
    private static By TABLE = By.xpath("//table[@id='main_table']");
    private static By MAIN_PAGE_LINK_QUESTIONS = By.xpath("//input[@id='list_of_questions']");
    private static By USER_NAME = By.xpath("//input[@id='username']");
    private static By PASSWORD = By.xpath("//input[@id='password']");
    private static By SIGN_IN = By.xpath("//button[@type='submit']");
    private static By TOTAL_COUNT = By.xpath("//h1[@id='total_count']");
    private static By PASS_COUNT = By.xpath("//h1[@id='pass_count']");
    private static By FAIL_COUNT = By.xpath("//h1[@id='fail_count']");
    private static By UPLOAD_FILE = By.xpath("//input[@id='upload_file']");
    private static By UPLOAD_FILE_BUTTON = By.xpath("//input[@id='upload_questions']");
    private static By COMPLETE_CELL = By.xpath("//table[@class='table table-striped table-bordered']//td[contains(text(),'Test complete')]/following-sibling::td//button[@id='cell_complete']");
    private static By TABLE_LIST = By.xpath("//table[@id='main_table']//tbody/tr");
    private static By MESSAGE = By.xpath("//div[@id='info_message']");
    private static By SIGN_UP = By.xpath("//input[@id='register_page']");
    private static By SIGN_UP_BUTTON = By.xpath("//input[@value='Sign Up']");
    private static By EMAIL = By.xpath("//input[@id='email']");
    private static By GENDER = By.xpath("//select[@id='gender']");
    private static By MENU = By.xpath("//div[@id='menuToggle']");
    private static By ABOUT_ITEM = By.xpath("//a[@id='link_about']");
    private static By MODAL_WINDOW = By.xpath("//div[@class='modal']");
    private static By MODAL_CLOSE = By.xpath("//button[@class='close-modal']");
    private static By MODAL_TITLE = By.xpath("//div[@class='modal']/h1");
    private static By MODAL_TEXT = By.xpath("//div[@class='modal']/p");
    private static String MODAL_TITLE_TEXT = "I'm a modal window";
    private static String MODAL_VERIFY_TEXT = "This is a modal window for a technical interview application.";
    private static final String UPLOAD_MESSAGE = "You successfully uploaded file is: %s";
    private static final String ERROR_UPLOAD_MESSAGE = "Maximum upload size of %s exceeded";

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1200");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        webDriver = new ChromeDriver(options);
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        webDriver.get("http://localhost:8080/");
    }

    @Test
    public void testVerifyTitle() {
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");
    }

    @Test
    public void testVerifyModalWindowOnMainPage() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MENU)).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ABOUT_ITEM)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_WINDOW)).isDisplayed());
        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_CLOSE)).isDisplayed());
        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_TITLE)).getText()).contains(MODAL_TITLE_TEXT);
        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_TEXT)).getText()).contains(MODAL_VERIFY_TEXT);
    }

    @Test
    public void testVerifyAddElementsOnPage() {
        loginUser("admin@qa.team", "123456");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));

        Integer totalCount = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText());

        Integer failCount = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(FAIL_COUNT)).getText());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TITLE)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_QUESTION_BUTTON)).click();

        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText())).isEqualTo(++totalCount);
        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(FAIL_COUNT)).getText())).isEqualTo(++failCount);

    }

    @Test
    public void testVerifyCompleteQuestion() {
        final String title = "Test complete";
        loginUser("admin@qa.team", "123456");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        Integer count = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASS_COUNT)).getText());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TITLE)).sendKeys(title);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys(title);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_QUESTION_BUTTON)).click();

        scrollPage();
        waiter(3000);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(COMPLETE_CELL)).click();
        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASS_COUNT)).getText())).isEqualTo(++count);
    }

    @Test
    public void testVerifyTable() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        loginUser("admin@qa.team", "123456");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TITLE)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys("Test1");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_QUESTION_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());
    }

    @Test
    public void testDeleteElementFromTable() {
        final String title = "Question for delete";
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));

        loginUser("admin@qa.team", "123456");
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TITLE)).sendKeys(title);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DESCRIPTION)).sendKeys(title);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(ADD_QUESTION_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());

        Integer count = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText());

        scrollPage();
        waiter(3000);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_LINK)).click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();

        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText())).isEqualTo(--count);
    }

    @Test
    public void testDeleteAllElementsFromTable() {
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        loginUser("admin@qa.team", "123456");
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TABLE)).isDisplayed());

        assertThat(convertListToLocator(TABLE_LIST).size()).isNotEqualTo(0);

        scrollPage();
        waiter(1000);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(DELETE_BUTTON)).click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();

        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(TOTAL_COUNT)).getText())).isEqualTo(0);
    }

    @Test
    public void testUploadCorrectJsonSizeFile() {
        File file = new File("src/main/resources/upload.json");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));

        loginUser("admin@qa.team", "123456");
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE)).sendKeys(file.getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText()).isEqualTo(String.format(UPLOAD_MESSAGE, "upload.json"));
    }

    @Test
    public void testUploadCorrectCsvSizeFile() {
        File file = new File("src/main/resources/upload.csv");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));

        loginUser("admin@qa.team", "123456");
        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE)).sendKeys(file.getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText()).isEqualTo(String.format(UPLOAD_MESSAGE, "upload.csv"));
    }

    @Test
    public void testUploadCorrectXlsxSizeFile() {
        File file = new File("src/main/resources/upload.xlsx");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));

        loginUser("admin@qa.team", "123456");

        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE)).sendKeys(file.getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText()).isEqualTo(String.format(UPLOAD_MESSAGE, "upload.xlsx"));
    }

    @Test
    public void testUploadInCorrectJsonSizeFile() {
        File file = new File("src/main/resources/10mb-sample.json");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));

        loginUser("admin@qa.team", "123456");

        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE)).sendKeys(file.getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE_BUTTON)).click();

        assertThat(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE)).getText()).isEqualTo(String.format(ERROR_UPLOAD_MESSAGE, "1MB"));
    }

    @Test
    public void testResetAllTechnicalInterviewQuestions() {
        File file = new File("src/main/resources/upload.json");
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));

        loginUser("admin@qa.team", "123456");

        assertThat(webDriver.getTitle()).isEqualTo("Technical interview questions");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE)).sendKeys(file.getAbsolutePath());

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(UPLOAD_FILE_BUTTON)).click();

        Integer count = Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(FAIL_COUNT)).getText());

        assertThat(convertListToLocator(TABLE_LIST).size()).isNotEqualTo(0);
        assertThat(count).isNotEqualTo(0);

        scrollPage();
        waiter(1000);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(RESET_ALL_BUTTON)).click();
        Alert alert = webDriver.switchTo().alert();
        alert.accept();

        assertThat(Integer.parseInt(webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(FAIL_COUNT)).getText())).isNotEqualTo(0);
    }

    @Test
    public void testRegisterNewUser() {
        String credential = createEmail();
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(DELAY));
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SIGN_UP)).click();


        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL)).sendKeys(credential);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD)).sendKeys(credential);
        selectDropDown(GENDER).selectByValue("Male");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SIGN_UP_BUTTON)).click();
        assertTrue(webDriver.getCurrentUrl().contains("process_register"));
    }

    private void loginUser(final String username, final String password) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MAIN_PAGE_LINK_QUESTIONS)).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(USER_NAME)).sendKeys(username);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD)).sendKeys(password);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SIGN_IN)).click();
    }

    @AfterAll
    public static void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private void scrollPage() {
        JavascriptExecutor js = ((JavascriptExecutor) webDriver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    private void waiter(final Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<WebElement> convertListToLocator(By locator) {
        return webDriver.findElements(locator);
    }

    private Select selectDropDown(final By locator) {
        return new Select(webDriver.findElement(locator));
    }

    private String createEmail() {
        return "test_" + new Date().getTime() + "@qa.team";
    }
}

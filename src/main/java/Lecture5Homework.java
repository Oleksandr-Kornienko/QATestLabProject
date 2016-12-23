import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class Lecture5Homework {
    private static WebDriver driver;

    @DataProvider(name = "testData")
    public final Object[][] testData() {
        return new Object[][]{
                {"automatio"}
        };
    }

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //1. Открыть главную страницу Bing http://www.bing.com/
        driver.navigate().to("https://www.bing.com/");
    }

    @Test(dataProvider = "testData")
    public void Method1(final String text) {

        /*2. Проверить, что на странице отображается изображение-логотип Bing, отображается
        и доступно для использования поле ввода запроса и кнопка поиска.*/
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'logo')]")).isDisplayed() &&
                driver.findElement(By.className("b_searchbox")).isDisplayed() &&
                driver.findElement(By.className("b_searchboxSubmit")).isDisplayed());

       /* 3. В поисковую строку ввести слово без последней буквы (например, “automatio”
        вместо “automation”). Дождаться появления слова целиком в выпадающем списке
        предложений. Выбрать искомое слово и дождаться загрузки результатов
        поискового запроса. Данные поискового запроса должны передаваться в тестовый
        метод используя @DataProvider.*/
        driver.findElement(By.className("b_searchbox")).sendKeys(text);
        waitUntilVisible(By.xpath("//li[@id='sa_5005']/div"));
        driver.findElement(By.xpath("//li[@id='sa_5005']/div")).click();
        waitUntilVisible(By.xpath("//li[@class='b_algo']"));

        /*4. Для каждого результата поиска выполнить следующие действия:
        5. Сохранить указанный адрес для страницы результата.
        6. Кликнуть по заголовку результата для перехода на найденную страницу.
        7. После перехода по ссылке проверить, что URL открытой страницы такой же, как был
        указан в результатах поиска.*/
        int resultsSize = driver.findElements(By.xpath("//ol[@id='b_results']//h2/a")).size();
        for (int i = 0; i < resultsSize; i++) {
            final List<WebElement> results = driver.findElements(By.xpath("//ol[@id='b_results']//h2/a"));
            final String currentUrl = results.get(i).getAttribute("href");
            waitUntilAllVisible(By.xpath("//ol[@id='b_results']//h2/a"));
            results.get(i).click();
            waitForUrl(currentUrl);
            Assert.assertTrue(currentUrl.equals(driver.getCurrentUrl()));
            driver.navigate().back();
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    private static void waitUntilVisible(final By locator) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    private static void waitUntilAllVisible(final By locator) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    private void waitForUrl(final String currentUrl) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.urlToBe(currentUrl));
    }

}


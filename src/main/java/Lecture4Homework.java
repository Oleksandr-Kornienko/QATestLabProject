import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Lecture4Homework {
    static EventFiringWebDriver driver;


    @DataProvider(name = "testData")
    protected Object[][] testData() {
        Properties cred = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/java/testData.txt");
            cred.load(input);
            input.close();
        } catch (IOException e) {

        }
        Object[][] testData = new Object[1][1];
        testData[0][0] = cred.getProperty("textForTest");

        return testData;
    }

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new EventFiringWebDriver(new FirefoxDriver());
        driver.register(new WebDriverLogger());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //1. Открыть главную страницу Bing http://www.bing.com/
        driver.navigate().to("https://www.bing.com/");
    }


    @Test(dataProvider = "testData")
    public void method1(final String textForTest) {

        //2. Перейти в раздел поиска изображений. Дождаться, что заголовок страницы имеет название “Лента изображений Bing.”
        getElement(By.id("scpl1")).click();
        waitUntilTitleContains("Лента изображений Bing");

       /* 3. Выполнить прокрутку страницы несколько раз.
        Каждый раз проверять, что при достижении низа страницы подгружаются новые блоки с изображениями.*/
        doScroll();
        doScroll();
        doScroll();

       /* 4. Выполнять поиск изображений по ключевым словам, которые будут браться из текстового файла.
        Данные должны передаваться в тестовый метод используя @DataProvider.*/
        driver.executeScript("window.scrollTo(0, 0)");
        waitUntilVisible(By.className("b_searchbox"));
        getElement(By.className("b_searchbox")).sendKeys(textForTest);


        /*5. После загрузки страницы с результатами поиска навести курсор на картинку.
        Проверить, что отобразилась рамка с увеличенным изображением.
        Под картинкой доступны кнопки добавления в коллекцию, поиска по изображению и сообщения о нарушении.*/
        getElement(By.className("b_searchboxSubmit")).click();
        waitUntilVisible(By.xpath("//div[@class='dg_u'][1]"));
        Actions action = new Actions(driver);
        action.moveToElement(getElement(By.xpath("//div[@class='dg_u'][1]"))).build().perform();
        waitUntilVisible(By.className("irhc"));
        Assert.assertTrue(getElement(By.xpath("//img[@title='Поиск по изображению']")).isDisplayed() &&
                getElement(By.xpath("//img[@title='Пометить как изображение для взрослых']")).isDisplayed());

        //6. Нажать на кнопку поиска по изображению и дождаться загрузки слайдшоу.
        getElement(By.xpath("//img[@title='Поиск по изображению']")).click();
        waitUntilVisible(By.xpath("//div[@id='detail_film']"));
    }

    @Parameters(value = "minimalPictureNumber")
    @Test
    public void method2(final int minimalPictureNumber) {
       /* 7. Воспользоваться кнопкой “Смотреть другие изображения” в блоке “Связанные изображения”.
        Проверить количество подгружаемых связанных изображений.
        Значение минимального количества картинок передавать в тест используя @Parameters.*/
        int imagesBeforeClick = driver.findElements(By.xpath("//ul[@class='dgControl_list']/li[not(@class='b_hide')]")).size();
        getElement(By.xpath("//a[@title='Смотреть другие изображения']")).click();
        waitUntilVisible(By.xpath("//ul[@class='dgControl_list']/li[not(@class='b_hide')]"));
        int imagesAfterClick = driver.findElements(By.xpath("//ul[@class='dgControl_list']/li[not(@class='b_hide')]")).size();
        Assert.assertTrue(imagesAfterClick > imagesBeforeClick);
        Assert.assertTrue(minimalPictureNumber == 5);
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    private static void waitUntilVisible(final By locator) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    private static WebElement getElement(final By locator) {
        return driver.findElement(locator);
    }

    private static void waitUntilTitleContains(final String title) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.titleContains(title));
    }

    private static void doScroll() {
        int imagesBeforeScroll = driver.findElements(By.xpath("//div[@class='img_cont hoff']/img")).size();
        driver.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        (new WebDriverWait(driver, 10)).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='img_cont hoff']/img")));
        int imagesAfterScroll = driver.findElements(By.xpath("//div[@class='img_cont hoff']/img")).size();
        Assert.assertTrue(imagesAfterScroll > imagesBeforeScroll);
    }
}

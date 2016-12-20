import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lecture3Homework {
    static WebDriver driver;

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //1. Открыть главную страницу поисковой системы Bing.
        driver.navigate().to("https://www.bing.com/");

        /*2. Перейти в раздел поиска изображений. Дождаться, что заголовок страницы имеет
        название “Лента изображений Bing.”*/
        getElement(By.id("scpl1")).click();
        waitUntilTitleContains("Лента изображений Bing");

        /*3. Выполнить прокрутку страницы несколько раз. Каждый раз проверять, что при
        достижении низа страницы подгружаются новые блоки с изображениями.*/
        //make scroll images shows correctly
        doScroll();
        doScroll();
        doScroll();

         /*4. В поисковую строку ввести слово без последней буквы (например “automatio”
        вместо “automation”). Дождаться появления слова целиком в выпадающем списке
        предложений. Выбрать искомое слово и дождаться загрузки результатов
        поискового запроса.*/
        driver.navigate().to("https://www.bing.com/images/explore?FORM=ILPSTR");
        getElement(By.className("b_searchbox")).sendKeys("automatio");
        waitUntilAllVisible(By.xpath("//div[@class='sa_tm']/strong"));
        getElements(By.xpath("//div[@class='sa_tm']/strong")).get(2).click();


        // 5. Установить фильтр Дата: “В прошлом месяце”. Дождаться обновления результатов.
        waitUntilAllVisible(By.xpath("//div[@class='dg_u']"));
        getElement(By.xpath("//span[@class='ftrH ftrHL' and @title='Фильтр: Дата']")).click();
        waitUntilAllVisible(By.xpath("//a[@title='В прошлом месяце']"));
        getElement(By.xpath("//a[@title='В прошлом месяце']")).click();

        /* 6. Нажать на первое изображение из результатов поиска. Дождаться перехода в
        режим слайд шоу.*/
        waitUntilStaleness(By.xpath("//div[@class='dg_u']"));
        waitUntilAllVisible(By.xpath("//div[@class='dg_u']"));
        getElement(By.xpath("//div[1][@class='dg_u']")).click();
        driver.navigate().refresh();
        waitUntilVisible(By.xpath("//a[@title='Следующий результат поиска изображений']"));

        /*7. Выполнить переключение на следующее, предыдущее изображение. После
        переключения между изображениями необходимо дожидаться обновления
        очереди изображений для показа в нижней части окна слайд шоу.*/
        getElement(By.xpath("//a[@title='Следующий результат поиска изображений']")).click();
        waitUntilVisible(By.xpath("//div[@id='detail_film']"));
        getElement(By.xpath("//a[@title='Предыдущий результат поиска изображений']")).click();
        waitUntilVisible(By.xpath("//div[@id='detail_film']"));

        /*8. Нажать на отображаемое изображение в режиме слайд шоу и удостовериться, что
        картинка загрузилась в отдельной вкладке/окне.*/
        getElement(By.xpath("//img[@title='Просмотр исходного изображения']")).click();
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        waitUntilVisible(By.xpath("//img"));
        driver.quit();
    }

    private static WebElement getElement(final By locator) {
        return driver.findElement(locator);
    }

    private static List<WebElement> getElements(final By locator) {
        return driver.findElements(locator);
    }

    private static void waitUntilAllVisible(final By locator) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    private static void waitUntilVisible(final By locator) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private static void waitUntilStaleness(final By locator) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.stalenessOf(getElement(locator)));
    }

    private static void waitUntilTitleContains(final String title) {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.titleContains(title));
    }

    private static void doScroll() {
        final JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        (new WebDriverWait(driver, 10)).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='img_cont hoff']/img")));
    }
}

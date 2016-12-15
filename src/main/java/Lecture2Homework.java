import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lecture2Homework {
    private static void setUp(final WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        final WebDriver driver = new FirefoxDriver();
        setUp(driver);
        //Open bing page
        driver.get("https://www.bing.com/");
        //Write "automation" in the "search" field
        final WebElement searchField = driver.findElement(By.className("b_searchbox"));
        searchField.sendKeys("automation");
        //Click on the search button
        final WebElement searchButton = driver.findElement(By.className("b_searchboxSubmit"));
        searchButton.click();
        //Print page title
        System.out.println("Page title is: " + driver.getTitle());
        //Write lists of titles to the WebElement list
        final List<WebElement> elements = driver.findElements(By.xpath("//*[@id='b_results']/li/div/h2"));
        //Print text in every element of a list
        for (int i = 0; i < elements.size(); i++) {
            System.out.println(i + 1 + " Link title is = " + elements.get(i).getText());
        }

        tearDown(driver);
    }
    private static void tearDown(final WebDriver driver) {
        driver.quit();
    }
}

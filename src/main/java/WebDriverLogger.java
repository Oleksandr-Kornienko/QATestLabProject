import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class WebDriverLogger implements WebDriverEventListener {
    public void beforeNavigateTo(String s, WebDriver webDriver) {
        System.out.println("Before navigate to :" + s);
    }

    public void afterNavigateTo(String s, WebDriver webDriver) {
        System.out.println("Navigated to :" + s);
    }

    public void beforeNavigateBack(WebDriver webDriver) {
        System.out.println("Navigating back to previous page");
    }

    public void afterNavigateBack(WebDriver webDriver) {
        System.out.println("Navigated back to previous page");
    }

    public void beforeNavigateForward(WebDriver webDriver) {
        System.out.println("Navigating forward to next page");
    }

    public void afterNavigateForward(WebDriver webDriver) {
        System.out.println("Navigated forward to next page");
    }

    public void beforeNavigateRefresh(WebDriver webDriver) {
        System.out.println("Refreshing");
    }

    public void afterNavigateRefresh(WebDriver webDriver) {
        System.out.println("Refreshed");
    }

    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        System.out.println("looking for element " + by);
    }

    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
        System.out.println("found element " + by);
    }

    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
        System.out.println("WebDriver click on element - " + webElement);
    }

    public void afterClickOn(WebElement webElement, WebDriver webDriver) {
        System.out.println("WebDriver clicked on element - " + webElement);
    }

    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver) {
        System.out.println("Changing value of element - " + webElement);
    }

    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver) {
        System.out.println("Changed value of element - " + webElement);
    }

    public void beforeScript(String s, WebDriver webDriver) {

    }

    public void afterScript(String s, WebDriver webDriver) {

    }

    public void onException(Throwable throwable, WebDriver webDriver) {
        System.out.println("Error occurred: " + throwable);
    }
}

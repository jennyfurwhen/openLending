import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OpenLending {
    public static void main(String[] args) throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Go to Google and search for OpenLending
        driver.get("https://www.google.com");
        WebElement searchbar = driver.findElement(By.name("q"));
        searchbar.sendKeys("OpenLending");
        searchbar.sendKeys(Keys.RETURN);

        // Click on the OpenLending result link
        String resultXpath = "//*[text()='Automated Lending Platform | Open Lending | United States']";
        driver.findElement(By.xpath(resultXpath)).click();

        // Wait for Resources link to be clickable before trying to click on it
        String resourcesXpath = "//div[@id='main_nav']//a[text()='Resources']";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(resourcesXpath)));
        driver.findElement(By.xpath(resourcesXpath)).click();

        // While Load More button is present, scroll to it and click on it
        // wait for Loading... button to appear and disappear before checking for the Load More button
        // Load More button is not displayed (but is still present as an element) when there are no more blogs to load
        String loadMoreButtonXpath = "//section[@id='featured-box-section']//button[text()='Load More']";
        String loadingButtonXpath = "//section[@id='featured-box-section']//button[text()='Loading...']";
        Actions actions = new Actions(driver);
        while (driver.findElement(By.xpath(loadMoreButtonXpath)).isDisplayed()) {
            WebElement loadMoreButton = driver.findElement(By.xpath(loadMoreButtonXpath));
            actions.moveToElement(loadMoreButton);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loadMoreButtonXpath)));
            loadMoreButton.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(loadingButtonXpath)));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadingButtonXpath)));
        }
    }
}

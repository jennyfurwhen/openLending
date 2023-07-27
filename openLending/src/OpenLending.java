import java.time.Duration;
import java.util.HashMap;
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

        // Iterate through each blog text and link 
        // If blog text and corresponding link alread exists, fail test
        // If it doesn't, add it to the hash and continue iterating
        // If all don't exist, pass
        String numOfBlogsXpath = "//section[@id='featured-box-section']//li";
        int numOfBlogs = driver.findElements(By.xpath(numOfBlogsXpath)).size();
        HashMap<String, String> blogsHash = new HashMap<String, String>();
        for (int i = 1; i < numOfBlogs; i++) {
            String blogXpath = String.format("//section[@id='featured-box-section']//li[%d]", i);
            String blogLinkXpath = String.format("(//div[@class='lenders-featured-block'])[%d]//a[@class='read-more-link outline-primary-link']", i);
            String blogText = driver.findElement(By.xpath(blogXpath)).getText();
            String blogLink = driver.findElement(By.xpath(blogLinkXpath)).getAttribute("href");
            if (blogsHash.containsKey(blogText)) {
                if (blogsHash.get(blogText) == blogLink) {
                    String errorMsg = String.format("Duplicate blog post found: %s with link %s", blogText, blogLink);
                    driver.quit(); 
                    throw new Error(errorMsg);
                }
            }
            else {
                blogsHash.put(blogText, blogLink);
            }
        }

        System.out.println("Test passed");
        driver.quit(); 
    }
}

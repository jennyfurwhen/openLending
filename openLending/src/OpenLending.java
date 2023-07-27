import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenLending {
    public static void main(String[] args) throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        // Go to Google and search for OpenLending
        driver.get("https://www.google.com");
        WebElement searchbar = driver.findElement(By.name("q"));
        searchbar.sendKeys("OpenLending");
        searchbar.sendKeys(Keys.RETURN);
    }
}

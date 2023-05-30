import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Step 1: Open browser and navigate to the website
        WebDriver driver = new ChromeDriver(); // Assuming you have Chrome WebDriver installed
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");

        // Step 2: Add 1 item in the cart 4 times using FOR loop
        for (int i = 0; i < 4; i++) {
            driver.findElement(By.cssSelector("#root > div > div.products-wrapper > div > div:nth-child(6) > div.product-action > button")).click();
            waitInSeconds(1); // Wait for 1 second
        }

        // Step 3: Click on basket icon and then click on Proceed to checkout button
        driver.findElement(By.cssSelector(".cart-icon")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'PROCEED TO CHECKOUT')]"))).click();

        // Step 4: Enter a promo code
        String totalAmount = getTotalAmount(driver, wait);
        driver.findElement(By.cssSelector(".promoCode")).sendKeys(totalAmount);

        // Step 5: Click on Apply button
        driver.findElement(By.cssSelector(".promoBtn")).click();

        // Step 6: Click on Place Order button
        driver.findElement(By.xpath("//button[contains(text(), 'Place Order')]")).click();

        // Step 7: Verify that Select option is disabled in the Choose Country drop-down
        WebElement countryDropdown = driver.findElement(By.cssSelector("#root > div > div > div > div > div > select"));
        Select select = new Select(countryDropdown);
        boolean isSelectOptionEnabled = !select.getFirstSelectedOption().isEnabled();
        System.out.println("Is 'Select' option disabled? " + isSelectOptionEnabled);

        // Step 8: Randomly choose a country and click on Proceed button
        select.selectByVisibleText(getRandomCountry(driver));
        driver.findElement(By.cssSelector("#root > div > div > div > div > button")).click();

        // Step 9: Check Terms & Conditions checkbox and click on Proceed button
        driver.findElement(By.cssSelector("#root > div > div > div > div > input")).click();
        driver.findElement(By.cssSelector("#root > div > div > div > div > button")).click();

        // Close the browser
        driver.quit();
    }

    private static String getTotalAmount(WebDriver driver, WebDriverWait wait) {
        WebElement totalAmountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div > div > div > div > span.totAmt")));
        return totalAmountElement.getText();
    }

    private static String getRandomCountry(WebDriver driver) {
        WebElement countryDropdown = driver.findElement(By.cssSelector("#root > div > div > div > div > div > select"));
        Select select = new Select(countryDropdown);
        int randomIndex = (int) (Math.random() * (select.getOptions().size() - 1)) + 1;
        return select.getOptions().get(randomIndex).getText();
    }

    private static void waitInSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
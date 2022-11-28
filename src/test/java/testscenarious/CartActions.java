package testscenarious;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class CartActions {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.e-bebek.com/");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtSearchBox")));
        WebElement search = driver.findElement(By.id("txtSearchBox"));
        search.sendKeys("kaşık maması", Keys.ENTER);
        Thread.sleep(5000);

        JavascriptExecutor js = (JavascriptExecutor)driver;

        long intialLength = (long) js.executeScript("return document.body.scrollHeight");

        while(true){
            js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long currentLength = (long) js.executeScript("return document.body.scrollHeight");
            if(intialLength == currentLength) {
                break;
            }
            intialLength = currentLength;
        }


        List<WebElement> products = driver.findElements(By.id("addToCartBtn"));

        int index = products.size()-1;


        List<WebElement> elements = driver.findElements(By.xpath("//span[@class=\"description plist-desc\"]"));

        String productText = elements.get(index).getText();


        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].scrollIntoView(true);",elements.get(index));
        executor.executeScript("arguments[0].click();",elements.get(index));

        Thread.sleep(2000);

        String productName = driver.findElement(By.xpath("(//b[@id=\"txtProductTitle\"])[2]")).getText();


        if (productText.equals(productName)){
            System.out.println("Expected output: " + productText);
            System.out.println("Received output: " + productName);
            System.out.println("Names are equal....");

            WebElement product = driver.findElement(By.id("addToCartBtn"));
            product.click();
            Thread.sleep(2000);
        } else {
            System.out.println("Expected output: " + productText);
            System.out.println("Received output: " + productName);
            System.out.println("Names are not equal....");
        }
        WebElement showProduct = driver.findElement(By.id("btnShowCart"));
        showProduct.click();

        Thread.sleep(2000);

        WebElement finishTransaction = driver.findElement(By.id("btnGoToShippingAddress"));
        finishTransaction.click();
        Thread.sleep(2000);

        if (driver.getTitle().equals("Giriş Yap")){
            System.out.println("Test case passed.");
            System.out.println("Expected output: Giriş Yap");
            System.out.println("Received output: " + driver.getTitle());
        } else {
            System.out.println("Test case have not passed.");
            System.out.println("Expected output: Giriş Yap");
            System.out.println("Received output: " + driver.getTitle());
        }
    }
}


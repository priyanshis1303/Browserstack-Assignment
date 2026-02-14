package automation.assignment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
public class Main {
	 public static void main(String[] args) {

	        WebDriverManager.chromedriver().setup();
	        WebDriver driver = new ChromeDriver();

	        driver.get("https://elpais.com/opinion/");
	        System.out.println("Opened El Pais Opinion page");

	        driver.quit();
	    }
}

package automation.assignment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openqa.selenium.JavascriptExecutor;

public class BstackParallelTest {
	  public static final String USERNAME = "priyanshisingh_uyzgV1";
	    public static final String ACCESS_KEY = "ZFU54TtjGqoJb36WXW1v";

	    public static final String HUB_URL =
	            "https://" + USERNAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

	    public static void main(String[] args) {

	        // 5 parallel threads
	        ExecutorService executor = Executors.newFixedThreadPool(5);

	        // Desktop + Mobile combinations
	        executor.submit(() -> runTest("Chrome", "Windows", "11"));
	        executor.submit(() -> runTest("Edge", "Windows", "11"));
	        executor.submit(() -> runTest("Safari", "OS X", "Monterey"));
	        executor.submit(() -> runMobileTest("Samsung Galaxy S22", "12.0"));
	        executor.submit(() -> runMobileTest("iPhone 13", "15"));

	        executor.shutdown();

	        try {
	            if (!executor.awaitTermination(2, java.util.concurrent.TimeUnit.MINUTES)) {
	                executor.shutdownNow();
	            }
	        } catch (InterruptedException e) {
	            executor.shutdownNow();
	        }

	    }

	    // Desktop test
	    public static void runTest(String browser, String os, String osVersion) {
	        try {
	            ChromeOptions options = new ChromeOptions();

	            options.setCapability("browserName", browser);
	            options.setCapability("bstack:options", java.util.Map.of(
	                    "os", os,
	                    "osVersion", osVersion,
	                    "sessionName", "ElPais Parallel Test"
	            ));

	            WebDriver driver = new RemoteWebDriver(new URL(HUB_URL), options);

	            driver.get("https://elpais.com/opinion/");
	            System.out.println(browser + " Title: " + driver.getTitle());
	            ((JavascriptExecutor) driver).executeScript(
	            	    "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test completed successfully\"}}"
	            	);

	            driver.quit();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Mobile test
	    public static void runMobileTest(String device, String osVersion) {
	        try {
	            ChromeOptions options = new ChromeOptions();

	            options.setCapability("browserName", "Chrome");
	            options.setCapability("bstack:options", java.util.Map.of(
	                    "deviceName", device,
	                    "osVersion", osVersion,
	                    "realMobile", "true",
	                    "sessionName", "ElPais Mobile Test"
	            ));

	            WebDriver driver = new RemoteWebDriver(new URL(HUB_URL), options);

	            driver.get("https://elpais.com/opinion/");
	            System.out.println(device + " Title: " + driver.getTitle());
	            ((JavascriptExecutor) driver).executeScript(
	            	    "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Test completed successfully\"}}"
	            	);

	            driver.quit();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}

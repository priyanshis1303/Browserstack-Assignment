package automation.assignment;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.HttpURLConnection;
import java.net.URL;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

public class ElPaisScraper {

    public static void main(String[] args) throws Exception {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(900, 700));


      //  driver.manage().window().maximize();
        driver.get("https://elpais.com/opinion/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Click "Accept cookies" button
            WebElement acceptCookies = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.cssSelector("button#didomi-notice-agree-button")
                    )
            );
            acceptCookies.click();
            System.out.println("Cookies accepted.");
        } catch (Exception e) {
            System.out.println("No cookie popup found.");
        }


        // ✅ correct locator for article links
        List<WebElement> links =
                driver.findElements(By.cssSelector("article h2 a"));

        List<String> translatedTitles = new ArrayList<>();

//       
        int openedArticles = 0;   // count successfully opened articles
        int index = 0;

        while (openedArticles < 5 && index < links.size()) {

            try {
                WebElement link = links.get(index);

                String titleSpanish = link.getText().trim();

                // skip empty titles
                if (titleSpanish.isEmpty()) {
                    index++;
                    continue;
                }

                System.out.println("\nSPANISH TITLE: " + titleSpanish);

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView(true);", link);
                Thread.sleep(1000);

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", link);

                Thread.sleep(4000);

                // get article content
                String content = driver.findElement(By.tagName("body")).getText();
                System.out.println("CONTENT (first 300 chars):\n" +
                        content.substring(0, Math.min(300, content.length())));

                // download image if exists
                try {
                    WebElement img = driver.findElement(By.cssSelector("figure img"));
                    String imgUrl = img.getAttribute("src");

                    InputStream in = new URL(imgUrl).openStream();
                    Files.copy(in, Paths.get("article_" + openedArticles + ".jpg"),
                            StandardCopyOption.REPLACE_EXISTING);

                    System.out.println("Image downloaded.");
                } catch (Exception e) {
                    System.out.println("No image found.");
                }

                // translate title
                String translated = translate(titleSpanish);
                translatedTitles.add(translated);
                System.out.println("ENGLISH TITLE: " + translated);

                openedArticles++;   // ✅ count only successful articles

                driver.navigate().back();
                Thread.sleep(4000);

                // re-fetch links after navigation
                links = driver.findElements(By.cssSelector("article h2 a"));

            } catch (Exception e) {
                System.out.println("Skipping invalid article...");
            }

            index++;
        }

     // ✅ analyze repeated words
        Map<String, Integer> wordCount = new HashMap<>();

        for (String title : translatedTitles) {
            for (String word : title.toLowerCase().split("\\W+")) {
                if (!word.isEmpty()) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }
        }

        System.out.println("\nRepeated words from all the headers combined (>2 times):");

        boolean found = false;   // ✅ track if any repeated word exists

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + " → " + entry.getValue());
                found = true;
            }
        }

        // ✅ print message if none found
        if (!found) {
            System.out.println("No repeated words found.");
        }

        driver.quit();
    }

    public static String translate(String text) {
        try {
            String urlStr = "https://api.mymemory.translated.net/get?q="
                    + java.net.URLEncoder.encode(text, "UTF-8")
                    + "&langpair=es|en";

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new URL(urlStr).openStream()));

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();

            String json = response.toString();

            int start = json.indexOf("\"translatedText\":\"");
            if (start == -1) return "Translation not found";

            start += 18;
            int end = json.indexOf("\"", start);

            return json.substring(start, end);

        } catch (Exception e) {
            return "Translation failed";
        }
    }


}

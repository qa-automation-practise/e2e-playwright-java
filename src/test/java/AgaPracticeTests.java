import com.microsoft.playwright.*;

import java.nio.file.Paths;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

//TODO Try with chrome-beta
//TODO Run the test with mvn command

public class AgaPracticeTests {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserType.LaunchOptions lp;

    @BeforeMethod
    public void beforeTest() {
        //start playwright server
        playwright = Playwright.create();
        lp = new BrowserType.LaunchOptions();
        //channel is version of browser you want
        lp.setChannel("chrome");
        lp.setHeadless(false);
        //launch method establishes the connection, returns a browser
        //In the first run: downloads browsers to C:\Users\antoncza\AppData\Local\ms-playwright
        browser = playwright.chromium().launch(lp);
        page = browser.newPage();
        page.navigate("https://playwright.dev/java/docs/screenshots#full-page-screenshots");

    }

    @Test
    public void howToDoScreenshotsAndVideoRecords() {
        System.out.println(page.title());
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("src/test/resources/Screens/test.png"))
                .setFullPage(true));

        page.locator("//code[contains(@class, 'codeBlockLines_e6Vv')]")
                .nth(0)
                .screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("src/test/resources/Screens/Element_test.png")));
    }

    @Test
    public void howToUseSelectors() {

    }

    @AfterMethod
    public void afterTest() {
        //close the browser
        browser.close();
        //close the server
        playwright.close();
    }

}

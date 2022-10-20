import com.microsoft.playwright.*;

import java.nio.file.Paths;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

//TODO Try with chrome-beta
//TODO Run the test with mvn command - mvn exec:java -D"exec.mainClass"="AgaPracticeTests" (quotes on Windows!)

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

    @Test(enabled = false)
    public void howToDoScreenshotsAndVideoRecords() {
        System.out.println(page.title());
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("src/test/resources/Screens/test.png"))
                .setFullPage(true));

        page.locator("//code[contains(@class, 'codeBlockLines_e6Vv')]")
                .nth(0)
                .screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("src/test/resources/Screens/Element_test.png")));
    }

    @Test(enabled = false)
    public void howToUseSelectors() {

    }

    @Test(enabled = false)
    public void howToGenerateCode(){
        //PWDEBUG = 1
        //mvn exec:java -e -D"exec.mainClass"="com.microsoft.playwright.CLI" -D"exec.args"="codegen https://www.saucedemo.com/"

        // Go to https://www.saucedemo.com/
        page.navigate("https://www.saucedemo.com/");
        // Click [data-test="username"]
        page.locator("[data-test=\"username\"]").click();
        // Fill [data-test="username"]
        page.locator("[data-test=\"username\"]").fill("test");
        // Press Tab
        page.locator("[data-test=\"username\"]").press("Tab");
        // Fill [data-test="password"]
        page.locator("[data-test=\"password\"]").fill("test");
        // Double click [data-test="username"]
        page.locator("[data-test=\"username\"]").dblclick();
        // Fill [data-test="username"]
        page.locator("[data-test=\"username\"]").fill("standard_user");
        // Press Tab
        page.locator("[data-test=\"username\"]").press("Tab");
        // Fill [data-test="password"]
        page.locator("[data-test=\"password\"]").fill("secret_sauce");
        // Press Enter
        page.locator("[data-test=\"password\"]").press("Enter");

        assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");
    }

    @AfterMethod
    public void afterTest() {
        //close the browser
        browser.close();
        //close the server
        playwright.close();
    }

}

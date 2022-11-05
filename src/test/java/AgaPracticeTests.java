import com.microsoft.playwright.*;

import java.nio.file.Paths;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

//TODO Try with chrome-beta
//DONE Try tracing with chunk
//DONE Run the test with mvn command - mvn exec:java -D"exec.mainClass"="AgaPracticeTests" (quotes on Windows!)

public class AgaPracticeTests {

    Playwright playwright;
    Browser browser;
    Page page;
    BrowserType.LaunchOptions lp;
    BrowserContext context;

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
    public void howToUseSelectors() {
        //The use of ElementHandle class is discouraged, better to use Locator class

        //Locator represents element (or elements) which matches the pattern
        Locator naviBarElements = page.locator("xpath=//ul[@class='dropdown__menu']/li/a");
        System.out.println("Number of elements with this locator: " + naviBarElements.count());
        System.out.println("Text in all elements: " + naviBarElements.allTextContents().toString());
        //nth = n-ty (enty) element
        System.out.println("Text of the first element: " + naviBarElements.nth(0).textContent());
        System.out.println("Last element: " + naviBarElements.last());
        System.out.println("Href attribute in first element: " + naviBarElements.first().getAttribute("href"));
        System.out.println("Class attribute of the third element: " + naviBarElements.nth(2).getAttribute("class"));
        System.out.println("Text in the last element: " + naviBarElements.last().innerHTML());

        System.out.println("All visible text in the locator: " + naviBarElements.allInnerTexts());
        System.out.println("All text (also hidden) in the locator: " + naviBarElements.allTextContents());

        Locator element = page.locator("//a[@class='menu__link menu__link--sublist menu__link--sublist-caret menu__link--active']");
        //click on the elem
        element.click();
        //also click on the elem
        element.dispatchEvent("click");
        //call focus on the elem
        element.focus();
        //element.highlight();
        System.out.println("Elem page: " + element.page().title());
        element.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("src/test/resources/Screens/Element_test.png")));


    }

    @Test(enabled = false)
    public void whatIsBrowserContext() {
        //BrowserContext is new browser window in incognito mode, with separate cookies and cache
        //It's possible to have a few context browsers at the same time
        BrowserContext browserContext1 = browser.newContext();
        Page page1 = browserContext1.newPage();
        page1.navigate("https://www.saucedemo.com/");
        page1.locator("[data-test=\"username\"]").fill("page1");
        page1.waitForTimeout(3000);

        BrowserContext browserContext2 = browser.newContext();
        Page page2 = browserContext1.newPage();
        page2.navigate("https://www.saucedemo.com/");
        page2.locator("[data-test=\"username\"]").fill("page2");
        page2.waitForTimeout(3000);

        //good practices - first close the page then the context browser and at the end the browser
        page1.close();
        browserContext1.close();

        page2.close();
        browserContext2.close();
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
    public void howToUseTraceViewer(){
        //Creates new tab(??) with separate cookies and cache
        context = browser.newContext();

        // Start tracing before creating / navigating a page.
        //Screenshots = screen photos; Snapshots = recorded DOM structure and network activity; Sources = source code to include in env variables
        context.tracing().start(new Tracing.StartOptions()
                .setName("MyFirstRecord!!!!")
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false)
                .setTitle("Title for my first record!"));

        //here should be the test code or the test will be get from getSources method
        page = context.newPage();
        page.navigate("https://www.saucedemo.com/");
        page.locator("[data-test=\"username\"]").click();
        page.locator("[data-test=\"username\"]").fill("test");
        page.locator("[data-test=\"username\"]").press("Tab");
        page.locator("[data-test=\"password\"]").fill("test");
        page.locator("[data-test=\"username\"]").dblclick();
        page.locator("[data-test=\"username\"]").fill("standard_user");
        page.locator("[data-test=\"username\"]").press("Tab");
        page.locator("[data-test=\"password\"]").fill("secret_sauce");
        page.locator("[data-test=\"password\"]").press("Enter");

        assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");

        // Stop tracing and export it into a zip archive.
        // To open the zip file: https://trace.playwright.dev/
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("src/test/resources/TraceViewer/trace.zip")));

    }


    @Test(enabled = false)
    public void howToGenerateCodeAndDebugging(){
        //PWDEBUG = 1
        //page.pause();
        //mvn exec:java -e -D"exec.mainClass"="com.microsoft.playwright.CLI" -D"exec.args"="codegen https://www.saucedemo.com/"

        // Go to https://www.saucedemo.com/
        page.navigate("https://www.saucedemo.com/");
        // Click [data-test="username"]
        page.locator("[data-test=\"username\"]").click();
        // Fill [data-test="username"]
        page.locator("[data-test=\"username\"]").fill("test");
        page.pause();
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

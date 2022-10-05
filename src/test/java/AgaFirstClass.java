import com.microsoft.playwright.*;

import java.nio.file.Paths;

public class AgaFirstClass {
    public static void main(String[] args){
        try (Playwright playwright = Playwright.create()){
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
            Page page = browser.newPage();
            page.navigate("https://playwright.dev/java/docs/screenshots#full-page-screenshots");
            System.out.println(page.title());
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("src/test/resources/Screens/test.png"))
                    .setFullPage(true));

            page.locator("//code[contains(@class, 'codeBlockLines_e6Vv')]")
                    .nth(0)
                    .screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("src/test/resources/Screens/Element_test.png")));
        }
    }
}

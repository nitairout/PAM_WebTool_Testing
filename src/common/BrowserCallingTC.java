package common;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.Reporter;

public class BrowserCallingTC extends TestBase {

	protected void browserSelection() throws InterruptedException {
		try {
			if ("chrome".equalsIgnoreCase(Dto.getWebDriverObj())) {
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				prefs.put("profile.default_content_settings.cookies", 2);
				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("download.default_directory", Constant.DOWNLOAD_PATH + "\\");
				prefs.put("download.prompt_for_download", false);
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless","--window-size=1900, 1200");
				options.addArguments("--disable-extensions");
				options.addArguments("test-type");
				options.addArguments("start-maximized");
				options.addArguments("--js-flags=--expose-gc");
				options.addArguments("--enable-precise-memory-info");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-default-apps");
				options.addArguments("disable-infobars");
				options.addArguments("--no-sandbox");
				options.addArguments("--dns-prefetch-disable");
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--log-level=3");
				options.setExperimentalOption("prefs", prefs);
				System.setProperty("webdriver.chrome.silentOutput", "true");
				checkBrowserHeadless = options.toJson().get("goog:chromeOptions").toString().contains("headless");
				d = new ChromeDriver(options);
				d.manage().window().maximize();
				Reporter.log("Chrome Running");

			}else if ("edge".equalsIgnoreCase(Dto.getWebDriverObj())) {
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				prefs.put("profile.default_content_settings.cookies", 2);
				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("download.default_directory", Constant.DOWNLOAD_PATH + "\\");
				prefs.put("download.prompt_for_download", false);
				EdgeOptions options = new EdgeOptions();
				//options.addArguments("--headless","--window-size=1900, 1200");
				options.addArguments("--disable-extensions");
				options.addArguments("test-type");
				options.addArguments("start-maximized");
				options.addArguments("--js-flags=--expose-gc");
				options.addArguments("--enable-precise-memory-info");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-default-apps");
				options.addArguments("disable-infobars");
				options.addArguments("--no-sandbox");
				options.addArguments("--dns-prefetch-disable");
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--log-level=3");
				options.setExperimentalOption("prefs", prefs);
				System.setProperty("webdriver.chrome.silentOutput", "true");
				checkBrowserHeadless = options.toJson().get("ms:edgeOptions").toString().contains("headless");
				d = new EdgeDriver(options);
				d.manage().window().maximize();
				Reporter.log("Edge Browser Running");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
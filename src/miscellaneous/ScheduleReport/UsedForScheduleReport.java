package miscellaneous.ScheduleReport;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import common.Constant;
import common.TestBase;

public class UsedForScheduleReport extends TestBase{
protected void openBrowserForScheduleReport() throws Exception {
	/*
	 
	try {
			if(d!=null) {
				d.quit();
				Reporter.log("Closed browser which opend for other test.");
			}
			Thread.sleep(10000);
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.default_content_settings.cookies", 2);
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.default_directory", Constant.DOWNLOAD_PATH + "\\");
		prefs.put("download.prompt_for_download", false);
		ChromeOptions options = new ChromeOptions();
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
	//	options.addArguments("--ignore-certificate-errors");
      //  options.addArguments("--disable-infobars");
       // options.addArguments("--user-agent=YOUR_CUSTOM_USER_AGENT");
      //  options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("prefs", prefs);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		d = new ChromeDriver(options);
		d.manage().window().maximize();
		Reporter.log("Chrome Running for schedule report");
Thread.sleep(3000);
	
		
	}catch(Exception e) {
		throw e;
	}
	
	*/
}
}

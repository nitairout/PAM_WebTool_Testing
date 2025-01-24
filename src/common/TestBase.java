package common;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import util.ErrorUtil;

public class TestBase{
	public static String standard="";
	public static String userDefined="";
	public static boolean checkBrowserHeadless=false;
	public static Properties locators = null;
	public static Properties testData = null;
	public static WebDriver d = null;
	public static String path, output;
	public static WebElement div;
	public static long wait_Time;
	public Select sel;
	public static ValueDTO Dto = new ValueDTO();
	public static Date now = new Date();
	public static Random random = new Random();
	public static String currentDate = new SimpleDateFormat("ddMMyyyyhhmmss").format(now);
	String str = Dto.getURl();
	String str1[] = str.split("com");
	public String navurl = str1[0];
	String Homepage = navurl + "com/index.aspx";
	//Below variables are to get tool tip in the chart
	public static List<String> tooltip;
	public static List<WebElement> bars;
	double  start, finish, totalTime,LoadTime_Seconds;
	@Parameters({ "browser" })
	@BeforeSuite
	public void initialize() throws Throwable {
		try {
			Constant.APPLICATION_LOGS.debug("Starting the Resource Advisor suite");
			Constant.APPLICATION_LOGS.debug("Loading Object XPATHS");
			standard="standard";
			userDefined="userDefined";
			locators = new Properties();
			testData = new Properties();
			FileInputStream fp=null;
			FileInputStream fpdata = null;
			fp = new FileInputStream(System.getProperty("user.dir")+ "\\src\\config\\ObjectLocator.properties");
			fpdata = new FileInputStream(System.getProperty("user.dir")+ "\\src\\config\\TestData.properties");
			locators.load(fp);
			testData.load(fpdata);
			PropertyConfigurator.configure(System.getProperty("user.dir")+ "\\src\\log\\log4j.properties");
			Logger.getRootLogger().setLevel(Level.OFF);

			/*Extent report block*/
		} catch (Throwable t) {
			Constant.APPLICATION_LOGS.debug("Error in initialize() of TestBase");
			throw t;
		}
	}
	@BeforeTest
	public void launchBrowser() throws Exception {
		

	}

	@BeforeMethod  
	public void beforeMethod() throws Exception  
	{  
		try {
			BrowserCallingTC bc = new BrowserCallingTC();
			bc.browserSelection();
			
		}catch(Exception e) {
			throw e;
		}
	}  
	
	@AfterMethod
	public void getResult(ITestResult result) throws Exception
	{
		try {
			if(result.getStatus() == ITestResult.FAILURE) {
				takeScreenShot(result.getName()+"_Error");
				Thread.sleep(3000);
			}
			closebrowser();
		}catch(Exception e) {
		throw e;
		}
	}

	@AfterSuite
	public void closeAll() throws Exception {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			Runtime.getRuntime().exec("taskkill /F /IM iexplore.exe");
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
			//Runtime.getRuntime().exec("taskkill /F /IM cmd.exe");
		} catch (Exception e) {
			Constant.APPLICATION_LOGS.debug("Error in closeAll() of TestBase");
		throw e;
		}
	}

	// Method to check and accept alert
	public void checkAlert() {
		try {
			d.switchTo().alert().accept();
			Constant.APPLICATION_LOGS.debug("Alert accepted");
		} catch (Exception e) {
			Constant.APPLICATION_LOGS.debug("Error in checkAlert() of TestBase");
			throw e;
		}
	}


	// Explicit wait for Xpath
	public static WebElement explicitWait_Xpath(String name) {
		WebElement dynamicElement = null;
		try {
			dynamicElement = (new WebDriverWait(d, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By.xpath(name)));
		} catch (Exception t) {
			Constant.APPLICATION_LOGS.debug("Cannot find element " + name);
		}
		return dynamicElement;
	}

	// for explicit wait for id
	public WebElement explicitWait_Id(String id) {
		WebElement dynamicElement = null;
		try {
			dynamicElement = (new WebDriverWait(d, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By
					.id(id)));
			aJaxWait();
			Thread.sleep(300);
		} catch (Exception t) {
			Constant.APPLICATION_LOGS.debug("Cannot find element " + id);
		}
		return dynamicElement;
	}

	// Method to find element using CSS using Property File
	public static WebElement getWebElementActionCSS(String css) {
		WebElement x;
		try {
			Utility.moveTheElementBasedOnCSS(locators.getProperty(css));
			x = d.findElement(By.cssSelector(locators.getProperty(css)));
		} catch (Throwable t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find object with id key -- " + css);
			Reporter.log("Cannot find object with id key -- " + css);
		}
		return x;
	}

	// Method to find element using ID using Hard Coding
	public static WebElement getWebElementActionCSS_D(String css) {
		WebElement x;
		try {
			Utility.moveTheElementBasedOnCSS(css);
			x = d.findElement(By.cssSelector(css));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find object with css selector key -- " + css);
		}
		return x;
	}

	// Normal drop down
	public void selectNoramalDropDown(String selectdropdown,
			String select1stoption) {
		try {

			getWebElementXpath(selectdropdown).click();
			Constant.APPLICATION_LOGS.debug("Clicked on " + selectdropdown + " Input feild");
			getWebElementXpath(select1stoption).click();
			Thread.sleep(2000);
			Constant.APPLICATION_LOGS.debug("Selected 1st option from " + select1stoption + " drop down");
		} catch (Exception t) {
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.warn("The data in " + select1stoption + " dropdown is not present" + t.toString());
		}
	}

	// Ajax Drop down
	public void selectAjaxDropDown(String selectdropdown, String select1stoption) {
		try {
			getWebElementXpath(selectdropdown).click();
			Thread.sleep(6000);
			Constant.APPLICATION_LOGS.debug("Clicked on " + selectdropdown + " Input feild");
			getWebElementXpath(select1stoption).click();
			aJaxWait();
			Constant.APPLICATION_LOGS.debug("Selected 1st option from "	+ select1stoption + " drop down");
		} catch (Exception t) {
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.warn("The data in" + select1stoption + "dropdown is not present" + t.toString());
		}
	}

	// Method to find element using Name using Property File
	public static WebElement getWebElementName(String name) {
		WebElement x;
		try {
			x = d.findElement(By.name(locators.getProperty(name)));
		} catch (Exception t) {
			x = null;
			Constant.APPLICATION_LOGS.error("Cannot find object with name key -- " + name);
			ErrorUtil.addVerificationFailure(t);
		}
		return x;
	}

	// Method to find element using Name using Hard Coding
	public static WebElement getWebElementName_D(String name) {
		WebElement x;
		try {
			x = d.findElement(By.name(name));
		} catch (Exception t) {
			x = null;
			Constant.APPLICATION_LOGS.error("Cannot find object with name key -- " + name);
			ErrorUtil.addVerificationFailure(t);
		}
		return x;
	}

	// Explicit wait for name
	public WebElement explicitWait_Name(String name) {
		WebElement dynamicElement = null;
		try {
			dynamicElement = (new WebDriverWait(d, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By.name(locators.getProperty(name))));
		} catch (Exception t) {
			Constant.APPLICATION_LOGS.debug("Cannot find element " + locators.getProperty(name));
		}
		return dynamicElement;
	}

	// Method to find element using LinkText using Hard Coding
	public static WebElement getWebElementLinkText_D(String linkText) {
		WebElement x;
		try {
			x = d.findElement(By.linkText(linkText));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find object with LinkText key -- " + linkText);
		}
		return x;
	}

	// Method to find element using LinkText using Property File
	public static WebElement getWebElementLinkText(String linkText) {
		WebElement x;
		try {
			x = d.findElement(By.linkText(locators.getProperty(linkText)));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find object with LinkText key -- " + linkText);
		}
		return x;
	}

	// Method to find element using ID using Property File
	public static WebElement getWebElementID(String id) {
		WebElement x;
		try {
			x = d.findElement(By.id(locators.getProperty(id)));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find object with id key -- " + id);
		}
		return x;
	}
	
	// Method to find element using ID using Property File
	public static WebElement getWebElementActionID(String id) {
		WebElement x;
		try {
			WebElement tableElement = d.findElement(By.id(locators.getProperty(id)) );
			Actions teAction = new Actions(d);
			teAction.moveToElement( tableElement,1,1 ).perform();
			
			
			x = d.findElement(By.id(locators.getProperty(id)));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find object with id key -- " + id);
		}
		return x;
	}

	// Method to find element using ID using Property File
	public static WebElement getWebElementID_D(String id) {
		WebElement x;
		try {
			x = d.findElement(By.id(id));
		} catch (Exception t) {
			x = null;
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find object with id key -- " + id);
		}
		return x;
	}

	/**
	 * This verifyText() is used to check the availability of the values. It
	 * will accept one String parameter which will fetch the actual value from
	 * property file. If the parameter is not present in property file then it
	 * will consider it as the direct values.
	 * @throws Exception 
	 * */
	public static void verifyText(String toBeVerifyValue) {
		String pattern = "";
		try {
			pattern = testData.getProperty(toBeVerifyValue);
			if (pattern == null)
				pattern = locators.getProperty(toBeVerifyValue);
			if (pattern == null)
				pattern = toBeVerifyValue;
			Constant.APPLICATION_LOGS.debug("Checking " + pattern);
			Assert.assertTrue(d.getPageSource().contains(pattern.trim()));

		} catch (Exception t) {
			throw t;
		}
	}

	// Method to compare two words
	public static void verifyText(String expected, String actual) {
		try {
			Constant.APPLICATION_LOGS.debug("Actual=" + actual+"::Expected=" + expected);
			Assert.assertEquals(expected, actual);
		} catch (Exception t) {
			throw t;
		}
	}

	public static void verifyTextDirect(String text) {
		try {
			Constant.APPLICATION_LOGS.debug("Checking " + text);
			Assert.assertTrue(d.getPageSource().contains(text));
		} catch (Exception t) {
			throw t;
		}
	}

	// For explicit wait for css
	public static WebElement explicitWait_CSS(String name) {
		WebElement dynamicElement = null;
		try {
			dynamicElement = (new WebDriverWait(d, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(name)));
		} catch (Throwable t) {
			Constant.APPLICATION_LOGS.debug("Cannot find element " + name);
		}
		return dynamicElement;
	}

	// Taking Screenshots
	public static void takeScreenShot(String fileName) {
		try {
			//Reporter.log("fileName"+d.manage().window().getSize());
			java.util.Date date = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss");
			String timeStamp = sdf.format(date).replaceAll(":", "_");
			
			File scrFile = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+ "\\Screenshots" + "\\" + fileName + ".jpg"));
			//FileUtils.copyFile(scrFile, new File("C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\PAM_Bin\\ScreenShot\\Schedule\\" + timeStamp+"_"+fileName + ".jpg"));
			
		} catch (Exception t) {
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Error in taking screenshot"	+ t.getMessage());
		}
	}


	public static void reNameFile(String file1, String file2) {
		try {
			File oldfile = new File(file1);
			File newfile = new File(file2);
			if (oldfile.exists()) {
				oldfile.renameTo(newfile);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// true- present
	// false - not present
	public static boolean isElementPresent(String element_xpath) {
		int count = d.findElements(By.xpath(locators.getProperty(element_xpath))).size();
		if (count == 0)
			return false;
		else
			return true;
	}

	// true- present
	// false - not present
	public static boolean isElementPresent_D(String element_xpath) {
		int count = d.findElements(By.xpath((element_xpath))).size();
		if (count == 0)
			return false;
		else
			return true;
	}

	// true- present
	// false - not present
	public static boolean isElementPresentCSS(String element_CSS) {
		int count = d.findElements(By.cssSelector(locators.getProperty(element_CSS))).size();
		if (count == 0)
			return false;
		else
			return true;
	}

	// true- present
	// false - not present
	public static boolean isElementPresentCSS_D(String element_CSS) {
		int count = d.findElements(By.cssSelector(element_CSS)).size();
		if (count == 0)
			return false;
		else
			return true;
	}

	// Ajax Wait
	public static void aJaxWait() {
		try {
			WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(20));
			Thread.sleep(1000);

			if(isElementPresent_D("//*[contains(@class,'loading-element')]")==true)
			{
				wait.until(ExpectedConditions.invisibilityOfAllElements(d.findElements(By.xpath("//*[contains(@class,'loading-element')]"))));
			}
			else if (d.findElements(By.xpath("//*[contains(@id,'ddlSource_LoadingDiv')]")).size() != 0
					&& d.findElement(By.xpath("//*[contains(@id,'ddlSource_LoadingDiv')]")).isDisplayed() == true) 
			{

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@id,'ddlSource_LoadingDiv')]")));
			}

			else if  (d.findElements(By.cssSelector(".flip-preloader")).size() != 0 && d.findElement(By.cssSelector(".flip-preloader")).isDisplayed() == true) 
			{

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".flip-preloader")));
			}

			else
			{
			//	Reporter.log("There is no ajaxwait will work for it");
			}
		} catch (Exception t) {
			Reporter.log("Page is taking long time to load, Kindly check manually");
		}
	}


	/*************** Application Specific Functions ****************/


	// PAM functions
	public void goToAnalysisPage() throws Exception {
		try {
			clickBasedOnAction("//span[contains(text(),'Analyze')]");
			Thread.sleep(5000);
			getWebElementActionXpath_D("(//li[@class='menu-first-li']//a[contains(text(),'Performance Analytics')])[1]").click();
			Thread.sleep(10000);
			Reporter.log("Navigation to PAM page tried 1st time.");
		} catch (Exception e) {
		}finally{
			if(d.findElements(By.xpath("//*[@id='trend-card']/img")).size()==0 || d.findElements(By.xpath("//*[@id='lp-card']/img")).size()==0) {
				d.navigate().to(navurl +"com/IntervalAnalytics/Interval.aspx#/cards");
				Thread.sleep(10000);
				Reporter.log("Navigation did not work properly it seems. tried 2nd time.");
			}
		}
	}
	
	//*[@id="navbarCollapse"]/ul/li[6]/div[1]/a/span
	
	
	
	// PAM functions
		public void gotoSetupHierarchyPage() throws Exception {
			try {
			clickCssOnAction("#navbarCollapse > ul > li:nth-child(6) > div.rmRootLink > a > span");
			clickBasedOnAction("//a[normalize-space()='Hierarchy Setup']");
			aJaxWait();
			Reporter.log("Navigated to setup hierachy page");
			Thread.sleep(5000);
			} catch (Exception e) {
				throw e;
			}finally{
				if(!d.getCurrentUrl().endsWith("setupHierarchy")) {
				d.navigate().to(navurl +"com/IntervalAnalytics/Interval.aspx#/setupHierarchy");
				Thread.sleep(10000);
				Reporter.log("Navigation did not work properly it seems.");
			}
			}
		}
		public void gotoManageTagPage() throws Exception {
			try {
			clickCssOnAction("#navbarCollapse > ul > li:nth-child(6) > div.rmRootLink > a > span");
			clickBasedOnAction("//a[normalize-space()='Manage Tags']");
			aJaxWait();
			Reporter.log("Navigated to manage tag page");
			Thread.sleep(5000);
			} catch (Exception e) {
				throw e;
			}finally{
				if(!d.getCurrentUrl().endsWith("tags/apply")) {
				d.navigate().to(navurl +"com/IntervalAnalytics/Interval.aspx#/tags/apply");
				Thread.sleep(10000);
				Reporter.log("Navigation did not work properly it seems.");
			}
			}
		}
		
		
		
		
	/*
	 * // PAM functions public void goToRAAnalysisPage() {
	 * 
	 * d.navigate().to(
	 * "https://tk3.dev.summitenergy.com/IntervalAnalytics/Interval.aspx#/cards");
	 * aJaxWait(); explicitWait_Xpath("//*[@id='trend-card']/img");
	 * Constant.APPLICATION_LOGS.debug("Navigated to PAM Cards page"); }
	 */

	

	public String getBrowserName() {
		return Dto.getWebDriverObj().toLowerCase();
	}


	


	public static boolean waitForPageLoad() {
		Wait<WebDriver> wait = new WebDriverWait(d, Duration.ofSeconds(60));
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				try {
					return ((Long) ((JavascriptExecutor) d).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) d)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}


	public void pageloadstart()
	{
		start = System.currentTimeMillis();

	}


	//Get time after loading page
	public void pageloadstop(String name)
	{
		finish = System.currentTimeMillis();
		totalTime = finish - start;
		LoadTime_Seconds = totalTime / 1000;
		Reporter.log("Time taken to load  : :  " + name +" ::::" + LoadTime_Seconds+ " " +" seconds");
	}


	/**
	 * The clickOnSite() is used for clicking on site. It will take 2 parameter .1st:to be filter. 
	 * 2nd: locator of the site to be click
	 * To select multiple site with multiple filter option we have to pass the parameters with separation of |
	 * Example to call this method clickOnSite(filtervalue1|filtervalue2|filtervalue3,sitelocator1|sitelocator2|sitelocator3);
	 * Note:The filtervalue1 and the sitelocator1 will be respectively and the number of filter value and site locator will be same
	 * 
	 * */
	public void clickOnSite(String toBeFilterSites,String xpathsToSelect) throws Exception{
		try{

			String filterArray[]=toBeFilterSites.split("\\|");
			String xpathArray[]=xpathsToSelect.split("\\|");
			int filterLength=filterArray.length;
			int xpathLength=xpathArray.length;
			if(filterLength!=xpathLength)
				throw new Exception("Number of search sites and locators are not same.");
			for(int i=0;i<filterLength;i++){
				explicitWait_Xpath(locators.getProperty("sitefilter")).clear();
				explicitWait_Xpath(locators.getProperty("sitefilter")).sendKeys(filterArray[i]);
				Thread.sleep(300);
				explicitWait_Xpath(locators.getProperty(xpathArray[i])).click();

			}
			aJaxWait();
			Thread.sleep(1000);
		}catch(Throwable e){
			throw e;
		}
	}


	
	/**
	 * This method is used to select the measurements based the searchstrings in
	 * configure tab
	 * 
	 * @throws Throwable
	 * 
	 */
	public void SelectCalcMesurementconfigurationtab(String searchstrings)
			throws Throwable {

		try {

			explicitWait_Xpath(locators.getProperty("FilterCalcMesurementconfigurationtab")).clear();
			Thread.sleep(200);
			getWebElementXpath("FilterCalcMesurementconfigurationtab").sendKeys(searchstrings);
			aJaxWait();
			Thread.sleep(1000);
			
			List<WebElement> measurementList =d.findElements(By.xpath("//div[contains(@class,'collapse in')]/commodity-def-location/div"));
				for(int i=1;i<=measurementList.size();i++){
					if(getWebElementXpath_D("//div[contains(@class,'collapse in')]/commodity-def-location["+i+"]/div/div/h4").getText().equalsIgnoreCase(searchstrings)){
						Thread.sleep(2000);
						getWebElementXpath_D("//div[contains(@class,'collapse in')]/commodity-def-location["+i+"]/div/div/span/i").click();
						Thread.sleep(2000);
						break;
					}
				}
			
			
			
			
			
			
			
			
			
			
			
			//explicitWait_Xpath(locators.getProperty("configMeasurementIcon")).click();
			aJaxWait();
			Thread.sleep(1000);

		} catch (Throwable t) {
			t.printStackTrace();
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find  -- " + searchstrings);
			throw t;
		}
	}


	/**
	 * This method is used to select the measurements based the searchstrings
	 *  @throws Throwable
	 * 
	 */
	public void SelectCalculatedMesurement(String searchstrings)throws Throwable {

		WebElement dialog;

		try {

			//Verify Calculated Measurement Filter box
			explicitWait_Xpath(locators.getProperty("FilterCalcMesurement"));
			//Clear the Values from Calculated Measurement Filter box
			getWebElementXpath("FilterCalcMesurement").clear();
			Thread.sleep(300);
			//Enter the Measurement name in Calculated Measurement Filter box
			getWebElementXpath("FilterCalcMesurement").sendKeys(searchstrings);
			Thread.sleep(500);
			//Select on the Measurement 
			explicitWait_Xpath(locators.getProperty("SelectMesurement"));
			dialog = getWebElementXpath("SelectMesurement");
			dialog.click();			
			aJaxWait();
			Thread.sleep(1000);

		} catch (Throwable t) {
			t.printStackTrace();
			ErrorUtil.addVerificationFailure(t);
			Constant.APPLICATION_LOGS.error("Cannot find  -- " + searchstrings);
			throw t;
		}

	}

	public void downloadFiles() throws Throwable{

		if("Firefox".equalsIgnoreCase(Dto.getWebDriverObj())){

			Robot robotObj = new Robot();	
			// Press arrow down key to select save radio button.
			Thread.sleep(1000);	
			robotObj.keyPress(KeyEvent.VK_DOWN);
			robotObj.keyRelease(KeyEvent.VK_DOWN);
			// Press tab key three time to navigate to Save button. 
			for(int i=0;i<2;i++)
			{
				Thread.sleep(1000);	
				robotObj.keyPress(KeyEvent.VK_TAB);
			}

			// Press down Save button.
			Thread.sleep(500);	
			robotObj.keyPress(KeyEvent.VK_ENTER);
			// Release up Save button, download process start.
			Thread.sleep(500);
			robotObj.keyRelease(KeyEvent.VK_ENTER);
			Reporter.log("File is Downloaded Successfully from Firefox");

		} else if(("ie").equalsIgnoreCase(Dto.getWebDriverObj())){

			Robot robot = new Robot();
			robot.setAutoDelay(250);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(1000);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_S);
			Reporter.log("File is Downloaded Successfully from IE");

		} else {
			Thread.sleep(500);
			Reporter.log("File is Downloaded Successfully from Chrome");

		}

		aJaxWait();
		Thread.sleep(5000);

	}
	public void gotoDashBoardHome() throws Exception{
		try{
			d.navigate().to(navurl +"com/index.aspx");
		aJaxWait();
		Thread.sleep(15000);
		}catch(Exception e){
			throw e;
		}
	}
	
	
	/**
	 *The below method is to push the widget to the respective dashboard.
	 *the Input for this method is "Dashboard name".
	 **Specify exact name present in the application
	 *@param DashboardName
	 *@throws Throwable
	 **/
	public void pushwidgetForSubSite(String DashboardName,String dbType) throws Throwable
	{		
		try
		{
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//*[@id='wrenchButton']/i").click();
			Thread.sleep(5000);
			explicitWait_Xpath("//*[@id='se-wrench-menu']/div[2]/div");
			List<WebElement> op = d.findElements(By.xpath("//*[@id='se-wrench-menu']/div[2]/div"));
			String listXpath="";
			//Click on save to dashboard
			for(int i=1;i<=op.size();i++){
				listXpath="//*[@id='se-wrench-menu']/div[2]/div["+i+"]/div/div[2]/div";
				if(getWebElementActionXpath_D(listXpath).getText().equalsIgnoreCase("Save to Dashboard")){
					clickBasedOnAction(listXpath);
					break;
				}
			}
			Thread.sleep(2000);
			//click on type of dashboard
			List<WebElement> dbList = d.findElements(By.xpath("//*[@id='dv-flyout-main-panel']/div[2]/div/div/ul/div/perfect-scrollbar/div/div[1]/div"));
			for(int i=1;i<=dbList.size();i++){
				listXpath="//*[@id='dv-flyout-main-panel']/div[2]/div/div/ul/div/perfect-scrollbar/div/div[1]/div["+i+"]/li/a/span[1]";
				if(getWebElementActionXpath_D(listXpath).getText().equalsIgnoreCase(dbType)){
					clickBasedOnAction(listXpath);
					break;
				}
			}
			aJaxWait();
			Thread.sleep(5000);
			clickBasedOnAction("//*[@id='dv-flyout-main-panel']/div[2]/div/div/div/div/input");
			Thread.sleep(5000);
			getWebElementActionXpath_D("//*[@id='dv-flyout-main-panel']/div[2]/div/div/div/div/input").sendKeys(DashboardName);
			Thread.sleep(15000);
			//click on ok to push the dashboard
			clickBasedOnAction("//*[@id='dv-flyout-main-panel']/div[2]/ra-dashboard-list/div/ul/div/perfect-scrollbar/div/div[1]/div/li/input");
			Thread.sleep(8000);
			//click on on for sub site
			getWebElementActionXpath_D("//html/body/div[2]/div/div/form/div[2]/div[1]").click();
			d.navigate().refresh();
			aJaxWait();
			Thread.sleep(5000);

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}

	}

		

			
			
			
			
			
			
			public void checkTheRawDataMode() throws Exception{
				try{
					//Click on to show setting link
					clickBasedOnAction("//*[@id='ddlraUser']/div/div/div[2]/i");
					//click on setting link
					clickBasedOnAction("//*[@id='ddlraUser']/div/div[2]/div/ul/li[2]/a/span");
					Thread.sleep(8000);
					//click on Interval Data Options tab
					clickBasedOnAction("//html/body/app-root/div/ess-base/div[1]/div[1]/div/div/div[2]/ra-user-preferences/div[2]/div/div[2]/ra-tabs/div[1]/ul/li[3]/a");
				    WebElement rawDataRadio = d.findElement(By.xpath("//*[@id='up-int-data']/div[1]/div[1]/ra-radiobutton-list/form/div[2]/label/span[2]"));
				    WebElement correctedDataRadio =  d.findElement(By.xpath("//*[@id='up-int-data']/div[1]/div[1]/ra-radiobutton-list/form/div[1]/label/span[2]"));
				    //Checking if the raw data mode is selected
				 // if(correctedDataRadio.isSelected()){
					  rawDataRadio.click();
					  //click on save
					  clickBasedOnAction("//*[@id='up-int-data']/div[2]/input[1]");
					  Thread.sleep(8000);
				//  }
				}catch(Exception e){
					Reporter.log("There is some issue while checking the raw data mode!!");
					throw e;
				}
			}
			
			
						
			
					
			
///////////////////////////////////New pam methods//////////////////////////////////////////////////
			
			
			// Tool tip verification
			public static List<String> getBarToolTip() throws Throwable {

				try {	
					tooltip = new ArrayList<String>();
					bars = d.findElements(By.xpath(locators.getProperty("toolTipBar")));
					Thread.sleep(1000);
					int count = 0;
					String tip =null;
					Actions a = new Actions(d);
					for (WebElement bar : bars) {
						a.moveToElement(bar,1,1).moveToElement(bar,1,1).build().perform();
						Thread.sleep(500);
						if (getWebElementXpath("tooltiptext") != null) {
							tip = getWebElementXpath("tooltiptext").getText();
							tooltip.add(tip);
							Thread.sleep(500);
						}
						count = count + 1;
					}

				} catch (Throwable t) {
					throw t;
				}
				return tooltip;
			}
			// Tool tip verification
			public static ArrayList<String> scatterPlotToolTip() throws Throwable {
				ArrayList<String> scToolTip=null;
				List<WebElement> dots=null;
				try {	
					scToolTip = new ArrayList<String>();
					dots = d.findElements(By.xpath(locators.getProperty("toolTipBar")));
					for (WebElement bar : dots) {
						Actions a = new Actions(d);
						a.moveToElement(bar,1,1).build().perform();
						Thread.sleep(1000);
						if (explicitWait_Xpath(locators.getProperty("tooltiptext")) != null) {
							WebElement tip = explicitWait_Xpath(locators.getProperty("tooltiptext"));
							scToolTip.add(tip.getText());
							Thread.sleep(1000);
						}
					}

				} catch (Throwable t) {
					t.printStackTrace();
					throw t;
				}
				return scToolTip;
			}

			//Get Tooltip for the variance test case
			public static List<String> varianceBarChartToolTip() throws Throwable {
				try {
					tooltip = new ArrayList<String>();
					bars = d.findElements(By.xpath(locators.getProperty("variancetooltip")));
					Thread.sleep(1000);
					String tip =null;
					Actions a = new Actions(d);
					for (WebElement bar : bars) {
						a.moveToElement(bar).moveToElement(bar).build().perform();
						Thread.sleep(500);
						if (explicitWait_Xpath(locators.getProperty("tooltiptext")) != null) {
							tip = getWebElementXpath("tooltiptext").getText();
							tooltip.add(tip);
							Thread.sleep(300);
						}
					}
				} catch (Throwable t) {
					throw t;
				}
				return tooltip;
			}

			
			/**
			 *The below method is to Test Indexby 
					@@param Index

			 *@throws Throwable
			 **/
			public void indexBySelectMatrics(String matrics) throws Throwable
			{		
				try
				{
					Utility.moveTheElement(locators.getProperty("OverlayExpander"));
					if(d.findElements(By.xpath(locators.getProperty("IndexBy")+"[contains(@class,'k-panelbar-expand')]")).size()>0) {
						getWebElementActionXpath("IndexBy").click();
						Thread.sleep(2000);	
					}	
					//moving cursor to make the element display
					Utility.moveTheElement(locators.getProperty("OverlayExpander"));
					getWebElementActionXpath("IndexSiteMetric").click();
					Thread.sleep(2000);
					clickUsingJavascriptExecuter("//*[normalize-space()='" + matrics + "']");
					aJaxWait();
					Thread.sleep(3000);

				} catch (Throwable t) {
					throw t;
				}
			}
			
			public void dataBreakDownSubMeter(String submeter) throws Throwable
			{		
				try
				{
					Utility.moveTheElement(locators.getProperty("OverlayExpander"));
					
					if(d.findElements(By.xpath("//span[normalize-space()='Data Breakdown']/parent::kendo-panelbar-item[@aria-expanded='false']")).size()>0)
						getWebElementActionXpath("DataBreakDownExpander").click();
					Thread.sleep(2000);
					getWebElementActionXpath("BreakDownType").click();
					Thread.sleep(1000);
					getWebElementActionXpath_D("//span[normalize-space()='"+submeter+"']").click();
					Thread.sleep(1000);
					printLog("Selected Submeters from DataBreakdown");
					
				} catch (Throwable t) {
					throw t;
				}
			}
			
			public void dataBreakDownSiteSchedule(String siteSchedule,String template) throws Throwable
			{		
				try
				{
					Utility.moveTheScrollToThebottomOnRquest("800");
					
					if(d.findElements(By.xpath("//span[normalize-space()='Data Breakdown']/parent::kendo-panelbar-item[@aria-expanded='false']")).size()>0)
						getWebElementActionXpath("DataBreakDownExpander").click();
					Thread.sleep(2000);
					getWebElementActionXpath("BreakDownType").click();
					Thread.sleep(1000);
					getWebElementActionXpath_D("//span[normalize-space()='"+siteSchedule+"']").click();
					Thread.sleep(1000);
					
					getWebElementActionXpath_D("//se-breakdown/descendant::kendo-dropdownlist[2]/descendant::span[2]").click();
					Thread.sleep(1000);
					getWebElementActionXpath_D("//span[normalize-space()='"+template+"']").click();
					Thread.sleep(1000);
					
					printLog("Selected site schedule from DataBreakdown");
					
					Utility.moveTheScrollToTheTopOnRquest("800");
				} catch (Throwable t) {
					throw t;
				}
			}
			
			public void indexBySelectIndex(String index) throws Throwable
			{
				try
				{
					//Utility.moveTheElement(locators.getProperty("OverlayExpander"));
					//if(d.findElements(By.xpath("//span[normalize-space()='Index By']/parent::kendo-panelbar-item[@aria-expanded='false']")).size()>0)
					if(d.findElements(By.xpath(locators.getProperty("IndexBy")+"[contains(@class,'k-panelbar-expand')]")).size()>0) {
						getWebElementActionXpath("IndexBy").click();
						Thread.sleep(2000);	
					}
					getWebElementActionXpath("WeatherAndDailyIndex").click();
					Thread.sleep(2000);
					getWebElementActionXpath_D("//li[normalize-space()='"+index+"']").click();
					Thread.sleep(6000);

				} catch (Throwable t) {
					throw t;
				}
			}
			
			/**
			 *Hide the enlarged screen 
			 * */
			public void hideEenlargeBottomTabsPanel() throws Exception {
				try {
					Utility.moveTheScrollToTheTop();
					Thread.sleep(1000);
					WebElement enlargeIcon = d.findElement(By.xpath(locators.getProperty("ReducePanelDownArrow")));
					Actions actions = new Actions(d); 
					//actions.moveToElement(enlargeIcon,1,1).perform();
					actions.moveToElement(enlargeIcon,1,1).build().perform();  
					actions.moveToElement(enlargeIcon,1,1).click().build().perform();  
					//actions.click().build().perform(); 
					Thread.sleep(1000);
				}catch(Exception e) {
					throw e;
				}
			}
			/**
			 *Enlarge the screen 
			 * */
			public void enlargeBottomTabsPanel() throws Exception {
				try {
					Thread.sleep(1000);
					WebElement enlargeIcon = d.findElement(By.xpath(locators.getProperty("EnlargePanelUpArrow")));
					Actions actions = new Actions(d); 
					//actions.moveToElement(enlargeIcon).perform();
					actions.moveToElement(enlargeIcon,1,1).build().perform(); 
					actions.moveToElement(enlargeIcon,1,1).click().build().perform(); 
					//actions.click().build().perform(); 
					Thread.sleep(1000);
				}catch(Exception e) {
					throw e;
				}
			}
			
			
			/**
			 *Hide the option and location panel
			 * */
			public void hideOptionLocationPanel() throws Exception{
				try{
					Utility.moveTheScrollToTheTop();
					Thread.sleep(2000);
					getWebElementActionXpath("HideOptionsPanelArrow").click();
					getWebElementActionXpath("HideLocationsPanelArrow").click();
					Thread.sleep(2000);
				}catch(Exception e){
					 
					throw e;
				}
			}
			/**
			 *Show the option and location panel
			 * */
			public void showOptionLocationPanel() throws Exception{
				try{
					Utility.moveTheScrollToTheTop();
					//*[@id="locationPanelResizableContainer"]/div[1]/div[2]/i
					getWebElementActionXpath("ShowOptionsPanelArrow").click();
					getWebElementActionXpath("ShowLocationsPanelArrow").click();
					Thread.sleep(2000);
					//*[@id='detailTabs']/div/div/se-panel/div[3]/div[1]/div[2]/i
				}catch(Exception e){
					 
					throw e;
				}
			}
			
			
			public void card2Card(String cardName) throws Throwable
			{		
				try

				{
					Utility.moveTheScrollToTheTop();
					//getWebElementActionXpath_D("//*[@id='c2cButton']/i").click();//div[contains(text(),'Calendar Load Profile Analysis')]
					getWebElementActionXpath_D("//i[@aria-label='Card to card navigation']").click();
					Thread.sleep(1000);
					switch(cardName) {
						case "Load Profile Analysis" :
							getWebElementActionXpath_D("//div[normalize-space()='Load Profile Analysis']").click();
							break;
						case "Trend Analysis" :
							getWebElementActionXpath_D("//div[normalize-space()='Trend Analysis']").click();
							break;
						case "Comparison Analysis" :
							getWebElementActionXpath_D("//div[normalize-space()='Comparison Analysis']").click();
							break;
						case "Calendar Load Profile Analysis" :
							getWebElementActionXpath_D("//div[normalize-space()='Calendar Load Profile Analysis']").click();
							break;
						case "Scatter Plot Analysis" :
							getWebElementActionXpath_D("//div[normalize-space()='Scatter Plot Analysis']").click();
							break;
						default:
							printLog("No cards are selected!!");
							break;
					}
					Thread.sleep(5000);
					
				} catch (Throwable t) {
					throw t;
				}

			}	

			public static void clickBasedOnAction(String xpath) throws Exception {
				WebElement xpathToCLick = null;
				Actions actions = new Actions(d); 
				try {
					xpathToCLick = d.findElement(By.xpath(xpath)); 
					actions.moveToElement(xpathToCLick,1,1).click().build().perform(); 
				} catch (Exception t) {
					throw t;
				}
			}
			public static void closebrowser() throws Exception{
				try{
			     d.quit();
			     Reporter.log("Browser closed successfully");
			     Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			     Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
				}catch(Exception e){
					throw e;
			}
		} 
			public static void clickCssOnAction(String cssSelector) throws Exception {
				WebElement cssToCLick = null;
				Actions actions = new Actions(d); 
				try {
					cssToCLick = d.findElement(By.cssSelector(cssSelector)); 
					actions.moveToElement(cssToCLick,1,1).click().build().perform(); 
				} catch (Exception t) {
					throw t;
				}
	}
			
			// Method to find element using Xpath using Hard Coding
			public static WebElement getWebElementXpath_D(String xpath) {
				WebElement x;
				try {
					x = d.findElement(By.xpath(xpath));
				} catch (Exception t) {
					x = null;
				}
				return x;
			}
			
			// Method to find element using CSS using Property File
			public static WebElement getWebElementCSS(String css) {
				WebElement x;
				try {
					x = d.findElement(By.cssSelector(locators.getProperty(css)));
				} catch (Exception e) {
					x = null;
				}
				return x;
			}

			// Method to find element using ID using Hard Coding
			public static WebElement getWebElementCSS_D(String css) {
				WebElement x;
				try {
					x = d.findElement(By.cssSelector(css));
				} catch (Exception e) {
					x = null;
				}
				return x;
			}

			
			// Method to find element using Xpath & Move element
				public static WebElement getWebElementActionXpath_D(String xpath) throws Exception {
					WebElement x;
					try {
						Utility.moveTheElement(xpath);
						x = d.findElement(By.xpath(xpath));
					} catch (Exception t) {
						x = null;
					}
					return x;
				}
			
			// Method to find element using Xpath using property file
			public static WebElement getWebElementXpath(String xpath) {
				WebElement x;
				try {
					x = d.findElement(By.xpath(locators.getProperty(xpath)));
				} catch (Exception e) {
					x = null;
				}
				return x;
			}

			// Method to find element using Xpath & Move action
			public static WebElement getWebElementActionXpath(String xpath) {
				WebElement x;
				try {
					Utility.moveTheElement(locators.getProperty(xpath));
					x = d.findElement(By.xpath(locators.getProperty(xpath)));
				} catch (Exception e) {
					x = null;
				}
				return x;
			}
			
			
			
	public static void printLog(String logMessage) {
		Reporter.log(logMessage);
	}
	public static void printLog(String logMessage,String customMessage) {
		Reporter.log(logMessage+" : "+customMessage);
	}
	
	public void goToPAMCard(String card) throws Exception{
		try {
		switch (card) {
		case "ComparisonAnalysisCard":
			getWebElementActionXpath_D("//*[@id='comp-card']/img").click();
			printLog("Inside Comparison Analysis Card");
			break;
		case "TrendAnalysisCard":
			getWebElementActionXpath_D("//*[@id='trend-card']/img").click();
			printLog("Inside Trend Analysis Card");
			break;
		case "LoadProfileAnalysisCard":
			getWebElementActionXpath_D("//*[@id='lp-card']/img").click();
			printLog("Inside LoadProfile Analysis Card");
			break;
		case "CalendarAnalysisCard":
			getWebElementActionXpath_D("//*[@id='calend-card']/img").click();
			printLog("Inside Calendar Analysis Card");
			break;
		case "ScatterPlotCard":
			Utility.moveTheScrollToThebottomOnRquest("800");
			getWebElementActionXpath_D("//*[@id='scatter-card']/img").click();
			printLog("Inside Scatter Plot Card");
			break;
		default:
			printLog("Card not found");
		}
		aJaxWait();
		Thread.sleep(5000);
		Utility.moveTheScrollToTheTop();
		}catch(Exception e) {
			throw e;
		}
	}
	/*Search items in location filter textbox*/
	public void searchSiteInLocationList(String searchName) throws Throwable {
		try {	
			Utility.moveTheScrollToTheTop();
			if(getWebElementActionXpath("SiteFilterTextBox").isDisplayed()) {
				getWebElementXpath("SiteFilterTextBox").clear();
				//getWebElementXpath("SiteFilterTextBox").sendKeys("\""+searchName+"\"");
				getWebElementXpath("SiteFilterTextBox").sendKeys(searchName);
				getWebElementXpath("SiteFilterTextBox").sendKeys(Keys.RETURN);
				Thread.sleep(2000);
				printLog("Filtered site name as "+searchName);
			}else 
				Assert.fail("Filter text box is not visible!!");
			
		} catch (Throwable t) {
			throw t;
		}
	}
	
	
	public void refreshToLoadTheChart() throws Exception{
		try{
			Utility.moveTheScrollToTheTop();
		/*	getWebElementActionXpath_D("//div[contains(text(),'Refresh')]").click();
			aJaxWait();
			Thread.sleep(2000);
			Assert.assertFalse(d.getPageSource().contains("Unknown error occurred"));
			Thread.sleep(2000);
			*/
			clickUsingJavascriptExecuter("//button[normalize-space()='Refresh']");
		        aJaxWait();
				Thread.sleep(2000);
		}catch(Exception e){
			printLog("May be refresh button is not showing or there is some error while loading the chart");
			throw e;
		}
		printLog("Clicked on refresh to load the chart!!");
	}
	
	
	public void addFixedDateRange(String startDate, String endDate) throws Exception {
		try {
			/*getWebElementActionXpath_D("//span[normalize-space()='Fixed']").click();
			Thread.sleep(1000);
			getWebElementActionXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").clear();
			Thread.sleep(1000);
			getWebElementXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").sendKeys(startDate);
			Thread.sleep(1000);
			getWebElementXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").sendKeys(Keys.TAB);
			getWebElementActionXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").clear();
			Thread.sleep(1000);
			getWebElementXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").sendKeys(startDate);
			Thread.sleep(2000);
			getWebElementXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").sendKeys(Keys.TAB);
			d.switchTo().activeElement().sendKeys(Keys.ENTER);
			//Utility.moveTheScrollToThebottomOnRquest("500");
			//getWebElementActionXpath_D("//button[normalize-space()='Apply']").click();	
			Thread.sleep(4000);*/
			getWebElementActionXpath_D("//span[normalize-space()='Fixed']").click();
			Thread.sleep(1000);
			getWebElementActionXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").click();
			Thread.sleep(1000);
			//pressing 2 times arrow to move to the month field
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").sendKeys(startDate);
			Thread.sleep(1000);
			//getWebElementXpath_D("//small[normalize-space()='Start Date']/following-sibling::kendo-datepicker//child::input").sendKeys(Keys.TAB);
			getWebElementActionXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").sendKeys(endDate);
			Thread.sleep(2000);
			getWebElementXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").sendKeys(Keys.TAB);
			d.switchTo().activeElement().sendKeys(Keys.ENTER);
			//Utility.moveTheScrollToThebottomOnRquest("500");
			//getWebElementActionXpath_D("//button[normalize-space()='Apply']").click();	
			Thread.sleep(4000);
			printLog("Added fixed date range as "+ startDate+" & "+endDate);	
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 *It change the date format
	 * */
	public String changeTheDateFormat(String format,LocalDate date){
		try{
		String returnDate="";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		returnDate=date.format(dateTimeFormatter);
	    return returnDate;
		}catch(Exception e){
			throw e;
		}
	}
	
	public void kabobMenuOptions(String menuOption) throws Throwable {
		try
		{
			Utility.moveTheScrollToTheTop();
			getWebElementXpath_D("//div[@id='titlebar']/div[@class='widget-header']//child::i[contains(@class,'se-menu-kabob clickable')]").click();
			Thread.sleep(2000);
			getWebElementXpath_D("//div[@id='se-wrench-menu']//child::div[contains(text(),'"+menuOption+"')]").click();
			Thread.sleep(5000);
			
		} catch (Throwable t) {
			throw t;
		}

	}
	
	public void fixedDateRangeForCLP(String month,String year) throws Throwable {
		try {
			
			//Click on the fixed date range icon
			//getWebElementActionXpath_D("//se-timeline-calendar[@title='Timeline']/div[@id='timelineCalendar']//span[normalize-space()='Fixed']/following-sibling::kendo-datepicker/child::button").click();
			getWebElementActionXpath_D("//span[normalize-space()='Fixed']/following-sibling::kendo-datepicker/child::button").click();
			
			String selectedYear=getWebElementXpath_D("//button[contains(@id,'kendo-calendarid')]/span").getText();
			//Click on year to select the year
			getWebElementActionXpath_D("//kendo-calendar[contains(@id,'kendo-calendarid')]//ul[@class='k-reset']/li[normalize-space()='"+year+"']").click();
			Thread.sleep(3000);
			String monthName=month.substring(0, 3);
			//click on passed month from the parameter
			getWebElementActionXpath_D("//kendo-calendar[contains(@id,'kendo-calendarid')]//td[@title='"+year+" "+monthName+"']//span[contains(text(),'"+monthName+"')]").click();
			
			
			/*
			if(!selectedYear.equalsIgnoreCase(year)) {
				//convert both the year in to int format
				int toBeselectYear=Integer.parseInt(year);//2019
				int alreadySelectedYear=Integer.parseInt(selectedYear);//2022
				int differenct=0;
				if(alreadySelectedYear>toBeselectYear) {
					differenct=alreadySelectedYear-toBeselectYear;
					//click the year arrow to show the year
					for(int i=1;i<=differenct;i++) {
						getWebElementActionXpath_D("//button[@class='btn btn-default btn-sm pull-left']").click();
						Thread.sleep(300);
					}
				}else if(alreadySelectedYear<toBeselectYear) {
					differenct=toBeselectYear-alreadySelectedYear;
					//click the year arrow to show the year
					for(int i=1;i<=differenct;i++) {
						getWebElementActionXpath_D("//button[@class='btn btn-default btn-sm pull-right']").click();
						Thread.sleep(300);
					}
				}
				Thread.sleep(2000);
				//select the month as parameter
				List<WebElement> yearsStartFrom=d.findElements(By.xpath("(//button[@class='btn btn-default'])"));
				for(int i=1;i<=yearsStartFrom.size();i++) {
					if(getWebElementXpath_D("(//button[@class='btn btn-default'])["+i+"]/span").getText().equalsIgnoreCase(month)) {
						getWebElementXpath_D("(//button[@class='btn btn-default'])["+i+"]/span").click();	
						break;
					}
				}
			}else {
				//select the month as parameter
				List<WebElement> yearsStartFrom=d.findElements(By.xpath("(//button[@class='btn btn-default'])"));
				for(int i=1;i<=yearsStartFrom.size();i++) {
					if(getWebElementXpath_D("(//button[@class='btn btn-default'])["+i+"]/span").getText().equalsIgnoreCase(month)) {
						getWebElementXpath_D("(//button[@class='btn btn-default'])["+i+"]/span").click();	
						break;
					}
				}
			}
			*/
			Thread.sleep(5000);
		} catch (Exception e) {
			throw e;
		}
	}

	public void fixedStartDate(String dateMonthYear,String card) throws Throwable {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate convertedDate = LocalDate.parse(dateMonthYear, formatter);
			String dateToConvert=changeTheDateFormat("MMMM-dd-yyyy",convertedDate);
			String month=dateToConvert.split("-")[0];
			String date=dateToConvert.split("-")[1];
			String year=dateToConvert.split("-")[2];
			
			//fixed date selected then the if block will execute
			if(d.findElements(By.xpath("//i[@class='fa fa-calendar']")).size()>1)
				getWebElementActionXpath_D("(//i[@class='fa fa-calendar'])[3]").click();
			else
				getWebElementActionXpath_D("(//i[@class='fa fa-calendar'])[1]").click();
			
			Thread.sleep(1000);
			if(!card.equalsIgnoreCase("CalendarAnalysisCard")) {
				getWebElementActionXpath_D("(//button[@class='btn btn-default btn-sm'])").click();
			}
			
			
			String selectedYear=getWebElementXpath_D("(//button[@class='btn btn-default btn-sm'])/strong").getText();
			if(!selectedYear.equalsIgnoreCase(year)) {
				//convert both the year in to int format
				int toBeSelectYear=Integer.parseInt(year);//2019
				int alreadySelectedYear=Integer.parseInt(selectedYear);//2022
				int differenct=0;
				if(alreadySelectedYear>toBeSelectYear) {
					differenct=alreadySelectedYear-toBeSelectYear;
					//click the year arrow to show the year
					for(int i=1;i<=differenct;i++) {
						getWebElementActionXpath_D("//button[@class='btn btn-default btn-sm pull-left']").click();
						Thread.sleep(300);
					}
				}else if(alreadySelectedYear<toBeSelectYear) {
					differenct=toBeSelectYear-alreadySelectedYear;
					//click the year arrow to show the year
					for(int i=1;i<=differenct;i++) {
						getWebElementActionXpath_D("//button[@class='btn btn-default btn-sm pull-right']").click();
						Thread.sleep(300);
					}
				}
				Thread.sleep(1000);
				//select the month as parameter
				List<WebElement> yearsStartFrom=d.findElements(By.xpath("(//button[@class='btn btn-default'])"));
				for(int i=1;i<=yearsStartFrom.size();i++) {
					if(getWebElementXpath_D("(//button[@class='btn btn-default'])["+i+"]/span").getText().equalsIgnoreCase(month)) {
						getWebElementXpath_D("(//button[@class='btn btn-default'])["+i+"]/span").click();	
						break;
					}
				}
			}
			//select the date
			List<WebElement> select1stFromCalander=d.findElements(By.xpath("(//table//td[contains(@id,'datepicker')])"));
			for(int i=1;i<=select1stFromCalander.size();i++) {
				if(getWebElementXpath_D("(//table//td[contains(@id,'datepicker')])["+i+"]").getText().equalsIgnoreCase(date)) {
					getWebElementXpath_D("(//table//td[contains(@id,'datepicker')])["+i+"]").click();	
					break;
				}
			}
			//Click on apply
			getWebElementXpath_D("//*[@id='comparisonDateRange']//button/i[@class='fa fa-check']").click();
			Thread.sleep(6000);
			printLog("Added fixed start date!!");
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 *The below method is to Select time interval.
			@@param Interval
			Test data: Interval = By year,By month,By week,By day,By hour
	 *@throws Throwable
	 **/
	public void timeinterval(String number,String Interval, boolean includeCurrentMonth) throws Throwable
	{		
		try
		{
			getWebElementActionXpath("TimeLineLast").click();
			getWebElementActionXpath("TimeLineNoOfMonths").click();
			getWebElementXpath("TimeLineNoOfMonths").clear();	
			getWebElementXpath("TimeLineNoOfMonths").sendKeys(number);
			new Select(getWebElementXpath("TimeLineInterval")).selectByVisibleText(Interval);
			boolean actualFlag = getWebElementXpath("IncludeCurrentMonthEnable").isDisplayed();
			if(includeCurrentMonth != actualFlag) {
				getWebElementXpath_D("//*[@class='fa fa-stack-1-5x fa-square-o']").click();
			}
			
			getWebElementXpath("ApplyTimelineOption").click();
			aJaxWait();
			Thread.sleep(1000);			

		} catch (Throwable t) {
			throw t;
		}


	}
	
	public void verifyTableDataWithExpected(String expectedtableDatas,int rowToBeVerify,String TestCaseName) throws Exception {
		String actual="";
		String expected="";
		try {
			String holdExpectedTableRows[]=expectedtableDatas.split("\\|");
			ArrayList<ArrayList<String>> hold4RowsOfActualTableData=Utility.returnPamTableData(rowToBeVerify) ;
			ArrayList<String> holdActualRowWiseData=null;
			for(int i=0;i<hold4RowsOfActualTableData.size();i++) {
				holdActualRowWiseData=hold4RowsOfActualTableData.get(i);
				for(int j=0;j<holdActualRowWiseData.size();j++) {
					actual=holdActualRowWiseData.get(j);
					expected=holdExpectedTableRows[i].split("~", -1)[j];
					Assert.assertEquals(actual,expected);
				}
			}
			printLog("Verified first "+rowToBeVerify+" rows data from table!!");
		}catch(Exception e) {
			printLog(TestCaseName+" Failed while verifying table data Actual is "+actual+" and Expected is "+expected);
			throw e;
		}
	}
	
	public void gotoExceptionNotificationPage() throws Exception {
		try {
			d.get(navurl +"com/Notification/MessageCenter.aspx?Tab=Schedule"); 
			aJaxWait();
			Reporter.log("Navigated to Exception and notification page");
			Thread.sleep(5000);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void dragAndDrop(String src, String tar) {
		WebElement s = getWebElementXpath(src);
		WebElement t = getWebElementXpath(tar);
		Actions act = new Actions(d);
		if ("ie".equalsIgnoreCase(Dto.getWebDriverObj())) {
			act.clickAndHold(s).perform();
			act.moveToElement(t,1,1).perform();
			act.release().perform();
			aJaxWait();
		} else {
			act.dragAndDrop(s, t).build().perform();
			aJaxWait();
		}
		Constant.APPLICATION_LOGS.debug("Selected " + src + " widget and Drag it to the dashboard page");
	}/**
	 *The below method is to push the widget to the respective dashboard.
	 *the Input for this method is "Dashboard name".
	 **Specify exact name present in the application
	 *@param DashboardName
	 *@throws Throwable
	 **/
	public void pushwidget(String DashboardName,String dbType) throws Throwable
	{		
		try
		{ 
			kabobMenuOptions("Save to Dashboard");
			aJaxWait();
			Thread.sleep(5000);

			//click on type of dashboard
			getWebElementActionXpath_D("//span[contains(normalize-space(),'"+dbType+"')]").click(); 
			aJaxWait();
			Thread.sleep(3000);

			//push to widget
			getWebElementActionXpath_D("//span[normalize-space()='"+DashboardName+"']/following-sibling::input[@type='button']").click(); 
			
			List<WebElement> a = d.findElements(By.xpath("//div[@class='btn btn-success']"));
			if(a.size()>0) {
				d.findElement(By.xpath("//div[@class='btn btn-success']")).click();
			}
			
			//Closing the pop up
			getWebElementActionXpath_D("(//a[@id='btnClose-flyout']/i)[1]").click(); 
			Thread.sleep(3000);
			
			if(dbType.equalsIgnoreCase("Legacy Dashboards")) {
				getWebElementActionXpath_D("//a[contains(text(),'Home')]").click();
				aJaxWait();
				Thread.sleep(5000);
			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}

	}
	
	/**
	 * The below method is to Select ComaprisionDateRange
	 * 
	 * @@param Interval Test data:Daterange = None,Previous Year,Previous Month,
	 * 		Previous Week,Previous Day, Year Before Last,Select Start Date
	 * @throws Throwable
	 **/
	public void comparisionDateRange(String Daterange) throws Throwable {
		try {
			if(d.findElements(By.xpath("//span[substring-after(normalize-space(),'Comparison Date Rang')]//*[contains(@class,'k-panelbar-collapse')]")).size()==0) {
			getWebElementActionXpath("ComparisonDateRangeExpander").click();
			Thread.sleep(1000);
			}
			new Select(getWebElementActionXpath("SelectcomparisonDateRange")).selectByVisibleText(Daterange);
			aJaxWait();
			Thread.sleep(2000);
		} catch (Throwable t) {
			t.printStackTrace();
			Assert.fail("Exception in ComparisionDateRange");
			throw t;
		}

	}/**
	 *  The below method is to Save the analysis 
	 * the Input for this method is NameofWidget.
	 * @param Nameofwidget
	 * @throws Throwable
	 */
	public void saveAnalysis(String NameofAnalysis) throws Throwable
	{
		try {//*[@id="se-wrench-menu"]/div[2]/div[1]/div/div[2]/div
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//i[@aria-label='Data details, exports and chart settings']").click();
			Thread.sleep(2000);
			List<WebElement> op = d.findElements(By.xpath("//div[contains(@class,'list-group-item')]"));
			String listXpath="";
			for(int i=1;i<=op.size();i++){
				listXpath="(//div[contains(@class,'list-group-item')])["+i+"]/div/div[2]";
				Thread.sleep(1000);
				if(getWebElementActionXpath_D(listXpath).getText().equalsIgnoreCase("Save Analysis")){
					Thread.sleep(1000);
					clickBasedOnAction(listXpath);
					break;
				}
			}
			
			Thread.sleep(4000);
			//Enter the name to save the analysis		
			WebElement save=getWebElementActionXpath_D("//input[@name='inputField']");
			Thread.sleep(2000);
			save.clear();
			save.sendKeys(NameofAnalysis);
			Thread.sleep(2000);
			//getWebElementActionXpath("saveanalysisOK").click();
			clickUsingJavascriptExecuter(locators.getProperty("saveanalysisOK"));
			Thread.sleep(2000);
			Thread.sleep(5000);
			getWebElementActionXpath("saveanalysisClose").click();
			aJaxWait();
			Thread.sleep(5000);

		} catch (Throwable t){
			t.printStackTrace();
			throw t;
		}

	}
	
	// PAM functions
	public void gotoManageMeasurementPage() throws Exception {
		try {
			explicitWait_Xpath(locators.getProperty("Administration"));
			d.navigate().to(navurl + "com/IntervalAnalytics/Interval.aspx#/calculatedStream/measurementTemplate");
			aJaxWait();
			Reporter.log("Navigated to manage measurement page");
			Thread.sleep(5000);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void gotoManageTagsPage() throws Exception {
		try {
			explicitWait_Xpath(locators.getProperty("Administration"));
			d.navigate().to(navurl + "com/IntervalAnalytics/Interval.aspx#/tags/apply");
			aJaxWait();
			Reporter.log("Navigated to manage tag page");
			Thread.sleep(5000);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void gotoSiteSchedulesPage() throws Exception {
		try {
			explicitWait_Xpath(locators.getProperty("Administration"));
			d.navigate().to(navurl + "com/IntervalDataModule/siteschedule.aspx#/SiteSchedule");
			aJaxWait();
			Reporter.log("Navigated to site schedules page");
			Thread.sleep(5000);
		} catch (Exception e) {
			throw e;
		}
	}
	public void gotoHierarchySetupPage() throws Exception {
		try {
			Utility.moveTheElement(locators.getProperty("Administration"));
			Thread.sleep(2000);			
			getWebElementActionXpath_D("//ul[@role='menu']//ra-child-menu//li//div//div//a[contains(text(),'Hierarchy Setup')]").click();
			Thread.sleep(8000);
			Reporter.log("Navigated to Hierarchy Setup  page");
		} catch (Exception e) {
			if(!d.getCurrentUrl().endsWith("com/IntervalAnalytics/Interval.aspx#/setupHierarchy")) {
				d.navigate().to(navurl +"com/IntervalAnalytics/Interval.aspx#/setupHierarchy");
				Thread.sleep(8000);
				Reporter.log("Navigation did not work properly it seems for exception page.");
			}
		}
	}
	
	public void gotoDataStreamsPage() throws Exception {
		try {
			explicitWait_Xpath(locators.getProperty("Administration"));
			d.navigate().to(navurl + "com/IntervalAnalytics/Interval.aspx#/datastreams");
			aJaxWait();
			Reporter.log("Navigated to Data Streams page");
			Thread.sleep(5000);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void gotoSiteMappingPage() throws Exception {
		try {
			explicitWait_Xpath(locators.getProperty("Administration"));
			d.navigate().to(navurl + "com/IntervalDataModule/IDMSiteManagement.aspx");
			aJaxWait();
			Reporter.log("Navigated to Site Mapping page");
			Thread.sleep(5000);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void gotoAverageProfilesPage() throws Exception {
		try {
			explicitWait_Xpath(locators.getProperty("Administration"));
			d.navigate().to(navurl + "com/intervaldatamodule/averageprofile.aspx#/AverageProfile");
			aJaxWait();
			Reporter.log("Navigated to Average Profiles page");
			Thread.sleep(8000);
		} catch (Exception e) {
			throw e;
		}
	}
	public int getSizeOfElement(String objectOfElements,String sourceOfLocator) {
		
		int sizeOfElement=0;
		try {
		if(sourceOfLocator.equalsIgnoreCase("xpath"))
			sizeOfElement=d.findElements(By.xpath(objectOfElements)).size();
		else if(sourceOfLocator.equalsIgnoreCase("css"))
			sizeOfElement=d.findElements(By.cssSelector(objectOfElements)).size();
		else
			sizeOfElement=d.findElements(By.xpath(objectOfElements)).size();
		}catch(Exception e) {
			throw e;
		}
		return sizeOfElement;
	}
	
	public void chartOptionExtendedLegend() throws Throwable {
		try {
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			// Select Legend tab.
			getWebElementXpath("ChartOptionsLegendTab").click();
			getWebElementXpath("ExtendLegend").click();
			getWebElementXpath("SaveAndClose").click();
			Thread.sleep(8000);		
		} catch (Throwable e) {
			throw e;
		}
	}
	
	
	public void includeAllDataPointsOnAxesTabChartOption() throws Throwable
	{
		try {
			
			// Closing the pop up if appearing on the chart
			if (d.findElements(By.xpath(locators.getProperty("AlertMessage"))).size()>0) {
				getWebElementActionXpath("AlertMessage").click();
			}
			
			kabobMenuOptions("Chart Options");
			// Click on Axes tab
			getWebElementXpath("AxisTabFromChartOptions").click();
			Thread.sleep(2000);

			// Click on 'Include All Data Points' radio
			getWebElementActionXpath("IncludeAllDataPointsAxesChartOption").click();
			Thread.sleep(2000);
			getWebElementXpath("SaveAndClose").click();
			Thread.sleep(6000);
			printLog("Clicked on 'Include All Data Points' radio button.");
		} catch (Throwable e) {
			throw e;
		}
	}
	

	public void clickUsingJavascriptExecuter(String xpath) throws Exception {
		WebElement xpathToCLick = null;
		try {
			xpathToCLick = d.findElement(By.xpath(xpath));
			JavascriptExecutor js = (JavascriptExecutor) d;
			js.executeScript("arguments[0].click();", xpathToCLick);
		} catch (Exception t) {
			throw t;
		}
	}
	
	}


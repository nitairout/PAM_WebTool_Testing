/*
 * This Utility class contains many reusable methods which are used across many test cases.
 * To call methods defined in this class, we don't need to create object, since all these methods are with static modifier.
 * To avoid creation of object outside of the class, a private constructor has been created
 */

package common;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import com.opencsv.CSVReader;
public class Utility extends TestBase{

	/**
	 * This below private constructor we have created to avoid creation of object outside of the class
	 * */
	private Utility(){}

	
	/**
	 * This method verifies if provided dashboard present or not 
	 * */
	public static void verifyDashboard(String dashboardname) throws Throwable {
		try {

			explicitWait_CSS("input[id*=ddlDashboards_Input]");
			d.findElement(By.cssSelector("input[id*=ddlDashboards_Input]")).click();
			Thread.sleep(3000);
			explicitWait_CSS("#divDashboarSelectorTree>div>ul>li:nth-of-type(2)");
			//	List<WebElement> options = d.findElements(By.cssSelector("#divDashboarSelectorTree>div[id*=rtvDashboards]>ul>li>div>span[class='rtIn']"));
			List<WebElement> options = d.findElements(By.xpath("//div[@id='divDashboarSelectorTree']/div/ul/li/div/span[2]"));
			List<String> a = new ArrayList<String>();

			for(WebElement dashboards:options)
			{
				a.add(dashboards.getText());
			}
			if(a.contains(dashboardname))
			{
				int index = a.indexOf(dashboardname) + 1;
				// to click on the given dashboard
				path="div#divDashboarSelectorTree>div>ul>li:nth-of-type("+index+")";
				div =WaitForCondition.findElement(d, By.cssSelector(path));
				((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView(true);",div);
				div.click();
			}
			else
			{
				Assert.fail("No Dashboard exist");
			}

			aJaxWait();
			Thread.sleep(8000);
		} catch (Exception e) {
			 
			throw e;
		}
	}

	/**
	 * It delete the dashboard if it is present
	 * */
	public static void deleteDashboard(String dashboardname) throws Throwable {
		try {
Utility.moveTheScrollToTheTop();
			getWebElementActionCSS_D("input[id*=ddlDashboards_Input]").click();
			Thread.sleep(2000);
			
			
			
			//	explicitWait_CSS("#divDashboarSelectorTree>div>ul>li:nth-of-type(2)");
			//	List<WebElement> options = d.findElements(By.cssSelector("#divDashboarSelectorTree>div[id*=rtvDashboards]>ul>li>div>span[class='rtIn']"));
			List<WebElement> options = d.findElements(By.xpath("//div[@id='divDashboarSelectorTree']/div/ul/li/div/span[2]"));
			int xpathCount = options.size();
			if (xpathCount > 1) {
				for(int c=1;c<=xpathCount;c++) {
					if(getWebElementActionXpath_D("//div[@id='divDashboarSelectorTree']/div/ul/li["+c+"]/div/span[2]").getText().equalsIgnoreCase(dashboardname)) {
						getWebElementActionXpath_D("//div[@id='divDashboarSelectorTree']/div/ul/li["+c+"]/div/span[2]").click();
						Thread.sleep(10000);
						//delete code
						//click on setting icon
						getWebElementActionXpath_D("//img[contains(@src,'App_Themes/Summit/images/dashboard-edit.png')]").click();
						Thread.sleep(1000);
						//click on delete link
						getWebElementActionXpath_D("/html[1]/body[1]/form[1]/div[1]/ul[1]/li[10]/a[1]/span[1]").click();
						Thread.sleep(1000);
						//click on ok
						getWebElementActionXpath_D("//div[contains(@id,'confirm')]/div/div[2]/a[1]/span/span").click();
						Thread.sleep(10000);
						
						break;
					}
				}
			}
			

			else
			{
				Assert.fail("No Dashboard exist");
			}
			aJaxWait();		
			Thread.sleep(5000);
		} catch (Exception e) {
			 
			throw e;
		}
	}


	/**
	 * it checks the onclick mode is active or not .It will exit if it is active
	 * */
	public static void checkAndExitOneClickMode(WebDriver d)	throws Throwable {
		try {
Utility.moveTheScrollToTheTop();
			explicitWait_Xpath("//*[@id='locationOptionButton2']/i[1]");
			if (!isElementPresentCSS_D("#locationOptionButton2 > i.fa-stack-1-5x.se-icon-tab.se-location-control.ng-hide")) {
				explicitWait_Xpath(locators.getProperty("clickonpointer"));
				clickBasedOnAction(locators.getProperty("clickonpointer"));
				Thread.sleep(2000);
			//	getWebElementCSS("oneclickmode").click();
				clickBasedOnAction("//*[@id='ctl00_myBody']/div/div[2]/div[1]/div/div[2]/span[2]");
				Thread.sleep(2000);
			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}


	/**
	 * Not used :Hold the LP chart tool tip value 
	 * */
	public static List<String> lpGetChartToolTip() throws Throwable {
		try {

			getWebElementXpath("TableDataTab").click();
			aJaxWait();
			Thread.sleep(500);
			tooltip = new ArrayList<String>();
			bars = d.findElements(By
					.xpath("//*[contains(@class,'highcharts-grid highcharts-xaxis-grid')]/*[contains(@class,'highcharts-grid-line')]"));
			int count = 0;
			for (WebElement bar : bars) {
				if (bar.getAttribute("stroke") != null
						&& bar.getAttribute("stroke").equals("#D3D3D3")) {
					Actions a = new Actions(d);
					a.moveToElement(bar,1,1);
					if (d.findElement(By.xpath(locators
							.getProperty("loadtooltiptext"))) != null) {
						WebElement tip = d.findElement(By.xpath(locators
								.getProperty("tooltiptext")));
						tooltip.add(tip.getText());
						count = count + 1;
					}
				} 
			}
			aJaxWait();
			Thread.sleep(600);
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
		return tooltip;
	}
	/**
	 * Not used: Hold the CLP chart tool tip value 
	 * */
	public static List<String> clpGetChartToolTip() throws Throwable {
		try {
			tooltip = new ArrayList<String>();
			bars = d.findElements(By
					.xpath("//*[@id='highcharts-120']/*[contains(@class,'highcharts-root')]/*[contains(@class,'highcharts-series-group')]/*[contains(@class,'highcharts-markers highcharts-series-0 highcharts-line-series highcharts-color-undefined highcharts-tracker')]/*[contains(@fill,'#2fb5ea')]"));
			int count = 0;
			for (WebElement bar : bars) {
				Actions a = new Actions(d);
				a.moveToElement(bar,1,1).click(bar).build().perform();
				if (d.findElement(By.xpath(locators.getProperty("tooltiptext"))) != null) {
					WebElement tip = d.findElement(By.xpath(locators.getProperty("tooltiptext")));
					tooltip.add(tip.getText());
					count = count + 1;
				}
			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
		return tooltip;
	}



	/**
	 * It will clear all the analysis from pam page
	 * */
	public static void clearsavedanalysis(WebDriver d) throws Exception
	{
		try{
			//new TestBase().goToAnalysisPage();;
			Utility.moveTheScrollToTheTop();
			//expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);
			//String allDeleteButtons= "//*[@id='detailTabs']/div/div/div[2]/div/div[2]/div/div/div[1]/div/div[3]/div[2]/div/div/div";
			String allDeleteButtons= "(//div[contains(@class,'se-saved-analyses-grid-icons')]//i[contains(@class,'fas fa-trash clickable')])";
			int remove = d.findElements(By.xpath(allDeleteButtons)).size();
			if (remove>0)
			{
				for(int i=1;i<=remove;i++)
				{
					getWebElementActionXpath_D(allDeleteButtons+"[1]").click();
					Thread.sleep(2000);
					//click on done to delete the analysis
					getWebElementActionXpath_D("//button/span[normalize-space()='Delete']").click();
					Thread.sleep(2000);
				}
			}
			else
				Reporter.log("No saved analysis available to close");
			
			//Going back to pam home page Clickin on create analysis
			getWebElementActionXpath("ExpandTheCreateAnalysisArrow").click();
			d.navigate().refresh();
			Thread.sleep(5000);
		}catch(Exception e){
			 
			throw e;
		}
	}

	
	
	public static void openSavedAnalysis(String analysisName) throws Exception
	{
		try{
			new TestBase().goToAnalysisPage();;
			explicitWait_Xpath("//*[@id='trend-card']/img");
			//expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(2000);
			getWebElementActionXpath_D("//div[normalize-space()='"+analysisName+"']").click();
			aJaxWait();Thread.sleep(8000);
		}catch(Exception e){
			 
			throw e;
		}
	}




	/**
	 * It create the dynamic dash board 
	 * */
	public static void dynamicDashboard(String dashboardname) throws InterruptedException  {
		try{

			explicitWait_Xpath("//input[contains(@id,'ddlDashboards_Input')]");
			d.findElement(By.xpath("//input[contains(@id,'ddlDashboards_Input')]")).click();
			aJaxWait();
			Thread.sleep(1000);
			path = "//div[contains(@id,'divDashboarSelectorTree')]/div/ul";
			WebElement board=d.findElement(By.xpath(path));
			List<WebElement>options=board.findElements(By.tagName("li"));
			int xpathCount = options.size();
			List<String> a = new ArrayList<String>();
			for (int i =1 ; i<= xpathCount;i++)
			{
				path="//div[contains(@id,'divDashboarSelectorTree')]/div/ul/li["+i+"]/div/span[2]";
				div=WaitForCondition.findElement(d, By.xpath(path));
				a.add(div.getText());
			}
			if(a.contains(dashboardname))
			{ 
				int index = a.indexOf(dashboardname)+1;
				path="//div[contains(@id,'divDashboarSelectorTree')]/div/ul/li["+index+"]/div/span[2]";
				div=WaitForCondition.findElement(d, By.xpath(path));
				div.click();
				aJaxWait();
				Thread.sleep(1000);
			}else {
				getWebElementID("ManageDashboard").click();
				aJaxWait();
				Thread.sleep(1000);
				WebElement iframe = getWebElementActionXpath_D("/html/body/form/div[1]/table/tbody/tr[2]/td[2]/iframe");
				d.switchTo().frame(iframe);
				aJaxWait();
				WebElement form = d.findElement(By.id("form1"));
				WebElement input = form.findElement(By.id("rtbDashboardTitle"));
				input.sendKeys(dashboardname);
				Thread.sleep(200);
				WebElement selecttype=form.findElement(By.id("rcbDashboardType"));
				selecttype.click();
				aJaxWait();
				Thread.sleep(1000);
				WebElement dynamic=form.findElement(By.xpath("//*[@id='rcbDashboardType_DropDown']/div/ul/li[2]"));
				dynamic.click();
				aJaxWait();		
				WebElement checkpampermission=d.findElement(By.xpath("//div[contains(@id,'div2')]/div[1]/input"));
				checkpampermission.click();
				aJaxWait();
				WebElement save = d.findElement(By.id("lblsave"));
				save.click();
				aJaxWait();
				Thread.sleep(1000);
			}	
			aJaxWait();
		}catch(Exception e){
			 
		}
	}

	/**
	 *It removes the tag assigned in ADS
	 * */
	public static void removeTagsinADS(WebDriver d) {
		try{

			path=".tag-item>ti-tag-item>ng-include>a";
			List<WebElement> x =d.findElements(By.cssSelector(path));
			if(x.size()>0)
			{		
				for (WebElement s:x) {
					path=".tag-item>ti-tag-item>ng-include>a";
					div = explicitWait_CSS(path);
					div.click();
					Thread.sleep(2000);
				}
			}
			aJaxWait();
		}catch(Exception e){
			 
		}
	}


	


	// Save Edited Widget
	public static void savewidget() throws Throwable {
		explicitWait_Xpath(locators.getProperty("SaveWidget"));
		getWebElementXpath("SaveWidget").click();
		aJaxWait();
		Thread.sleep(1000);
		Constant.APPLICATION_LOGS.debug("Clicked on save button");
		Reporter.log("Clicked on save button");
	}

	// Edit Widget
	public static void editWidget() throws Throwable {
		// Edit Widget
		Thread.sleep(3000);
		getWebElementActionCSS("WidgetMenu_Icon").click();
		aJaxWait();
		explicitWait_Xpath(locators.getProperty("EditWidget"));
		getWebElementXpath("EditWidget").click();
		aJaxWait();
		Constant.APPLICATION_LOGS.debug("Clicked on the widget Edit option");
		Reporter.log("Clicked on the widget Edit option");
	}

	public static String returnShortMOnth() {
		String mon="";
		try {
			Calendar c = Calendar.getInstance();
			mon=c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH );
			//	mon=c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
		} catch (Exception e) {
			 
		}
		return mon;
	}

	/**
	 * 
	 * @param exportType
	 * @category This utility method is used to export the CSV/PDF file based on parameter. 
	 */
	public static void SelectOptionsUnderKabobMenu(String exportType,String fileToDeleteForEdport){

		try{
			//delete all related downloaded file
			if(!fileToDeleteForEdport.equals("")) {
				File uploadDirectory = new File(Constant.SRC_FOLDER);
				File[] downloadedFiles = uploadDirectory.listFiles();
				for (File file : downloadedFiles) {
					if (file.isFile()){ 
						if(file.getName().startsWith(fileToDeleteForEdport.split("\\.")[0]) && file.getName().endsWith(fileToDeleteForEdport.split("\\.")[1])) {
							file.delete();
						}
					}
				}
			} 

				
			
			
			
			Utility.moveTheScrollToTheTop();
			//Click on wrench Button
			getWebElementActionXpath_D("//*[@id='wrenchButton']/i").click();
			Thread.sleep(1000);		
			List<WebElement> op = d.findElements(By.xpath("//*[@id='se-wrench-menu']/div[2]/div"));
			String listXpath="";
			for(int i=1;i<=op.size();i++){
				listXpath="//*[@id='se-wrench-menu']/div[2]/div["+i+"]/div/div[2]/div";
				if(getWebElementActionXpath_D(listXpath).getText().equalsIgnoreCase(exportType)){
					getWebElementActionXpath_D(listXpath).click();
					break;
				}
			}
			aJaxWait();
		}catch(Exception e){
			throw new org.openqa.selenium.NoSuchElementException("Element not found in SelectOptionsUnderKabobMenu");
		}
	}

	

	//Data Lobby functions
	public static void selectProject(String projectname) throws Throwable
	{
		try{
			d.findElement(By.xpath("//a[@id='browse_link']")).click();
			aJaxWait();
			d.findElement(By.xpath("//a[@id='project_view_all_link_lnk']")).click();
			aJaxWait();
			//d.get("https://servicedeskstg1.ems.schneider-electric.com/secure/BrowseProjects.jspa?selectedCategory=all&selectedProjectType=all");
			//explicitWait_CSS("td.cell-type-name a");
			Thread.sleep(500);
			explicitWait_CSS("#project-filter-text");
			getWebElementCSS_D("#project-filter-text").sendKeys(projectname);
			Thread.sleep(1000);
			//explicitWait_CSS("td.cell-type-name a");
			//getWebElementCSS_D("td.cell-type-name a").click();
			//*[@id="projects"]/div/table/tbody/tr[1]/td[1]/a
			d.findElement(By.xpath("//*[@id='projects']/div/table/tbody/tr[1]/td[1]/a")).click();
			aJaxWait();
			Thread.sleep(10000);
			aJaxWait();
			d.findElement(By.xpath("//span[contains(text(),'Queues')]")).click();
			aJaxWait();
			Thread.sleep(1000);

		}catch(Exception e){
			throw e;
		}

	}

	
	/**
	 * Select account and shadow meter site
	 * */
	public static void clickOnAccountShadowMeter() throws Throwable
	{
		try{
			//expand capriata/saiwa main meter
			getWebElementXpath_D("//*[@id='treeView']/div[2]/div/div[3]/ul/li/span/span[1]/span[2]").click();
			//expand energy balance
			getWebElementXpath_D("//*[@id='treeView']/div[2]/div/div[3]/ul/li/ol/span[1]/span/ul/li/span/span[1]/span[2]").click();
			//expand building and office
			getWebElementXpath_D("//*[@id='treeView']/div[2]/div/div[3]/ul/li/ol/span[1]/span/ul/li/ol/span[1]/span/ul/li/span/span[1]/span[2]").click();
			verifyText("PAMTrendAccShadowPAMTestCapriataSaiwa");
			//select PAMTestAccount10001 energy
			getWebElementXpath_D("//*[@id='treeView']/div[2]/div/div[3]/ul/li/ol/span[1]/span/ul/li/ol/span[1]/span/ul/li/ol/span[1]/span/ul/li/div/div/span[2]/span/i/i[1]/i/i[3]").click();
			//select PAMTestShadowMeter20001 energy
			getWebElementXpath_D("//*[@id='treeView']/div[2]/div/div[3]/ul/li/ol/span[1]/span/ul/li/ol/span[1]/span/ul/li/ol/span[2]/span/ul/li/div/div/span[2]/span/i/i[1]/i/i[3]").click();
		}catch(Exception e){
			throw e;
		}

	}
	

	
	public static void addNewCustomizedWidgets(String widgetType, String analysisName)throws Exception {
		try {
			// Click on Customize Icon
			explicitWait_Xpath(locators.getProperty("DashboardIcon"));
			getWebElementActionXpath("DashboardIcon").click();
			Thread.sleep(5000);

			// Click on Widgets library
			explicitWait_Xpath(locators.getProperty("Widgets_Lib"));
			getWebElementActionXpath("Widgets_Lib").click();
			Thread.sleep(3000);
			
			
			List<WebElement> widgetList = d.findElements(By.xpath("//*[@id='sidebar']/div[1]/div/div[4]/ul/div/perfect-scrollbar/div/div[1]/li"));
			for(int i=1;i<=widgetList.size();i++) {
				
				String widgetTypeXpath="//*[@id='sidebar']/div[1]/div/div[4]/ul/div/perfect-scrollbar/div/div[1]/li["+i+"]";
				JavascriptExecutor js = (JavascriptExecutor) d;
				WebElement Element = getWebElementActionXpath_D(widgetTypeXpath);
				js.executeScript("arguments[0].scrollIntoView();", Element);
				
				String widget=getWebElementActionXpath_D(widgetTypeXpath).getText();
				if(widgetType.equalsIgnoreCase(widget)) {
					//Click on the widgetType
					getWebElementActionXpath_D(widgetTypeXpath).click();
					Thread.sleep(3000);
					break;
				}
			}
			//click on search text box
			getWebElementActionXpath_D("//*[@id='sidebar']/div[1]/div/div[4]/div/div[2]/input").click();
			//added searched item in search text box
			getWebElementActionXpath_D("//*[@id='sidebar']/div[1]/div/div[4]/div/div[2]/input").sendKeys(analysisName);
			
			
			List<WebElement> dashboard = d.findElements(By.xpath("//*[@id='dv-flyout-main-panel']/div[2]/div/div/ra-widget-flyout/div/div/perfect-scrollbar/div/div[1]/div"));
			String dbName="";
			for(int i=1;i<=dashboard.size();i++){
				dbName=getWebElementActionXpath_D("//*[@id='dv-flyout-main-panel']/div[2]/div/div/ra-widget-flyout/div/div/perfect-scrollbar/div/div[1]/div["+i+"]/div/div[1]/div[2]/label").getText();
				if(dbName.equalsIgnoreCase(analysisName)) {
					getWebElementActionXpath_D("//*[@id='dv-flyout-main-panel']/div[2]/div/div/ra-widget-flyout/div/div/perfect-scrollbar/div/div[1]/div["+i+"]/div/div[2]/button/ra-tooltip[2]/div/span").click();
					break;
				}
			}
			//click on add to dashboard button
			aJaxWait();
			Thread.sleep(3000);
			//d.navigate().refresh();
			WebElement element = d.findElement(By.xpath(locators.getProperty("SecondLayer_closeIcon")));
			JavascriptExecutor js = (JavascriptExecutor)d;
			js.executeScript("arguments[0].click();", element);
			Thread.sleep(2000);
			
			WebElement element1 = d.findElement(By.xpath(locators.getProperty("FirstLayer_closeIcon")));
			JavascriptExecutor js1 = (JavascriptExecutor)d;
			js1.executeScript("arguments[0].click();", element1);
			Thread.sleep(3000);

		} catch (Exception e) {
			 
			Constant.APPLICATION_LOGS.debug("Error in assignWidgets");
			throw e;
		}

	}
	/* At the end we delete newly added widget , so that we can create again in next run */	
	public static void deleteLegacyDBWidget() throws Exception {
		try {		
			
			
			int numberofWidgetOne=d.findElements(By.xpath("//a/span[@class='rdCustom']")).size();
			int numberofWidgetTwo=d.findElements(By.xpath("//tr[contains(@class,'rdMiddle')]")).size();
			if(numberofWidgetOne>0 & numberofWidgetTwo>0) {
				for(int i=1;i<=numberofWidgetOne;i++) {
					getWebElementActionXpath_D("(//a/span[@class='rdCustom'])[1]").click();
					Thread.sleep(1000);
					getWebElementActionXpath_D("(//span[@class='rmText' and text()='Delete'])[1]").click();
					Thread.sleep(1000);
				}
			}
			/*getWebElementActionCSS("WidgetMenu_Icon").click();
			Thread.sleep(1000);
			getWebElementActionXpath_D("(//span[@class='rmText' and text()='Delete'])[1]").click();
			Thread.sleep(1000);
			*/
		} catch (Exception t) {
			throw t;

		}
	}
	
	
	/**
	 * Used javascript executer to make the element display 
	 * */
	public static void makeTheElementDisplay(String elementXpath)throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) d;
			WebElement Element = getWebElementXpath_D(elementXpath);
			js.executeScript("arguments[0].scrollIntoView();", Element);
			Thread.sleep(1000);
		}catch(Exception e) {
			throw e;
		}
	}
	/**
	 *Move the scroll 600 pixel top 
	 * */
	public static void moveTheScrollToTheTop() throws Exception{
		try {
			 JavascriptExecutor js = (JavascriptExecutor) d;
			 js.executeScript("window.scrollBy(0,-600)", "");
			Thread.sleep(2000);
		}catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 *Move the scroll 600 pixel top 
	 * */
	public static void moveTheScrollToTheTopOnRquest(String pixel) throws Exception{
		try {
			 JavascriptExecutor js = (JavascriptExecutor) d;
			 js.executeScript("window.scrollBy(0,-"+pixel+")", "");
			Thread.sleep(1000);
		}catch(Exception e) {
			throw e;
		}
	}
	public static void moveTheScrollToThebottomOnRquest(String pixel) throws Exception{
		try {
			 JavascriptExecutor js = (JavascriptExecutor) d;
			 js.executeScript("window.scrollBy(0,"+pixel+")", "");
			Thread.sleep(1000);
		}catch(Exception e) {
			throw e;
		}
	}
	/**
	 *Move the scroll 600 pixel down 
	 * */
	public static void moveTheScrollToTheDown() throws Exception{
		try {
			
			 JavascriptExecutor js = (JavascriptExecutor) d;
			 js.executeScript("window.scrollBy(0,600)", "");
			Thread.sleep(2000);
		}catch(Exception e) {
		throw e;
		}
	}

	
	/**
	 *Select the location type out of 4  based on passed name 
	 * */
	public static void selectLocationType(String type) throws Exception{
		//Manage Locations List,Hide Account and Shadow Meters,Hide Filter
		try {
			moveTheScrollToTheTop();
			explicitWait_Xpath(locators.getProperty("LocationOptionButton")).click();
			if("Manage Locations List".equals(type)) {
				getWebElementActionXpath_D("//*[@id='ctl00_myBody']/div/div[2]/div[3]/div/div[2]").click();
			}else if("Hide Account and Shadow Meters".equals(type)) {
				getWebElementActionXpath_D("//*[@id='ctl00_myBody']/div/div[2]/div[2]/div/div[2]/div/div[2]").click();
			}else if("Show Account and Shadow Meters".equals(type)) {
				getWebElementActionXpath_D("//*[@id='ctl00_myBody']/div/div[2]/div[2]/div/div[2]/div/div[2]").click();
			}else if("Hide Filter".equals(type)) {
				getWebElementActionXpath_D("//*[@id='ctl00_myBody']/div/div[2]/div[1]/div/div[2]/span").click();
			}
			Thread.sleep(3000);
		}catch(Exception e) {
			 
			throw e;
		}
	}
	/**
	 *Move the control to the particular location based on passed xpath
	 * */
	public static void moveTheElement(String pathOfTheElement) {
		try{
			WebElement tableElement = d.findElement(By.xpath(pathOfTheElement) );
			Actions teAction = new Actions(d);
			teAction.moveToElement( tableElement,1,1 ).build().perform();
		}catch(Exception e){
			throw e;
		}
	}
	
	
	
	/**
	 *Move and click the control to the particular location based on passed xpath
	 * */
	public static void moveTheElementandClick(String pathOfTheElement) {
		try{
			WebElement element = d.findElement(By.xpath(pathOfTheElement) );
			Actions teAction = new Actions(d);
			teAction.moveToElement( element,1,1 ).click().build().perform();			
			
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 *Drag and Drop the control to the particular location based on passed xpath
	 * */
	public static void dragAndDrop(String pathOfTheElement) {
		try{
			WebElement from = d.findElement(By.xpath(pathOfTheElement) );
			Actions builder = new Actions(d);
			builder.dragAndDropBy(from, 100,100).build().perform();
			
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 *Move the control to the particular location based on passed css selector
	 * */
	public static void moveTheElementBasedOnCSS(String pathOfTheElement){
		try{
			WebElement tableElement = d.findElement(By.cssSelector(pathOfTheElement) );
			Actions teAction = new Actions(d);
			teAction.moveToElement( tableElement,1,1 ).perform();
		}catch(Exception e){
			throw e;
		}
	}
	
	/**
	 *It will select the dashboard
	 * */
	


	
	/**
	 *Hide the option and location panel for cards
	 * */
	public static void hideOptionLocationPanelForCards() throws Exception{
		try{
			moveTheScrollToTheTop();
			Thread.sleep(2000);
			getWebElementActionXpath("HideOptionsPanelArrow").click();
			getWebElementActionXpath("HideLocationsPanelArrow").click();
			Thread.sleep(2000);
		}catch(Exception e){
			 
			throw e;
		}
	}
	/**
	 *Show the option and location panel for cards
	 * */
	public static void showOptionLocationPanelForCards() throws Exception{
		try{
			moveTheScrollToTheTop();
			//*[@id="locationPanelResizableContainer"]/div[1]/div[2]/i
			getWebElementActionXpath("ShowOptionsPanelArrow").click();
			getWebElementActionXpath("ShowLocationsPanelArrow").click();
			Thread.sleep(2000);
			//*[@id='detailTabs']/div/div/se-panel/div[3]/div[1]/div[2]/i
		}catch(Exception e){
			 
			throw e;
		}
	}
	/**
	 *It click on compress empty space for pam new widget
	 * */
	public static void compressEmptySpace() throws Exception{
		try{
			moveTheScrollToTheTop();
			WebElement xpathToCLick = null;
			Actions actions = new Actions(d); 
			xpathToCLick = d.findElement(By.xpath("//i[contains(@class,'fal fa-ellipsis-v fa-lg')]")); 
			actions.moveToElement(xpathToCLick,1,1).click().build().perform(); 
			Thread.sleep(3000);
			
			xpathToCLick = d.findElement(By.xpath("//span[contains(text(),'Compress Empty Space')]")); 
			actions.moveToElement(xpathToCLick,1,1).click().build().perform(); 
			Thread.sleep(3000);
			/*//click on ...
			clickBasedOnAction("//*[@id='ra-dashboard-main']/div[3]/div/div/div[1]/span/i");
			//click on compress empty space
			clickBasedOnAction("//*[@id='ra-dashboard-main']/div[3]/div/div/div[1]/div/a[5]/span");
			Thread.sleep(5000);*/
			//Clicking on dashboard title to lost compress empty space focus
			xpathToCLick = d.findElement(By.xpath("//*[@class='dashboard-title']")); 
			actions.moveToElement(xpathToCLick,1,1).click().build().perform(); 
			Thread.sleep(3000);
			moveTheScrollToTheTop();
		}catch(Exception e){
			 
			throw e;
		}
	}
	
	/**
	 *It check if the perticular icon is present
	 * */
	public static void isIconPresent(String xpath) throws Exception{
		try{
			boolean avgIconIsPresent= getWebElementActionXpath_D(xpath).isDisplayed();
			if(!avgIconIsPresent){
				throw new Exception("The icon is not present in the screen!!");
			}
		}catch(Exception e){
			 
			throw e;
		}
	}
	
		/**
	 * delete the filters from pam widget
	 * */
		public static void deleteFilter() {
			try {
				List<WebElement> filter = d.findElements(By.xpath("//app-ra-dashboard-applied-filter[1]/div[1]/div"));
				if (filter.size() > 1) {
					getWebElementXpath("Filter_Close_Icon").click();
					aJaxWait();
					Thread.sleep(3000);
					Reporter.log("Successfully delete the existing filters from dashboard");
				} else {
					Reporter.log("There is no filter is applying on the dashboard");
				}
			} catch (Throwable t) {
				// TODO Auto-generated catch block
				t.printStackTrace();
			}

		}

		/**
		 * Not used for pam. creared for RA widget: delete widget by name 
		 * */
		public static void deleteWidgetByTitle(String widgetNameAsParameter) throws Throwable{

			try {

				//compress emptyspace to make widgets formatted
				Utility.compressEmptySpace();
				
				//calculate the number of widgets are available
				List<WebElement> widgetNames = d.findElements(By.xpath("//div/gridster/gridster-item"));
				int numberOfWidget=widgetNames.size();
				Reporter.log("Number of widgets are : "+numberOfWidget);
				
				String widgetName="";
				WebElement xpathToCLick = null;
				Actions actions = new Actions(d); 
				
				//Loop used to travel from one widget to another
				for(int i=1;i<=numberOfWidget;i++){
					
					// move the control to respective widget
					Utility.moveTheElement("//div/dashboard-def-template/ra-dashboard-area/div/gridster/gridster-item["+i+"]");

					// Get the widget name
					widgetName = getWebElementActionXpath_D("//div/dashboard-def-template/ra-dashboard-area/div/gridster/gridster-item["+i+"]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
					//Comparing the parameter contain with widget name to make delete functionality start
					if(widgetName.contains(widgetNameAsParameter)){
						// Click the setting icon for the widget to delete
						xpathToCLick = d.findElement(By.xpath("//div/dashboard-def-template/ra-dashboard-area/div/gridster/gridster-item["+i+"]/ra-widget-container/div/div[1]/div/div/div[2]/div/div[3]/div/i"));
						actions.moveToElement(xpathToCLick,1,1).click().build().perform();
						Thread.sleep(2000);

						// Click on delete link
						xpathToCLick = d.findElement(By.xpath("//*[@id='ra-db-widget-menu']//*[@id='wcWidgetDelete']/i"));
						actions.moveToElement(xpathToCLick,1,1).click().build().perform();
						Thread.sleep(2000);

						xpathToCLick = d.findElement(By.xpath("//i[@class='fal fa-ellipsis-v fa-lg']")); 
						actions.moveToElement(xpathToCLick,1,1).click().build().perform(); 
						Thread.sleep(3000);
						
						xpathToCLick = d.findElement(By.xpath("//div[@class='dropdown-content widget-config-item dropdown-click']/a[5]/span")); 
						actions.moveToElement(xpathToCLick,1,1).click().build().perform(); 
						Thread.sleep(3000);
						//If any widget deleted then the number of widgets also decreased 
						numberOfWidget=numberOfWidget-1;
						i=i-1;
						
						//Make the widget formatted after delete the widget
						Utility.compressEmptySpace();
					}
				}
				
			}catch(Exception t){
				throw t;
			}
			}
		
		
		

		/**
		 * Not used anywhere. Hold the trend chart tool tip value 
		 * */
		public static List<String> trendGetChartToolTip() throws Throwable {

			try {
				// Tool tip verification

				getWebElementXpath("TableDataTab").click();
				aJaxWait();
				Thread.sleep(500);
				tooltip = new ArrayList<String>();
				bars = d.findElements(By.xpath(locators.getProperty("toolTipBar")));
				for (WebElement bar : bars) {
					Actions a = new Actions(d);
					a.moveToElement(bar,1,1).build().perform();
					WebElement tip = d.findElement(By.xpath(locators.getProperty("tooltiptext")));
					tooltip.add(tip.getText());
				}
				aJaxWait();
				Thread.sleep(600);
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}
			return tooltip;
		}
		///////////////////////////////////New pam methods//////////////////////////////////////////////////
		
		
		
		public static ArrayList<ArrayList<String>> returnPamTableData(int numberOfRows) throws Exception{
			try {
				ArrayList<ArrayList<String>> tableDatas=new ArrayList<ArrayList<String>>();
				ArrayList<String> innerCellData=null;
				List<WebElement> allRowsCells=null;
				for(int i=1;i<=numberOfRows;i++) {
					innerCellData=new ArrayList<String>();//*[@id="tablebody"]/tr[1]/th
					innerCellData.add(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/th").getText());
					allRowsCells=d.findElements(By.xpath("//*[@id='tablebody']/tr["+i+"]/td"));
					for(int j=1;j<=allRowsCells.size();j++) {
						innerCellData.add(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/td["+j+"]").getText());
					}
					tableDatas.add(innerCellData);
					
				}
				
				return tableDatas;
			}catch(Exception e){
				throw e;
			}
		}
		
		
		public static void selectDashboard(String dbType,String dbName) throws Exception{
			try{
				Thread.sleep(5000);
				moveTheScrollToTheTop();
				Thread.sleep(2000);
				//verifyDashboardExists(dbName);
				
				String dashBoardPath="//*[@id='navbarCollapse']/ul/li[1]/div[1]/a/span";
				Actions actions = new Actions(d); 
				try{
				getWebElementActionXpath_D(dashBoardPath).click();
				}catch(Exception e){
					Thread.sleep(3000);
					getWebElementActionXpath_D("//ra-header/div[1]/div[1]/ra-menu[1]/nav[1]/div[1]/ul[1]/li[1]/div[1]/a[1]/span[1]").click();
				}
				String legasyPath="//a[@class='menu-first-child bold'][contains(text(),'"+dbType+"')]";
				actions = new Actions(d); 
				WebElement LDB = d.findElement(By.xpath(legasyPath)); 
				actions.moveToElement(LDB,1,1).click().build().perform();
				Thread.sleep(2000);
				
				if(dbType.equalsIgnoreCase("Legacy Dashboards")) {
					createOrSelectFromLegacyDB(dbName);
				}else {
				
				//click on hamburger menu icon
				getWebElementActionXpath_D("//div[contains(@class,'box db-library')]/i[1]").click();
				Thread.sleep(2000);
				//select dashboard
				getWebElementActionXpath_D("//button[contains(text(),'Dashboard')]").click();
				Thread.sleep(3000);
				//select blank dashboard
				getWebElementActionXpath_D("(//div[@class='dashboard-wrapper']//div[@class='dashboard-button-wrapper']/button)[1]").click();
				Thread.sleep(3000);
				getWebElementActionXpath_D("//input[@id='dashboard-name']").sendKeys(dbName);
				Thread.sleep(2000);
				//add dashboard name
				getWebElementActionXpath_D("//span[@class='ra-dropdown-input-text light ng-star-inserted']").click();
				Thread.sleep(1000);
				getWebElementActionXpath_D("//*[@class='ul']//span[contains(text(),'PAM Dashboards')]").click();
				Thread.sleep(2000);
				
				getWebElementActionXpath_D("//input[@value='Create']").click();
				Thread.sleep(3000);
			
				printLog("Selected dashboard as ",dbName);
				}
			}catch(Exception e){
				 
				throw e;
			}
			
		}
		//sdm udm
		public static void selectSingleMeasurement(String commodity,String measurement,String measurementType) throws Exception{
			try{
				String measurementCategory="";
				Utility.moveTheScrollToTheTop();
				getWebElementActionXpath_D("//table/thead/tr[1]/th//i[contains(@class,'se-icon-"+(commodity.toLowerCase())+"-o com-header')]").click();
				Thread.sleep(2000);
				
				getWebElementActionXpath_D("//li[contains(@id,'k-tabstrip-tab')]//span[contains(text(),'"+commodity+"')]").click();
				Thread.sleep(2000);
				
				//Deselect all
				//getWebElementActionXpath_D("//span[contains(normalize-space(),'Deselect All')]").click();
				getWebElementActionXpath_D("//*[contains(@class,'se-btn-deselect-measures')]").click();
				Thread.sleep(2000);
				
				//Checking if both the standard and userdefind=ed measurements are in collapsed state
				int meaurementTypeArr=d.findElements(By.xpath("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]")).size();
				if(meaurementTypeArr>0)
					getWebElementActionXpath_D("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]").click();
			
				//Expand the respective measurement type
				if(measurementType.equalsIgnoreCase("standard")) {
					measurementCategory="systemGeneratedMeasurements";
					getWebElementActionXpath_D("(//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'expand')])[2]").click();
					Thread.sleep(1000);
					}else if(measurementType.equalsIgnoreCase("userDefined")) {
					measurementCategory="userDefinedMeasurements";
					getWebElementActionXpath_D("(//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'expand')])[1]").click();
					Thread.sleep(1000);
				}
				
				//getWebElementActionXpath_D("//span[@class='se-btn-deselect-measures btn btn-link btn-xs redSE pull-right']").click();
				//Click on filter
				getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
				getWebElementXpath_D("//input[@id='commodityModalFilter']").sendKeys(measurement);
				Thread.sleep(2000);
				//click on measurement filter icon
				getWebElementActionXpath_D("//kendo-grid-filter-menu/a[@title='Measurements Filter Menu']//kendo-svgicon").click();
				Thread.sleep(1000);
				//click on filter drop down
				getWebElementActionXpath_D("(//div[contains(@id,'k-grid') and contains(@id,'filter-menu')]//kendo-dropdownlist)[1]").click();
				Thread.sleep(1000);
				//Select the value from the drop down
				getWebElementXpath_D("//span[contains(.,'Is equal to')]").click();
				Thread.sleep(1000);
				//Enter the measurement name to filter
				getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").clear();
				getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").sendKeys(measurement);
				Thread.sleep(2000);
				//Click on filter button
				getWebElementActionXpath_D("//button[contains(text(),'Filter')]").click();
				Thread.sleep(2000);
				
				//click on plus icon to select measurement
				getWebElementActionXpath_D("//i[contains(@class,'fa fa-lg clickable fa-plus-circle')]").click();
				//getWebElementActionXpath_D("//*[@id='commodityModalForm']//se-collapse[@identifier='"+measurementCategory+"']//i[@class='fa fa-lg clickable fa-plus-circle']").click();
				Thread.sleep(1000);
				//save and close
				getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
				Thread.sleep(5000);
				printLog("Selected commodity as "+commodity+" And measurement as "+measurement);
			}catch(Exception e) {
				throw e;
			}
		}
		
	
		
		/**
		* THis method accept only one String with different special character.
		* The special characters are categorized the commodity type(#) the measurement name(*) the measurement type(_)
		* Example: "Electricity#Demand_"+standard+"*Energy_"+standard+"|Weather#Temperature_"+standard
		* 
		* Electricity: Commodity
		* Demand: Measurement
		* ~standard: Measurement Type
		* Weather: Commodity 
		* Temperature: Measurement
		* ~userDefined: Measurement Type
		*
		* @param  "Electricity#Demand_"+standard+"*Energy_"+standard+"|Weather#Temperature_"+userDefined
		* 
		* 
		*/
		
		public static void selectMultipleMeasurements(String commodityMeasurementAndType) throws Throwable {
			try {
				//Click on electric commodity icon to enter into select measurement pop up
				getWebElementActionXpath_D("//table/thead/tr[1]/th//i[contains(@class,'se-icon-electricity-o com-header')]").click();
				//Deselect all
				getWebElementActionXpath_D("//*[contains(@class,'se-btn-deselect-measures')]").click();
				Thread.sleep(2000);
				//getWebElementActionXpath_D("//span[contains(normalize-space(),'Deselect All')]").click();
				//getWebElementActionXpath_D("//span[@class='se-btn-deselect-measures btn btn-link btn-xs redSE pull-right']").click();
				//"Electricity#Demand_"+standard+"*Energy_"+standard+"|Weather#Temperature_"+standard
				String allToBeSelectedCommodityAndMeasurements[]=commodityMeasurementAndType.split("\\|");
				String commodityAndMeasurement[]=null;
				String commodity="";
				String measurement="";
				String measurementCategory="";
				String measurementAndType[]=null;
				//"Electricity#Demand_"+standard+"*Energy_"+standard+"
				if(allToBeSelectedCommodityAndMeasurements.length>=1) {
					for(String comAndMea:allToBeSelectedCommodityAndMeasurements) {
						//Holding 1 commodity measurement and measurement type 
						commodityAndMeasurement=comAndMea.split("#");
						//Commodity
						commodity=commodityAndMeasurement[0];
						//Clicking on commodity functionality
						getWebElementActionXpath_D("//ul/li//span[text()='"+commodity+"']").click();
						Thread.sleep(2000);
						measurementAndType=commodityAndMeasurement[1].split("\\*");
						
						for(int m=0;m<measurementAndType.length;m++) {
							measurement=measurementAndType[m].split("~")[0];
							measurementCategory=measurementAndType[m].split("~")[1];
							
														//Checking if both the standard and userdefind=ed measurements are in collapsed state
							int meaurementTypeArr=d.findElements(By.xpath("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]")).size();
							if(meaurementTypeArr>0)
								getWebElementActionXpath_D("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]").click();
						
							//Expand the respective measurement type
							if(measurementCategory.equalsIgnoreCase("standard")) {
								measurementCategory="systemGeneratedMeasurements";
								getWebElementActionXpath_D("(//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'expand')])[2]").click();
							}else if(measurementCategory.equalsIgnoreCase("userDefined")) {
								measurementCategory="userDefinedMeasurements";
								getWebElementActionXpath_D("(//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'expand')])[1]").click();
							}
							
							//Click on filter
							getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
							getWebElementXpath_D("//input[@id='commodityModalFilter']").sendKeys(measurement);
							Thread.sleep(3000);
							
							
							//click on measurement filter icon
							getWebElementActionXpath_D("//kendo-grid-filter-menu/a[@title='Measurements Filter Menu']//kendo-svgicon").click();
							Thread.sleep(500);
							//click on filter drop down
							getWebElementActionXpath_D("(//div[contains(@id,'k-grid') and contains(@id,'filter-menu')]//kendo-dropdownlist)[1]").click();
							Thread.sleep(1000);
							//Select the value from the drop down
							getWebElementActionXpath_D("//span[normalize-space()='Is equal to']").click();
							Thread.sleep(1000);
							//Enter the measurement name to filter
							getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").clear();
							getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").sendKeys(measurement);
							Thread.sleep(2000);
							
							//Click on filter button
							getWebElementActionXpath_D("//button[contains(text(),'Filter')]").click();
							Thread.sleep(2000);
							
							//click on plus icon to select measurement
							getWebElementActionXpath_D("//i[contains(@class,'fa fa-lg clickable fa-plus-circle')]").click();
							Thread.sleep(1000);
							printLog("Selected commodity as "+commodity+" And measurement as "+measurement);
							
							//to remove measurement filter for further use
							getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
							//click on measurement filter icon
							getWebElementActionXpath_D("//kendo-grid-filter-menu/a[@title='Measurements Filter Menu']//kendo-svgicon").click();
							Thread.sleep(500);
							//click on filter drop down
							getWebElementActionXpath_D("(//div[contains(@id,'k-grid') and contains(@id,'filter-menu')]//kendo-dropdownlist)[1]").click();
							Thread.sleep(1000);
							//Select the value from the drop down
							getWebElementActionXpath_D("//span[normalize-space()='Is equal to']").click();
							Thread.sleep(1000);
							//Enter the measurement name to filter
							getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").clear();
							
							
						}
					}
				}
				//Click on save and close
				getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
				Thread.sleep(5000);
				
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;
			}

		}
		
		
		/**
		 * This method is accepting 2 parameter . 1:The filename starting with 2:Number of row to be fetch
		 * This Method is used to return number of rows which is passed in method parameters
		 */
		public static  ArrayList<List<String>> returnNumberOfRowsFromcsv(String fileName,int rowToBeFetch)
		{
			ArrayList<List<String>> allRows=null;
			try{
				@SuppressWarnings("resource")
				CSVReader reader = new CSVReader(new FileReader(Constant.DOWNLOAD_PATH+"\\"+latestDownLoadedFileFromDownload(fileName,"csv")));
				List<String[]> li=reader.readAll();
				allRows=new ArrayList<List<String>>();
				Iterator<String[]> allCsvFile= li.iterator();
				int count=0;
				while(allCsvFile.hasNext()){
					if(count==rowToBeFetch)
						break;
					allRows.add(Arrays.asList(allCsvFile.next()));
					count+=1;
				}
			}
			catch (Throwable t) {
				t.printStackTrace();
			}finally{
			}
			return allRows;
		}
		
		/**
		 * This method is accepting 2 parameter . 1:The file name starting with 2:Extension of the file
		 * This Method is used to return the latest downloaded file with 
		 * the file name starting with the first parameter of the method and the
		 * extension is the 2nd parameter of the method.
		 * After returning the latest downloaded file it will keep 50 latest file and will delete old files with same name
		 * 
		 */
		public static String latestDownLoadedFileFromDownload(String fileName,String fileType){
			String returnFile="";
			try{
				int counter=0;
				File uploadDirectory = new File(Constant.SRC_FOLDER);
				File[] downloadedFiles = uploadDirectory.listFiles();
				Arrays.sort(downloadedFiles, new Comparator<File>() {
					@Override
					public int compare(File firstFile, File secondFile) {
						return Long.valueOf(secondFile.lastModified()).compareTo(firstFile.lastModified());
					}
				});
				for (File file : downloadedFiles) {
					if (file.isFile()){ 
						if(file.getName().startsWith(fileName) && file.getName().endsWith(fileType)) {
							if(returnFile.equals("")){
								returnFile=file.getName();
							}
							if(counter>=20 && file.exists() && file.isFile()){
								file.delete();
							}
							counter+=1;
						}
					}
				}
				if(returnFile.equals("")){
					returnFile="No such file available in Download folder!!";
				}   
			}catch(Exception e){
				returnFile="No such file available in Download folder!!";
				 
			}
			return returnFile;
		}
		
		/**
		 *Hold and return table header value
		 * */
		public static ArrayList<String> holdAndReturnTableHeader() throws Exception{
			ArrayList<String> toBeReturnRows=null;
			try{
				toBeReturnRows=new ArrayList<String>();
				List<WebElement> tableHeader = d.findElements(By.xpath("//*[@id='tablehead']/tr/th"));
				for(int j=1;j<=tableHeader.size();j++){
					Utility.moveTheElement("//*[@id='tablehead']/tr/th["+j+"]");
					toBeReturnRows.add(getWebElementXpath_D("//*[@id='tablehead']/tr/th["+j+"]").getText());
				}
				Reporter.log("holded all the value from table header");
			}catch(Exception e){
				 
				throw e;
			}
			return toBeReturnRows;
		}
		
		// Go to respective Dashboard page
		public static void goToDashboard(String dashboardFolder, String dashboardname) throws Throwable {
			try {
				//Go to My Dashboards
				String str = Dto.getURl();
				String str1[] = str.split("com");
				String navurl = str1[0];
				//d.navigate().to("https://core.stg1.resourceadvisor.schneider-electric.com/dashboard/app");
				d.navigate().to(navurl + "com/dashboard/app");
				aJaxWait();
				Thread.sleep(5000);
				// Click on Customize Icon
				explicitWait_Xpath(locators.getProperty("DashboardIcon"));
				getWebElementXpath("DashboardIcon").click();
				aJaxWait();
				Thread.sleep(2000);

				//filtered the dashboard name
				getWebElementXpath_D("//*[@id='sidebar']/div[1]/div/div[2]/div/div[2]/input").sendKeys(dashboardname);	
				Thread.sleep(5000);
				List<WebElement> dashboard = d.findElements(By.xpath("//perfect-scrollbar/div/div[1]/div/div/div/div/div[1]/div[1]/div/span[2]"));
				Reporter.log("dashboard.size()..."+dashboard.size());
				
				String dbName="";
				for(int i=1;i<=dashboard.size();i++){
					dbName=getWebElementXpath_D("//perfect-scrollbar/div/div[1]/div/div["+i+"]/div/div/div[1]/div[1]/div/span[2]").getText();
					if(dbName.equalsIgnoreCase(dashboardname)) {
						aJaxWait();
						d.findElement(By.xpath("//perfect-scrollbar/div/div[1]/div/div["+i+"]/div/div/div/div[2]/ra-tooltip[1]/div/button")).click();
						Thread.sleep(3000);
						break;
					}
				}
				//click on respective dashboard
				String actualDashboardName = getWebElementXpath("DashboardTitle").getText();
				// Verify the user is navigates to the dashboard
				Assert.assertEquals(actualDashboardName, dashboardname);
				Reporter.log("Click on the existing dashboard '"+ dashboardname + "'");
			} catch (Throwable t) {
				t.printStackTrace();
				throw t;

			}
		}
		
		public static void verifyDashboardExists(String dashboardTitle) {
			try{
				Thread.sleep(1000);
				// Click on Customize Icon
				getWebElementXpath("DashboardIcon").click();
				aJaxWait();
				Thread.sleep(2000);

				getWebElementXpath_D("//*[@id='sidebar']/div[1]/div/div[2]/div/div[2]/input").sendKeys(dashboardTitle);
				Thread.sleep(1000);
				List<WebElement> dashboard = d.findElements(By.xpath("//perfect-scrollbar/div/div[1]/div/div/div/div/div[1]/div[1]/div/span[2]"));
				Reporter.log("dashboard.size()..." + dashboard.size());
				if(dashboard.size()== 0){
					// Close Dashboard Search tab
					WebElement element = d.findElement(By.xpath(locators.getProperty("SecondLayer_closeIcon")));
					JavascriptExecutor js = (JavascriptExecutor) d;
					js.executeScript("arguments[0].click();", element);
					aJaxWait();
					Thread.sleep(5000);

					WebElement element1 = d.findElement(By.xpath(locators.getProperty("FirstLayer_closeIcon")));
					JavascriptExecutor js1 = (JavascriptExecutor) d;
					js1.executeScript("arguments[0].click();", element1);
					aJaxWait();
					Thread.sleep(5000);
					
					Reporter.log("No Dashboards are existings with the name of "+dashboardTitle);

				}else{
					Reporter.log("Yes Dashboards are existings with the name of "+dashboardTitle);				
					String dbName="";
					Thread.sleep(3000);
					for(int i=1;i<=dashboard.size();i++){
						dbName=getWebElementXpath_D("//perfect-scrollbar/div/div[1]/div/div["+i+"]/div/div/div[1]/div[1]/div/span[2]").getText();
						if(dbName.equalsIgnoreCase(dashboardTitle)) {
							aJaxWait();
							d.findElement(By.xpath("//perfect-scrollbar/div/div[1]/div/div["+i+"]/div/div/div/div[2]/ra-tooltip[1]/div/button")).click();
							Thread.sleep(3000);
							break;
						}
					}
					//click on respective dashboard
					Thread.sleep(2000);
					String actualDashboardName = getWebElementXpath("DashboardTitle").getText();
					// Verify the user is navigates to the dashboard
					Assert.assertEquals(actualDashboardName, dashboardTitle);
					Reporter.log("Click on the existing dashboard '"+ dashboardTitle + "'");
					Utility.deleteDashboard();
				}

			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		public static void deleteDashboard() {
			try {
				Thread.sleep(3000);
				aJaxWait();
				String icon = locators.getProperty("Dashboard_Settings_Icon");
				Utility.moveTheElement(icon);
				// Click on dashboard settings icon
				getWebElementXpath("Dashboard_Settings_Icon").click();
				aJaxWait();
				Thread.sleep(2000);

				Reporter.log("Click on dashboard settings icon");

				// Click on delete link
				getWebElementXpath("Dashboard_Delete_Link").click();
				aJaxWait();
				Thread.sleep(2000);
				Reporter.log("Click on delete link");

				// Click on confirmation Ok button
				getWebElementXpath("Dashboard_Delete_Confirmation_Ok_button").click();
				aJaxWait();
				Thread.sleep(2000);
				Reporter.log("Click on confirmation Ok button");
				d.navigate().refresh();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void deleteTags(String categoryName) throws Throwable {
			try {//Delete the tags from Define Tags tab
				getWebElementActionXpath("DefineTags_Tab").click();
				//Search with category name
				getWebElementXpath("CategoriesTag_FilterIcon").click();			
				d.findElement(By.xpath("(//div[contains(@class,'input-group')]/input[contains(@class,'form-control')])[2]")).click();
				d.findElement(By.xpath("(//div[contains(@class,'input-group')]/input[contains(@class,'form-control')])[2]")).sendKeys(categoryName);
				Thread.sleep(2000);
				if(d.findElements(By.xpath("(//span[@class='info ellipsis cursor-click'])[1]/span")).size()>0) {
					if(d.findElement(By.xpath("(//span[@class='info ellipsis cursor-click'])[1]/span")).getText().equalsIgnoreCase(categoryName)) {
						//Click on category name
						d.findElement(By.xpath("(//span[@class='info ellipsis cursor-click'])[1]/span")).click();
						Thread.sleep(2000);
				
						int  tagsList = d.findElements(By.xpath("//div[@id='tagList']/i[1]")).size();
						String tagPath=null;
						boolean flag=false;
						for(int i=1;i<=tagsList;i++) {
							tagPath = "//div[@id='tagList']/i[1]";
							d.findElement(By.xpath(tagPath)).click();
							Thread.sleep(2000);
					
							flag = removeTagsFromSiteLocation(categoryName);
							if(flag== true) {
						getWebElementXpath("DefineTags_Tab").click();
						//Search with category name
						getWebElementXpath("CategoriesTag_FilterIcon").click();			
						d.findElement(By.xpath("(//div[contains(@class,'input-group')]/input[contains(@class,'form-control')])[2]")).click();
						d.findElement(By.xpath("(//div[contains(@class,'input-group')]/input[contains(@class,'form-control')])[2]")).sendKeys(categoryName);
						Thread.sleep(2000);
						if(d.findElement(By.xpath("(//span[@class='info ellipsis cursor-click'])[1]/span")).getText().equalsIgnoreCase(categoryName)) {
						//Click on category name
						d.findElement(By.xpath("(//span[@class='info ellipsis cursor-click'])[1]/span")).click();
						Thread.sleep(2000);
						
						d.findElement(By.xpath(tagPath)).click();
						Thread.sleep(2000);
					}
					}
					List<WebElement> alert = d.findElements(By.xpath("//div[@class='modal-footer-btns']/div[contains(text(),'OK')]"));
					if(alert.size()>0) {
						d.findElement(By.xpath("//div[@class='modal-footer-btns']/div[contains(text(),'OK')]")).click();
					}
					
					
				}
				
				Utility.moveTheScrollToTheDown();
				//Click on Delete button
				getWebElementActionXpath("ManageTags_DeleteButton").click();
				//Click on OK bytton from popup
				getWebElementActionXpath_D("//div[contains(@class,'btn btn-success') and normalize-space()='Yes']").click();
				Thread.sleep(3000);
				printLog("Successfully deleted the Category");
				}else {
					printLog("There is not category exists");
				}
				Utility.moveTheScrollToTheTop();
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw e;
				}
			}

		private static boolean removeTagsFromSiteLocation(String categoryName) throws Throwable {
			boolean flagActive=false;
			try {
				List<WebElement> alert1 = d.findElements(By.xpath("//div[@class='ra-pam-control location-list']//li"));
				if(alert1.size()>0) {
					String sitename = d.findElement(By.xpath("//div[@class='ra-pam-control location-list']//li")).getText();
					String[] site = sitename.split("\\\\");
					d.findElement(By.xpath("//div[@class='ra-pam-modal-action-btns']/button[contains(text(),'OK')]")).click();
					Thread.sleep(8000);
					Utility.moveTheScrollToTheTop();
					//Click on 'Apply Tags to Location' tab
					getWebElementActionXpath("ApplyTagsToLocation_Tab").click();
					Thread.sleep(5000);
					
					// Search with location site name
					getWebElementActionXpath("FilterByLocation").click();
					getWebElementXpath("FilterByLocation").clear();
					getWebElementXpath("FilterByLocation").sendKeys(site[site.length-1]);
					aJaxWait();
					
					//se-panel//ui-view//div[2]//div[3]/div[1]/div[26]/div[2]/span/span
					//Select first site checkbox
					//getWebElementActionXpath_D("//input[@type='checkbox' and contains(@id,'select-all')]").click();
					//getWebElementActionXpath_D("(//tbody[@class='k-table-tbody']/tr)[1]/td[1]//input[@type='checkbox']").click();
					getWebElementActionXpath("SelectAll_Checkbox").click();
					Thread.sleep(5000);
					
					//Click on Remove Tags tab
					getWebElementActionXpath("RemoveTagsTab").click();
					Thread.sleep(2000);
					new Select(getWebElementXpath("SelectCategory_dropdown")).selectByVisibleText(categoryName);
					Thread.sleep(2000);
					getWebElementActionXpath("RemoveAllTags_button").click();
					Thread.sleep(4000);
					
					getWebElementActionXpath("ManageTages_OK_button").click();
					Thread.sleep(5000);
					flagActive=true;
				}
			}catch (Throwable e) {
					throw e;
					}
			return flagActive;
			}
			
		


		public static void deleteMeasurement(String measurementName)throws Throwable {
			try {
				getWebElementXpath("ManageMeasurement_SearchBar").click();
				getWebElementXpath("ManageMeasurement_SearchBar").clear();
				getWebElementXpath("ManageMeasurement_SearchBar").sendKeys(measurementName);
				Thread.sleep(5000);
				List<WebElement> list = d.findElements(By.xpath("//span[@class='commoditydef']/h4[1]"));
				if (list.size() == 1) {
						// Click on the measurement name from results
						getWebElementXpath_D("//*[@class='commoditydef']//h4[normalize-space()='"+measurementName+"']").click();
						Thread.sleep(2000);

						// Click on Advanced Tab
						getWebElementXpath_D("//span[normalize-space()='Advanced']").click();
						Thread.sleep(2000);

						// Click on 'Delete this measurement' link
						getWebElementXpath_D("//button[normalize-space()='Delete this measurement']").click();
						Thread.sleep(2000);

						// Click on Delete button
						getWebElementXpath_D("//div[@class='modal-footer-btns']/button[normalize-space()='Delete']").click();
						Thread.sleep(2000);

						// Click on Yes button from popup
						getWebElementXpath_D("//div[@class='btn btn-primary'][normalize-space()='Yes']").click();
						Thread.sleep(5000);
						printLog("Successfully deleted the measurement "+measurementName);
					}
				Thread.sleep(3000);
			} catch (Throwable t) {
			throw t;

		}
	}
	
	// Go to respective Dashboard page
	public static void goToPAMDashboard(String dashboardFolder, String dashboardname) throws Throwable {
		try {
			//Go to My Dashboards
			String str = Dto.getURl();
			String str1[] = str.split("com");
			String navurl = str1[0];
			//d.navigate().to("https://core.stg1.resourceadvisor.schneider-electric.com/dashboard/app");
			d.navigate().to(navurl + "com/dashboard/app");
			aJaxWait();
			Thread.sleep(8000);
			d.navigate().refresh();
			aJaxWait();
			Thread.sleep(12000);
			Utility.moveTheScrollToTheTop();
			// Click on Customize Icon
			explicitWait_Xpath(locators.getProperty("DashboardIcon"));
			getWebElementActionXpath("DashboardIcon").click();
			aJaxWait();
			Thread.sleep(5000);

			//filtered the dashboard name
			//getWebElementXpath_D("//*[@id='sidebar']/div[1]/div/div[2]/div/div[2]/input").sendKeys(dashboardname);	
			getWebElementXpath_D("//div[contains(@class,'search-container')]/input[@placeholder='Search Dashboards']").sendKeys(dashboardname);
			Thread.sleep(12000);
			List<WebElement> dashboard = d.findElements(By.xpath("//perfect-scrollbar/div/div[1]/div/div/div/div/div[1]/div[1]/div/span[2]"));
			Reporter.log("dashboard.size()..."+dashboard.size());
			
			String dbName="";
			for(int i=1;i<=dashboard.size();i++){
				dbName=getWebElementXpath_D("//perfect-scrollbar/div/div[1]/div/div["+i+"]/div/div/div[1]/div[1]/div/span[2]").getText();
				if(dbName.equalsIgnoreCase(dashboardname)) {
					aJaxWait();
					d.findElement(By.xpath("//perfect-scrollbar/div/div[1]/div/div["+i+"]/div/div/div/div[2]/ra-tooltip[1]/div/button")).click();
					Thread.sleep(5000);
					break;
				}
			}
			//click on respective dashboard
			String actualDashboardName = getWebElementXpath("DashboardTitle").getText();
			// Verify the user is navigates to the dashboard
			//Assert.assertEquals(actualDashboardName, dashboardname);
			Reporter.log("Click on the existing dashboard '"+ dashboardname + "'");
			Thread.sleep(15000);
			Utility.deleteFilter();
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;

		}
	}
	
	// Delete all widgets in the dashbaord
	public static void deleteAllWidgets() throws Throwable {

		try {// compress emptyspace to make widgets formatted
			Utility.compressEmptySpace();

			// calculate the number of widgets are available
			int widgetNames = d.findElements(By.xpath("//div/gridster/gridster-item")).size();
			String widgetNameToDelete="";
			WebElement xpathToCLick = null;
			Actions actions = new Actions(d);
			for(int i=1;i<=widgetNames;i++) {
				widgetNameToDelete=getWebElementActionXpath_D("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//div[contains(@class,'title-label')]").getText();
					// Used this block if the board have more that 3 widget the scroll should move to 150 pixcel down
					if (i > 2 && i % 2 == 0) {
						JavascriptExecutor js = (JavascriptExecutor) d;
						js.executeScript("window.scrollBy(0,150)", "");
						Thread.sleep(2000);
					}else{
						moveTheScrollToTheTop();
					}
					
					
					// move the control to respective widget
					Utility.moveTheElement("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//div[contains(@class,'title-label')]");
					
					/*//As the delete button is not visible for Chart tab we are clicked to Table/Statistics tab
					List<WebElement> tableSize = d.findElements(By.xpath("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//a[text()='Table']"));
					System.out.println("tableSize.size()...."+tableSize.size());
					if(tableSize.size()>0) {
						getWebElementActionXpath_D("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//a[text()='Table']").click();
					}else {
						List<WebElement> statsSize = d.findElements(By.xpath("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//a[text()='Statistics']"));
						if(statsSize.size()>0) {
						getWebElementActionXpath_D("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//a[text()='Statistics']").click();
						}
					}*/
					
					Thread.sleep(2000);
					// Click the setting icon for the widget to delete
					xpathToCLick = d.findElement(By.xpath("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//div[contains(@class,'title-bar-icon')]/i"));
					actions.moveToElement(xpathToCLick,1,1).click().build().perform();
					Thread.sleep(2000);
					// Click on delete link
					xpathToCLick = d.findElement(By.xpath("//a[@id='wcWidgetDelete']/span"));
					actions.moveToElement(xpathToCLick,1,1).click().build().perform();
					Thread.sleep(2000);
					// If any widget deleted then the number of widgets also decreased
					widgetNames = widgetNames - 1;
					i = i - 1;
					
			}
			Thread.sleep(10000);
			printLog("Widget(s) deleted succesfully. ");
		} catch (Exception t) {
			throw t;
		}
	}

		
	
	public static void createOrSelectFromLegacyDB(String dashboardname) throws Exception {
		try {
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//input[@id='ctl00_ContentPlaceHolder1_ddlDashboards_Input']").click();
			Thread.sleep(3000);
			
			int numberOfLDB=d.findElements(By.xpath("(//div[@id='divDashboarSelectorTree']//li[@class='rtLI']//span[@class='rtIn'])")).size();
			String dbName="";
			boolean createFlag=true;
			for(int i=1;i<=numberOfLDB;i++) {
				dbName=getWebElementXpath_D("(//div[@id='divDashboarSelectorTree']//li[@class='rtLI']//span[@class='rtIn'])["+i+"]").getText();
				if(dbName.contentEquals(dashboardname)) {
					getWebElementXpath_D("(//div[@id='divDashboarSelectorTree']//li[@class='rtLI']//span[@class='rtIn'])["+i+"]").click();
					createFlag=false;
					aJaxWait();
					Thread.sleep(5000);
					printLog(dashboardname+" for legacy DB is selected successfully.");
					break;
				}
			}
			
			if(createFlag) {
				getWebElementActionXpath_D("//img[contains(@id,'ctl00_ContentPlaceHolder1_Img13') and @src='App_Themes/Summit/images/dashboard_add.png']").click();
				Thread.sleep(2000);
				d.switchTo().frame(getWebElementActionXpath_D("(//table[contains(@class,'rwTable')]//tr[@class='rwContentRow']//iframe)[1]"));
				Thread.sleep(1000);
				getWebElementActionXpath_D("//input[@id='rtbDashboardTitle']").click();
				getWebElementXpath_D("//input[@id='rtbDashboardTitle']").clear();
				getWebElementXpath_D("//input[@id='rtbDashboardTitle']").sendKeys(dashboardname);
				Thread.sleep(1000);
				getWebElementActionXpath_D("//span[@id='lblsave' and text()='Save']").click();
				printLog("New "+dashboardname+" legacy DB created successfully.");
				
			}else {
				Utility.deleteLegacyDBWidget();
			}
			
		} catch (Exception e) {
			throw e;
		}

	}

	// Go to respective Dashboard page
	public static void goToMyDashboardWidgets(String dashboardFolder, String dashBoardToSelect) throws Throwable {
		try {
			
			//Check if My Dashboard selected
		if(!isElementPresent_D("//div[contains(@class,'box db-library')]/i[contains(@class,'fal fa-bars')]")) {
			//Dashboart icon
			try {
				getWebElementActionXpath_D("//a/span[text()='Dashboards' and @class='menu-text']").click();
			}catch(Exception e) {
				Thread.sleep(3000);
				getWebElementActionXpath_D("(//div[@id='navbarCollapse']/ul/li//a/span)[1]").click();
			}
			//Clicked on My Dashboards
			Thread.sleep(2000);
			getWebElementActionXpath_D("//a[@class='menu-first-child bold'][contains(text(),'My Dashboards')]").click();
			aJaxWait();
		}
		
		
		
		
			// Click on Customize Icon
			getWebElementActionXpath_D("//div[contains(@class,'box db-library')]/i[contains(@class,'fal fa-bars')]").click();
			Thread.sleep(5000);

			// Click on the dashboard
			String path = "//span[contains(text(),'"+dashboardFolder+"')]";
			getWebElementActionXpath_D(path).click();
			Thread.sleep(3000);
			Reporter.log("Click on the dashboard '" + dashboardFolder + "'");
			
			int widgetSize=d.findElements(By.xpath("//div[contains(@class,'dashboard-wrapper')]//span[contains(@class,'dashboard-title')]")).size();
			String widgetName="";
			for(int i=1;i<=widgetSize;i++) {
				widgetName=getWebElementActionXpath_D("(//div[contains(@class,'dashboard-wrapper')]//span[contains(@class,'dashboard-title')])["+i+"]").getText();
				if(widgetName.equalsIgnoreCase(dashBoardToSelect)) {
					getWebElementActionXpath_D("(//div[contains(@class,'dashboard-wrapper')])["+i+"]//button[text()='View']").click();
					Thread.sleep(8000);
					break;
				}
			}

			printLog("Selected "+dashBoardToSelect+" .");
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;

		}
	}
	// Click on existing widget customized settings icon and edit link
			public static void clickOnEditLinkOfWidget(String clickToLink) throws Exception{
				try {
					moveTheScrollToTheTop();
					WebElement settingsIcon = d.findElement(By.xpath("//gridster/gridster-item[1]//i[contains(@class,'widget-menu-ellipse')]"));
					Actions act = new Actions(d); 
					act.moveToElement(settingsIcon,1,1).perform(); 
					Actions actions = new Actions(d); 
					actions.moveToElement(settingsIcon,1,1).perform();
					Thread.sleep(2000);
					WebElement settings = d.findElement(By.xpath("//gridster/gridster-item[1]//i[contains(@class,'widget-menu-ellipse')]")); 
					actions.moveToElement(settings,1,1).click().build().perform(); 
					Thread.sleep(2000);
					WebElement editIcon =null;
					if(clickToLink.equals("Edit"))
						editIcon = d.findElement(By.xpath("//a[@id='wcEditWidget']/span[contains(text(),'"+clickToLink+"')]"));
					else if(clickToLink.equals("Visit Page"))
						editIcon = d.findElement(By.xpath("//a[@id='wcVisitWidget']/span[contains(text(),'"+clickToLink+"')]"));
					
					act.moveToElement(editIcon,1,1).click().build().perform(); 
					printLog("Click on Edit link from widget configuration");
					Thread.sleep(5000);

					} catch (Throwable e) {
						throw e;
					}
				}
	
			// This function is only related to Push Widget from widget library test cases
			public static void pushWidgetFromWidgetLibrary(String analysisName) throws Throwable{
				try {

					// Navigate to 'AutoTest_Dashboards' and Select 'All Cards New Widgets' 
					Utility.goToMyDashboardWidgets("AutoTest_Dashboards","All Cards New Widgets");
					
					//Removed global filter if PAMTest_Naperville, IL is present
					if(analysisName.equals("Trend Analysis") && (isElementPresent_D("//div[3]/i[@class='fal fa-times img-space1']") || isElementPresent_D("//div[4]/i[@class='fal fa-times img-space1']"))) {
						getWebElementActionXpath_D("//div[contains(@class,'right gf-btn-close')]").click();
					}
					
					//Trying to delete if any other widgets were available due to test case failure.
					Utility.deleteAllWidgets();
					
					//Add widgets
					getWebElementXpath_D("//button[text()='Add Widget(s)']").click();
					Thread.sleep(2000);
					printLog("Clicked on add Widgets.");
					
					//Expand widgets from blank widget
					getWebElementActionXpath_D("//a[text()='Widgets']").click();
					Thread.sleep(2000);
					//On Analysis
					getWebElementActionXpath_D("//span[text()='Analysis']").click();
					Thread.sleep(5000);
					printLog("Clicked on analysis to select Analysis");
					
					//select card
					int widgetSize=d.findElements(By.xpath("//div[@class='ps-content']/div[contains(@class,'widget-hover')]")).size();
					String widgetName="";
					//Add to dashboard
					for(int i=1;i<=widgetSize;i++) {
						widgetName=getWebElementActionXpath_D("(//div[@class='ps-content']/div[contains(@class,'widget-hover')])["+i+"]//label[contains(@class,'widget-title')]").getText();
						if(widgetName.equalsIgnoreCase(analysisName)) {
							getWebElementActionXpath_D("(//div[@class='ps-content']/div[contains(@class,'widget-hover')])["+i+"]//button").click();
							Thread.sleep(5000);
							break;
						}
					}
					printLog("Selected the Analysis");
					
					//Close the 2 pop up
					getWebElementActionXpath_D("(//a/i[@class='fal fa-times'])[1]").click();
					Thread.sleep(2000);
					
					getWebElementXpath_D("//div[contains(text(),'Configure Widget')]").click();
					Thread.sleep(5000);
					printLog("Clicked on configure widget.");
					
					//selecting site
					
					if(analysisName.equals("Comparison Analysis")) {
						getWebElementActionXpath_D("//pam-site//ra-site-search//input[@class='light']").click();
						getWebElementXpath_D("//pam-site//ra-site-search//input[@class='light']").sendKeys("PAMTest_Herentals, Biscuits");
						Thread.sleep(1000);
						getWebElementActionXpath_D("//div[@class='site-name-body']/span[contains(text(),'PAMTest_Herentals, Biscuits')]").click();
						getWebElementActionXpath_D("//i[@class='far fa-plus-square']").click();
						printLog("Selected site as PAMTest_Herentals, Biscuits.");
						
						//span[contains(text(),'PAMTest_Naperville, IL')]
						getWebElementActionXpath_D("//pam-site//ra-site-search//input[@class='light']").click();
						getWebElementXpath_D("//pam-site//ra-site-search//input[@class='light']").sendKeys(testData.getProperty("PAMTestNapervilleIL"));
						Thread.sleep(1000);
						getWebElementActionXpath_D("//div[@class='site-name-body']/span[contains(text(),'PAMTest_Naperville, IL')]").click();
						printLog("Selected site as PAMTest_Naperville, IL.");
					}else {
						getWebElementActionXpath_D("//pam-site//ra-site-search//input[@class='light']").click();
						getWebElementXpath_D("//pam-site//ra-site-search//input[@class='light']").sendKeys(testData.getProperty("PAMTestCapriataSaiwa"));
						Thread.sleep(1000);
						getWebElementActionXpath_D("//div[@class='site-name-body']/span[contains(text(),'PAMTest_Capriata/Saiwa')]").click();
						printLog("Selected site as PAMTest_Capriata/Saiwa.");
					}
					
					
					//Save
					getWebElementXpath("WidgetConfigSaveButton").click();
					Thread.sleep(20000);
					printLog("Saved the widget");
					} catch (Throwable e) {
						throw e;
					}
				}


			public static void deleteDownloadFiles(String fileName, String extension) {
				try{
					File directory = new File(Constant.DOWNLOAD_PATH);
					for (File f : directory.listFiles()) {
						if(f.getName().startsWith(fileName) && f.getName().endsWith(extension)) {
							f.delete();
				    }
				}
					printLog("Deleted the "+fileName+" from download folder.");
				} catch (Throwable e) {
					throw e;
				}
				
			}
			
			public static List<String[]> readCSV(String filename) throws IOException {
		        try (CSVReader reader = new CSVReader(new FileReader(filename))) {
		            return reader.readAll();
		        }
		    }
			public static boolean compareCSV(String pamCSVFile,String baselineCSVFile) throws Exception {
				boolean fileComparison=true;
				try {

					List<String[]> pamData = readCSV(pamCSVFile);
		            List<String[]> baselineData = readCSV(baselineCSVFile);
		            
		           // System.out.println(pamData.size()+":::::::::::"+baselineData.size());
		            
		            int pamDataSize=pamData.size();
		            int baselineDataSize=baselineData.size();
		            String[] pamOriginal =null;
		            String[] baselineOriginal =null;
		            double baselineAfterConvert=0.0;
		            double pamAfterConvert=0.0;
		            double pamOriginalValue,baselineOriginalValue;
		            int breakCounter=0;
		            int pamIndex=2;
		            int cebmIndex=5;
		            br:
		            for (int i = 0; i < pamDataSize; i++) {
		            //for (int i = 2; i < 100; i++) {
		                 pamOriginal = pamData.get(pamIndex);
		                 baselineOriginal = baselineData.get(cebmIndex);
		                 pamOriginalValue=Double.parseDouble((pamOriginal[1].replaceAll(",", "").length()==0 ? "0.0" : pamOriginal[1].replaceAll(",", "")));
		                 baselineOriginalValue=Double.parseDouble((baselineOriginal[3].replaceAll(",", "").length()==0 ? "0.0" : baselineOriginal[3].replaceAll(",", "")));
		                
		                
		                //Baseline convert
		                baselineAfterConvert=0.0;
		               // if(Math.abs(baselineOriginalValue)<=1000) {
		                String valllll=String.valueOf(baselineOriginalValue);
		   			 	//String vals=valllll.substring(0, (valllll.indexOf(".")+5));
		                String vals=valllll.substring(valllll.indexOf(".")+1, (valllll.indexOf(".")+5));
		            /*    
		   			 if(Double.parseDouble(vals)<=1000) {
		                	baselineAfterConvert=Math.round(Math.round(baselineOriginalValue * 1e2) / 1e2);
		                } else {
		                	baselineAfterConvert=Math.round(baselineOriginalValue);
		                }
		                */
		   			baselineAfterConvert=Math.round(baselineOriginalValue);
		                //PAM convert
		                pamAfterConvert=0.0;
		                pamAfterConvert=Math.round(pamOriginalValue);
		               //pamAfterConvert=pamOriginalValue;
		                
		                //interval missing condition
		               double pamValtoCompare=pamAfterConvert;
		               double baselineValTOCompare=baselineAfterConvert;
		                if(baselineOriginal[0].equals(pamOriginal[0])) {
			                if (Double.compare(pamValtoCompare, baselineValTOCompare) != 0) {
			                	fileComparison=false;
			                	printLog("CEBM "+baselineOriginal[0]+" value is "+baselineValTOCompare+", PAM "+pamOriginal[0]+" value is "+pamValtoCompare+".");
			                	System.out.println("CEBM "+baselineOriginal[0]+" value is "+baselineValTOCompare+", PAM "+pamOriginal[0]+" value is "+pamValtoCompare+".");
			                	breakCounter++;
			                }else {
			                	//System.out.println(baselineAfterConvert+":::::"+i+"::::"+pamAfterConvert);
			                }
			                
			                pamIndex=pamIndex+1;
				            cebmIndex=cebmIndex+1;
		                }else {
		                	 pamIndex=pamIndex+1;
		                	//To print the missing interval log
		                	System.out.println(pamOriginal[0]+" CEBM interval is missing interval.");
		                	printLog(pamOriginal[0]+" CEBM interval is missing interval.");
		                }
		                if(pamDataSize==pamIndex || baselineDataSize==cebmIndex)
		                	break br;
		            }            
		            System.out.println(pamIndex+":::::::::::"+cebmIndex);
				}catch(Exception e) {
					fileComparison=false;
					throw e;
				}
				return fileComparison;
			}
			
			
			public static boolean compareCSVDuplicate(String pamCSVFile,String baselineCSVFile) throws Exception {
				boolean fileComparison=true;
				try {

					List<String[]> pamData = readCSV(pamCSVFile);
		            List<String[]> baselineData = readCSV(baselineCSVFile);
		            
		           // System.out.println(pamData.size()+":::::::::::"+baselineData.size());
		            
		            int pamDataSize=pamData.size();
		            int baselineDataSize=baselineData.size();
		            String[] pamOriginal =null;
		            String[] baselineOriginal =null;
		            double baselineAfterConvert=0.0;
		            double pamAfterConvert=0.0;
		            double pamOriginalValue,baselineOriginalValue;
		            int breakCounter=0;
		            int pamIndex=2;
		            int cebmIndex=5;
		            br:
		            for (int i = 0; i < pamDataSize; i++) {
		            //for (int i = 2; i < 100; i++) {
		                 pamOriginal = pamData.get(pamIndex);
		                 baselineOriginal = baselineData.get(cebmIndex);
		                 pamOriginalValue=Double.parseDouble((pamOriginal[1].replaceAll(",", "").length()==0 ? "0.0" : pamOriginal[1].replaceAll(",", "")));
		                 baselineOriginalValue=Double.parseDouble((baselineOriginal[3].replaceAll(",", "").length()==0 ? "0.0" : baselineOriginal[3].replaceAll(",", "")));
		                
		                
		                //Baseline convert
		                baselineAfterConvert=0.0;
		               // baselineAfterConvert=convertAndReturnTheValForBaselineOnly(baselineOriginalValue);
		               // baselineAfterConvert=single_convertAndReturnTheValForBaselineOnly(baselineOriginalValue);
		                
		               baselineAfterConvert=Math.round(returnSingleDecemalValue(baselineOriginalValue));
		                //baselineAfterConvert=returnSingleDecemalValue(baselineOriginalValue);
		                
		                
		                //PAM convert
		                pamAfterConvert=0.0;
		                pamAfterConvert=Math.round(pamOriginalValue);
		              // pamAfterConvert=pamOriginalValue;
		                
		                //interval missing condition
		               double pamValtoCompare=pamAfterConvert;
		               double baselineValTOCompare=baselineAfterConvert;
		                if(baselineOriginal[0].equals(pamOriginal[0])) {
			                if (Double.compare(pamValtoCompare, baselineValTOCompare) != 0) {
			                	fileComparison=false;
			                	printLog("CEBM "+baselineOriginal[0]+" value is "+baselineValTOCompare+", PAM "+pamOriginal[0]+" value is "+pamValtoCompare+".");
			                	System.out.println("CEBM "+baselineOriginal[0]+" value is "+baselineValTOCompare+", PAM "+pamOriginal[0]+" value is "+pamValtoCompare+".");
			                	breakCounter++;
			                }else {
			                	//System.out.println(baselineAfterConvert+":::::"+i+"::::"+pamAfterConvert);
			                }
			                
			                pamIndex=pamIndex+1;
				            cebmIndex=cebmIndex+1;
		                }else {
		                	 pamIndex=pamIndex+1;
		                	//To print the missing interval log
		                	System.out.println(pamOriginal[0]+" CEBM interval is missing interval.");
		                	printLog(pamOriginal[0]+" CEBM interval is missing interval.");
		                }
		                if(pamDataSize==pamIndex || baselineDataSize==cebmIndex)
		                	break br;
		            }            
		            System.out.println(pamIndex+":::::::::::"+cebmIndex);
				}catch(Exception e) {
					fileComparison=false;
					throw e;
				}
				return fileComparison;
			}
			
			
			private static double convertAndReturnTheValForBaselineOnly(double number) {
				double baselineAfterConvert=0.00;
				try {
					//DecimalFormat decff = new DecimalFormat("#.##");
					DecimalFormat decff = new DecimalFormat("#.##");
					
					 String convertToString=String.valueOf(number);
					 String getTwoDecemalFromString=convertToString.substring(0, (convertToString.indexOf(".")+3));
					 double withOnlyTwoDecemalVal=Double.parseDouble(getTwoDecemalFromString);
					 
					// System.out.println("only 2 decemal points  "+withOnlyTwoDecemalVal);
					 String convertIntoString=String.valueOf(withOnlyTwoDecemalVal);
					 //System.out.println("Converted into string:   "+convertIntoString);
					 String absoluteVal=convertIntoString.split("\\.")[0];
					 String decemalValVal=convertIntoString.split("\\.")[1];
					 if(decemalValVal.length()==1) {
						 decemalValVal=decemalValVal+"0";
					 }
					 //System.out.println("Absolute val:  "+absoluteVal);
					// System.out.println("decemal point:  "+decemalValVal);
				     
					 //To round up right side val (<5=0) (>5=10) (==5=5)
					 int convertStringDecToInt=Integer.parseInt(decemalValVal);
					 int roundCondition=convertStringDecToInt%10;
					 int finalDecemalVal=0;
					// if(roundCondition>5) {
					 if(roundCondition>4) {
						 finalDecemalVal=((convertStringDecToInt/10)*10)+10;
			 	    }else if(roundCondition<5) {
			 	    	finalDecemalVal=(convertStringDecToInt/10)*10;
			 	    }else {
			 	    //	finalDecemalVal=Integer.parseInt(decemalValVal);
			 	    }
			 	  // System.out.println("Final decemal val ::   "+finalDecemalVal);
			 	   
			 	   if(finalDecemalVal==100) {
			 		  baselineAfterConvert=Math.round(withOnlyTwoDecemalVal);
			 	   }else {
			 		   String toBeConvert=absoluteVal+"."+finalDecemalVal;
			 		  baselineAfterConvert=Double.parseDouble(toBeConvert);
			 	   }
			 	 // System.out.println("Final compare val ::   "+baselineAfterConvert);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				return baselineAfterConvert;
			}
			
			
			
			private static double returnSingleDecemalValue(double number) {
				double baselineAfterConvert=0.00;
				try {
					
					String convertIntoString=String.valueOf(number);
					 String absoluteVal=convertIntoString.split("\\.")[0];
					 String decemalValVal=convertIntoString.split("\\.")[1];
					 if(decemalValVal.length()==1) {
						 decemalValVal=decemalValVal+"0";
					 }
					 decemalValVal=decemalValVal.substring(0,2);
					 String totalValue=absoluteVal+"."+decemalValVal;
					 double afterConvert=Double.parseDouble(totalValue);
					 baselineAfterConvert=afterConvert;
			 	 // System.out.println("Final compare val ::   "+baselineAfterConvert);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				return baselineAfterConvert;
			}
			
			
			
			private static double single_convertAndReturnTheValForBaselineOnly(double number) {
				double baselineAfterConvert=0.00;
				try {
					//DecimalFormat decff = new DecimalFormat("#.##");
					DecimalFormat decff = new DecimalFormat("#.#");
					 double withOnlyTwoDecemalVal=Double.parseDouble(decff.format(number));
					 
					// System.out.println("only 2 decemal points  "+withOnlyTwoDecemalVal);
					 String convertIntoString=String.valueOf(withOnlyTwoDecemalVal);
					 //System.out.println("Converted into string:   "+convertIntoString);
					 String absoluteVal=convertIntoString.split("\\.")[0];
					 String decemalValVal=convertIntoString.split("\\.")[1];
					 if(decemalValVal.length()==1) {
						 decemalValVal=decemalValVal+"0";
					 }
					 //System.out.println("Absolute val:  "+absoluteVal);
					// System.out.println("decemal point:  "+decemalValVal);
				     
					 //To round up right side val (<5=0) (>5=10) (==5=5)
					 int convertStringDecToInt=Integer.parseInt(decemalValVal);
					 int roundCondition=convertStringDecToInt%10;
					 int finalDecemalVal;
					 if(roundCondition>5) {
						 finalDecemalVal=((convertStringDecToInt/1)*1)+0;
			 	    }else if(roundCondition<5) {
			 	    	finalDecemalVal=(convertStringDecToInt/1)*1;
			 	    }else {
			 	    	finalDecemalVal=Integer.parseInt(decemalValVal);
			 	    }
			 	  // System.out.println("Final decemal val ::   "+finalDecemalVal);
			 	   
			 	 //  if(finalDecemalVal>5) {
			 		//  baselineAfterConvert=Math.round(withOnlyTwoDecemalVal);
			 	   //}else {
			 		   String toBeConvert=absoluteVal+"."+finalDecemalVal;
			 		  baselineAfterConvert=Double.parseDouble(toBeConvert);
			 	   //}
			 	 
					 
					 baselineAfterConvert=withOnlyTwoDecemalVal;
			 	 // System.out.println("Final compare val ::   "+baselineAfterConvert);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				return baselineAfterConvert;
			}
			
			
			public static boolean oneTharseHold(String pamCSVFile,String baselineCSVFile) throws Exception {
				boolean fileComparison=true;
				try {

					List<String[]> pamData = readCSV(pamCSVFile);
		            List<String[]> baselineData = readCSV(baselineCSVFile);
		            
		           // System.out.println(pamData.size()+":::::::::::"+baselineData.size());
		            
		            int pamDataSize=pamData.size();
		            int baselineDataSize=baselineData.size();
		            String[] pamOriginal =null;
		            String[] baselineOriginal =null;
		            double baselineAfterConvert=0.0;
		            double pamAfterConvert=0.0;
		            double pamOriginalValue,baselineOriginalValue;
		            int breakCounter=0;
		            int pamIndex=2;
		            int cebmIndex=5;
		            br:
		            for (int i = 0; i < pamDataSize; i++) {
		            //for (int i = 2; i < 100; i++) {
		                 pamOriginal = pamData.get(pamIndex);
		                 baselineOriginal = baselineData.get(cebmIndex);
		                 pamOriginalValue=Double.parseDouble((pamOriginal[1].replaceAll(",", "").length()==0 ? "0.0" : pamOriginal[1].replaceAll(",", "")));
		                 baselineOriginalValue=Double.parseDouble((baselineOriginal[3].replaceAll(",", "").length()==0 ? "0.0" : baselineOriginal[3].replaceAll(",", "")));
		                
		                
		                //Baseline convert
		                baselineAfterConvert=0.0;
		                //baselineAfterConvert=Math.round(baselineOriginalValue);
		                baselineAfterConvert=baselineOriginalValue;
		                
		                //PAM convert
		                pamAfterConvert=0.0;
		                //pamAfterConvert=Math.round(pamOriginalValue);
		               pamAfterConvert=pamOriginalValue;
		                
		                //interval missing condition
		               double pamValtoCompare=pamAfterConvert;
		               double baselineValTOCompare=baselineAfterConvert;
		                if(baselineOriginal[0].equals(pamOriginal[0])) {
			                //if (Integer.compare(pamValtoCompare, baselineValTOCompare) != 0) {
		                	
		                	if(((baselineAfterConvert-pamAfterConvert)>1) || ((pamAfterConvert-baselineAfterConvert)>1)) {
			                	fileComparison=false;
			                	printLog("CEBM "+baselineOriginal[0]+" value is "+baselineValTOCompare+", PAM "+pamOriginal[0]+" value is "+pamValtoCompare+".");
			                	//System.out.println("CEBM "+baselineOriginal[0]+" value is "+baselineValTOCompare+", PAM "+pamOriginal[0]+" value is "+pamValtoCompare+".");
			                	breakCounter++;
			                }else {
			                	//System.out.println(baselineAfterConvert+":::::"+i+"::::"+pamAfterConvert);
			                }
			                
			                pamIndex=pamIndex+1;
				            cebmIndex=cebmIndex+1;
		                }else {
		                	 pamIndex=pamIndex+1;
		                	//To print the missing interval log
		                	System.out.println(pamOriginal[0]+" CEBM interval is missing interval.");
		                	printLog(pamOriginal[0]+" CEBM interval is missing interval.");
		                }
		                if(pamDataSize==pamIndex || baselineDataSize==cebmIndex)
		                	break br;
		            }            
		            System.out.println(pamIndex+":::::::::::"+cebmIndex);
				}catch(Exception e) {
					fileComparison=false;
					throw e;
				}
				return fileComparison;
			}
			
}
			

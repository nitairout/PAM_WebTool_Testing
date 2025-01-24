package regression.DashboardsAndWidgets.LegacyDashBoardAndWidgets;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies viewing dashboard/widgets in kiosk mode
 */
public class QIDM_177_LegacyViewDashboardinKioskMode extends TestBase{
	LoginTC login = null;
	boolean widgetDeleteFlag=true;
	
	@Test
	public void legacyViewDashboardinKioskMode() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Legacy Dashboards'
			String dashBoardPath="//*[@id='navbarCollapse']/ul/li[1]/div[1]/a/span";
			try{
			getWebElementActionXpath_D(dashBoardPath).click();
			}catch(Exception e){
				Thread.sleep(3000);
				getWebElementActionXpath_D("//ra-header/div[1]/div[1]/ra-menu[1]/nav[1]/div[1]/ul[1]/li[1]/div[1]/a[1]/span[1]").click();
			}
			String legasyPath="//a[@class='menu-first-child bold'][contains(text(),'Legacy Dashboards')]";
			Utility.moveTheElementandClick(legasyPath);
			Thread.sleep(2000);
			
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//input[@id='ctl00_ContentPlaceHolder1_ddlDashboards_Input']").click();
			Thread.sleep(3000);
			
			int numberOfLDB=d.findElements(By.xpath("(//div[@id='divDashboarSelectorTree']//li[@class='rtLI']//span[@class='rtIn'])")).size();
			String dbName="";
			//Select 'ExistingKioskDashboard' from the list
			for(int i=1;i<=numberOfLDB;i++) {
				dbName=getWebElementXpath_D("(//div[@id='divDashboarSelectorTree']//li[@class='rtLI']//span[@class='rtIn'])["+i+"]").getText();
				if(dbName.contentEquals("ExistingKioskDashboard")) {
					getWebElementXpath_D("(//div[@id='divDashboarSelectorTree']//li[@class='rtLI']//span[@class='rtIn'])["+i+"]").click();
					aJaxWait();
					Thread.sleep(5000);
					printLog("ExistingKioskDashboard for legacy DB is selected successfully.");
					break;
				}
			}
			
			List<WebElement> col1WidgetList = d.findElements(By.xpath("//div[@id='ctl00_ContentPlaceHolder1_portalContainer_area2portalColumn1']/div"));
			List<WebElement> col2WidgetList = d.findElements(By.xpath("//div[@id='ctl00_ContentPlaceHolder1_portalContainer_area2portalColumn2']/div"));
			
			int noOfWidgets = col1WidgetList.size()+col2WidgetList.size()-2;
			ArrayList<String> dashbaordWidgets = new ArrayList<String>();
			ArrayList<String> kioskWidgets = new ArrayList<String>();
			String titlePath,title;
			//Verify widget title in Dashboard page - title...Kiosk-Trend-Last30Days Kiosk-CLP-CurrentMonth Kiosk-Comparison-YTD Kiosk-LP-Last7Days Kiosk-TrendYTD
			for(int i=1;i<=noOfWidgets;i++) {
				titlePath = "(//*[@class='rdTitleBar'])["+i+"]";
				title = d.findElement(By.xpath(titlePath)).getText().trim();
				dashbaordWidgets.add(title);
			}
			
			//Click on Dashboard wrench icon			
			getWebElementXpath_D("//div[@id='ctl00_ContentPlaceHolder1_UpdatePanel1']//img[@id='ctl00_ContentPlaceHolder1_Img1']").click();
			Thread.sleep(2000);
			
			//Click on 'Preview Kiosk' link
			getWebElementXpath_D("//div[@id='ctl00_ContentPlaceHolder1_rcMenu_detached']/ul//a/span[text()='Preview Kiosk']").click();
			aJaxWait();
			Thread.sleep(5000);
			
			//Verify the page url
			Assert.assertTrue(d.getCurrentUrl().contains("KioskIndexEdit.aspx"));
			printLog("Verified the page url contains 'KioskIndexEdit.aspx'");
			//Verify widget title in 'Preview Kiosk' page
			for(int i=1;i<=noOfWidgets;i++) {
				titlePath = "(//*[@class='rdTitleBar'])["+i+"]";
				title = d.findElement(By.xpath(titlePath)).getText().trim();
				kioskWidgets.add(title);
			}
			
			//Compare the widget titles on Dashboard and Preview pages
			for(int i=0;i<noOfWidgets;i++) {
				Assert.assertEquals(dashbaordWidgets.get(i), kioskWidgets.get(i));
			}
			printLog("Verified and Compare the widget titles on Dashboard and Preview pages");
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

package regression.DashboardsAndWidgets.MyDashBoardAndWidgets;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

public class QIDM_215_ShareDashboard extends TestBase {
	LoginTC login = null;

	@Test
	public void shareDashboard() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Share Dashboard Test' under 'AutoTest_Dashboards' folder
			Utility.goToPAMDashboard("AutoTest_Dashboards", "Share Dashboard Test");
			
			//Verify the widget title on the dashboard
			String firstWidget = getWebElementActionXpath_D("//gridster/gridster-item[1]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
			String secondWidget = getWebElementActionXpath_D("//gridster/gridster-item[2]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
			String thirdWidget = getWebElementActionXpath_D("//gridster/gridster-item[3]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
			String fourthWidget = getWebElementActionXpath_D("//gridster/gridster-item[4]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
						
			// Click on dashboard settings icon
			getWebElementActionXpath("Dashboard_Settings_Icon").click();
			aJaxWait();
			Thread.sleep(2000);
			Reporter.log("Click on dashboard settings icon");
			
			// Click on Share Link
			//getWebElementActionXpath_D("").click();
			getWebElementActionXpath_D("//span[normalize-space()='Share']").click();
			aJaxWait();
			Thread.sleep(2000);
			Reporter.log("Click on Share Link");
			
			//Search with external user
			getWebElementActionXpath("Search_Users").click();
			Thread.sleep(1000);
			getWebElementXpath("Search_Users").sendKeys(Keys.BACK_SPACE);
			Thread.sleep(2000);
			getWebElementXpath("Search_Users").sendKeys("pautoexternal");
			aJaxWait();
			Thread.sleep(2000);
			Reporter.log("Search with external user");
			
			// Select external user
			getWebElementActionXpath("Select_External_User").click();
			aJaxWait();
			Thread.sleep(2000);
			Reporter.log("Select external user");
			
			// Click on Add Users link
			getWebElementActionXpath("AddUsers_link").click();
			aJaxWait();
			Thread.sleep(2000);
			Reporter.log("Click on Add Users link");
			
			// Click on Share button
			//getWebElementActionXpath_D("//span[normalize-space()='Share']").click();
			getWebElementActionXpath_D("//button[contains(text(),'Share')]").click();
			aJaxWait();
			Thread.sleep(5000);
			Reporter.log("Click on Share button");
			
			getWebElementActionXpath("ShareDashboard_okbutton").click();
			//getWebElementActionXpath_D("//button[contains(text(),'Share')]").click();
			aJaxWait();
			Thread.sleep(2000);
			Reporter.log("Click on Share button");
			
			//logout the application by internal user
			login.logout();
						
			explicitWait_CSS("input[id*=ddlClientId_Input]");
			d.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtUsername']")).sendKeys("pautoexternal");
			d.findElement(By.xpath("//span[@id='ctl00_ContentPlaceHolder1_lblUserSearch']")).click();
		
			aJaxWait();
			Thread.sleep(3000);
			getWebElementActionXpath_D("//a[contains(text(),'pautoexternal')]").click();
			waitForPageLoad();
			aJaxWait();
			Thread.sleep(8000);
			Reporter.log("Logged in as External user");
			

			//Go to 'Share Dashboard Test' under 'AutoTest_Dashboards' folder
			Utility.goToPAMDashboard("My Dashboards", "Share Dashboard Test");
			
			//Verify the widget title on the dashboard
			String firstWidget1 = getWebElementActionXpath_D("//gridster/gridster-item[1]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
			String secondWidget1 = getWebElementActionXpath_D("//gridster/gridster-item[2]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
			String thirdWidget1 = getWebElementActionXpath_D("//gridster/gridster-item[3]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
			String fourthWidget1 = getWebElementActionXpath_D("//gridster/gridster-item[4]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText();
			
			Assert.assertEquals(firstWidget, firstWidget1);
			Assert.assertEquals(secondWidget, secondWidget1);
			Assert.assertEquals(thirdWidget, thirdWidget1);
			Assert.assertEquals(fourthWidget, fourthWidget1);

			d.navigate().refresh();
			Utility.deleteDashboard();
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

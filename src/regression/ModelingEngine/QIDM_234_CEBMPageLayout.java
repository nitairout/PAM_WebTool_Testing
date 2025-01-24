package regression.ModelingEngine;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * To verify that the Baseline CEBM window layout functions correctly and displays the expected elements.
 */
public class QIDM_234_CEBMPageLayout extends TestBase{
	LoginTC login = null;
	@Test
	public void cebmPageLayout() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Search for site 'PAMTest_Capriata/Saiwa \ PAMTest_Energy Balance \ PAMTest_Ovens \ PAMTest_Bruciatori forno L6'
			searchSiteInLocationList("PAMTest_Bruciatori forno L6");
			getWebElementXpath("PAMTest_BruciatorifornoL6_Energy").click();
			
			refreshToLoadTheChart();
			
			/// Click on Manage Measurement link from Legend Icon
			getWebElementXpath("LegendMarker").click();
			Thread.sleep(2000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);
			
			//Click on Baseline Tab
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(1000);
			getWebElementXpath("CreateNewBaseLine_button").click();
			Thread.sleep(5000);
			
			//Verify that the full-size CEBM windows open
			//boolean fullscreen = getWebElementXpath_D("//div[@window-class='fullscreen']").isDisplayed();
			//Assert.assertTrue(fullscreen);
			
			//Provide the name of the baseline
			getWebElementActionXpath_D("//div[@class='se-input-container']/input").sendKeys("layout");
			
			getWebElementXpath("BaselineStarteDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementActionXpath("BaselineStarteDate").sendKeys("01/01/2023");
					
			getWebElementXpath("BaselineEndDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementActionXpath("BaselineEndDate").sendKeys("09/30/2023");
			
			//Click Refresh button
			getWebElementActionXpath("Baseline_RefreshWhileCreate").click();
			Thread.sleep(6000);
			Reporter.log("Added the date range and refresh the baseline to load");
			
			//Click on Add Driver button
			getWebElementActionXpath_D("//button[normalize-space()='Add Driver']").click();
			Thread.sleep(2000);
			//click on PAMTest_Capriata/Saiwa when the add driver pop up will open
			//Search the site and select
			getWebElementActionXpath_D("(//*[@id='pam-hierarchy-matcher']/input)[3]").clear();
			getWebElementActionXpath_D("(//*[@id='pam-hierarchy-matcher']/input)[3]").sendKeys("PAMTest_Capriata/Saiwa");
			Thread.sleep(2000);
			getWebElementActionXpath_D("(//span[text()='PAMTest_Capriata/Saiwa'])[2]").click();
			Thread.sleep(2000);
			//Expand Electricity
			getWebElementActionXpath_D("(//h6[contains(@class,'panel-title')]/i[2])[1]").click();
			Thread.sleep(2000);
			getWebElementActionXpath_D("//h6[normalize-space()='Energy - kWh']").click();
			Thread.sleep(2000);
			
			//Click on Select button
			getWebElementActionXpath_D("//div[@class='ra-pam-modal-action-btns']/button[contains(text(),'Select')]").click();
				
			//Click on Generate button
			getWebElementActionXpath("BaselineGenerate_button").click();
			Thread.sleep(10000);
			Reporter.log("Selected the site with measurement and generated.");
			
			List<WebElement> list = d.findElements(By.xpath("//div[@class='driver-name']"));
			String driverName;
			String[] expectedDrivers = {"Temperature", "Energy"};
			for(int i=1;i<=list.size();i++) {
				driverName = getWebElementXpath_D("(//div[@class='driver-name'])["+i+"]").getText();
				Assert.assertEquals(driverName, expectedDrivers[i-1]);
				}
			Reporter.log("Verified the drivers");
			//click on Details hide '<' icon
			getWebElementActionXpath_D("(//i[@class='blueSE clickable collapseIcon fa fa-chevron-left fa-pull-right'])[1]").click();
			Thread.sleep(2000);			
			boolean name = getWebElementXpath("BaselineName").isDisplayed();
			Assert.assertFalse(name);
			
			//Click on Details '>' icon
			getWebElementActionXpath_D("(//i[@class='blueSE clickable collapseIcon fa fa-pull-right fa-chevron-right'])[1]").click();
			Thread.sleep(2000);
			name = getWebElementXpath("BaselineName").isDisplayed();
			Assert.assertTrue(name);
			
			//Hide the Statistics tab
			String path = "(//i[@class='blueSE clickable collapseIcon fa fa-chevron-down fa-pull-right'])[1]";	
			Utility.moveTheElementandClick(path);
			Thread.sleep(2000);
			//should not be display any data like drivers
			list = d.findElements(By.xpath("//*[@class='se-grid statistics statistics2']//tr//div"));			
			Assert.assertEquals(list.size(), 0);
			
			//expand the Statistics tab
			path = "(//i[@class='blueSE clickable collapseIcon fa fa-pull-right fa-chevron-up'])[1]";		
			Utility.moveTheElementandClick(path);
			Thread.sleep(2000);
			//should be display the data like drivers
			list = d.findElements(By.xpath("//*[contains(@class,'se-grid statistics')]//tr//div"));			
			Assert.assertTrue(list.size()>0);
			
			//Verify that the driver y-axes stack on the right outside the Time Series chart.
			List<WebElement> yaxis = d.findElements(By.xpath("(//div[@id='time-series-chart']//div)[1]//*[@class='overaxes-above']//*[@class='y2tick']"));
			Assert.assertTrue(yaxis.size()>0);
			
			//Click on Cancel button
			getWebElementXpath("BaselineCancel_button").click();
			
			//Close the baseline popup
			getWebElementActionXpath("Baseline_Close_button").click();
			Thread.sleep(10000);	
			Reporter.log("Verified and close the baseline pop up.");
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

package regression.ModelingEngine;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies creating, Editing and Deleting user defined baseline for a site.
 */
public class QIDM_228_UserDefinedBaseline extends TestBase{
	LoginTC login = null;
	String baselineName = "auto test baseline";
	String baselineNameEdit = "Auto Test Updated";
	@Test
	public void userDefinedBaseline() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			//Search for site 'PAMTest_Capriata/Saiwa \ PAMTest_Energy Balance \ PAMTest_Ovens \ PAMTest_Bruciatori forno L6'
			searchSiteInLocationList("PAMTest_Bruciatori forno L6");
			getWebElementXpath("PAMTest_BruciatorifornoL6_Energy").click();
			
			refreshToLoadTheChart();
			Thread.sleep(5000);
			
			//Click on Manage Measurement link from Legend Icon
			getWebElementActionXpath("LegendMarker").click();
			Thread.sleep(2000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);
			
			//Click on Baseline Tab
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(1000);
			
			//verify same baseline exists, if yes delete the baseline
			int checkBaselineExist=d.findElements(By.xpath("//tr[contains(@class,'baseline-item')]/td[contains(.,'"+baselineNameEdit+"') or contains(.,'"+baselineName+"')]")).size();
			if(checkBaselineExist==1) {
				//click on delete icon
				getWebElementActionXpath_D("//tr[contains(@class,'baseline-item')]/td[contains(.,'"+baselineNameEdit+"') or contains(.,'"+baselineName+"')]/following-sibling::td[4]/i").click();
				Thread.sleep(2000);
				getWebElementActionXpath_D("//div[@class='modal-footer-btns']/div[contains(text(),'Yes')]").click();
				Thread.sleep(5000);
				getWebElementActionXpath_D("//div[@class='ra-pam-modal-action-btns']/button[normalize-space()='Save']").click();
				Thread.sleep(10000);
				
			}
			
			//Click on 'Create New Baseline'
			getWebElementXpath("CreateNewBaseLine_button").click();
			Thread.sleep(1000);
			
			//Verify the Popup title
			String popupTitle = getWebElementActionXpath_D("(//div[@class='ra-pam-modal-header']/h3)[2]").getText();
			Assert.assertEquals(popupTitle, "Create/Edit Energy (kWh) Baseline for PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens \\ PAMTest_Bruciatori forno L6");
			
			//Provide the name of the baseline
			getWebElementXpath("BaselineName").sendKeys(baselineName);
			
			//Change the date range from '1/1/2022' to '12/31/2022'
			getWebElementXpath("BaselineStarteDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath("BaselineStarteDate").sendKeys("1/1/2022");
			Thread.sleep(1000);
			
			getWebElementXpath("BaselineEndDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath("BaselineEndDate").sendKeys("12/31/2022");
			Thread.sleep(1000);
			
			//Verify the interval
			//String interval =new Select(getWebElementActionXpath_D("//select[@name='interval']")).getFirstSelectedOption().getText();
			String interval =new Select(getWebElementActionXpath_D("//select[@name='interval']")).getFirstSelectedOption().getAttribute("value");
			Assert.assertEquals(interval,"Days");
			
			String driverName = getWebElementActionXpath_D("//div[@class='driver-name']").getText();
			Assert.assertEquals(driverName,"Temperature");
			
			//Click on Generate button
			getWebElementActionXpath("BaselineGenerate_button").click();
			Thread.sleep(20000);//takes more time to load the chart on the popup
			
			//Verify Scatter Plot chart is displaying
			List<WebElement> scatterPlotChart = d.findElements(By.xpath("(//div[@class='chart-container'])[1]//div[@id='scatter-plot-chart']"));
			Assert.assertEquals(scatterPlotChart.size(), 1);
			
			//Verify Time Series chart is displaying
			List<WebElement> timeSeriesChart = d.findElements(By.xpath("(//div[@class='chart-container'])[2]//div[@id='time-series-chart']"));
			Assert.assertEquals(timeSeriesChart.size(), 1);
			
			//Click on Save button
			getWebElementActionXpath("BaselineSave_button").click();
			Thread.sleep(5000);
			
			//Verify the Baseline added
			checkBaselineExist=d.findElements(By.xpath("//tr[contains(@class,'baseline-item')]/td[contains(.,'"+baselineName+"')]")).size();
			if(checkBaselineExist==0) {
				Assert.fail("Baseline did not created successfully.");
			}else {
				Reporter.log("Baseleine added successfully.");
				//click on edit
				getWebElementXpath_D("//tr[contains(@class,'baseline-item')]/td[contains(.,'"+baselineName+"')]/following-sibling::td[3]/i").click();
				Thread.sleep(10000);
				Reporter.log("Clicked on edit button");
			}
			
			
			//Update the baseLine values
			//Provide the name of the baseline
			getWebElementXpath("BaselineName").clear();
			Thread.sleep(1000);
			getWebElementXpath("BaselineName").sendKeys(baselineNameEdit);
			
			//Change the date range from '1/1/2023' to '9/30/2023'
			getWebElementXpath("BaselineStarteDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath("BaselineStarteDate").sendKeys("1/1/2023");
			Thread.sleep(1000);
			
			getWebElementXpath("BaselineEndDate").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath("BaselineEndDate").sendKeys("9/30/2023");
			Thread.sleep(1000);
					
			//Click on Generate button
			getWebElementActionXpath("BaselineGenerate_button").click();
			Thread.sleep(10000);
			
			List<WebElement> chartDateRange = d.findElements(By.xpath("(//div[@id='time-series-chart']//div)[1]//*[@class='xaxislayer-above']/*[@class='xtick']"));
			String startChartDate = d.findElement(By.xpath("((//div[@id='time-series-chart']//div)[1]//*[@class='xaxislayer-above']/*[@class='xtick'])[1]")).getText();
			Assert.assertEquals(startChartDate, "Jan 2023");
			
			String endChartDate = d.findElement(By.xpath("((//div[@id='time-series-chart']//div)[1]//*[@class='xaxislayer-above']/*[@class='xtick'])["+chartDateRange.size()+"]")).getText();
			Assert.assertEquals(endChartDate, "Sep 2023");
			
			//Click on Save button
			getWebElementActionXpath("BaselineSave_button").click();
			Thread.sleep(5000);
			
			//pop up save button
			getWebElementActionXpath_D("//div[@class='modal-footer-btns']/div[contains(text(),'Save')]").click();
			Thread.sleep(5000);
			
			//Verify the Baseline added
			checkBaselineExist=d.findElements(By.xpath("//tr[contains(@class,'baseline-item')]/td[contains(.,'"+baselineNameEdit+"')]")).size();
			if(checkBaselineExist==0) {
				Assert.fail("Baseline did not created successfully.");
			}else {
				Reporter.log("Baseleine added successfully.");
				//click on delete icon
				getWebElementActionXpath_D("//tr[contains(@class,'baseline-item')]/td[contains(.,'"+baselineNameEdit+"') or contains(.,'"+baselineName+"')]/following-sibling::td[4]/i").click();
				Thread.sleep(2000);
				getWebElementActionXpath_D("//div[@class='modal-footer-btns']/div[contains(text(),'Yes')]").click();
				Thread.sleep(5000);
				getWebElementActionXpath_D("//div[@class='ra-pam-modal-action-btns']/button[normalize-space()='Save']").click();
				Thread.sleep(10000);
			}
			
			//Close the baseline popup
			getWebElementActionXpath("Baseline_Close_button").click();
			Thread.sleep(10000);	
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
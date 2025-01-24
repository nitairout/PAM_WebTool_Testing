package regression.ModelingEngine;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies the new enhancements on measurment configuration pop up and checks the expected setting on Expression,Valid Data Range and Baseline tabs.
 */
public class QIDM_225_ManageMeasurementConfigurationPopup extends TestBase{
	LoginTC login = null;
	@Test
	public void manageMeasurementConfigurationPopup() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			//Search for site 'PAMTest_Capriata/Saiwa \ PAMTest_Energy Balance \ PAMTest_Ovens \ PAMTest_Bruciatori forno L6'
			searchSiteInLocationList("PAMTest_Bruciatori forno L6");
			getWebElementXpath_D("//table/tbody/tr[4]/td[3]/child::span").click();
			
			// Select the Fixed date range of '1/1/2023' - '1/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			/// Click on Manage Measurement link from Legend Icon
			getWebElementXpath("LegendMarker").click();
			Thread.sleep(2000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);
			printLog("Clicked on manage measurement link from PAM page.");
			
			//Verify the Popup title
			String popupTitle = getWebElementActionXpath_D("//div[@class='ra-pam-modal-header']/h3").getText();
			assertEquals(popupTitle, "Configuration of Energy for PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens \\ PAMTest_Bruciatori forno L6");
			
			//Verify the presence of tabs on popup
			List<WebElement> list = d.findElements(By.xpath("(//kendo-tabstrip//ul[@role='tablist'])[2]/li"));
			Assert.assertEquals(list.size(), 3);
			String[] tabs = {"Expression","Valid Data Range","Baseline"};
			for(int i=0;i<list.size();i++) {
				Assert.assertEquals(getWebElementXpath_D("(//kendo-tabstrip//ul[@role='tablist'])[2]/li["+(i+1)+"]").getText(),tabs[i]);
			}
			
			//Verify the default active tab on the popup, it should be "Expression"
			String expressionActiveTab="(//kendo-tabstrip//ul[@role='tablist'])[2]/li[@aria-selected='true']";
			String activeTab = getWebElementXpath_D(expressionActiveTab).getText();
			Assert.assertEquals(activeTab,"Expression");
			
			String defaultExpression = getWebElementXpath_D("//div[@id='se-config-calc-meas-modal-left-col']//div[@class='section-paragraph']/p[1]").getText();
			Assert.assertEquals(defaultExpression,"[AggregateChildren(AGG_ELECTRICITY,Include)][Electricity:ENERGY]");
			
			//Click on "Valid Data Range" tab
			getWebElementXpath_D("(//kendo-tabstrip//ul[@role='tablist'])[2]//span[contains(text(),'Valid Data Range')]").click();
			Thread.sleep(3000);
			//Verify the description
			String validDateRangeDesc = getWebElementXpath_D("(//div[contains(@class,'valid-data-range-container')]//div[@class='section-paragraph'])[1]").getText();
			Assert.assertEquals(validDateRangeDesc, "This page enables the user to define the start and end dates that define when the location/measurement has a valid data set.");
			
			//Verify the start and end dares fields
			Assert.assertEquals(getWebElementXpath_D("//input[@id='datepicker-5']").isDisplayed(), true);
			Assert.assertEquals(getWebElementXpath_D("//input[@id='datepicker-6']").isDisplayed(), true);
			
			//Click on Baseline Tab
			getWebElementXpath_D("(//kendo-tabstrip//ul[@role='tablist'])[2]//span[contains(text(),'Baseline')]").click();
			Thread.sleep(3000);
			String baseLineLocation = getWebElementXpath_D("//div[@class='baseline-container']/*[@class='selected-location']").getText();
			Assert.assertEquals(baseLineLocation, "Configuring Baseline for PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens \\ PAMTest_Bruciatori forno L6");

			String baseLineDescription = getWebElementXpath_D("(//div[@class='baseline-container']//div[@class='section-paragraph']/span)[1]").getText();
			Assert.assertEquals(baseLineDescription, "This tab contains information regarding baselines that have been created for this specific location and measurement. Use this page to view, edit and create new baseline measurements.");
			
			String baseLines = getWebElementXpath_D("(//div[@class='baseline-container']//div[@class='section-paragraph']/span)[2]").getText();
			Assert.assertEquals(baseLines, "Below is a list of all Baselines that have been created. Here you can create new baselines or edit existing baselines. You may also set any of these baselines as the default baseline for this location/measurement.");
			
			//close the popup
			getWebElementActionXpath_D("//*[contains(@class,'ra-pam-modal')]/child::button[normalize-space()='Close']").click();
			
			//Go to Manage Measurement page
			gotoManageMeasurementPage();
			
			//Clicked on Configurations Tab
			getWebElementXpath("ConfigureLocationsTab_ManageMeasurements").click();
			Thread.sleep(3000);
			//Filter with subsite name 'PAMTest_Main Meter'
			getWebElementActionXpath("LocationPanelFilter").click();
			getWebElementXpath("LocationPanelFilter").clear();
			getWebElementXpath("LocationPanelFilter").sendKeys("PAMTest_Bruciatori forno L6");
			Thread.sleep(2000);
			getWebElementXpath("LocationPanelFilter").sendKeys(Keys.TAB);
			Thread.sleep(2000);
			getWebElementXpath_D("//span[normalize-space()='PAMTest_Bruciatori forno L6']").click();
			Thread.sleep(2000);
			printLog("Selected 'PAMTest_Bruciatori forno L6' from configuration tab");
			
			//Filter with measurement name 'Energy'
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys("Energy");			
			Thread.sleep(2000);
			printLog("Filtered with Energy measurement");
			
			//Clicked on setting icon for the measurement
			/*List<WebElement> measurementslist = d.findElements(By.xpath("//commodity-def-location//h4"));
			String measurement, measurementPath;
			for(int i=1;i<=measurementslist.size();i++) {
				measurementPath = "(//commodity-def-location//h4)["+i+"]";
				measurement = d.findElement(By.xpath(measurementPath)).getText();
				if(measurement.equalsIgnoreCase("Energy")) {
					String gearPath = "(//commodity-def-location//i[@class='fa fa-gear'])["+i+"]";
					//getWebElementXpath_D(gearPath).click();
					
					WebElement element = d.findElement(By.xpath(gearPath));
					JavascriptExecutor js = (JavascriptExecutor)d;
					js.executeScript("arguments[0].click();", element);
					Thread.sleep(5000);
					break;
				}
			}
			*/
			//Click on energy measurement setting icon
			clickUsingJavascriptExecuter("//h4[normalize-space()='Energy']/following-sibling::span/i");
			Thread.sleep(5000);
			
			//Verify that Popup title is same as measurement configuration
			String actualtitle = getWebElementActionXpath_D("//div[@class='ra-pam-modal-header']/h3").getText();
			assertEquals(actualtitle, popupTitle);
			
			//Verify the presence of tabs on popup
			List<WebElement> list1 = d.findElements(By.xpath("(//*[@id='configCalculatedMeasurementModal']//ul)[1]/li//div[contains(@class,'se-tab-heading')]"));
			Assert.assertEquals(list1.size(), 3);
			for(int i=0;i<list.size();i++) {
				Assert.assertEquals(getWebElementXpath_D("((//*[@id='configCalculatedMeasurementModal']//ul)[1]/li//div[contains(@class,'se-tab-heading')])["+(i+1)+"]").getText(),tabs[i]);
			}
			
			//Verify the default active tab on the popup, it should be "Expression"
			String activeTabMeasurement = getWebElementXpath_D(expressionActiveTab).getText();
			Assert.assertEquals(activeTabMeasurement,activeTab);
			
			String defaultExpressionMeasurement = getWebElementXpath_D("//div[@id='se-config-calc-meas-modal-left-col']//div[@class='section-paragraph']/p[1]").getText();
			Assert.assertEquals(defaultExpressionMeasurement,defaultExpression);
			
			//Click on "Valid Data Range" tab
			getWebElementXpath_D("((//*[@id='configCalculatedMeasurementModal']//ul)[1]/li//div[contains(@class,'se-tab-heading')])[2]").click();
			Thread.sleep(3000);
			//Verify the description
			String validDateRangeDescMeasurement = getWebElementXpath_D("//div[@class='section-paragraph']/span").getText();
			Assert.assertEquals(validDateRangeDescMeasurement, validDateRangeDesc);
			
			//Verify the start and end dares fields
			//Assert.assertEquals(getWebElementXpath_D("(//kendo-datepicker//input)[1]").isDisplayed(), true);
			//Assert.assertEquals(getWebElementXpath_D("(//kendo-datepicker//input)[2]").isDisplayed(), true);
			
			//Click on Baseline Tab
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(4000);
			
			//Verify the location on Baseline tab should be same as from Pam card measurement popup
			Assert.assertEquals(getWebElementXpath_D("//div[@class='baseline-container']/h4").getText().trim(), baseLineLocation);
			
			//Verify the description on Baseline tab should be same as from Pam card measurement popup
			String baseLineDescriptionMeasurement = getWebElementXpath_D("(//div[@class='baseline-container']//div[@class='section-paragraph']/span)[1]").getText();
			Assert.assertEquals(baseLineDescriptionMeasurement,baseLineDescription);
			
			//Verify the baseline message on Baseline tab should be same as from Pam card measurement popup
			String baseLinesMeasurement = getWebElementXpath_D("(//div[@class='baseline-container']//div[@class='section-paragraph']/span)[2]").getText();
			Assert.assertEquals(baseLinesMeasurement,baseLines);
			
			//close the popup
			getWebElementActionXpath_D("//*[contains(@class,'ra-pam-modal')]/child::button[normalize-space()='Close']").click();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

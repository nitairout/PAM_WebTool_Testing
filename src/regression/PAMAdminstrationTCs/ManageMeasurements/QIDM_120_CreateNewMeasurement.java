package regression.PAMAdminstrationTCs.ManageMeasurements;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test created new measurement under 'Manage Measurements' 
 * and configures to a location by adding expression and etc. 
 * Then it loads chart for this measurement in Trend Analysis card and verifies data.
 */
public class QIDM_120_CreateNewMeasurement extends TestBase {
	LoginTC login = null;
	String measurementName ="QIDM-120 Test";
	String expression = "[Electricity:ENERGY][kWh] / 2";
	
	@Test
	public void createNewMeasurement() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Goto Manage Measurement page
			gotoManageMeasurementPage();
			
			//Verify that default page should be 'Manage Measurements'
			String activeTab = getWebElementXpath_D("//*[contains(@class,'k-active')]/span").getText();
			Assert.assertEquals(activeTab, "Manage Measurements");
			
			//Delete the Measurement if already existing with the same name 'MeasurementWithoutUnits'
			Utility.deleteMeasurement(measurementName);	
			
			//Clicked on 'New Measurement' button
			//getWebElementXpath_D("//div[@id='se-mng-meas-com-container']/div/span").click();
			getWebElementXpath_D("//*[contains(text(),'New Measurement')]").click();
			Thread.sleep(2000);
			
			//Verified the header of the popup
			String popupHeader = getWebElementXpath_D("//div[@class='modal-header']/h3").getText();
			Assert.assertEquals(popupHeader, "Create Measurement"); 
			
			//Provide the measurement name
			getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[1]/div/input").click();
			getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[1]/div/input").clear();
			getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[1]/div/input").sendKeys(measurementName);
			
			//Verified the default commodity name should be 'Electricity
			String defaultCommodity = new Select(getWebElementXpath_D("//div[@id='basicSetupTab']//select[@name='selectedCommodityedit']")).getFirstSelectedOption().getText();
			Assert.assertEquals(defaultCommodity, "Electricity");
			
			//Provide the description of the measurement
			getWebElementXpath_D("//div[@id='basicSetupTab']//textarea[@name='Description']").sendKeys("Auto Test");
			
			//Provide the UOM as 'kWh'
			getWebElementXpath_D("//div[@id='basicSetupTab']//input[@placeholder='Add a unit']").click();
			getWebElementXpath_D("//div[@id='basicSetupTab']//input[@placeholder='Add a unit']").sendKeys("kWh");
			Thread.sleep(5000);
			getWebElementActionXpath_D("//span[normalize-space(text())='kWh']").click();
			
			//Verified the default Aggregation Value should be 'Sum'
			String aggregationValue = new Select(getWebElementXpath_D("//div[@id='basicSetupTab']//select[@name='selectedAggregation']")).getFirstSelectedOption().getText();
			Assert.assertEquals(aggregationValue, "Sum");
			
			//Verify the indexing option should be enable
			boolean indexingOption = getWebElementXpath_D("//div[@id='basicSetupTab']//input[@name='Normalizable']").isSelected();
			Assert.assertTrue(indexingOption);
			
			//Verify the interval Exceptions should be enable
			boolean intervalExceptions = getWebElementXpath_D("//div[@id='basicSetupTab']//input[@id='chkIntervalExp']").isSelected();
			Assert.assertTrue(intervalExceptions);
			
			//Verify the daily Exceptions should be not checked
			boolean dailyExceptions = getWebElementXpath_D("//div[@id='basicSetupTab']//input[@id='chkDailyExp']").isSelected();
			Assert.assertFalse(dailyExceptions);
			
			//Select daily exceptions checkbox to checked
			getWebElementXpath_D("//div[@id='basicSetupTab']//input[@id='chkDailyExp']").click();
			
			//Click on Save & Close button
			getWebElementActionXpath_D("//div[@class='modal-footer']/child::button[normalize-space(text())='Save & Close']").click();
			aJaxWait();
			Thread.sleep(8000);
			
			printLog("Successfully added the measurement "+measurementName);
			
			//Filter the measurement name with 'QIDM-120 Test'
			getWebElementXpath("ManageMeasurement_SearchBar").clear();
			getWebElementXpath("ManageMeasurement_SearchBar").sendKeys(measurementName);			
			Thread.sleep(5000);
			
			//Click on the measurement name from results
			getWebElementXpath_D("//div[contains(@class,'well clickable')]/span[1]").click();
			Thread.sleep(2000);
			
			//Verify the measurement name from edit measurement
			String actualMeasurementName = getWebElementXpath_D("//input[@name='MeasurementGroupLabel']").getAttribute("value");
			Assert.assertEquals(actualMeasurementName, measurementName);
			
			//Verify the Commodity from edit measurement
			defaultCommodity = new Select(getWebElementXpath_D("//div[@id='basicSetupTab']//select[@name='selectedCommodityedit']")).getFirstSelectedOption().getText();
			Assert.assertEquals(defaultCommodity, "Electricity");
			
			//Verify the description from edit measurement
			String description = getWebElementXpath_D("//div[@id='basicSetupTab']//textarea[@name='Description']").getAttribute("value");
			Assert.assertEquals(description, "Auto Test");
			
			//Verify the UOM from edit measurement
			String uom = getWebElementXpath_D("//*[@name='selectedUnits']//span[contains(@class,'k-chip-label k-text-ellipsis')]").getText();
			Assert.assertEquals(uom, "kWh");
			
			//Verified the Aggregation Value  from edit measurement
			aggregationValue = new Select(getWebElementXpath_D("//div[@id='basicSetupTab']//select[@name='selectedAggregation']")).getFirstSelectedOption().getText();
			Assert.assertEquals(aggregationValue, "Sum");
			
			//Verify the indexing Option as enabled from edit measurement
			indexingOption = getWebElementXpath_D("//div[@id='basicSetupTab']//input[@name='Normalizable']").isSelected();
			Assert.assertTrue(indexingOption);
			
			//Verify the interval Exceptions as enabled from edit measurement
			intervalExceptions = getWebElementXpath_D("//div[@id='basicSetupTab']//input[@id='chkIntervalExp']").isSelected();
			Assert.assertTrue(indexingOption);
			
			//Verify the daily Exceptions as enabled from edit measurement
			dailyExceptions = getWebElementXpath_D("//div[@id='basicSetupTab']//input[@id='chkDailyExp']").isSelected();
			Assert.assertTrue(indexingOption);
			
			//Click on Cancel button
			getWebElementActionXpath_D("//div[@class='modal-footer']/button[3]").click();
			Thread.sleep(2000);
			
			printLog("Verified all the measurement values as expected from Edit Measurement.");
			
			//clear the measurement from manage measurement
			getWebElementXpath("ManageMeasurement_SearchBar").click();
			getWebElementXpath("ManageMeasurement_SearchBar").clear();
			d.navigate().refresh();
			Thread.sleep(10000);
			
			Utility.moveTheScrollToTheTop();
			//Clicked on Configurations Tab
			getWebElementActionXpath("ConfigureLocationsTab_ManageMeasurements").click();
			Thread.sleep(5000);
			//d.navigate().refresh();
			Thread.sleep(5000);
			printLog("Go to Configurations Tab");
			//Filter with site name 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath("LocationPanelFilter").click();
			getWebElementActionXpath("LocationPanelFilter").clear();
			getWebElementXpath("LocationPanelFilter").sendKeys(testData.getProperty("PAMTestCapriataSaiwa"));
			Thread.sleep(2000);
			getWebElementXpath("LocationPanelFilter").click();
			Thread.sleep(2000);
			//Click on site 'PAMTest_Main Meter'
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa']").click();
			Thread.sleep(5000);
			aJaxWait();
			Thread.sleep(5000);
			
			//Filter with measurement name 'QIDM-120 Test'
			getWebElementXpath("FilterByNameAndUOM").click();
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(measurementName);			
			Thread.sleep(5000);
			
			//Click on setting icon for the measurement for edit expression
			getWebElementActionXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(5000);
			
			//Provide the measurement
			getWebElementXpath("EditExpression_Textarea").click();
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys(expression);
			
			//Click on Validate button
			getWebElementActionXpath("EditExpression_Validate").click();
			Utility.moveTheScrollToTheDown();
			
			//Click on 'Save and close' button
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(5000); 
			Utility.moveTheScrollToTheTop();
			
			//Verified the measurement name and expression
			Assert.assertEquals(getWebElementXpath_D("//h4[contains(@class,'clickable')]").getText(), measurementName);
			Assert.assertEquals(getWebElementXpath_D("//span[@class='col-xs-10 ellipsis']").getText(), expression);
			
			printLog("Successfully added the expression "+expression);
			
			//Navigate to PAM page
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select single measurement under Electricity - 'QIDM-120 Test'
			Utility.selectSingleMeasurement("Electricity",measurementName,userDefined);
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			//Click on the measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			// Select the Fixed date range of '1/1/2023' - '1/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();

			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Verified the Legend from the chart");
			
			enlargeBottomTabsPanel();
			Utility.hideOptionLocationPanelForCards();
			Utility.moveTheScrollToTheTop();
			//Verify the Statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total QIDM-120 Test");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "130,891");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementUnit").getText(), "kWh");
			
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementTitle").getText(), "Min Daily QIDM-120 Test");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementValue").getText(), "939.0");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementUnit").getText(), "kWh");
			
			printLog("Verified the Stats Datat");
			Utility.moveTheScrollToTheTop();
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			//First 2 rows of table data verification
			String byDayTableData="1/1/2023~4,730|1/2/2023~5,927";
			verifyTableDataWithExpected(byDayTableData,2,"QIDM_120_CreateNewMeasurement");
			printLog("By day interval verification completed!!");
			
			Utility.moveTheScrollToTheTop();
			//Goto Manage measurements page to delete the measurement
			gotoManageMeasurementPage();
			
			//Delete Measurement
			Utility.deleteMeasurement(measurementName);
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}



package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies creating a measurement without units and verifying data for this measurement in PAM card. 
 * we should be able to create and see the measurements in PAM fine without units.
 */
public class QIDM_143_CreateMeasurementWithoutUnits extends TestBase {
	LoginTC login = null;
	String measurementName = "MeasurementWithoutUnits";
	String expression = "[Electricity:ENERGY][kWh] / 2";
	
	@Test
	public void createMeasurementWithoutUnits() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Goto Manage Measurement page
			gotoManageMeasurementPage();
			
			//Verify that default page should be 'Manage Measurements'
			String activeTab = getWebElementXpath_D("//*[contains(@class,'k-tabstrip-item k-active')]/span").getText();
			Assert.assertEquals(activeTab, "Manage Measurements");
			
			//Delete the Measurement if already existing with the same name 'MeasurementWithoutUnits'
			Utility.deleteMeasurement(measurementName);
			d.navigate().refresh();
			Thread.sleep(10000);		
			
			//Clicked on 'New Measurement' button
			getWebElementXpath("NewMeasurement_Button").click();
			Thread.sleep(2000);
			
			//Verified the header of the popup
			String popupHeader = getWebElementXpath_D("//div[@class='modal-header']/h3").getText();
			Assert.assertEquals(popupHeader, "Create Measurement"); 
			
			//Provide the measurement name
			getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[1]/div/input").click();
			getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[1]/div/input").clear();
			getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[1]/div/input").sendKeys(measurementName);
			
			//Verified the default commodity name should be 'Electricity
			String defaultCommodity = getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[2]/div/select[@name='selectedCommodityedit']/option[@value='0: Object']").getText();
			Assert.assertEquals(defaultCommodity, "Electricity");
			
			//Verified the default Aggregation Value should be 'Sum'
			String aggregationValue = getWebElementXpath_D("//div[@id='basicSetupTab']/div/div/div[5]/span[2]/select[@name='selectedAggregation']/option[@value='SUM']").getText();
			Assert.assertEquals(aggregationValue, "Sum");
			
			//Click on Save button
			getWebElementActionXpath_D("//div[@class='modal-footer']/button[2]").click();
			Thread.sleep(5000);
			
			String warningMsg = getWebElementXpath_D("//div[@class='modal-content']/div[2]/h4").getText();
			Assert.assertEquals(warningMsg, "You must enter at least one unit of measure (UOM) associated with this measurement. Or select \"(Unitless)\" if this measurement supports data streams without units.");
			
			//Click on 'OK' from warning popup
			getWebElementXpath_D("//div[@class='modal-content']/div[3]/div").click();
			
			//Provide the UOM as 'Unitless'
			getWebElementXpath_D("//div[@id='basicSetupTab']//input[@placeholder='Add a unit']").click();
			getWebElementXpath_D("//div[@id='basicSetupTab']//input[@placeholder='Add a unit']").sendKeys("(Unitless)");
			Thread.sleep(5000);
			getWebElementActionXpath_D("//kendo-list/div/ul/li/span").click();

			//Click on Save & Close button
			getWebElementActionXpath_D("//div[@class='modal-footer']/button[1]").click();
			aJaxWait();
			Thread.sleep(8000);
			
			//Clicked on Configurations Tab
			getWebElementActionXpath("ConfigureLocationsTab_ManageMeasurements").click();
			Thread.sleep(10000);
			printLog("Go to Configurations Tab");
			d.navigate().refresh();
			Thread.sleep(10000);
			//Filter with site name 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath("LocationPanelFilter").click();
			getWebElementXpath("LocationPanelFilter").sendKeys(testData.getProperty("PAMTestCapriataSaiwa"));
			Thread.sleep(2000);
			getWebElementXpath("LocationPanelFilter").click();
			Thread.sleep(2000);
			
			//Click on site 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa']").click();
			Thread.sleep(5000);
			aJaxWait();
			Thread.sleep(5000);
			
			//Filter with measurement name 'MeasurementWithoutUnits'
			getWebElementXpath("FilterByNameAndUOM").click();
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(measurementName);			
			Thread.sleep(5000);
			
			//Click on setting icon for the measurement for edit expression
			getWebElementXpath("ConfigLocationMeasurementSettingIcon").click();
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
			Thread.sleep(8000); 
			Utility.moveTheScrollToTheTop();
			
			//Verified the measurement name and expression
			Assert.assertEquals(getWebElementXpath_D("//h4[contains(@class,'clickable')]").getText(), measurementName);
			Assert.assertEquals(getWebElementXpath_D("//span[@class='col-xs-10 ellipsis']").getText(), expression);
			//Navigate to PAM page
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select single measurement under Electricity - 'MeasurementWithoutUnits'
			Utility.selectSingleMeasurement("Electricity",measurementName,userDefined);
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			//Click on the measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			// Select the Fixed date range of '1/1/2023' - '1/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			
			enlargeBottomTabsPanel();
			
			//Hide option and location panel
			hideOptionLocationPanel();
			//Verify the Statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total MeasurementWithoutUnits");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "130,891");
			
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementTitle").getText(), "Min Daily MeasurementWithoutUnits");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementValue").getText(), "939.0");
			
			Assert.assertEquals(getWebElementXpath("Statistics_ThirdElementTitle").getText(), "Max Daily MeasurementWithoutUnits");
			Assert.assertEquals(getWebElementXpath("Statistics_ThirdElementValue").getText(), "12,726");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementTitle").getText(), "Avg Daily MeasurementWithoutUnits");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementValue").getText(), "4,222");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), "MeasurementWithoutUnits ()");
			printLog("Verified table header for unit less measurement");
			
			//First 2 rows of table data verification
			String byDayTableData="1/1/2023~4,730|1/2/2023~5,927";
			verifyTableDataWithExpected(byDayTableData,2,"QIDM_143_CreateMeasurementWithoutUnits");
			printLog("By day interval verification completed!!");
			
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

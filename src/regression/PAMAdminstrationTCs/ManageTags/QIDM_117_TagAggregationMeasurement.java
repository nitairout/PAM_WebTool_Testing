package regression.PAMAdminstrationTCs.ManageTags;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*This test verifies functionality of adding expression to a measurement for aggregating the children which are tagged with same tag.*/

public class QIDM_117_TagAggregationMeasurement extends TestBase {
	LoginTC login = null;
	String expression = "[AggregateChildren(Autotag,Autotag1)][Electricity:ENERGY][kWh]";
	String measurementName = "QIDM-117 Test Measurement";

	@Test
	public void tagAggregationMeasurement() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Manage Measurements page under Administration menu tab
			gotoManageMeasurementPage();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			printLog("Navigate to Manage Measurement page");
			//Clicked on Configurations Tab
			getWebElementXpath("ConfigureLocationsTab_ManageMeasurements").click();
			Thread.sleep(5000);
			//Filter with subsite name 'PAMTest_Main Meter'
			getWebElementActionXpath("LocationPanelFilter").click();
			getWebElementXpath("LocationPanelFilter").sendKeys(testData.getProperty("PAMTest_Main_Meter"));
			Thread.sleep(2000);
			getWebElementXpath("LocationPanelFilter").click();
			
			//Click on subsite 'PAMTest_Main Meter'
			getWebElementActionXpath_D("//tr[2]/td[1]//span[normalize-space()='PAMTest_Main Meter']").click();
			
			//Filter with measurement name 'QIDM-117 Test Measurement'
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(measurementName);			
			Thread.sleep(2000);
			
			//Click on setting icon for the measurement for edit expression
			getWebElementXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(2000);
			//Provide the measurement
			getWebElementXpath("EditExpression_Textarea").click();
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys(expression);			
			//Click on Validate button
			getWebElementActionXpath("EditExpression_Validate").click();
			Thread.sleep(2000);
			Utility.moveTheScrollToTheDown();
			//Click on 'Save and close' button
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(5000); 
			Utility.moveTheScrollToTheTop();
			//Verified the measurement name and expression
			Assert.assertEquals(getWebElementXpath_D("//h4[contains(@class,'clickable')]").getText(), measurementName);
			Assert.assertEquals(getWebElementXpath_D("//span[@class='col-xs-10 ellipsis']").getText(), expression);
			
			//Navigate to PAM page
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			Utility.selectSingleMeasurement("Electricity", measurementName,userDefined);
			
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			//Click on the measurement for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();
			// Select the Fixed date range of '1/1/2023' - '1/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			
			//Verify the Statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total QIDM-117 Test Measurement");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "1,210,311");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementUnit").getText(), "kWh");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			//First 2 rows of table data verification
			String byDayTableData="1/1/2023~26,536|1/2/2023~46,388";
			verifyTableDataWithExpected(byDayTableData,2,"QIDM_117_TagAggregationMeasurement");
			printLog("By day interval verification completed!!");
			
			//Navigate to Manage Measurements page under Administration menu tab
			gotoManageMeasurementPage();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			printLog("Navigate to Manage Measurement page");
			//Clicked on Configurations Tab
			getWebElementXpath("ConfigureLocationsTab_ManageMeasurements").click();
			aJaxWait();
			Thread.sleep(5000); 
			d.navigate().refresh(); 
			Thread.sleep(10000);
			//Filter with subsite name 'PAMTest_Main Meter'
			getWebElementActionXpath("LocationPanelFilter").click();
			getWebElementXpath("LocationPanelFilter").sendKeys(testData.getProperty("PAMTest_Main_Meter"));
			Thread.sleep(2000);
			getWebElementXpath("LocationPanelFilter").click();
			
			//Click on subsite 'PAMTest_Main Meter'
			getWebElementActionXpath_D("//tr[2]/td[1]//span[normalize-space()='PAMTest_Main Meter']").click();
			
			//Searched with measurement name
			getWebElementXpath("FilterByNameAndUOM").click();
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(measurementName);			
			Thread.sleep(5000);
			
			//Clicked on setting icon for the measurement
			getWebElementXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(5000);
			//Cleared the expression from edit expression
			getWebElementXpath("EditExpression_Textarea").click();
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys("1");
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.BACK_SPACE);
			Thread.sleep(500);
			Thread.sleep(5000);
			
			Utility.moveTheScrollToTheDown();
			//Click on Save & Close button
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(5000);
			
			printLog("Successfully deleted the expression");
			Utility.moveTheScrollToTheTop();
			
			//Verify the expression should not be shown
			Assert.assertEquals(getWebElementXpath_D("//h4[contains(@class,'clickable')]").getText(), measurementName);
			Assert.assertTrue(d.findElements(By.xpath("//span[@class='col-xs-10 ellipsis']")).size()==0);
			
			login.logout();
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

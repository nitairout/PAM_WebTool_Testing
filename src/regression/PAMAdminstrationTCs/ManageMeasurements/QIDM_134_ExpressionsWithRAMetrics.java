package regression.PAMAdminstrationTCs.ManageMeasurements;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies the data for the measurements where expressions are referring to different RA Metrics.  
 * check the below measurements in manage measurements to see the expressions. 
 * PAMAutoTest-RAMetricTest1 
 * PAMAutoTest-RAMetricTest2
 * PAMAutoTest-RAMetricTest3
 */
public class QIDM_134_ExpressionsWithRAMetrics extends TestBase {
	LoginTC login = null;
	String PAMAutoTest_RAMetricTest1 ="PAMAutoTest-RAMetricTest1";
	String PAMAutoTest_RAMetricTest2 ="PAMAutoTest-RAMetricTest2";
	String PAMAutoTest_RAMetricTest3 ="PAMAutoTest-RAMetricTest3";
	
	@Test
	public void expressionsWithRAMetrics() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Goto Manage Measurement page
			gotoManageMeasurementPage();
			
			//Clicked on Configurations Tab
			getWebElementXpath("ConfigureLocationsTab_ManageMeasurements").click();
			Thread.sleep(3000);
			//Filter with subsite name 'PAMTest_Main Meter'
			getWebElementActionXpath("LocationPanelFilter").click();
			getWebElementXpath("LocationPanelFilter").sendKeys(testData.getProperty("PAMTestCapriataSaiwa"));
			Thread.sleep(2000);
			getWebElementXpath("LocationPanelFilter").sendKeys(Keys.TAB);
			Thread.sleep(2000);
			
			//Click on site 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa']").click();
			Thread.sleep(5000);
			aJaxWait();
			Thread.sleep(5000);
			printLog("Selected PAMTest_Capriata/Saiwa from configuration tab");
			
			//Filter with measurement name 'PAMAutoTest_RAMetricTest1
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(PAMAutoTest_RAMetricTest1);			
			Thread.sleep(2000);
			printLog("Filtered the easurement "+PAMAutoTest_RAMetricTest1);
			//Clicked on setting icon for the measurement
			getWebElementXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(5000);
			//Cleared and added the expression from edit expression
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys("[Electricity:ENERGY][kWh] / {RAMetric(PAMAutoTest-ProductionUnits) }");
			getWebElementActionXpath("EditExpression_Validate").click();
			Thread.sleep(2000);
			//expression Validation verification
			Assert.assertTrue(isElementPresent_D("//span[contains(text(),'The expression is successfully evaluated')]"));
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(3000);
			printLog("Added the expression and validated it for "+PAMAutoTest_RAMetricTest1);
			
			//Filter with measurement name 'PAMAutoTest_RAMetricTest2
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(PAMAutoTest_RAMetricTest2);			
			Thread.sleep(2000);
			printLog("Filtered the easurement "+PAMAutoTest_RAMetricTest2);
			//Clicked on setting icon for the measurement
			getWebElementXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(5000);
			//Cleared and added the expression from edit expression
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys("[Electricity:ENERGY][kWh]-{RAMetric(AutoTest-MonthlyTarget)}");
			getWebElementActionXpath("EditExpression_Validate").click();
			Thread.sleep(5000);
			//expression Validation verification
			Assert.assertTrue(isElementPresent_D("//span[contains(text(),'The expression is successfully evaluated')]"));
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(3000);
			printLog("Added the expression and validated it for "+PAMAutoTest_RAMetricTest2);
			
			//Filter with measurement name 'PAMAutoTest_RAMetricTest3
			getWebElementXpath("FilterByNameAndUOM").clear();
			getWebElementXpath("FilterByNameAndUOM").sendKeys(PAMAutoTest_RAMetricTest3);			
			Thread.sleep(2000);
			printLog("Filtered the easurement "+PAMAutoTest_RAMetricTest3);
			//Clicked on setting icon for the measurement
			getWebElementXpath("ConfigLocationMeasurementSettingIcon").click();
			Thread.sleep(5000);
			//Cleared and added the expression from edit expression
			getWebElementXpath("EditExpression_Textarea").clear();
			getWebElementXpath("EditExpression_Textarea").sendKeys(" [Electricity:ENERGY][kWh]*[[DisaggregationMethod=>Constant]]{RAMetric(PAMAuto_AnnualMetric)}");
			getWebElementActionXpath("EditExpression_Validate").click();
			Thread.sleep(2000);
			//expression Validation verification
			Assert.assertTrue(isElementPresent_D("//span[contains(text(),'The expression is successfully evaluated')]"));
			getWebElementActionXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(3000);
			printLog("Added the expression and validated it for "+PAMAutoTest_RAMetricTest3);
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");

			Utility.selectSingleMeasurement("Electricity",PAMAutoTest_RAMetricTest1,userDefined);
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			//Click on the measurements for subsite
			getWebElementXpath("PAMTest_MainMeter_Energy").click();			
			
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			//Select the time interval - By month
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By month");
			printLog("Selected interval By month");
			
			refreshToLoadTheChart();
			Thread.sleep(10000);
			List<WebElement> list = d.findElements(By.xpath("//*[contains(@class,'highcharts-point')]"));
			System.out.println("list..."+list.size());
			
			WebElement icon = d.findElement(By.xpath("(//*[contains(@class,'highcharts-point')])[1]"));
			String toolTipText =null;
			Actions a = new Actions(d);
			a.moveToElement(icon,1,1).moveToElement(icon,1,1).build().perform();
			Thread.sleep(300);
			if (getWebElementXpath("tooltiptext") != null) {
				toolTipText = getWebElementXpath("tooltiptext").getText();
				printLog("toolTipText..."+toolTipText);
			}
			
			//Chart tool tip
			Utility.moveTheElement("//*[contains(@class,'highcharts-point')]");
			String chartToolTip=getWebElementXpath("tooltiptext").getText();
			Assert.assertTrue(chartToolTip.contains(PAMAutoTest_RAMetricTest1+": 261.8 kWh"));
			String legendValue=getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legendValue, "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			printLog("Legend and chart tool tip verification completed for "+PAMAutoTest_RAMetricTest1);
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table verification started
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), PAMAutoTest_RAMetricTest1+" (kWh)");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/th").getText(), "Jan 2023");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/td").getText(), "261.8");
			Utility.moveTheScrollToTheTop();
			printLog("Verification completed for "+PAMAutoTest_RAMetricTest1);
			
			//Started for PAMAutoTest_RAMetricTest2
			Utility.selectSingleMeasurement("Electricity",PAMAutoTest_RAMetricTest2,userDefined);
			aJaxWait();
			
			WebElement icon1 = d.findElement(By.xpath("(//*[contains(@class,'highcharts-point')])[1]"));
			toolTipText =null;
			a = new Actions(d);
			a.moveToElement(icon1,1,1).moveToElement(icon1,1,1).build().perform();
			Thread.sleep(300);
			if (getWebElementXpath("tooltiptext") != null) {
				toolTipText = getWebElementXpath("tooltiptext").getText();
				printLog("toolTipText..."+toolTipText);
			}
			//Chart tool tip
			Utility.moveTheElement("//*[contains(@class,'highcharts-point')]");
			chartToolTip=getWebElementXpath("tooltiptext").getText();
			Assert.assertTrue(chartToolTip.contains(PAMAutoTest_RAMetricTest2+": 260,282"));
			legendValue=getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legendValue, "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			printLog("Legend and chart tool tip verification completed for "+PAMAutoTest_RAMetricTest2);
			
			//Table verification started
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), PAMAutoTest_RAMetricTest2+" (kWh)");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/th").getText(), "Jan 2023");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/td").getText(), "260,282");
			Utility.moveTheScrollToTheTop();
			printLog("Verification completed for "+PAMAutoTest_RAMetricTest2);
			
			//Started for PAMAutoTest_RAMetricTest3
			Utility.selectSingleMeasurement("Electricity",PAMAutoTest_RAMetricTest3,userDefined);
			aJaxWait();
			
			WebElement icon2 = d.findElement(By.xpath("(//*[contains(@class,'highcharts-point')])[1]"));
			toolTipText =null;
			a = new Actions(d);
			a.moveToElement(icon2,1,1).moveToElement(icon2,1,1).build().perform();
			Thread.sleep(300);
			if (getWebElementXpath("tooltiptext") != null) {
				toolTipText = getWebElementXpath("tooltiptext").getText();
				printLog("toolTipText..."+toolTipText);
			}
			//Chart tool tip
			Utility.moveTheElement("//*[contains(@class,'highcharts-point')]");
			chartToolTip=getWebElementXpath("tooltiptext").getText();
			Assert.assertTrue(chartToolTip.contains(PAMAutoTest_RAMetricTest3+": 143,979"));
			legendValue=getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legendValue, "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter Cost per Unit");
			printLog("Legend and chart tool tip verification completed for "+PAMAutoTest_RAMetricTest3);
			
			//Table verification started
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), PAMAutoTest_RAMetricTest3+" (Cost per Unit)");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/th").getText(), "Jan 2023");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/td").getText(), "143,979");
			Utility.moveTheScrollToTheTop();
			printLog("Verification completed for "+PAMAutoTest_RAMetricTest3);
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
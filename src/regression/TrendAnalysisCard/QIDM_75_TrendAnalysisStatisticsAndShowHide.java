package regression.TrendAnalysisCard;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies Statistics under 'Statistics' tab and Show/Hide Statistics option under kabob menu.
 * Verifies Statistics values are as expected for select date range and measurements and stats , 
 * then verifies if stats are ordered and hidden as per selections made using show/hide option. 
 * Then pushes the same chart to the dashboard and verifies starts are displayed as expected 
 * under callouts and statistics tab.
 */
 
public class QIDM_75_TrendAnalysisStatisticsAndShowHide extends TestBase {
	LoginTC login = null;

	@Test
	public void trendAnalysisStatisticsAndShowHide() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select the measurement 'Energy' from Electricity commodity for site 'PAMTest_Capriata/Saiwa'
			Utility.selectSingleMeasurement("Electricity","Energy",standard);			
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			// Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			// Selected AutoTest_StoreHours from Site Schedule from DataBreakdown
			dataBreakDownSiteSchedule("Site Schedule","AutoTest_StoreHours");
			printLog("Selected AutoTestTemplate fom Site Schedule from DataBreakdown");
			d.navigate().refresh();
			aJaxWait();
			Thread.sleep(15000);
			
			//Hide option and location panel
			hideOptionLocationPanel();
			
			//Enlarge bottom panel
			enlargeBottomTabsPanel();

			//Verify statistics value
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total Usage");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "261,782");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementTitle").getText().replaceAll("\\n", " "), "Total Usage Close");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementValue").getText(), "197,985");
			Assert.assertEquals(getWebElementXpath("Statistics_ThirdElementTitle").getText().replaceAll("\\n", " "), "Total Usage Open");
			Assert.assertEquals(getWebElementXpath("Statistics_ThirdElementValue").getText(), "63,797");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementTitle").getText(), "Avg Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementValue").getText(), "8,445");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementTitle").getText(), "Max Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementValue").getText(), "25,452");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementTitle").getText(), "Min Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementValue").getText(), "1,878");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementTitle").getText(), "Raw Data");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementValue").getText(), "100.0");			
			//Assert.assertEquals(getWebElementXpath("Statistics_SeventhElementValue").getText(), "99.87");
			printLog("Verification of statistics tab data is completed!!");
			
			//Select "Show/Hide Statistics" from kabob menu options
			kabobMenuOptions("Show/Hide Statistics");
			
			//Remove some statistics variables. SO we are removing Min Daily Usage.
			getWebElementActionXpath_D("//span[normalize-space()='- Min Daily Usage']/parent::div/following-sibling::div/i[1]").click();
			//Drag and drop the statistics variables 
			//dragAndDrop("Drag1", "Drag2");
			//Reporter.log("Dragged and dropped the statistics variables");
			Thread.sleep(3000);
			getWebElementXpath_D("//button[normalize-space()='Apply']").click();
			aJaxWait();
			Thread.sleep(15000);
			
			//Verify statistics value after changes
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Total Usage");
			Assert.assertEquals(getWebElementXpath("Statistics_FirstElementValue").getText(), "261,782");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementTitle").getText().replaceAll("\\n", " "), "Total Usage Open");
			Assert.assertEquals(getWebElementXpath("Statistics_SecondElementValue").getText(), "63,797");
			Assert.assertEquals(getWebElementXpath("Statistics_ThirdElementTitle").getText().replaceAll("\\n", " "), "Total Usage Close");
			Assert.assertEquals(getWebElementXpath("Statistics_ThirdElementValue").getText(), "197,985");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementTitle").getText(), "Avg Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementValue").getText(), "8,445");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementTitle").getText(), "Max Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementValue").getText(), "25,452");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementTitle").getText(), "Raw Data");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementValue").getText(), "100.0");
			//Assert.assertEquals(getWebElementXpath("Statistics_SixthElementValue").getText(), "99.87");
			printLog("Verification of statistics tab data on after modify is completed.");
			
			//Pushed the widget to dashboard
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			
			//Clicked on statistics tab on widget
			getWebElementActionXpath_D("//gridster/gridster-item[1]/ra-widget-container/div/div[2]/div[2]/div/pam-trend-widget/div/ra-tabs/div[1]/ul/li[2]/a").click(); 
			Thread.sleep(2000);
			
			String basicstatiXpath="//gridster/gridster-item[1]/ra-widget-container/div/div[2]/div[2]/div/pam-trend-widget/div/ra-tabs/div[3]/ra-tab[2]/div/div/ra-call-out/div/div/div[1]/div/";
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[2]/div[2]/div/div[1]/ra-tooltip/div/div/div/span").getText(), "Total Usage");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[2]/div[2]/div/div[2]/ra-tooltip/div/div/div/span").getText(), "261,782");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[2]/div[2]/div/div[3]/ra-tooltip/div/div").getText(), "kWh");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[3]/div[2]/div/div[1]/ra-tooltip/div/div/div/span").getText(), "Total Usage - Open");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[3]/div[2]/div/div[2]/ra-tooltip/div/div/div/span").getText(), "63,797");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[3]/div[2]/div/div[3]/ra-tooltip/div/div").getText(), "kWh");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[3]/div[2]/div/div[4]/ra-tooltip/div/div").getText(), "24.37%");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[4]/div[2]/div/div[1]/ra-tooltip/div/div/div/span").getText(), "Total Usage - Close");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[4]/div[2]/div/div[2]/ra-tooltip/div/div/div/span").getText(), "197,985");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[4]/div[2]/div/div[3]/ra-tooltip/div/div").getText(),  "kWh");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[4]/div[2]/div/div[4]/ra-tooltip/div/div").getText(),  "75.63%");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[5]/div[2]/div/div[1]/ra-tooltip/div/div/div/span").getText(), "Avg Daily Usage");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[5]/div[2]/div/div[2]/ra-tooltip/div/div/div/span").getText(),"8,445");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[5]/div[2]/div/div[3]/ra-tooltip/div/div").getText(),"kWh");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[6]/div[2]/div/div[1]/ra-tooltip/div/div/div/span").getText(), "Max Daily Usage");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[6]/div[2]/div/div[2]/ra-tooltip/div/div/div/span").getText(), "25,452");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[6]/div[2]/div/div[3]/ra-tooltip/div/div").getText(), "kWh");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[6]/div[2]/div/div[4]/ra-tooltip/div/div").getText(), "1/24/23");

			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[7]/div[2]/div/div[1]/ra-tooltip/div/div/div/span").getText(), "Raw Data");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[7]/div[2]/div/div[2]/ra-tooltip/div/div/div/span").getText(), "100.0%");
			//Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[7]/div[2]/div/div[2]/ra-tooltip/div/div/div/span").getText(), "99.87%");
			Assert.assertEquals(getWebElementActionXpath_D(basicstatiXpath+"div[7]/div[2]/div/div[3]/ra-tooltip/div/div").getText(), "Complete");

			printLog("Verified the statistics tab data on dashboard widget");

			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

package regression.TrendAnalysisCard;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies Comparison Data Options from kabob menu in Trend card.  
 * Verifies each chart options under Comparison Data Options are displayed correctly in PAM chart and 
 * at the end pushes the chart to dashboard and verifies widget is displayed same as PAM card
 */

public class QIDM_74_TrendAnalysisComparisonDataOptions extends TestBase{
	LoginTC login = null;
	
	@Test
	public void trendAnalysisComparisonDataOptions() throws Throwable {
		try {
			
			String PAMTest_CapriataSaiwakWhPreviousMonth="PAMTest_Capriata/Saiwa kWh (Previous Month)";
			String PAMTest_CapriataSaiwaSavingsvsPreviousMonth="PAMTest_Capriata/Saiwa % (Savings vs. Previous Month)";
			
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
			
			Utility.moveTheScrollToTheTop();
			Assert.assertEquals(getWebElementXpath("ChartStartDate").getText(), testData.getProperty("FixedDateStartJan012023"));
			Assert.assertEquals(getWebElementXpath("ChartEndDate").getText(), testData.getProperty("FixedDateEndJan312023"));
			printLog("Verified the date range on chart");
			Utility.moveTheScrollToTheDown();
			
			comparisionDateRange("Previous Month");
			
			// Verify the legends display under the chart.
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), PAMTest_CapriataSaiwakWhPreviousMonth);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), "PAMTest_Capriata/Saiwa kWh (Variance from Previous Month)");
			printLog("Verified legends from the chart.");
			
			String chartOptionPath = "//*[@id='comparisonDataOptionsForm']/div/div/select";
			// Click on KabobMenu and select Chart Options and select 'Show Variance Only' option
			kabobMenuOptions("Comparison Data Options");			
			String chartOption = getWebElementXpath_D(chartOptionPath).getAttribute("value");
			Assert.assertEquals(chartOption, "AllWithVariance");
			printLog("Verified the default Chart option");
			
			new Select(getWebElementXpath_D(chartOptionPath)).selectByValue("VarianceOnly");
			getWebElementXpath_D("//*[@id='comparisonDataOptionsForm']/div[2]/button[2]").click();			
			aJaxWait();					
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh (Variance from Previous Month)");
			printLog("Verified the legends for 'Show Variance Only' option");
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Comparison Data Options");
			new Select(getWebElementXpath_D(chartOptionPath)).selectByValue("TimelineWithComparison");
			getWebElementXpath_D("//*[@id='comparisonDataOptionsForm']/div[2]/button[2]").click();
			aJaxWait();
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), PAMTest_CapriataSaiwakWhPreviousMonth);
			printLog("Verified the legends for 'Show Variance as Savings' option");
			
			// Click on KabobMenu and selected Comparison Data Options and selected 'Show Variance as Savings' checkbox.
			kabobMenuOptions("Comparison Data Options");
			new Select(getWebElementXpath_D(chartOptionPath)).selectByValue("AllWithVariance");
			getWebElementXpath_D("//*[@id='comparisonDataOptionsForm']/div[1]/div[2]/input").click();
			getWebElementXpath_D("//*[@id='comparisonDataOptionsForm']/div[2]/button[2]").click();
			aJaxWait();
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), PAMTest_CapriataSaiwakWhPreviousMonth);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), "PAMTest_Capriata/Saiwa kWh (Savings vs. Previous Month)");
			printLog("Verified the legends for 'Show Variance as Savings' option");
			
			// Click on KabobMenu and selected Comparison Data Options and selected 'Show Variance as Percentage' checkbox.
			kabobMenuOptions("Comparison Data Options");
			getWebElementXpath_D("//*[@id='comparisonDataOptionsForm']/div[1]/div[3]/input").click();
			getWebElementXpath_D("//*[@id='comparisonDataOptionsForm']/div[2]/button[2]").click();
			aJaxWait();
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), PAMTest_CapriataSaiwakWhPreviousMonth);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), PAMTest_CapriataSaiwaSavingsvsPreviousMonth);
			printLog("Verified the legends for 'Show Variance as Percentage' option");
			
			//Pushed the widget to dashboard
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			
			//Verified the date range and legends from dashboard widget
			Assert.assertEquals(d.findElement(By.xpath("//*[@class='highcharts-title']")).getText(), "1/1/23 - 1/31/23");
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("ColumnLegendTwo").getText(), PAMTest_CapriataSaiwakWhPreviousMonth);
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText(), PAMTest_CapriataSaiwaSavingsvsPreviousMonth);
			printLog("Verified the date range and legends from dashboard widget");
			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

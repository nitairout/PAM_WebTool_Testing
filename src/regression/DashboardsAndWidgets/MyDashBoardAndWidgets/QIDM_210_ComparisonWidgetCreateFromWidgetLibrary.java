package regression.DashboardsAndWidgets.MyDashBoardAndWidgets;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies creating new Comparison Analysis widget from widget library and 
 * checks widget is created for selected sites with default widget settings. 
 * And it also verifies changing date range from widget settings.
 * 
 */

public class QIDM_210_ComparisonWidgetCreateFromWidgetLibrary extends TestBase{
	LoginTC login = null;
	@Test
	public void comparisonWidgetCreateFromWidgetLibrary() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			Utility.pushWidgetFromWidgetLibrary("Comparison Analysis");
			
			//Calculating and verifying current month
			LocalDate currentdate = LocalDate.now();
			String widgetStartDate=changeTheDateFormat("M/d/yy", currentdate.with(TemporalAdjusters.firstDayOfYear()));
			String widgetEndDate=changeTheDateFormat("M/d/yy", currentdate);

			Assert.assertEquals(getWebElementXpath_D("(//*[@class='highcharts-title']/*[name()='tspan'])[1]").getText(),widgetStartDate+" - "+widgetEndDate);
			
			ArrayList<String> legendArray=new ArrayList<String>();
			legendArray.add("Energy kWh");
			legendArray.add("PAMTest_Herentals, Biscuits");
			legendArray.add(testData.getProperty("PAMTestNapervilleIL"));
			
			//Legend verification
			Assert.assertTrue(legendArray.contains(getWebElementActionXpath_D("//*[contains(@class,'highcharts-legend')]/span[1]").getText()));
			Assert.assertTrue(legendArray.contains(getWebElementActionXpath_D("//*[@class='highcharts-axis-labels highcharts-xaxis-labels']/*[name()='text'][1]").getText()));
			Assert.assertTrue(legendArray.contains(getWebElementActionXpath_D("//*[@class='highcharts-axis-labels highcharts-xaxis-labels']/*[name()='text'][2]").getText()));
			printLog("Legend and start end date verification complated.");
			
			//Pie tab
			getWebElementActionXpath_D("//a[contains(text(),'Pie') and @class='e-tab-a']").click();
			Thread.sleep(2000);
			
			Assert.assertEquals(getWebElementXpath_D("(//*[@class='highcharts-title']/*[name()='tspan'])[2]").getText(),widgetStartDate+" - "+widgetEndDate);
			
			String pieLegendOne = getWebElementXpath("PieChartLegendOne").getText().replaceAll("\\n", " ");
			String pieLegendTwo = getWebElementXpath("PieChartLegendTwo").getText().replaceAll("\\n", " ");
			Assert.assertEquals(pieLegendOne, "PAMTest_Herentals, Biscuits Energy kWh");
			Assert.assertEquals(pieLegendTwo, "PAMTest_Naperville, IL Energy kWh");
			printLog("pie chart Legend and start end date verification complated.");
			
			getWebElementActionXpath("NewWidgetTableTab").click();
			Thread.sleep(1000);
			
			//Table tab header and site verification
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr/th[2]").getText(), "Energy (kWh)");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), "Energy (kWh) %");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr[1]/th").getText(), "PAMTest_Herentals, Biscuits");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr[2]/th").getText(), testData.getProperty("PAMTestNapervilleIL"));
			
			//Table tab data verification
			int tableDatasRow=d.findElements(By.xpath("//*[@id='tablebody']/tr/td")).size();
			String tableData="";
			for(int i=1;i<=tableDatasRow;i++) {
				tableData=getWebElementXpath_D("(//*[@id='tablebody']/tr/td)["+i+"]").getText();
				Assert.assertNotNull(tableData);
			}
			printLog("Verified table data, header and sites.");
			
			Utility.clickOnEditLinkOfWidget("Edit");
			
			//Date range period DD (By default it is Rolling Period selected)
			getWebElementActionXpath_D("//pam-timeline//div[@class='ra-dropdown-input']/span").click();
			getWebElementXpath_D("//span[contains(text(),'Month to Date')]").click();
			
			getWebElementActionXpath("WidgetConfigSaveButton").click();
			Thread.sleep(20000);
			printLog("Changed to Month to Date from widget config page.");
			
			//Clicked on Chart tab as the last verification was in table tab
			getWebElementActionXpath("NewWidgetChartTab").click();
			Thread.sleep(1000);
			
			widgetStartDate=changeTheDateFormat("M/d/yy", currentdate.with(TemporalAdjusters.firstDayOfMonth()));
			widgetEndDate=changeTheDateFormat("M/d/yy", currentdate);

			Assert.assertEquals(getWebElementXpath_D("(//*[@class='highcharts-title']/*[name()='tspan'])[1]").getText(),widgetStartDate+" - "+widgetEndDate);
			
			//Legend verification for edited widget
			Assert.assertTrue(legendArray.contains(getWebElementActionXpath_D("//*[contains(@class,'highcharts-legend')]/span[1]").getText()));
			Assert.assertTrue(legendArray.contains(getWebElementActionXpath_D("//*[@class='highcharts-axis-labels highcharts-xaxis-labels']/*[name()='text'][1]").getText()));
			Assert.assertTrue(legendArray.contains(getWebElementActionXpath_D("//*[@class='highcharts-axis-labels highcharts-xaxis-labels']/*[name()='text'][2]").getText()));
			printLog("Legend and start end date verification complated for edited widget.");
			
			//Pie tab for edited widget
			getWebElementActionXpath_D("//a[contains(text(),'Pie') and @class='e-tab-a']").click();
			Thread.sleep(2000);
			
			Assert.assertEquals(getWebElementXpath_D("(//*[@class='highcharts-title']/*[name()='tspan'])[2]").getText(),widgetStartDate+" - "+widgetEndDate);
			
			pieLegendOne = getWebElementXpath("PieChartLegendOne").getText().replaceAll("\\n", " ");
			pieLegendTwo = getWebElementXpath("PieChartLegendTwo").getText().replaceAll("\\n", " ");
			Assert.assertEquals(pieLegendOne, "PAMTest_Herentals, Biscuits Energy kWh");
			Assert.assertEquals(pieLegendTwo, "PAMTest_Naperville, IL Energy kWh");
			printLog("pie chart Legend and start end date verification complated for edited widget.");
			
			Utility.deleteAllWidgets();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
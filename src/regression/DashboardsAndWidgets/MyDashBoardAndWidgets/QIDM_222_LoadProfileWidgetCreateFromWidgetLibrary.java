package regression.DashboardsAndWidgets.MyDashBoardAndWidgets;
import java.time.LocalDate;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies creating new Load Profile Analysis widget from widget library 
 * and checks widget is created for selected site with default widget settings. 
 * And it also verifies going to PAM card by selecting 'Visit Page' option under settings
 * 
 */

public class QIDM_222_LoadProfileWidgetCreateFromWidgetLibrary extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileWidgetCreateFromWidgetLibrary() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			Utility.pushWidgetFromWidgetLibrary("Load Profile Analysis");
			
			//Calculating and verifying last 30 days date
			LocalDate today = LocalDate.now();
			String expectedEndChartDate =changeTheDateFormat("M/d/yy",today.plusDays(1))+", 12:00 AM";
			String expectedStartChartDate =changeTheDateFormat("M/d/yy",today.minusDays(6))+", 12:00 AM";
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-title']").getText(),expectedStartChartDate+" - "+expectedEndChartDate);

			ArrayList<String> expectedLegendArrayList=new ArrayList<String>();
			expectedLegendArrayList.add("PAMTest_Capriata/Saiwa kW");
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwaGJ"));
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			
			int allLegendsSize=d.findElements(By.xpath("//gridster/gridster-item[1]/ra-widget-container//div[@class='highcharts-legend']//div/span")).size();
			String actualLegend="";
			for(int i=1;i<=allLegendsSize;i++) {
				actualLegend=getWebElementXpath_D("(//gridster/gridster-item[1]/ra-widget-container//div[@class='highcharts-legend']//div/span)["+i+"]").getText().replace("\n", " ");
				Assert.assertTrue(expectedLegendArrayList.contains(actualLegend));
			}
			printLog("Legend verification complated.");
			
			//Call out verification
			int chartCalloutDataSize=d.findElements(By.xpath("//ra-tab[1]//div[@class='callout-data']//div[contains(@class,'call-out-item')]")).size();
			for(int i=1;i<=chartCalloutDataSize;i++) {
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[1]//div[@class='callout-data']//span[contains(@class,'callout-title')])["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[1]//div[@class='callout-data']//span[contains(@class,'callout-value')])["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[1]//div[@class='callout-data']//span[contains(@class,'callout-uom')])["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[1]//div[@class='callout-data']//span[contains(@class,'callout-uom')])["+i+"]").getText());
			}
			printLog("Chart call out verification completed.");
			
			//On Statistics tab
			getWebElementXpath("NewWidgetStatisticsTab").click();
			Thread.sleep(1000);
			
			//Though we have 24 call out data in stats tab, we are only verifying 4(1st row) data. So commented below line
			//int statsCalloutDataSize=d.findElements(By.xpath("//ra-tab[2]//div[@class='item-flex']")).size();
			for(int i=1;i<4;i++) {
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[2]//div[@class='item-flex']//span[contains(@class,'callout-title')])["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[2]//div[@class='item-flex']//div[contains(@class,'callout-value')]/span)["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[2]//div[@class='item-flex']//div[contains(@class,'callout-uom')])["+i+"]").getText());
			}
			printLog("Statistics call out verification completed.");
			
			//On Table tab
			getWebElementXpath("NewWidgetTableTab").click();
			Thread.sleep(1000);
			
			//Table header verification for comparison date as previous month
			Assert.assertEquals(testData.getProperty("PAMTestCapriataSaiwa"), getWebElementXpath_D("//thead[@id='tablehead']/tr[1]/th[2]").getText());
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[1]").getText(),"Demand (kW)");
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[2]").getText(),"Volume (m3)");
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[3]").getText(),"Energy (GJ)");
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[4]").getText(),"Volume (m3)");
			//Checking table data notnull
			for(int i=0;i<3;i++) {
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/th[1]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[1]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[2]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[3]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[4]").getText());
			}
			printLog("Verified table header and data from chart.");
			
			Utility.clickOnEditLinkOfWidget("Visit Page");
			printLog("Came to PAM page now.");

			//Visit page is opened another tab
			ArrayList<String> tabs2 = new ArrayList<String> (d.getWindowHandles());
			d.switchTo().window(tabs2.get(1));
			Thread.sleep(15000);

			//Inside 2nd tab now
			String expectedEndPAMChartDate =changeTheDateFormat("M/d/yyyy",today.plusDays(1))+" 12:00 AM";
			String expectedStartPAMChartDate =changeTheDateFormat("M/d/yyyy",today.minusDays(6))+" 12:00 AM";
			//Chart actual start and end date
			String actualStartChartDate=getWebElementXpath("ChartStartDate").getText();
			String actualEndChartDate=getWebElementXpath("ChartEndDate").getText();
			//verify actual date range is as expected date range
			Assert.assertEquals(actualStartChartDate, expectedStartPAMChartDate);
			Assert.assertEquals(actualEndChartDate, expectedEndPAMChartDate);
			
			int allPAMLegendsSize=d.findElements(By.xpath("//div[contains(@class,'highcharts-legend')]//div/span")).size();
			String actualPAMLegend="";
			for(int i=1;i<=allPAMLegendsSize;i++) {
				actualPAMLegend=getWebElementXpath_D("(//div[contains(@class,'highcharts-legend')]//div/span)["+i+"]").getText().replace("\n", " ");
				Assert.assertTrue(expectedLegendArrayList.contains(actualPAMLegend));
			}
			printLog("Legend verification complated on PAM page.");
			
			//Hide optional and location panel
			Utility.hideOptionLocationPanelForCards();
			Utility.moveTheScrollToTheDown();
			
			//Statistics call out verification
			for(int i=1;i<4;i++) {
				Assert.assertNotNull(getWebElementXpath_D("(//pam-statistic//*[@class='se-statistic-title'])["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//pam-statistic//*[@class='se-statistic-value'])["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//pam-statistic//*[@class='se-statistic-unit'])["+i+"]").getText());
			}
			printLog("Statistics call out data verification completed.");
			
			d.close();
			d.switchTo().window(tabs2.get(0));
			printLog("Closed the 2nd tab and came to parent tab.");
			
			Utility.deleteAllWidgets();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
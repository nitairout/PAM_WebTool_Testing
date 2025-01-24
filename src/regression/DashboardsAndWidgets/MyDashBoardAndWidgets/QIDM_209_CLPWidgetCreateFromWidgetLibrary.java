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
 * This test verifies creating new Calendar Load Profile Analysis widget 
 * from widget library and checks widget is created for selected site with 
 * default widget settings.  And it also verifies going to PAM card by selecting 
 * 'Configure' under settings options.
 * 
 */

public class QIDM_209_CLPWidgetCreateFromWidgetLibrary extends TestBase{
	LoginTC login = null;
	@Test
	public void clpWidgetCreateFromWidgetLibrary() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			Utility.pushWidgetFromWidgetLibrary("Calendar Load Profile Analysis");
			
			//Calculating and verifying current month
			LocalDate currentdate = LocalDate.now();
			String currentMonth=currentdate.getMonth().toString()+" "+currentdate.getYear();
			Assert.assertTrue(getWebElementXpath_D("//div[@class='header-month']").getText().equalsIgnoreCase(currentMonth));
			
			ArrayList<String> expectedLegendArrayList=new ArrayList<String>();
			expectedLegendArrayList.add("PAMTest_Capriata/Saiwa kW");
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwaGJ"));
			expectedLegendArrayList.add("PAMTest_Capriata/Saiwa \u00B0C");
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			expectedLegendArrayList.add("PAMTest_Capriata/Saiwa %");
			
			int allLegendsSize=d.findElements(By.xpath("//gridster/gridster-item[1]/ra-widget-container//span[@class='calendar-legend-item']/span")).size();
			String actualLegend="";
			for(int i=1;i<=allLegendsSize;i++) {
				actualLegend=getWebElementXpath_D("(//gridster/gridster-item[1]/ra-widget-container//span[@class='calendar-legend-item']/span)["+i+"]").getText().replace("\n", " ");
				Assert.assertTrue(expectedLegendArrayList.contains(actualLegend));
			}
			printLog("Legend verification complated.");
			
			Utility.clickOnEditLinkOfWidget("Edit");
			
			//Configure button
			getWebElementActionXpath_D("//a[contains(text(),'Configure')]").click();
			Thread.sleep(20000);
			printLog("Came inside PAM page.");
			
			//PAM page Verification started 
			currentMonth=currentdate.getMonth().toString()+" "+currentdate.getYear();
			Assert.assertTrue(getWebElementActionXpath("ClpChartMonth").getText().equalsIgnoreCase(currentMonth));
			
			int allPAMLegendsSize=d.findElements(By.xpath("//span[@class='legend-text--calendar']/span")).size();
			String actualPAMLegend="";
			for(int i=1;i<=allPAMLegendsSize;i++) {
				actualPAMLegend=getWebElementXpath_D("(//span[@class='legend-text--calendar']/span)["+1+"]").getText().replace("\n", " ");
				Assert.assertTrue(expectedLegendArrayList.contains(actualPAMLegend));
			}
			printLog("PAM Legend and date range verification complated.");
			
			getWebElementActionXpath_D("//span[contains(text(),'Last month')]").click();
			aJaxWait();
			Thread.sleep(10000);
			getWebElementActionXpath_D("//div[contains(text(),'Save')]").click();
			Thread.sleep(20000);
			printLog("Clicked on Lst Month and saved to the dashboard.");
			
			String previousMonth=currentdate.getMonth().minus(1).toString()+" "+currentdate.getYear();
			if(currentdate.getMonth().toString().equalsIgnoreCase("JANUARY")) {
				int year = currentdate.getYear()-1;
				previousMonth=currentdate.getMonth().minus(1).toString()+" "+year;
			}
			Assert.assertTrue(getWebElementXpath_D("//div[@class='header-month']").getText().equalsIgnoreCase(previousMonth));
			
			Utility.deleteAllWidgets();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
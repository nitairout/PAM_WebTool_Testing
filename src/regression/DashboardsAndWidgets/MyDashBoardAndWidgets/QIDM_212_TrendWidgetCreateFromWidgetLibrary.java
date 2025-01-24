package regression.DashboardsAndWidgets.MyDashBoardAndWidgets;
import java.time.LocalDate;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies creating new Trend Analysis widget from widget 
 * library and checks widget is created for selected site with default widget settings. 
 * And it also verifies site and date range locking functionality
 * 
 */

public class QIDM_212_TrendWidgetCreateFromWidgetLibrary extends TestBase{
	LoginTC login = null;
	@Test
	public void trendWidgetCreateFromWidgetLibrary() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			Utility.pushWidgetFromWidgetLibrary("Trend Analysis");
			
			//Calculating and verifying last 30 days date
			LocalDate today = LocalDate.now();
			String expectedEndChartDate =changeTheDateFormat("M/d/yy",today);
			String expectedStartChartDate =changeTheDateFormat("M/d/yy",today.minusDays(29));
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-title']").getText(),expectedStartChartDate+" - "+expectedEndChartDate);

			//Call out verification
			int chartCalloutDataSize=d.findElements(By.xpath("//ra-tab[1]//div[@class='callout-data']//div[contains(@class,'call-out-item')]")).size();
			for(int i=1;i<=chartCalloutDataSize;i++) {
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[1]//div[@class='callout-data']//span[contains(@class,'callout-title')])["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[1]//div[@class='callout-data']//span[contains(@class,'callout-value')])["+i+"]").getText());
				Assert.assertNotNull(getWebElementXpath_D("(//ra-tab[1]//div[@class='callout-data']//span[contains(@class,'callout-uom')])["+i+"]").getText());
			}
			printLog("Chart call out verification completed.");
			
			//Click on Hide Highlights to make the legends display
			WebElement hideHighlights = d.findElement(By.xpath("//gridster/gridster-item[1]//span[contains(text(),'Hide Highlights')]"));
			Actions actionMove = new Actions(d); 
			actionMove.moveToElement(hideHighlights,1,1).perform(); 
			Actions actionsClick = new Actions(d); 
			actionsClick.click().build().perform(); 
			Thread.sleep(5000);
			
			//LEgend verification starts
			ArrayList<String> expectedLegendArrayList=new ArrayList<String>();
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwakWh"));
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwaGJ"));
			expectedLegendArrayList.add("PAMTest_Capriata/Saiwa \u00B0C");
			expectedLegendArrayList.add("PAMTest_Capriata/Saiwa %");
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			expectedLegendArrayList.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			
			int allLegendsSize=d.findElements(By.xpath("//gridster/gridster-item[1]/ra-widget-container//div[@class='highcharts-legend']//div/span")).size();
			String actualLegend="";
			for(int i=1;i<=allLegendsSize;i++) {
				actualLegend=getWebElementXpath_D("(//gridster/gridster-item[1]/ra-widget-container//div[@class='highcharts-legend']//div/span)["+i+"]").getText().replace("\n", " ");
				Assert.assertTrue(expectedLegendArrayList.contains(actualLegend));
			}
			printLog("Legend verification complated.");
			
			//On Statistics tab
			getWebElementActionXpath("NewWidgetStatisticsTab").click();
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
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[1]/th[2]").getText(),testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[1]").getText(),testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[2]").getText(),"Volume (m3)");
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[3]").getText(),"Energy (GJ)");
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[4]").getText(),"Volume (m3)");
			Assert.assertEquals(getWebElementXpath_D("//thead[@id='tablehead']/tr[2]/th[5]").getText(),testData.getProperty("TableHeaderTemperatureCDegree"));
			
			//Checking table data notnull
			for(int i=0;i<3;i++) {
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/th[1]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[1]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[2]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[3]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[4]").getText());
				Assert.assertNotNull(getWebElementXpath_D("//tbody[@id='tablebody']/tr["+(i+1)+"]/td[5]").getText());
			}
			printLog("Verified table header and data from chart.");
			
			Utility.clickOnEditLinkOfWidget("Edit");
			printLog("Came to PAM page now.");
			
			getWebElementActionXpath_D("//*[@id='dv-flyout-main-panel']//div[starts-with(@class,'col-11')]//input[contains(@class,'form-control')]").sendKeys("","Trend Analysis Edit");
			Thread.sleep(1000);
			//site lock icon
			getWebElementActionXpath_D("(//ra-config-lock//i[contains(@class,'fa-lock-open unlock')])[1]").click();
			Thread.sleep(1000);
			//date range lock icon
			getWebElementActionXpath_D("(//ra-config-lock//i[contains(@class,'fa-lock-open unlock')])[1]").click();
			Thread.sleep(1000);
			
			//Save from widget library
			getWebElementXpath("WidgetConfigSaveButton").click();
			Thread.sleep(20000);
			printLog("Saved edited analysis name with locked.");
			
			//Check the lock icon
			Assert.assertTrue(isElementPresent_D("//gridster/gridster-item//i[contains(@class,'fa-lock lock')]"));
			//Tooltip verification for lock icon
			Actions lockButtonAction = new Actions(d);
		    WebElement lockButton = d.findElement(By.xpath("//gridster/gridster-item//i[contains(@class,'fa-lock lock')]"));
		    lockButtonAction.clickAndHold(lockButton).perform();
		    Thread.sleep(2000);
		    String actualTooltipText=getWebElementXpath_D("//div[contains(@class,'lock-popup-content')]").getText().replaceAll("\\n", " ");
		    Assert.assertTrue(actualTooltipText.contains("Date Range Division/Group/Site Locked"));
		    printLog("Verified the lock icon present with the tooltip text.");
		    
		   //Clicked on global filter
			getWebElementXpath_D("//i[@class='fal fa-filter']").click();
			
			//selecting site
			getWebElementActionXpath_D("(//ra-site-search/ra-auto-complete//input[@class='light'])[1]").click();
			getWebElementXpath_D("(//ra-site-search/ra-auto-complete//input[@class='light'])[1]").sendKeys(testData.getProperty("PAMTestNapervilleIL"));
			Thread.sleep(2000);
			getWebElementActionXpath_D("//div[contains(@class,'site-name-body')]//span[contains(text(),'PAMTest_Naperville')]").click();
			printLog("Selected site as PAMTest_Naperville, IL.");
			
			//Static radio button
			getWebElementActionXpath_D("(//ra-radiobutton-list//span[contains(@class,'rbl-checkmark')])[2]").click();
			Thread.sleep(1000);
			//Start date
			getWebElementActionXpath_D("(//div[@class='ra-calendar']/input[contains(@class,'ra-calendar-input')])[1]").click();
			int startYearInCalender=Integer.parseInt(getWebElementXpath_D("//span[@class='ra-cal-year-text']").getText());
			
			//Selecting 2023
			if(startYearInCalender>2023) {
				for(int i=0;i<(startYearInCalender-2023);i++) {
					getWebElementXpath_D("//div[@class='ra-year-head']//i[contains(@class,'far fa-chevron-left')]").click();
				}
			}else {
				for(int i=0;i<(2023-startYearInCalender);i++) {
					getWebElementXpath_D("//div[@class='ra-year-head']//i[@class='far fa-chevron-right']").click();
				}
			}
			//Selecting January as start date
			getWebElementXpath_D("//span[contains(text(),'January')]").click();
			Thread.sleep(1000);
			//End date
			getWebElementActionXpath_D("(//div[@class='ra-calendar']/input[contains(@class,'ra-calendar-input')])[2]").click();
			int endYearInCalender=Integer.parseInt(getWebElementXpath_D("//span[@class='ra-cal-year-text']").getText());
			Thread.sleep(1000);

			//Selecting 2023
			if(endYearInCalender>2023) {
				for(int i=0;i<(endYearInCalender-2023);i++) {
					getWebElementXpath_D("//div[@class='ra-year-head']//i[@class='far fa-chevron-left']").click();
				}
			}else {
				for(int i=0;i<(2023-endYearInCalender);i++) {
					getWebElementXpath_D("//div[@class='ra-year-head']//i[@class='far fa-chevron-right']").click();
				}
			}
			//Selecting January as end date
			getWebElementXpath_D("//span[contains(text(),'January')]").click();
			
			Thread.sleep(1000);
			//Save global filter
			getWebElementActionXpath_D("(//button[contains(@class,'config-save') and contains(text(),'Save')])").click();
			Thread.sleep(15000);
			
			//Global filter verification
			Assert.assertEquals(testData.getProperty("PAMTestNapervilleIL"), getWebElementXpath_D("(//div[contains(@class,'apply-filter-container')]//span)[1]").getText());
			Assert.assertEquals("Jan 2023 - Jan 2023", getWebElementXpath_D("(//div[contains(@class,'apply-filter-container')]//span)[2]").getText());
			printLog("Verified global filtered data.");
			
			getWebElementActionXpath("NewWidgetChartTab").click();
			Thread.sleep(2000);
			//Start and end date for global filter
			expectedEndChartDate =changeTheDateFormat("M/d/yy",today);
			expectedStartChartDate =changeTheDateFormat("M/d/yy",today.minusDays(29));
			Assert.assertEquals(getWebElementXpath_D("//*[@class='highcharts-title']").getText(),expectedStartChartDate+" - "+expectedEndChartDate);

			allLegendsSize=d.findElements(By.xpath("//gridster/gridster-item[1]/ra-widget-container//div[@class='highcharts-legend']//div/span")).size();
			actualLegend="";
			for(int i=1;i<=allLegendsSize;i++) {
				actualLegend=getWebElementXpath_D("(//gridster/gridster-item[1]/ra-widget-container//div[@class='highcharts-legend']//div/span)["+i+"]").getText().replace("\n", " ");
				Assert.assertTrue(expectedLegendArrayList.contains(actualLegend));
			}
			printLog("Legend verification complated for global filter.");
			
			//Removed global filter if PAMTest_Naperville, IL is present
			if(isElementPresent_D("//div[3]/i[@class='fal fa-times img-space1']") || isElementPresent_D("//div[4]/i[@class='fal fa-times img-space1']")) {
				getWebElementActionXpath_D("//div[contains(@class,'right gf-btn-close')]").click();
			}
			
			Utility.deleteAllWidgets();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
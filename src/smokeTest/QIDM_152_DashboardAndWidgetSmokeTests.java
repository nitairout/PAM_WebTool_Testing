package smokeTest;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies creating a dashboard, Saved Analysis, pushing a chart to the dashboard 
 * and creating a widget from widget library.
 */
public class QIDM_152_DashboardAndWidgetSmokeTests extends TestBase {
	LoginTC login = null;
	String dashboard = "Temp Dashboard";
	String analysis = "Trend Smoke Test";

	@Test
	public void dashboardAndWidgetSmokeTests() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", dashboard);
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			goToAnalysisPage();
			Utility.clearsavedanalysis(d);
			goToPAMCard("TrendAnalysisCard");

			// Search with site testData.getProperty("PAMTestCapriataSaiwa")
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			// Click on the measurements
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			
			saveAnalysis(analysis);
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			
			goToAnalysisPage();
			//expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);
			
			boolean savedAnalysis = getWebElementActionXpath_D("//div[contains(text(),'"+analysis+"')]").isDisplayed();
			Assert.assertTrue(savedAnalysis);
			printLog("Verified 'Trend Smoke Test' analysis is there under saved analysis");
			
			//Delete the saved analysis 'Trend Smoke Test'
			getWebElementActionXpath_D("(//div[contains(@class,'se-saved-analyses-grid-icons')]//i[contains(@class,'fas fa-trash clickable')])[1]").click();
			Thread.sleep(6000);
			getWebElementActionXpath_D("//button/span[normalize-space()='Delete']").click();
			Thread.sleep(5000);
			
			//Go to Dashboard page
			gotoDashBoardHome();
			
			//Verified the site name on table
			Assert.assertEquals(getWebElementActionXpath_D("//gridster/gridster-item/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText(),analysis);
			Reporter.log("Verified the widget title");
			
			//Delete the widget
			Utility.deleteAllWidgets();
			
			//Create the widget from widget library 
			Utility.addNewCustomizedWidgets("Analysis","Load Profile Analysis");
			//Click on configure
			//getWebElementActionXpath_D("//ra-dashboard-area/div/gridster/gridster-item[1]/ra-widget-container/div/div[2]/div[3]/ra-widget-state/div/div/div/div[2]/div").click();
			getWebElementActionXpath_D("//div[contains(text(),'Configure Widget')]").click();
			Thread.sleep(3000);
			getWebElementActionXpath_D("//pam-site//ra-site-search//input[@class='light']").click();
			getWebElementXpath_D("//pam-site//ra-site-search//input[@class='light']").sendKeys(testData.getProperty("PAMTestCapriataSaiwa"));
			Thread.sleep(1000);
			getWebElementActionXpath_D("//div[@class='site-name-body']/span[contains(text(),'PAMTest_Capriata/Saiwa')]").click();
			printLog("Selected site as PAMTest_Capriata/Saiwa.");
			//Save the widgets
			getWebElementXpath("WidgetConfigSaveButton").click();
			aJaxWait();
			Thread.sleep(10000);
			
			//Verify the legends on Chart
			String legendArray[]={"PAMTest_Capriata/Saiwa kW",testData.getProperty("PAMTest_CapriataSaiwaGJ"),testData.getProperty("PAMTest_CapriataSaiwam3"),testData.getProperty("PAMTest_CapriataSaiwam3")};
			List<String> legendArr=Arrays.asList(legendArray);
			String legendPath,actualLegendName;
			//Verify the legends on the chart
			for(int i=0;i<legendArr.size();i++) {
				legendPath = "//gridster[1]/gridster-item[1]/ra-widget-container[1]/div[1]/div[2]/div[2]/div[1]/pam-load-profile-widget[1]//*[contains(@class,'highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-"+i+"')]/span";
				actualLegendName = d.findElement(By.xpath(legendPath)).getText().replaceAll("\\n", " ");
				Assert.assertTrue(legendArr.contains(actualLegendName));
			}
			Utility.deleteAllWidgets();
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}	
	}
}
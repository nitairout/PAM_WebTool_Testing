package regression.ModelingEngine;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies baseline loading time on the PAM chart meets the performance requirements 
 * when using the maximum number of change points for the baseline, 
 * if it doesn't meet the requirements test case will fail and tells how much more time it took than exapected.
 */
public class QIDM_238_BaselinePerformanceTest extends TestBase{
	LoginTC login = null;
	String baselineName = "Performance with Temperature and 10 change points";
	String PAMTest_FornoLinea1_measurementPath = "//table/tbody/tr[4]/td[3]/child::span";
	StopWatch stopwatch = null;
	int actualTime = 0;
	int expectedTime = 0;
	
	@Test
	public void baselinePerformanceTest() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			
			// Search for site name 'PAMTest_Forno Linea 1'
			searchSiteInLocationList("PAMTest_Forno Linea 1");
			getWebElementXpath_D(PAMTest_FornoLinea1_measurementPath).click();

			refreshToLoadTheChart();

			// Click on Manage Measurement link from Legend Icon
			getWebElementXpath("LegendMarker").click();
			Thread.sleep(2000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);

			// Click on Baseline Tab
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(1000);
			
			// verify same baseline exists, if yes select the baseline radio button
			getWebElementXpath_D("//td[.='"+baselineName+"']/preceding-sibling::td//child::input").click();
			Thread.sleep(5000);
			// Save & Close the baseline popup
			getWebElementActionXpath("Baseline_SaveAndClose").click();
			Thread.sleep(10000);
			
			//Added the Fixed date "12/01/2022"- "11/30/2023"
			addFixedDateRange("12/01/2022","11/30/2023");
			
			//Select the time interval - By hour
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By hour");
			printLog("Changed the interval to hour in PAM.");
			//aJaxWait();
			expectedTime = 35;//In Seconds
			
		    stopwatch = new StopWatch();
			stopwatch.start();
			
			new WebDriverWait(d, Duration.ofSeconds(400)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.getProperty("ColumnLegendOne"))));
			Assert.assertTrue(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " ").contains("PAMTest_Capriata/Saiwa \\...\\ PAMTest_Forno Linea 1 kWh"));
			stopwatch.stop();
			
			actualTime = (int) stopwatch.getTime(TimeUnit.SECONDS);	
			printLog("actualTime..."+actualTime);
			printLog("expectedTime..."+expectedTime);
			Assert.assertTrue(actualTime <= expectedTime);
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
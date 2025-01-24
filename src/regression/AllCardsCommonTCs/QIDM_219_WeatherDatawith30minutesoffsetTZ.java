package regression.AllCardsCommonTCs;

import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies temperature data is displayed as expected for the weather data which has 30 minutes off set time zone. 
 * for example : (UTC+05:30) Chennai, Kolkata, Mumbai, New Delhi
 */
public class QIDM_219_WeatherDatawith30minutesoffsetTZ extends TestBase {
	LoginTC login = null;
	
	@Test
	public void weatherDatawith30minutesoffsetTZ() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Searched with site 'PAMTest_Malanpur'
			searchSiteInLocationList("PAMTest_Malanpur");
			Utility.selectSingleMeasurement("Weather","Temperature",standard);
			//Click on the Weather Temperature Measurement
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Malanpur']/following-sibling::td[7]/child::span").click();
			
			refreshToLoadTheChart();
			
			//Selected data tab(table)
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			String dateRange;
			//1st 4 rows of table data verification
			for(int i=1;i<=4;i++) {
				dateRange = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/th").getText();
				Assert.assertTrue(dateRange.contains(i+":00 AM"));
			}
			
			// Navigate to Trend Analysis card using C2C
			card2Card("Trend Analysis");
			//Selected data tab(table)
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			LocalDate today = LocalDate.now();
			
			//Verified table data for first 4 rows
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText(),changeTheDateFormat("M/d/yyyy", today.minusDays(6)));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[2]/th").getText(),changeTheDateFormat("M/d/yyyy", today.minusDays(5)));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[3]/th").getText(),changeTheDateFormat("M/d/yyyy", today.minusDays(4)));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[4]/th").getText(),changeTheDateFormat("M/d/yyyy", today.minusDays(3)));
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
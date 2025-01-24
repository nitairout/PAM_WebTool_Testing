package regression.LoadProfileAnalysisCard;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies functionality of the slider ,which can be used to increase , 
 * decrease or zoom in the chart date range to analyze the data.
 */
public class QIDM_172_LoadProfileChangingDateRangeWithSlider extends TestBase{
	LoginTC login = null;
	
	@Test
	public void loadProfileChangingDateRangeWithSlider() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Searched with site 'PAMTest_Capriata/Saiwa'
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			//Click on the Electricity Demand Measurement
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			//Chart actual start and end date
			String actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			//String expectedStartAndEndDate = "1/1/2023, 12:00 AM - 2/1/2023, 12:00 AM";
			String expectedStartAndEndDate ="1/1/2023 12:00 AM - 2/1/2023 12:00 AM";
			
			//verify actual date range is as expected date range
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the start and end dates of the chart as fixed date range.");

			//Move the slider at the bottom of the chart to left and verify that date range on the top is changed
			Utility.moveTheElementandClick("(//*[@class='highcharts-scrollbar-arrow'])[1]");
			Thread.sleep(1000);
			
			//Chart actual start and end date
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			//verify actual date range is as expected date range
			//expectedStartAndEndDate = "12/28/2022, 12:00 AM - 1/28/2023, 12:00 AM";
			expectedStartAndEndDate = "12/28/2022 12:00 AM - 1/28/2023 12:00 AM";
			Assert.assertEquals(actualStartEndDateFromChart, expectedStartAndEndDate);
			printLog("Verified the start and end dates for moved the slider at the bottom of the chart tp left side.");
			
			//Scroll to right
			Utility.moveTheElementandClick("(//*[@class='highcharts-scrollbar-arrow'])[2]");
			Thread.sleep(1000);
			
			//Chart actual start and end date
			actualStartEndDateFromChart=getWebElementActionXpath_D("//*[contains(@class,'highcharts-title')]").getText();
			
			//verify actual date range is as expected date range
			//Assert.assertEquals(actualStartEndDateFromChart, "1/1/2023, 12:00 AM - 2/1/2023, 12:00 AM");
			Assert.assertEquals(actualStartEndDateFromChart, "1/1/2023 12:00 AM - 2/1/2023 12:00 AM");
			printLog("Verified the start and end dates for moved the slider at the bottom of the chart tp right side should be fixed date range.");
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

package regression.TrendAnalysisCard;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies Statistics values for Gas and Water default measurements in Trend Analysis card.
 */
public class QIDM_186_TrendAnalysisStatisticsForGasAndWaterCommodities extends TestBase {
	LoginTC login = null;

	@Test
	public void trendAnalysisStatisticsForGasAndWaterCommodities() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Select Gas commodity for a site 'PAMTest_Capriata/Saiwa'
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();

			// Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			//Hide option and location panel
			Utility.hideOptionLocationPanelForCards();
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Total Volume");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementValue").getText(), "527,748");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "m3");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTitle").getText(), "Total Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementValue").getText(), "19,316");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementUnit").getText(), "GJ");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTitle").getText(), "Avg Daily Volume");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementValue").getText(), "17,024");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementUnit").getText(), "m3");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementTitle").getText(), "Avg Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementValue").getText(), "623.1");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementUnit").getText(), "GJ");
			
			Utility.moveTheScrollToTheDown();
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementTitle").getText(), "Max Daily Volume");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementValue").getText(), "25,413");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementUnit").getText(), "m3");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTimeStamp").getText(), "1/17/2023");
			
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementTitle").getText(), "Max Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementValue").getText(), "930.1");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementUnit").getText(), "GJ");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTimeStamp").getText(), "1/17/2023");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementTitle").getText(), "Min Daily Volume");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementValue").getText(), "3,528");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementUnit").getText(), "m3");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTimeStamp").getText(), "1/1/2023");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_EighthElementTitle").getText(), "Min Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_EighthElementValue").getText(), "129.1");
			Assert.assertEquals(getWebElementActionXpath("Statistics_EighthElementUnit").getText(), "GJ");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementTimeStamp").getText(), "1/1/2023");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_NinthElementTitle").getText(), "Raw Data");
			Assert.assertEquals(getWebElementActionXpath("Statistics_NinthElementValue").getText(), "99.97");
			Assert.assertEquals(getWebElementActionXpath("Statistics_NinthElementUnit").getText(), "% Complete");
			
			printLog("Verified the all the Statistics data for 'Gas' commodity");
			//Hide option and location panel
			Utility.showOptionLocationPanelForCards();
			hideEenlargeBottomTabsPanel();
			
			//De-Select the Gas measurement
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();
			
			//Select the Water measurement
			getWebElementXpath("PAMTest_CapriataSaiwa_Water").click();
			refreshToLoadTheChart();
			
			//Hide option and location panel
			Utility.hideOptionLocationPanelForCards();
			Utility.moveTheScrollToTheDown();
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Total Volume");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementValue").getText(), "159,001");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "m3");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTitle").getText(), "Avg Daily Volume");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementValue").getText(), "5,129");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementUnit").getText(), "m3");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTitle").getText(), "Max Daily Volume");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementValue").getText(), "9,793");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementUnit").getText(), "m3");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTimeStamp").getText(), "1/16/2023");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementTitle").getText(), "Min Daily Volume");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementValue").getText(), "160.0");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementUnit").getText(), "m3");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTimeStamp").getText(), "1/6/2023");
			
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementTitle").getText(), "Raw Data");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementValue").getText(), "100.0");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementUnit").getText(), "% Complete");
			printLog("Verified the all the Statistics data for 'Water' commodity");
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
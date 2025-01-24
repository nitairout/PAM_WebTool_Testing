package regression.LoadProfileAnalysisCard;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies default statistic values displayed under statistics tab. Also hides some statistics from 'Show/Hide Statistics' option 
 * and verifies hidden statistics are not displayed under statistics anymore.
 */
public class QIDM_48_LoadProfileStatistics extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileStatistics() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			Utility.selectSingleMeasurement("Electricity","Demand",standard);
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			//Hide option and location panel
			Utility.hideOptionLocationPanelForCards();
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			//Verify the Statistics value
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTitle").getText(), "Max Demand");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementValue").getText(), "1,988");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementUnit").getText(), "kW");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FirstElementTimeStamp").getText(), "1/24/2023 11:45 AM");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementTitle").getText(), "Total Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementValue").getText(), "261,782");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SecondElementUnit").getText(), "kWh");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementTitle").getText(), "Avg Demand");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementValue").getText(), "351.9");
			Assert.assertEquals(getWebElementActionXpath("Statistics_ThirdElementUnit").getText(), "kW");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementTitle").getText(), "Load Factor");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FourthElementValue").getText(), "17.70");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementTitle").getText(), "Avg Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementValue").getText(), "8,445");
			Assert.assertEquals(getWebElementActionXpath("Statistics_FifthElementUnit").getText(), "kWh");
			
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementTitle").getText(), "Max Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementValue").getText(), "25,452");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementUnit").getText(), "kWh");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SixthElementTimeStamp").getText(), "1/24/2023");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementTitle").getText(), "Min Daily Usage");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementValue").getText(), "1,878");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementUnit").getText(), "kWh");
			Assert.assertEquals(getWebElementActionXpath("Statistics_SeventhElementTimeStamp").getText(), "1/20/2023");
			
			Assert.assertEquals(getWebElementActionXpath("Statistics_EighthElementTitle").getText(), "Min Demand");
			Assert.assertEquals(getWebElementActionXpath("Statistics_EighthElementValue").getText(), "32.00");
			Assert.assertEquals(getWebElementActionXpath("Statistics_EighthElementUnit").getText(), "kW");
			Assert.assertEquals(getWebElementActionXpath("Statistics_EighthElementTimeStamp").getText(), "1/27/2023 10:00 AM");
			printLog("Verified the all the Statistics data for 'Demand' measurement");
			
			//Select "Show/Hide Statistics" from kabob menu options
			kabobMenuOptions("Show/Hide Statistics");
			//Remove Max Demabd Nad Total Usage
			getWebElementActionXpath_D("//span[contains(normalize-space(),'Max Demand')]/ancestor::li[1]/descendant::i[contains(@class,'fas fa-trash-alt')]").click();
			Thread.sleep(1000);
			getWebElementActionXpath_D("//span[contains(normalize-space(),'Total Usage')]/ancestor::li[1]/descendant::i[contains(@class,'fas fa-trash-alt')]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//button[normalize-space()='Apply'][contains(@class,'btn-success')]").click();
			Thread.sleep(5000);
			
			Assert.assertNotEquals(getWebElementXpath("Statistics_FirstElementTitle").getText(), "Max Demand");			
			Assert.assertNotEquals(getWebElementXpath("Statistics_SecondElementTitle").getText(), "Total Usage");
			printLog("Verified the Statistics data for 'Demand' measurement after removed 'Max Demand' and 'Total Usage'");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
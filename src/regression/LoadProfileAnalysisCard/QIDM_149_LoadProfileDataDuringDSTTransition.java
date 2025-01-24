package regression.LoadProfileAnalysisCard;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies data during day light saving (DST) transition for a site which is in North America. 
 * It verifies data for Standard Energy measurement , 
 * temperature measurement and calculated measurement.
 * 
 */

public class QIDM_149_LoadProfileDataDuringDSTTransition extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileDataDuringDSTTransition() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Electricity#Energy*Auto Tet DST and Weather*Temperature
			Utility.selectMultipleMeasurements("Electricity#Energy~standard*Auto Test DST~userDefined|Weather#Temperature~standard");
			//PAMTest_Naperville, IL \ PAMTest_Main Meter \ PAMTest_Electric \ PAMTest_Feeder B
			searchSiteInLocationList("PAMTest_Feeder B");
			//selected electric
			getWebElementXpath("PAMTest_FeederB_Energy").click();
			//Selected weather
			getWebElementXpath("PAMTest_FeederB_Weather").click();
			
			//Added the Fixed date
			addFixedDateRange("03/13/2022","03/13/2022");
			
			refreshToLoadTheChart();
			
			//Verify the day light saving message
			Assert.assertEquals(getWebElementXpath_D("//div[@class='warnings']/child::div[contains(@class,'alert')]/span").getText(), "Your selected time range includes a Daylight Saving Time change");
			
			// Closing the day light saving pop up
			if (getWebElementActionXpath("AlertMessage").isDisplayed()) {
				getWebElementActionXpath("AlertMessage").click();
			}
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Naperville, IL \\...\\ PAMTest_Feeder B kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), "PAMTest_Naperville, IL \\...\\ PAMTest_Feeder B kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText().replaceAll("\\n", " "), "PAMTest_Naperville, IL \\...\\ PAMTest_Feeder B \u00B0C");
			printLog("Legend verified for 3 measurementscompleted.");
			
			//Table data verification started
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Naperville, IL \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Feeder B");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderTemperatureC"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Auto Test DST (kWh)");
			printLog("Table header verification completed for starting dst.");
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			String startingTableData[]="3/13/2022 01:45 AM~313.3~~156.7|3/13/2022 03:00 AM~311.3~21.00~155.6".split("\\|");
			String startingRow[]=null;
			for(int i=0;i<startingTableData.length;i++) {
				startingRow=startingTableData[i].split("~");
				Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr["+(i+7)+"]/th").getText(), startingRow[0]);
				Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr["+(i+7)+"]/td[1]").getText(), startingRow[1]);
				Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr["+(i+7)+"]/td[2]").getText(), startingRow[2]);
			}
			printLog("Table data verification completed for starting dst.");
			
			//Hide Enlarge the bottom panel 
			hideEenlargeBottomTabsPanel();
			
			//Added the Fixed date for  DST end 
			addFixedDateRange("11/6/2022","11/6/2022");
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Naperville, IL \\...\\ PAMTest_Feeder B kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), "PAMTest_Naperville, IL \\...\\ PAMTest_Feeder B kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendThree").getText().replaceAll("\\n", " "), "PAMTest_Naperville, IL \\...\\ PAMTest_Feeder B \u00B0C");
			printLog("Legend verified for 3 measurements completed.");
			
			//Table data verification started
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Naperville, IL \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Feeder B");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderTemperatureC"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Auto Test DST (kWh)");
			printLog("Table header verification completed for end dst.");
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			Utility.moveTheScrollToTheTopOnRquest("300");
			
			String endTableData[]="11/6/2022 01:00 AM~659.5~41.00~329.7|11/6/2022 01:15 AM~666.1~~333.1|11/6/2022 01:30 AM~629.8~~314.9|11/6/2022 01:45 AM~600.1~~300.0|11/6/2022 01:00 AM*~653.8~41.00~326.9|11/6/2022 01:15 AM*~688.6~~344.3|11/6/2022 01:30 AM*~699.4~~349.7|11/6/2022 01:45 AM*~676.4~~338.2".split("\\|");
			String endRow[]=null;
			for(int i=0;i<endTableData.length;i++) {
				endRow=endTableData[i].split("~");
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+4)+"]/th").getText(), endRow[0]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+4)+"]/td[1]").getText(), endRow[1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+4)+"]/td[2]").getText(), endRow[2]);
			}
			printLog("Table header verification completed for end dst.");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

package regression.LoadProfileAnalysisCard;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies loading chart and verifying table data for the sites which has different interval lengths.
 */
public class QIDM_184_LoadProfileChartwithMixedIntervalLengths extends TestBase{
	LoginTC login = null;
	
	@Test
	public void loadProfileChartwithMixedIntervalLengths() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Electricity*Energy and Weather*Temperature
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Weather#Temperature~standard");
			
			// Select a site 'PAMTest_Capriata/Saiwa'
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			// Click on Energy measurement
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			// Click on Temperature measurement
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();
			// Select Fixed date range as '01/01/2023 - 01/05/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), "01/05/2023");
			refreshToLoadTheChart();
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwa0C"));
			printLog("Legend verified for 2 measurements are completed.");
			
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderTemperatureC"));
			printLog("Table header verification completed");
			//Table Data verification
			String tableData="1/1/2023 12:15 AM~91.00~|1/1/2023 12:30 AM~88.00~|1/1/2023 12:45 AM~90.00~|1/1/2023 01:00 AM~91.00~13.89";
			verifyTableDataWithExpected(tableData,4,"QIDM_184_LoadProfileChartwithMixedIntervalLengths");
			printLog("Table Data verification completed");
			
			// De-select the Energy measurement for site 'PAMTest_Capriata/Saiwa'
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			// De-select the Temperature measurement for site 'PAMTest_Capriata/Saiwa'
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();
			refreshToLoadTheChart();
			//d.navigate().refresh();
			aJaxWait();
			// Select a site 'QA Site 1 \ QA Test Intervals \ 10 MINS'
			//searchSiteInLocationList("\"QA Site 1\"");
			searchSiteInLocationList("10 MINS");
			// Click on Energy measurement for 10 MINS sub site
			getWebElementXpath_D("//table/tbody/tr[3]/td[3]/child::span").click();
			
			refreshToLoadTheChart();
			
			// Click on Energy measurement for 30 MINS sub site
			searchSiteInLocationList("30 MINS");
			getWebElementXpath_D("//table/tbody/tr[3]/td[3]/child::span").click();
			
			// Select Fixed date range as '01/01/2017 - 01/02/2017'
			addFixedDateRange("01/01/2017", "01/02/2017");
			Utility.moveTheScrollToTheTop();
			refreshToLoadTheChart();
			
			//Legend verification
			ArrayList<String> legends=new ArrayList<String>();
			legends.add("QA Site 1 \\...\\ 10 MINS kWh");
			legends.add("QA Site 1 \\...\\ 30 MINS kWh");
			Assert.assertTrue(legends.contains(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(legends.contains(getWebElementXpath("LineLegendOne").getText().replaceAll("\\n", " ")));
			printLog("Legend verified for 2 measurements are completed.");
			
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ QA Test Intervals \\ 10 MINS");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[3]").getText(), "QA Site 1 \\ QA Test Intervals \\ 30 MINS");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			printLog("Table header verification completed");
			//Table Data verification
			String tableData1="1/1/2017 12:10 AM~31.00~|1/1/2017 12:20 AM~34.00~|1/1/2017 12:30 AM~36.00~63.00|1/1/2017 12:40 AM~30.00~";
			verifyTableDataWithExpected(tableData1,4,"QIDM_184_LoadProfileChartwithMixedIntervalLengths");
			printLog("Table Data verification completed");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
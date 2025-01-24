package regression.ComparisonAnalysisCard;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

/*
 * This test verifies indexing by RA Metrics with different aggregation types to check 
 * if its taking correct index value based on its aggregation type.
 */
public class QIDM_213_ComparisonAnalysisIndexByRAMetricsWithDifferentAggregationTypes extends TestBase{
	LoginTC login = null;
	@Test
	public void comparisonAnalysisIndexByRAMetricsWithDifferentAggregationTypes() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("ComparisonAnalysisCard");
			
			// Search for Site'PAMTest_Capriata/Saiwa' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			//Added the Fixed date
			addFixedDateRange("01/01/2023","06/30/2023");
			refreshToLoadTheChart();
			// Select Site Metrics as 'AutoTestTotalTypeAverage' from IndexBy
			indexBySelectMatrics("AutoTestTotalTypeAverage");
			aJaxWait();
			
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), "AutoTestTotalTypeAverage");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[4]").getText(), "Energy (kWh / NumberOfEmployees)");
			printLog("Table header verification completed.");

			//Table Data verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(), "592.7");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[3]").getText(), "2,424");
			printLog("Verified table value for 'AutoTestTotalTypeAverage'and 'Energy (kwh/NumberOfEmployees)'");
			
			// Select Site Metrics as 'AutoTestTotalTypeMax' from IndexBy
			indexBySelectMatrics("AutoTestTotalTypeMax");
			aJaxWait();
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), "AutoTestTotalTypeMax");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[4]").getText(), "Energy (kWh / kW)");
			printLog("Table header verification completed.");
			
			//Table Data verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(), "83.00");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[3]").getText(), "17,311");
			printLog("Verified table value for 'AutoTestTotalTypeMax'and 'Energy (kWh / kW)'");
			
			// Select Site Metrics as 'AutoTestTotalTypeMin' from IndexBy
			indexBySelectMatrics("AutoTestTotalTypeMin");
			aJaxWait();
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), "AutoTestTotalTypeMin");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[4]").getText(), "Energy (kWh / PF)");
			printLog("Table header verification completed.");
			
			//Table Data verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(), "91.00");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[3]").getText(), "15,789");
			printLog("Verified table value for 'AutoTestTotalTypeMin'and 'Energy (kWh / PF)'");
			
			// Select Site Metrics as 'AutoTestTotalTypeMostRecent(Sq. Ft.)' from IndexBy
			indexBySelectMatrics("AutoTestTotalTypeMostRecent(Sq. Ft.)");
			aJaxWait();
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), "AutoTestTotalTypeMostRecent(Sq. Ft.)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[4]").getText(), "Energy (kWh / Sq. Ft.)");
			printLog("Table header verification completed.");
			
			//Table Data verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(), "3,700");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[3]").getText(), "388.3");
			printLog("Verified table value for 'AutoTestTotalTypeMostRecent(Sq. Ft.)'and 'Energy (kWh / Sq. Ft.)'");
			
			// Select Site Metrics as 'AutoTestTotalTypeNone' from IndexBy
			indexBySelectMatrics("AutoTestTotalTypeNone");
			aJaxWait();
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), "AutoTestTotalTypeNone");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[4]").getText(), "Energy (kWh / test)");
			printLog("Table header verification completed.");
			
			//Table Data verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(), "6,000");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[3]").getText(), "239.5");
			printLog("Verified table value for 'AutoTestTotalTypeNone'and 'Energy (kWh / test)'");
			
			// Select Site Metrics as 'AutoTestTotalTypeSUM' from IndexBy
			indexBySelectMatrics("AutoTestTotalTypeSUM");
			aJaxWait();
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), "AutoTestTotalTypeSUM");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[4]").getText(), "Energy (kWh / Production Units)");
			printLog("Table header verification completed.");
			
			//Table Data verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(), "395.0");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[3]").getText(), "3,637");//"3,638"
			printLog("Verified table value for 'AutoTestTotalTypeSUM'and 'Energy (kWh / Production Units)'");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
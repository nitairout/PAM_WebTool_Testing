package regression.TrendAnalysisCard;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 *This test verifies all data breakdown by Submeters in  Trend analysis card.  
 *Verifies chart, legend , statistics and table data.
.*/

public class QIDM_63_TrendAnalysisDataBreakdownBySubmeters extends TestBase{
	LoginTC login = null;
	
	@Test
	public void trendAnalysisDataBreakdownBySubmeters() throws Throwable {
		try {
			//Login into RA application with Internal user
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Trend Analysis Card page
			goToPAMCard("TrendAnalysisCard");
			
			//Searched with site name as 'PAMTest_Capriata/Saiwa' from Locations panel 
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			//Selected the electricity measurement for site
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			//Added fixed date range of '01/01/2022 - 01/31/2022'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			// Select the 'Submeters' from DataBreakdown
			dataBreakDownSubMeter("Submeters");
			printLog("Selected Submeters from DataBreakdown");
			
			//Refreshed the chart
			refreshToLoadTheChart();
			Thread.sleep(5000);
			//Verified the Legends from chart
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_EnergyBalancekWh"));
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			printLog("Verified legends from chart!!");
			
			//Hide option and location panel
			hideOptionLocationPanel();
			//hideEenlargeBottomTabsPanel();
			
			//Verify 1st 3 statistics value
			Utility.moveTheScrollToTheDown();
			String statsLabels[]= {"Total Usage","Total Usage PAMTest_Energy Balance","Total Usage PAMTest_Main Meter"};
			String statsValue[]= {"523,564","261,782","261,782"};
			for(int i=1;i<4;i++) {
				Assert.assertEquals(statsLabels[i-1], getWebElementActionXpath_D("(//pam-statistic//*[@class='se-statistic-title'])["+i+"]").getText().replaceAll("\\n", " "));
				Assert.assertEquals(statsValue[i-1], getWebElementActionXpath_D("(//pam-statistic//*[@class='se-statistic-value'])["+i+"]").getText());
			}
			printLog("Verification of statistics tab is completed!!");
			
			//Table headers verification
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), "PAMTest_Energy Balance");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[3]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[3]/th[2]/span[2]").getText(), "%(kWh)");
			printLog("Verified PAMTest_Energy Balance table headers");
			
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("PAMTest_Main_Meter"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[3]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[3]/th[2]/span[2]").getText(), "%(kWh)");
			printLog("Verified PAMTest_Main Meter table headers");
			
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Total");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[3]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			printLog("Verified Total table headers");
			
			//1st 4 rows of table data verification
			String expectedSubMeterTableData="1/1/2023~9,459~50.00~9,459~50.00~18,918|1/2/2023~11,854~50.00~11,854~50.00~23,708|1/3/2023~3,847~50.00~3,847~50.00~7,694|1/4/2023~2,246~50.00~2,246~50.00~4,492";
			verifyTableDataWithExpected(expectedSubMeterTableData,4,"QIDM_63_TrendAnalysisDataBreakdownOptions");
			
			//Logout from the application
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
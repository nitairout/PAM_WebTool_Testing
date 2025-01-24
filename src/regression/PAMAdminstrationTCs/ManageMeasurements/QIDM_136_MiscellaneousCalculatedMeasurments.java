package regression.PAMAdminstrationTCs.ManageMeasurements;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 *This test verifies various calculated measurements to test following features
 *Verifies how missing data is displayed for a calculated measurement
 *Verifies measurement 'PAMAutoTest-Multiple Measurments' where expression refers to another calculated measurement
 *Verifies measurement 'PAMAutoTest-Constant' where this measurement has constant number as an expression
 */
public class QIDM_136_MiscellaneousCalculatedMeasurments extends TestBase {
	LoginTC login = null;
	
	@Test
	public void miscellaneousCalculatedMeasurments() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");

			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();		
			
			// Select the Fixed date range of '5/5/2021' - '5/15/2021'
			addFixedDateRange("5/5/2021", "5/15/2021");
			
			refreshToLoadTheChart();
		
			String legendValue=getWebElementXpath("ColumnLegendOne").getText();
			Assert.assertEquals(legendValue, testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Legend verification completed ");
			
			//Table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table verification started
			String tableDateArr[]={"5/7/2021","5/8/2021","5/9/2021","5/10/2021","5/11/2021","5/12/2021"};
			for(int i=0;i<tableDateArr.length;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+3)+"]/th").getText(), tableDateArr[i]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+3)+"]/td").getText(), "");
			}
			Utility.moveTheScrollToTheTop();
			
			Utility.selectMultipleMeasurements("Electricity#PAMAutoTest-Multiple Measurments~userDefined|Other#PAMAutoTest-Constant~userDefined");
			
			// Select Electric and Gas commodity for a site PAMTest_Main Meter.
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			getWebElementXpath("PAMTest_MainMeter_Energy").click();
			getWebElementXpath("PAMTest_MainMeter_Other").click();
			refreshToLoadTheChart();
			
			//Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			String legendOne=getWebElementActionXpath("LineLegendOne").getText().replaceAll("\\n", " ");
			String legendTwo=getWebElementActionXpath("LineLegendTwo").getText().replaceAll("\\n", " ");
			
			Assert.assertEquals(legendOne, testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeter"));
			Assert.assertEquals(legendTwo, testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeter"));
			printLog("Verified legends for 2 measurement");

			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), "PAMAutoTest-Multiple Measurments ()");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), "PAMAutoTest-Constant ()");
			printLog("Verified table header for 2 measurement");
			
			String tableDataVerifocation="1/1/2023~94.59~100.0|1/2/2023~118.5~100.0|1/3/2023~38.47~100.0";
			verifyTableDataWithExpected(tableDataVerifocation, 3, "QIDM_136_MiscellaneousCalculatedMeasurments");
			printLog("Verified table data for 3 rows");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

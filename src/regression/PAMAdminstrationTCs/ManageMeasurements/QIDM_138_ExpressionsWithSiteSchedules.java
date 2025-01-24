package regression.PAMAdminstrationTCs.ManageMeasurements;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 *This test verifies measurements having expressions referring to different types of 
 *site schedules like client template and site template etc.
 *For example both measurements PAMAutoTest-SiteSchedule and Auto Test -DefaultExpressionWithClientTemplate has an 
 *expression where it defines the values based on the site schedule periods.
 *
 */
public class QIDM_138_ExpressionsWithSiteSchedules extends TestBase {
	LoginTC login = null;
	
	@Test
	public void expressionsWithSiteSchedules() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");

			Utility.selectSingleMeasurement("Electricity","PAMAutoTest-SiteSchedule",userDefined);
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			getWebElementXpath("PAMTest_MainMeter_Energy").click();	
			
			// Select the Fixed date range of '1/2/2023' - '1/3/2023'
			addFixedDateRange("1/2/2023", "1/3/2023");
			
			refreshToLoadTheChart();
		
			String legendValue=getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legendValue, "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			printLog("Legend verification completed ");
			
			//Click on the Table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table verification started from 1 AM to 8 AM
			String tableDateArr1amTO8am[]={"1/2/2023 01:00 AM","1/2/2023 02:00 AM","1/2/2023 03:00 AM","1/2/2023 04:00 AM","1/2/2023 05:00 AM","1/2/2023 06:00 AM","1/2/2023 07:00 AM","1/2/2023 08:00 AM"};
			for(int i=0;i<tableDateArr1amTO8am.length;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+1)+"]/th").getText(), tableDateArr1amTO8am[i]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+1)+"]/td").getText(), "2.000");
			}
			printLog("Verified table data for 1 AM to 8 AM");
			
			//Table verification started from 9 AM to 5 PM
			int thisTableRowSize=tableDateArr1amTO8am.length;
			String tableDateArr9amTO5pm[]={"1/2/2023 09:00 AM","1/2/2023 10:00 AM","1/2/2023 11:00 AM","1/2/2023 12:00 PM","1/2/2023 01:00 PM","1/2/2023 02:00 PM","1/2/2023 03:00 PM","1/2/2023 04:00 PM","1/2/2023 05:00 PM"};
			for(int i=1;i<=tableDateArr9amTO5pm.length;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+thisTableRowSize)+"]/th").getText(), tableDateArr9amTO5pm[i-1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+thisTableRowSize)+"]/td").getText(), "1.000");
			}
			printLog("Verified table data for 9 AM to 5 PM");
			
			//Table verification started from 6 PM to 11 PM
			thisTableRowSize=thisTableRowSize+tableDateArr9amTO5pm.length;
			String tableDateArr6pmTO11pm[]={"1/2/2023 06:00 PM","1/2/2023 07:00 PM","1/2/2023 08:00 PM","1/2/2023 09:00 PM","1/2/2023 10:00 PM","1/2/2023 11:00 PM"};
			for(int i=1;i<=tableDateArr6pmTO11pm.length;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+thisTableRowSize)+"]/th").getText(), tableDateArr6pmTO11pm[i-1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+thisTableRowSize)+"]/td").getText(), "2.000");
			}
			printLog("Verified table data for 6 pm to 11 pm");
			Utility.moveTheScrollToTheTop();
			
			Utility.selectSingleMeasurement("Electricity","Auto Test -DefaultExpressionWithClientTemplate",userDefined);
			aJaxWait();
			
			//Table verification started from 1 AM to 9 AM
			String tableDateArr1amTO9am[]={"1/2/2023 01:00 AM","1/2/2023 02:00 AM","1/2/2023 03:00 AM","1/2/2023 04:00 AM","1/2/2023 05:00 AM","1/2/2023 06:00 AM","1/2/2023 07:00 AM","1/2/2023 08:00 AM","1/2/2023 09:00 AM"};
			for(int i=0;i<tableDateArr1amTO8am.length;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+1)+"]/th").getText(), tableDateArr1amTO8am[i]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+1)+"]/td").getText(), "0");
			}
			printLog("Verified table data for 1 AM to 9 AM");
			
			//Table verification started from 10 AM to 5 PM
			int thisTableRowSizeDefault=tableDateArr1amTO9am.length;
			String tableDateArr10amTO5pm[]={"1/2/2023 10:00 AM","1/2/2023 11:00 AM","1/2/2023 12:00 PM","1/2/2023 01:00 PM","1/2/2023 02:00 PM","1/2/2023 03:00 PM","1/2/2023 04:00 PM","1/2/2023 05:00 PM"};
			for(int i=1;i<=tableDateArr10amTO5pm.length;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+thisTableRowSizeDefault)+"]/th").getText(), tableDateArr10amTO5pm[i-1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+thisTableRowSizeDefault)+"]/td").getText(), "1.000");
			}
			printLog("Verified table data for 10 AM to 5 PM");
			
			//Table verification started from 6 PM to 11 PM
			thisTableRowSizeDefault=thisTableRowSizeDefault+tableDateArr10amTO5pm.length;
			String defaultExpTableDateArr6pmTO11pm[]={"1/2/2023 06:00 PM","1/2/2023 07:00 PM","1/2/2023 08:00 PM","1/2/2023 09:00 PM","1/2/2023 10:00 PM","1/2/2023 11:00 PM"};
			for(int i=1;i<=defaultExpTableDateArr6pmTO11pm.length;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+thisTableRowSizeDefault)+"]/th").getText(), defaultExpTableDateArr6pmTO11pm[i-1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+thisTableRowSizeDefault)+"]/td").getText(), "0");
			}
			printLog("Verified table data for 6 PM to 11 PM");

			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
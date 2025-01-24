package regression.ModelingEngine;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

/*
 * This test verifies common analysis CUSUM reports for modeling
 */
public class QIDM_242_CUSUMReports extends TestBase{
	LoginTC login = null;
	
	@Test
	public void cusmurReports() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			
			//Click on Common Analyses tab
			getWebElementActionXpath_D("//li/a[contains(text(),'Common Analyses')]").click();
			aJaxWait();
			Thread.sleep(5000);
			
			String[] reports = {"Electricity Energy", "Gas Volume", "Gas Energy", "Water Volume"};
			
			String[] expectedElectricEnergyreport = {"Energy (kWh)","Energy Baseline (kWh)","Energy Baseline vs Actual (kWh)","Energy Baseline vs Actual CUSUM (kWh)"};
			String[] expectedGasVolumereport = {"Volume (m3)","Volume Baseline (m3)","Volume Baseline vs Actual (m3)","Volume Baseline vs Actual CUSUM (m3)"};
			String[] expectedGasEnergyreport = {"Energy (GJ)","Energy Baseline (GJ)","Energy Baseline vs Actual (GJ)","Energy Baseline vs Actual CUSUM (GJ)"};
			String[] expectedWaterVolumereport = {"Volume (m3)","Volume Baseline (m3)","Volume Baseline vs Actual (m3)","Volume Baseline vs Actual CUSUM (m3)"};
			String reportPath = null;
			String[] expectedReport = null;

			for(int i=0;i<reports.length;i++) {
				reportPath = "//div[@class='se-common-analyses-container']//li/a[contains(text(),'"+reports[i]+"')]";
				
				getWebElementActionXpath_D(reportPath).click();
				
				printLog("Click on the report - "+reports[i]);
			
				ArrayList<String> tab = new ArrayList<String>(d.getWindowHandles());
				d.switchTo().window(tab.get(1));
				waitForPageLoad();
				aJaxWait();
				Thread.sleep(5000);
				
				if(reports[i].equalsIgnoreCase("Electricity Energy")) {
					expectedReport = expectedElectricEnergyreport;					
					// UnSelected Energy measurement for site 'Automation_TestSite1'
					getWebElementActionXpath_D("//td[normalize-space()='Automation_TestSite1']/following-sibling::td[2]/child::span").click();	
					refreshToLoadTheChart();
					// Searched with sitename 'PAMTest_Capriata/Saiwa'
					searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));					
					// Selected Energy measurement for site 'PAMTest_Capriata/Saiwa'
					getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
					refreshToLoadTheChart();
					Thread.sleep(5000);
				}else if(reports[i].equalsIgnoreCase("Gas Volume")) {
					expectedReport = expectedGasVolumereport;
				}else if(reports[i].equalsIgnoreCase("Gas Energy")) {
					expectedReport = expectedGasEnergyreport;
				}else if(reports[i].equalsIgnoreCase("Water Volume")) {
					expectedReport = expectedWaterVolumereport;
				}
				
				//Table headers verification
				explicitWait_Xpath("DataTableTab");
				getWebElementActionXpath("DataTableTab").click();
				Thread.sleep(5000);
				
				Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), expectedReport[0]);
				Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), expectedReport[1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), expectedReport[2]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[4]").getText(), expectedReport[3]);
				printLog("Verified table headers of "+reports[i]);
				
				d.close();
				d.switchTo().window(tab.get(0));
			}

			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}

package regression.LoadProfileAnalysisCard;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies auto calculating energy values from demand data and demand 
 * form energy data for the sites where it has only demand or energy data.
 */
public class QIDM_181_LoadProfileAutoCalculatedDemandAndEnergy extends TestBase{
	LoginTC login = null;
	
	@Test
	public void loadProfileAutoCalculatedDemandAndEnergy() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Energy and Demand measurement from Electricity commodity
			Utility.selectMultipleMeasurements("Electricity#Energy~standard*Demand~standard");
			
			String measurementSiteXpath="//table/tbody/tr[3]/td[3]/child::span";
			
			//Searched with site 'QA Site 1 \ QA Test Commodities \ kW-Only'
			searchSiteInLocationList("kW-Only");
			//Click on the Energy & Demand Measurement from Electricity commodity
			getWebElementXpath_D(measurementSiteXpath).click();
			
			//Added the Fixed date
			addFixedDateRange("07/01/2015","07/02/2015");
			
			refreshToLoadTheChart();
			
			// Verify the legend and start & end date from chart
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 \\...\\ kW-Only kW");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), "QA Site 1 \\...\\ kW-Only kWh");
			printLog("Charts verification successfully for kW-Only site");
			
			//Selected Data tab(table)
			getWebElementXpath("DataTableTab").click();
			
			//Verified the 4 rows data from the table
			String tableDataForkWOnlySite = "7/1/2015 12:15 AM~95.00~23.75|7/1/2015 12:30 AM~96.00~24.00|7/1/2015 12:45 AM~99.00~24.75|7/1/2015 01:00 AM~96.00~24.00";
			verifyTableDataWithExpected(tableDataForkWOnlySite, 4, "QIDM_181_LoadProfileAutoCalculatedDemandAndEnergy");
			printLog("Table data verification successfully for kW-Only site");
			
			//De-select the measurement for site 'QA Site 1 \ QA Test Commodities \ kW-Only'
			getWebElementXpath_D(measurementSiteXpath).click();
			refreshToLoadTheChart();
			
			//Searched with site 'QA Site 1 \ QA Test Commodities \ kWh-Only'
			searchSiteInLocationList("kWh-Only");
			//Click on the Energy & Demand Measurement from Electricity commodity
			getWebElementXpath_D(measurementSiteXpath).click();
			
			//Added the Fixed date
			addFixedDateRange("07/01/2017","07/02/2017");
			
			refreshToLoadTheChart();
			// Verify the legends from chart
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "QA Site 1 \\...\\ kWh-Only kW");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText(), "QA Site 1 \\...\\ kWh-Only kWh");
			printLog("Charts verification successfully for kWh-Only site");
			
			//Selected data tab(table)
			getWebElementXpath("DataTableTab").click();
			
			//Verified the 4 rows data from the table
			String tableDataForkWhOnlySite = "7/1/2017 12:15 AM~71.75~17.94|7/1/2017 12:30 AM~67.38~16.85|7/1/2017 12:45 AM~70.03~17.51|7/1/2017 01:00 AM~69.81~17.45";
			verifyTableDataWithExpected(tableDataForkWhOnlySite, 4, "QIDM_181_LoadProfileAutoCalculatedDemandAndEnergy");
			printLog("Table data verification successfully for kWh-Only site");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

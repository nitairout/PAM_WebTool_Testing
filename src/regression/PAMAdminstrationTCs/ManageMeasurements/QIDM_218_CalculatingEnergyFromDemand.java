package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

/*
 * This test verifies auto calculating energy data when there is only demand data coming from the source.
 * It verifies calculating the energy from demand using both new and old expressions.
 * Expression for old way of calculation : ([Electricity:DEMAND][kW] * (IntervalSizeMinutes([Electricity:DEMAND][kW])/60))
 * Expression for new way of calculation: EnergyFromDemand([Electricity:DEMAND])
 */
public class QIDM_218_CalculatingEnergyFromDemand extends TestBase {
	LoginTC login = null;
	
	@Test
	public void calculatingEnergyFromDemand() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Searched with site 'QA Site 1 \ Z_QA Other TestData \ DemandMeterData \ AutoCalculatedEnergy_NewExpression'
			searchSiteInLocationList("AutoCalculatedEnergy_NewExpression");
			//Click on the Electricity Demand Measurement
			getWebElementXpath_D("//table/tbody/tr[4]/td[3]/child::span").click();
			
			refreshToLoadTheChart();
			
			//Searched with site 'QA Site 1 \ Z_QA Other TestData \ DemandMeterData \ AutoCalculatedEnergy_OldExpression'
			searchSiteInLocationList("AutoCalculatedEnergy_OldExpression");
			//Click on the Electricity Demand Measurement
			getWebElementXpath_D("//table/tbody/tr[4]/td[3]/child::span").click();
			
			//Added the Fixed date
			addFixedDateRange("9/1/2020","9/2/2020");
			
			refreshToLoadTheChart();
			
			//Selected data tab(table)
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			String newExpressionValue, oldExpressionValue;
			//1st 4 rows of table data verification
			for(int i=1;i<=4;i++) {
				newExpressionValue = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/td[1]").getText();
				oldExpressionValue = getWebElementActionXpath_D("//*[@id='tablebody']/tr["+i+"]/td[2]").getText();
				Assert.assertEquals(newExpressionValue, oldExpressionValue);
			}
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

package regression.ModelingEngine;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies system generated baseline is available or not for Electric , Gas and Water measurements.
 */
public class QIDM_227_SystemGeneratedBaseline extends TestBase{
	LoginTC login = null;
	String defaultBaseline = "System Generated";
	
	@Test
	public void systemGeneratedBaseline() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Provide site name, measurements under commodity and measurement icon path
			verifySystemGeneratedBaseLineForCommodity("PAMTest_Electric","Electricity#Energy~standard*Energy Baseline~standard","//tbody/tr[.='PAMTest_Capriata/Saiwa']/following-sibling::tr[.='PAMTest_Electric'][1]/td[3]/child::span");						
			verifySystemGeneratedBaseLineForCommodity("PAMTest_Gas","Gas#Energy~standard*Energy Baseline~standard","//tbody/tr[.='PAMTest_Capriata/Saiwa']/following-sibling::tr[.='PAMTest_Gas'][1]/td[4]/child::span");			
			verifySystemGeneratedBaseLineForCommodity("PAMTest_Gas","Gas#Volume~standard*Volume Baseline~standard","//tbody/tr[.='PAMTest_Capriata/Saiwa']/following-sibling::tr[.='PAMTest_Gas'][1]/td[4]/child::span");
			verifySystemGeneratedBaseLineForCommodity("PAMTest_Water","Water#Volume~standard*Volume Baseline~standard","//tbody/tr[.='PAMTest_Capriata/Saiwa']/following-sibling::tr[.='PAMTest_Water'][1]/td[5]/child::span");

			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void verifySystemGeneratedBaseLineForCommodity(String sitename1, String measurements, String measurementIcon) throws Throwable {
		try {
			//Added measurements from commodity
			Utility.selectMultipleMeasurements(measurements);

			//Search for site name
			searchSiteInLocationList(sitename1);
			getWebElementXpath_D(measurementIcon).click();
			
			refreshToLoadTheChart();
			
			//Click on Manage Measurement link from Legend Icon
			getWebElementXpath("LegendMarker").click(); 
			Thread.sleep(2000);
			getWebElementXpath("ManageMeasurementLink").click();
			Thread.sleep(2000);
			
			//Click on Baseline Tab
			getWebElementXpath("BaselineTab").click();
			Thread.sleep(1000);
			
			//verify System Generated Baseline should be there
			int checkSystemGenerateBaseline=d.findElements(By.xpath("//tr[contains(@class,'baseline-item')]/td[contains(.,'System Generated')]")).size();
			if(checkSystemGenerateBaseline==0) {
				Assert.fail("System Generated is not present for "+sitename1);
			}else {
				boolean selectedBaseline=getWebElementXpath_D("//tr[contains(@class,'baseline-item')]/td[contains(.,'System Generated')]/preceding-sibling::td//input").isSelected();
				Assert.assertTrue(selectedBaseline);
			}
			Reporter.log("Verified System Generated baseline is present and the respective radio button is available.");
			
			//Close the baseline popup
			getWebElementActionXpath("Baseline_Close_button").click();
			Thread.sleep(10000);
			
			getWebElementXpath_D(measurementIcon).click();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}

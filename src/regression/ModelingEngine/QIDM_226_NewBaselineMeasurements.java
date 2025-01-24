package regression.ModelingEngine;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies if new measurements created for baselines are available for Energy , Gas and Water commodities under measurement selector.
 */
public class QIDM_226_NewBaselineMeasurements  extends TestBase{
	LoginTC login = null;
	@Test
	public void newBaselineMeasurements() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//table/thead/tr[1]/th//i[contains(@class,'se-icon-electricity-o com-header')]").click();
			Thread.sleep(2000);
			
			//Verify baseline measurements from Electricity commodity
			verifyMeasurement("Electricity*Energy Baseline*Energy Baseline vs Actual*Energy Baseline vs Actual CUSUM");
			//Verify baseline measurements from Gas commodity
			verifyMeasurement("Gas*Energy Baseline*Energy Baseline vs Actual*Energy Baseline vs Actual CUSUM*Volume Baseline*Volume Baseline vs Actual*Volume Baseline vs Actual CUSUM");
			//Verify baseline measurements from Water commodity
			verifyMeasurement("Water*Volume Baseline*Volume Baseline vs Actual*Volume Baseline vs Actual CUSUM");
			
			//Close the measurements popup
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void verifyMeasurement(String measurements) throws Exception{
		try{
			//click on respective commodity
			String commodityAndMeasurement[]=measurements.split("\\*");
			getWebElementActionXpath_D("//li[contains(@id,'k-tabstrip-tab')]//span[contains(text(),'"+commodityAndMeasurement[0]+"')]").click();
			Thread.sleep(2000);
			
			//Checking if both the standard and userdefind=ed measurements are in collapsed state
			int meaurementTypeArr=d.findElements(By.xpath("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]")).size();
			if(meaurementTypeArr>0)
				getWebElementActionXpath_D("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]").click();
		
			//Expand the respective measurement type
			getWebElementActionXpath_D("(//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'expand')])[2]").click();
			Thread.sleep(1000);
					
			for(int k=1;k<commodityAndMeasurement.length;k++) {
				//Click on filter
				getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
				getWebElementXpath_D("//input[@id='commodityModalFilter']").sendKeys(commodityAndMeasurement[k]);
				Thread.sleep(2000);
				//click on measurement filter icon
				getWebElementActionXpath_D("//kendo-grid-filter-menu/a[@title='Measurements Filter Menu']//kendo-svgicon").click();
				Thread.sleep(1000);
				//click on filter drop down
				getWebElementActionXpath_D("(//div[contains(@id,'k-grid') and contains(@id,'filter-menu')]//kendo-dropdownlist)[1]").click();
				Thread.sleep(1000);
				//Select the value from the drop down
				getWebElementXpath_D("//span[contains(.,'Is equal to')]").click();
				Thread.sleep(1000);
				//Enter the measurement name to filter
				getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").clear();
				getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").sendKeys(commodityAndMeasurement[k]);
				Thread.sleep(2000);
				//Click on filter button
				getWebElementActionXpath_D("//button[contains(text(),'Filter')]").click();
				Thread.sleep(2000);
				//click on plus icon to select measurement
				boolean flag = getWebElementActionXpath_D("//i[contains(@class,'fa fa-lg clickable fa-plus-circle')]").isDisplayed();
				Assert.assertTrue(flag);
				Thread.sleep(1000);
				printLog("Verified the measurement "+commodityAndMeasurement[k]+" under commodity as "+commodityAndMeasurement[0]);
				
				/*
				//to remove measurement filter for further use
				getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
				//click on measurement filter icon
				getWebElementActionXpath_D("//kendo-grid-filter-menu/a[@title='Measurements Filter Menu']//kendo-svgicon").click();
				Thread.sleep(500);
				//click on filter drop down
				getWebElementActionXpath_D("(//div[contains(@id,'k-grid') and contains(@id,'filter-menu')]//kendo-dropdownlist)[1]").click();
				Thread.sleep(1000);
				//Select the value from the drop down
				getWebElementActionXpath_D("//span[normalize-space()='Is equal to']").click();
				Thread.sleep(1000);
				//Enter the measurement name to filter
				getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").clear();
				
				getWebElementActionXpath_D("//button[contains(text(),'Filter')]").click();
				Thread.sleep(2000);
			*/
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
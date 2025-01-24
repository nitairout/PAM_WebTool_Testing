package regression.PAMAdminstrationTCs.ManageTags;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*This test verifies enabling the measurements only for the sites which are tagged with certain types of tags.  (i.e called Tag Limited Measurements)*/
public class QIDM_118_TagLimitedMeasurement extends TestBase {
	LoginTC login = null;
	String measurementName = "Auto Tag Limited Measurement";
	String expression = "[Electricity:ENERGY] * 2";

	@Test
	public void tagLimitedMeasurement() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Manage Measurements page under Administration menu tab
			gotoManageMeasurementPage();
			Utility.moveTheScrollToTheTop();
			printLog("Navigate to Manage Measurement page");
			
			//Filter the measurement name with 'Auto Tag Limited Measurement'
			getWebElementXpath("ManageMeasurement_SearchBar").clear();
			getWebElementXpath("ManageMeasurement_SearchBar").sendKeys(measurementName);			
			Thread.sleep(5000);
			
			//Click on the measurement name from results
			getWebElementActionXpath_D("//div[@class='well clickable']/span[1]").click();
			Thread.sleep(2000);
			//Click on Advanced Tab
			getWebElementXpath_D("//span[normalize-space()='Advanced']").click();
			Thread.sleep(2000);
			
			//Select check box for 'Make this measurement available only to location with the following tags'
			if(!getWebElementXpath_D("//div[@id='advancedTab']//input[@type='checkbox']").isSelected()) {
				getWebElementXpath_D("//div[@id='advancedTab']//input[@type='checkbox']").click();
			}
			Thread.sleep(2000);
			
			//Click on the tags field, and select tag 'Autotag: Autotag1' from the list
			getWebElementXpath_D("//*[@id='advancedTab']//kendo-multiselect//input[@class='k-input-inner']").click();
			Thread.sleep(2000);		
			
			getWebElementXpath_D("//li[.='Autotag :  Autotag1']").click();
			
			//Click on Save button
			getWebElementXpath("EditExpression_Save").click();
			Thread.sleep(5000);
			
			//Click on Default Expression tab
			getWebElementXpath_D("//span[contains(text(),'Default Expression')]").click();
			Thread.sleep(5000);
			
			//Select check box for 'Enable Default Expression'
			if(!getWebElementXpath_D("//div[@id='defaultExpressionTab']//input[@id='enableDefault']").isSelected()) {
			getWebElementActionXpath_D("//div[@id='defaultExpressionTab']//input[@id='enableDefault']").click();
			}
			Thread.sleep(5000);
			//}
			//Provide the expression
			getWebElementXpath_D("//textarea[@id='DefaultExpression']").clear();
			getWebElementXpath_D("//textarea[@id='DefaultExpression']").sendKeys(expression);
			
			//Click on the Validate button
			getWebElementXpath_D("//button[normalize-space()='Validate']").click();
			
			//Click on Save & Close button
			getWebElementXpath("EditExpression_SaveAndClose").click();
			Thread.sleep(5000);
			
			//Navigate to PAM page
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			Utility.selectSingleMeasurement("Electricity", measurementName,userDefined);
			
			//Search with the sitename 'PAMTest_Shadow 7650' from Location panel
			searchSiteInLocationList("PAMTest_Shadow 7650");
			
			//Verify the sitename should be 'PAMTest_Shadow 7650' and it should be enable for the measurement
			Assert.assertEquals(getWebElementXpath_D("//table/tbody/tr[4]/td[1]").getText(), "PAMTest_Shadow 7650");
			
			//Go to measurement and add 'Energy'
			//Clicked on electricity measurement
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//table/thead/tr[1]/th//i[contains(@class,'se-icon-electricity-o com-header')]").click();
			Thread.sleep(2000);
			
			getWebElementActionXpath_D("//li[contains(@id,'k-tabstrip-tab')]//span[contains(text(),'Electricity')]").click();
			Thread.sleep(2000);
			
			//Checking if both the standard and userdefind=ed measurements are in collapsed state
			int meaurementTypeArr=d.findElements(By.xpath("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]")).size();
			if(meaurementTypeArr>0)
				getWebElementActionXpath_D("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]").click();
		
			//Expand the standard measurement type
			getWebElementActionXpath_D("(//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'expand')])[2]").click();
			Thread.sleep(1000);
			//Click on filter
			getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
			getWebElementXpath_D("//input[@id='commodityModalFilter']").sendKeys("Energy");
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
			getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").sendKeys("Energy");
			Thread.sleep(2000);
			//Click on filter button
			getWebElementActionXpath_D("//button[contains(text(),'Filter')]").click();
			Thread.sleep(2000);
			
			//click on plus icon to select measurement
			getWebElementActionXpath_D("//i[contains(@class,'fa fa-lg clickable fa-plus-circle')]").click();
			Thread.sleep(1000);
			//save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);
			printLog("Selected commodity as Energy");
			
			getWebElementActionXpath_D("//table/tbody/tr[4]/td[3]/child::span").click();
			Thread.sleep(5000);
			
			// Select the Fixed date range of '1/1/2023' - '1/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			//Verify the chart legends on the chart
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");

			//Click on the Table Data tab
			Utility.moveTheScrollToTheDown();
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//verify data is displayed for for both measurements and check 'Auto Tag Limited Measurement ' value is two times of Energy
			Assert.assertEquals(getWebElementXpath_D("//pam-table//thead/tr[2]/th[1]").getText().trim(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//pam-table//thead/tr[2]/th[2]").getText().trim(), "Auto Tag Limited Measurement (kWh)");
			Assert.assertEquals(getWebElementXpath_D("//pam-table//tbody/tr[1]/td[1]").getText(), "9,459");
			Assert.assertEquals(getWebElementXpath_D("//pam-table//tbody/tr[1]/td[2]").getText(), "18,918");
			
			//Navigate to Manage Measurements page under Administration menu tab
			gotoManageMeasurementPage();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			printLog("Navigate to Manage Measurement page");
			
			//Filter with measurement name 'Auto Tag Limited Measurement'
			getWebElementXpath("ManageMeasurement_SearchBar").clear();
			getWebElementXpath("ManageMeasurement_SearchBar").sendKeys(measurementName);			
			Thread.sleep(2000);
			getWebElementActionXpath_D("//div[@class='well clickable']/span[1]").click();
			Thread.sleep(2000);
			//Click on Advanced Tab
			getWebElementXpath_D("//span[normalize-space()='Advanced']").click();
			Thread.sleep(2000);
			
			//Remove the existing tags
			getWebElementXpath_D("//*[@id='advancedTab']//kendo-multiselect//input[@class='k-input-inner']").click();
			Thread.sleep(2000);	
			d.switchTo().activeElement().sendKeys(Keys.BACK_SPACE);
			Thread.sleep(500);
			Thread.sleep(2000);
			
			//unSelect check box for 'Make this measurement available only to location with the following tags'
			getWebElementXpath_D("//div[@id='advancedTab']//input[@type='checkbox']").click();
			Thread.sleep(2000);
			
			getWebElementXpath("EditExpression_Save").click();
			Thread.sleep(5000);
			
			//Click on Default Expression tab
			getWebElementXpath_D("//span[contains(text(),'Default Expression')]").click();
			Thread.sleep(2000);
			
			//Cleared the expression
			getWebElementXpath_D("//textarea[@id='DefaultExpression']").clear();
			
			//De-Select check box for 'Enable Default Expression'
			getWebElementXpath_D("//div[@id='defaultExpressionTab']//input[@id='enableDefault']").click();
			
			//Click on Save & Close button
			getWebElementActionXpath_D("//div[@class='modal-footer']/button[@class='btn btn-success pull-right']").click();
			Thread.sleep(5000);
			
			login.logout();
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

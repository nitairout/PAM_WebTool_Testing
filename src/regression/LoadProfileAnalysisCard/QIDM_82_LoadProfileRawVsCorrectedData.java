package regression.LoadProfileAnalysisCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test compares the data in raw Vs corrected mode and verifies 
 * values are corrected and data quality status flags are updated in corrected mode.
 */

public class QIDM_82_LoadProfileRawVsCorrectedData extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileRawVsCorrectedData() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			//Selecting PAMTest_Shadow 7650
			searchSiteInLocationList("PAMTest_Shadow 7650");
			getWebElementXpath("PAMTest_Shadow7650_Energy").click();
			
			//Added the Fixed date
			addFixedDateRange("02/11/2022","02/11/2022");
			
			refreshToLoadTheChart();
			
			//Verify if the raw mode is by default
			if(!isElementPresent("ChangeTOCorrectedMode")){
				throw new Exception("Raw mode is not selected by default!");
			}
			Utility.moveTheScrollToTheDown();
			//Expand overlay
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			//Select Data quality flag checkbox
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			Thread.sleep(1000);
			printLog("Data quality flag checked.");
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");
			printLog("Legend verified for Raw mode.");
			
			//Table data verification started
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Shadow 7650");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for raw mode.");
			
			//Moving the cursor position to the table data where we have the test data to verify
			Utility.moveTheElement("//table[@id='maintable']//tr[32]");
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			String rawmodeTableData[]="2/11/2022 08:15 AM~~Unknown|2/11/2022 08:30 AM~~Unknown|2/11/2022 08:45 AM~~Unknown|2/11/2022 09:00 AM~~Unknown|2/11/2022 09:15 AM~471.0~Suspect+Note".split("\\|");
			String rowWise[]=null;
			for(int i=0;i<5;i++) {
				rowWise=rawmodeTableData[i].split("~", -1);
				output=getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+14)+"]/th").getText();
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+14)+"]/th").getText(), rowWise[0]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+14)+"]/td[1]").getText(), rowWise[1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+14)+"]/td[2]").getText(), rowWise[2]);
			}
			//Hide Enlarge the bottom panel 
			hideEenlargeBottomTabsPanel();
			
			// Selected corrected mode
			getWebElementActionXpath("ChangeTOCorrectedMode").click();
			aJaxWait();
			printLog("Selected Corrected mode and load the chart.");
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");
			printLog("Legend verified for Corrected mode.");
			
			//"i" icon tool tip verification
			
			String path ="//div[contains(@class,'info-tooltip-chart')]/a/i";
			Utility.moveTheElement(path);
			WebElement icon = d.findElement(By.xpath("//div[contains(@class,'info-tooltip-chart')]/a"));
			String iconToolTip=icon.getAttribute("title").replaceAll("<b>","").replaceAll("</b>","");
			Assert.assertEquals(iconToolTip, "This chart contains 5 estimated values.");
			printLog("Verified the flag icon tool tip for corrected mode.");
			
			/*WebElement icon = d.findElement(By.xpath(locators.getProperty("DataQualityChart_i_IconOnCorrectMode")));
			Utility.moveTheElement("//i[@class='fa fa-info-circle']");
			String iconToolTip=getWebElementXpath("DataQualityChart_i_IconOnCorrectMode").getAttribute("data-bs-original-title").replaceAll("<b>5</b>", "5");
			Assert.assertEquals(iconToolTip, "This chart contains 5 estimated values.");
			printLog("Verified the flag icon tool tip for corrected mode.");*/
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Shadow 7650");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for corrected mode.");
			
			
			
			//Moving the cursor position to the table data where we have the test data to verify
			Utility.moveTheElement("//table[@id='maintable']//tr[32]");
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			//*[@id="tablebody"]/tr[33]/th
			String correctmodeTableData[]="2/11/2022 08:15 AM~87.67~Valid+Note+Estimate|2/11/2022 08:30 AM~90.93~Valid+Note+Estimate|2/11/2022 08:45 AM~82.77~Valid+Note+Estimate|2/11/2022 09:00 AM~100.7~Valid+Note+Estimate|2/11/2022 09:15 AM~108.9~Valid+Note+Estimate".split("\\|");
			String correctRowWise[]=null;
			for(int i=0;i<5;i++) {
				correctRowWise=correctmodeTableData[i].split("~");
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+14)+"]/th").getText(), correctRowWise[0]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+14)+"]/td[1]").getText(), correctRowWise[1]);
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+(i+14)+"]/td[2]").getText(), correctRowWise[2]);
			}
					
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
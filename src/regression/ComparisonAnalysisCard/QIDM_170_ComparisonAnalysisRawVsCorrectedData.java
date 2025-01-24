package regression.ComparisonAnalysisCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test compares the data in raw Vs corrected mode and verifies values are 
 * corrected and data quality status flags are updated in corrected mode.
 * 
 */

public class QIDM_170_ComparisonAnalysisRawVsCorrectedData extends TestBase{
	LoginTC login = null;
	@Test
	public void comparisonAnalysisRawVsCorrectedData() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("ComparisonAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			searchSiteInLocationList("PAMTest_Shadow 7650");
			getWebElementXpath("PAMTest_Shadow7650_Energy").click();
			
			//Added the Fixed date
			addFixedDateRange("10/01/2022","10/31/2022");
			
			//Expand overlay
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			//Data quality flag on
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			Thread.sleep(1000);
			printLog("Data quality flag checked.");
			
			refreshToLoadTheChart();
			
			//Verify if the raw mode is by default
			if(!isElementPresent("ChangeTOCorrectedMode")){
				throw new Exception("Raw mode is not selected by default!");
			}
						
			//Legend verification
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 Energy kWh");
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");
			printLog("Legend verified for Raw mode.");
			
			//Table data verification started
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[2]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for raw mode.");

			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Shadow 7650");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[1]").getText(), "216,173");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(), "Suspect");

			// Selected corrected mode
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath("ChangeTOCorrectedMode").click();
			aJaxWait();
			printLog("Selected Corrected mode and load the chart.");
			
			//Legend verification
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 Energy kWh");
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText().replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Shadow 7650 kWh");
			printLog("Legend verified for Corrected mode.");
			
			//"i" icon tool tip verification
			String path ="//div[contains(@class,'info-tooltip-chart')]/a/i";
			Utility.moveTheElement(path);
			WebElement icon = d.findElement(By.xpath("//div[contains(@class,'info-tooltip-chart')]/a"));
			String iconToolTip=icon.getAttribute("title").replaceAll("<b>","").replaceAll("</b>","");
			Assert.assertEquals(iconToolTip, "This chart contains one or more estimated intervals. For more detailed information please view this data in the Load Profile card.");
			printLog("Verified the flag icon tool tip for corrected mode.");
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[2]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr/th[3]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for Corrected mode.");

			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Shadow 7650");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[1]").getText(), "216,173");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(), "Suspect+Estimate");

			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
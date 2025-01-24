package regression.DataQualityScenarios;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies if the data is corrected as expected for a site when there is a 'Rollover' (when there is a "Permanent" drop)
 * */
public class QIDM_167_RollOver extends TestBase {
	LoginTC login = null;
	@Test
	public void rollOver() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			
			// Navigate to LoadProfileAnalysisCard
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Added Energy & Energy Cumulative measurement from Electricity commodity
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Electricity#Energy Cumulative~standard");

			//Select Electric commodity for a site Rollover
			searchSiteInLocationList("Rollover");
			getWebElementXpath("DQElectricitySiteAfterFilter").click();
			Reporter.log("Searched for 'Rollover' site");

			// Select the Fixed date range of July052019 and July062019
			addFixedDateRange(testData.getProperty("July052019"), testData.getProperty("July062019"));
			
			//Expand overlay
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			//Data quality flag on
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			Thread.sleep(1000);
			printLog("Data quality flag checked.");
			
			//Load the chart
			refreshToLoadTheChart();
			
			//Include all data points radio button from chart option
			includeAllDataPointsOnAxesTabChartOption();		
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replace("\n", " "), "QA Site 1 \\...\\ Rollover kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replace("\n", " "), "QA Site 1 \\...\\ Rollover kWh");
			printLog("Legend verified for Raw mode.");
			
			//"i" icon tool tip verification 
			Thread.sleep(4000);
			WebElement icon = d.findElement(By.xpath(locators.getProperty("DataQualityChart_i_IconOnCorrectMode")));
			String iconToolTip=getWebElementXpath("DataQualityChart_i_IconOnCorrectMode").getAttribute("data-bs-original-title").replaceAll("<b>","").replaceAll("</b>","");
			Assert.assertEquals(iconToolTip, "This chart contains 1 estimated value.");
			printLog("Verified the flag icon tool tip for Raw mode.");
			
			//Table data verification started
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ Z_Data Quality Scenarios \\ Rollover");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Energy Cumulative (kWh)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[4]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for Raw mode.");
			
			int rowVal=0;
			/*if(checkBrowserHeadless) {
				//Moving the cursor position to the table data where we have the test data to verify
				Utility.moveTheElement("//table[@id='maintable']//tr[30]");
				Thread.sleep(2000);
				Utility.moveTheElement("//table[@id='maintable']//tr[40]");
				Thread.sleep(2000);
				Utility.moveTheElement("//table[@id='maintable']//tr[40]");
				Thread.sleep(2000);
				rowVal=57;
			}else {*/
				Utility.moveTheElement("//table[@id='maintable']//tr[20]");
				Thread.sleep(2000);
				Utility.moveTheElement("//table[@id='maintable']//tr[50]");
				Thread.sleep(2000);
				Utility.moveTheElement("//table[@id='maintable']//tr[45]");
				Thread.sleep(2000);
				rowVal=37;
			//}
			enlargeBottomTabsPanel();
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/th").getText(), "7/6/2019 12:00 AM");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/td[1]").getText(),"100.0");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/td[2]").getText(), "Note+Estimate");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/td[3]").getText(), "200.0");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/td[4]").getText(), "Suspect+Note");
			printLog("Raw mode functionality complated!!");
			
			//Enlarge the bottom panel 
			hideEenlargeBottomTabsPanel();
			
			// Selected corrected mode
			getWebElementActionXpath("ChangeTOCorrectedMode").click();
			aJaxWait();
			printLog("Selected Corrected mode and load the chart.");
			
			//Legend verification
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replace("\n", " "), "QA Site 1 \\ Z_Data Quality Scenarios \\ Rollover Energy kWh");
			//Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replace("\n", " "), "QA Site 1 \\ Z_Data Quality Scenarios \\ Rollover Energy Cumulative kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendOne").getText().replace("\n", " "), "QA Site 1 \\...\\ Rollover kWh");
			Assert.assertEquals(getWebElementXpath("LineLegendTwo").getText().replace("\n", " "), "QA Site 1 \\...\\ Rollover kWh");
			
			printLog("Legend verified for Corrected mode.");
			
			//"i" icon tool tip verification 
			icon = d.findElement(By.xpath(locators.getProperty("DataQualityChart_i_IconOnCorrectMode")));
			iconToolTip=getWebElementXpath("DataQualityChart_i_IconOnCorrectMode").getAttribute("data-bs-original-title").replaceAll("<b>","").replaceAll("</b>","");
			Assert.assertEquals(iconToolTip, "This chart contains 1 estimated value.");
			printLog("Verified the flag icon tool tip for corrected mode.");
			
			//Table Header verification
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "QA Site 1 \\ Z_Data Quality Scenarios \\ Rollover");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Energy Cumulative (kWh)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[4]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for corrected mode.");
			
			//Moving the cursor position to the table data where we have the test data to verify
			Utility.moveTheElement("//table[@id='maintable']//tr[30]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[40]");
			Thread.sleep(2000);
			Utility.moveTheElement("//table[@id='maintable']//tr[40]");
			//Thread.sleep(2000);
			enlargeBottomTabsPanel();
			
			rowVal=0;
			rowVal=41;
			
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/th").getText(), "7/6/2019 12:00 AM");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/td[1]").getText(),"100.0");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/td[2]").getText(), "Note+Estimate");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/td[3]").getText(), "200.0");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr["+rowVal+"]/td[4]").getText(), "Suspect+Note");
			printLog("Corrected mode functionality complated!!");
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}

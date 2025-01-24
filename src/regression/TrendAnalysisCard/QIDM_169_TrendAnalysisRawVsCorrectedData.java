package regression.TrendAnalysisCard;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class QIDM_169_TrendAnalysisRawVsCorrectedData extends TestBase{
	LoginTC login = null;
	@Test
	public void trendAnalysisRawVsCorrectedData() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			//Search for site 'PAMTest_Capriata/Saiwa \ PAMTest_Energy Balance \ PAMTest_Ovens \ PAMTest_Banda FORNI 3-4-5'
			searchSiteInLocationList("PAMTest_Banda FORNI 3-4-5");
			getWebElementXpath_D("//table/tbody/tr[4]/td[3]/child::span").click();
			
			//Added the Fixed date "07/25/2023"- "07/31/2023"
			addFixedDateRange("07/25/2023","07/31/2023");
			
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
			
			//Verified the chart having Data Quality Flags
			List<WebElement> dataFlags = d.findElements(By.xpath("//*[@class='far fa-map-marker-alt']"));
			Assert.assertTrue(dataFlags.size()==2);
			printLog("Verified the chart having Data Quality Flags");
			
			WebElement icon = d.findElement(By.xpath("(//*[contains(@class,'highcharts-point')])[5]"));
			String toolTipText =null;
			Actions a = new Actions(d);
			a.moveToElement(icon,1,1).moveToElement(icon,1,1).build().perform();
			Thread.sleep(300);
			if (getWebElementXpath("tooltiptext") != null) {
				toolTipText = getWebElementXpath("tooltiptext").getText();
				printLog("toolTipText..."+toolTipText);
			}
			Assert.assertTrue(toolTipText.contains("Status: Suspect"));
						
			//Legend verification
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Banda FORNI 3-4-5 kWh");
			printLog("Legend verified for Raw mode.");
			
			//Table data verification started
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens \\ PAMTest_Banda FORNI 3-4-5");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for raw mode.");
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();

			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[5]/th").getText(), "7/29/2023");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[5]/td[1]").getText(), "26.63");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[5]/td[2]").getText(), "Suspect");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[6]/th").getText(), "7/30/2023");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[6]/td[1]").getText(), "18.28");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[6]/td[2]").getText(), "Suspect");

			//Hide Enlarge the bottom panel 
			hideEenlargeBottomTabsPanel();
			
			// Selected corrected mode
			getWebElementActionXpath("ChangeTOCorrectedMode").click();
			aJaxWait();
			printLog("Selected Corrected mode and load the chart.");
			
			//Legend verification
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa \\...\\ PAMTest_Banda FORNI 3-4-5 kWh");
			printLog("Legend verified for Corrected mode.");
			
			//Verified the chart having Data Quality Flags
			dataFlags = d.findElements(By.xpath("//*[@class='far fa-map-marker-alt']"));
			Assert.assertTrue(dataFlags.size()==3);
			printLog("Verified the chart having Data Quality Flags");
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens \\ PAMTest_Banda FORNI 3-4-5");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), testData.getProperty("TableHeaderDataQuality"));
			printLog("Table header verification completed for corrected mode.");
			
			//Enlarge the bottom panel 
			enlargeBottomTabsPanel();
			
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[4]/th").getText(), "7/28/2023");
			Assert.assertNotNull(getWebElementActionXpath_D("//*[@id='tablebody']/tr[4]/td[1]").getText());
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[4]/td[2]").getText(), "Valid+Estimate");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[5]/th").getText(), "7/29/2023");
			Assert.assertNotNull(getWebElementActionXpath_D("//*[@id='tablebody']/tr[5]/td[1]").getText());
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[5]/td[2]").getText(), "Valid+Estimate");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[6]/th").getText(), "7/30/2023");
			Assert.assertNotNull(getWebElementActionXpath_D("//*[@id='tablebody']/tr[6]/td[1]").getText());
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[6]/td[2]").getText(), "Suspect+Estimate");
			printLog("Table data verification completed for corrected mode.");
			
			hideEenlargeBottomTabsPanel();
			
			//"i" icon tool tip verification 
			String path = "(//i[@class='fa fa-info-circle'])[1]";
			Utility.moveTheElement(path);
			String iconToolTip=d.findElement(By.xpath("//div[@id='estimateMsg']/a")).getAttribute("aria-label").replaceAll("<b>","").replaceAll("</b>","");
			Assert.assertEquals(iconToolTip, "This chart contains 3 estimated values.");
			printLog("Verified the flag icon tool tip for corrected mode.");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
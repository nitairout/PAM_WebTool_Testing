package regression.ComparisonAnalysisCard;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies all three overlay options under comparison card.
 */
public class QIDM_79_ComparisonAnalysisOverlayOptions extends TestBase {
	LoginTC login = null;

	@Test
	public void comparisonAnalysisOverlayOptions() throws Throwable {
		try {

			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			
			goToPAMCard("ComparisonAnalysisCard");
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();
			refreshToLoadTheChart();
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			printLog("Selected Electricity Energy for PAMTestCapriataSaiwa and PAMTest_Herentals, Biscuits site ");
			
			// Selected Fixed date range '10/01/2022' - '10/31/2022'
			addFixedDateRange("10/01/2022", "10/31/2022");
			refreshToLoadTheChart();
			
			// Added the Overlay and Markers value and verify the marker line displays on the chart
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			getWebElementActionXpath("MarkersCheckbox").click();
			Thread.sleep(1000);
			getWebElementActionXpath("DemandFromMarker").click();
			Thread.sleep(1000);
			getWebElementActionXpath("NewMarkerValue").sendKeys("500000");
			Thread.sleep(1000);
			Assert.assertEquals(getWebElementActionXpath("MarkerLine").isDisplayed(), true);
			printLog("Verify the MarkerLine on a chart is displaying on the chart");
			//Uncheck Markers from Overlay
			getWebElementActionXpath("MarkersCheckbox").click();
			Thread.sleep(1000);
			
			//Click on 'Data Quality Flag' checkbox
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			aJaxWait();
			
			//Verified the chart having Data Quality Flags
			List<WebElement> dataFlags = d.findElements(By.xpath("//*[@class='far fa-map-marker-alt']"));
			Assert.assertTrue(dataFlags.size()>0);
			printLog("Verified the chart having Data Quality Flags");
			
			// Verified the headers on the table data
			String tableHeaders[] = {"Site","Energy (kWh)","Data Quality","Energy (kWh) %"};
			ArrayList<String> holdTableHeaders = Utility.holdAndReturnTableHeader();
			for (int i = 0; i < holdTableHeaders.size(); i++) {
				Assert.assertEquals(holdTableHeaders.get(i), tableHeaders[i]);
			}
			// Verified the data for the 3 rows from table Data
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText(),"PAMTest_Herentals, Biscuits");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td[2]").getText(),"Suspect+Estimate");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[2]/th").getText(),testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[2]/td[2]").getText(),"Suspect");
			printLog("Verified the table data as expected");
			
			//Uncheck 'Data Quality Flag' checkbox
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			aJaxWait();
			
			//Click on 'Petero Line' checkbox
			getWebElementActionXpath_D("//div[@id='overlayPareto']//span[contains(@class,'se-select-option-checkbox')]").click();
			aJaxWait();
			
			String petrolineFirstValue = getWebElementActionXpath_D("(//div[@class='highcharts-data-labels highcharts-series-1 highcharts-line-series ']/div)[1]/span").getText();
			Assert.assertEquals(petrolineFirstValue, "94.77 %");
			String petrolineSecondValue =getWebElementActionXpath_D("(//div[@class='highcharts-data-labels highcharts-series-1 highcharts-line-series ']/div)[2]/span").getText();
			Assert.assertEquals(petrolineSecondValue, "100.0 %");
			//Assert.assertEquals(petrolineSecondValue, "100 %");
			//Uncheck 'Petero Line' checkbox
			getWebElementActionXpath_D("//div[@id='overlayPareto']//span[contains(@class,'se-select-option-checkbox')]").click();
			aJaxWait();
			
			Utility.moveTheScrollToTheTop();
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Gas#Energy~standard");
			
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Gas").click();
			refreshToLoadTheChart();
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Gas").click();
			printLog("Selected Electricity and Gas Energy for PAMTestCapriataSaiwa and PAMTest_Herentals, Biscuits site ");
			
			refreshToLoadTheChart();
			
			//Verified the alert message
			Assert.assertEquals(d.findElement(By.xpath("//div[@class='warnings']/div/span")).getText(), "Pie Chart and Pareto line overlay are unavailable for this analysis. All measurements must have common units.");
			printLog("Verified the alert message as 'Pie Chart and Pareto line overlay are unavailable for this analysis. All measurements must have common units.'");
			
			//Verified that Petero Line option should be disabled
			Assert.assertEquals(getWebElementActionXpath_D("//div[@id='overlayPareto']").getAttribute("disabled"), "true");
			printLog("Verified that 'Petero Line' option should be disabled");
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}


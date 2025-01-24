package regression.AllCardsCommonTCs;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies saving, overwriting and deleting saved analysis reports from all cards.
 */
public class QIDM_57_AllCardsSaveAndDeleteAnalysisReports extends TestBase {
	LoginTC login = null;

	@Test
	public void allCardsSaveAndDeleteAnalysisReports() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			Utility.clearsavedanalysis(d);
			
			//Navigate to Trend Analysis Card and saved analysis as 'Trend Test Report'
			goToPAMCard("TrendAnalysisCard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();			
			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();			
			//Verify that legend display under the chart.
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Verified the legend for Trend Analysis Card..");			
			//Save the analysis as 'Trend Test Report'
			saveAnalysis("Trend Test Report");
			printLog("Saved the analysis as 'Trend Test Report'");	
			
			// Navigate to Load Profile Analysis card using C2C and saved analysis as 'LP Test Report'
			card2Card("Load Profile Analysis");
			//Verify that legend display under the chart.
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Verified the legend for Load Profile Analysis Card..");			
			//Save the analysis as 'LP Test Report'
			saveAnalysis("LP Test Report");
			printLog("Saved the analysis as 'LP Test Report'");
			
			// Navigate to Calendar Load Profile Analysis card using C2C and saved analysis as 'CLP Test Report'
			card2Card("Calendar Load Profile Analysis");
			//Verify that legend display under the chart.
			Assert.assertEquals(getWebElementActionXpath_D("(//span[@class='legend-text--calendar'])[1]/span").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Verified the legend for Calendar Load Profile Analysis Card..");			
			//Save the analysis as 'CLP Test Report'
			saveAnalysis("CLP Test Report");
			printLog("Saved the analysis as 'CLP Test Report'");
			
			// Navigate to Comparison Analysis card using C2C and saved analysis as 'Comparison Test Report'
			card2Card("Comparison Analysis");
			//Verify that legend display under the chart.
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText().replace("\n", " "), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Verified the legend for Comparison Analysis Card..");			
			//Save the analysis as 'Comparison Test Report'
			saveAnalysis("Comparison Test Report");
			printLog("Saved the analysis as 'Comparison Test Report'");
			
			// Navigate to Scatter Plot Analysis card using C2C and saved analysis as 'Scatter Plot Test Report'
			card2Card("Scatter Plot Analysis");
			//Verify that legend display under the chart.
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Verified the legend for Scatter Plot Analysis Card..");			
			//Save the analysis as 'Scatter Plot Test Report'
			saveAnalysis("Scatter Plot Test Report");
			printLog("Saved the analysis as 'Scatter Plot Test Report'");
			
			//Verify the list of saved analysis , it should be 5
			goToAnalysisPage();			
			//expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);
			
			int listOfSavedAnalysis = d.findElements(By.xpath("//tbody/tr")).size();
			Assert.assertEquals(listOfSavedAnalysis, 5);
			
			String analysisArray[]={"Trend Test Report","LP Test Report","CLP Test Report","Comparison Test Report","Scatter Plot Test Report"};
			String analysisPath,actualAnalysisName;
			for(String expectedAnalysisName : analysisArray){
				analysisPath = "//div[normalize-space()='"+expectedAnalysisName+"']";
				actualAnalysisName = d.findElement(By.xpath(analysisPath)).getText();
				Assert.assertEquals(actualAnalysisName, expectedAnalysisName);	
			}
			printLog("Verified all the saved analysis names");
			
			//Click on Trend saved analysis and change the date range to 1/1/2023 and saved the analysis
			d.findElement(By.xpath("//div[normalize-space()='Trend Test Report']")).click();
			Thread.sleep(5000);
			Assert.assertEquals(getWebElementXpath("ChartStartDate").getText(), testData.getProperty("FixedDateStartJan012023"));
			Assert.assertEquals(getWebElementXpath("ChartEndDate").getText(), testData.getProperty("FixedDateEndJan312023"));
			printLog("Verified the date range on chart");
			//provide end date as '1/1/2023'
			getWebElementActionXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_LEFT);
			Thread.sleep(500);
			getWebElementXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").sendKeys("1/1/2023");
			Thread.sleep(2000);
			getWebElementXpath_D("//small[normalize-space()='End Date']/following-sibling::kendo-datepicker//child::input").sendKeys(Keys.TAB);
			d.switchTo().activeElement().sendKeys(Keys.ENTER);
			Thread.sleep(4000);
			//Save the analysis as 'Trend Test Report'
			kabobMenuOptions("Save Analysis");
			//Click on Overwrite Existing Analysis checkbox
			getWebElementActionXpath_D("//div[contains(@class,'checkbox')]//input").click();
			Thread.sleep(2000);
			getWebElementActionXpath("saveanalysisOK").click();
			Thread.sleep(5000);
			getWebElementActionXpath("saveanalysisClose").click();
			aJaxWait();
			Thread.sleep(5000);
			
			//Verify the updated date range on saved analysis
			goToAnalysisPage();
			//expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);
			
			//Click on Trend saved analysis and verify the updated date range on the chart 
			d.findElement(By.xpath("//div[normalize-space()='Trend Test Report']")).click();
			Thread.sleep(5000);
			Assert.assertEquals(getWebElementXpath("ChartStartDate").getText(), "1/1/2023 12:00 AM");
			Assert.assertEquals(getWebElementXpath("ChartEndDate").getText(), "1/2/2023 12:00 AM");
			printLog("Verified the date range on chart after update on the saved analysis");
			
			//Click on Load Profile saved analysis and update the card with name as 'LP Test Report2' and verify new saved analysis added 
			goToAnalysisPage();
			//expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);			
			//Click on Load Profile Analysis
			d.findElement(By.xpath("//div[normalize-space()='LP Test Report']")).click();
			Thread.sleep(5000);
			Assert.assertEquals(getWebElementXpath_D("//div[@class='widget-header']/h3/span[1]").getText(),"LP Test Report");			
			//Save the analysis as 'LP Test Report2'
			kabobMenuOptions("Save Analysis");
			//Enter the name to save the analysis	
			WebElement save=getWebElementActionXpath_D("//input[@name='inputField']");
			Thread.sleep(2000);
			save.clear();
			save.sendKeys("LP Test Report2");
			Thread.sleep(2000);
			
			clickUsingJavascriptExecuter(locators.getProperty("saveanalysisOK"));
			Thread.sleep(2000);
			Thread.sleep(5000);
			getWebElementActionXpath("saveanalysisClose").click();
			aJaxWait();
			Thread.sleep(5000);
			
			goToAnalysisPage();
			//expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);
			
			listOfSavedAnalysis = d.findElements(By.xpath("//tbody/tr")).size();
			Assert.assertEquals(listOfSavedAnalysis, 6);
			
			actualAnalysisName = d.findElement(By.xpath("//div[normalize-space()='LP Test Report2']")).getText();
			Assert.assertEquals(actualAnalysisName, "LP Test Report2");	
			
			//Clear all the saved analysis
			goToAnalysisPage();
			Utility.clearsavedanalysis(d);
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}


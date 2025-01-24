package regression.LoadProfileAnalysisCard;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies all chart options Series Type and Color in Trend card. 
 * Selects each option one by one and loads chart and verifies series on the chart are displayed as per selection.
 */
public class QIDM_182_LoadProfileChartOptionsSeriesColor  extends TestBase{
	LoginTC login = null;
	
	@Test
	public void loadProfileChartOptionsSeriesColor() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			//Directly select main meter and energy balance is not working
			//Searching with "PAMTest_Capriata/Saiwa" and expanding to select PAMTest_Energy Balance & PAMTest_Main Meter energy
			searchSiteInLocationList("PAMTest_Capriata/Saiwa");
			getWebElementActionXpath_D("//td[normalize-space()='PAMTest_Capriata/Saiwa']//*[contains(@class,'k-svg-icon')]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Energy Balance']/following-sibling::td[2]/child::span").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//td[normalize-space()='PAMTest_Main Meter']/following-sibling::td[2]/child::span").click();
			Thread.sleep(1000);

			// Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();

			Utility.moveTheScrollToTheTop();
			//Verify the legend Colors before change
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[1]")).getAttribute("fill"), "#2fb5ea");
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[2]")).getAttribute("fill"), "#f5a300");
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			
			getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode']/*[@role='combobox'])[1]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//span[normalize-space()='Use Tag Colors']").click();
			Thread.sleep(1000);
			
			new Select(getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode']/select)[1]")).selectByVisibleText("Autotag");
			Thread.sleep(1000);
			
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			Thread.sleep(10000);
			
			//Verify the legend Colors after change
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[1]")).getAttribute("fill"), "rgb(255, 127, 237)");
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[2]")).getAttribute("fill"), "rgb(178, 0, 255)");

			//UnSelect Electric commodity for a site PAMTest_Energy Balance
			searchSiteInLocationList(testData.getProperty("PAMTest_Energy_Balance"));
			getWebElementXpath("PAMTest_EnergyBalance_Energy").click();
			
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Gas#Volume~standard");
			//UnSelect Electric commodity for a site PAMTest_Main Meter
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			//Select Volume from Gas Commodity
			getWebElementXpath("PAMTest_MainMeter_Gas").click();
			refreshToLoadTheChart();
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			//Go to Series Color tab
			getWebElementXpath("ChartOptionsSeriesColorTab").click();
			Thread.sleep(2000);
			
			//Click on Commodity radio button
			getWebElementXpath_D("//input[@id='chartOptions_colorByCommodity']").click();
			Thread.sleep(2000);
			
			//Select Yellow color for Gas commodity
			getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode']/*[@role='combobox'])[2]").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
			//getWebElementXpath_D("//*[normalize-space()='Palette']").click();
			Thread.sleep(1000);		
			//siteDD.selectByVisibleText("Palette");
			Thread.sleep(1000);
			
			new Select(getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-detail'])[2]//select[1]")).selectByValue("5: 5");
			Thread.sleep(1000);
			
			//Select Green color for Electricity commodity
			getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-mode']/*[@role='combobox'])[1]").click();
			Thread.sleep(1000);
			d.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
			//getWebElementXpath_D("//*[normalize-space()='Palette']").click();
			Thread.sleep(3000);		
			//siteDD.selectByVisibleText("Palette");
			
			getWebElementActionXpath_D("(//span[contains(@class,'k-column-title')])[1]").click();//for cursor out of the field
			Thread.sleep(1000);
			new Select(getWebElementActionXpath_D("(//div[@id='se-chartop-colorby-detail'])[1]//select[1]")).selectByValue("3: 3");
			Thread.sleep(1000);
			
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			aJaxWait();
			Thread.sleep(10000);
			
			//Verify the legend Colors after change
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[1]")).getAttribute("fill"), "#3dcd58");
			Assert.assertEquals(d.findElement(By.xpath("(//*[@class='highcharts-point'])[2]")).getAttribute("fill"), "#ffd100");
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

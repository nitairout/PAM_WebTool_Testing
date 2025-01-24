package regression.PAMAdminstrationTCs.ManageMeasurements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 *This test verifies calculated measurements using 'Make Stream' in  
 *the expression and verifies the values in Trend card.
 */
public class QIDM_140_MakeDataStreamUsingExpression extends TestBase {
	LoginTC login = null;
	
	@Test
	public void makeDataStreamUsingExpression() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");

			Utility.selectSingleMeasurement("Electricity","Auto Test-MakeDataStream_Scenario1",userDefined);
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			getWebElementXpath("PAMTest_MainMeter_Energy").click();	
			
			//Select the Fixed date range of '01/01/2023' - '01/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			//Select the time interval - By week
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By month");
			printLog("Selected interval By month");
			
			WebElement icon = d.findElement(By.xpath("(//*[contains(@class,'highcharts-point')])[1]"));
			String toolTipText =null;
			Actions a = new Actions(d);
			a.moveToElement(icon,1,1).moveToElement(icon,1,1).build().perform();
			Thread.sleep(300);
			if (getWebElementXpath("tooltiptext") != null) {
				toolTipText = getWebElementXpath("tooltiptext").getText();
				printLog("toolTipText..."+toolTipText);
			}
			
			//Chart tool tip
			//Utility.moveTheElement("//*[contains(@class,'highcharts-point')]");
			//Chart tool tip
			WebElement icon1 = d.findElement(By.xpath("(//*[contains(@class,'highcharts-point')])[1]"));
			toolTipText =null;
			a = new Actions(d);
			a.moveToElement(icon1,1,1).moveToElement(icon1,1,1).build().perform();
			Thread.sleep(300);
			if (getWebElementXpath("tooltiptext") != null) {
				toolTipText = getWebElementXpath("tooltiptext").getText();
				printLog("toolTipText..."+toolTipText);
			}
			
			String chartToolTip=getWebElementActionXpath("tooltiptext").getText();
			Assert.assertTrue(chartToolTip.contains("Auto Test-MakeDataStream_Scenario1: 1,200 kWh"));
			
			String legendValue=getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legendValue, "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			printLog("Legend and chart tool tip verification completed for Test-MakeDataStream_Scenario1");
			
			//Click on the Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Table verification started
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), "Auto Test-MakeDataStream_Scenario1 (kWh)");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/th").getText(), "Jan 2023");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/td").getText(), "1,200");
			Utility.moveTheScrollToTheTop();
			printLog("Table Verification completed for Test-MakeDataStream_Scenario1");
			
			//Started for Auto Test-MakeDataStream_Scenario2
			Utility.selectSingleMeasurement("Electricity","Auto Test-MakeDataStream_Scenario2",userDefined);
			Thread.sleep(2000);
			//Search with the sitename from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			//getWebElementXpath("PAMTest_MainMeter_Energy").click();	
			//refreshToLoadTheChart();
			
			//Chart tool tip
			icon1 = d.findElement(By.xpath("(//*[contains(@class,'highcharts-point')])[1]"));
			toolTipText =null;
			a = new Actions(d);
			a.moveToElement(icon1,1,1).moveToElement(icon1,1,1).build().perform();
			Thread.sleep(300);
			if (getWebElementXpath("tooltiptext") != null) {
				toolTipText = getWebElementXpath("tooltiptext").getText();
				printLog("toolTipText..."+toolTipText);
			}
			//Utility.moveTheElement("//*[contains(@class,'highcharts-point')]");
			chartToolTip=getWebElementXpath("tooltiptext").getText();
			Assert.assertTrue(chartToolTip.contains("Auto Test-MakeDataStream_Scenario2: 260,582 kWh"));
			legendValue=getWebElementXpath("LineLegendOne").getText();
			Assert.assertEquals(legendValue, "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			printLog("Legend and chart tool tip verification completed for Test-MakeDataStream_Scenario2");
			
			//Table verification started
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th[2]").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th").getText(), "Auto Test-MakeDataStream_Scenario2 (kWh)");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/th").getText(), "Jan 2023");
			Assert.assertEquals(getWebElementXpath_D("//*[@id='tablebody']/tr/td").getText(), "260,582");
			Utility.moveTheScrollToTheTop();
			printLog("Table Verification completed for MakeDataStream_Scenario2");
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
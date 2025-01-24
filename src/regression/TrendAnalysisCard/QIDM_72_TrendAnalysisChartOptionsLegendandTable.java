package regression.TrendAnalysisCard;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies chart options available for Legend and Table data.
 */

public class QIDM_72_TrendAnalysisChartOptionsLegendandTable extends TestBase {
	LoginTC login = null;
	@Test
	public void trendAnalysisChartOptionsLegendandTable() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			goToAnalysisPage();
			Utility.clearsavedanalysis(d);
			goToPAMCard("TrendAnalysisCard");

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
			Thread.sleep(10000);
			// verify start and end date from chart
			Assert.assertEquals(getWebElementXpath("ChartStartDate").getText(), testData.getProperty("FixedDateStartJan012023"));
			Assert.assertEquals(getWebElementXpath("ChartEndDate").getText(), testData.getProperty("FixedDateEndJan312023"));
			printLog("Verified the Date Range on the chart");
			//Verify that extended legend display under the chart.
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance kWh");
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kWh");
			printLog("Verified legends from chart!!");
			
			// Click on KabobMenu and select Chart Options.
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			// Select Legend tab.
			getWebElementXpath("ChartOptionsLegendTab").click();
			getWebElementXpath("ExtendLegend").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();		
			//Verify that extended legend display under the chart.
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_EnergyBalancekWh"));
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), testData.getProperty("PAMTest_CapriataSaiwaPAMTest_MainMeterkWh"));
			printLog("Verified the extended legend displays under the chart");
			
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			// Switch to Legend tab and select Hide Location Path checkbox.
			getWebElementXpath("ChartOptionsLegendTab").click();
			getWebElementXpath("LegendHideLocationPath").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			Thread.sleep(10000);
			//Verify that Legend hide location path testData.getProperty("PAMTestCapriataSaiwa") and displays legends PAMTest_Energy Balance kWh and PAMTest_Main Meter kWh.
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Energy Balance kWh");
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), "PAMTest_Main Meter kWh");
			printLog("Verified the legend hide location path displays under the chart");
			
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			// Switch to Legend tab and select Hide Legend checkbox.
			getWebElementXpath("ChartOptionsLegendTab").click();
			Thread.sleep(2000);
			getWebElementXpath("HideLegend").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			// Verify that Legend is hidden and no legend displays under the chart.
			int legendsCount = d.findElements(By.xpath("ColumnLegendOne")).size();
			Assert.assertEquals(legendsCount, 0);
			printLog("Verify that Legend is hidden and no legend displays under the chart");
			
			// Go back to Chart Options and select Table tab.
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			getWebElementActionXpath("TableTabFromChartOptions").click();
			Thread.sleep(2000);

			// Select an option Transpose.
			getWebElementActionXpath("Transpose").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			//Verified the columns headers should change to interval data time stamp
			/*String[] expectedTableData = {"Date/Time","10/1/2022","10/2/2022","10/3/2022","10/4/2022","10/5/2022","10/6/2022","10/7/2022","10/8/2022","10/9/2022","10/10/2022",
					"10/11/2022","10/12/2022","10/13/2022","10/14/2022","10/15/2022","10/16/2022","10/17/2022","10/18/2022","10/19/2022","10/20/2022","10/21/2022",
					"10/22/2022","10/23/2022","10/24/2022","10/25/2022","10/26/2022","10/27/2022","10/28/2022","10/29/2022","10/30/2022","10/31/2022",};
			*/
			String[] expectedTableData = {"Date/Time","1/1/2023","1/2/2023","1/3/2023","1/4/2023","1/5/2023","1/6/2023","1/7/2023","1/8/2023","1/9/2023","1/10/2023",
					"1/11/2023","1/12/2023","1/13/2023","1/14/2023","1/15/2023","1/16/2023","1/17/2023","1/18/2023","1/19/2023","1/20/2023","1/21/2023",
					"1/22/2023","1/23/2023","1/24/2023","1/25/2023","1/26/2023","1/27/2023","1/28/2023","1/29/2023","1/30/2023","1/31/2023",};
			List<WebElement> headersLsit =d.findElements(By.xpath("//*[@id='tablehead']/tr/th"));
			String header,path = null;
			for(int i=0;i<headersLsit.size();i++) {
				path = "//*[@id='tablehead']/tr/th["+(i+1)+"]";
				clickUsingJavascriptExecuter(path);
		        header = getWebElementActionXpath_D(path).getText();
				Assert.assertEquals(header, expectedTableData[i]);
			}
			//Verify the Site names with locations from table tab
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[1]/th[1]")).getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance");
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[1]/th[2]")).getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[2]/th[1]")).getText(), "PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter");
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[2]/th[2]")).getText(), testData.getProperty("TableHeaderEnergykWh"));
			
			clickUsingJavascriptExecuter("//*[@class='widget-header']//child::i[contains(@class,'se-menu-kabob clickable')]");
			// Go back to chart option Table tab.
			kabobMenuOptions("Chart Options");
			Thread.sleep(2000);
			getWebElementActionXpath("TableTabFromChartOptions").click();
			Thread.sleep(2000);
			// Select Hide Location Path.
			getWebElementActionXpath("TableHideLocationPath").click();
			getWebElementXpath("SaveAndClose").click();
			aJaxWait();
			Thread.sleep(10000);
			//Verify the Site names after hide the locations from table tab
			Utility.moveTheScrollToTheDown();
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[1]/th[1]")).getText(), "PAMTest_Energy Balance");
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[1]/th[2]")).getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[2]/th[1]")).getText(), testData.getProperty("PAMTest_Main_Meter"));
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[2]/th[2]")).getText(), testData.getProperty("TableHeaderEnergykWh"));
			
			//kabobMenuOptions("Save Analysis");
			saveAnalysis("QIDM_72_ChartOptionsLegendandTable_SavedAnalysis");
			
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(15000);
			//Enlarge the dashboard widget size
			//Utility.dragAndDrop(locators.getProperty("WidgetDragAndDropXpath"));
			Thread.sleep(5000);
			
			String widget = getWebElementActionXpath("WidgetTitle").getText();
			Assert.assertEquals(widget, "QIDM_72_ChartOptionsLegendandTable_SavedAnalysis");
			
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath("WidgetTableTab").click();
			Thread.sleep(3000);
			
			List<WebElement> columnsSize = d.findElements(By.xpath("//*[@id='tablehead']/tr/th"));
			Assert.assertEquals(columnsSize.size(), 32);
			
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[1]/th[1]")).getText(), "PAMTest_Energy Balance");
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[1]/th[2]")).getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[2]/th[1]")).getText(), testData.getProperty("PAMTest_Main_Meter"));
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablebody']/tr[2]/th[2]")).getText(), testData.getProperty("TableHeaderEnergykWh"));
			// click on logout
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}

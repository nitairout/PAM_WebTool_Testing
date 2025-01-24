package regression.DashboardsAndWidgets.LegacyDashBoardAndWidgets;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies pushing PAM card to legacy dashboard and verifies 
 * widget is displayed as expected by verifying data in Chart. 
 * It also verifies editing widget title and navigating back to the PAM card again.
 * 
 */

public class QIDM_173_LegacyCalenderLoadProfileWidget extends TestBase{
	LoginTC login = null;
	boolean widgetDeleteFlag=true;
	@Test
	public void legacyCalenderLoadProfileWidget() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			Utility.selectDashboard("Legacy Dashboards","AutoTestDashboard");
			
			goToAnalysisPage();
			goToPAMCard("CalendarAnalysisCard");
			
			//Added Demand measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Demand",standard);

			// Select Jan 2023 against Fixed period
			fixedDateRangeForCLP("January", "2023");
			
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			refreshToLoadTheChart();
			
			// Added the Overlay and Markers value and verify the marker line displays on the chart
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			getWebElementActionXpath("MarkersCheckbox").click();
			Thread.sleep(1000);
			getWebElementActionXpath("DemandFromMarker").click();
			Thread.sleep(1000);
			getWebElementActionXpath("NewMarkerValue").sendKeys("500");
			Thread.sleep(1000);
			//d.switchTo().activeElement().sendKeys(Keys.ENTER);
			
			Assert.assertEquals(getWebElementActionXpath("MarkerLine").isDisplayed(), true);
			printLog("Verify the MarkerLine on a chart is displaying on the chart");
			aJaxWait();
			
			// Select first two days(Sunday and Monday) on the chart
			getWebElementActionXpath_D("(//*[@class='highcharts-series-group'])[1]").click();
			getWebElementActionXpath_D("(//*[@class='highcharts-series-group'])[2]").click();
			Thread.sleep(3000);

			Utility.hideOptionLocationPanelForCards();
			
			//Holding legend from chart and compare tab to verify in widget
			ArrayList<String> legendArray=new ArrayList<String>();
			legendArray.add(getWebElementActionXpath_D("//*[@id='mainChart']/div/span/span[3]/span/span[1]/span").getText());
			legendArray.add(getWebElementActionXpath("LineLegendOne").getText());
			legendArray.add(getWebElementActionXpath("LineLegendTwo").getText());
			
			//Table data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(1000);
			
			ArrayList<String> tableHeaderArrayOne=new ArrayList<String>();
			int headerRowOne=d.findElements(By.xpath("//*[@id='tablehead']/tr[2]/th")).size();
			for(int i=1;i<=headerRowOne;i++) {
				tableHeaderArrayOne.add(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th["+i+"]").getText());
			}
			printLog("Hold the table header.");

			ArrayList<String> tableDataArray=new ArrayList<String>();	
			tableDataArray.add(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText());
			int forstCellSize=d.findElements(By.xpath("//*[@id='tablebody']/tr[1]/td")).size();
			for(int i=1;i<=forstCellSize;i++) {
				tableDataArray.add(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td["+i+"]").getText());
			}
			printLog("Hold 1st row of the table data.");
			
			pushwidget("AutoTestDashboard", "Legacy Dashboards");
			
			Utility.moveTheScrollToThebottomOnRquest("100");
			d.switchTo().frame(getWebElementActionXpath("SwitchToLegacyWidgetFrame"));
			Thread.sleep(5000);
			//Compare tab verification
			Thread.sleep(2000);
			legendArray.contains(getWebElementActionXpath_D("(//*[@id='gemini']//span[@ng-bind-html='serie.name'])[1]").getText());
			printLog("Compared with compare tab data with PAM compare tab.");
			
			//Tool icon to edit
			d.switchTo().frame(getWebElementActionXpath("SwitchToLegacyWidgetFrame"));
			getWebElementActionXpath_D("//a[@title='Menu']/span[@class='rdCustom']").click();
			Thread.sleep(2000);
			getWebElementActionXpath_D("(//span[@class='rmText' and text()='Edit'])[1]").click();
			Thread.sleep(4000);
			
			d.switchTo().frame(getWebElementActionXpath_D("/html/body/form/div[1]/table/tbody/tr[2]/td[2]/iframe"));
			getWebElementActionXpath_D("//input[@id='rtbWidgetTitle']").clear();
			getWebElementXpath_D("//input[@id='rtbWidgetTitle']").sendKeys("CLP Widget 1");
			Thread.sleep(1000);
			getWebElementActionXpath_D("//span[@id='lblsav' and text()='Save']").click();
			aJaxWait();
			Thread.sleep(5000);
			printLog("Edited the widget to CLP Widget 1.");
			
			d.switchTo().frame(getWebElementActionXpath("SwitchToLegacyWidgetFrame"));
			Assert.assertEquals("CLP Widget 1", getWebElementXpath_D("//*[text()='CLP Widget 1']").getText());
			
			//Going to pam page by clicking on >(Additional Details)
			Thread.sleep(2000);
			getWebElementActionCSS("AdditionalDetails").click();
			aJaxWait();
			Thread.sleep(5000);
			printLog("Inside PAM page now.");
			
			Assert.assertEquals("CLP Widget 1", getWebElementXpath_D("//*[text()='CLP Widget 1']").getText());
			
			//Click on home link to go to widget page to delete
			
			getWebElementActionXpath_D("//a[contains(text(),'Home')]").click();
			aJaxWait();
			Thread.sleep(5000);
			printLog("Inside widget page.");
			
			//By default the AutoTestDashboard widget is selected from Legacy Dashboard
			
			Utility.deleteLegacyDBWidget();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
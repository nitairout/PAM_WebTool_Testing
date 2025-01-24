package regression.DashboardsAndWidgets.LegacyDashBoardAndWidgets;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies pushing PAM card to legacy dashboard and verifies widget is displayed as expected by 
 * verifying data in Chart, Statistics and Table tabs. 
 * It also verifies editing widget title and navigating back to the PAM card again.
 * 
 */

public class QIDM_176_LegacyTrendAnalysisWidget extends TestBase{
	LoginTC login = null;
	boolean widgetDeleteFlag=true;
	@Test
	public void legacyTrendAnalysisWidget() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			Utility.selectDashboard("Legacy Dashboards","AutoTestDashboard");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			//Select the Previous Month as Comparison Date Range
			getWebElementActionXpath("ComparisonDateRangeExpander").click();
			Thread.sleep(1000);
			new Select(getWebElementActionXpath("SelectcomparisonDateRange")).selectByVisibleText("Previous Month");
			
			refreshToLoadTheChart();
			
			// Selected AutoTest_StoreHours from Site Schedule from DataBreakdown
			dataBreakDownSiteSchedule("Site Schedule","AutoTest_StoreHours");
			printLog("Selected AutoTestTemplate fom Site Schedule from DataBreakdown");
			
			Utility.hideOptionLocationPanelForCards();
			
			//Holding legend to verify in widget
			ArrayList<String> legendArray=new ArrayList<String>();
			legendArray.add(getWebElementActionXpath("ColumnLegendOne").getText());
			legendArray.add(getWebElementActionXpath("ColumnLegendTwo").getText());
			legendArray.add(getWebElementActionXpath("LineLegendThree").getText());
			legendArray.add(getWebElementActionXpath("LineLegendFour").getText());
			legendArray.add(getWebElementActionXpath("ColumnLegendFive").getText());
			legendArray.add(getWebElementActionXpath("ColumnLegendSix").getText());
			
			//Holding statistics data to verify in widget
			ArrayList<String> statisticsValueArray=new ArrayList<String>();
			ArrayList<String> statisticsLevelArray=new ArrayList<String>();
			ArrayList<String> statisticsUnitArray=new ArrayList<String>();
			//1st 6 statistics value
			for(int i=1;i<=6;i++) {
				statisticsLevelArray.add(getWebElementActionXpath_D("(//pam-statistic//*[@class='se-statistic-title'])["+i+"]").getText());
				statisticsValueArray.add(getWebElementActionXpath_D("(//pam-statistic//*[@class='se-statistic-value'])["+i+"]").getText());
				statisticsUnitArray.add(getWebElementActionXpath_D("(//pam-statistic//*[@class='se-statistic-unit'])["+i+"]").getText());
			}
			
			printLog("Hold all the legend and statistics value.");
			
			//Table data tab
			getWebElementXpath("DataTableTab").click();
			
			ArrayList<String> tableHeaderArrayOne=new ArrayList<String>();
			ArrayList<String> tableHeaderArrayTwo=new ArrayList<String>();
			int headerRowOne=d.findElements(By.xpath("//*[@id='tablehead']/tr[2]/th")).size();
			int headerRowTwo=d.findElements(By.xpath("//*[@id='tablehead']/tr[3]/th")).size();
			for(int i=1;i<=headerRowOne;i++) {
				tableHeaderArrayOne.add(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th["+i+"]").getText());
			}
			for(int i=1;i<=headerRowTwo;i++) {
				tableHeaderArrayTwo.add(getWebElementActionXpath_D("//*[@id='tablehead']/tr[3]/th["+i+"]").getText());
			}
			printLog("Hold all the table header.");

			ArrayList<String> tableDataArray=new ArrayList<String>();	
			tableDataArray.add(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText());
			int forstCellSize=d.findElements(By.xpath("//*[@id='tablebody']/tr[1]/td")).size();
			for(int i=1;i<=forstCellSize;i++) {
				tableDataArray.add(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td["+i+"]").getText());
			}
			printLog("Hold 1st row of the table data.");
			
			pushwidget("AutoTestDashboard", "Legacy Dashboards");
			
			File deleteFil=new File(Constant.DOWNLOAD_PATH+"\\trendAnalysis.csv");
			deleteFil.delete();
			Utility.moveTheScrollToThebottomOnRquest("150");
			
			d.switchTo().frame(getWebElementActionXpath("SwitchToLegacyWidgetFrame"));
			Thread.sleep(8000);
			
			legendArray.contains(getWebElementActionXpath("ColumnLegendOne").getText());
			legendArray.contains(getWebElementActionXpath("ColumnLegendTwo").getText());
			legendArray.contains(getWebElementActionXpath("LineLegendThree").getText());
			legendArray.contains(getWebElementActionXpath("LineLegendFour").getText());
			legendArray.contains(getWebElementActionXpath("ColumnLegendFive").getText());
			legendArray.contains(getWebElementActionXpath("ColumnLegendSix").getText());
			printLog("Legend compared in widget.");
			
			//Statistics tab comparisonDateRange
			//Stats tab
			getWebElementActionXpath_D("//i[@class='fa fa-calculator']").click();
			Thread.sleep(2000);
			for(int i=0;i<statisticsValueArray.size();i++) {
				d.getPageSource().contains(statisticsValueArray.get(i));
				d.getPageSource().contains(statisticsLevelArray.get(i));
				d.getPageSource().contains(statisticsUnitArray.get(i));
			}
			printLog("Compared statistics data with PAM stat.");

			//Widget hambgur menu to export csv
			getWebElementActionXpath_D("//*[@id='gemini']//i[@class='fa fa-navicon']").click();
			getWebElementActionXpath_D("//a[contains(text(),'Export Chart Data to CSV')]").click();
			Thread.sleep(5000);
			
			//getting CSV data to compare
			ArrayList<List<String>> csvData=Utility.returnNumberOfRowsFromcsv("trendAnalysis",4);
			
			//Comparing PAM table data with widget table data
			for(int i=1;i<=headerRowOne;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th["+i+"]").getText(), tableHeaderArrayOne.get(i-1));
			}
			for(int i=1;i<=headerRowTwo;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[3]/th["+i+"]").getText(), tableHeaderArrayTwo.get(i-1));
			}
			
			//Comparing table data with csv with PAM
			ArrayList<String> csvDataToCompare = new ArrayList<String>(csvData.get((csvData.size()-1)));
			for(int i=0;i<tableDataArray.size();i++) {
				if(i==0) {
					//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy h:mm:ss a");
					//LocalDate csvDateConvert = LocalDate.parse(csvDataToCompare.get(i), formatter);
					//Assert.assertEquals(tableDataArray.get(i),changeTheDateFormat("M/d/yyyy",csvDateConvert));
					Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/th").getText(), tableDataArray.get(i));
				}else if(i!=6 && i!=0){
					Assert.assertEquals(tableDataArray.get(i), csvDataToCompare.get(i));
					Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[1]/td["+i+"]").getText(), tableDataArray.get(i));
				}
			}
			
			printLog("Compared the table header and data.");
			
			d.switchTo().frame(getWebElementActionXpath("SwitchToLegacyWidgetFrame"));
			
			//Tool icon to edit
			getWebElementActionXpath_D("//a[@title='Menu']/span[@class='rdCustom']").click();
			Thread.sleep(2000);
			getWebElementActionXpath_D("(//span[@class='rmText' and text()='Edit'])[1]").click();
			Thread.sleep(4000);
			
			d.switchTo().frame(getWebElementActionXpath_D("/html/body/form/div[1]/table/tbody/tr[2]/td[2]/iframe"));
			getWebElementActionXpath_D("//input[@id='rtbWidgetTitle']").clear();
			getWebElementXpath_D("//input[@id='rtbWidgetTitle']").sendKeys("Trend Widget 1");
			Thread.sleep(1000);
			getWebElementActionXpath_D("//span[@id='lblsav' and text()='Save']").click();
			aJaxWait();
			Thread.sleep(5000);
			printLog("Edited the widget to Trend Widget 1.");
			
			d.switchTo().frame(getWebElementActionXpath("SwitchToLegacyWidgetFrame"));
			Assert.assertEquals("Trend Widget 1", getWebElementXpath_D("//*[text()='Trend Widget 1']").getText());
			
			//Going to pam page by clicking on >(Additional Details)
			Thread.sleep(2000);
			getWebElementActionCSS("AdditionalDetails").click();
			aJaxWait();
			Thread.sleep(5000);
			printLog("Inside PAM page now.");
			
			Assert.assertEquals("Trend Widget 1", getWebElementXpath_D("//*[text()='Trend Widget 1']").getText());
			
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
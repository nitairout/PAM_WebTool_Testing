package regression.DashboardsAndWidgets.LegacyDashBoardAndWidgets;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 *This test verifies pushing PAM card to legacy dashboard and verifies widget is displayed as expected by verifying data in Chart, pie chart, Statistics and Table tabs. 
 *It also verifies editing widget title and navigating back to the PAM card again.
 * 
 */

public class QIDM_175_LegacyComparisonAnalysisWidget extends TestBase{
	LoginTC login = null;
	boolean widgetDeleteFlag=true;
	@Test
	public void legacyComparisonAnalysisWidget() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			Utility.selectDashboard("Legacy Dashboards","AutoTestDashboard");
			
			goToAnalysisPage();
			goToPAMCard("ComparisonAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);

			// Search for Site'PAMTest_Capriata/Saiwa' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			// Search for Site'PAMTest_Herentals, Biscuits' and click on Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestHerentalsBiscuits"));
			getWebElementXpath("PAMTest_HerentalsBiscuits_Energy").click();
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			// Selected AutoTest_StoreHours from Site Schedule from DataBreakdown
			dataBreakDownSiteSchedule("Site Schedule","AutoTest_StoreHours");
			printLog("Selected AutoTestTemplate fom Site Schedule from DataBreakdown");
			
			// Select Site Metrics as 'AutoTest(Sq. Ft.)' from IndexBy and verify the legend
			indexBySelectMatrics("AutoTest(Sq. Ft.)");
			
			Utility.hideOptionLocationPanelForCards();

			//Holding legend to verify in widget
			ArrayList<String> legendArray=new ArrayList<String>();
			ArrayList<String> paiLegendArray=new ArrayList<String>();
			legendArray.add(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " "));
			legendArray.add(getWebElementActionXpath("ColumnLegendTwo").getText().replaceAll("\\n", " "));
			paiLegendArray.add(getWebElementActionXpath("PieChartLegendOne").getText().replaceAll("\\n", " "));
			paiLegendArray.add(getWebElementActionXpath("PieChartLegendTwo").getText().replaceAll("\\n", " "));
			paiLegendArray.add(getWebElementActionXpath("PieChartLegendThree").getText().replaceAll("\\n", " "));
			paiLegendArray.add(getWebElementActionXpath("PieChartLegendFour").getText().replaceAll("\\n", " "));
			
			//Table data tab
			getWebElementXpath("DataTableTab").click();
			
			ArrayList<String> tableHeaderArrayOne=new ArrayList<String>();
			int headerRowOne=d.findElements(By.xpath("//*[@id='tablehead']/tr[1]/th")).size();
			for(int i=1;i<=headerRowOne;i++) {
				tableHeaderArrayOne.add(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th["+i+"]").getText());
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
			
			File deleteFil=new File(Constant.DOWNLOAD_PATH+"\\comparisonAnalysis.csv");
			deleteFil.delete();
			Utility.moveTheScrollToThebottomOnRquest("100");
			
			d.switchTo().frame(getWebElementActionXpath("SwitchToLegacyWidgetFrame"));
			Thread.sleep(5000);
			
			//legend verification
			legendArray.contains(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " "));
			legendArray.contains(getWebElementActionXpath("ColumnLegendTwo").getText().replaceAll("\\n", " "));
			printLog("Legend verification completed for.");
			
			//Pai chart legend verification
			getWebElementActionXpath("WidgetPaiChart").click();
			paiLegendArray.contains(getWebElementActionXpath("PieChartLegendOne").getText().replaceAll("\\n", " "));
			paiLegendArray.contains(getWebElementActionXpath("PieChartLegendTwo").getText().replaceAll("\\n", " "));
			paiLegendArray.contains(getWebElementActionXpath("PieChartLegendThree").getText().replaceAll("\\n", " "));
			paiLegendArray.contains(getWebElementActionXpath("PieChartLegendFour").getText().replaceAll("\\n", " "));
			printLog("PAi chart Legend verification completed for.");
			
			//Widget hambgur menu to export csv
			getWebElementActionXpath_D("//*[@id='gemini']//i[@class='fa fa-navicon']").click();
			getWebElementActionXpath_D("//a[contains(text(),'Export Chart Data to CSV')]").click();
			Thread.sleep(5000);
			
			//getting CSV data to compare
			ArrayList<List<String>> csvData=Utility.returnNumberOfRowsFromcsv("comparisonAnalysis",4);
			
			//Comparing table data with csv with PAM
			ArrayList<String> csvDataToCompare = new ArrayList<String>(csvData.get((csvData.size()-1)));
			for(int i=0;i<tableDataArray.size();i++) {
				if(i==0) {
					Assert.assertTrue(d.getPageSource().contains(tableDataArray.get(i)));
				}else if(i!=6 && i!=0){
					Assert.assertTrue(d.getPageSource().contains(csvDataToCompare.get(i)));
					Assert.assertTrue(d.getPageSource().contains(tableDataArray.get(i)));
				}
			}
			
			//Comparing PAM table data with widget table data
			for(int i=1;i<=headerRowOne;i++) {
				Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[1]/th["+i+"]").getText(), tableHeaderArrayOne.get(i-1));
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
			getWebElementXpath_D("//input[@id='rtbWidgetTitle']").sendKeys("Comparison Analysis 1");
			Thread.sleep(1000);
			getWebElementActionXpath_D("//span[@id='lblsav' and text()='Save']").click();
			aJaxWait();
			Thread.sleep(5000);
			printLog("Edited the widget to Comparison Analysis 1.");
			
			d.switchTo().frame(getWebElementActionXpath("SwitchToLegacyWidgetFrame"));
			Assert.assertEquals("Comparison Analysis 1", getWebElementXpath_D("//*[text()='Comparison Analysis 1']").getText());
			
			//Going to pam page by clicking on >(Additional Details)
			Thread.sleep(2000);
			getWebElementActionCSS("AdditionalDetails").click();
			aJaxWait();
			Thread.sleep(5000);
			printLog("Inside PAM page now.");
			
			Assert.assertEquals("Comparison Analysis 1", getWebElementXpath_D("//*[text()='Comparison Analysis 1']").getText());
			
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
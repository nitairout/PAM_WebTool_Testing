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
 * This test verifies all the overlay options available on trend card.
 * Selects Markers, Alerts and Data Quality Flags options and 
 * verifies all options are displayed as expected on the chart and on the widget  
*/

public class QIDM_67_TrendAnalysisOverlayOptions extends TestBase {
	LoginTC login = null;

	@Test
	public void trendAnalysisOverlayOptions() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("External");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			goToAnalysisPage();

			// Selected Trend Analysis Card
			goToPAMCard("TrendAnalysisCard");

			// Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity", "Energy",standard);

			// Searched with sitename 'PAMTest_Capriata/Saiwa'
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			// Selected Energy measurement for site 'PAMTest_Capriata/Saiwa'
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();

			// Added fixed date range '3/10/2023' - '4/10/2023'
			addFixedDateRange("3/10/2023", "4/10/2023");
			refreshToLoadTheChart();

			Utility.moveTheScrollToTheTop();
			if(d.findElements(By.xpath("AlertMessage")).size()>0) {
				getWebElementActionXpath("AlertMessage").click();
			}
			
			// verify start and end date from chart
			Assert.assertEquals(getWebElementXpath("ChartStartDate").getText(), "3/10/2023");
			Assert.assertEquals(getWebElementXpath("ChartEndDate").getText(), "4/10/2023");
			// Verified the legend
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), "Energy kWh");
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Utility.moveTheScrollToTheDown();

			// Added the Overlay and Markers value as "5000"
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			getWebElementActionXpath("MarkersCheckbox").click();
			Thread.sleep(1000);
			getWebElementActionXpath("DemandFromMarker").click();
			Thread.sleep(1000);
			getWebElementActionXpath("NewMarkerValue").sendKeys("5000");
			Thread.sleep(1000);
			
			//Clicked on AlertCheckbox 
			getWebElementActionXpath("OverlayAlertsCheckbox").click(); 
			Thread.sleep(1000);
			
			//select data quality flags checkbox
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			aJaxWait();

			// verify new marker line displaying on the chart
			Assert.assertEquals(getWebElementActionXpath("MarkerLine").isDisplayed(), true);
			if(!checkBrowserHeadless) {
			Assert.assertTrue(getWebElementXpath_D("//*[@class='highcharts-axis-labels highcharts-yaxis-labels ']").getText().contains("5k") || getWebElementXpath_D("//*[@class='highcharts-axis-labels highcharts-yaxis-labels ']").getText().contains("5000"));
			}
			printLog("Verify the MarkerLine is displaying on the chart");
			
			// Clicked on Data tab and Verified Headers of the table
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			String expectedTableHeader1[] = {"Date/Time","Markers",testData.getProperty("PAMTestCapriataSaiwa")};
			String headerRow ;
			List<WebElement> tableHeader1 = d.findElements(By.xpath("//*[@id='tablehead']/tr[1]/th"));
			for(int i=1;i<=tableHeader1.size();i++){
				Utility.moveTheElement("//*[@id='tablehead']/tr[1]/th["+i+"]");
				headerRow = getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th["+i+"]").getText();
				Assert.assertEquals(headerRow, expectedTableHeader1[i-1]);
			}
			String expectedTableHeader2[] = {"New Marker",testData.getProperty("TableHeaderEnergykWh"),testData.getProperty("TableHeaderDataQuality")};
			List<WebElement> tableHeader2 = d.findElements(By.xpath("//*[@id='tablehead']/tr[2]/th"));
			for(int i=1;i<=tableHeader2.size();i++){
				Utility.moveTheElement("//*[@id='tablehead']/tr[2]/th["+i+"]");
				headerRow = getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th["+i+"]").getText();
				Assert.assertEquals(headerRow, expectedTableHeader2[i-1]);
			}
			//Verified the 4 rows data from the table
			String tableDataForMatrix = "3/10/2023~5,000~5,981~Valid|3/11/2023~5,000~3,618~Suspect|3/12/2023~5,000~10,122~Valid|3/13/2023~5,000~8,641~Valid";
			verifyTableDataWithExpected(tableDataForMatrix, 4, "QIDM_67_TrendAnalysisOverlayOptions");
			printLog("Table data and headers verification successfully!!");
			
			// Clicked on Alerts tab
			getWebElementXpath("AlertsTab").click();
			Thread.sleep(2000);			
			
			//Verified the headers of the Alert tab
			String expectedAlertTabHeader[] = {"Date/Time","Site","Submeter","Type","Subject","Threshold","Actual","UOM","Status"};
			String alertheaderRow ;
			List<WebElement> alerttableHeader = d.findElements(By.xpath("//*[@id='tablehead']/tr[1]/th"));
			for(int i=1;i<=alerttableHeader.size();i++){
				Utility.moveTheElement("//*[@id='tablehead']/tr[1]/th["+i+"]");
				alertheaderRow = getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th["+i+"]").getText();
				Assert.assertEquals(alertheaderRow, expectedAlertTabHeader[i-1]);
			}
			
			//Sort row data from the Alert tab
			if(!d.getCurrentUrl().contains("stg1")) {
				WebElement sortIcon = d.findElement(By.xpath("//*[@id='tablehead']/tr[1]/th[1]/span"));
				Actions actions = new Actions(d); 
				actions.moveToElement(sortIcon,1,1).moveToElement(sortIcon,1,1).click().build().perform();
				
				actions = new Actions(d); 
				actions.moveToElement(sortIcon,1,1).moveToElement(sortIcon,1,1).click().build().perform();
			}
			//Verified the first row data from the Alert tab
			String alertTabDataForMatrix = "4/5/2023 12:01 AM~PAMTest_Capriata/Saiwa~N/A~Daily Consumption~AutoTest_Energy Alert~5000~7410~kWh~Open";
			//String alertTabDataForMatrix = "4/5/23, 4:01 AM~PAMTest_Capriata/Saiwa~N/A~Daily Consumption~AutoTest_Energy Alert~5000~7410~kWh~Open";
			String[] gtAlertRowData=alertTabDataForMatrix.split("~");
			Utility.moveTheScrollToTheDown();
			for (int i = 1; i <= gtAlertRowData.length; i++) {
				Assert.assertEquals(getWebElementXpath_D("(//table/tbody[@class='k-table-tbody'])[2]/tr[1]/td[" + i + "]").getText(), gtAlertRowData[i-1]);
			}
			Utility.moveTheScrollToTheTop();
			printLog("Alert Tab table data and headers verification successfully!!");
			Utility.moveTheElementandClick("//*[contains(@class,'highcharts-label highcharts-point')][1]/*[contains(@style,'font-size:12px;font-weight:bold;color:white;fill:white')]");
			Thread.sleep(5000);
			
			//Verified the data from Alert popup
			String dateAndTime = d.findElement(By.xpath("//div[@class='panel-heading']/h4/small")).getText();
			Assert.assertEquals(dateAndTime, "Apr 5, 2023, 12:01:00 AM");			
			
			String siteName = d.findElement(By.xpath("(//div[@class='panel-heading']/h6)[1]")).getText();
			Assert.assertEquals(siteName, alertTabDataForMatrix.split("~")[1]);
			
			String exceptionType = d.findElement(By.xpath("(//div[@class='panel-body']//*[@class='centered']//tr[3]/td/h5)[1]")).getText();
			Assert.assertTrue(exceptionType.contains(alertTabDataForMatrix.split("~")[3]));
			
			String alertSubject = d.findElement(By.xpath("//div[@class='panel-heading']/h4/span")).getText();
			Assert.assertEquals(alertSubject, alertTabDataForMatrix.split("~")[4]);
			
			String threadshold = d.findElement(By.xpath("(//div[@class='panel-body']//*[@class='centered']//tr[3]/td/h5)[2]")).getText();
			Assert.assertTrue(threadshold.contains("5000"));
			
			String actual = d.findElement(By.xpath("//div[@class='panel-body']//*[@class='centered']//tr[2]/td/span[1]")).getText();
			//Assert.assertEquals(actual, alertTabDataForMatrix.split("~")[6]);
			Assert.assertEquals(actual, "7,410");
			
			String uom = d.findElement(By.xpath("//div[@class='panel-body']//*[@class='centered']//tr[2]/td/span[2]")).getText();
			Assert.assertEquals(uom, alertTabDataForMatrix.split("~")[7]);
			
			String status = d.findElement(By.xpath("(//div[@class='panel-heading']/h6)[2]")).getText();
			Assert.assertEquals(status, alertTabDataForMatrix.split("~")[8]);
			
			printLog("Verified Alert Tab data and Alert popup data");
			
			//Close the alert
			getWebElementActionXpath_D("//span[contains(text(),'Close') and @class='btn btn-default pull-right']").click();
			Thread.sleep(2000);
			
			WebElement icon = d.findElement(By.xpath("(//*[contains(@class,'highcharts-point')])[2]"));
			String toolTipText =null;
			Actions a = new Actions(d);
			a.moveToElement(icon,1,1).moveToElement(icon,1,1).build().perform();
			Thread.sleep(300);
			if (getWebElementXpath("tooltiptext") != null) {
				toolTipText = getWebElementXpath("tooltiptext").getText();
				printLog("toolTipText..."+toolTipText);
			}
			
			//Utility.moveTheElement("//i[@class='far fa-map-marker-alt']");
						
			//Verify Data Flags status as Suspect from tooltip
			//String toolTipText = getWebElementActionXpath_D("//div[@class='widget-tooltip']").getText();
			//System.out.println("toolTipText..."+toolTipText);
			Assert.assertTrue(toolTipText.contains("Status: Suspect"));
			
			// Clicked on Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//Verified Data Flags status as Suspect from Table data
			String dataQualityFlag = getWebElementActionXpath_D("//*[@id='tablebody']/tr[2]/td[3]").getText();
			Assert.assertEquals(dataQualityFlag, "Suspect");
			printLog("Verified Data Flags status as Suspect from both tooltip and Table data");
			
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(5000);
			
			//verify Marker line available on the chart
			Assert.assertEquals(getWebElementActionXpath_D("//*[contains(@class,'highcharts-plot-line-label')]").isDisplayed(), true);
			
			//Verify Data Quality Fags available on the chart
			List<WebElement> dataQualityFlags = d.findElements(By.xpath("//*[contains(@class,'highcharts-label highcharts-data-label highcharts-data-label-color-undefined highcharts-tracker')]/span"));
			Assert.assertEquals(dataQualityFlags.size(), 2);
			
			//Verify Alerts available on the chart
			List<WebElement> alerts = d.findElements(By.xpath("//*[contains(@class,'highcharts-label highcharts-point')]"));
			Assert.assertEquals(alerts.size(), 2);
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
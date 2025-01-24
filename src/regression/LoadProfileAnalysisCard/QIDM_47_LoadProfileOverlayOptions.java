package regression.LoadProfileAnalysisCard;
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
 * This test verifies all the overlay options available in Load Profile card , 
 * first it verifies on PAM chart and then pushes to widget and verifies the same.
 */

public class QIDM_47_LoadProfileOverlayOptions extends TestBase{
	LoginTC login = null;
	@Test
	public void loadProfileOverlayOptions() throws Throwable {
		try {

			login= LoginTC.getLoginTCObject();
			login.login("External");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			goToAnalysisPage();
			goToPAMCard("LoadProfileAnalysisCard");
			
			// Search for Site 'PAMTest_Trasformatore 1' and select Electric Measurement
			searchSiteInLocationList(testData.getProperty("PAMTestTrasformatore1"));
			getWebElementActionXpath("PAMTest_Trasformatore1_Energy").click();
			printLog("Searched for 'PAMTest_Trasformatore 1' and clicked on Electric Measurement");
			
			//Added the Fixed date
			addFixedDateRange("04/01/2023","04/30/2023");

			refreshToLoadTheChart();
			
			// Expand overlay
			getWebElementActionXpath("OverlayExpander").click();
			Thread.sleep(1000);
			//Checked site schedule
			getWebElementActionXpath("OverlaySiteScheduleCheckbox").click();
			getWebElementActionXpath_D("//div[contains(@class,'customOption')]//*[@textfield='TemplateName']").click();
			getWebElementActionXpath_D("//span[normalize-space()='AutoTest_StoreHours']").click();
			Thread.sleep(3000);
			
			//Check Marker
			getWebElementActionXpath("MarkersCheckbox").click();
			Thread.sleep(2000);
			getWebElementActionXpath("DemandFromMarker").click();
			Thread.sleep(1000);
			getWebElementActionXpath("NewMarkerValue").sendKeys("1500");
			Thread.sleep(1000);
			
			//Check Average profile
			getWebElementXpath("OverlayAverageProfileCheckbox").click();
			Thread.sleep(4000);
			
			//Clicked on AlertCheckbox 
			getWebElementActionXpath("OverlayAlertsCheckbox").click(); 
			Thread.sleep(2000);	
			
			//select data quality flags checkbox
			getWebElementActionXpath("OverlayDataQualityFlagsCheckBox").click();
			Thread.sleep(5000);
			printLog("DQ flag option selected");
			
			//Legend Verification
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText(),"PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kW");
			Assert.assertEquals(getWebElementActionXpath("LineLegendTwo").getText(),"PAMTest_Capriata/Saiwa \\...\\ PAMTest_Trasformatore 1 kW - Average");
			
			String closeLabel=getWebElementXpath_D("//loadprofile/kendo-splitter/kendo-splitter-pane[1]/div[2]/span[1]/span").getText();
			String openLabel=getWebElementXpath_D("//loadprofile/kendo-splitter/kendo-splitter-pane[1]/div[2]/span[2]/span").getText();
			Assert.assertEquals(closeLabel, "Close");
			Assert.assertEquals(openLabel, "Open");
			printLog("Verified the legends on the chart");
			
			// verify new marker line displaying on the chart
			Assert.assertEquals(getWebElementActionXpath("MarkerLine").isDisplayed(), true);
			Assert.assertTrue(d.findElement(By.xpath("//*[@class='highcharts-plot-line-label ']")).getText().contains("New Marker"));
			printLog("Verify the MarkerLine is displaying on the chart");
			
			// Clicked on Data tab and Verified Headers of the table
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			String expectedMarkerTableHeader1[] = {"Date/Time","Markers","PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Cabina 1 \\ PAMTest_Trasformatore 1"};
			String headerRow ;
			List<WebElement> markerTableHeader1 = d.findElements(By.xpath("//*[@id='tablehead']/tr[1]/th"));
			for(int i=1;i<=markerTableHeader1.size();i++){
				Utility.moveTheElement("//*[@id='tablehead']/tr[1]/th["+i+"]");
				headerRow = getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th["+i+"]").getText();
				Assert.assertEquals(headerRow, expectedMarkerTableHeader1[i-1]);
			}
			String expectedMarkerTableHeader2[] = {"New Marker","Demand (kW)",testData.getProperty("TableHeaderDataQuality"),"Demand (kW)",testData.getProperty("TableHeaderDataQuality")};
			List<WebElement> markerTableHeader2 = d.findElements(By.xpath("//*[@id='tablehead']/tr[2]/th"));
			for(int i=1;i<=markerTableHeader2.size();i++){
				Utility.moveTheElement("//*[@id='tablehead']/tr[2]/th["+i+"]");
				headerRow = getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th["+i+"]").getText();
				Assert.assertEquals(headerRow, expectedMarkerTableHeader2[i-1]);
			}
			//Verified the 4 rows data from the table
			//Verifying  'Average Profile', 'Marker' and 'Data Quality' data from Table tab
			String tableDataForMarker = "4/1/2023 12:15 AM~1,500~96.00~Valid~36.00~|4/1/2023 12:30 AM~1,500~84.00~Valid~60.00~|4/1/2023 12:45 AM~1,500~~Unknown~66.00~|4/1/2023 01:00 AM~1,500~~Suspect~60.00~";
			verifyTableDataWithExpected(tableDataForMarker, 4, "QIDM_47_LoadProfileOverlayOptions");
			printLog("Table data verification successfully for Marker, Average Profile and Data Quality !!");
			
			//Clicked on Alert Tab 
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
			
			//Verified the first row data from the Alert tab
			String alertTabDataForMatrix;
			if(!d.getCurrentUrl().contains("stg1")){
				WebElement sortIcon = d.findElement(By.xpath("//*[@id='tablehead']/tr[1]/th[1]/span"));
				Actions actions = new Actions(d); 
				actions.moveToElement(sortIcon,1,1).moveToElement(sortIcon,1,1).click().build().perform();
				Thread.sleep(2000);
			}
			if(d.getCurrentUrl().contains("tk1")|| d.getCurrentUrl().contains("stg1")) {
				alertTabDataForMatrix = "4/6/2023 11:15 AM~PAMTest_Capriata/Saiwa~PAMTest_Trasformatore 1~Interval Data~AutoTest_Demand Alert~100~96~kW~Open";
			}else{
				//Verified the first row data from the Alert tab
				alertTabDataForMatrix = "4/6/2023 10:00 AM~PAMTest_Capriata/Saiwa~PAMTest_Trasformatore 1~Interval Data~AutoTest_Demand Alert~100~96~kW~Open";
			}
			String[] gtAlertRowData=alertTabDataForMatrix.split("~");
			Utility.moveTheScrollToTheDown();
			for (int i = 1; i <= gtAlertRowData.length; i++) {
				Assert.assertEquals(getWebElementXpath_D("(//table/tbody[@class='k-table-tbody'])[2]/tr[1]/td[" + i + "]").getText(), gtAlertRowData[i-1]);
			}
			Utility.moveTheScrollToTheTop();
				
			//verifyTableDataWithExpected(alertTabDataForMatrix, 1, "QIDM_47_LoadProfileOverlayOptions");
			printLog("Alert Tab table data and headers verification successfully!!");
			
			int list = d.findElements(By.xpath("//*[contains(@class,'highcharts-label highcharts-point')]/*[contains(@style,'font-size:12px;font-weight:bold;color:white;fill:white')]")).size();
			WebElement highchart = null;
			if(list==3) {
				highchart= d.findElement(By.xpath("(//*[contains(@class,'highcharts-label highcharts-point')]/*[contains(@style,'font-size:12px;font-weight:bold;color:white;fill:white')])[3]"));
			}else{
				highchart= d.findElement(By.xpath("(//*[contains(@class,'highcharts-label highcharts-point')]/*[contains(@style,'font-size:12px;font-weight:bold;color:white;fill:white')])[2]"));
			}
			Actions a=new Actions(d);
			a.moveToElement(highchart,1,1).click().build().perform();
			aJaxWait();
			Thread.sleep(3000);
			//Verified the data from Alert popup
			String dateAndTime = d.findElement(By.xpath("//div[@class='panel-heading']/h4/small")).getText();
			if(d.getCurrentUrl().contains("tk1")|| d.getCurrentUrl().contains("stg1")) {
				Assert.assertEquals(dateAndTime, "Apr 6, 2023, 11:15:00 AM");
			}else{
				Assert.assertEquals(dateAndTime, "Apr 6, 2023, 10:00:00 AM");
			}
			//Assert.assertEquals(dateAndTime, "Apr 6, 2023 10:00:00 AM");			
			String siteName = d.findElement(By.xpath("(//div[@class='panel-heading']/h6)[1]")).getText();
			Assert.assertTrue(siteName.contains(alertTabDataForMatrix.split("~")[1]));
			String exceptionType = d.findElement(By.xpath("(//div[@class='panel-body']//*[@class='centered']//tr[3]/td/h5)[1]")).getText();
			Assert.assertTrue(exceptionType.contains(alertTabDataForMatrix.split("~")[3]));
			String alertSubject = d.findElement(By.xpath("//div[@class='panel-heading']/h4/span")).getText();
			Assert.assertEquals(alertSubject, alertTabDataForMatrix.split("~")[4]);
			String threadshold = d.findElement(By.xpath("(//div[@class='panel-body']//*[@class='centered']//tr[3]/td/h5)[2]")).getText();
			Assert.assertTrue(threadshold.contains("100"));
			String actual = d.findElement(By.xpath("//div[@class='panel-body']//*[@class='centered']//tr[2]/td/span[1]")).getText();
			//Assert.assertEquals(actual, alertTabDataForMatrix.split("~")[6]);
			Assert.assertEquals(actual, "96.00");
			String uom = d.findElement(By.xpath("//div[@class='panel-body']//*[@class='centered']//tr[2]/td/span[2]")).getText();
			Assert.assertEquals(uom, alertTabDataForMatrix.split("~")[7]);
			String status = d.findElement(By.xpath("(//div[@class='panel-heading']/h6)[2]")).getText();
			Assert.assertEquals(status, alertTabDataForMatrix.split("~")[8]);
			//Close the alert
			getWebElementActionXpath_D("//span[contains(text(),'Close') and @class='btn btn-default pull-right']").click();
			Thread.sleep(5000);
			printLog("Verified Alert popup data from chart");
			
			//Added the Fixed date
			addFixedDateRange("04/06/2023","04/06/2023");
			Utility.moveTheScrollToTheDown();
			// Clicked on Data tab and verify the data from the table
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			//Unselect AlertCheckbox 
			getWebElementActionXpath("OverlayAlertsCheckbox").click(); 
			aJaxWait();
			getWebElementActionXpath("OverlayAlertsCheckbox").click(); 
			aJaxWait();
			//Verified the 4 rows data from the table
			//Verifying  'Data Quality' data from Table tab
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[3]/th").getText(),"4/6/2023 12:45 AM");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[3]/td[3]").getText(),"Suspect");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[5]/th").getText(),"4/6/2023 01:15 AM");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablebody']/tr[5]/td[3]").getText(),"Suspect");
			printLog("Table data verification successfully for Data Quality !!");
			
			
			//Verified the chart having Data Quality Flags
			List<WebElement> dataFlags = d.findElements(By.xpath("//*[@class='highcharts-point']"));
			Assert.assertTrue(dataFlags.size()>0);
			printLog("Verified the chart having Data Quality Flags");
			
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(10000);
			
			//Enlarge the dashboard widget size
			//Utility.dragAndDrop(locators.getProperty("WidgetDragAndDropXpath"));
			Thread.sleep(10000);
			/*//Enlarge the dashboard widget size
			Utility.dragAndDrop(status);
			Actions builder = new Actions(d);			 
			WebElement from = d.findElement(By.xpath(locators.getProperty("WidgetDragAndDropXpath")));			
			builder.dragAndDropBy(from, 100,100).perform();
			Thread.sleep(10000);*/
			
			// Verify the Legends on the chart.
			Assert.assertEquals(getWebElementActionXpath("LineLegendOne").getText(),"PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Cabina 1 \\ PAMTest_Trasformatore 1 kW");
			Assert.assertEquals(getWebElementActionXpath("LineLegendTwo").getText(),"PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter \\ PAMTest_Electric \\ PAMTest_Cabina 1 \\ PAMTest_Trasformatore 1 kW - Average");
			printLog("Verify the Legends on the chart.");
			
			getWebElementActionXpath("WidgetTableTab").click();
			Thread.sleep(3000);
			markerTableHeader1 = d.findElements(By.xpath("//*[@id='tablehead']/tr[1]/th"));
			for(int i=1;i<=markerTableHeader1.size();i++){
				Utility.moveTheElement("//*[@id='tablehead']/tr[1]/th["+i+"]");
				headerRow = getWebElementXpath_D("//*[@id='tablehead']/tr[1]/th["+i+"]").getText();
				Assert.assertEquals(headerRow, expectedMarkerTableHeader1[i-1]);
			}
			String expectedMarkerTableHeader[] = {"New Marker","Demand (kW)",testData.getProperty("TableHeaderDataQuality"),"Demand (kW)",testData.getProperty("TableHeaderDataQuality")};
			markerTableHeader2 = d.findElements(By.xpath("//*[@id='tablehead']/tr[2]/th"));
			for(int i=1;i<=markerTableHeader2.size();i++){
				Utility.moveTheElement("//*[@id='tablehead']/tr[2]/th["+i+"]");
				headerRow = getWebElementXpath_D("//*[@id='tablehead']/tr[2]/th["+i+"]").getText();
				Assert.assertEquals(headerRow, expectedMarkerTableHeader[i-1]);
			}
			//Verified the 5 rows data from the table
			//Verifying  'Average Profile', 'Marker' and 'Data Quality' data from Table tab
			String tableDataForWidget = "4/6/23 12:15 AM~1,500~216.0~Valid~228.0~|4/6/23 12:30 AM~1,500~~Unknown~240.0~|4/6/23 12:45 AM~1,500~384.0~Suspect~198.0~|4/6/23 1:00 AM~1,500~~Unknown~224.0~|4/6/23 1:15 AM~1,500~396.0~Suspect~200.0~";
			verifyTableDataWithExpected(tableDataForWidget, 5, "QIDM_47_LoadProfileOverlayOptions");
			printLog("Table data verification successfully for Marker, Average Profile and Data Quality !!");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			login.logout();
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
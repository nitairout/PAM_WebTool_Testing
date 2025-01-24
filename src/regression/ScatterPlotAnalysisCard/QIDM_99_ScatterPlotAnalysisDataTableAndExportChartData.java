package regression.ScatterPlotAnalysisCard;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies export functionality for scatter plot. compares the data from table and exported csv file.
 */
public class QIDM_99_ScatterPlotAnalysisDataTableAndExportChartData extends TestBase{
	LoginTC login = null;
	@Test
	public void scatterPlotAnalysisDataTableAndExportChartData() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Scatter Plot Analysis Card page
			goToPAMCard("ScatterPlotCard");
			printLog("Navigated to Scatter plot Card page");

			//Selecting Energy and Temperature measurements for PAMTest_Capriata/Saiwa & PAMTest_Naperville, IL
			Utility.selectMultipleMeasurements("Electricity#Energy~standard|Weather#Temperature~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			getWebElementXpath("PAMTest_CapriataSaiwa_Weather").click();
			
			refreshToLoadTheChart();
			
			searchSiteInLocationList(testData.getProperty("PAMTestNapervilleIL"));
			getWebElementXpath("PAMTest_NapervilleIL_Energy").click();
			getWebElementXpath("PAMTest_NapervilleIL_Weather").click();
			
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();
			
			//verify x-axis value
			String xaxis = d.findElement(By.xpath("(//*[@class='highcharts-axis-title'])[1]")).getText();
			Assert.assertEquals(xaxis,"\u00B0F,\u00B0C");
			
			//Verify Y-axis measurement always first in the table
			String yaxis = d.findElement(By.xpath("//*[@id='tablehead']/tr[2]/th[1]")).getText();
			Assert.assertEquals(yaxis,testData.getProperty("TableHeaderEnergykWh"));
			
			//delete all files based on name so that we will get fresh file everytime
			Utility.deleteDownloadFiles("scatterPlotAnalysis",".csv");
			
			// Click on KabobMenu and select 'Export Chart Data to CSV'.
			kabobMenuOptions("Export Chart Data to CSV");			
			ArrayList<List<String>> csvDataToCheckTheMsg=null;
			List<String> firstRowData=null;
			DateFormat csvDatesFormats =null;
			DateFormat convertDt =null;
			String convertedDt=null;
			
			//Get first row and verify with table data
			csvDataToCheckTheMsg=Utility.returnNumberOfRowsFromcsv("scatterPlotAnalysis",4);
			firstRowData=csvDataToCheckTheMsg.get(2);
			csvDatesFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			convertDt = new SimpleDateFormat("M/d/yyyy");
			convertedDt=convertDt.format(csvDatesFormats.parse(firstRowData.get(0)));
			verifyText(convertedDt);
			verifyText(firstRowData.get(1));
			csvDatesFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			convertDt = new SimpleDateFormat("M/d/yyyy");
			convertedDt=convertDt.format(csvDatesFormats.parse(firstRowData.get(0)));
			verifyText(convertedDt);
			verifyText(firstRowData.get(3));
			printLog("Verified the data from Table and Excel file");
			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
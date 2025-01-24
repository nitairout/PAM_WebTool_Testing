package regression.TrendAnalysisCard;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 *This test verifies all index options available in Trend card. Selects each option one by one and loads chart for default Energy measurement 
 *and verifies chart is loaded as expected by verifying the index values on the chart and table data. 
.*/

public class QIDM_66_TrendAnalysisIndexByOptions extends TestBase{
	LoginTC login = null;
	
	@Test
	public void trendAnalysisIndexByOptions() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Added Energy measurement from Electricity commodity
			Utility.selectSingleMeasurement("Electricity","Energy",standard);
			
			// Searched with sitename 'PAMTest_Capriata/Saiwa'
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			// Selected Energy measurement for site 'PAMTest_Capriata/Saiwa'
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			// Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			//Getting the chart tool tip value
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate jan2023 = LocalDate.parse("01/01/2023", formatter);
			List<String> chartTooltipValues=getBarToolTip();
			int dateCounter=0;
			
			//verification the default by day interval
			for(String value:chartTooltipValues) {
				if(!value.contains(changeTheDateFormat("M/d/yyyy",jan2023.plusDays(dateCounter)))) 
					throw new Exception("Chart interval dates are not following By Day");
				dateCounter++;
				if(dateCounter==30)
					break;
			}
			
			//Selecting index by matrix as AutoTest(Sq. Ft.)
			indexBySelectMatrics("AutoTest(Sq. Ft.)");
			
			//Verification of legends and table data with tgable header
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh / Sq. Ft.");
			printLog("Verified legend on the chart is displayed for above selected measurment");
			
			//Click on Table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			// Verified the headers on the table data
			//Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), "Energy (kWh / Sq. Ft.)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Sq. Ft.");
			
			String tableDataForMatrix="1/1/2023~9,459~3.784~2,500|1/2/2023~11,854~4.742~2,500|1/3/2023~3,847~1.539~2,500|1/4/2023~2,246~0.8984~2,500";
			verifyTableDataWithExpected(tableDataForMatrix,4,"QIDM_66_TrendAnalysisIndexByOptions");
			printLog("Table data and headers verification successfully!!");
			
			
			String cddExpectedLegends="PAMTest_Capriata/Saiwa kWh / Sq. Ft. / CDD (\u00B0C)";
			String cddExpectedTableData="1/1/2023~9,459~3.784~2,500~0|1/2/2023~11,854~4.742~2,500~0|1/3/2023~3,847~1.539~2,500~0|1/4/2023~2,246~0.8984~2,500~0";
			
			
			// Select Weather and Daily Indexes as 'CDD (°C)' from IndexBy and verify the legend
			//new Select(getWebElementActionXpath("WeatherAndDailyIndex")).selectByVisibleText("CDD (°C)");
			indexBySelectIndex("CDD (\u00B0C)");
			
			chartTooltipValues=getBarToolTip();
			dateCounter=0;
			//verification the default by day interval
			for(String value:chartTooltipValues) {
				if(!value.contains(changeTheDateFormat("M/d/yyyy",jan2023.plusDays(dateCounter)))) 
					throw new Exception("Chart interval dates are not following By Day");
				dateCounter++;
				if(dateCounter==30)
					break;
			}
			
			//Click on Table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			// Verified the headers on the table data
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), "Energy(kWh / Sq. Ft.)");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[2]").getText(), "Energy (kWh / Sq. Ft. / CDD (\u00B0C))");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[3]").getText(), "Sq. Ft.");
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[4]").getText(), "CDD (\u00B0C)");
			
			//Verified the table data for the first 4 rows
			verifyTableDataWithExpected(cddExpectedTableData,4,"QIDM_66_TrendAnalysisIndexByOptions");
			printLog("Table data and headers verification successfully!!");
			//Verify the legend
			Assert.assertEquals(getWebElementXpath("ColumnLegendOne").getText(), cddExpectedLegends);
			
			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
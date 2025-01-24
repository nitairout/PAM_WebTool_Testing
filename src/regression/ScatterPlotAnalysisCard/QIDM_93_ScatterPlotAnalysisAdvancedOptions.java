package regression.ScatterPlotAnalysisCard;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test validates the Scatter Plot advanced options. 
 * Refine the series selections (select series for each axis) each measurement and verify the chart.
 */
public class QIDM_93_ScatterPlotAnalysisAdvancedOptions extends TestBase{
	LoginTC login = null;
	@Test
	public void scatterPlotAnalysisAdvancedOptions() throws Throwable {
		try {
			//Login into RA application with Internal user
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Scatter Plot Analysis Card page
			goToPAMCard("ScatterPlotCard");
			printLog("Navigated to Scatter plot Card page");

			//Selecting Energy measurements for site
			Utility.selectMultipleMeasurements("Electricity#Energy~standard*Demand~standard");
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			searchSiteInLocationList(testData.getProperty("PAMTestPowerhouse"));
			getWebElementXpath("PAMTest_Powerhouse_Energy").click();
			refreshToLoadTheChart();
			searchSiteInLocationList(testData.getProperty("PAMTestOvens"));
			getWebElementXpath("PAMTest_Ovens_Energy").click();
			printLog("Selected Electricity Energy & Demand with PAMTest_Capriata/Saiwa, PAMTest_Powerhouse,PAMTest_Oven");
			//refreshToLoadTheChart();
			//Added the Fixed date
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"),testData.getProperty("FixedDateEndJan312023"));
			
			refreshToLoadTheChart();
			
			//Scatter plot advance option  ellipse 
			getWebElementActionXpath_D("//div[@class='py-3']/span[@class='pull-right']").click();
			Thread.sleep(2000);
			
			//delete existing added comparisons
			int existingComparision=d.findElements(By.xpath("(//div[@class='sp-adv-opt-pair'])")).size();
			if(existingComparision>1) {
				for(int i=1;i<=existingComparision;i++) {
					getWebElementActionXpath_D("(//div[@class='sp-adv-opt-pair'])[1]/i").click();
				}
			}
			
			
			//PAMTest_Capriata/Saiwa comparison
			//Y axis
			getWebElementActionXpath_D("(//span[normalize-space()='Y-Axis']/following-sibling::kendo-dropdownlist)[3]").click();
			getWebElementActionXpath_D("//span[normalize-space()='Electricity - Energy'][contains(@class,'k-list-item-text')]").click();
			
			getWebElementActionXpath_D("(//span[normalize-space()='Y-Axis']/following-sibling::kendo-dropdownlist)[4]").click();
			//getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens'][contains(@class,'k-list-item-text')]").click();
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa'][contains(@class,'k-list-item-text')]").click();
			//X axis
			getWebElementActionXpath_D("(//span[normalize-space()='X-Axis']/following-sibling::kendo-dropdownlist)[2]").click();
			getWebElementActionXpath_D("//span[normalize-space()='Electricity - Demand'][contains(@class,'k-list-item-text')]").click();
			
			getWebElementActionXpath_D("(//span[normalize-space()='X-Axis']/following-sibling::kendo-dropdownlist)[3]").click();
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa'][contains(@class,'k-list-item-text')]").click();
			
			//Add it 
			getWebElementActionXpath_D("//button/i[@class='fas fa-plus']").click();
			Thread.sleep(2000);
			printLog("Aded PAMTest_Capriata/Saiwa comparison");
			
			//FOr PAMTest_Powerhouse comparison
			//Y axis
			getWebElementActionXpath_D("(//span[normalize-space()='Y-Axis']/following-sibling::kendo-dropdownlist)[3]").click();
			getWebElementActionXpath_D("//span[normalize-space()='Electricity - Energy'][contains(@class,'k-list-item-text')]").click();
			
			getWebElementActionXpath_D("(//span[normalize-space()='Y-Axis']/following-sibling::kendo-dropdownlist)[4]").click();
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse'][contains(@class,'k-list-item-text')]").click();
			
			//X axis
			getWebElementActionXpath_D("(//span[normalize-space()='X-Axis']/following-sibling::kendo-dropdownlist)[2]").click();
			getWebElementActionXpath_D("//span[normalize-space()='Electricity - Demand'][contains(@class,'k-list-item-text')]").click();
			
			getWebElementActionXpath_D("(//span[normalize-space()='X-Axis']/following-sibling::kendo-dropdownlist)[3]").click();
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse'][contains(@class,'k-list-item-text')]").click();
			
				//Add it 
			getWebElementActionXpath_D("//button/i[@class='fas fa-plus']").click();
			Thread.sleep(2000);
			printLog("Aded PAMTest_Powerhouse comparison");
			
			//FOr PAMTest_Ovens comparison
			//Y axis
			getWebElementActionXpath_D("(//span[normalize-space()='Y-Axis']/following-sibling::kendo-dropdownlist)[3]").click();
			getWebElementActionXpath_D("//span[normalize-space()='Electricity - Energy'][contains(@class,'k-list-item-text')]").click();
			
			getWebElementActionXpath_D("(//span[normalize-space()='Y-Axis']/following-sibling::kendo-dropdownlist)[4]").click();
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens'][contains(@class,'k-list-item-text')]").click();
			
			//X axis
			getWebElementActionXpath_D("(//span[normalize-space()='X-Axis']/following-sibling::kendo-dropdownlist)[2]").click();
			getWebElementActionXpath_D("//span[normalize-space()='Electricity - Demand'][contains(@class,'k-list-item-text')]").click();
			
			getWebElementActionXpath_D("(//span[normalize-space()='X-Axis']/following-sibling::kendo-dropdownlist)[3]").click();
			getWebElementActionXpath_D("//span[normalize-space()='PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens'][contains(@class,'k-list-item-text')]").click();
				
			//Add it 
			getWebElementActionXpath_D("//button/i[@class='fas fa-plus']").click();

			Thread.sleep(2000);
			printLog("Aded PAMTest_Ovens comparison");
			//ok to add all comparisons
			getWebElementActionXpath_D("//span[@class='btn btn-default btn-success'][text()='OK']").click();
			Thread.sleep(5000);
			printLog("Added all comparison");
			
			ArrayList<String> legends=new ArrayList<String>();
			legends.add("PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa kW");
			legends.add("PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa kW");
			legends.add("PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens kWh vs. PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens kW");
			legends.add("PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens kWh vs. PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Ovens kW");
			legends.add("PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse kWh vs. PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse kW");
			legends.add("PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse kWh vs. PAMTest_Capriata/Saiwa \\ PAMTest_Energy Balance \\ PAMTest_Powerhouse kW");
			
			int allLegendsInChart=d.findElements(By.xpath("(//div[@class='highcharts-legend']//span)")).size();
			if(allLegendsInChart==6) {
				for(int i=1;i<=allLegendsInChart;i++) {
					Assert.assertTrue(legends.contains(getWebElementActionXpath_D("(//div[@class='highcharts-legend']//span)["+i+"]").getText()));
				}
			}else {
				throw new Exception("Expected comparisons are 6 but actual is "+allLegendsInChart);
			}
			printLog("Verified all comparison");
			
			//hide liner trend
			getWebElementActionXpath_D("//div[@class='customOption in']//i[1]").click();
			Thread.sleep(10000);
			
			allLegendsInChart=d.findElements(By.xpath("(//div[@class='highcharts-legend']//span)")).size();
			if(allLegendsInChart==3) {
				for(int i=1;i<=allLegendsInChart;i++) {
					Assert.assertTrue(legends.contains(getWebElementActionXpath_D("(//div[@class='highcharts-legend']//span)["+i+"]").getText()));
				}
			}else {
				throw new Exception("Expected comparisons are 3 but actual is "+allLegendsInChart);
			}
			printLog("Verified after hide liner trend ");
			
			//Table data verification
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
			
			//String tableData = "1/1/2023~9,459~748.0 ~458.3~93.68~3,631~250.8|1/2/2023~11,854~1,372~5,566~322.8~4,799~493.3";
			String tableData = "1/1/2023~748.0~9,459~93.68~458.3~250.8~3,631|1/2/2023~1,372~11,854~322.8~5,566~493.3~4,799";
			verifyTableDataWithExpected(tableData, 2, "QIDM_93_ScatterPlotAnalysisAdvancedOptions");
			printLog("Table data verified successfully!");


			login.logout();
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	}

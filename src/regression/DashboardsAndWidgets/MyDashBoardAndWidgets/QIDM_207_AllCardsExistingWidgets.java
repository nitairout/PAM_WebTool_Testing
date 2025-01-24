package regression.DashboardsAndWidgets.MyDashBoardAndWidgets;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies exiting widgets from all cards are loaded and displaying data as expected as before.
 */
public class QIDM_207_AllCardsExistingWidgets extends TestBase{
	LoginTC login = null;
	@Test
	public void allCardsExistingWidgets() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'All Cards Existing Widgets' under 'AutoTest_Dashboards' folder
			Utility.goToPAMDashboard("AutoTest_Dashboards", "All Cards Existing Widgets");
			d.navigate().refresh();
			Thread.sleep(30000);//given more wait time to load the widgets
			// Calculate the number of widgets are available on the dashboard. it should be '5'
			List<WebElement> widgetNames = d.findElements(By.xpath("//div/gridster/gridster-item"));
			Assert.assertEquals(widgetNames.size(), 5);
			printLog("Number of widgets available on the dahboard are : " + widgetNames.size());
			
			String widgetNameToVerify="";
			int allVerification=0;
			for(int i=1;i<=widgetNames.size();i++) {
				widgetNameToVerify=getWebElementActionXpath_D("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//div[contains(@class,'title-label')]").getText();
				
				if(widgetNameToVerify.equalsIgnoreCase("Trend Widget")) {
					//call trend method
					trend_ExistingWidgetVerification(i);
					Assert.assertEquals(widgetNameToVerify, "Trend Widget");
					allVerification++;
				}
				else if(widgetNameToVerify.equalsIgnoreCase("Load Profile Widget")) {
					//call lp method
					lp_ExistingWidgetVerification(i);
					Assert.assertEquals(widgetNameToVerify, "Load Profile Widget");
					allVerification++;
				}
				else if(widgetNameToVerify.equalsIgnoreCase("Comparison Widget")) {
					//call comparison method
					comparison_ExistingWidgetVerification(i);
					Assert.assertEquals(widgetNameToVerify, "Comparison Widget");
					allVerification++;
				}
				else if(widgetNameToVerify.equalsIgnoreCase("CLP Widget")) {
					//call clp method
					clp_ExistingWidgetVerification(i);
					Assert.assertEquals(widgetNameToVerify, "CLP Widget");
					allVerification++;
				}
				else if(widgetNameToVerify.equalsIgnoreCase("Scatter Plot Widget")) {
					//call Scatter method
					scatter_ExistingWidgetVerification(i);
					Assert.assertEquals(widgetNameToVerify, "Scatter Plot Widget");
					allVerification++;
				}else {
					throw new Exception("There is no such widget present");
				}
			}

			//Checking if all widget verification success
			Assert.assertEquals(allVerification, widgetNames.size());
			
			login.logout();
			
		}catch(Throwable e) {
				e.printStackTrace();
				throw e;
			}
	}
	
	private void trend_ExistingWidgetVerification(int widgetIndex) throws Exception {
		try {
			printLog("************Trend widget verification started.************");
			String baseURL="//gridster/gridster-item["+widgetIndex+"]";
			Utility.moveTheElement(baseURL);
			//Click on Chart tab
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Chart')]").click();
			Thread.sleep(1000);
			printLog("Click on Chart tab on 'Trend Widget'");
			//Verify the date range on the chart tab
			Assert.assertEquals(getWebElementXpath_D("("+baseURL+"//*[@class='highcharts-title'])[1]").getText(), "1/1/23 - 1/31/23");
			printLog("Verified the date range as '1/1/23 - 1/31/23' on the chart.");
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath_D(baseURL+"//*[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			printLog("Verified the legend as 'PAMTest_Capriata/Saiwa kWh' on the trend chart.");
			//Verify the callout values
			String basicXpathTrendCalloutLabel = "("+baseURL+"//div[@class='item-container']/div[2]/div/span[@class='callout-title '])";
			String basicXpathTrendCalloutValue = "("+baseURL+"//div[@class='item-container']/div[2]/div/span[@class='bold callout-value'])";
			String basicXpathTrendCalloutUOM = "("+baseURL+"//div[@class='item-container']/div[2]/div/span[@class='callout-uom'])";
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutLabel+"[1]").getText(), "Total Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutValue+"[1]").getText(), "261,782");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutUOM+"[1]").getText(), "kWh");
			
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutLabel+"[2]").getText(), "Avg Daily Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutValue+"[2]").getText(), "8,445");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutUOM+"[2]").getText(), "kWh");
			
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutLabel+"[3]").getText(), "Max Daily Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutValue+"[3]").getText(), "25,452");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendCalloutUOM+"[3]").getText(), "kWh");
			printLog("Verify the callouts values on the 'Trend Widget' chart");
			
			//Click on Statistics tab on 'Trend Widget'
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Statistics')]").click();
			Thread.sleep(1000);
			printLog("Click on Statistics tab on 'Trend Widget'");
			
			//Verify the Statistics values
			String basicXpathTrendStatName = "("+baseURL+"//div[@class='title-container-div callout-title-new']/span[@class='callout-title1 '])";
			String basicXpathTrendStatData = "("+baseURL+"//span[@class='textFitted text-fitted-overflow'])";
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatName+"[6]").getText(),"Total Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatData+"[1]").getText(),"261,782");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatName+"[7]").getText(),"Avg Daily Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatData+"[2]").getText(),"8,445");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatName+"[8]").getText(),"Max Daily Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatData+"[3]").getText(),"25,452");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatName+"[9]").getText(),"Min Daily Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatData+"[4]").getText(),"1,878");
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatName+"[10]").getText(),"Raw Data");
			//Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatData+"[5]").getText(),"99.87%");//TK1
			Assert.assertEquals(getWebElementXpath_D(basicXpathTrendStatData+"[5]").getText(),"100.0%");
			printLog("Verify the Statistics values on the 'Trend Widget' chart");
			
			//Click on Table tab
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Table')]").click();
			Thread.sleep(1000);
			printLog("Click on Table tab on 'Trend Widget'");
			
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D(baseURL+"//*[@id='tablehead']/tr[1]/th[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D(baseURL+"//*[@id='tablehead']/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			printLog("Table header verification completed on 'Trend Widget'");
			
			//table data verification
			String basicXpathTableData = baseURL+"//*[@id='tablebody']";
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathTableData+"/tr[1]/th").getText(),"1/1/23");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathTableData+"/tr[1]/td[1]").getText(),"9,459");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathTableData+"/tr[2]/th").getText(),"1/2/23");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathTableData+"/tr[2]/td[1]").getText(),"11,854");
			printLog("Verification of Table Data is completed for 'Trend Widget'");
			//End verification on Trend Widget
			printLog("************Trend widget verification Ended.************");
		}catch(Exception e) {
			throw e;
		}
	}
	private void lp_ExistingWidgetVerification(int widgetIndex) throws Exception {
		try {
			printLog("************LP widget verification started.************");
			String baseURL="//gridster/gridster-item["+widgetIndex+"]";
			Utility.moveTheElement(baseURL);
			//Click on Chart tab
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Chart')]").click();
			//Verify the date range on the chart tab
			Assert.assertEquals(getWebElementXpath_D("("+baseURL+"//*[@class='highcharts-title'])[1]").getText(), "1/1/23, 12:00 AM - 2/1/23, 12:00 AM");
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath_D(baseURL+"//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText(), testData.getProperty("PAMTest_CapriataSaiwakWh"));
			Assert.assertEquals(getWebElementXpath_D(baseURL+"//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-1']/span").getText(), testData.getProperty("PAMTest_CapriataSaiwam3"));
			printLog("Verify the legends and chart date from the chart");
			
			//Verify the callout values
			String basicXpathLP = "("+baseURL+"//div[@class='item-container']/div[2]/div";
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='callout-title '])[1]").getText(), "Max Demand");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='bold callout-value'])[1]").getText(), "1,988");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='callout-uom'])[1]").getText(), "kW");
			
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='callout-title '])[2]").getText(), "Total Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='bold callout-value'])[2]").getText(), "261,782");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='callout-uom'])[2]").getText(), "kWh");
			
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='callout-title '])[3]").getText(), "Total Volume");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='bold callout-value'])[3]").getText(), "527,748");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLP+"/span[@class='callout-uom'])[3]").getText(), "m3");
			printLog("Verify the callouts values on the chart");
			
			//Click on Statistics tab on 'Load Profile Widget'
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Statistics')]").click();
			Thread.sleep(1000);
			printLog("Click on Statistics tab on 'Load Profile Widget'");
			//Verify the Statistics values
			String basicXpathLPStatsTitle = "("+baseURL+"//div[@class='title-container-div callout-title-new']/span[@class='callout-title1 '])";
			String basicXpathLPStatsValue = "("+baseURL+"//span[@class='textFitted text-fitted-overflow'])";
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[17]").getText(),"Max Demand");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[1]").getText(),"1,988");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[18]").getText(),"Total Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[2]").getText(),"261,782");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[19]").getText(),"Total Volume");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[3]").getText(),"527,748");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[20]").getText(),"Avg Demand");
			//Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[4]").getText(),"352.3");//TK1
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[4]").getText(),"351.9");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[21]").getText(),"Load Factor");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[5]").getText(),"17.70%");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[22]").getText(),"Avg Volume");
			//Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[6]").getText(),"177.6");//TK1
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[6]").getText(),"177.4");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[23]").getText(),"Avg Daily Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[7]").getText(),"8,445");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[24]").getText(),"Max Volume");
			//Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[8]").getText(),"547.0");//TK1
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[8]").getText(),"305.0");
			
			getWebElementXpath_D("("+baseURL+"//i[@class='fas fa-angle-right cursor-pointer'])[2]").click();
			Thread.sleep(1000);
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[21]").getText(),"Max Daily Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[1]").getText(),"25,452");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[22]").getText(),"Min Volume");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[2]").getText(),"31.00");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[23]").getText(),"Min Daily Usage");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[3]").getText(),"1,878");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsTitle+"[24]").getText(),"Min Demand");
			Assert.assertEquals(getWebElementXpath_D(basicXpathLPStatsValue+"[4]").getText(),"32.00");
			printLog("Click on Statistics Values on 'Load Profile Widget'");
			
			//Click on Table tab
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Table')]").click();
			Thread.sleep(1000);
			printLog("Click on Table tab on 'Load Profile Widget'");
			//Verify Table Header verification
			String basicXpathLPHeader = baseURL+"//*[@id='tablehead']";
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPHeader+"/tr[1]/th[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPHeader+"/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPHeader+"/tr[2]/th[2]").getText(), "Volume (m3)");
			printLog("Table header verification completed on 'Load Profile Widget'");
			
			//Verify Table data verification
			String basicXpathLPTableData = baseURL+"//*[@id='tablebody']";
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPTableData+"/tr[1]/th").getText(),"1/1/23 12:15 AM");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPTableData+"/tr[1]/td[1]").getText(),"91.00");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPTableData+"/tr[1]/td[2]").getText(),"35.00");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPTableData+"/tr[2]/th").getText(),"1/1/23 12:30 AM");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPTableData+"/tr[2]/td[1]").getText(),"88.00");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathLPTableData+"/tr[2]/td[2]").getText(),"32.00");
			printLog("Verification of Table Data is completed for 'Load Profile Widget'");
			//End verification on Load Profile Widget
			printLog("************LP widget verification end.************");
		}catch(Exception e) {
			throw e;
		}
	}
	private void clp_ExistingWidgetVerification(int widgetIndex) throws Exception {
		try {
			printLog("************CLP widget verification started.************");
			Utility.moveTheElement("//gridster/gridster-item["+widgetIndex+"]");
			Thread.sleep(2000);
			//Start verification on CLP Widget
			//Verify the date range on the chart
			Assert.assertEquals(getWebElementActionXpath_D("//gridster/gridster-item["+widgetIndex+"]//div[@id='calendarChart']/div[@class='header-month']").getText(), "January 2023");
			//Verify the legend on the chart
			Assert.assertTrue(getWebElementActionXpath_D("//gridster/gridster-item["+widgetIndex+"]//div[@class='legend-container']/span[1]/span").getText().replaceAll("\\n", " ").contains(testData.getProperty("PAMTest_CapriataSaiwakWh")));
			printLog("Verify the legend 'PAMTest_Capriata/Saiwa kWh' and date range on the chart");
			//End verification on CLP Widget
			printLog("************CLP widget verification end.************");
		
		}catch(Exception e) {
			throw e;
		}
	}
	private void comparison_ExistingWidgetVerification(int widgetIndex) throws Exception {
		try {
			printLog("************Comparison widget verification started.************");
			String baseURL="//gridster/gridster-item["+widgetIndex+"]";
			Utility.moveTheElement(baseURL);
			//Click on Chart tab
			getWebElementActionXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Chart')]").click();
			//Verify the date range on the chart tab
			Assert.assertEquals(getWebElementActionXpath_D("("+baseURL+"//*[@class='highcharts-title'])[1]").getText(), "1/1/23 - 1/31/23");
			//Verified the sites on the chart
			String[] sitesList = getWebElementXpath_D(baseURL+"//*[@class='highcharts-axis-labels highcharts-xaxis-labels']").getText().split("\\n");
			Assert.assertEquals(sitesList[0], testData.getProperty("PAMTestNapervilleIL"));
			Assert.assertEquals(sitesList[1], testData.getProperty("PAMTestCapriataSaiwa"));
			//Verify the legend on the chart
			Assert.assertEquals(getWebElementXpath_D(baseURL+"//*[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span").getText(), "Energy kWh");
			printLog("Verify the legend date from chart tab ");
			
			//Click on Pie tab
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Pie')]").click();
			Thread.sleep(1000);
			//Verify the date range on the Pie tab
			Assert.assertEquals(getWebElementXpath_D("("+baseURL+"//*[@class='highcharts-title'])[2]").getText(), "1/1/23 - 1/31/23");
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath_D(baseURL+"//*[@class='highcharts-legend-item highcharts-pie-series highcharts-color-0']/span").getText().replaceAll("\\n", " "), "PAMTest_Naperville, IL Energy kWh");
			Assert.assertEquals(getWebElementXpath_D(baseURL+"//*[@class='highcharts-legend-item highcharts-pie-series highcharts-color-1']/span").getText().replaceAll("\\n", " "), testData.getProperty("PAMTest_CapriataSaiwaEnergykWh"));
			printLog("Verified the pie chart data including legends.");
			
			//Click on Table tab
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Table')]").click();
			Thread.sleep(1000);
			printLog("Click on Table tab");
			//table data verification - //generic method is not using here because the column count is 5 instead of 3
			String basicXpathComparison = baseURL+"//*[@id='tablebody']";
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[1]/th").getText(),testData.getProperty("PAMTestNapervilleIL"));
			//Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[1]/td[1]").getText(),"2,055,937");//TK1
			//Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[1]/td[2]").getText(),"88.71");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[1]/td[1]").getText(),"2,082,185");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[1]/td[2]").getText(),"88.83");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[2]/th").getText(),testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[2]/td[1]").getText(),"261,782");
			//Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[2]/td[2]").getText(),"11.29");//TK1
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[2]/td[2]").getText(),"11.17");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[3]/th").getText(),"Total");
			//Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[3]/td[1]").getText(),"2,317,719");//TK1
			//Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[3]/td[2]").getText(),"100.00");//TK1
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[3]/td[1]").getText(),"2,343,967");			
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathComparison+"/tr[3]/td[2]").getText(),"100.0");
			printLog("Verification of Table Data is for Comparison Widget has completed!!");
			//End verification on Comparison Widget
			printLog("************Comparison widget verification end.************");
		}catch(Exception e) {
			throw e;
		}
	}
	private void scatter_ExistingWidgetVerification(int widgetIndex) throws Exception {
		try {
			printLog("************Scatter widget verification started.************");
			String baseURL="//gridster/gridster-item["+widgetIndex+"]";
			//Click on Chart tab
			Utility.moveTheElement(baseURL);
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Chart')]").click();
			Thread.sleep(1000);
			printLog("Click on Chart tab on the 'Scatter Plot Widget'");
			//Verify the date range on the chart tab
			Assert.assertEquals(getWebElementXpath_D("("+baseURL+"//*[@class='highcharts-title'])[1]").getText(), "1/1/23 - 1/31/23");
			printLog("Verified the date range as '1/1/23 - 1/31/23' on the chart.");
			//Verify the chart legend
			Assert.assertEquals(getWebElementXpath_D(baseURL+"//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			Assert.assertEquals(getWebElementXpath_D(baseURL+"//*[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/span").getText(), "PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa \u00B0C");
			printLog("Verified the legends as 'PAMTest_Capriata/Saiwa kWh vs. PAMTest_Capriata/Saiwa °C' on the chart.");
			
			//Click on Table tab on 'Scatter Plot Widget'
			getWebElementXpath_D(baseURL+"//div[@class='wc-wrapper']//a[contains(text(),'Table')]").click();
			Thread.sleep(1000);
			printLog("Click on Table tab on the 'Scatter Plot Widget'");
			
			//Table Header verification
			String basicXpathScatterTableHeader = baseURL+"//*[@id='tablehead']";
			String basicXpathScatterTableData = baseURL+"//*[@id='tablebody']";
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableHeader+"/tr[1]/th[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableHeader+"/tr[2]/th[1]").getText(), testData.getProperty("TableHeaderEnergykWh"));
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableHeader+"/tr[2]/th[2]").getText(), "Temperature (\u00B0C)");
			printLog("Table header verification completed on 'Scatter Plot Widget'");
			
			//table data verification
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableData+"/tr[1]/th").getText(),"1/1/23");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableData+"/tr[1]/td[1]").getText(),"9,459");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableData+"/tr[1]/td[2]").getText(),"14.28");
			
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableData+"/tr[2]/th").getText(),"1/2/23");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableData+"/tr[2]/td[1]").getText(),"11,854");
			Assert.assertEquals(getWebElementActionXpath_D(basicXpathScatterTableData+"/tr[2]/td[2]").getText(),"15.00");
			printLog("Verification of Table Data is completed for 'Scatter Plot Widget'");
			printLog("************Scatter widget verification End.************");
		}catch(Exception e) {
			throw e;
		}
	}
	

}

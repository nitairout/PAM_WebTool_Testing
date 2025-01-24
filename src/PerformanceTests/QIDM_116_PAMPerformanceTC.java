package PerformanceTests;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

/*
 * This test verifies load time of saved analysis is expected after each release and there are no changes in the performance after the release
 */
public class QIDM_116_PAMPerformanceTC extends TestBase {

	LoginTC login = null;
	static HashMap<String, Throwable> failedReasonForAnalysis = null;
	static Map<String, String> passedReason = null;
	String dashboardname = "PAMPerformanceDashboard";
	StopWatch stopwatch = null;
	String envName = null;
	int actualTime = 0;
	int expectedTime = 0;

	@Test
	public void pamPerformanceTest() throws Throwable {
		try {
			List<List<String>> excelFinalList = new ArrayList<>();
			
			String[] performancePages = { "DashboardPage", "CapriataSaiwa_Last12MonthsWithComparison", "CapriataSaiwa_Last30daysWithAvgProfile",
									      "CapriataSaiwa_MainMeter_LastMonth", "CapriataSaiwa_EnergyBalance_12MonthsDataBreakdown",
									      "ScatterPlot_2Sites_withOneYearData"};

			failedReasonForAnalysis = new HashMap<String, Throwable>();
			passedReason = new HashMap<String, String>();
			
			
			
			if ("https://core.stg1.resourceadvisor.schneider-electric.com/login.aspx".contains(Dto.getURl())) {
				envName = "Stage ";
			} else if ("https://resourceadvisor.schneider-electric.com".contains(Dto.getURl())) {
				envName = "Prod ";
			}	
			
			login = LoginTC.getLoginTCObject();
			d.get(Dto.getURl());
			waitForPageLoad();
			Thread.sleep(5000);
			
			expectedTime = 12;

			// Added the user id
			explicitWait_CSS("#raUserName");
			getWebElementCSS_D("#raUserName").clear();
			getWebElementCSS_D("#raUserName").sendKeys("pautouser101");
			Thread.sleep(1000);
			// Click on login button
			d.findElement(By.cssSelector("#btnLogin")).click();
			Thread.sleep(3000);

			// Added the pwd
			getWebElementCSS_D("#raPassword").clear();
			getWebElementCSS_D("#raPassword").sendKeys("Performanceuser1!@#");

			// Click on login button
			d.findElement(By.cssSelector("#btnLogin")).click();
			
			
			
			
			for (int i = 0; i < performancePages.length; i++) {
			switch (performancePages[i]) {
			case "DashboardPage":
				try {
					excelFinalList.add(dashboardPageVerification());
				} catch (Throwable e) {
				}
				break;
			case "CapriataSaiwa_Last12MonthsWithComparison":
				try {
					excelFinalList.add(CapriataSaiwa_Last12MonthsWithComparison());
				} catch (Throwable e) {
				}
				break;
			case "CapriataSaiwa_Last30daysWithAvgProfile":
				try {
					excelFinalList.add(CapriataSaiwa_Last30daysWithAvgProfile());
				} catch (Throwable e) {
				}
				break;
			case "CapriataSaiwa_MainMeter_LastMonth":
				try {
					excelFinalList.add(CapriataSaiwa_MainMeter_LastMonth());
				} catch (Throwable e) {
				}
				break;
			case "CapriataSaiwa_EnergyBalance_12MonthsDataBreakdown":
				try {
					excelFinalList.add(CapriataSaiwa_EnergyBalance_12MonthsDataBreakdown());
				} catch (Throwable e) {
				}
				break;
				
			case "ScatterPlot_2Sites_withOneYearData":
				try {
					excelFinalList.add(ScatterPlot_2Sites_withOneYearData());
				} catch (Throwable e) {
				}
				break;
				
			default:
				printLog("No cards are selected");

			}
			}

			for (String key : passedReason.keySet()) {
				printLog("The " + key + " was Passed");
			}

			for (String key : failedReasonForAnalysis.keySet()) {
				printLog("The " + key + " was Failed");
			}
			try (XSSFWorkbook workbook = new XSSFWorkbook()) {
				XSSFSheet sheet = workbook.createSheet("Performance Sheet");
				
				CellStyle cellStyleForPassed = workbook.createCellStyle(); 
				XSSFFont fontForSuccess = workbook.createFont();
				 fontForSuccess.setFontHeightInPoints((short)11);
				 fontForSuccess.setColor(IndexedColors.GREEN.getIndex());
				 cellStyleForPassed.setFont(fontForSuccess);
				 
				 XSSFCellStyle mainTCStyle = workbook.createCellStyle();
				 mainTCStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(237, 146, 35),new DefaultIndexedColorMap()));
				 mainTCStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				 XSSFFont mainTCFont = workbook.createFont();
				 mainTCFont.setBold(true);
				 mainTCStyle.setFont(mainTCFont);
				 
				 
				 XSSFCellStyle headedStyle = workbook.createCellStyle();
				 headedStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(65, 89, 130),new DefaultIndexedColorMap()));
				 headedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				 headedStyle.setAlignment(HorizontalAlignment.CENTER);
				 headedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				 XSSFFont headedFont = workbook.createFont();
				 headedFont.setColor(IndexedColors.WHITE.getIndex());
				 headedFont.setBold(true);
				 headedStyle.setFont(headedFont);
				 
				 CellStyle cellStyleForFailed = workbook.createCellStyle();        
				 XSSFFont fontForFailure = workbook.createFont();
				 fontForFailure.setFontHeightInPoints((short)11);
				 fontForFailure.setColor(IndexedColors.RED.getIndex());
				 cellStyleForFailed.setFont(fontForFailure);
				
				 Row row0 = sheet.createRow(0);
					Cell cell0 = row0.createCell(0); 
					cell0.setCellValue("Page Name");
					Cell cell1 = row0.createCell(1); 
					cell1.setCellValue("EnvironmentName");
					Cell cell2 = row0.createCell(2); 
					cell2.setCellValue("Expected Time to load (Sec)");
					Cell cell3 = row0.createCell(3); 
					cell3.setCellValue("Actual Time to load (Sec)");
					Cell cell4 = row0.createCell(4); 
					cell4.setCellValue("Status");
					int rowNum = 1;
					for (List<String> list : excelFinalList) {
						Row row = sheet.createRow(rowNum++); 
						int cellNum = 0; 
						for (String val : list) {
							Cell cell = row.createCell(cellNum++);
							cell.setCellValue(val); 
							sheet.autoSizeColumn(cellNum);
						}
					}
					
					sheet.autoSizeColumn(0);
					sheet.autoSizeColumn(1);
					sheet.autoSizeColumn(2);
					sheet.autoSizeColumn(3);
					sheet.autoSizeColumn(4);
					
					File f = new File(System.getProperty("user.dir") + "\\Screenshots\\", "PerformanceTest.xlsx");
					FileOutputStream out = new FileOutputStream(f);
					workbook.write(out);
					out.flush();
					out.close();
			}
				login.logout();

				// Redirect to exception block if any verification failure
				if (failedReasonForAnalysis.size() > 0) {
					throw new Throwable("PAMPerformanceTest failed");
				}
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}finally {
			new SendingPerformanceEmail().sendEmail("PerformanceTest.xlsx");
		}
	}

	private List<String> dashboardPageVerification() throws Throwable {
		List<String> dashboard = new ArrayList<String>();
		try {
			
			dashboard.add("DashboardPage");
			dashboard.add(envName);	
			
			stopwatch = new StopWatch();
			stopwatch.start();
			new WebDriverWait(d, Duration.ofSeconds(600)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//gridster/gridster-item[2]//*[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span")));
			Assert.assertTrue(getWebElementXpath_D("//gridster/gridster-item[1]//div[@class='legend-container']/span[1]/span").getText().replaceAll("\\n", " ").contains("PAMTest_Capriata/Saiwa \\ PAMTest_Main Meter kW"));
			Assert.assertTrue(getWebElementXpath_D("//gridster/gridster-item[2]//*[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span").getText().replaceAll("\\n", " ").contains(testData.getProperty("PAMTestCapriataSaiwa")));
			Assert.assertTrue(getWebElementXpath_D("//gridster/gridster-item[3]//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText().replaceAll("\\n", " ").contains(testData.getProperty("PAMTestCapriataSaiwa")));
			Assert.assertTrue(getWebElementXpath_D("//gridster/gridster-item[4]//*[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span").getText().replaceAll("\\n", " ").contains("PAMTest_Herentals, Biscuits"));
			Assert.assertTrue(getWebElementXpath_D("//gridster/gridster-item[5]//*[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span").getText().replaceAll("\\n", " ").contains(testData.getProperty("PAMTestCapriataSaiwa")));
			stopwatch.stop();
			
			actualTime = (int) stopwatch.getTime(TimeUnit.SECONDS);
			
			// Calculate the number of widgets are available on the dashboard. it should be '5'
			List<WebElement> widgetNames = d.findElements(By.xpath("//div/gridster/gridster-item"));
			Assert.assertEquals(widgetNames.size(), 5);
			printLog("Number of widgets available on the dahboard are : " + widgetNames.size());
			Assert.assertTrue(actualTime <= expectedTime);
			
			dashboard.add(Integer.toString(expectedTime));
			dashboard.add(Integer.toString(actualTime));
			dashboard.add("Passed");
			passedReason.put("DashboardPage", "Passed");
		} catch (Throwable e) {
			dashboard.add(Integer.toString(expectedTime));
			dashboard.add(Integer.toString(actualTime));
			dashboard.add("Failed");
			e.printStackTrace();
			failedReasonForAnalysis.put("DashboardPage", e);
		}
		
		return dashboard;
	}
	private List<String> CapriataSaiwa_Last12MonthsWithComparison() throws Throwable {
		List<String> last12Months = new ArrayList<>();
		try {
			last12Months.add("CapriataSaiwa_Last12MonthsWithComparisonCard");
			last12Months.add(envName);
			goToAnalysisPage();

			// expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);

			// Click on saved analysis card
			getWebElementActionXpath_D("//div[contains(text(),'Capriata/Saiwa_Last12MonthsWithComparison')]").click();

			stopwatch = new StopWatch();
			stopwatch.start();
			new WebDriverWait(d, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span")));
			stopwatch.stop();
			
			expectedTime = 12;
			actualTime = (int) stopwatch.getTime(TimeUnit.SECONDS);
			Assert.assertTrue(actualTime <= expectedTime);
			
			last12Months.add(Integer.toString(expectedTime));
			last12Months.add(Integer.toString(actualTime));
			last12Months.add("Passed");
			passedReason.put("Last12MonthsWithComparison", "Passed");
		} catch (Throwable e) {
			last12Months.add(Integer.toString(expectedTime));
			last12Months.add(Integer.toString(actualTime));
			last12Months.add("Failed");
			failedReasonForAnalysis.put("Last12MonthsWithComparison", e);
			e.printStackTrace();
		}
		return last12Months;
	}
	
	private List<String> CapriataSaiwa_Last30daysWithAvgProfile() throws Throwable {
		List<String> last30Days = new ArrayList<>();
		try {
			
			last30Days.add("Last30daysWithAvgProfile");
			last30Days.add(envName);
			goToAnalysisPage();

			// expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);

			// Click on saved analysis card
			getWebElementActionXpath_D("//div[contains(text(),'Capriata/Saiwa_Last30daysWithAvgProfile')]").click();

			stopwatch = new StopWatch();
			stopwatch.start();
			new WebDriverWait(d, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='highcharts-legend-item highcharts-line-series highcharts-color-undefined highcharts-series-0']/span")));
			stopwatch.stop();
			
			expectedTime = 23;			
			actualTime = (int) stopwatch.getTime(TimeUnit.SECONDS);
			Assert.assertTrue(actualTime <= expectedTime);
			
			last30Days.add(Integer.toString(expectedTime));
			last30Days.add(Integer.toString(actualTime));
			last30Days.add("Passed");
			passedReason.put("Last30daysWithAvgProfile", "Passed");
		} catch (Throwable e) {
			last30Days.add(Integer.toString(expectedTime));
			last30Days.add(Integer.toString(actualTime));
			last30Days.add("Failed");
			failedReasonForAnalysis.put("Last30daysWithAvgProfile", e);
			e.printStackTrace();
		}
		return last30Days;
	}
	
	private List<String> CapriataSaiwa_MainMeter_LastMonth() throws Throwable {
		List<String> lastMonth = new ArrayList<>();
		try {
			lastMonth.add("MainMeter_LastMonth");
			lastMonth.add(envName);
			goToAnalysisPage();

			// expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);
			
			// Click on saved analysis card
			getWebElementActionXpath_D("//div[contains(text(),'Capriata/Saiwa\\Main Meter_LastMonth')]").click();

			stopwatch = new StopWatch();
			stopwatch.start();
			new WebDriverWait(d, Duration.ofSeconds(30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[@ng-bind-html='serie.name'])[1]")));
			stopwatch.stop();
			
			expectedTime = 5;
			actualTime = (int) stopwatch.getTime(TimeUnit.SECONDS);
			Assert.assertTrue(actualTime <= expectedTime);
			
			lastMonth.add(Integer.toString(expectedTime));
			lastMonth.add(Integer.toString(actualTime));
			lastMonth.add("Passed");
			passedReason.put("MainMeter_LastMonth", "Passed");
		} catch (Throwable e) {
			lastMonth.add(Integer.toString(expectedTime));
			lastMonth.add(Integer.toString(actualTime));
			lastMonth.add("Failed");
			failedReasonForAnalysis.put("MainMeter_LastMonth", e);
			e.printStackTrace();
		}
		return lastMonth;		
	}
	
	private List<String> CapriataSaiwa_EnergyBalance_12MonthsDataBreakdown() throws Throwable {
		List<String> dataBreakdown = new ArrayList<>();
		try {
			dataBreakdown.add("EnergyBalance_12MonthsDataBreakdown");
			dataBreakdown.add(envName);
			goToAnalysisPage();

			// expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);
			
			// Click on saved analysis card
			getWebElementActionXpath_D("//div[contains(text(),'Capriata/Saiwa\\EnergyBalance_12MonthsDataBreakdown')]").click();

			stopwatch = new StopWatch();
			stopwatch.start();
			new WebDriverWait(d, Duration.ofSeconds(120)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='highcharts-legend-item highcharts-column-series highcharts-color-undefined highcharts-series-0']/span")));
			stopwatch.stop();
			
			expectedTime = 120;
			actualTime = (int) stopwatch.getTime(TimeUnit.SECONDS);
			Assert.assertTrue(actualTime <= expectedTime);
			
			dataBreakdown.add(Integer.toString(expectedTime));
			dataBreakdown.add(Integer.toString(actualTime));
			dataBreakdown.add("Passed");
			passedReason.put("EnergyBalance_12MonthsDataBreakdown", "Passed");
		} catch (Throwable e) {
			dataBreakdown.add(Integer.toString(expectedTime));
			dataBreakdown.add(Integer.toString(actualTime));
			dataBreakdown.add("Failed");
			e.printStackTrace();
			failedReasonForAnalysis.put("EnergyBalance_12MonthsDataBreakdown", e);
		}
		return dataBreakdown;		
	}
	
	private List<String> ScatterPlot_2Sites_withOneYearData() throws Throwable {
		List<String> scatterPlot = new ArrayList<>();
		try {
			scatterPlot.add("ScatterPlot_2Sites_withOneYearData");
			scatterPlot.add(envName);
			goToAnalysisPage();

			// expand the saved analysis in PAM page
			getWebElementActionXpath("ExpandTheSavedAnalysisArrow").click();
			Thread.sleep(3000);
			
			// Click on saved analysis card
			getWebElementActionXpath_D("//div[contains(text(),'ScatterPlot_2Sites_withOneYearData')]").click();

			stopwatch = new StopWatch();
			stopwatch.start();
			new WebDriverWait(d, Duration.ofSeconds(120)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='highcharts-legend-item highcharts-scatter-series highcharts-color-undefined highcharts-series-1']/span")));
			stopwatch.stop();
			
			expectedTime = 15;
			actualTime = (int) stopwatch.getTime(TimeUnit.SECONDS);
			Assert.assertTrue(actualTime <= expectedTime);
			
			scatterPlot.add(Integer.toString(expectedTime));
			scatterPlot.add(Integer.toString(actualTime));
			scatterPlot.add("Passed");
			passedReason.put("ScatterPlot_2Sites_withOneYearData", "Passed");
		} catch (Throwable e) {
			scatterPlot.add(Integer.toString(expectedTime));
			scatterPlot.add(Integer.toString(actualTime));
			scatterPlot.add("Failed");
			failedReasonForAnalysis.put("ScatterPlot_2Sites_withOneYearData", e);
			e.printStackTrace();
		}
		return scatterPlot;
	}	
}
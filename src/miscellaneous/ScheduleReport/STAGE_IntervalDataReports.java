package miscellaneous.ScheduleReport;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import common.TestBase;
import listener.NoRetry;

public class STAGE_IntervalDataReports extends TestBase{
@Test(retryAnalyzer = NoRetry.class)
	public void stageIntervalDataDailyReports() throws Exception
	{
			try {
				new UsedForScheduleReport().openBrowserForScheduleReport();
			d.navigate().to(testData.getProperty("gmailurl"));
			Thread.sleep(5000);
			getWebElementXpath_D(locators.getProperty("gmailusername")).clear();
			getWebElementXpath_D(locators.getProperty("gmailusername")).sendKeys("pamprodtestreports1@gmail.com");
			getWebElementActionXpath_D(locators.getProperty("gmailnext")).click();
			Thread.sleep(5000);
			getWebElementXpath_D("//input[contains(@name,'Passwd')]").clear();	
			getWebElementXpath_D("//input[contains(@name,'Passwd')]").sendKeys("Schneider!@#");
			//getWebElementActionXpath_D("//span[contains(text(),'Next')]").click();
			getWebElementXpath_D("//input[contains(@name,'Passwd')]").sendKeys(Keys.ENTER);
			Thread.sleep(5000);
			
			//calculating the time zone for schedule generate time
			String timeDeclaration=" 06:00 EST";
			LocalDate localDate = LocalDate.now(ZoneId.of("America/New_York"));
			//String executionTodayDate=changeTheDateFormat("M/d/yyyy",localDate.minusDays(1));
			String executionTodayDate=changeTheDateFormat("M/d/yyyy",localDate);
			String executionDate=executionTodayDate;
			
			getWebElementActionXpath_D("//a[contains(text(),'PAMReports')]").click();
			Thread.sleep(8000);
			String emails="";
			int reportCounter=0;
			for(int i=1;i<3;i++) {
				emails=getWebElementXpath_D("(//div[contains(@class,'bGI nH')])[2]/div[@class='ae4 UI']//table/tbody/tr["+i+"]//span[@class='y2']").getText();
				if(emails.contains("Auto_Stage")) {
					reportCounter++;
						if(emails.contains(executionTodayDate)) {
							//d.findElement(By.xpath("//table/tbody/tr["+i+"]/td[5]/div/div/div/span/span[contains(@class,'bqe')]")).click();
							getWebElementActionXpath_D("((//div[contains(@class,'bGI nH')])[2]//td[@class='xY a4W']//div[@class='xT'])["+i+"]//span[@class='bqe']").click();
							stageScheduleVerification(executionDate);
							Thread.sleep(6000);
							d.navigate().back();
							Thread.sleep(8000);
						}else {
							printLog("There is no stage report generated today.;");
							throw new Exception("Seems like there is no stage report generated today");
						}
				}
		}
			if(reportCounter==0) {
				throw new Exception("Seems like there is no stage report generated today");
			}
			
			//d.findElement(By.xpath("//table/tbody/tr[1]/td[5]/div/div/div/span/span[contains(@class,'bqe')]")).click();
			}catch(Throwable t) {
				t.printStackTrace();
				throw t;
			}
	}
	public void stageScheduleVerification(String executionDate) throws Exception {
		try {
				printLog("*******Stage schedule report verification started!!*******");
				printLog("");	
				//number of excel attachment verification
				int numberOfExcel=d.findElements(By.xpath(locators.getProperty("checkattachement"))).size();
				if(numberOfExcel==2) {
					printLog("Expected excel attachment is 2 and actual is "+numberOfExcel);
				}else {
					printLog("Expected excel attachment is 2 and actual is "+numberOfExcel);
					throw new Exception("Expected excel attachment is 2 and actual is "+numberOfExcel);
				}
				//number of image verification
				int numberOfImage=d.findElements(By.xpath("//div[2]/table/tbody/tr[3]/td//img")).size();
				if(numberOfImage==2) {
					printLog("Expected image is 2 and actual is "+numberOfImage);
				}else {
					printLog("Expected image is 2 and actual is "+numberOfImage);
					throw new Exception("Expected image is 2 and actual is "+numberOfImage);
				}
				//number of table verification
				int numberOfTable=d.findElements(By.xpath("//table/tbody/tr[3]/td/div[1]/div/table")).size();
				if(numberOfTable==1) {
					printLog("Expected table is 1 and actual is "+numberOfTable);
				}else {
					printLog("Expected table is 1 and actual is "+numberOfTable);
					throw new Exception("Expected table is 1 and actual is "+numberOfTable);
				}
				
				List<WebElement> allReports=d.findElements(By.xpath("//div[2]/table/tbody"));
				int numberOfReports=allReports.size();
				
				//all report name declared
				List<String> reportHeadersArray = Arrays.asList("Auto_Stage_CLPThisMonth","Auto_Stage_TrendLast30days","Auto_Stage_ComparisonYTD","Auto_Stage_LPLast7Days","Auto_Stage_ScatterPlotLast30Days");
				
				if(numberOfReports<=5) {
				//printLog("Expected reports are 5 and actual is "+numberOfReports);
				printLog("                                                                                   ");
				for(int i=1;i<=numberOfReports;i++) {
				//report header verification
				String reportHeader=getWebElementActionXpath_D("(//div[2]/table/tbody)["+i+"]/tr[2]/td").getText();
				if(reportHeadersArray.contains(reportHeader)) {
					printLog("Report Header  :  "+reportHeader);
				}else {
					throw new Exception(reportHeader+" is not generated.");
				}
				
				//data error verification
				String data=getWebElementActionXpath_D("((//div[2]/table/tbody)/tr[3]/td)["+i+"]").getText();
				String errorMsgOne="The system failed to generate the requested report.";
				String errorMsgTwo="The report timed out in PAM. Please contact your client manager.";
				if(data.contains(errorMsgOne) || data.contains(errorMsgTwo)) {
					throw new Exception(reportHeader+" Report does not have any data.");
				}
				
				//generate date varification
				//List<WebElement> reportGenerationDate=d.findElements(By.xpath("(((//div[2]/table/tbody)/tr[3]/td)["+i+"]//table/tbody)[1]/tr"));
				//for(int j=1;j<=reportGenerationDate.size();j++) {
					String generateDate=getWebElementActionXpath_D("(((//div[2]/table/tbody)/tr[3]/td)["+i+"]//table/tbody)[1]/tr[1]/td[2]").getText();
					String reportSchedule=getWebElementActionXpath_D("(((//div[2]/table/tbody)/tr[3]/td)["+i+"]//table/tbody)[1]/tr[2]/td[2]").getText();
					String createdBy=getWebElementActionXpath_D("(((//div[2]/table/tbody)/tr[3]/td)["+i+"]//table/tbody)[1]/tr[3]/td[2]").getText();
					if(generateDate.contains(executionDate)) {
						printLog("Generate Date : "+generateDate);
						printLog("Report Scheduled : "+reportSchedule);
						printLog("Created By : "+createdBy);
					}else {
						throw new Exception("Today is "+executionDate+" and the latest report is not generatred on "+generateDate);
					}
					
					
				//}
				/*
				//Analysis type Location
				//	List<WebElement> analysisTypeLocation=d.findElements(By.xpath("(((//div[2]/table/tbody)/tr[3]/td)["+i+"]//table/tbody)[3]/tr"));
				//for(int k=1;k<analysisTypeLocation.size();k++) {
					String analysisName=getWebElementActionXpath_D("(((//div[2]/table/tbody)/tr[3]/td)["+i+"]//table/tbody)[3]/tr[1]/td[2]").getText();
					String type=getWebElementActionXpath_D("(((//div[2]/table/tbody)/tr[3]/td)["+i+"]//table/tbody)[3]/tr[2]/td[2]").getText();
					String location=getWebElementActionXpath_D("(((//div[2]/table/tbody)/tr[3]/td)["+i+"]//table/tbody)[3]/tr[3]/td[2]").getText();
					printLog("analysisName : "+analysisName);
					printLog("type : "+type);
					printLog("location : "+location);
				//	}
				
				*/
					Thread.sleep(4000);
					printLog("");
				}
				}else {
				throw new Exception("Expected report is 5 and Actual is "+numberOfReports);
				}
				printLog("*******Stage schedule report verification ended!!*******");
				printLog("");	
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	
}

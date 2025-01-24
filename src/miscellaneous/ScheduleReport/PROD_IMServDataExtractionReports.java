package miscellaneous.ScheduleReport;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

import common.Constant;
import common.TestBase;
import common.Utility;
import listener.NoRetry;

public class PROD_IMServDataExtractionReports extends TestBase{

	@Test(retryAnalyzer = NoRetry.class)
	public void dateExtractionReport() throws Throwable
	{
			/*Expected values*/
			List<String> expectedReportHeader=new ArrayList<String>();
			expectedReportHeader.add("Prod_IMServ_IntervalData_Daily");
			expectedReportHeader.add("Prod_IMServ_RegisterReadings_Daily");
			//Uncomment for UAT. Commented on 29-Aug-2023 as per Lakshmi request
			//expectedReportHeader.add("UAT_IMServ_IntervalData_Daily");
			//expectedReportHeader.add("UAT_IMServ_RegisterReadings_Daily");
			
			
			Map<String,String> passedProdIntervalData=new HashMap<String,String>();
			Map<String,String> failedProdIntervalData=new HashMap<String,String>();
			
			Map<String,String> passedProdRegisterReadings=new HashMap<String,String>();
			Map<String,String> failedProdRegisterReadings=new HashMap<String,String>();
			
			Map<String,String> passedUATIntervalData=new HashMap<String,String>();
			Map<String,String> failedUATIntervalData=new HashMap<String,String>();
			
			Map<String,String> passedUATRegisterReadings=new HashMap<String,String>();
			Map<String,String> failedUATRegisterReadings=new HashMap<String,String>();
			
			

			String failedMessage="";
			String failedDownloadLinkMessage="";
			String numberOfReportFailureMsg="";
			String receivedDateAndTime="";
			boolean loginSuccess=false;

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
			Thread.sleep(3000);
			//getObject_Xpath_D("//div[@id='passwordNext']//button").click();
			Thread.sleep(8000);
			//Reporter.log("Logged in successfully!!");
			LocalDate localDate = LocalDate.now(ZoneId.of("America/New_York"));
			//String executionTodayDate=changeTheDateFormat("dd/MM/yyyy",localDate.minusDays(1));
			String executionTodayDate=changeTheDateFormat("dd/MM/yyyy",localDate);
			String executionDate=executionTodayDate;
			//System.out.println("Todays date is::"+executionDate);
			loginSuccess=true;
			
			getWebElementActionXpath_D("//a[contains(text(),'IMServDER')]").click();
			Thread.sleep(8000);
			//All unread email
			int  allUnreadEmails=d.findElements(By.xpath("(//div[contains(@class,'bGI nH')])[2]//td[@class='xY a4W']//span[@class='bqe']")).size();
			
			//if(allUnreadEmails!=4) {
			if(allUnreadEmails!=2) {
				numberOfReportFailureMsg="Expected prod report should be 2 but actual report generated is "+allUnreadEmails;
			}
			
			String reportName="";
			String generatedDate="";
			for(int i=1;i<=allUnreadEmails;i++) {
				
				failedDownloadLinkMessage="";
				
				getWebElementActionXpath_D("(((//div[contains(@class,'bGI nH')])[2]//td[@class='xY a4W']//div[@class='xT'])//span[@class='bqe'])[1]").click();
				Thread.sleep(8000);
				
				reportName=getWebElementActionXpath_D("//div[@class='a3s aiL ']/div[1]/table/tbody/tr[2]/td").getText();
				generatedDate=getWebElementActionXpath_D("//div[@id=':9z' or @class='a3s aiL ']//table/tbody/tr[3]/td/p[1]").getText();
				//receivedDateAndTime=getObject_Xpath_D("//div/table[@class='cf gJ']//span[@class='g3']").getText();
				receivedDateAndTime=getWebElementXpath_D("//div/table[@class='cf gJ']//span[@class='g3']").getAttribute("title");
				
				//converting PST to EST time zone to print in report
				String amPm=receivedDateAndTime.substring((receivedDateAndTime.length()-2),receivedDateAndTime.length());
				receivedDateAndTime= receivedDateAndTime.substring(0, (receivedDateAndTime.length()-3));
				receivedDateAndTime=receivedDateAndTime+" "+amPm;
				String DATE_FORMAT = "MMM d, yyyy, h:mm a";
		        LocalDateTime ldt = LocalDateTime.parse(receivedDateAndTime, DateTimeFormatter.ofPattern(DATE_FORMAT));
		        ZoneId pstTime = ZoneId.of("America/Los_Angeles");
		        ZonedDateTime pstZonedDateTime = ldt.atZone(pstTime);
		        ZoneId newYokZoneId = ZoneId.of("America/New_York");
		        ZonedDateTime nyDateTime = pstZonedDateTime.withZoneSameInstant(newYokZoneId);
		        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
		        receivedDateAndTime= format.format(nyDateTime)+" EST";
				
				//Delete existing files
				deleteFile(reportName);
				Thread.sleep(6000);
				
				//click to download the attachment
				int checkIfDownlaedTheFile=0;
				if(d.findElements(By.xpath("//div[@id=':9z' or @class='a3s aiL ']//table/tbody/tr[3]//a[contains(text(),'click here')]")).size()>0) {
					getWebElementActionXpath_D("//div[@id=':9z' or @class='a3s aiL ']//table/tbody/tr[3]//a[contains(text(),'click here')]").click();
					Thread.sleep(5000);
					 checkIfDownlaedTheFile=1;
				}else {
					checkIfDownlaedTheFile=0;
					failedDownloadLinkMessage=" Download link is not present.";
				}
				
				String failedCSVDataMessage="";
				String passedCSVDataMessage="";
				CSVReader reader =null;
				if(checkIfDownlaedTheFile==1) {
					reader = new CSVReader(new FileReader(Constant.DOWNLOAD_PATH+"\\"+Utility.latestDownLoadedFileFromDownload(reportName,"csv")));
					if(reportName.equalsIgnoreCase("Prod_IMServ_RegisterReadings_Daily") || reportName.equalsIgnoreCase("UAT_IMServ_RegisterReadings_Daily")) {
						int dataCount=0;
						List<String[]> li=reader.readAll();
						Iterator<String[]> allCsvFile= li.iterator();
						String csvValue[]=null;
						while(allCsvFile.hasNext()){
							csvValue=null;
							csvValue=allCsvFile.next();
							if(csvValue[6].length()>0) {
								dataCount++;
							}
						}
						if(dataCount>=2)
							passedCSVDataMessage="Downloaded file has some reading data";
						else
							failedCSVDataMessage=" Doesnot have any reading data"; 
					}else if(reportName.equalsIgnoreCase("Prod_IMServ_IntervalData_Daily") || reportName.equalsIgnoreCase("UAT_IMServ_IntervalData_Daily")) {
						List<String[]> li=reader.readAll();
						Iterator<String[]> allCsvFile= li.iterator();
						String val[]=null;
						int colCount=0;
						int rowCount=0;
						br:
						while(allCsvFile.hasNext()){
							val=allCsvFile.next();
							rowCount++;
							colCount=0;
							for(int j=0;j<=10;j++) {
								colCount++;
								if(val[j].length()==0) {
									char column=(char)(colCount+64);
									failedCSVDataMessage=" Interval datas are missing in row "+rowCount+" and column "+column; 
									break br;
								}else {
									passedCSVDataMessage="Downloaded file has interval data";
								}
							}
					}
					
					}else {
						Reporter.log("No reports are generated!!!");
					}
					
				}	
				
				
				if(expectedReportHeader.contains(reportName)) {
					//if report name is present then only we are checking the generated date
					if(generatedDate.contains(executionDate)) {
						if(reportName.equalsIgnoreCase("Prod_IMServ_IntervalData_Daily")) {
							
							passedProdIntervalData.put(reportName, reportName +" is received on "+receivedDateAndTime);
							
							
							//if download data is present then adding here forlog
							if(!passedCSVDataMessage.equals("")) {
								passedProdIntervalData.put("CSVDatePresent", passedCSVDataMessage);
							}else {
								failedProdIntervalData.put(reportName,(reportName+ " : "+(failedDownloadLinkMessage.equals("") ? failedCSVDataMessage : failedDownloadLinkMessage)));
							}
							
						}else if(reportName.equalsIgnoreCase("Prod_IMServ_RegisterReadings_Daily")) {
	
							passedProdRegisterReadings.put(reportName, reportName +" is received on "+receivedDateAndTime);
							
							//if download data is present then adding here forlog
							if(!passedCSVDataMessage.equals("")) {
								passedProdRegisterReadings.put("CSVDatePresent", passedCSVDataMessage);
							}else {
								failedProdRegisterReadings.put(reportName,(reportName+ " : "+(failedDownloadLinkMessage.equals("") ? failedCSVDataMessage : failedDownloadLinkMessage)));
							}
							
						}else if(reportName.equalsIgnoreCase("UAT_IMServ_IntervalData_Daily")) {
							
							passedUATIntervalData.put(reportName, reportName +" is received on "+receivedDateAndTime);
							
							//if download data is present then adding here forlog
							if(!passedCSVDataMessage.equals("")) {
								passedUATIntervalData.put("CSVDatePresent", passedCSVDataMessage);
							}else {
								failedUATIntervalData.put(reportName,(reportName+ " : "+(failedDownloadLinkMessage.equals("") ? failedCSVDataMessage : failedDownloadLinkMessage)));
							}
						}else if(reportName.equalsIgnoreCase("UAT_IMServ_RegisterReadings_Daily")) {
	
							passedUATRegisterReadings.put(reportName, reportName +" is received on "+receivedDateAndTime);
							
							//if download data is present then adding here forlog
							if(!passedCSVDataMessage.equals("")) {
								passedUATRegisterReadings.put("CSVDatePresent", passedCSVDataMessage);
							}else {
								failedUATRegisterReadings.put(reportName,(reportName+ " : "+(failedDownloadLinkMessage.equals("") ? failedCSVDataMessage : failedDownloadLinkMessage)));
							}
							
						}else {
							failedMessage="There is some issue while while adding pased log message on generater date block!!";
						}
					}else {
						if(reportName.equalsIgnoreCase("Prod_IMServ_IntervalData_Daily")) {
							String message=reportName +" is not generated on "+executionDate+" successfully \r\n"+failedProdIntervalData.get(reportName);
							failedProdIntervalData.put(reportName, message);
						}else if(reportName.equalsIgnoreCase("Prod_IMServ_RegisterReadings_Daily")) {
							String message=reportName +" is not generated on "+executionDate+" successfully \r\n"+failedProdIntervalData.get(reportName);
							failedProdRegisterReadings.put(reportName, message);
						}else if(reportName.equalsIgnoreCase("UAT_IMServ_IntervalData_Daily")) {
							String message=reportName +" is not generated on "+executionDate+" successfully \r\n"+failedProdIntervalData.get(reportName);
							failedUATIntervalData.put(reportName, message);
						}else if(reportName.equalsIgnoreCase("UAT_IMServ_RegisterReadings_Daily")) {
							String message=reportName +" is not generated on "+executionDate+" successfully \r\n"+failedProdIntervalData.get(reportName);
							failedUATRegisterReadings.put(reportName, message);
						}else {
							failedMessage="There is some issue while while adding failed log message on generater date block!!";
						}
					}
				}else {
					failedMessage=failedMessage+"~ "+reportName+" is not generated today ";
				}
				
				Thread.sleep(5000);
				d.navigate().back();
				Thread.sleep(8000);
				
			}
			
			
			if(passedProdIntervalData.size()>0) {
				Reporter.log("***********************Prod_IMServ_IntervalData_Daily***************************");
				for (Map.Entry<String, String> entry : passedProdIntervalData.entrySet()) {
				  //  Object key = entry.getKey();
					Reporter.log(entry.getValue());
				}
				Reporter.log("\r\n");
			}
			
			if(passedProdRegisterReadings.size()>0) {
				Reporter.log("***********************Prod_IMServ_RegisterReadings_Daily***************************");
				for (Map.Entry<String, String> entry : passedProdRegisterReadings.entrySet()) {
				  //  Object key = entry.getKey();
					Reporter.log(entry.getValue());
				}
				Reporter.log("\r\n");
			}
			
			if(passedUATIntervalData.size()>0) {
				Reporter.log("***********************UAT_IMServ_IntervalData_Daily***************************");
				for (Map.Entry<String, String> entry : passedUATIntervalData.entrySet()) {
				  //  Object key = entry.getKey();
					Reporter.log(entry.getValue());
				}
				Reporter.log("\r\n");
			}
			
			if(passedUATRegisterReadings.size()>0) {
				Reporter.log("***********************UAT_IMServ_RegisterReadings_Daily***************************");
				for (Map.Entry<String, String> entry : passedUATRegisterReadings.entrySet()) {
				  //  Object key = entry.getKey();
					Reporter.log(entry.getValue());
				}
				Reporter.log("\r\n");
			}
			
			//Uncomment for UAT. Commented on 29-Aug-2023 as per Lakshmi request
			//if(failedProdIntervalData.size()>0 || failedProdRegisterReadings.size()>0 || failedUATIntervalData.size()>0 || failedUATRegisterReadings.size()>0 || numberOfReportFailureMsg.length()!=0) {
			if(failedProdIntervalData.size()>0 || failedProdRegisterReadings.size()>0 || numberOfReportFailureMsg.length()!=0) {
				throw new Exception();
			}
				
			}catch(Throwable t) {
				if(loginSuccess) {
					Reporter.log(numberOfReportFailureMsg==null ? "" : numberOfReportFailureMsg);
					Reporter.log(failedProdIntervalData.get("Prod_IMServ_IntervalData_Daily")==null ? "" : failedProdIntervalData.get("Prod_IMServ_IntervalData_Daily"));
					Reporter.log(failedProdRegisterReadings.get("Prod_IMServ_RegisterReadings_Daily")==null ? "" : failedProdRegisterReadings.get("Prod_IMServ_RegisterReadings_Daily"));
					//Uncomment for UAT. Commented on 29-Aug-2023 as per Lakshmi request
					//Reporter.log(failedUATIntervalData.get("UAT_IMServ_IntervalData_Daily")==null ? "" : failedUATIntervalData.get("UAT_IMServ_IntervalData_Daily"));
					//Reporter.log(failedUATRegisterReadings.get("UAT_IMServ_RegisterReadings_Daily")==null ? "" : failedUATRegisterReadings.get("UAT_IMServ_RegisterReadings_Daily"));
				}else {
					Reporter.log("Login failed.");
				}
				t.printStackTrace();
				throw t;
				
			}
	}

	
	private void deleteFile(String fileNameStartWith) {
		File downloadDirectory = new File(Constant.DOWNLOAD_PATH);   
		File[] files=downloadDirectory.listFiles();
		for (File f : files) {
		//	System.out.println(f.getName());
		    if (f.getName().startsWith(fileNameStartWith) && f.getName().endsWith(".csv")) {
		        f.delete();
		    }
		}
	}
	
}

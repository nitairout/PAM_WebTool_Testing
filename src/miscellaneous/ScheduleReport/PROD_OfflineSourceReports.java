package miscellaneous.ScheduleReport;
import java.time.LocalDate;
import java.time.ZoneId;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import common.TestBase;
import listener.NoRetry;

public class PROD_OfflineSourceReports extends TestBase{
	@Test(retryAnalyzer = NoRetry.class)
	public void Prod_offLineDailyReports() throws Exception
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
				/*
				String nameOfTheFolder=getWebElementActionXpath_D("(//div[contains(@class,'aio aip')]/span[contains(@class,'nU')])[2]/a").getText();
				String numberOfUnreadEmails=getWebElementActionXpath_D("(//div[contains(@class,'aio aip')])[2]/div").getText();
				Reporter.log("Nme of the folder is "+nameOfTheFolder+" and number of unread emails are "+numberOfUnreadEmails);
				*/
				System.out.println();
				getWebElementActionXpath_D("//a[contains(text(),'OfflineSource')]").click();
				Thread.sleep(8000);
			
			//calculating the time zone for schedule generate time
			String timeDeclaration=" 06:00 UTC";
			LocalDate localDate = LocalDate.now(ZoneId.of("UTC"));
			//String executionTodayDate=changeTheDateFormat("M/d/yyyy",localDate.minusDays(1));
			String executionTodayDate=changeTheDateFormat("MM/dd/yyyy",localDate);
			String executionDate=executionTodayDate;
			
			for(int i=1;i<3;i++) {
				String emails=getWebElementXpath_D("(((//div[contains(@class,'bGI nH')])[2]//td[@class='xY a4W']//div[@class='xT'])//span[@class='bqe'])["+i+"]").getText();
				if(emails.contains("Prod_OfflineSourceReport")  ) {
						if(emails.contains(executionTodayDate)) {
							
							//Clicked on unread emails
							getWebElementActionXpath_D("(((//div[contains(@class,'bGI nH')])[2]//td[@class='xY a4W']//div[@class='xT'])//span[@class='bqe'])["+i+"]").click();
							Thread.sleep(8000);
							
							int numberOfExcel=d.findElements(By.xpath(locators.getProperty("checkattachement"))).size();
							if(numberOfExcel==1) {
								Reporter.log("Expected attachment is 1, but actual is "+numberOfExcel);
							}else {
								Assert.fail("Expected attachment is 1, but actual is "+numberOfExcel);
							}
							//Checking the report heading
							String reportHeading=getWebElementActionXpath_D("(//div[contains(@class,'aiL')]//table)[1]/tbody/tr[2]/td").getText();
							if(reportHeading.contains("Offline Source Report")) {
								Reporter.log("Report heading is "+reportHeading);
							}else {
								Assert.fail("Report heading is "+reportHeading);
							}
							
							for(int k=1;i<3;i++) {
								int totalTD=d.findElements(By.xpath("(//div[contains(@class,'aiL')]//table)[2]/tbody/tr["+k+"]/td")).size();
								for(int j=1;j<=totalTD;j++) {
									Assert.assertNotNull(getWebElementActionXpath_D("(//div[contains(@class,'aiL')]//table)[2]/tbody/tr["+k+"]/td["+j+"]").getText());
								}
							}
							
						}else {
							printLog("There is no Prod Offline report generated today.;");
							throw new Exception("Seems like there is no Prod Offline report generated today");
						}
						break;
				}
			}
			
			
			/*
			
			//All unread email
			int  allUnreadEmails=d.findElements(By.xpath("(//div[contains(@class,'bGI nH')])[2]//td[@class='xY a4W']//span[@class='bqe']")).size();
			if(allUnreadEmails!=1) {
				Reporter.log("Expected offline report should be 1 but actual report generated is "+allUnreadEmails);
			}
			*/
			
			}catch(Throwable t) {
				t.printStackTrace();
				throw t;
			}
	}
}

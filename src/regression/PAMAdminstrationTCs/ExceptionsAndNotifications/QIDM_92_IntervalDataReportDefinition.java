package regression.PAMAdminstrationTCs.ExceptionsAndNotifications;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

/*
 * This test validates creation of  'Interval data Report' definition under 'Exceptions and Notifications' page. 
 * First It creates the definition and check if there is a calendar icon next to saved report and under Saved Analysis.
 */
public class QIDM_92_IntervalDataReportDefinition extends TestBase {
	LoginTC login = null;

	@Test
	public void intervalDataReportDefinition() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("External");

			// Go to Notifications page
			gotoExceptionNotificationPage();

			// Selected "Interval Data Report" from dropdown and navigates to Interval Data Report page
			getWebElementXpath("ScheduleTypeDropdown").click();
			Thread.sleep(2000);
			getWebElementXpath("IntervalDataReport").click();
			Thread.sleep(2000);
			//Provide the Schedule Name and selected the saved analysis
			getWebElementXpath("ScheduleNameTextBox").sendKeys("SR_AutoTest_Report");
			getWebElementXpath("SavedAnalysisTextBox").click();
			Thread.sleep(2000);
			int savedAnalysisList = d.findElements(By.xpath("//*[@id='smartSearchInner']/table")).size();
			String savedAnalysisPath, savedAnalysis;
			if (savedAnalysisList > 0) {
				for (int i = 1; i <= savedAnalysisList; i++) {
					savedAnalysisPath = "//*[@id='smartSearchInner']/table[@id='dsTable'][" + i + "]/tbody/tr/td";
					savedAnalysis = getWebElementXpath_D(savedAnalysisPath).getText();
					if (savedAnalysis.equalsIgnoreCase("SR_AutoTest_Report")) {
						getWebElementXpath_D(savedAnalysisPath).click();
						Thread.sleep(2000);
						getWebElementXpath_D("(//input[@type='button'])[5]").click();
						Thread.sleep(2000);
						break;
					}
				}
			}

			//Selected Report Format as 'Chart and Table'
			getWebElementXpath("ReportFormatDropdown").click();
			Thread.sleep(2000);
			getWebElementXpath("ChartAndTableOption").click();
			Thread.sleep(2000);
			// Click on next
			getWebElementXpath_D("(//div[@class='k-content'])[1]//a[@class='buttonMain']/span").click();
			Thread.sleep(2000);
			
			//Seleced the frequency as Daily
			getWebElementXpath("FrequencyDaily").click();
			Thread.sleep(2000);
			getWebElementXpath("FrequencyNextButton").click();
			Thread.sleep(2000);

			//Selected the external users for recipients
			getWebElementXpath("ReceipientsUsersTextBox").click();
			getWebElementXpath("ReceipientsUsersTextBox").clear();
			getWebElementXpath("ReceipientsUsersTextBox").sendKeys("pautoexternal");
			Thread.sleep(5000);
			aJaxWait();
			
			getWebElementXpath_D("//*[@class='smartsearch-table']//input[1]").click();
			Thread.sleep(2000);
			getWebElementXpath_D("(//*[@id='notification']//*[@class='buttonMain']/span[@class='ng-binding'])[1]").click();
			Thread.sleep(2000);
			
			//Accept the consent and schedule
			getWebElementXpath("ConsentCheckbox").click();
			Thread.sleep(2000);
			getWebElementXpath("IntervalDataReportSchedule").click();
			Thread.sleep(5000);
			
			//Verified the scheduled report from the grid and clicked on report link
			Assert.assertEquals(getWebElementXpath("ScheduleReportLink").getText(), "SR_AutoTest_Report");
			getWebElementXpath("ScheduleReportLink").click();
			Thread.sleep(5000);
			Assert.assertEquals(getWebElementXpath("ScheduleNameTextBox").getAttribute("value"), "SR_AutoTest_Report");
			//Deleted the report
			getWebElementXpath("ScheduleReportDelete").click();
			Thread.sleep(5000);
			
			//Click on Alert for delete the report
			getWebElementXpath("AlertOk").click();
			Thread.sleep(5000);
			Assert.assertNotEquals(getWebElementXpath("ScheduleReportLink").getText(), "SR_AutoTest_Report");
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

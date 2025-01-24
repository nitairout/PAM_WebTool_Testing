package regression.PAMAdminstrationTCs.ExceptionsAndNotifications;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;

/*
 * This test validates creation 'Interval Data Alert' definition under 'Exceptions and Notifications' page.
 */


public class TK3_QIDM_88_IntervalDataAlertDefinition extends TestBase {
	LoginTC login = null;
	String alert = "SD_TestIntervalDataAlert";

	@Test
	public void intervalDataAlertDefinition() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("External");

			// Go to Notifications page
			gotoExceptionNotificationPage();
			
			//verify the alert already exists, if yes delete the alert.
			getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_txtSearchSchedule']").clear();
			getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_txtSearchSchedule']").sendKeys(alert);
			getWebElementXpath_D("//a[@id='ctl00_ContentPlaceHolder1_lnkScheduleGo']/span").click();
			Thread.sleep(5000);
			int list = d.findElements(By.xpath("//div[@id='ctl00_ContentPlaceHolder1_grdNotificaionSchedule_upPanelSchedule']//table[@class='rgMasterTable rgClipCells']/tbody/tr")).size();
			int listTableRow = d.findElements(By.xpath("//div[@id='ctl00_ContentPlaceHolder1_grdNotificaionSchedule_upPanelSchedule']//table[@class='rgMasterTable rgClipCells']/tbody/tr/td")).size();
			
			String alertName;
			if(listTableRow>1) {
			for(int i=1;i<=list;i++) {
				alertName = getWebElementActionXpath_D("(//div[@id='ctl00_ContentPlaceHolder1_grdNotificaionSchedule_upPanelSchedule']//table/tbody/tr["+i+"]/td[2]/a)[2]").getText();
				if(alertName.equalsIgnoreCase(alert)) {
					getWebElementActionXpath_D("(//div[@id='ctl00_ContentPlaceHolder1_grdNotificaionSchedule_upPanelSchedule']//table/tbody/tr["+i+"]/td[10]/a)").click();
					Thread.sleep(2000);
					getWebElementActionXpath_D("//span[contains(text(),'Ok')]").click();
					break;
				}
			}
			}
			printLog("Deleted the pre created alert notification");
			
			// Selected "Interval Data Alert" from dropdown and navigates to Interval Data Report page
			getWebElementXpath("ScheduleTypeDropdown").click();
			Thread.sleep(2000);
			getWebElementXpath("IntervalDataAlert").click();
			Thread.sleep(5000);
			
			getWebElementActionXpath("IntervalDataAlertSetupNameTextBox").sendKeys(alert);
			Thread.sleep(1000);
			getWebElementActionXpath("IntervalDataAlertSetupServiceDropdown").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//li[contains(.,'Electricity')]").click();
			Thread.sleep(1000);
			
			getWebElementActionXpath("IntervalDataAlertSetupDataTypeDropdown").click();
			Thread.sleep(2000);
			getWebElementActionXpath("EnergyDataType").click();
			Thread.sleep(5000);
			
			String uom = getWebElementXpath("IntervalDataAlertSetupUOMDropdown").getText();
			Assert.assertEquals(uom, "kWh");
			
			String level = getWebElementXpath("IntervalDataAlertSetupLevelDropdown").getText();
			Assert.assertEquals(level, "Site");
			
			getWebElementXpath("SetupNext").click();
			Thread.sleep(5000);
			printLog("Setup tab data filled up and clicked on next");
			
			getWebElementXpath("ParticipantsSiteTextbox").clear();
			getWebElementXpath("ParticipantsSiteTextbox").sendKeys("PAMTest_Capriata/Saiwa");
			Thread.sleep(1000);
			getWebElementXpath_D("//span[contains(.,'PAMTest_Capriata/Saiwa')]").click();
			Thread.sleep(2000);
			
			getWebElementXpath("AddSitesButton").click();
			Thread.sleep(2000);
			getWebElementXpath("ParticipantsNext").click();
			Thread.sleep(5000);
			printLog("paticipents tab data filled up and clicked on next");
			
			getWebElementXpath("ConditionsExceptionTypeDropdown").click();
			Thread.sleep(500);
			getWebElementXpath_D("//span[contains(.,'Interval Data')]").click();
			Thread.sleep(500);
			getWebElementXpath("CompareToDropdown").click();
			Thread.sleep(500);
			getWebElementXpath_D("//span[contains(.,'Fixed Value')]").click();
			Thread.sleep(500);
			
			getWebElementXpath("FlagAsExceptionDropdown").click();
			Thread.sleep(500);
			getWebElementXpath_D("//li[contains(.,'greater than')]").click();
			Thread.sleep(1000);
			//getWebElementActionXpath("FlagValue").click();
			//Thread.sleep(5000);
			//Up arrow
			getWebElementActionXpath_D("//*[@id='divCompare']//kendo-numerictextbox/span/button[1]/kendo-icon-wrapper/kendo-svgicon").click();
			//getWebElementActionXpath_D("//div[@id='divCompare']/div/kendo-numerictextbox/input").sendKeys("1");
			Thread.sleep(1000);
			getWebElementActionXpath_D("//span[contains(text(),'Next')]").click();
			Thread.sleep(5000);
			printLog("Conditions tab data filled up and clicked on next");
			
			getWebElementActionXpath_D("//div[@id='timeperiod']/div[1]/input").click();
			//Time period next
			getWebElementXpath_D("//span[contains(text(),'Next')]").click();
			Thread.sleep(5000);
			printLog("Time period tab data filled up and clicked on next");
			
			//Selected the external users for recipients
			getWebElementXpath_D("(//input[@type='text'])[2]").clear();
			getWebElementXpath_D("(//input[@type='text'])[2]").sendKeys("pautoexternal");
			getWebElementXpath_D("//span[contains(.,'pautoexternal')]").click();
			Thread.sleep(1000);
			//Searched user from the list
			getWebElementXpath_D("(//input[@type='checkbox'])[2]").click();
			Thread.sleep(2000);
			
			getWebElementActionXpath_D("//span[contains(text(),'Add User to Recipients')]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("(//input[@type='checkbox'])[3]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//span[contains(.,'Save')]").click();
			Thread.sleep(5000);
			printLog("Notification tab data filled up and clicked on next");
			
			getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_txtSearchSchedule']").clear();
			getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_txtSearchSchedule']").sendKeys(alert);
			getWebElementXpath_D("//a[@id='ctl00_ContentPlaceHolder1_lnkScheduleGo']/span").click();
			Thread.sleep(3000);
			
			Assert.assertEquals(getWebElementActionXpath_D("(//div[4]/div[4]/div/div/table/tbody/tr/td[2]/a)[1]").getText(), alert);
			getWebElementActionXpath_D("(//td[10]/a)[1]").click();
			Thread.sleep(4000);
			printLog("Verified if the created notification is present");

			getWebElementActionXpath_D("//span[contains(text(),'Ok')]").click();
			Thread.sleep(4000);
			Assert.assertNotEquals(getWebElementActionXpath_D("(//div[4]/div[4]/div/div/table/tbody/tr/td[2]/a)[1]").getText(), alert);
			printLog("Deleted the notification for further use");
			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

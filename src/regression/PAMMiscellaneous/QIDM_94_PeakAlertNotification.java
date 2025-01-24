package regression.PAMMiscellaneous;
import java.util.ArrayList;
import java.util.Arrays;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.*;
/*
 * This test evaluates the Peak Alert Notification. Test involves adding a subscriber and enabling the ISO linked to the subscriber
 * */
public class QIDM_94_PeakAlertNotification extends TestBase{
	LoginTC login =null;
	boolean isDelete=true;
	int rowNum=0;
	@Test
	public void peakAlertNotification() throws Throwable
	{
		try
		{
			login= LoginTC.getLoginTCObject(); 
			login.login("Internal");
			
			/*
			//Click on 'Procurement' main menu tab
			getWebElementActionXpath_D("//span[contains(text(),'Procurement')]").click();
			Thread.sleep(2000);
			Reporter.log("Click on 'Procurement' main menu tab");
			//Click on 'Peak Alert Notifications' link
			getWebElementActionXpath_D("//a[contains(text(),'Peak Alert Notifications')]").click();
			Thread.sleep(6000);
			Reporter.log("Click on 'Peak Alert Notifications' link");
			*/
			d.navigate().to(navurl +"com/peakalert/app/");
			Thread.sleep(8000);
			
			//3 tab verification
			int numOfTab=d.findElements(By.xpath("//ul[@role='tablist']/li")).size();
			Assert.assertEquals(numOfTab, 3);
			printLog("Verified 3 tabs.");
			
			//Click on 'My Subscriptions' tab
			getWebElementActionXpath_D("//li/span[contains(text(),'My Subscriptions')]").click();
			Thread.sleep(10000);
			printLog("Click on 'My Subscriptions' tab");
			
			//De-select all ISO before selecting TestISO. TO make sure all the ISO are de-selected
			/*int selectIsoSize=d.findElements(By.xpath("//*[contains(@class,'k-switch-container')]")).size();
			Assert.assertEquals(selectIsoSize, 6);
			printLog("Verified 6 ISOs.");
			
			ArrayList<String> isoLists= new ArrayList<String>(Arrays.asList("ERCOT","IESO","ISO-NE","NYISO","PJM","TestISO"));
			
			for(int i=1;i<=selectIsoSize;i++) {
				if(isElementPresent_D("(//*[contains(@class,'k-switch-on')])["+i+"]")) {
					//getWebElementXpath_D("//tbody/tr["+i+"]/td[1]/kendo-switch[1]").click();
					getWebElementXpath_D("(//*[contains(@class,'k-switch-on')])["+i+"]").click();
				}
				isoLists.contains(getWebElementActionXpath_D("(//tr[1]/td[@class='ng-star-inserted'])[1]").getText());
			}
			printLog("Disabled and verified all the ISO");*/
			
			
			//Click on 'Manage Subscribers' tab
			getWebElementActionXpath_D("//li/span[contains(text(),'Manage Subscribers')]").click();
			Thread.sleep(5000);
			printLog("Click on 'Manage Subscribers' tab");
			
			getWebElementActionXpath_D("//button[contains(text(),'Add a Subscriber')]").click();
			Thread.sleep(8000);
			printLog("Click on 'Add a Subscriber' button");
			
			getWebElementActionXpath_D("//div/input[@id='email']").click();
			getWebElementActionXpath_D("//div/input[@id='email']").sendKeys("lakshmi.boggala@se.com");
			getWebElementActionXpath_D("//input[@value='Continue']").click();
			printLog("Provide email id as 'lakshmi.boggala@se.com'");
			Thread.sleep(5000);
			
			//Select only ERCOT. rest should be de-select
			int isoSize=d.findElements(By.xpath("//div[2]/kendo-listview/div/div/label")).size();
			String isoLable="";
			for(int i=1;i<=isoSize;i++) {
				isoLable=getWebElementActionXpath_D("(//div[2]/kendo-listview/div/div/label//span)["+i+"]").getText();
				if(!isoLable.equalsIgnoreCase("ERCOT")) {
					//Check other checkbox if checked then uncheck
					if(getWebElementActionXpath_D("(//div[2]/kendo-listview/div/div/label//input)["+i+"]").isSelected()) 
						getWebElementActionXpath_D("(//div[2]/kendo-listview/div/div/label//input)["+i+"]").click();
				}else {
					//Check TestISO checkbox if un-checked then check
					if(!getWebElementActionXpath_D("(//div[2]/kendo-listview/div/div/label//input)["+i+"]").isSelected()) 
						getWebElementActionXpath_D("(//div[2]/kendo-listview/div/div/label//input)["+i+"]").click();
				}
			}
			printLog("Check only ERCOT check box.");
			
			//check the consent
			((JavascriptExecutor)d).executeScript("document.body.style.zoom='80%';");
			WebElement consentCheckBox = d.findElement(By.xpath("(//label[2]/input[contains(@class,'ng-valid')])[1]"));
			JavascriptExecutor executorSave = (JavascriptExecutor)d;
			executorSave.executeScript("arguments[0].click();", consentCheckBox);
			Thread.sleep(4000);
			printLog("Click on checkbox 'i have received consent'");
			//Click on Save button
			WebElement saveConsentButton = d.findElement(By.xpath("(//div[@class='footer-modal-popup']//input[@value='Save' and @class='btn btn-ok'])[1]"));
			executorSave.executeScript("arguments[0].click();", saveConsentButton);
			Thread.sleep(5000);
			((JavascriptExecutor)d).executeScript("document.body.style.zoom='100%';");
			Thread.sleep(2000);
			printLog("Click on 'Save' button");
			//*[@id="user-ticket-list-grid"]/div/kendo-grid-list/div/div[1]/table
			int addSubscriptionTableSize=d.findElements(By.xpath("//*[@id='user-ticket-list-grid']//table[@class='k-grid-table']//tr")).size();
			//getting the row number where the verification values are available
			for(int i=1;i<=addSubscriptionTableSize;i++) {
				if(getWebElementActionXpath_D("//*[@id='user-ticket-list-grid']//table[@class='k-grid-table']//tr["+i+"]/td[3]").getText().equals("lakshmi.boggala@se.com")) {
					rowNum=i;
				}
			}
			//Verification
			Assert.assertEquals(getWebElementActionXpath_D("//td[normalize-space()='lakshmi.boggala@se.com']").getText(), "lakshmi.boggala@se.com");
			//Assert.assertEquals(getWebElementActionXpath_D("//*[@id='user-ticket-list-grid']//table[@class='k-grid-table']//tr["+rowNum+"]/td[4]").getText(), "pautointernal");
			Assert.assertEquals(getWebElementActionXpath_D("//td[normalize-space()='ERCOT']").getText(), "ERCOT");
			printLog("Verified the item after saved.");
			Thread.sleep(3000);
			
			//Click on edit
			getWebElementActionXpath_D("//tbody/tr[1]/td[6]/a[1]/span[1]").click();
			Thread.sleep(8000);
			isoLable="";
			for(int i=1;i<=isoSize;i++) {
				isoLable=getWebElementActionXpath_D("(//*[contains(@id,'k-tabstrip-tabpanel')]//subscriber-edit//kendo-listview/div/div)["+i+"]/label/span").getText();
				if(isoLable.equalsIgnoreCase("ISO-NE")) {
					//Check ISO-NE checkbox if un-checked then check
					if(!getWebElementActionXpath_D("(//*[contains(@id,'k-tabstrip-tabpanel')]//subscriber-edit//kendo-listview/div/div)["+i+"]/label/input").isSelected()) 
						getWebElementActionXpath_D("(//*[contains(@id,'k-tabstrip-tabpanel')]//subscriber-edit//kendo-listview/div/div)["+i+"]/label/input").click();
					break;
				}
			}
			printLog("Check only ISO-NE check box.");
			((JavascriptExecutor)d).executeScript("document.body.style.zoom='80%';");
			
			//check the consent
			consentCheckBox = d.findElement(By.xpath("//*[contains(@id,'k-tabstrip-tabpanel')]//subscriber-edit//label[2]/input[contains(@class,'ng-valid')]"));
			executorSave = (JavascriptExecutor)d;
			executorSave.executeScript("arguments[0].click();", consentCheckBox);
			Thread.sleep(1000);
			printLog("Click on checkbox 'i have received consent' for update");
			//Click on Save button to update
			saveConsentButton = d.findElement(By.xpath("//*[contains(@id,'k-tabstrip-tabpanel')]//subscriber-edit//input[@value='Save' and @class='btn btn-ok']"));
			executorSave.executeScript("arguments[0].click();", saveConsentButton);
			Thread.sleep(8000);
			printLog("Click on 'Save' button to update");
			
			//Verification after update
			Assert.assertEquals(getWebElementActionXpath_D("//td[normalize-space()='lakshmi.boggala@se.com']").getText(), "lakshmi.boggala@se.com");
			//Assert.assertEquals(getWebElementActionXpath_D("//table[@class='k-grid-table']//tr["+rowNum+"]/td[4]").getText(), "pautointernal");
			Assert.assertEquals(getWebElementActionXpath_D("//td[normalize-space()='ERCOT, ISO-NE']").getText(), "ERCOT, ISO-NE");
			printLog("Verified the item after updated.");
			
			d.navigate().refresh();
			Thread.sleep(8000);
			
			//Click on 'Manage Subscribers' tab
			getWebElementActionXpath_D("//li/span[contains(text(),'Manage Subscribers')]").click();
			Thread.sleep(5000);
			printLog("Click on 'Manage Subscribers' tab");
			
			//Click on Delete
			getWebElementActionXpath_D("//tbody/tr[1]/td[7]/a[1]/span[1]").click();
			Thread.sleep(3000);
			//Yes to delete
			getWebElementActionXpath_D("(//input[@value='Yes' and @class='btn btn-ok'])[2]").click();
			isDelete=false;
			printLog("Deleted the notification");
			
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}finally {
			if(isDelete) {
				d.navigate().refresh();
				Thread.sleep(7000);
				//Click on 'Manage Subscribers' tab
				getWebElementActionXpath_D("//li/span[contains(text(),'Manage Subscribers')]").click();
				Thread.sleep(5000);
				printLog("Click on 'Manage Subscribers' tab");
				//Click on Delete
				getWebElementActionXpath_D("//tbody/tr[1]/td[7]/a[1]/span[1]").click();
				Thread.sleep(3000);
				//Yes to delete
				getWebElementActionXpath_D("(//input[@value='Yes' and @class='btn btn-ok'])[2]").click();
				isDelete=false;
				printLog("Deleted the notification in finally block.");
			}
			login.logout();
		}
	}	
}


package regression.PAMAdminstrationTCs.ExceptionsAndNotifications;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*This test verifies mapping a site to PAM and verifies mapped site available under PAM Managed Locations list.*/

public class QIDM_114_SiteMapping extends TestBase {
	LoginTC login = null;
	String siteName ="PAMTest_Bauru";
	@Test
	public void siteMapping() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Site Mapping page under Administration menu tab
			gotoSiteMappingPage();
			Thread.sleep(10000);
			//Clicked on the Site tab			
			getWebElementActionXpath_D("(//*[@class='rtsUL'])[2]/li[2]/a/span/span").click();
			
			//Verify the site name is already added on the participants list, if yes delete the site
			List<WebElement> listOfParticipants = d.findElements(By.xpath("(//div[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_rgDivisionSites']/table//tbody)[2]/tr//a"));
			String participantsPath, participantsName=null;
			for(int i=1;i<=listOfParticipants.size();i++) {
				participantsPath = "(//div[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_rgDivisionSites']/table//tbody)[2]/tr["+i+"]/td[2]";
				participantsName = d.findElement(By.xpath(participantsPath)).getText();
				if(participantsName.equalsIgnoreCase(siteName)) {
					String deleteIconPath = "(//div[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_rgDivisionSites']/table//tbody)[2]/tr["+i+"]/td[7]/a";
					d.findElement(By.xpath(deleteIconPath)).click();
					Thread.sleep(3000);
					d.findElement(By.xpath("(//div[@class='rwDialogPopup radconfirm']/div[2]/a)[1]")).click();
					Thread.sleep(3000);
					getWebElementActionXpath_D("(//span[@class='rbText'])[1]").click();
					printLog("Successfully added the PAMTest_Bauru site");
					break;
				}
			}
			
			//Provide the site name for search bar
			getWebElementActionXpath_D("//input[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_cbEFDBSiteSearch_Input']").click();
			getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_cbEFDBSiteSearch_Input']").clear();
			getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_cbEFDBSiteSearch_Input']").sendKeys(siteName);
			printLog("Provide the site name for search bar");
			Thread.sleep(10000);
			
			//Click on the site name from the site search list
			getWebElementActionXpath_D("//table[@class='efdb_list_items']/tbody/tr/td[1]").click();
			printLog("Click on the site name from the site search list");
			Thread.sleep(5000);
			
			//Click on '+' icon for add sites
			getWebElementActionXpath_D("//a[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_lnkbtnAddSites']").click();
			Thread.sleep(5000);
			
			//Verify the site name is added on the participants list
			listOfParticipants = d.findElements(By.xpath("(//div[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_rgDivisionSites']/table//tbody)[2]/tr"));
			for(int i=1;i<=listOfParticipants.size();i++) {
				participantsPath = "(//div[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_rgDivisionSites']/table//tbody)[2]/tr["+i+"]/td[2]";
				participantsName = d.findElement(By.xpath(participantsPath)).getText();
				if(participantsName.equalsIgnoreCase(siteName)) {
					printLog("Successfully added the PAMTest_Bauru site");
					break;
				}
			}
			Assert.assertEquals(participantsName,siteName);
			//Clicked on Save button
			getWebElementActionXpath_D("(//span[@class='rbText'])[1]").click();
			printLog("Clicked on Save button");
			Thread.sleep(3000);
			Utility.moveTheScrollToTheTop();
			//Navigate to PAM page
			goToAnalysisPage();
			//Naviagate to Trend Analysis Card page
			goToPAMCard("TrendAnalysisCard");
			Thread.sleep(500);
			//Click on Locations panel config icon
			getWebElementActionXpath_D("//i[@class='far fa-ellipsis-v se-menu-kabob clickable']").click();
			
			//Click on 'Manage Locations list' option
			getWebElementActionXpath_D("//div[contains(text(),'Manage Locations List')]").click();
			printLog("Click on 'Manage Locations list' option");
			Thread.sleep(10000);
			
			//Click on Site Name textbox and provide the name as 'PAMTest_Bauru' from Location Editor page
			getWebElementActionXpath_D("(//div[@class='radius-sm'])[3]//input").click();
			getWebElementXpath_D("(//div[@class='radius-sm'])[3]//input").sendKeys(siteName);
			Utility.moveTheScrollToTheDown();
			
			//Click on Search button
			getWebElementActionXpath_D("//span[@class='btn btn-default pull-right']").click();
			printLog("Click on Search button");
			Thread.sleep(3000);
			Utility.moveTheScrollToTheTop();
			
			//Verified that sitename should be available on results
			String actualSiteName = d.findElement(By.xpath("//span[@class='info blue bold']")).getText();
			Assert.assertEquals(actualSiteName, siteName);
			printLog("Verified that sitename should be available on results");
			Utility.moveTheScrollToTheDown();
			
			//Click on Cancel button
			getWebElementActionXpath_D("//span[@class='btn btn-link pull-right']").click();
			printLog("Click on cancel button");
			Thread.sleep(1000);
			
			//Navigate to Site Mapping page under Administration menu tab
			gotoSiteMappingPage();
			Thread.sleep(3000);
			
			//Click on Site tab			
			getWebElementXpath_D("(//*[@class='rtsUL'])[2]/li[2]/a/span/span").click();
			printLog("Click on site tab");
			Thread.sleep(3000);
			//Verify the site name is available on the participants list and then delete
			listOfParticipants = d.findElements(By.xpath("(//div[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_rgDivisionSites']/table//tbody)[2]/tr"));
			for(int i=1;i<=listOfParticipants.size();i++) {
				participantsPath = "(//div[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_rgDivisionSites']/table//tbody)[2]/tr["+i+"]/td[2]";
				participantsName = d.findElement(By.xpath(participantsPath)).getText();
				if(participantsName.equalsIgnoreCase(siteName)) {
					String deleteIconPath = "(//div[@id='ctl00_ContentPlaceHolder1_ParticipantsCtrl_rgDivisionSites']/table//tbody)[2]/tr["+i+"]/td[7]/a";
					d.findElement(By.xpath(deleteIconPath)).click();
					Thread.sleep(3000);
					d.findElement(By.xpath("(//div[@class='rwDialogPopup radconfirm']/div[2]/a)[1]")).click();
					Thread.sleep(3000);
					getWebElementActionXpath_D("(//span[@class='rbText'])[1]").click();
					printLog("Successfully added the PAMTest_Bauru site");
					break;
				}
			}
			
			//Navigate to PAM page
			goToAnalysisPage();
			Thread.sleep(3000);
			goToPAMCard("TrendAnalysisCard");
			printLog("Navigate to Trendcard");
			
			//Click on Locations panel config icon
			getWebElementActionXpath_D("//i[@class='far fa-ellipsis-v se-menu-kabob clickable']").click();
			
			//Click on 'Manage Locations list' option
			getWebElementActionXpath_D("//div[contains(text(),'Manage Locations List')]").click();
			Thread.sleep(3000);
			printLog("Click on Manage Locations list");
			
			getWebElementActionXpath_D("(//div[@class='radius-sm'])[3]//input").click();
			getWebElementXpath_D("(//div[@class='radius-sm'])[3]//input").sendKeys(siteName);
			printLog("provide the site name");
			Utility.moveTheScrollToTheDown();
			
			//Click on Search button
			getWebElementActionXpath_D("//span[@class='btn btn-default pull-right']").click();
			printLog("Click on Search button");
			
			Utility.moveTheScrollToTheTop();
			//Click on Site Name textbox and provide the name as 'PAMTest_Bauru' from Location Editor page
			List<WebElement> siteNameResults = d.findElements(By.xpath("//span[@class='info blue bold']"));
			Assert.assertEquals(siteNameResults.size(), 0);
			printLog("verify the sitename result");
			Utility.moveTheScrollToTheDown();
			
			//Click on Cancel button
			getWebElementActionXpath_D("//span[@class='btn btn-link pull-right']").click();
			printLog("Click on Cance button");
			Thread.sleep(300);
			
			login.logout();
			printLog("Logout from the application");
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

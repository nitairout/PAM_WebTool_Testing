package regression.PAMMiscellaneous;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies advance search options available for the locations under 'Manage Locations Lists' option in PAM cards.
 */
public class QIDM_206_ManageLocationsList extends TestBase{
	LoginTC login = null;
	boolean deleteSite=true;
	@Test
	public void manageLocationsList() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();
			Thread.sleep(8000);
			
			goToPAMCard("TrendAnalysisCard");
			Thread.sleep(5000);
			
			// Select manage location list from kabob menu
			getWebElementXpath_D("//i[contains(@class,'far fa-ellipsis-v se-menu-kabob clickable')]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//div[contains(text(),'Manage Locations List')]").click();
			Thread.sleep(2000);
			printLog("Inside manage location list page");
			
			//Search with 'PAM Automation Client' at Division level
			new Select(getWebElementActionXpath_D("//*[@type='division']//select")).selectByVisibleText("PAM Automation Client");
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath_D("//span[@class='btn btn-default pull-right']").click();
			Thread.sleep(2000);
			
			Utility.moveTheScrollToTheTop();
			//Verified the list of sites available at Division level
			int siteList = d.findElements(By.xpath("//span[@class='info blue bold']")).size();
			Assert.assertEquals(siteList, 6);	
			printLog("Verified that 6 sites are available at Division level");
			//Change back to 'All divisions'
			new Select(getWebElementActionXpath_D("//*[@type='division']//select")).selectByVisibleText("All divisions");
			
			//Search with 'PAM Automation Client' at Group level
			new Select(getWebElementActionXpath_D("//*[@type='group']//select")).selectByVisibleText("PAM Automation Group");
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath_D("//span[@class='btn btn-default pull-right']").click();
			Thread.sleep(2000);
			Utility.moveTheScrollToTheTop();
			//Verified the list of sites available at Group level
			siteList = d.findElements(By.xpath("//span[@class='info blue bold']")).size();
			Assert.assertEquals(siteList, 3);
			printLog("Verified that 3 sites are available at Group level");
			//Change back to 'All groups'
			new Select(getWebElementActionXpath_D("//*[@type='group']//select")).selectByVisibleText("All groups");

			// Search and verify site 'PAMTest_Naperville, IL' from manage location
			getWebElementActionXpath("AdvanceSiteSearchBox").clear();
			getWebElementXpath("AdvanceSiteSearchBox").sendKeys(testData.getProperty("PAMTestNapervilleIL"));
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath_D("//span[@class='btn btn-default pull-right']").click();
			Thread.sleep(2000);
			Utility.moveTheScrollToTheTop();
			Assert.assertEquals(getWebElementActionXpath_D("//span[@class='info blue bold']").getText(), testData.getProperty("PAMTestNapervilleIL"));
			printLog("Verified the site name 'PAMTest_Naperville, IL' populated from advance searched");
			getWebElementActionXpath("AdvanceSiteSearchBox").clear();
			
			d.navigate().refresh();
			// Select and verify the address
			getWebElementActionXpath("AdvanceAddressSearchBox").clear();
			getWebElementXpath("AdvanceAddressSearchBox").sendKeys("100 Cadbury Road");
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath_D("//span[@class='btn btn-default pull-right']").click();
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			Thread.sleep(2000);
			Assert.assertEquals(getWebElementActionXpath_D("//*[@class='info blue bold']").getText(),"PAMTest_Claremont");
			Assert.assertEquals(getWebElementActionXpath_D("//div[normalize-space()='100 Cadbury Road, Claremont, TAS, 7011']").getText(),"100 Cadbury Road, Claremont, TAS, 7011");
			
			printLog("Verified the sitename and address populated from advance searched");
			getWebElementActionXpath("AdvanceAddressSearchBox").clear();
			
			d.navigate().refresh();
			// Search and verify Submeter 'PAMTest_Powerhouse' from manage location
			getWebElementActionXpath("AdvanceSubMeterSearchBox").clear();
			getWebElementXpath("AdvanceSubMeterSearchBox").sendKeys("PAMTest_Powerhouse");
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath_D("//span[@class='btn btn-default pull-right']").click();
			Thread.sleep(5000);
			Utility.moveTheScrollToTheTop();
			Thread.sleep(2000);
			String baseXpath="(//span[@class='info blue bold'])";
			Assert.assertEquals(getWebElementActionXpath_D(baseXpath+"[1]").getText(), "PAMTest_Bournville");
			Assert.assertEquals(getWebElementActionXpath_D(baseXpath+"[2]").getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementActionXpath_D(baseXpath+"[3]").getText(), "PAMTest_Herentals, Biscuits");
			Assert.assertEquals(getWebElementActionXpath_D(baseXpath+"[4]").getText(), testData.getProperty("PAMTestNapervilleIL"));
			printLog("Verified the 4 sites populated for 'PAMTest_Powerhouse' submeter searched");
			getWebElementActionXpath("AdvanceSubMeterSearchBox").clear();
			
			// Search and verify site 'PAMTest_Bournville' from manage location
			getWebElementActionXpath("AdvanceSiteSearchBox").clear();
			getWebElementXpath("AdvanceSiteSearchBox").sendKeys("PAMTest_Bournville");
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath_D("//span[@class='btn btn-default pull-right']").click();
			Thread.sleep(2000);
			Utility.moveTheScrollToTheTop();
			Assert.assertEquals(getWebElementActionXpath_D(baseXpath).getText(), "PAMTest_Bournville");
			printLog("Verified the site name 'PAMTest_Bournville' populated from advance searched");
			//Arrow to add site from advance search
			getWebElementActionXpath_D("//span[@class='pull-right']//i").click();
			Thread.sleep(2000);
			Utility.moveTheScrollToTheDown();
			//Save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save and Exit')]").click();
			Thread.sleep(5000);
			
			//Verify the sitename exists on PAM page
			boolean siteExist = false;
			List<WebElement>  name = d.findElements(By.xpath("//tbody[@class='k-table-tbody']/tr/td[1]"));
			String siteName;
			for(int i=1;i<name.size();i++){
				siteName=getWebElementActionXpath_D("//tbody[@class='k-table-tbody']/tr["+i+"]/td[1]").getText();
				if(siteName.equalsIgnoreCase("PAMTest_Bournville")) {
					siteExist = true;
					break;
				}
			}
			Assert.assertTrue(siteExist);
			printLog("Verified that site 'PAMTest_Bournville' should be available on the PAM page.");
			
			// Select manage location list from kabob menu
			Utility.moveTheScrollToTheTop();
			getWebElementXpath_D("//i[contains(@class,'far fa-ellipsis-v se-menu-kabob clickable')]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//div[contains(text(),'Manage Locations List')]").click();
			Thread.sleep(2000);
			printLog("Inside manage location list page");
			
			name = d.findElements(By.xpath("(//div[@class='locationEditor'])[2]/div/span[2]"));
			for(int i=1;i<name.size();i++){
				siteName=getWebElementActionXpath_D("(//div[@class='locationEditor'])[2]/div["+i+"]/span[2]").getText();
				if(siteName.equalsIgnoreCase("PAMTest_Bournville")) {
					getWebElementActionXpath_D("(//i[@class='fa fa-remove'])["+i+"]").click();//(//div[@class='locationEditor'])[2]/div[2]/span[1]/i[@class='fa fa-remove']
					Thread.sleep(2000);
					printLog("Successfully delete the site 'PAMTest_Bournville' from the list.");
					//Click on Save & Exit button
					getWebElementActionXpath_D("//span[@class='btn btn-default pull-right errored']").click();
					Thread.sleep(2000);
					deleteSite=false;
					break;
				}
			}
			
			siteExist = false;
			name = d.findElements(By.xpath("//tbody[@class='k-table-tbody']/tr/td[1]"));
			for(int i=1;i<name.size();i++){
				siteName=getWebElementActionXpath_D("//tbody[@class='k-table-tbody']/tr["+i+"]/td[1]").getText();
				if(siteName.equalsIgnoreCase("PAMTest_Bournville")) {
					siteExist = true;
					break;
				}
			}
			Assert.assertEquals(siteExist, false);
			printLog("Verified that site 'PAMTest_Bournville' should not available on the PAM page.");
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(deleteSite) {
					goToAnalysisPage();
					Thread.sleep(8000);
					
					goToPAMCard("TrendAnalysisCard");
					Thread.sleep(5000);
					
					// Select manage location list from kabob menu
					getWebElementXpath_D("//i[contains(@class,'far fa-ellipsis-v se-menu-kabob clickable')]").click();
					Thread.sleep(1000);
					getWebElementXpath_D("//div[contains(text(),'Manage Locations List')]").click();
					Thread.sleep(2000);
					printLog("Inside manage location list page");
					int nameList = d.findElements(By.xpath("(//div[@class='locationEditor'])[2]/div/span[2]")).size();
					String siteName=null;
					for(int i=1;i<nameList;i++){
						siteName=getWebElementActionXpath_D("(//div[@class='locationEditor'])[2]/div["+i+"]/span[2]").getText();
						if(siteName.equalsIgnoreCase("PAMTest_Bournville")) {
							getWebElementActionXpath_D("(//i[@class='fa fa-remove'])["+i+"]").click();
							Thread.sleep(2000);
							printLog("Successfully delete the site 'PAMTest_Bournville' from the list.");
							//Click on Save & Exit button
							getWebElementActionXpath_D("//span[@class='btn btn-default pull-right errored']").click();
							Thread.sleep(2000);
							deleteSite=false;
							break;
						}
					}
					
					boolean siteExist = false;
					nameList = d.findElements(By.xpath("//tbody[@class='k-table-tbody']/tr/td[1]")).size();
					for(int i=1;i<nameList;i++){
						siteName=getWebElementActionXpath_D("//tbody[@class='k-table-tbody']/tr["+i+"]/td[1]").getText();
						if(siteName.equalsIgnoreCase("PAMTest_Bournville")) {
							siteExist = true;
							break;
						}
					}
					Assert.assertEquals(siteExist, false);
					printLog("Verified that site 'PAMTest_Bournville' should not available on the PAM page.");
					
				}
				
				login.logout();
			}catch(Exception e) {
				throw e;
			}
		}
	}
}

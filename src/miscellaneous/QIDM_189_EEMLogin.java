package miscellaneous;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import util.ErrorUtil;
/*This test verifies if EEM web site is working and we can create a new users under 'Administration' tab*/
public class QIDM_189_EEMLogin extends TestBase {
	LoginTC login =null;
	@Test
	public void eemLogin() throws Exception{
	try{
		login = LoginTC.getLoginTCObject();
		login.loginToEEM();
		Reporter.log("EEM Login successfully");
		
		//Verify dashboard page
		Assert.assertEquals(getWebElementXpath_D("//span[@id='ctl00_ctl00_content_bodyContent_DesktopTitle']").getText(), "Welcome");
		Thread.sleep(2000);
		//Administration tab
		getWebElementActionXpath_D("//div[@id='ctl00_ctl00_ctlHeader_header']//a[contains(text(),'Administration')]").click();
		Assert.assertEquals(getWebElementXpath_D("//div[@id='lblCatGroupSystem']").getText(), "System");
		Assert.assertEquals(getWebElementXpath_D("//span[@id='lblCatGroupModules']").getText(), "Modules");
		Assert.assertEquals(getWebElementXpath_D("//span[@id='Label1']").getText(), "Tools");
		printLog("Clicked on Administration tab and verified some values.");
		
		//Clicked on Account link
		getWebElementActionXpath_D("//li/span[@id='lblAccounts']").click();
		Thread.sleep(2000);
		//User Link
		getWebElementActionXpath_D("//a[@id='hypUsersManage']").click();
		Thread.sleep(2000);
		
		String headerArr[]= {"User Name","Last Name","First Name","E-Mail","Organization","Role","Group Name"};
		int headerSize=d.findElements(By.xpath("//tr[@class='datagrid-header']/td")).size();
		for(int i=1;i<=headerSize;i++) {
			Assert.assertEquals(getWebElementXpath_D("//tr[@class='datagrid-header']/td["+i+"]").getText(), headerArr[(i-1)]);
		}
		d.getPageSource().contains("admin_autouser");
		printLog("Verified the table header.");
		
		//new button
		getWebElementActionXpath_D("//div[contains(@id,'ecaButAddNewUser_ButtonPanel')]//span[text()='New']").click();
		Thread.sleep(2000);
		printLog("Clicked on new button.");
		
		Assert.assertEquals(getWebElementXpath_D("(//label[@class='control-label'])[1]").getText(), "User Name:");
		//User name text bov
		Assert.assertTrue(isElementPresent_D("//input[contains(@id,'bodyContent_txtUsername')]"));
		//save button
		Assert.assertTrue(isElementPresent_D("//button[contains(@id,'bodyContent_ecaButAdd') and text()='Save']"));

		getWebElementActionXpath_D("//span[contains(text(),'Logout')]").click();
		printLog("Logged out successfully");
	}catch(Exception t){
		t.printStackTrace();
		ErrorUtil.addVerificationFailure(t);
		Reporter.log("Error in EEMLogin test " + t.getStackTrace());
		Assert.fail("Error in EEMLogin test", t);
		throw t;
	}
	}
}

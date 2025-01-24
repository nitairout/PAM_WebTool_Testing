package regression.PAMAdminstrationTCs.AverageProfile;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;

public class QIDM_119_AverageProfile extends TestBase {
	LoginTC login = null;
	String expectedAverageProfile = "AutoTest_AvgProfile";
	//String siteName = testData.getProperty("PAMTestCapriataSaiwa");
	String actualAverageProfile, actualSiteName = "";

	@Test
	public void averageProfile() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			String siteName = testData.getProperty("PAMTestCapriataSaiwa");
			// Navigate to Average Profiles page under Administration menu tab
			gotoAverageProfilesPage();

			// Verify the Average Profile is already exists, if yes it should be delete
			int listOfAverageProfile = d.findElements(By.xpath("//div[@class='k-grid-table-wrap']/table/tbody/tr")).size();
			int verifyOnlyOneRow=d.findElements(By.xpath("//div[@class='k-grid-table-wrap']/table/tbody/tr/td")).size();
			if(verifyOnlyOneRow>1) {
				for (int i = 1; i <= listOfAverageProfile; i++) {
					actualAverageProfile = getWebElementXpath_D("//div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[1]/a").getText();
					if (actualAverageProfile.equalsIgnoreCase(expectedAverageProfile)) {
						getWebElementXpath_D("//div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[5]/a").click();
						Thread.sleep(3000);
						getWebElementXpath_D("//button[contains(.,'Delete')]").click();
						printLog("Aleady we have average profile '" + expectedAverageProfile + "' Now,it was deleted.");
						break;
					}
				}
			}else {
				printLog("There is no profile to delete.");
			}

			// Create New Average Profile '+' icon
			getWebElementXpath_D("//button[contains(@title,'Create New Average Profile')]").click();
			aJaxWait();
			
			//provide the average profile name
			getWebElementXpath_D("//div[@id='spinner-container']//kendo-label[@ng-reflect-text='Average Profile Name:']//input").sendKeys(expectedAverageProfile);
			
			//Click on 'Calculate Average Using' dropdown
			getWebElementActionXpath_D("(//div[@id='spinner-container']//span[@class='k-input-value-text'])[2]").click();
			Thread.sleep(1000);
			//Select '4 weeks' from the list
			getWebElementXpath_D("//span[contains(.,'4 Weeks Using same day of week')]").click();

			//Click on Next button
			getWebElementXpath_D("//button//span[contains(text(),'Next')]").click();
			Thread.sleep(3000);
			
			// Clicked on the Site tab
			getWebElementXpath_D("//span[contains(.,'Site')]").click();
			Thread.sleep(3000);
			// Provide the site name for search bar
			getWebElementXpath_D("(//input[@type='text'])[2]").clear();
			getWebElementXpath_D("(//input[@type='text'])[2]").sendKeys(siteName);
			Thread.sleep(1000);
			//After filter the site we are selecting
			getWebElementXpath_D("//span[contains(.,'PAMTest_Capriata/Saiwa')]").click();
			Thread.sleep(3000);

			// Click on '+' icon for add sites
			getWebElementActionXpath_D("//button[contains(.,'Add Sites')]").click();
			Thread.sleep(2000);

			// Verify the site name is added on the participants list
			actualSiteName = d.findElement(By.xpath("//td[contains(.,'PAMTest_Capriata/Saiwa')]")).getText();
			printLog("Successfully added the PAMTest_Capriata/Saiwa site");
			Assert.assertEquals(actualSiteName, siteName);

			// Clicked on Save button
			getWebElementActionXpath_D("//button[contains(.,'Save')]").click();
			Thread.sleep(5000);

			boolean avgProfileIsExist=false;
			// Verify the Average Profile was added
			listOfAverageProfile = d.findElements(By.xpath("//div[@class='k-grid-table-wrap']/table/tbody/tr")).size();
			verifyOnlyOneRow=d.findElements(By.xpath("//div[@class='k-grid-table-wrap']/table/tbody/tr/td")).size();
			if(verifyOnlyOneRow>1) {
				for (int i = 1; i <= listOfAverageProfile; i++) {
					actualAverageProfile = getWebElementXpath_D("//div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[1]/a").getText();
					if (actualAverageProfile.equalsIgnoreCase(expectedAverageProfile)) {
						avgProfileIsExist=true;
						printLog("Successfully added the average profile '" + expectedAverageProfile);
						break;
					}
				}
			}
			Assert.assertTrue(avgProfileIsExist);

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

}

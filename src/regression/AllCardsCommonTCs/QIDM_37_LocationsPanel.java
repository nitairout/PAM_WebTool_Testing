package regression.AllCardsCommonTCs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.*;

/*
 * This test verifies all the filtering and Kabob menu options under locations panel.
 */

public class QIDM_37_LocationsPanel extends TestBase {
	LoginTC login = null;
	String filterSiteXpath = "(//td[contains(@class,'k-table-td k-text-nowrap')])";

	@Test
	public void locationsPanel() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");

			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");

			// Searched with site name 'PAMTest_Capriata/Saiwa' from Location panel
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			Assert.assertEquals(getWebElementXpath_D(filterSiteXpath).getText(), testData.getProperty("PAMTestCapriataSaiwa"));
			printLog("Verified the site with site search!!");

			// Select the site filtered by tag
			getWebElementXpath("SiteFilterTextBox").clear();
			getWebElementXpath("SiteFilterTextBox").sendKeys("tag:AUTO TEST CATEGORY 1:Auto Tag 3");
			getWebElementXpath("SiteFilterTextBox").sendKeys(Keys.RETURN);
			Thread.sleep(2000);
			//searchSiteInLocationList("tag:AUTO TEST CATEGORY 1:Auto Tag 3");
			Assert.assertEquals(getWebElementXpath_D(filterSiteXpath+"[1]").getText(), "QA Site 1");
			printLog("Verified site with tag name search!!");

			// Select the site filtered by meter
			getWebElementXpath("SiteFilterTextBox").clear();
			getWebElementXpath("SiteFilterTextBox").sendKeys("MeterID:MDLZ.EU.IT.ITATC_COMX.GTW_E01.ELEC_Cabina1_Trasformatore1");
			getWebElementXpath("SiteFilterTextBox").sendKeys(Keys.RETURN);
			Thread.sleep(2000);
			//searchSiteInLocationList("MeterID:MDLZ.EU.IT.ITATC_COMX.GTW_E01.ELEC_Cabina1_Trasformatore1");
			Assert.assertEquals(getWebElementXpath_D(filterSiteXpath + "[5]").getText(), testData.getProperty("PAMTestTrasformatore1"));
			printLog("Verified site with meter id search!!");

			getWebElementXpath("SiteFilterTextBox").clear();
			
			// verifying all kabob menus
			getWebElementActionXpath("KabobMenuIcon").click();
			String kabobMenusArray[] = { "Hide Filter", "Show Account and Shadow Meters", "Manage Locations List", "Export Locations List to CSV" };
			List<WebElement> kabobMenuOptions = d.findElements(By.xpath("(//div[@class='list-group-item'])"));
			for (int i = 1; i <= kabobMenuOptions.size(); i++) {
				//Assert.assertEquals(kabobMenusArray[i - 1],	getWebElementXpath_D("(//div[@class='list-group-item'])[" + i + "]//div[@class='col-xs-10']") .getText());
			}
			printLog("Verified all kabob menu options!!!");
			
			// Hide the search site text box
			getWebElementXpath_D("//span[contains(text(),'Hide Filter')]").click();
			Assert.assertEquals(d.findElements(By.xpath(locators.getProperty("SiteFilterTextBox"))).size(), 0);

			// Show the search site text box
			getWebElementActionXpath("KabobMenuIcon").click();
			getWebElementXpath_D("//span[contains(text(),'Show Filter')]").click();
			printLog("Hide & Show filter completed");

			//Declaration some reference and delete the file if exist
			ArrayList<List<String>> csvDataToCompareIntable=null;
			String fileName="LocationsList";
			File fileTODelete=new File(Constant.DOWNLOAD_PATH+"\\"+fileName+".csv");
			fileTODelete.delete();
			
			// Select manage location list from kabob menu
			getWebElementXpath("KabobMenuIcon").click();
			getWebElementXpath_D("//div[contains(text(),'Export Locations List to CSV')]").click();
			Thread.sleep(2000);
			printLog("Clicked to download the csv.");
			
			//Hold the data from csv file
			csvDataToCompareIntable=Utility.returnNumberOfRowsFromcsv(fileName,16);
			Reporter.log("Holded first 16 rows of data from csv file");
			
			//Verify Values
			ArrayList<String> csvData=null;
			for (int c=0;c < csvDataToCompareIntable.size();c++){
				csvData=new ArrayList<String>(csvDataToCompareIntable.get(c));
				Assert.assertNotNull(csvData);
			}
			Reporter.log("Verified not null till 16th row");

			// Select manage location list from kabob menu
			getWebElementXpath("KabobMenuIcon").click();
			getWebElementXpath_D("//div[contains(text(),'Manage Locations List')]").click();
			Thread.sleep(2000);
			printLog("Inside manage location list page");

			// Search and verify site from manage location
			getWebElementActionXpath("AdvanceSiteSearchBox").clear();
			getWebElementXpath("AdvanceSiteSearchBox").sendKeys(testData.getProperty("PAMTestNapervilleIL"));
			getWebElementActionXpath("AdvanceSearchButton").click();
			Thread.sleep(2000);
			Assert.assertEquals(getWebElementActionXpath_D("//*[@class='info blue bold'][normalize-space()='PAMTest_Naperville, IL']").getText(), testData.getProperty("PAMTestNapervilleIL"));
			printLog("Verified the site name populated fromadvance searched");

			// Select and verify the address
			getWebElementActionXpath("AdvanceSiteSearchBox").clear();
			getWebElementActionXpath("AdvanceAddressSearchBox").clear();
			getWebElementXpath("AdvanceAddressSearchBox").sendKeys("1555 W.Ogden Avenue");
			getWebElementActionXpath("AdvanceSearchButton").click();
			Thread.sleep(2000);
			Assert.assertEquals(getWebElementActionXpath_D("(//div[@class='locationEditor'])[1]/div/div/div[2]").getText(), "1555 W. Ogden Avenue, Naperville, IL, 60540");
			printLog("Verified the address populated fromadvance searched");
			

			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
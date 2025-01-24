package smokeTest;

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
 * This test verifies creating a new tag category and applying a tag to the site under Mange Tags.
 */
public class QIDM_154_ManageTagsSmokeTests extends TestBase {
	LoginTC login = null;
	String categoryName = "auto smoke test Tag";
	
	@Test
	public void manageTagsSmokeTests() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Manage Tab page under Administration menu tab
			gotoManageTagsPage();
			
			// Verify if already Category has added. if Yes, delete the Category
			Utility.deleteTags(categoryName);
			
			//Click on Define Tags tab
			getWebElementXpath("DefineTags_Tab").click();
			
			//Click on Create New Category button and Provide the Category name as 'Auto Test Category'
			getWebElementXpath("CreateNewCategory").click();			
			getWebElementXpath("CategoryName_Textbox").sendKeys(categoryName);				
			Thread.sleep(2000);
			
			//Click on Save button
			getWebElementActionXpath("ManageTags_SaveButton").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			//Click on 'Apply Tags to Location' tab
			getWebElementXpath("ApplyTagsToLocation_Tab").click();
			Thread.sleep(5000);
			
			//Clicked on Config icon
			getWebElementActionXpath("Config_Icon").click();
			Thread.sleep(3000);
			//Clicked on 'Select Tag Categories'
			getWebElementXpath("SelectTagCategories").click();
			Thread.sleep(3000);
			//div[@id='showingColumnControl']/table/tbody/tr/td[3]/input
			//unselected all the tags from the list
			List<WebElement> selectedtagList = d.findElements(By.xpath("//td/input[contains(@class,'ng-untouched ng-pristine ng-valid')]"));
			System.out.println("selectedtagList/..."+selectedtagList);
			for(int i=1;i<=selectedtagList.size();i++) {
				if(getWebElementActionXpath_D("//tr["+i+"]/td[3]/input[contains(@class,'ng-untouched ng-pristine ng-valid')]").isSelected()) {
					getWebElementActionXpath_D("//tr["+i+"]/td[3]/input[contains(@class,'ng-untouched ng-pristine ng-valid')]").click();
				}
			}
			//Clicked on Update button
			getWebElementActionXpath("UpdateButton_SelectTagCategories").click();
			Thread.sleep(3000);
			
			//Select first site checkbox
			getWebElementActionXpath_D("(//tbody[@class='k-table-tbody']/tr)[1]/td[1]//input[@type='checkbox']").click();
			Thread.sleep(5000);
			//Select Category Name as 'Auto Test Category' and 'AutoTest1" as Tag
			new Select(getWebElementActionXpath("SelectCategory_dropdown")).selectByVisibleText(categoryName);
			Thread.sleep(5000);
			new Select(getWebElementActionXpath("SelectTag_dropdown")).selectByVisibleText("Tag 1");
			Thread.sleep(3000);
			//Click on Apply button
			getWebElementActionXpath("ApplyTag_button").click();
			Thread.sleep(8000);
			//Verified the popup message as '1 Location were tagged with "Tag 1"'
			String msg =getWebElementXpath_D( "//div[contains(@class,'modal-body')]/h4").getText();
			Assert.assertEquals(msg, "1 Locations were tagged with\"Tag 1\"");
			getWebElementActionXpath("ManageTages_OK_button").click();
			Thread.sleep(3000);
			Utility.deleteTags(categoryName);
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	
}
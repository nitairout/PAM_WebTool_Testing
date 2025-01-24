package regression.PAMAdminstrationTCs.ManageTags;

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
* This test verifies functionality defining and applying tags to the locations.
* First it creates new tag category and tags and applies to the locations. 
* then update the tag name and at the end removes tag from all the locations and delete the category.
*/

public class QIDM_98_DefineTagsAndApplyToLocations extends TestBase {
	LoginTC login = null;
	String categoryName = "QIDM_98_AutoTestCategory";
	String[] newTagsList = {"AutoTest1","AutoTest2","AutoTest3"};
	String newTagsPath;
	String updateTagName = "AutoTest10";

	@Test
	public void defineTagsAndApplyToLocations() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Manage Tab page under Administration menu tab
			gotoManageTagsPage();
			
			// Verify if already Category has added. if Yes, delete the Category
			Utility.deleteTags(categoryName);
			
			//Click on Define Tags tab
			getWebElementActionXpath("DefineTags_Tab").click();
			
			//Click on Create New Category button and Provide the Category name as 'Auto Test Category'
			getWebElementXpath("CreateNewCategory").click();			
			getWebElementXpath("CategoryName_Textbox").sendKeys(categoryName);				
			Thread.sleep(2000);
			//Added 3 Tags for Category
			for(int i=0;i<3;i++) {
				newTagsPath = "(//*[@id='tagList'])["+(i+1)+"]/input[1]";
				getWebElementActionXpath_D(newTagsPath).clear();
				Thread.sleep(1000);
				getWebElementXpath_D(newTagsPath).sendKeys(newTagsList[i]);
				Thread.sleep(1000);
			}
			
			//De-select 'Enable Tag based Subtotals' checkbox
			getWebElementActionXpath("EnableTagbasedSubtotals").click();
			//Click on Save button
			getWebElementActionXpath("ManageTags_SaveButton").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			
			//Click on 'Apply Tags to Location' tab
			getWebElementActionXpath("ApplyTagsToLocation_Tab").click();
			Thread.sleep(3000);
			
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
			
			Utility.moveTheScrollToTheTop();
			//Search with location "PAMTest_Trasformatore 1"
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementActionXpath("FilterByLocation").clear();
			getWebElementXpath("FilterByLocation").sendKeys("PAMTest_Trasformatore 1");
			aJaxWait();Thread.sleep(5000);
			//Select All checkbox
			getWebElementActionXpath("SelectAll_Checkbox").click();
			Thread.sleep(5000);
			//Select Category Name as 'Auto Test Category' and 'AutoTest1" as Tag
			new Select(getWebElementXpath("SelectCategory_dropdown")).selectByVisibleText(categoryName);
			Thread.sleep(5000);
			new Select(getWebElementXpath("SelectTag_dropdown")).selectByVisibleText(newTagsList[0]);
			//Click on Apply button
			getWebElementXpath("ApplyTag_button").click();
			Thread.sleep(5000);
			//Verified the popup message as '5 Locations were tagged with"AutoTest1"'
			String msg =getWebElementXpath_D( "//div[contains(@class,'modal-body')]/h4").getText();
			Assert.assertTrue(msg.contains("5 Locations were tagged with\""+newTagsList[0]+"\"") || msg.contains("The selected tag has already been applied to the selected locations."));
			getWebElementActionXpath("ManageTages_OK_button").click();
			Thread.sleep(3000);
			
			//Search with location "PAMTest_Trasformatore 2"
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementXpath("FilterByLocation").clear();
			getWebElementXpath("FilterByLocation").sendKeys("PAMTest_Trasformatore 2");
			aJaxWait();
			//Select All checkbox
			getWebElementActionXpath("SelectAll_Checkbox").click();
			Thread.sleep(2000);
			//Select Category Name as 'Auto Test Category' and 'AutoTest2" as Tag
			new Select(getWebElementXpath("SelectCategory_dropdown")).selectByVisibleText(categoryName);
			Thread.sleep(2000);
			new Select(getWebElementXpath("SelectTag_dropdown")).selectByVisibleText(newTagsList[1]);
			//Click on Apply button
			getWebElementXpath("ApplyTag_button").click();
			Thread.sleep(2000);
			//Verified the popup message as '5 Locations were tagged with "AutoTest2"'
			msg =getWebElementXpath_D( "//div[contains(@class,'modal-body')]/h4").getText();
			Assert.assertTrue(msg.contains("5 Locations were tagged with\""+newTagsList[1]+"\"") || msg.contains("The selected tag has already been applied to the selected locations."));
			getWebElementActionXpath("ManageTages_OK_button").click();
			Thread.sleep(3000);
			
			//Clicked on Config icon
			getWebElementActionXpath("Config_Icon").click();
			Thread.sleep(3000);
			//Clicked on 'Select Tag Categories' and select 'QIDM_98_AutoTestCategory' from the list
			getWebElementXpath("SelectTagCategories").click();
			Thread.sleep(3000);
			
			List<WebElement> tagList = d.findElements(By.xpath("//tr/td[4]/div/span"));
			//Assert.assertTrue(tagList.size()>0);
			String tagPath, tagName, tagCheckboxPath;
			for(int i=1; i<=tagList.size();i++) {
				//tagPath = "//tr["+i+"]/td[4]/div/span";
				tagPath = "/html/body/div/div[2]/div/mat-dialog-container/div/div/ng-component/table/tbody/tr["+i+"]/td[4]/div/span";
				tagName = d.findElement(By.xpath(tagPath)).getText();
				if(tagName.equalsIgnoreCase(categoryName)){
					tagCheckboxPath= "/html/body/div/div[2]/div/mat-dialog-container/div/div/ng-component/table/tbody/tr["+i+"]/td[3]/input";
					d.findElement(By.xpath(tagCheckboxPath)).click();
						break;
					}				
			}
			//Clicked on Update button
			getWebElementActionXpath("UpdateButton_SelectTagCategories").click();
			Thread.sleep(3000);
			
			//Search with location "PAMTest_Trasformatore"
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementXpath("FilterByLocation").clear();
			getWebElementXpath("FilterByLocation").sendKeys("PAMTest_Trasformatore");
			aJaxWait();
			//Verify the count of locations displayed on the grid, it should be count of '10'
			List<WebElement> tags = d.findElements(By.xpath("//td/span/input[contains(@class,'k-checkbox')]"));
			Assert.assertEquals(tags.size(), 10);	
			//Verified the tags on the grid should be 'Autotag1'
			for(int i=1;i<=tags.size();i++) {
				String path = "//div[contains(@class,'k-grid-content k-virtual-content')]//table[@role='presentation']/tbody/tr["+i+"]/td[1]//span/b";
				String tag = d.findElement(By.xpath(path)).getText();
				Assert.assertTrue(tag.equalsIgnoreCase("AutoTest1")||tag.equalsIgnoreCase("AutoTest2"));
			}
			
			Utility.moveTheScrollToTheTop();
			//Go to Define Tags tab
			getWebElementActionXpath("DefineTags_Tab").click();
			//Search with category name as 'Auto Test Category'
			getWebElementXpath("CategoriesTag_FilterIcon").click();			
			d.findElement(By.xpath("(//div[contains(@class,'input-group')]/input[contains(@class,'form-control')])[2]")).click();
			d.findElement(By.xpath("(//div[contains(@class,'input-group')]/input[contains(@class,'form-control')])[2]")).sendKeys(categoryName);
			Thread.sleep(2000);
			//Click on category name
			d.findElement(By.xpath("(//span[@class='info ellipsis cursor-click'])[1]/span")).click();
			Thread.sleep(2000);
			getWebElementActionXpath_D("(//*[@id='tagList'])[1]/input[1]").clear();
			Thread.sleep(1000);
			//Update the tag name as from 'AutoTest1' to 'AutoTest10'
			getWebElementXpath_D("(//*[@id='tagList'])[1]/input[1]").sendKeys(updateTagName);
			//Click on Save button
			getWebElementActionXpath("ManageTags_SaveButton").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			
			//Click on 'Apply Tags to Location' tab
			getWebElementXpath("ApplyTagsToLocation_Tab").click();
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementXpath("FilterByLocation").clear();
			getWebElementXpath("FilterByLocation").sendKeys("PAMTest_Trasformatore");
			Thread.sleep(2000);
			
			//Verified the tag name should be updated from 'AutoTag1' to 'AutoTag10'
			String tag = d.findElement(By.xpath("//td[contains(@class,'k-table-td k-touch-action-auto')]//span/b")).getText();
			Assert.assertEquals(tag, updateTagName);
						
			//Select All checkbox
			getWebElementActionXpath("SelectAll_Checkbox").click();
			Thread.sleep(2000);
			
			//Click on Remove Tags tab
			getWebElementActionXpath("RemoveTagsTab").click();
			Thread.sleep(2000);
			new Select(getWebElementXpath("SelectCategory_dropdown")).selectByVisibleText(categoryName);
			Thread.sleep(2000);
			getWebElementActionXpath("RemoveAllTags_button").click();
			Thread.sleep(2000);
			
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


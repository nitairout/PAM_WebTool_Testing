package regression.PAMAdminstrationTCs.ManageTags;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
*This test verifies filtering options available tags under 'Manage Tags' page and under 'Location panel' in PAM cards. 
*/

public class QIDM_101_TagsAndFiltering extends TestBase {
	LoginTC login = null;	
	String newTagsPath;

	@Test
	public void tagsAndFiltering() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Manage Tab page under Administration menu tab
			gotoManageTagsPage();
			
			//Click on 'Apply Tags to Location' tab
			getWebElementXpath("ApplyTagsToLocation_Tab").click();
			
			//Search with location testData.getProperty("PAMTestCapriataSaiwa")
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementXpath("FilterByLocation").sendKeys(testData.getProperty("PAMTestCapriataSaiwa"));
			Thread.sleep(3000);
			//verified only searched site is displayed
			String location  = d.findElement(By.xpath("//div[normalize-space()='PAMTest_Capriata/Saiwa']")).getText();
			Assert.assertEquals(location, testData.getProperty("PAMTestCapriataSaiwa"));
			
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
			//Clicked on Config icon
			getWebElementActionXpath("Config_Icon").click();
			Thread.sleep(3000);
			//Clicked on 'Select Tag Categories' and select 'Autotag' from the list
			getWebElementXpath("SelectTagCategories").click();
			Thread.sleep(3000);
			
			List<WebElement> tagList = d.findElements(By.xpath("//span[contains(@class,'k-treeview-leaf-text')]/span"));
			//Assert.assertTrue(tagList.size()>0);
			String tagPath, tagName, tagCheckboxPath;
			for(int i=1; i<=tagList.size();i++) {
				//tagPath = "//tr["+i+"]/td[4]/div/span";
				tagPath = "//span[@id='"+i+"']//span[contains(@class,'k-treeview-leaf-text')]/span";
				tagName = d.findElement(By.xpath(tagPath)).getText();
				if(tagName.equalsIgnoreCase("Autotag")){
					tagCheckboxPath= "/html/body/div/div[2]/div/mat-dialog-container/div/div/ng-component/table/tbody/tr["+i+"]/td[3]/input";
					d.findElement(By.xpath(tagCheckboxPath)).click();
						break;
					}				
			}
			//Clicked on Update button
			getWebElementActionXpath("UpdateButton_SelectTagCategories").click();
			Thread.sleep(3000);
			
			Utility.moveTheScrollToTheTop();
			//Filter the location by tag "tag:Autotag:Autotag1"
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementXpath("FilterByLocation").clear();
			getWebElementXpath("FilterByLocation").sendKeys("tag:Autotag:Autotag1");
			Thread.sleep(5000);
			
			List<WebElement> tags = d.findElements(By.xpath("//div[contains(@class,'k-grid-content k-virtual-content')]//table[@role='presentation']/tbody/tr/td[1]//span/b"));
			//Verified the tags on the grid should be 'Autotag1'
			for(int i=1;i<=tags.size();i++) {
				String path = "//div[contains(@class,'k-grid-content k-virtual-content')]//table[@role='presentation']/tbody/tr["+i+"]/td[1]//span/b";
				String tag = d.findElement(By.xpath(path)).getText();
				Assert.assertTrue(tag.equalsIgnoreCase("Autotag1"));
			}
			
			//Filter the location by Leafnodes "Show:Leafnodes Name:Automation"
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementXpath("FilterByLocation").clear();
			getWebElementXpath("FilterByLocation").sendKeys("Show:Leafnodes Name:Automation");
			Thread.sleep(5000);
			String[] leafNodes = {"Automation TestSite1.LB_SS_TestSource02092022_1","Automation TestSite2.LB_SS_SiteCLietnHierID2092022_1","Z_DLSourcesFromAutomation"};
			List<WebElement> leafnodesList = d.findElements(By.xpath("//div[@class='ag-pinned-left-cols-container']/div/div[2]/span/span"));
			//Verified the tags on the grid
			for(int i=0;i<leafnodesList.size();i++) {
				String path = "//div[@class='ag-pinned-left-cols-container']/div["+(i+1)+"]/div[2]/span/span";
				String tag = d.findElement(By.xpath(path)).getText();
				Assert.assertEquals(tag, leafNodes[i]);
			}
			
			//Filter the location by Sitenodes "Show:sitenodes"
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementXpath("FilterByLocation").clear();
			getWebElementXpath("FilterByLocation").sendKeys("Show:sitenodes");
			Thread.sleep(5000);
			
			String[] siteNodes = {"Automation_TestSite1","Automation_TestSite2",testData.getProperty("PAMTestCapriataSaiwa"),"PAMTest_Claremont","PAMTest_Herentals, Biscuits","PAMTest_Malanpur",testData.getProperty("PAMTestNapervilleIL"),"QA Site 1"};
			List<WebElement> siteNodesList = d.findElements(By.xpath("//table/tbody/tr/td[2]/div[2]"));
			//Verified the tags on the grid
			for(int i=0;i<siteNodesList.size();i++) {
				String path = "//table/tbody/tr["+(i+1)+"]/td[2]/div[2]";
				String tag = d.findElement(By.xpath(path)).getText();
				Assert.assertEquals(tag, siteNodes[i]);
			}
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			searchSiteInLocationList(testData.getProperty("PAMTest_Main_Meter"));
			
			//click on tag icon and select 'Autotag' from the list
			getWebElementXpath("Tag_Icon_LocationPanel").click();
			Thread.sleep(2000);
			getWebElementXpath_D("//span[contains(text(),'Autotag')]/ancestor::span/preceding::kendo-checkbox[1]/input").click();
			
			clickUsingJavascriptExecuter("//button[normalize-space()='OK']");
	        aJaxWait();
			Thread.sleep(2000);
	
			//Verified the tag mapped with the site 'PAMTest_Main Meter'
			clickUsingJavascriptExecuter("//span[contains(@class,'fa-stack')]/span");
	        aJaxWait();
			Thread.sleep(2000);
			d.findElement(By.xpath("//tr[2]/td[2]/span[contains(@class,'fa-stack')]")).click();
			Assert.assertTrue(d.findElement(By.xpath("//tr[2]/td[2]/span[contains(@class,'fa-stack')]")).getAttribute("data-bs-original-title").contains("Autotag : Autotag1"));
			
			//Filter with with "tag:Autotag:Tag 2" and find the sites mapped with this tag
			searchSiteInLocationList("tag:Autotag:Tag 2");
			
			//verified these two sites are tagged with Autotag:Tag2
			Assert.assertEquals(d.findElement(By.xpath("//tr[4]/td[1]")).getText(), "PAMTest_Banda FORNI 3-4-5");
			d.findElement(By.xpath("//tr[4]/td[2]/span[contains(@class,'fa-stack')]")).click();
			Assert.assertTrue(d.findElement(By.xpath("//tr[4]/td[2]/span[contains(@class,'fa-stack')]")).getAttribute("data-bs-original-title").contains("Autotag : Tag 2"));
			//Assert.assertTrue(d.findElement(By.xpath("((//*[@class='collapse-if'])[3]//span[@ng-show='tagConfiguration.showTags'])[1]")).getAttribute("tooltip-html-unsafe").contains("Autotag : Tag 2"));

			Assert.assertEquals(d.findElement(By.xpath("//tr[5]/td[1]")).getText(), "PAMTest_Blindo 2 area forni 3-4-5");
			d.findElement(By.xpath("//tr[5]/td[2]/span[contains(@class,'fa-stack')]")).click();
			Assert.assertTrue(d.findElement(By.xpath("//tr[5]/td[2]/span[contains(@class,'fa-stack')]")).getAttribute("data-bs-original-title").contains("Autotag : Tag 2"));
			//Assert.assertTrue(d.findElement(By.xpath("((//*[@class='collapse-if'])[3]//span[@ng-show='tagConfiguration.showTags'])[2]")).getAttribute("tooltip-html-unsafe").contains("Autotag : Tag 2"));
			
			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
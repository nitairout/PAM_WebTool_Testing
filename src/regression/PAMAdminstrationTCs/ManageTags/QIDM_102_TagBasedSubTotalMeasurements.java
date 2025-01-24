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

/*This test verifies functionality of tag based subtotal measurements.
 * First it creates new tag category with subtotal measurements enabled for Energy measurements.
 * Then it applies to the location and go the PAM card and load chart for this tab based measurement 
 * and verifies legend, stats and table data. 
 * Then it pushes the same chart to the widget and verify every is as expected. 
 * at the end it also verified this measurement is displayed in manage measurement page under 'tag based sub totals.
*/
public class QIDM_102_TagBasedSubTotalMeasurements extends TestBase {
	LoginTC login = null;
	String categoryName = "QIDM102 Test";
	String tagName = "Tag 1";	
	String savedAnalysisName = "QIDM_102_SavedAnalysis";

	@Test
	public void tagBasedSubTotalMeasurements() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'Temp Dashboard' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "Temp Dashboard");
			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			goToAnalysisPage();
			Utility.clearsavedanalysis(d);

			// Navigate to Manage Tab page under Administration menu tab
			gotoManageTagsPage();

			// Verify if already Category has added. if Yes, delete the Category
			Utility.deleteTags(categoryName);

			// Click on Create New Category button and Provide the Category name as 'Auto Test Category'
			getWebElementXpath("CreateNewCategory").click();
			getWebElementXpath("CategoryName_Textbox").sendKeys(categoryName);
			Thread.sleep(2000);
			getWebElementXpath("Category_Description").click();
			getWebElementXpath("Category_Description").sendKeys("Test Description");			
			
			// Remove third tag
			getWebElementActionXpath_D("(//*[@class='fa fa-remove fa-lg clickable'])[3]").click();
			
			// Remove second tag
			getWebElementActionXpath_D("(//*[@class='fa fa-remove fa-lg clickable'])[2]").click();

			// Click on 'Select Measurements' button
			getWebElementActionXpath("SelectMeasurements_button").click();
			Thread.sleep(3000);
			// Deselect all
			getWebElementActionXpath_D("//span[contains(text(),'Deselect All')]").click();
			Thread.sleep(3000);
			// Click on filter
			getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
			getWebElementXpath_D("//input[@id='commodityModalFilter']").sendKeys("Energy");
			
			//getWebElementActionXpath_D("(//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'expand')])[2]").click();
			Thread.sleep(1000);//Click on filter
			getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
			getWebElementXpath_D("//input[@id='commodityModalFilter']").sendKeys("Energy");
			Thread.sleep(2000);
			//click on measurement filter icon
			getWebElementActionXpath_D("//kendo-grid-filter-menu/a[@title='Measurements Filter Menu']//kendo-svgicon").click();
			Thread.sleep(1000);
			//click on filter drop down
			getWebElementActionXpath_D("(//div[contains(@id,'k-grid') and contains(@id,'filter-menu')]//kendo-dropdownlist)[1]").click();
			Thread.sleep(1000);
			//Select the value from the drop down
			getWebElementXpath_D("//span[contains(.,'Is equal to')]").click();
			Thread.sleep(1000);
			//Enter the measurement name to filter
			getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").clear();
			getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").sendKeys("Energy");
			Thread.sleep(2000);
			//Click on filter button
			getWebElementActionXpath_D("//button[contains(text(),'Filter')]").click();
			Thread.sleep(2000);
			
			//click on plus icon to select measurement
			getWebElementActionXpath_D("//i[contains(@class,'fa fa-lg clickable fa-plus-circle')]").click();
			Thread.sleep(1000);
			//save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);
			
			// Click on Save button
			getWebElementActionXpath("ManageTags_SaveButton").click();
			aJaxWait();
			Utility.moveTheScrollToTheTop();

			// Click on 'Apply Tags to Location' tab
			getWebElementXpath("ApplyTagsToLocation_Tab").click();
			aJaxWait();
			//Clicked on Config icon
			getWebElementActionXpath("Config_Icon").click();
			Thread.sleep(3000);
			//Clicked on 'Select Tag Categories'
			getWebElementActionXpath("SelectTagCategories").click();
			Thread.sleep(3000);
			
			//unselected all the tags from the list
			List<WebElement> selectedtagList = d.findElements(By.xpath("//td/input[@class='ng-pristine ng-untouched ng-valid ng-not-empty']"));
			for(int i=1;i<=selectedtagList.size();i++) {
				getWebElementActionXpath_D("(//td/input[@class='ng-pristine ng-untouched ng-valid ng-not-empty'])[1]").click();
			}
			//Clicked on Update button
			getWebElementActionXpath("UpdateButton_SelectTagCategories").click();
			Thread.sleep(3000);
			
			Utility.moveTheScrollToTheTop();
			
			// Search with location "PAMTest_Energy Balance"
			getWebElementActionXpath("FilterByLocation").click();
			getWebElementXpath("FilterByLocation").clear();
			getWebElementXpath("FilterByLocation").sendKeys("PAMTest_Energy Balance");
			aJaxWait();
			Utility.moveTheScrollToTheTop();
			// Select 'PAMTest_Energy Balance' under PAMTest_Capriata/Saiwa checkbox
			//getWebElementActionXpath("SelectAll_Checkbox").click();
			getWebElementActionXpath_D("//tbody/tr[1]/td[@role='gridcell']/span/input").click();
			Thread.sleep(5000);
			// Select Category Name as 'QIDM102 Test' and 'Tag 1" as Tag
			new Select(getWebElementXpath("SelectCategory_dropdown")).selectByVisibleText(categoryName);
			Thread.sleep(2000);
			new Select(getWebElementXpath("SelectTag_dropdown")).selectByVisibleText(tagName);
			// Click on Apply button
			getWebElementXpath("ApplyTag_button").click();
			aJaxWait();
			// Verified the popup message as '5 Locations were tagged with "AutoTest1"'
			String msg =getWebElementXpath_D( "//div[contains(@class,'modal-body')]/h4").getText();
			Assert.assertEquals(msg, "1 Locations were tagged with\"" + tagName + "\"");
			getWebElementActionXpath("ManageTages_OK_button").click();
			Thread.sleep(3000);
			
			//Go to PAM page
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			d.navigate().refresh();
			Thread.sleep(10000);
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//i[contains(@class,'se-icon-electricity-o')]").click();
			Thread.sleep(2000);
			
			//Deselect all
			getWebElementActionXpath_D("//*[contains(@class,'se-btn-deselect-measures')]").click();
			Thread.sleep(2000);
			
			//Checking if both the standard and userdefind=ed measurements are in collapsed state
			int meaurementTypeArr=d.findElements(By.xpath("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]")).size();
			if(meaurementTypeArr>0)
				getWebElementActionXpath_D("//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'collapse')]").click();
			
			getWebElementActionXpath_D("(//kendo-tabstrip//kendo-panelbar-item[contains(@id,'k-panelbar') and contains(@id,'item-default')]//kendo-svgicon[contains(@class,'expand')])[1]").click();
			Thread.sleep(1000);
			
			//Click on filter
			/*getWebElementActionXpath_D("//input[@id='commodityModalFilter']").clear();
			getWebElementXpath_D("//input[@id='commodityModalFilter']").sendKeys(categoryName);*/
			Thread.sleep(2000);
			//click on measurement filter icon
			getWebElementActionXpath_D("//kendo-grid-filter-menu/a[@title='Measurements Filter Menu']//kendo-svgicon").click();
			Thread.sleep(1000);
			//click on filter drop down
			getWebElementActionXpath_D("(//div[contains(@id,'k-grid') and contains(@id,'filter-menu')]//kendo-dropdownlist)[1]").click();
			Thread.sleep(1000);
			//Select the value from the drop down
			getWebElementXpath_D("//span[contains(.,'Is equal to')]").click();
			Thread.sleep(1000);
			//Enter the measurement name to filter
			getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").clear();
			getWebElementActionXpath_D("(//kendo-grid-filter-menu-input-wrapper/input)[1]").sendKeys("QIDM102 Test-Tag 1 ENERGY");
			Thread.sleep(2000);
			//Click on filter button
			getWebElementActionXpath_D("//button[contains(text(),'Filter')]").click();
			Thread.sleep(2000);
			
			//click on plus icon to select measurement
			getWebElementActionXpath_D("//i[contains(@class,'fa fa-lg clickable fa-plus-circle')]").click();
			//getWebElementActionXpath_D("//*[@id='commodityModalForm']//se-collapse[@identifier='"+measurementCategory+"']//i[@class='fa fa-lg clickable fa-plus-circle']").click();
			Thread.sleep(1000);
			//save and close
			getWebElementActionXpath_D("//span[contains(text(),'Save & Close')]").click();
			Thread.sleep(5000);
						
			//Search for site and select the measurement
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			// Select the Fixed date range of '1/1/2023' - '1/31/2023'
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			refreshToLoadTheChart();

			String legendName1 = getWebElementXpath("ColumnLegendOne").getText();
			//Updating from 'Location Measurement Unit' to 'Location Unit' - on 'May 5th 2024'
			//Assert.assertEquals(legendName1.replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa QIDM102 Test-Tag1 ENERGY kWh");
			Assert.assertEquals(legendName1.replaceAll("\\n", " "), "PAMTest_Capriata/Saiwa kWh");
			
			//Verify measurement on chart data tooltip
			List<String> chartTooltipValues=getBarToolTip();
			int dateCounter=0;
			
			//verification of the measurement 'QIDM102 Test-Tag 1 ENERGY' should be display on the chart
			for(String value:chartTooltipValues) {
				if(!value.contains("QIDM102 Test-Tag 1 ENERGY")) 
					throw new Exception("chart should be populate value for 'QIDM102 Test-Tag 1 ENERGY' measurement");
				dateCounter++;
				if(dateCounter==30)
					break;
			}
			
			//Verify statistics label
			Assert.assertEquals(getWebElementXpath_D("(//pam-statistic//*[@class='se-statistic-title'])[1]").getText(), "Total QIDM102 Test-Tag 1 ENERGY");
			//Table data verification started
			Utility.moveTheScrollToTheDown();
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
			//Table Header verification
			Assert.assertEquals(getWebElementActionXpath_D("//*[@id='tablehead']/tr[2]/th[1]").getText(), "QIDM102 Test-Tag 1 ENERGY (kWh)");
			
			saveAnalysis(savedAnalysisName); // Pushed the widget to dashboard
			pushwidget("Temp Dashboard","Automation Test Dashboards");
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(15000);
			
			getWebElementActionXpath("WidgetTableTab").click();
			Thread.sleep(3000);
			
			Assert.assertEquals(d.findElement(By.xpath("//*[@id='tablehead']/tr[2]/th[1]")).getText(), "QIDM102 Test-Tag 1 ENERGY (kWh)");

			login.logout();
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	}
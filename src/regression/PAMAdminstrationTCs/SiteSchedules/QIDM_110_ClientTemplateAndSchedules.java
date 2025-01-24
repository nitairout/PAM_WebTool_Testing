package regression.PAMAdminstrationTCs.SiteSchedules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies creating template for the client under 'Site Schedule' page. 
 * Also verifies schedule periods are displayed in Trend Analysis chart and 
 * table data as specified in Site Schedule for Week day and Week End.
 */
public class QIDM_110_ClientTemplateAndSchedules extends TestBase {
	LoginTC login = null;
	String templateName = "QIDM110_Client Template";
	String templateDescription = "AutoTest";
	String scheduleName = "Auto Test- Days";

	@Test
	public void clientTemplateAndSchedules() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Site Schedules page under Administration menu tab
			gotoSiteSchedulesPage();
			
			//Verify site schedule already available in the grid. if yes, delete the site schedule
			int templateListSize = d.findElements(By.xpath("//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr")).size();
			String templateNameFromGrid,templatePath;
			for(int i=1;i<=templateListSize;i++) {
				templatePath="//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[2]/span";
				explicitWait_Xpath(templatePath);
				templateNameFromGrid=getWebElementActionXpath_D(templatePath).getText();
				if(templateNameFromGrid.equalsIgnoreCase(templateName)) {
					//delete the existing site schedule
					String deleteIconPath = "//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]//button[@type='button']";
					getWebElementActionXpath_D(deleteIconPath).click();
					Thread.sleep(5000);
					getWebElementActionXpath_D("//input[@value='Ok']").click();
					printLog("Deleted the Existing scheduled template '"+templateName+"'");
					break;
				}
			}
			
			//Created new Site template with name 'ClientTemplate'
			createSiteTemplate(templateName,templateDescription);
			
			//Click on Schedule Tab
			getWebElementXpath("SchedulesTab_SiteSchedule").click();
			Thread.sleep(2000);
			
			//Click on Create New Schedule button
			getWebElementXpath("CreateNew_Schedule").click();
			Thread.sleep(4000);					 
			
			//Click on Template dropdown
			getWebElementXpath_D("//*[@class='k-svg-i-caret-alt-down k-button-icon k-svg-icon k-icon ng-star-inserted']").click();
			Thread.sleep(2000);
			
			//Selected newly created template 'QIDM110_Client Template' 
			getWebElementXpath_D("//span[contains(text(),'"+templateName+"')]").click();
			
			//Provide Schedule name as 'Auto Test Store Hours'
			getWebElementXpath_D("(//*[@class='k-input-inner'])[2]").sendKeys(scheduleName);
			
			//Click on Save and Next button
			//getWebElementXpath("SaveAndNext_button_SetupTab").click();
			getWebElementActionXpath_D("//span[contains(text(),'Save & Next')]").click();
			//getWebElementActionXpath_D("(//a[contains(@class,'buttonMain')])[2]").click();
			Thread.sleep(8000);
			
			//Provide the time range from Calendar from Details tab 
			for(int i=1;i<=5;i++) {
				String path = "//div[@class='k-scheduler-content']/table[@class='k-scheduler-table']//tr[1]/td["+(i+1)+"]";
				Actions actions = new Actions(d);
				WebElement elementLocator = d.findElement(By.xpath(path));
				actions.doubleClick(elementLocator).perform();
				aJaxWait();
				
				//Provide the Start time 00:00 and end time 24:00
				
				getWebElementXpath_D("(//input[@class='k-input-inner'])[1]").click();
				getWebElementXpath_D("(//input[@class='k-input-inner'])[1]").clear();
				getWebElementXpath_D("(//input[@class='k-input-inner'])[1]").sendKeys("00:00");
				getWebElementXpath_D("//span[contains(text(),'Start:')]").click();
				Thread.sleep(3000);
				getWebElementXpath_D("(//input[@class='k-input-inner'])[2]").click();
				getWebElementXpath_D("(//input[@class='k-input-inner'])[2]").clear();
				getWebElementXpath_D("(//input[@class='k-input-inner'])[2]").sendKeys("24:00");
				getWebElementXpath_D("//span[contains(text(),'Start:')]").click();
				Thread.sleep(3000);

				//Clicked on the Period dropdown
				getWebElementActionXpath_D("//table/tr[4]/td[2]/kendo-dropdownlist").click();
				
				//Select the Period as 'Open'
				getWebElementActionXpath_D("//li[contains(.,'Weekday')]").click();
				Thread.sleep(2000);

				//Click on Save button from popup
				getWebElementXpath_D("(//*[@class='buttonMain'])[4]").click();
				aJaxWait();
			}
			
			//Click on 'Mark empty time period as:' dropdown
			getWebElementXpath_D("(//*[@class='k-input-inner'])[1]").click();
			Thread.sleep(1000);
			getWebElementXpath_D("//li[contains(.,'Weekend')]").click();
			aJaxWait();
			
			//Click on Save button
			getWebElementXpath_D("//span[normalize-space()='Save']").click();
			Thread.sleep(10000);
			
			//Click on Save & Next button
			getWebElementXpath_D("//span[contains(text(),'Save & Next')]").click();
			Thread.sleep(15000);
			Utility.moveTheScrollToTheDown();
			
			//Click on Next button from 'Special Days' tab
			getWebElementActionXpath_D("//span[contains(text(),'Next')]").click();
			Thread.sleep(3000);
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Trend Analysis card page
			goToPAMCard("TrendAnalysisCard");
			
			//Search with the site 'PAMTest_Capriata/Saiwa' 
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			//Select the Electricity - Energy measurement
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			//Provided the fixed date range from '01/02/2023 - 01/07/2023'
			addFixedDateRange("1/1/2023", "1/7/2023");
			refreshToLoadTheChart();
			
			//Selected data breakdown as Site Schedule and select the template name as 'QIDM110_Client Template'
			dataBreakDownSiteSchedule("Site Schedule",templateName);
			Thread.sleep(5000);
			
			//Verify the legends under the chart
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh (Weekday)");
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), "PAMTest_Capriata/Saiwa kWh (Weekend)");
			printLog("Verified legends from chart!!");

			//Select the time interval - By hour
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By day");
			Utility.moveTheScrollToTheDown();
			//Click on Table Data tab
			getWebElementActionXpath("DataTableTab").click();
			Thread.sleep(2000);
						
			String byDayTableData[]={"1/1/2023~~9,459","1/2/2023~11,854~","1/3/2023~3,847~","1/4/2023~2,246~","1/5/2023~1,963~","1/6/2023~7,773~","1/7/2023~~8,726"};
			String data,timeStampPath,weekDayValuePath,weekEndValuePath,expectedTimeStamp,expectedweekDayValue,expectedweekEndValue,actualTimeStamp,actualweekDayValue,actualweekEndValue;
			for(int i=0;i<7;i++) {
				timeStampPath = "//*[@id='tablebody']/tr["+(i+1)+"]/th";
				weekDayValuePath = "//*[@id='tablebody']/tr["+(i+1)+"]/td[1]";
				weekEndValuePath = "//*[@id='tablebody']/tr["+(i+1)+"]/td[3]";
				data = byDayTableData[i];
				expectedTimeStamp = data.split("~", -1)[0];
				expectedweekDayValue =data.split("~", -1)[1];
				expectedweekEndValue = data.split("~", -1)[2];
				
				actualTimeStamp = getWebElementActionXpath_D(timeStampPath).getText();
				Assert.assertEquals(actualTimeStamp,expectedTimeStamp);
				
				actualweekDayValue = getWebElementActionXpath_D(weekDayValuePath).getText();
				Assert.assertEquals(actualweekDayValue,expectedweekDayValue);
				
				actualweekEndValue = getWebElementActionXpath_D(weekEndValuePath).getText();
				Assert.assertEquals(actualweekEndValue,expectedweekEndValue);
			}
			Utility.moveTheScrollToTheTop();
			//De-Select the Electricity - Energy measurement
			getWebElementActionXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			Thread.sleep(2000);
			
			//Search with the site 'PAMTest_Naperville, IL' 
			searchSiteInLocationList(testData.getProperty("PAMTestNapervilleIL"));
			//Select the Electricity - Energy measurement
			getWebElementXpath("PAMTest_NapervilleIL_Energy").click();
			refreshToLoadTheChart();
			
			//Selected data breakdown as Site Schedule and select the template name as 'QIDM110_Client Template'
			dataBreakDownSiteSchedule("Site Schedule",templateName);
			Thread.sleep(5000);
			
			//Verify the legends under the chart
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Naperville, IL kWh (Weekday)");
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), "PAMTest_Naperville, IL kWh (Weekend)");
			
			login.logout();
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}



	private void createSiteTemplate(String template, String templateDesc) throws Throwable {
		try {
		//Click on Templates tab
		getWebElementActionXpath_D("(//*[@class='k-link ng-star-inserted'])[1]").click();
		Thread.sleep(3000);
		
		//Verified the newly added template is already available in the grid if yes, delete the template
		int templateListSize = d.findElements(By.xpath("//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr")).size();
		String templateNameFromGrid,templatePath;
		String deleteIconPath = "";
		for(int i=1;i<=templateListSize;i++) {
			templatePath="//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[1]/span";
			templateNameFromGrid=getWebElementActionXpath_D(templatePath).getText();
			if(templateNameFromGrid.equalsIgnoreCase(template)) {
				//delete the existing site schedule
				deleteIconPath = "//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[6]//button";
				getWebElementActionXpath_D(deleteIconPath).click();
				Thread.sleep(2000);
				getWebElementActionXpath_D("//input[@class='btn btn-ok' and @value='Ok'] ").click();
				Thread.sleep(2000);
				printLog("Verified the newly added template is deleted");
				break;
			}
		}
		
		//Click on Create New Template button
		getWebElementXpath("CreateNew_Template").click();
		Thread.sleep(3000);
		Assert.assertEquals(getWebElementXpath_D("//span[@class='page-title']").getText(), "Create New Template");
		
		//Provide template name as 'QIDM109_Test Template'
		getWebElementXpath("TemplateName").sendKeys(template);
		
		//Provide template description as 'Auto Test'
		getWebElementXpath("TemplateDescription").sendKeys(templateDesc);

		//Provide Period Name as 'Open'
		getWebElementXpath("PeriodNames").sendKeys("Weekday");
		getWebElementXpath("AddButton_PeriodNames").click();
		Thread.sleep(3000);
		
		getWebElementActionXpath("PeriodNames").click();
		getWebElementActionXpath("PeriodNames").clear();
		//Provide Period Name as 'Close'
		getWebElementActionXpath("PeriodNames").sendKeys("Weekend");
		getWebElementXpath("AddButton_PeriodNames").click();
		Thread.sleep(3000);
		
		//Select the client checkbox
		//getWebElementActionXpath_D("//input[@class='ng-pristine ng-valid ng-touched']").click();
		getWebElementActionXpath_D("//input[@ng-show='showGlobal']").click();
		Thread.sleep(3000);
		Utility.moveTheScrollToTheDown();
		//Click on Save button
		getWebElementXpath("SaveButton_CreateNewTemplate").click();
		Thread.sleep(5000);
		
		//Verified the newly added template is available in the grid
		templateListSize = d.findElements(By.xpath("//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr")).size();
		String newTemplateToVerify="";
		for(int i=1;i<=templateListSize;i++) {
			templateNameFromGrid=getWebElementActionXpath_D("//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[1]/span").getText();
			if(templateNameFromGrid.equalsIgnoreCase(template)) {
				newTemplateToVerify=templateNameFromGrid;
				break;
			}
		}
		Assert.assertEquals(newTemplateToVerify, template);
		printLog("Verified the newly added template.");
		
	} catch (Throwable e) {
		throw e;
	}
	}

}

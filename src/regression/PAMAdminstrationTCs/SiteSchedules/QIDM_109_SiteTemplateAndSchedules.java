package regression.PAMAdminstrationTCs.SiteSchedules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;
/*
 * This test verifies creating Site schedule template for specific sites under 'Site Schedule' page.
 * Also verifies schedule periods are displayed in Trend Analysis chart 
 * and table data as specified in Site Schedule.
 */
public class QIDM_109_SiteTemplateAndSchedules extends TestBase {
	LoginTC login = null;
	String templateName = "QIDM109_Test Template";
	String templateDescription = "Auto Test";
	String scheduleName = "Auto Test-Store Hours";

	@Test
	public void siteTemplateAndSchedules() throws Throwable {
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
					printLog("Deleted the Existing scheduled template 'SiteSchedule'");
					break;
				}
			}
			
			//Created new Site template with name 'QIDM109_Test Template'
			createSiteTemplate(templateName,templateDescription);
			
			//Click on Schedule Tab
			getWebElementXpath("SchedulesTab_SiteSchedule").click();
			Thread.sleep(2000);
			
			//Click on Create New Schedule button
			getWebElementXpath("CreateNew_Schedule").click();
			Thread.sleep(4000);					 
			
			//Click on Template dropdown
			getWebElementXpath_D("//*[contains(@class,'k-svg-i-caret-alt-down k-button-icon k-svg-icon k-icon')]").click();
			Thread.sleep(2000);
			
			//Selected newly created template 'QIDM109_Test Template' 
			getWebElementXpath_D("//span[contains(text(),'"+templateName+"')]").click();
			
			//Provide Schedule name as 'Auto Test Store Hours'
			getWebElementXpath_D("(//*[@class='k-input-inner'])[2]").sendKeys(scheduleName);
			
			//Click on Save and Next button
			getWebElementActionXpath_D("//span[contains(text(),'Save & Next')]").click();
			Thread.sleep(8000);
			
			//Provide the time range from Calendar from Details tab 
			for(int i=1;i<=5;i++) {
				String path = "//div[@class='k-scheduler-content']/table[@class='k-scheduler-table']//tr[1]/td["+(i+1)+"]";
				Actions actions = new Actions(d);
				WebElement elementLocator = d.findElement(By.xpath(path));
				actions.doubleClick(elementLocator).perform();
				aJaxWait();
				
				//Provide the Start time 09:00				
				getWebElementXpath_D("(//input[@class='k-input-inner'])[1]").click();
				getWebElementXpath_D("(//input[@class='k-input-inner'])[1]").clear();
				getWebElementXpath_D("(//input[@class='k-input-inner'])[1]").sendKeys("09:00");
				getWebElementXpath_D("//span[contains(text(),'Start:')]").click();
				Thread.sleep(3000);
				getWebElementXpath_D("(//input[@class='k-input-inner'])[2]").click();
				getWebElementXpath_D("(//input[@class='k-input-inner'])[2]").clear();
				getWebElementXpath_D("(//input[@class='k-input-inner'])[2]").sendKeys("17:00");
				getWebElementXpath_D("//span[contains(text(),'Start:')]").click();
				Thread.sleep(3000);
				
			    //Clicked on the Period dropdown
				getWebElementActionXpath_D("//table/tr[4]/td[2]/kendo-dropdownlist").click();
				//Select the Period as 'Open'
				getWebElementActionXpath_D("//li[contains(.,'Open')]").click();
				Thread.sleep(3000);
				//Click on Save button from popup
				getWebElementXpath_D("(//*[@class='buttonMain'])[4]").click();
				Thread.sleep(3000);
				aJaxWait();
			}
			
			//Click on 'Mark empty time period as:' dropdown
			getWebElementXpath_D("(//*[@class='k-input-inner'])[1]").click();
			Thread.sleep(3000);
			
			//Select Close option
			getWebElementXpath_D("//li[contains(.,'Close')]").click();
			aJaxWait();
			
			//Click on save button
			getWebElementActionXpath_D("//span[normalize-space()='Save']").click();
			Thread.sleep(10000);
			
			//Click on Save & Next button
			WebElement xpathToCLick = d.findElement(By.xpath("//span[normalize-space()='Save & Next']"));
			JavascriptExecutor js = (JavascriptExecutor) d;
			js.executeScript("arguments[0].click();", xpathToCLick);
			Thread.sleep(15000);
			Utility.moveTheScrollToTheDown();
			
			//Click on Next button from 'Special Days' tab
			getWebElementActionXpath_D("//span[contains(text(),'Next')]").click();
			Thread.sleep(3000);
			
			//Click on Site Tab from Participents tab
			getWebElementActionXpath_D("//span[normalize-space()='Site']").click();
			Thread.sleep(2000);
			
			//Click on Site Search
			getWebElementActionXpath_D("//input[@class='light content-text-padding']").click();
			getWebElementActionXpath_D("//*[@class='light content-text-padding']").click();
			Thread.sleep(2000);
			//Selected the Site 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D("//span[contains(text(),'PAMTest_Capriata/Saiwa')]").click();
			Thread.sleep(2000);
			
			//Click on Addsites '+' button
			getWebElementActionXpath_D("//span[contains(text(),'Add sites')]").click();
			Thread.sleep(2000);
			
			//Click on 'Done' button
			getWebElementXpath_D("//span[contains(text(),'Done')]").click();
			Thread.sleep(3000);
			
			
			boolean addedSiteSchedule = false;
			//Verify newly added Site schedule 'SiteSchedule' available in the grid.
			int templateList = d.findElements(By.xpath("//tbody/tr[contains(@class,'k-master-row')]")).size();
			for(int i=1;i<=templateList;i++) {
				templatePath="//tbody/tr[contains(@class,'k-master-row')]["+i+"]/td[2]/span";
				explicitWait_Xpath(templatePath);
				templateNameFromGrid=getWebElementActionXpath_D(templatePath).getText();
				if(templateNameFromGrid.equalsIgnoreCase(templateName)) {
					printLog("Verify newly added site schedule 'SiteSchedule' available in the grid.");
					addedSiteSchedule = true;
					break;
				}
			}
			
			//Verified site scheduled should be added.
			Assert.assertTrue(addedSiteSchedule);
			
			//Navigate to PAM page
			goToAnalysisPage();
			
			//Navigate to Trend Analysis card page
			goToPAMCard("TrendAnalysisCard");
			
			//Search with the site 'PAMTest_Capriata/Saiwa' 
			searchSiteInLocationList(testData.getProperty("PAMTestCapriataSaiwa"));
			
			//Select the Electricity - Energy measurement
			getWebElementXpath("PAMTest_CapriataSaiwa_Energy").click();
			
			//Provided the fixed date range from '01/02/2023 - 01/02/2023'
			addFixedDateRange("1/2/2023", "1/2/2023");
			refreshToLoadTheChart();
			Thread.sleep(5000);
		    Utility.moveTheScrollToTheDown();
			//Selected data breakdown as Site Schedule and select the template name as 'QIDM109_Test Template'
			dataBreakDownSiteSchedule("Site Schedule",templateName);
			printLog("Selected 'QIDM109_Test Template' fom Site Schedule from DataBreakdown");
			Thread.sleep(5000);
			
			//Verify the legends under the chart
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh (Close)");
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), "PAMTest_Capriata/Saiwa kWh (Open)");
			printLog("Verified legends from chart!!");
			Thread.sleep(5000);
			
			//Click on Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(3000);
						
			String byDayTableData[]={"1/2/2023 01:00 AM~680.0~","1/2/2023 02:00 AM~708.0~","1/2/2023 03:00 AM~779.0~","1/2/2023 04:00 AM~798.0~",
					 "1/2/2023 05:00 AM~865.0~","1/2/2023 06:00 AM~961.0~","1/2/2023 07:00 AM~1,079~","1/2/2023 08:00 AM~1,133~",
					 "1/2/2023 09:00 AM~1,253~","1/2/2023 10:00 AM~~1,288","1/2/2023 11:00 AM~~240.0","1/2/2023 12:00 PM~~151.0",
					 "1/2/2023 01:00 PM~~128.0","1/2/2023 02:00 PM~~85.00","1/2/2023 03:00 PM~~104.0","1/2/2023 04:00 PM~~144.0",
					 "1/2/2023 05:00 PM~~134.0","1/2/2023 06:00 PM~108.0~","1/2/2023 07:00 PM~200.0~","1/2/2023 08:00 PM~250.0~",
					 "1/2/2023 09:00 PM~193.0~","1/2/2023 10:00 PM~158.0~","1/2/2023 11:00 PM~221.0~","1/3/2023 12:00 AM~194.0~"};
			String data,timeStampPath,closeValuePath,openValuePath,actualTimeStamp,actualCloseValue,actualOpenValue,expectedTimeStamp,expectedCloseValue,expectedOpenValue;
			for(int i=0;i<24;i++) {
				timeStampPath = "//*[@id='tablebody']/tr["+(i+1)+"]/th";
				closeValuePath = "//*[@id='tablebody']/tr["+(i+1)+"]/td[1]";
				openValuePath = "//*[@id='tablebody']/tr["+(i+1)+"]/td[3]";
				data = byDayTableData[i];
				expectedTimeStamp = data.split("~", -1)[0];
				expectedCloseValue =data.split("~", -1)[1];
				expectedOpenValue = data.split("~", -1)[2];
				
				actualTimeStamp = getWebElementActionXpath_D(timeStampPath).getText();
				Assert.assertEquals(actualTimeStamp,expectedTimeStamp);
				
				actualCloseValue = getWebElementActionXpath_D(closeValuePath).getText();
				Assert.assertEquals(actualCloseValue,expectedCloseValue);
				
				actualOpenValue = getWebElementActionXpath_D(openValuePath).getText();
				Assert.assertEquals(actualOpenValue,expectedOpenValue);
			}
			Utility.moveTheScrollToTheTop();
			//De-Select the Electricity - Energy measurement
			getWebElementActionXpath("PAMTest_CapriataSaiwa_Energy").click();
			refreshToLoadTheChart();
			Thread.sleep(5000);
			
			//Search with the site 'PAMTest_Naperville, IL' 
			searchSiteInLocationList(testData.getProperty("PAMTestNapervilleIL"));
			//Select the Electricity - Energy measurement
			getWebElementXpath("PAMTest_NapervilleIL_Energy").click();
			refreshToLoadTheChart();
			Thread.sleep(3000);
			 
			//Verify the legends under the chart
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Naperville, IL kWh");
			Utility.moveTheScrollToTheDown();
			 
			//Verify the template available for unassigned site
			getWebElementActionXpath("BreakDownType").click();
			Thread.sleep(1000);
			Assert.assertTrue(d.findElements(By.xpath("//span[normalize-space()='"+templateName+"']")).size()==0);
			
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
		String editLinkPath = "";
		for(int i=1;i<=templateListSize;i++) {
			templatePath="//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[1]/span";
			templateNameFromGrid=getWebElementActionXpath_D(templatePath).getText();
			if(templateNameFromGrid.equalsIgnoreCase(template)) {
				//delete the existing site schedule
				editLinkPath = "//kendo-grid-list/div/div[@class='k-grid-table-wrap']/table/tbody/tr["+i+"]/td[5]/a/span";
				getWebElementActionXpath_D(editLinkPath).click();
				Thread.sleep(2000);
				Utility.moveTheScrollToTheDown();
				getWebElementActionXpath_D("//button[@class='buttonMain']/span[text()='Delete']").click();
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
		getWebElementXpath("PeriodNames").sendKeys("Open");
		getWebElementXpath("AddButton_PeriodNames").click();
		Thread.sleep(3000);
		
		getWebElementActionXpath("PeriodNames").click();
		getWebElementActionXpath("PeriodNames").clear();
		//Provide Period Name as 'Close'
		getWebElementActionXpath("PeriodNames").sendKeys("Close");
		getWebElementXpath("AddButton_PeriodNames").click();
		Thread.sleep(3000);
		
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

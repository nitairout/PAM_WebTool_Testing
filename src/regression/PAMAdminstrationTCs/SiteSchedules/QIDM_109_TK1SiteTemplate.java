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
 * This test verifies creating Site schedule template for specific sites under 'Site Schedule' page.
 * Also verifies schedule periods are displayed in Trend Analysis chart 
 * and table data as specified in Site Schedule.
 */
public class QIDM_109_TK1SiteTemplate extends TestBase {
	LoginTC login = null;
	String templateName = "QIDM109 Test Template";
	String templateDescription = "Auto Test";
	String scheduleName = "Auto Test Store Hours";

	@Test
	public void siteTemplateAndSchedules() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Navigate to Site Schedules page under Administration menu tab
			gotoSiteSchedulesPage();
			
			//Verify site schedule already available in the grid. if yes, delete the site schedule
			List<WebElement>templateList = d.findElements(By.xpath("/html/body/form/div[5]/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[3]/div[3]/table/tbody/tr"));
			String templateNameFromGrid,templatePath;
			for(int i=1;i<=templateList.size();i++) {
				templatePath="/html/body/form/div[5]/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[3]/div[3]/table/tbody/tr["+i+"]/td[2]/span";
				explicitWait_Xpath(templatePath);
				templateNameFromGrid=getWebElementActionXpath_D(templatePath).getText();
				if(templateNameFromGrid.equalsIgnoreCase(templateName)) {
					//delete the existing site schedule
					String deleteIconPath = "/html/body/form/div[5]/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[3]/div[3]/table/tbody/tr["+i+"]/td[7]/a";
					getWebElementActionXpath_D(deleteIconPath).click();
					Thread.sleep(5000);
					getWebElementActionXpath_D("//span[contains(text(),'OK')]").click();
					printLog("Deleted the Existing scheduled template 'QIDM109_Test Template'");
					break;
				}
			}
			
			//Created new Site template with name 'QIDM109_Test Template'
			createSiteTemplate(templateName,templateDescription);
			
			//Click on Schedule Tab
			getWebElementXpath("SchedulesTab_SiteSchedule").click();
			Thread.sleep(2000);
			
			//Click on Create New Schedule button
			getWebElementXpath_D("//span[contains(text(),'Create New Schedule')]").click();
			Thread.sleep(2000);					 
			
			//Click on Template dropdown
			getWebElementXpath_D("//*[@class='bx-field-table']/tbody/tr[1]/td[2]/span").click();
			Thread.sleep(2000);
			
			//Selected newly created template 'QIDM109_Test Template' 
			getWebElementXpath_D("//span[contains(text(),'QIDM109 Test Template')]").click();
			
			//Provide Schedule name as 'Auto Test'
			getWebElementXpath("ScheduleName_SetupTab").sendKeys(scheduleName);
			//Click on Save and Next button
			getWebElementXpath("SaveAndNext_button_SetupTab").click();
			Thread.sleep(5000);
			
			//Provide the time range from Calendar from Details tab 
			for(int i=1;i<=5;i++) {
				String path = "//div[@class='k-scheduler-content']/table[@class='k-scheduler-table']//tr[1]/td["+(i+1)+"]";
				Actions actions = new Actions(d);
				WebElement elementLocator = d.findElement(By.xpath(path));
				actions.doubleClick(elementLocator).perform();
				aJaxWait();
				
				//Provide the Start time 09:00
				getWebElementXpath_D("(//input[@id='tmPicker'])[1]").click();
				getWebElementXpath_D("(//input[@id='tmPicker'])[1]").clear();
				getWebElementXpath_D("(//input[@id='tmPicker'])[1]").sendKeys("09:00");
				Thread.sleep(1000);
			
				//Provide the End time 17:00
				getWebElementXpath_D("(//input[@id='tmPicker'])[2]").click();
				getWebElementXpath_D("(//input[@id='tmPicker'])[2]").clear();
				getWebElementXpath_D("(//input[@id='tmPicker'])[2]").sendKeys("17:00");
				Thread.sleep(1000);
				
				//Clicked on the Period dropdown
				getWebElementXpath_D("(//span[@class='k-input-inner']/span)[8]").click();
				Thread.sleep(4000);
				
				//Select the Period as 'Open'
				getWebElementActionXpath_D("(//*[@class='k-animation-container']//*[@class='k-list-item-text'])[1]").click();
				Thread.sleep(2000);
				//Click on Save button from popup
				getWebElementXpath_D("(//div[@class='ng-scope'])[7]//a[@class='buttonMain'][1]").click();
				aJaxWait();
			}
			
			//Click on 'Mark empty time period as:' dropdown
			getWebElementXpath_D("//div[@id='scheduleTab-2']//span[@class='k-input-inner']").click();
			aJaxWait();
			//Select Close option
			getWebElementXpath_D("(//*[@class='k-list-ul'])[1]/li[2]").click();
			aJaxWait();
			
			//Click on save
			getWebElementXpath_D("//tbody/tr[1]/td[1]/div[5]/div[2]/a[1]/span[1]").click();
			Thread.sleep(10000);
			
			//Click on Save & Next button
			getWebElementXpath_D("(//a[@class='buttonMain'])[6]/span[@class='ng-binding']").click();
			Thread.sleep(10000);
			Utility.moveTheScrollToTheDown();
			
			//Click on Next button from 'Special Days' tab
			getWebElementActionXpath_D("/html/body/form/div[5]/div[1]/div/div/div[2]/div[1]/div/div/div[4]/div[1]/a[2]/span").click();
			Thread.sleep(2000);
			
			//Click on Site Tab from Participents tab
			getWebElementXpath_D("(//*[@class='k-tabstrip-items k-reset'])[2]/li[2]").click();
			Thread.sleep(2000);
			
			//Click on Site Search
			getWebElementActionXpath_D("(//div[@class='ng-isolate-scope'])[5]/span").click();
			Thread.sleep(3000);
			//Selected the Site 'PAMTest_Capriata/Saiwa'
			getWebElementActionXpath_D("//input[@data-text='PAMTest_Capriata/Saiwa']").click();
			Thread.sleep(2000);
			
			//Click on Addsites '+' button
			getWebElementActionXpath_D("(//a[@class='buttonPlus'])[2]").click();
			Thread.sleep(2000);
			String participent = d.findElement(By.xpath("//tr[@class='k-master-row ng-scope']/td[1]")).getText();
			Assert.assertNotNull(participent);
			
			//Click on 'Done' button
			getWebElementXpath_D("(//a[@class='buttonMain'])[13]/span[@class='ng-binding']").click();
			Thread.sleep(3000);
			
			boolean addedSiteSchedule = false;
			//Verify newly added client schedule 'QIDM109_Test Template' available in the grid.
			templateList = d.findElements(By.xpath("/html/body/form/div[5]/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[3]/div[3]/table/tbody/tr"));
			for(int i=1;i<=templateList.size();i++) {
				templatePath="/html/body/form/div[5]/div[1]/div/div/div[2]/div[1]/div[2]/div[1]/div[3]/div[3]/table/tbody/tr["+i+"]/td[2]/span";
				explicitWait_Xpath(templatePath);
				templateNameFromGrid=getWebElementActionXpath_D(templatePath).getText();
				if(templateNameFromGrid.equalsIgnoreCase(templateName)) {
					printLog("Verify newly added client schedule 'QIDM109_Test Template' available in the grid.");
					addedSiteSchedule = true;
					break;
				}
			}
			
			//Verified client scheduled should be added.
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
			
			//Selected data breakdown as Site Schedule and select the template name as 'QIDM109_Test Template'
			getWebElementXpath("DataBreakDownExpander").click();
			new Select(getWebElementXpath("BreakDownType")).selectByVisibleText("Site Schedule");
			Thread.sleep(1000);
			new Select(getWebElementXpath("BreakDownSiteSchedule")).selectByVisibleText(templateName);
			aJaxWait();
			printLog("Selected 'QIDM109_Test Template' fom Site Schedule from DataBreakdown");
			Thread.sleep(5000);
			
			//Verify the legends under the chart
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Capriata/Saiwa kWh (Close)");
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendTwo").getText(), "PAMTest_Capriata/Saiwa kWh (Open)");
			printLog("Verified legends from chart!!");

			//Select the time interval - By hour
			Select timeInterval=new Select(getWebElementXpath("PAMIntervalDropDown"));
			timeInterval.selectByVisibleText("By hour");
			
			//Click on Table Data tab
			getWebElementXpath("DataTableTab").click();
			Thread.sleep(2000);
						
			String byDayTableData[]={"1/2/2023 1:00 am~680.0~","1/2/2023 2:00 am~708.0~","1/2/2023 3:00 am~779.0~","1/2/2023 4:00 am~798.0~",
					 "1/2/2023 5:00 am~865.0~","1/2/2023 6:00 am~961.0~","1/2/2023 7:00 am~1,079~","1/2/2023 8:00 am~1,133~",
					 "1/2/2023 9:00 am~1,253~","1/2/2023 10:00 am~~1,288","1/2/2023 11:00 am~~240.0","1/2/2023 12:00 pm~~151.0",
					 "1/2/2023 1:00 pm~~128.0","1/2/2023 2:00 pm~~85.00","1/2/2023 3:00 pm~~104.0","1/2/2023 4:00 pm~~144.0",
					 "1/2/2023 5:00 pm~~134.0","1/2/2023 6:00 pm~108.0~","1/2/2023 7:00 pm~200.0~","1/2/2023 8:00 pm~250.0~",
					 "1/2/2023 9:00 pm~193.0~","1/2/2023 10:00 pm~158.0~","1/2/2023 11:00 pm~221.0~","1/3/2023 12:00 am~194.0~"};
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
			
			//De-Select the Electricity - Energy measurement
			getWebElementActionXpath("PAMTest_CapriataSaiwa_Energy").click();
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//span[@id='commodityElectricity']").click();
			Thread.sleep(2000);
			//Click on save and close
			getWebElementActionXpath_D("//button[text()='Save & Close']").click();
			Thread.sleep(5000);
			//Search with the site 'PAMTest_Naperville, IL' 
			searchSiteInLocationList(testData.getProperty("PAMTestNapervilleIL"));
			//Select the Electricity - Energy measurement
			getWebElementXpath("PAMTest_NapervilleIL_Energy").click();
			refreshToLoadTheChart();
			
			//Verify the legends under the chart
			Assert.assertEquals(getWebElementActionXpath("ColumnLegendOne").getText(), "PAMTest_Naperville, IL kWh");
			//Verify the template available for unassigned site
			getWebElementXpath("BreakDownSiteSchedule").click();
			List<WebElement> optionsList = d.findElements(By.xpath("//select[@id='breakdownSiteSchedule']/option"));
			String scheduleName,scheduleNamePath;
			for(int i=1;i<=optionsList.size();i++) {
				scheduleNamePath="//select[@id='breakdownSiteSchedule']/option["+i+"]";
				scheduleName=d.findElement(By.xpath(scheduleNamePath)).getText();
				if(scheduleName.equalsIgnoreCase(templateName)) {
					Assert.fail(templateName+" Should not displayed for unassigned site");
				}
			}
			
			login.logout();
			
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void createSiteTemplate(String template, String templateDesc) throws Throwable {
		try {
		//Click on Templates tab
		getWebElementActionXpath_D("/html[1]/body[1]/form[1]/div[5]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/ul[1]/li[1]").click();
		Thread.sleep(3000);
		
		//Verified the newly added template is already available in the grid if yes, delete the template
		List<WebElement> templateList = d.findElements(By.xpath("//tr[@class='k-master-row ng-scope']/td[1]"));
		String templateNameFromGrid,templatePath;
		for(int i=1;i<=templateList.size();i++) {
			templatePath="(//span[@ng-bind='dataItem.TemplateName'])["+i+"]";
			templateNameFromGrid=d.findElement(By.xpath(templatePath)).getText();
			if(templateNameFromGrid.equalsIgnoreCase(template)) {
				String deleteTemplatePath="//tr["+i+"]/td[7]/a[@class='buttonPopupClose']";
				d.findElement(By.xpath(deleteTemplatePath)).click();
				Thread.sleep(2000);
				d.findElement(By.xpath("(//a[@class='buttonMain'])[3]")).click();
				Thread.sleep(2000);
				printLog("Verified the newly added template is deleted");
				break;
			}
		}
				
		//Click on Create New Template button
		getWebElementXpath_D("//span[contains(text(),'Create New Template')]").click();
		Thread.sleep(3000);
		
		String pageTitle = d.findElement(By.xpath("//span[@class='page-title']")).getText();
		Assert.assertEquals(pageTitle, "Create New Template");
		
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
		Thread.sleep(3000);
		
		//Verified the newly added template is available in the grid
		templateList = d.findElements(By.xpath("//tr[@class='k-master-row ng-scope']/td[1]"));
		//String templateNameFromGrid,templatePath;
		for(int i=1;i<=templateList.size();i++) {
			templatePath="(//tr[@class='k-master-row ng-scope'])["+i+"]/td[1]";
			templateNameFromGrid=d.findElement(By.xpath(templatePath)).getText();
			if(templateNameFromGrid.equalsIgnoreCase(template)) {
				printLog("Verified the newly added template");
				break;
			}
		}
		
		
	} catch (Throwable e) {
		e.printStackTrace();
		throw e;
	}
	}

}

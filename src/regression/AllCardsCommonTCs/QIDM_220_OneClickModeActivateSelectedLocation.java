package regression.AllCardsCommonTCs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies functionality of one click mode 'Advance Data Selector' 
 * option 'Activate Selected Location' on Trend Analysis and Comparison cards. 
 * Also pushes same chart to dashboard and comes back to the card to verify 
 * one click mode is still enabled.
 */
public class QIDM_220_OneClickModeActivateSelectedLocation extends TestBase{
	LoginTC login = null;
	@Test
	public void oneClickModeActivateSelectedLocation() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'One Click Mode Tests' under 'Automation Test Dashboards' folder
			Utility.goToPAMDashboard("Automation Test Dashboards", "One Click Mode Tests");
			Utility.deleteFilter();	
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Enable One Click Mode
			getWebElementXpath("OnceClick_Icon").click();
			getWebElementXpath("OneClickModeLink").click();
			
			//Search the site as 'PAM Test_Capriata/Saiwa'
			searchSiteInLocationList("PAMTest_Capriata/Saiwa");
			//Click on SiteName 'PAM Test_Capriata/Saiwa'
			getWebElementActionXpath_D("//td[contains(@class,'k-table-td k-text-nowrap') and text()='PAMTest_Capriata/Saiwa']").click();
			refreshToLoadTheChart();
			
			//Verify the one click mode is selected for  PAMTest_Capriata/Saiwa
			int selectedSize=d.findElements(By.xpath("//tr[.='PAMTest_Capriata/Saiwa'][contains(@class,'row-selected row-activated')]")).size();
			if(selectedSize!=1) {
				throw new Exception("Onl click mode is not selected for PAMTest_Capriata/Saiwa.");
			}
			
			//Since there is no other option to verify is 4 measurement are selected i just added this type of verification  also added the above PAMTest_Capriata/Saiwa. highlighted verification
			//This wiii check the color of the commodity icon before selected the one click mode
			int selectedMeasurements = d.findElements(By.xpath("//tr[.='PAMTest_Capriata/Saiwa']//following-sibling::span//child::i[1][contains(@class,' se-icon-circle')][@style='color: rgb(153, 153, 153);']")).size();
			Assert.assertEquals(selectedMeasurements, 0);
			printLog("Verified default selected measurements and highlighted the PAMTest_Capriata/Saiwa. for one click mode activated ");
			
			ArrayList<String> trendLegendArray=new ArrayList<String>();
			trendLegendArray.add(testData.getProperty("PAMTest_CapriataSaiwakWh"));
			trendLegendArray.add(testData.getProperty("PAMTest_CapriataSaiwaGJ"));
			trendLegendArray.add("PAMTest_Capriata/Saiwa \u00B0C");
			trendLegendArray.add("PAMTest_Capriata/Saiwa %");
			trendLegendArray.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			trendLegendArray.add(testData.getProperty("PAMTest_CapriataSaiwam3"));
			
			// Verify the legends on Trend Card
			Assert.assertTrue(trendLegendArray.contains(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(trendLegendArray.contains(getWebElementActionXpath("ColumnLegendTwo").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(trendLegendArray.contains(getWebElementActionXpath("LineLegendThree").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(trendLegendArray.contains(getWebElementActionXpath("LineLegendFour").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(trendLegendArray.contains(getWebElementActionXpath("ColumnLegendFive").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(trendLegendArray.contains(getWebElementActionXpath("ColumnLegendSix").getText().replaceAll("\\n", " ")));
			printLog("Verified the Legends on Trend Analysis Card");
			
			//Push the Trend widget to dashboard
			pushwidget("One Click Mode Tests","Automation Test Dashboards");
			printLog("Push the Trend widget to dashboard");
			
			// Navigate to Comparison Analysis card
			card2Card("Comparison Analysis");
			aJaxWait();
			List<WebElement> onceClickStatus = d.findElements(By.xpath(locators.getProperty("VerifyEnableOneClick")));
			Assert.assertEquals(onceClickStatus.size(), 1);
			printLog("Verified that one click mode is active for Comparison analysis card");
			
			ArrayList<String> comLegendArray=new ArrayList<String>();
			comLegendArray.add("PAMTest_Capriata/Saiwa kWh");
			comLegendArray.add("PAMTest_Capriata/Saiwa m3");
			comLegendArray.add("PAMTest_Capriata/Saiwa m3");
			comLegendArray.add("PAMTest_Capriata/Saiwa %");
			comLegendArray.add("PAMTest_Capriata/Saiwa \u00B0C");
			comLegendArray.add("PAMTest_Capriata/Saiwa GJ");
			
			// Verify the legends on Comparison Analysis Card
			Assert.assertTrue(comLegendArray.contains(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(comLegendArray.contains(getWebElementActionXpath("ColumnLegendTwo").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(comLegendArray.contains(getWebElementActionXpath("ColumnLegendThree").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(comLegendArray.contains(getWebElementActionXpath("ColumnLegendFour").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(comLegendArray.contains(getWebElementActionXpath("ColumnLegendFive").getText().replaceAll("\\n", " ")));
			Assert.assertTrue(comLegendArray.contains(getWebElementActionXpath("ColumnLegendSix").getText().replaceAll("\\n", " ")));
			printLog("Verified the Legends on Comparison Analysis Card");
			
			//push the comparison widget to dashbaord
			pushwidget("One Click Mode Tests","Automation Test Dashboards");
			printLog("Push the comparison widget to dashboard");
			
			//Go to Dashboard page
			gotoDashBoardHome();
			aJaxWait();
			Thread.sleep(5000);
			
			// compress empty space to make widgets formatted
			Utility.compressEmptySpace();
			
			// calculate the number of widgets are available
			List<WebElement> widgetNames = d.findElements(By.xpath("//div/gridster/gridster-item"));
			Assert.assertEquals(widgetNames.size(), 2);
			printLog("Number of widgets available on the dahboard are : " + widgetNames.size());
			
			//Verified the widgets title on the dashboard
			Assert.assertEquals(getWebElementActionXpath_D("//gridster/gridster-item[1]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText(),"New Trend Analysis");
			Assert.assertEquals(getWebElementActionXpath_D("//gridster/gridster-item[2]/ra-widget-container/div/div[1]/div/div/div[1]/ra-tooltip/div/div").getText(),"New Comparison Analysis");
			printLog("Verified the widgets title on the dashboard");
			
			Assert.assertTrue(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " ").contains(testData.getProperty("PAMTestCapriataSaiwa")));
			printLog("Verified the legend on the widget chart for 'PAMTest_Capriata/Saiwa' site");
			
			// Click on Filter icon
			getWebElementActionXpath("FilterIcon").click();
			aJaxWait();
			Thread.sleep(2000);
			//Search with site name 'PAMTest_Naperville, IL'
			String siteNameConfig = "(//div[@class='ra-autocomplete-container']//input[@class='light'])[2]";
			d.findElement(By.xpath(siteNameConfig)).click();
			d.findElement(By.xpath(siteNameConfig)).sendKeys(testData.getProperty("PAMTestNapervilleIL"));
			aJaxWait();
			//Select the site name form the list
			String path = "//div[@class='site-name-body']//span[contains(text(),'PAMTest_Naperville, IL')]";
			Utility.moveTheElementandClick(path);
			Thread.sleep(5000);
			
			d.findElement(By.xpath("//div[@id='dvglobalfilter']//label[contains(text(),'Site')]")).click();
			Thread.sleep(2000);
			// Click on Save button from Filter tab
			getWebElementActionXpath("Save_button_FilterTab").click();
			aJaxWait();
			Thread.sleep(5000);
			
			//Verified that widgets should be populate filtered data
			Assert.assertTrue(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " ").contains(testData.getProperty("PAMTestNapervilleIL")));
			printLog("Verified the widget data has updated to filtered 'PAMTest_Naperville, IL' site data");	
			
			//Click on config icon for Trend analysis widget
			WebElement settingsIcon = d.findElement(By.xpath("//gridster/gridster-item[2]/ra-widget-container[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/i[1]"));
			Actions act = new Actions(d); 
			act.moveToElement(settingsIcon,1,1).perform(); 
			act = new Actions(d); 
			act.moveToElement(settingsIcon,1,1).perform();
			Thread.sleep(2000);
			
			String settings = "//gridster/gridster-item[2]/ra-widget-container[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/i[1]";
			Utility.moveTheElementandClick(settings);
			Thread.sleep(5000);
			
			// Click on Visit Page link
			getWebElementActionXpath_D("//*[contains(text(),'Visit Page')]").click();
			Thread.sleep(2000);
			printLog("Click on Visit Page link");

			ArrayList<String> newTb = new ArrayList<String>(d.getWindowHandles());
			// User has switched to new tab PAM page
			d.switchTo().window(newTb.get(1));
			aJaxWait();
			Thread.sleep(10000);
			Assert.assertTrue(getWebElementActionXpath("ColumnLegendOne").getText().replaceAll("\\n", " ").contains(testData.getProperty("PAMTestNapervilleIL")));
			printLog("Verified the data has updated to filtered site data on PAM page");	
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}finally {
			//Go to Dashboard page
			gotoDashBoardHome();			
			// Verify if widgets are existing if yes delete the widgets
			Utility.deleteAllWidgets();			
			Utility.deleteFilter();			
			login.logout();
		}
	}
}
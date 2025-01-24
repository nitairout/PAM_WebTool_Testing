package regression.DashboardsAndWidgets.MyDashBoardAndWidgets;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.Constant;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies download options available on the widget Chart and table data.
 */
public class QIDM_208_WidgetDownloadOptions extends TestBase{
	LoginTC login = null;
	String widgetTitle = "Trend Widget";
	@Test
	public void widgetDownloadOptions() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Go to 'All Cards Existing Widgets' under 'AutoTest_Dashboards' folder
			Utility.goToPAMDashboard("AutoTest_Dashboards", "All Cards Existing Widgets");
			Thread.sleep(10000);//given more wait time to load the widgets
			
			List<WebElement> widgetNames = d.findElements(By.xpath("//div/gridster/gridster-item"));
			Assert.assertEquals(widgetNames.size(), 5);
			printLog("Number of widgets available on the dahboard are : " + widgetNames.size());
			String widgetNameToVerify="";
			int widgetIndexToVerify=0;
			for(int i=1;i<=widgetNames.size();i++) {
				widgetNameToVerify=getWebElementActionXpath_D("//gridster/gridster-item["+i+"]//div[@class='wc-wrapper']//div[contains(@class,'title-label')]").getText();
				if(widgetNameToVerify.equalsIgnoreCase("Trend Widget")) {
					Assert.assertEquals(widgetNameToVerify, "Trend Widget");
					widgetIndexToVerify=i;
					break;
				}
			}
			
			//Verify the widget title on the dashboard
			Assert.assertEquals(getWebElementActionXpath_D("//gridster/gridster-item["+widgetIndexToVerify+"]//div[@class='wc-wrapper']//div[contains(@class,'title-label')]").getText(),widgetTitle);
			printLog("Verified Widget title as 'Trend Widget' on the Dashboard");
			//Click on Chart tab
			getWebElementXpath_D("//gridster/gridster-item["+widgetIndexToVerify+"]//div[@class='wc-wrapper']//a[contains(text(),'Chart')]").click();
			Thread.sleep(1000);
			
			//Verify the PNG file has downloaded
			downloadWidget(widgetTitle,"png",widgetIndexToVerify);
			
			//Verify the JPEG file has downloaded
			downloadWidget(widgetTitle,"jpeg",widgetIndexToVerify);
			
			//Verify the PDF file has downloaded
			downloadWidget(widgetTitle,"pdf",widgetIndexToVerify);
			
			//Verify the SVG file has downloaded
			downloadWidget(widgetTitle,"svg",widgetIndexToVerify);
			
			//Click on Table tab
			getWebElementXpath_D("//gridster/gridster-item["+widgetIndexToVerify+"]//div[@class='wc-wrapper']//a[contains(text(),'Table')]").click();
			Thread.sleep(1000);
			
			//Verify the Excel file has downloaded
			downloadWidget(widgetTitle,"xlsx",widgetIndexToVerify);
			
			login.logout();
			
		}catch(Throwable e) {
				e.printStackTrace();
				throw e;
			}
	}
	
	private static void downloadWidget(String fileName, String fileType,int allVerificationIndex) throws Exception{
		try {
			//Verify PNG file download
			File file =new File(Constant.DOWNLOAD_PATH+"\\"+fileName+"."+fileType);
			if(file.exists()) {
				file.delete();
			}
			Utility.moveTheScrollToTheTop();
			WebElement settingsIcon = d.findElement(By.xpath("//gridster/gridster-item["+allVerificationIndex+"]//i[contains(@class,'widget-menu-ellipse')]"));
			Actions act = new Actions(d); 
			act.moveToElement(settingsIcon,1,1).perform(); 
			Actions actions = new Actions(d); 
			actions.moveToElement(settingsIcon,1,1).perform();
			Thread.sleep(2000);
			WebElement settings = d.findElement(By.xpath("//gridster/gridster-item["+allVerificationIndex+"]//i[contains(@class,'widget-menu-ellipse')]")); 
			actions.moveToElement(settings,1,1).click().build().perform(); 
			Thread.sleep(2000);
			WebElement downloadIcon =null;
			if(fileType.equalsIgnoreCase("xlsx")) {
				downloadIcon = d.findElement(By.xpath("//div[@class='gridDownload']//span[contains(text(),'Excel')]"));
			}else {
				downloadIcon = d.findElement(By.xpath("//div[@class='chartDownload']//span[contains(text(),'"+fileType.toUpperCase()+"')]"));
			}
			act.moveToElement(downloadIcon,1,1).click().build().perform(); 
			printLog("Click on "+fileType+" from widget configuration");
			Thread.sleep(5000);
			
			if(file.exists()){
				if(FileUtils.sizeOf(file)<1)
					throw new Exception(fileType+" file does not have any data!");
			}else {
				throw new Exception(fileType+" file is not downloded!");
			}
			printLog(fileType+" image downloaded and verified");

			} catch (Throwable e) {
				throw e;
			}
		}

}
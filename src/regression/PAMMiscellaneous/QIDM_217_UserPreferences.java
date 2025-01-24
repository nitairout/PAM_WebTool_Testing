package regression.PAMMiscellaneous;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies user level setting for 'Interval Data' under settings page.
 */
public class QIDM_217_UserPreferences extends TestBase{
	LoginTC login = null;
	@Test
	public void userPreferences() throws Throwable {
		try {
			login= LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Verify Default configuration
			intervalDataSetup("Raw Data","Disable","No");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Verified by default 'raw data' mode is enabled on the card by checking the icon(tool tip message)
			Assert.assertEquals(getWebElementXpath_D("//i[@class='far fa-wave-triangle']").getAttribute("title"),"Switch from raw to corrected data");
			
			// Search for Site 'PAMTest_Capriata/Saiwa \ PAMTest_Main Meter \ PAMTest_Electric \ PAMTest_Cabina 1 \ PAMTest_Trasformatore 2' and select Electric Measurement
			searchSiteInLocationList("PAMTest_Trasformatore 2");
			Utility.moveTheElement("(//td[normalize-space()='PAMTest_Trasformatore 2'])[1]/following-sibling::td[2]/child::span");
			aJaxWait();
			// To get the tool tip text and assert
			WebElement tooltip = d.findElement(By.xpath("(//td[normalize-space()='PAMTest_Trasformatore 2'])[1]/following-sibling::td[2]/child::span"));
			String toolTipText = tooltip.getAttribute("data-bs-original-title");
			System.out.println("toolTipText..."+toolTipText);
			Assert.assertFalse(toolTipText.contains("MDLZ.EU.IT.ITATC_COMX.GTW_E02.ELEC_Cabina1_Trasformatore2"));
			
			// Provide 'Data Type', 'DataQualityFlag', 'ShowMeterID'
			intervalDataSetup("Corrected Data","Enable","Yes");
			
			goToAnalysisPage();
			goToPAMCard("TrendAnalysisCard");
			
			//Verified 'Corrected data' mode is enabled on the card by checking the icon(tool tip message)
			Assert.assertEquals(getWebElementXpath_D("//i[@class='far fa-wave-sine se-chart-control-btn']").getAttribute("title"),"Switch from corrected to raw data");
			
			// Search for Site 'PAMTest_Capriata/Saiwa \ PAMTest_Main Meter \ PAMTest_Electric \ PAMTest_Cabina 1 \ PAMTest_Trasformatore 2' and select Electric Measurement
			searchSiteInLocationList("PAMTest_Trasformatore 2");
			Utility.moveTheElement("(//td[normalize-space()='PAMTest_Trasformatore 2'])[1]/following-sibling::td[2]/child::span");
			aJaxWait();
			// To get the tool tip text and assert
			tooltip = d.findElement(By.xpath("(//td[normalize-space()='PAMTest_Trasformatore 2'])[1]/following-sibling::td[2]/child::span"));
			toolTipText = tooltip.getAttribute("data-bs-original-title");
			System.out.println("toolTipText..."+toolTipText);
			Assert.assertTrue(toolTipText.contains("MDLZ.EU.IT.ITATC_COMX.GTW_E02.ELEC_Cabina1_Trasformatore2"));
			
			// Added fixed date range
			addFixedDateRange(testData.getProperty("FixedDateStartJan012023"), testData.getProperty("FixedDateEndJan312023"));
			d.findElement(By.xpath("(//td[normalize-space()='PAMTest_Trasformatore 2'])[1]/following-sibling::td[2]/child::span")).click();
			refreshToLoadTheChart();
			
			//Verify Data Flags available on the chart
			List<WebElement> dataQulaityFlags = d.findElements(By.xpath("//*[contains(@class,'far fa-map-marker-alt')]"));
			Assert.assertTrue(dataQulaityFlags.size()>0);
			
		}catch(Throwable e) {
			e.printStackTrace();
			throw e;
		}finally {
			//Setup to Default configuration
			intervalDataSetup("Raw Data","Disable","No");
			login.logout();
		}
	}
	
	private void intervalDataSetup(String dataType, String dataQualityFlag, String showMeterID) throws Throwable {
		try {
			// Click on Client name
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//div[@class='kiosk-hide']//ra-client-ddl[@id='ddlraUser']//div//i[@class='fal fa-chevron-down raddl-color']").click();
			Thread.sleep(1000);
			//Click on Settings option
			getWebElementActionXpath_D("(//a[@class='profile-link'])[2]").click();
			Thread.sleep(5000);
			
			//click on Interval Data Options tab
			Utility.moveTheElementandClick("//*[@class='e-tabs']//a[contains(text(),'Interval Data Options')]");
			Thread.sleep(5000);
			
		    String showMeterIDTooltipYES = "//*[@id='up-int-data']/div[1]/div[2]/ra-radiobutton-list/form/div[1]/label/span[2]";
		    String showMeterIDTooltipNo = "//*[@id='up-int-data']/div[1]/div[2]/ra-radiobutton-list/form/div[2]/label/span[2]";
		    String correctedDataRadiobutton = "//*[@id='up-int-data']/div[1]/div[1]/ra-radiobutton-list/form/div[1]/label/span[2]";
		    String rawDataRadiobutton = "//*[@id='up-int-data']/div[1]/div[1]/ra-radiobutton-list/form/div[2]/label/span[2]";
			String showDataQualityFlagsDisable = "//*[@id='up-int-data']/div[1]/div[1]/ra-checkbox//span[@class='chk-check-box']";
			String showDataQualityFlagsEnable = "//*[@id='up-int-data']/div[1]/div[1]/ra-checkbox//span[@class='chk-check-box chk-check-box-checked']";
			
			if(dataType.equalsIgnoreCase("Corrected Data")) {
				//Select Corrected Data Radiobutton
				d.findElement(By.xpath(correctedDataRadiobutton)).click();
			}else if(dataType.equalsIgnoreCase("Raw Data")) {
				//Select Raw Data Radiobutton
				d.findElement(By.xpath(rawDataRadiobutton)).click();
			}
			
			List<WebElement> diableList = d.findElements(By.xpath(showDataQualityFlagsDisable));
			List<WebElement> enableList = d.findElements(By.xpath(showDataQualityFlagsEnable));
			if(diableList.size()>0 && enableList.size() == 0 && dataQualityFlag.equalsIgnoreCase("Enable")) {
				//ShowDataQualityFlags Enable
				d.findElement(By.xpath(showDataQualityFlagsDisable)).click();
			}else if(diableList.size() == 0 && enableList.size()>0 && dataQualityFlag.equalsIgnoreCase("Disable")) {
				//ShowDataQualityFlags Disable
				d.findElement(By.xpath(showDataQualityFlagsEnable)).click();
			}
			
			if(showMeterID.equalsIgnoreCase("Yes")) {
				//Select ShowMeterIDTooltip Yes Radiobutton
				d.findElement(By.xpath(showMeterIDTooltipYES)).click();
			}else if(showMeterID.equalsIgnoreCase("No")) {
				//Select ShowMeterIDTooltip No Radiobutton
				d.findElement(By.xpath(showMeterIDTooltipNo)).click();
			}
		    
			// Click on 'Save' button
			getWebElementActionXpath_D("(//input[@class='settings-save light'])[2]").click();			
			Thread.sleep(10000);
		} catch (Throwable t) {
			throw t;

		}
	}

}

/**

 * File Name : LoginTC
 * The LoginTC class created to hold all the login related methods. 
 * As this is a singleton class it will not create multiple object.
 *
 * @author Cambridge Technologies.
 * @contact Cambridge Technologies
 * @since   29-June-2017 
 * 
 */
package common;

import org.openqa.selenium.By;
import org.testng.Reporter;


public class LoginTC extends TestBase implements Cloneable{

	private LoginTC(){}

	private static LoginTC object=null;
	public static synchronized LoginTC getLoginTCObject(){
		if(object == null){
			synchronized(LoginTC.class){
				if(object == null){
					object = new LoginTC();
				}
			}
		}
		return object;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	// Declaration
	ValueDTO Dto = new ValueDTO();

	
	public void login(String userType) throws Exception{
		try{
			printLog("Execution URL is "+Dto.getURl());
			System.out.println(Dto.getURl());
			//Add the url into browser
			d.get(Dto.getURl());
			waitForPageLoad();
			Thread.sleep(5000);
			
			
			// Added the user id
			explicitWait_CSS("#raUserName");
			getWebElementCSS_D("#raUserName").clear();
			getWebElementCSS_D("#raUserName").sendKeys(Dto.getInternalusername());
			Thread.sleep(1000);
			//Click on login button
			d.findElement(By.cssSelector("#btnLogin")).click();
			Thread.sleep(3000);
			
			//Added the pwd
			explicitWait_CSS("#raPassword");
			getWebElementCSS_D("#raPassword").clear();
			getWebElementCSS_D("#raPassword").sendKeys(Dto.getInternalPassword());
			
			//Click on login button
			d.findElement(By.cssSelector("#btnLogin")).click();
			waitForPageLoad();
			Thread.sleep(15000);
			
			switch(userType)
	        {
	            case "Internal":
	            	explicitWait_CSS("input[id*=ddlClientId_Input]");
	            	if(d.findElements(By.cssSelector("input[id*=ddlClientId_Input]")).size() != 0)
	    			{
	            		getWebElementCSS_D("input[id*=ddlClientId_Input]").click();
	            		getWebElementCSS_D("input[id*=ddlClientId_Input]").clear();
	            		getWebElementCSS_D("input[id*=ddlClientId_Input]").sendKeys(testData.getProperty("ClientName"));
	            		Thread.sleep(2000);	    				
	            		//getWebElementCSS_D("li.rcbHovered").click();
	            		explicitWait_Xpath("//li[normalize-space()='"+testData.getProperty("ClientName")+"']").click();
	            		
	    			}
	                break;
	            case "External":
	            	getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_txtUsername']").sendKeys(testData.getProperty("ExternalUser"));
	            	getWebElementXpath_D("//span[@id='ctl00_ContentPlaceHolder1_lblUserSearch']").click();
	    			Thread.sleep(5000);
	    			explicitWait_Xpath("//a[contains(text(),'"+testData.getProperty("ExternalUser")+"')]").click();
	    			explicitWait_Xpath("//*[@id='ctl00_NavMenu']/ul/li[3]/a/span");
	                break;
	            default:
	              printLog("User type is neither internal nor external user");
	        }
			waitForPageLoad();
			Thread.sleep(15000);
			 printLog("Logged in successfully with "+userType+" user!!");
		}catch(Exception e){
			throw e;
		}
	}
	

	public void login(String userType, String selectDBType,String dbName) throws Exception{
		try{
					// Added the user id
			explicitWait_CSS("#raUserName");
			getWebElementCSS_D("#raUserName").clear();
			getWebElementCSS_D("#raUserName").sendKeys(Dto.getInternalusername());
			Thread.sleep(1000);
			//Click on login button
			d.findElement(By.cssSelector("#btnLogin")).click();
			Thread.sleep(3000);
			
			//Added the pwd
			getWebElementCSS_D("#raPassword").clear();
			getWebElementCSS_D("#raPassword").sendKeys(Dto.getInternalPassword());
			
			//Click on login button
			d.findElement(By.cssSelector("#btnLogin")).click();
			waitForPageLoad();
			Thread.sleep(5000);
			
			switch(userType)
	        {
	            case "Internal":
	            	if(d.findElements(By.cssSelector("input[id*=ddlClientId_Input]")).size() != 0)
	    			{
	            		getWebElementCSS_D("input[id*=ddlClientId_Input]").click();
	            		getWebElementCSS_D("input[id*=ddlClientId_Input]").clear();
	            		getWebElementCSS_D("input[id*=ddlClientId_Input]").sendKeys(testData.getProperty("ClientName"));
	    				getWebElementCSS_D("li.rcbHovered").click();
	    			}
	                break;
	            case "External":
	            	getWebElementXpath_D("//input[@id='ctl00_ContentPlaceHolder1_txtUsername']").sendKeys(testData.getProperty("ExternalUser"));
	            	getWebElementXpath_D("//span[@id='ctl00_ContentPlaceHolder1_lblUserSearch']").click();
	    			Thread.sleep(5000);
	    			explicitWait_Xpath("//a[contains(text(),'"+testData.getProperty("ExternalUser")+"')]").click();
	    			explicitWait_Xpath("//*[@id='ctl00_NavMenu']/ul/li[3]/a/span");
	                break;
	            default:
	              printLog("User type is neither internal nor external user");
	        }
			waitForPageLoad();
			Thread.sleep(5000);
			 printLog("Logged in successfully with "+userType+" user!!");
			Utility.selectDashboard(selectDBType,dbName);
			
		}catch(Exception e){
			throw e;
		}
	}
	
	
	
		public void logout() throws Exception  {

		/*explicitWait_Xpath(locators.getProperty("logout"));
		getWebElementXpath("logout").click();
		Reporter.log("Logged out from Application");
		aJaxWait();*/
		
		Utility.moveTheScrollToTheTop();
		getWebElementActionXpath_D("//div[@class='kiosk-hide']//ra-client-ddl[@id='ddlraUser']//div//i[@class='fal fa-chevron-down raddl-color']").click();
		Thread.sleep(1000);
		getWebElementActionXpath_D("//span[@class='profile-link']").click();
		printLog("Logout from the application");
	}	


/*
	public void logout() throws Exception {
		try{
		Utility.moveTheScrollToTheTop();
		getWebElementActionXpath_D("//*[@id='ddlraUser']/div/div/div[2]/i").click();
		Thread.sleep(2000);
		getWebElementActionXpath_D("//*[@id='ddlraUser']/div/div[2]/div/ul/li[3]/span").click();
		Reporter.log("Test case logged out successfully");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}*/
		
		public void loginToEEM()  {
			try {
				String eemUrl=locators.getProperty("TK1EEMUrl").trim();
				String eemUserId=locators.getProperty("EEMUserName").trim();
				String eemPassword=locators.getProperty("EEMPassword").trim();
					if(("https://tk1.dev.summitenergy.com".equalsIgnoreCase(Dto.getURl()))){
						eemUrl=locators.getProperty("TK1EEMUrl").trim();
						//eemPassword=locators.getProperty("EEMTK1PassWord").trim();
					}else if(("https://core.stg1.resourceadvisor.schneider-electric.com".equalsIgnoreCase(Dto.getURl()))){
						eemUrl=locators.getProperty("StegEEMUrl").trim();
						//eemPassword=locators.getProperty("EEMTK4PassWord").trim();
					}
				else if(("https://resourceadvisor.schneider-electric.com".equalsIgnoreCase(Dto.getURl())))
					eemUrl=locators.getProperty("ProdEEMUrl").trim();
				

					d.navigate().to("https://eemna2.resourceadvisor.schneider-electric.com/IONEEM");
				waitForPageLoad();
				// Login
				getWebElementID("EEMUser").clear();
				getWebElementID("EEMUser").sendKeys(eemUserId);
				// Password
				getWebElementID("EEMPwd").clear();
				getWebElementID("EEMPwd").sendKeys(eemPassword);
				getWebElementID("EEMLoginButton").click();
				waitForPageLoad();
				aJaxWait();
				Reporter.log("Logged in as EEM");
			} catch (Exception e) {
				throw e;
			}
		}
	
}



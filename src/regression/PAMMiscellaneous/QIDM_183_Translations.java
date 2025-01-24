package regression.PAMMiscellaneous;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies PAM page are translated as per language selection under 'Settings' page
 */
public class QIDM_183_Translations extends TestBase {
	LoginTC login = null;

	@Test
	public void translations() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			
			//Change Language to 'Italiano (IT)'
			changeLanguage("Italiano (IT)");
			
			//Navigate to PAM page
			d.navigate().to(navurl +"com/IntervalAnalytics/Interval.aspx#/cards");
			aJaxWait();
			Thread.sleep(2000);
			//Verify the tabs
			String tabNamePath, tabName;
			String[] expectedMenuTab = { "CRUSCOTTI", "ANALIZZA", "PIANO", "GESTIONE","RAPPORTI", "AMMINISTRAZIONE", "COLLEGAMENTI RAPIDI", "HELP DESK" };
			for(int i=0;i<expectedMenuTab.length;i++) {
				tabNamePath="(//div[@id='navbarCollapse']//span[@class='menu-text'])["+(i+1)+"]";
				tabName = d.findElement(By.xpath(tabNamePath)).getText();
				Assert.assertEquals(tabName, expectedMenuTab[i]);
			}
			String cardNamePath, actualCardName, widgetHeader = "";

			// Holding all the expected cards names
			String[] expectedCardsArray = { "Analisi tendenza", "Analisi profilo di carico calendario", "Analisi profilo di carico", "Analisi comparativa", "Analisi del grafico a dispersione" };
			//String[] expectedWidgetHeader = {"Nuova analisi tendenza c", "Nuova analisi profilo di carico calendario", "Nuova analisi profilo di carico", "Nuova analisi comparativa", "Nuova analisi del grafico a dispersione" };
			String[] expectedWidgetHeader = {"Nuova analisi tendenza c", "Nuova analisi profilo di carico calendario", "Nuova analisi profilo di carico", "Nuova analisi comparativa", "Nuova analisi del grafico a dispersione"};
			WebElement element = null;
			JavascriptExecutor js = (JavascriptExecutor)d;
			// Verify all the card name
			for (int i = 0; i < 5; i++) {
				cardNamePath = "(//div[@class='se-widget-grid']/div/h2)[" + (i + 1) + "]";
				actualCardName = getWebElementActionXpath_D(cardNamePath).getText();
				Assert.assertEquals(actualCardName, expectedCardsArray[i]);
				
				//getWebElementActionXpath_D(cardNamePath).click();
				element = d.findElement(By.xpath(cardNamePath));
				js = (JavascriptExecutor)d;
				js.executeScript("arguments[0].click();", element);
				Thread.sleep(5000);
				Utility.moveTheScrollToTheTop();
				widgetHeader = getWebElementActionXpath("WidgetHeader").getText();
				Assert.assertEquals(widgetHeader,expectedWidgetHeader[i]);
				Assert.assertTrue(getWebElementActionXpath_D("(//div[contains(@class,'widget-header')]/h3)[1]").getText().equalsIgnoreCase("Località"));
				Assert.assertEquals(getWebElementActionXpath_D("(//div[contains(@class,'widget-header')]/h3)[3]").getText(), "Opzioni");
				d.navigate().back();
				Utility.moveTheScrollToTheTop();
				printLog("Verified the Card.." + expectedCardsArray);
			}

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		} finally {
			//Change Language to 'English (US)'
			changeLanguage("English (US)");
			login.logout();
		}
	}
	
	private void changeLanguage(String language) throws Throwable {
		try {
			aJaxWait();//add wait for load the dashboard page takes time
			// Click on Settings Link
			Utility.moveTheScrollToTheTop();
			getWebElementActionXpath_D("//div[@class='kiosk-hide']//ra-client-ddl[@id='ddlraUser']//div//i[@class='fal fa-chevron-down raddl-color']").click();
			Thread.sleep(1000);
			getWebElementActionXpath_D("(//a[@class='profile-link'])[2]").click();
			Thread.sleep(5000);

			// Click on Display Language dropdown
			getWebElementActionXpath_D("(//div[@class='ra-dropdown-input'])[1]").click();
			Thread.sleep(1000);
			String lanuagePath;
			// Get the list of available languages in the dropdown
			List<WebElement> langList = d.findElements(By.xpath("//*[@class='item light ng-star-inserted']"));
			for (int i = 0; i < langList.size(); i++) {
				lanuagePath = "//div[@class='ra-items-container-dropdown ra-show-popup-dropdown']//*[@class='item light ng-star-inserted']["+(i+1)+"]";
				Utility.moveTheElement(lanuagePath);
				String lang = d.findElement(By.xpath(lanuagePath)).getText();
				if (lang.equalsIgnoreCase(language)) {
					// Select the language from the list
					d.findElement(By.xpath(lanuagePath)).click();
					break;
				}
			}

			// Click on 'Save' button
			getWebElementActionXpath_D("//input[@class='settings-save light']").click();			
			Thread.sleep(10000);
			printLog("Change the language into - "+language);
		} catch (Throwable t) {
			throw t;

		}
	}

}

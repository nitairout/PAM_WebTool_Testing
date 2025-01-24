package regression.AllCardsCommonTCs;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import common.LoginTC;
import common.TestBase;
import common.Utility;

/*
 * This test verifies PAM Home page and all cards descriptions
 */
public class QIDM_36_CardsHomePage extends TestBase {
	LoginTC login = null;

	@Test
	public void cardsHomePage() throws Throwable {
		try {
			login = LoginTC.getLoginTCObject();
			login.login("Internal");
			goToAnalysisPage();

			// Verified the list of cards available on the PAM page it should be '5'
			List<WebElement> cardsCount = d.findElements(By.xpath("//div[@class='se-widget-grid']/div/h2"));
			Assert.assertEquals(cardsCount.size(), 5);

			String cardNamePath, cardDescriptionPath, actualCardName, actualCardDescription, widgetHeader = "";

			// Holding all the expected cards names
			String[] expectedCardsArray = { "Trend Analysis", "Calendar Load Profile Analysis", "Load Profile Analysis", "Comparison Analysis", "Scatter Plot Analysis" };

			// Holding all the expected card descriptions
			String[] expectedCardDescArray = {"Graph usage and demand over time to spot trends and identify anomalies.",
											  "Analyze daily load shapes within a monthly view or overlay days for comparison.",
											  "Visualize the load shape of your facility to detect any unusual spikes or sags.",
											  "Compare sites against each other to detect abnormal usage.",
											  "Discover correlations between two sets of data, like usage and temperature." };

			// Verify all the card name and descriptions
			for (int i = 0; i < cardsCount.size(); i++) {
				cardNamePath = "(//div[@class='se-widget-grid']/div/h2)[" + (i + 1) + "]";
				actualCardName = getWebElementActionXpath_D(cardNamePath).getText();
				Assert.assertEquals(actualCardName, expectedCardsArray[i]);
				cardDescriptionPath = "(//div[@class='se-widget-grid']/div/span)[" + (i + 1) + "]";
				actualCardDescription = getWebElementActionXpath_D(cardDescriptionPath).getText();
				Assert.assertEquals(actualCardDescription, expectedCardDescArray[i]);
				getWebElementActionXpath_D(cardNamePath).click();
				Thread.sleep(5000);
				widgetHeader = getWebElementActionXpath("WidgetHeader").getText();
				Assert.assertEquals(widgetHeader, "New " + expectedCardsArray[i]);
				d.navigate().back();
				Utility.moveTheScrollToTheTop();
				printLog("Verified the Card.." + expectedCardsArray);
			}

			login.logout();

		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}

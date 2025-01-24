package miscellaneous.ScheduleReport;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import listener.NoRetry;
import util.ErrorUtil;
@Test(retryAnalyzer = NoRetry.class)
public class IMServ_DailyAllScheduleReport extends TestBase{
	
	@Test(priority = 3 , retryAnalyzer = NoRetry.class)
	public void PROD_IMServDataExtractionReports() throws Exception {
		try {
			new PROD_IMServDataExtractionReports().dateExtractionReport();
			Reporter.log("PROD_IMServDataExtractionReports is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in PROD_IMServDataExtractionReports", t);
		} 
	}
}
/*
/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div/div[4]/div/div/div[1]/div/div/div[3]/div/div/div[3]
/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div/div[4]/div/div/div[1]/div/div/div[2]/div/div/div[3]
*/
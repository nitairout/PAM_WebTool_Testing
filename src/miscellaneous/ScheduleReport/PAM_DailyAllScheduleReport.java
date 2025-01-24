package miscellaneous.ScheduleReport;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import listener.NoRetry;
import util.ErrorUtil;
@Test(retryAnalyzer = NoRetry.class)
public class PAM_DailyAllScheduleReport extends TestBase{
	@Test(priority = 1 , retryAnalyzer = NoRetry.class)
	public void PROD_IntervalDataReports() throws Exception {
		try {
			new PROD_IntervalDataReports().prodIntervalDataDailyReports();
			Reporter.log("PROD_IntervalDataReports is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in PROD_IntervalDataReports", t);
		} 
	}
	@Test(priority = 2 , retryAnalyzer = NoRetry.class)
	public void STAGE_IntervalDataReports() throws Exception {
		try {
			new STAGE_IntervalDataReports().stageIntervalDataDailyReports();
			Reporter.log("STAGE_IntervalDataReports is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in STAGE_IntervalDataReports", t);
		} 
	}
}
/*
/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div/div[4]/div/div/div[1]/div/div/div[3]/div/div/div[3]
/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div/div[4]/div/div/div[1]/div/div/div[2]/div/div/div[3]
*/
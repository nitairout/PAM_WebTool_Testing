package miscellaneous.ScheduleReport;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import listener.NoRetry;
import util.ErrorUtil;
@Test(retryAnalyzer = NoRetry.class)
public class AllDailyScheduleReport extends TestBase{
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
	@Test(priority = 4 , retryAnalyzer = NoRetry.class)
public void PROD_OfflineSourceReports() throws Exception {
	try {
		new PROD_OfflineSourceReports().Prod_offLineDailyReports();
		Reporter.log("PROD_OfflineSourceReports is executed sucessfully");
	} catch (Throwable t) {
		ErrorUtil.addVerificationFailure(t);
		Assert.fail("Error in PROD_OfflineSourceReports", t);
	} 
}
	@Test(priority = 5 , retryAnalyzer = NoRetry.class)
	public void STAGE_OfflineSourceReports() throws Exception {
		try {
			new STAGE_OfflineSourceReports().Stage_offLineDailyReports();
			Reporter.log("STAGE_OfflineSourceReports is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in STAGE_OfflineSourceReports", t);
		} 
	}
}
/*
/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div/div[4]/div/div/div[1]/div/div/div[3]/div/div/div[3]
/html/body/div[7]/div[3]/div/div[2]/div[1]/div[2]/div/div/div/div/div/div[4]/div/div/div[1]/div/div/div[2]/div/div/div[3]
*/
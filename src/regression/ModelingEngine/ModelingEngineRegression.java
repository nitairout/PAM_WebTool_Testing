package regression.ModelingEngine;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class ModelingEngineRegression extends TestBase {

	@Test(priority = 1)
	public void QIDM_225_ManageMeasurementConfigurationPopup() throws Exception {
		try {
			new QIDM_225_ManageMeasurementConfigurationPopup().manageMeasurementConfigurationPopup();
			printLog("QIDM_225_ManageMeasurementConfigurationPopup is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_225_ManageMeasurementConfigurationPopup", t);
		}
	}

	@Test(priority = 2)
	public void QIDM_226_NewBaselineMeasurements() throws Exception {
		try {
			new QIDM_226_NewBaselineMeasurements().newBaselineMeasurements();
			printLog("QIDM_226_NewBaselineMeasurements is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_226_NewBaselineMeasurements", t);
		}
	}
	
	@Test(priority = 3)
	public void QIDM_227_SystemGeneratedBaseline() throws Exception {
		try {
			new QIDM_227_SystemGeneratedBaseline().systemGeneratedBaseline();
			printLog("QIDM_227_SystemGeneratedBaseline is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_227_SystemGeneratedBaseline", t);
		}
	}
	
	@Test(priority = 4)
	public void QIDM_228_UserDefinedBaseline() throws Exception {
		try {
			new QIDM_228_UserDefinedBaseline().userDefinedBaseline();
			printLog("QIDM_228_UserDefinedBaseline is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_228_UserDefinedBaseline", t);
		}
	}
	@Test(priority = 5)
	public void QIDM_229_HourlyBaselineComparePAMvsCEBM() throws Exception {
		try {
			new QIDM_229_HourlyBaselineComparePAMvsCEBM().hourlyBaselineComparePAMvsCEBM();
			printLog("QIDM_229_HourlyBaselineComparePAMvsCEBM is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_229_HourlyBaselineComparePAMvsCEBM", t);
		}
	}
	
	@Test(priority = 6)
	public void QIDM_230_DailyBaselineComparePAMvsCEBM() throws Exception {
		try {
			new QIDM_230_DailyBaselineComparePAMvsCEBM().dailyBaselineComparePAMvsCEBM();
			printLog("QIDM_230_DailyBaselineComparePAMvsCEBM is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_230_DailyBaselineComparePAMvsCEBM", t);
		}
	}
	
	@Test(priority = 7)
	public void QIDM_231_IntervalBaselineComparePAMvsCEBM() throws Exception {
		try {
			new QIDM_231_IntervalBaselineComparePAMvsCEBM().intervalBaselineComparePAMvsCEBM();
			printLog("QIDM_231_IntervalBaselineComparePAMvsCEBM is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_231_IntervalBaselineComparePAMvsCEBM", t);
		}
	}
	
	
	@Test(priority = 8)
	public void QIDM_232_WeeklyBaselineComparePAMvsCEBM() throws Exception {
		try {
			new QIDM_232_WeeklyBaselineComparePAMvsCEBM().weeklyBaselineComparePAMvsCEBM();
			printLog("QIDM_232_WeeklyBaselineComparePAMvsCEBM is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_232_WeeklyBaselineComparePAMvsCEBM", t);
		}
	}
	
	@Test(priority = 9)
	public void QIDM_233_MonthlyBaselineComparePAMvsCEBM() throws Exception {
		try {
			new QIDM_233_MonthlyBaselineComparePAMvsCEBM().monthlyBaselineComparePAMvsCEBM();
			printLog("QIDM_233_MonthlyBaselineComparePAMvsCEBM is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_233_MonthlyBaselineComparePAMvsCEBM", t);
		}
	}
	
	@Test(priority = 10)
	public void QIDM_234_CEBMPageLayout() throws Exception {
		try {
			new QIDM_234_CEBMPageLayout().cebmPageLayout();
			printLog("QIDM_234_CEBMPageLayout is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_234_CEBMPageLayout", t);
		}
	}
	@Test(priority = 11)
	public void QIDM_235_DailyBaselineDataVerification() throws Exception {
		try {
			new QIDM_235_DailyBaselineDataVerification().dailyBaselineDataVerification();
			printLog("QIDM_235_DailyBaselineDataVerification is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_235_DailyBaselineDataVerification", t);
		}
	}
	@Test(priority = 12)
	public void QIDM_236_VerifyDataforSystemGeneratedBaseline() throws Exception {
		try {
			new QIDM_236_VerifyDataforSystemGeneratedBaseline().verifyDataforSystemGeneratedBaseline();
			printLog("QIDM_236_VerifyDataforSystemGeneratedBaseline is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_236_VerifyDataforSystemGeneratedBaseline", t);
		}
	}
	@Test(priority = 13)
	public void QIDM_238_BaselinePerformanceTest() throws Exception {
		try {
			new QIDM_238_BaselinePerformanceTest().baselinePerformanceTest();
			printLog("QIDM_238_BaselinePerformanceTest is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_238_BaselinePerformanceTest", t);
		}
	}
	@Test(priority = 14)
	public void QIDM_241_SiteSchedulePicker() throws Exception {
		try {
			new QIDM_241_SiteSchedulePicker().siteSchedulePicker();
			printLog("QIDM_241_SiteSchedulePicker is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_241_SiteSchedulePicker", t);
		}
	}
	@Test(priority = 15)
	public void QIDM_242_CUSUMReports() throws Exception {
		try {
			new QIDM_242_CUSUMReports().cusmurReports();
			printLog("QIDM_242_CUSUMReports is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_242_CUSUMReports", t);
		}
	}
}

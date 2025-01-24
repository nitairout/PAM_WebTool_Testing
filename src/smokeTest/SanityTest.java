package smokeTest;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class SanityTest extends TestBase {
	 

	@Test(priority =1)
	public void QIDM_146_TrendAnalysisSmokeTest() throws Exception {
		try {
			new QIDM_146_TrendAnalysisSmokeTest().trendAnalysisSmokeTest();
			Reporter.log("QIDM_146_TrendAnalysisSmokeTest is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_146_TrendAnalysisSmokeTest", t);
		} 
	}
	
	@Test(priority =2)
	public void QIDM_147_LoadProfileAnalysisSmokeTest() throws Exception {
		try {
			new QIDM_147_LoadProfileAnalysisSmokeTest().loadProfileAnalysisSmokeTest();
			Reporter.log("QIDM_147_LoadProfileAnalysisSmokeTest is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_147_LoadProfileAnalysisSmokeTest", t);
		} 
	}
	
	@Test(priority =3)
	public void QIDM_148_CalendarLoadProfileAnalysisSmokeTest() throws Exception {
		try {
			new QIDM_148_CalendarLoadProfileAnalysisSmokeTest().calendarLoadProfileAnalysisSmokeTest();
			Reporter.log("QIDM_148_CalendarLoadProfileAnalysisSmokeTest is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_148_CalendarLoadProfileAnalysisSmokeTest", t);
		} 
	}
	
	//@Test(priority =4)
	public void QIDM_150_ComparisonAnalysisSmokeTest() throws Exception {
		try {
			new QIDM_150_ComparisonAnalysisSmokeTest().comparisonAnalysisSmokeTest();
			Reporter.log("QIDM_150_ComparisonAnalysisSmokeTest is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_150_ComparisonAnalysisSmokeTest", t);
		} 
	}
	
	//@Test(priority =5)
	public void QIDM_151_ScatterPlotAnalysisSmokeTest() throws Exception {
		try {
			new QIDM_151_ScatterPlotAnalysisSmokeTest().scatterPlotAnalysisSmokeTest();
			Reporter.log("QIDM_151_ScatterPlotAnalysisSmokeTest is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_151_ScatterPlotAnalysisSmokeTest", t);
		} 
	}
	
	//@Test(priority =6)
	public void QIDM_152_DashboardAndWidgetSmokeTests() throws Exception {
		try {
			new QIDM_152_DashboardAndWidgetSmokeTests().dashboardAndWidgetSmokeTests();
			Reporter.log("QIDM_152_DashboardAndWidgetSmokeTests is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_152_DashboardAndWidgetSmokeTests", t);
		} 
	}
	
	//@Test(priority =7)
	public void QIDM_153_ManageMeasurementsSmokeTests() throws Exception {
		try {
			new QIDM_153_ManageMeasurementsSmokeTests().manageMeasurementsSmokeTests();
			Reporter.log("QIDM_153_ManageMeasurementsSmokeTests is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_153_ManageMeasurementsSmokeTests", t);
		} 
	}
	
	//@Test(priority =8)
	public void QIDM_154_ManageTagsSmokeTests() throws Exception {
		try {
			new QIDM_154_ManageTagsSmokeTests().manageTagsSmokeTests();
			Reporter.log("QIDM_154_ManageTagsSmokeTests is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_154_ManageTagsSmokeTests", t);
		} 
	}

}

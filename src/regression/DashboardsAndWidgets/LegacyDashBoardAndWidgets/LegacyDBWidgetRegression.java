package regression.DashboardsAndWidgets.LegacyDashBoardAndWidgets;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class LegacyDBWidgetRegression extends TestBase {
	
	@Test(priority =1)
	public void QIDM_173_LegacyCalenderLoadProfileWidget() throws Exception {
		try {
			new QIDM_173_LegacyCalenderLoadProfileWidget().legacyCalenderLoadProfileWidget();
			Reporter.log("QIDM_173_LegacyCalenderLoadProfileWidget is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_173_LegacyCalenderLoadProfileWidget", t);
		} 
	}
	
	@Test(priority =2)
	public void QIDM_175_LegacyComparisonAnalysisWidget() throws Exception {
		try {
			new QIDM_175_LegacyComparisonAnalysisWidget().legacyComparisonAnalysisWidget();
			Reporter.log("QIDM_175_LegacyComparisonAnalysisWidget is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_175_LegacyComparisonAnalysisWidget", t);
		} 
	}
	
	@Test(priority =3)
	public void QIDM_176_LegacyTrendAnalysisWidget() throws Exception {
		try {
			new QIDM_176_LegacyTrendAnalysisWidget().legacyTrendAnalysisWidget();
			Reporter.log("QIDM_176_LegacyTrendAnalysisWidget is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_176_LegacyTrendAnalysisWidget", t);
		} 
	}
	
	@Test(priority =4)
	public void QIDM_177_LegacyViewDashboardinKioskMode() throws Exception {
		try {
			new QIDM_177_LegacyViewDashboardinKioskMode().legacyViewDashboardinKioskMode();
			Reporter.log("QIDM_177_LegacyViewDashboardinKioskMode is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_177_LegacyViewDashboardinKioskMode", t);
		} 
	}
	
	@Test(priority =5)
	public void QIDM_180_LegacyLoadProfileWidget() throws Exception {
		try {
			new QIDM_180_LegacyLoadProfileWidget().legacyLoadProfileWidget();
			Reporter.log("QIDM_180_LegacyLoadProfileWidget is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_180_LegacyLoadProfileWidget", t);
		} 
	}
	
	
	
	
	
	
}

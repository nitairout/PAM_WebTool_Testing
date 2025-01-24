package regression.PAMMiscellaneous;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class MiscellaneousRegression extends TestBase {
	
	@Test(priority =1)
	public void QIDM_183_Translations() throws Exception {
		try {
			new QIDM_183_Translations().translations();
			Reporter.log("QIDM_183_Translations is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_183_Translations", t);
		} 
	}
	
	@Test(priority =2)
	public void QIDM_206_ManageLocationsList() throws Exception {
		try {
			new QIDM_206_ManageLocationsList().manageLocationsList();
			Reporter.log("QIDM_206_ManageLocationsList is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_206_ManageLocationsList", t);
		} 
	}
	
	@Test(priority =3)
	public void QIDM_217_UserPreferences() throws Exception {
		try {
			new QIDM_217_UserPreferences().userPreferences();
			Reporter.log("QIDM_217_UserPreferences is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_217_UserPreferences", t);
		} 
	}
	
	@Test(priority =4)
	public void QIDM_94_PeakAlertNotification() throws Exception {
		try {
			new QIDM_94_PeakAlertNotification().peakAlertNotification();
			Reporter.log("QIDM_94_PeakAlertNotification is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_94_PeakAlertNotification", t);
		} 
	}


}

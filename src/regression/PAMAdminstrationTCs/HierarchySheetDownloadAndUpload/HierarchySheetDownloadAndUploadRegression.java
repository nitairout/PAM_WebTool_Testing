package regression.PAMAdminstrationTCs.HierarchySheetDownloadAndUpload;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class HierarchySheetDownloadAndUploadRegression extends TestBase {
	
	@Test(priority = 1)
	public void QIDM_103_HierarchySetupDownload() throws Exception {
		try {
			new QIDM_103_HierarchySetupDownload().hierarchySetupDownload();
			Reporter.log("QIDM_103_HierarchySetupDownload is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_103_HierarchySetupDownload", t);
		}
	}

	@Test(priority = 2)
	public void QIDM_104_HierarchySetupEditExpressions() throws Exception {
		try {
			new QIDM_104_HierarchySetupEditExpressions().hierarchySetupEditExpressions();
			Reporter.log("QIDM_104_HierarchySetupEditExpressions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_104_HierarchySetupEditExpressions", t);
		}
	}

	@Test(priority = 3)
	public void QIDM_105_AddAndMoveSubsites() throws Exception {
		try {
			new QIDM_105_AddAndMoveSubsites().addAndMoveSubsites();
			Reporter.log("QIDM_105_AddAndMoveSubsites is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_105_AddAndMoveSubsites", t);
		}
	}

}

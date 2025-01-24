package regression.PAMAdminstrationTCs.SiteSchedules;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class SiteSchedulesRegression extends TestBase {
	
	@Test(priority = 1)
	public void QIDM_109_SiteTemplateAndSchedules() throws Exception {
		try {
			new QIDM_109_SiteTemplateAndSchedules().siteTemplateAndSchedules();
			Reporter.log("QIDM_109_SiteTemplateAndSchedules is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_109_SiteTemplateAndSchedules", t);
		}
	}
	
	@Test(priority = 2)
	public void QIDM_110_ClientTemplateAndSchedules() throws Exception {
		try {
			new QIDM_110_ClientTemplateAndSchedules().clientTemplateAndSchedules();
			Reporter.log("QIDM_110_ClientTemplateAndSchedules is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_110_ClientTemplateAndSchedules", t);
		}
	}

}

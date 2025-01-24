package regression.PAMAdminstrationTCs.AverageProfile;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class AverageProfileRegression extends TestBase {
	
	@Test(priority =1)
	public void QIDM_119_AverageProfile() throws Exception {
		try {
			new QIDM_119_AverageProfile().averageProfile();
			Reporter.log("QIDM_119_AverageProfile is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_119_AverageProfile", t);
		} 
	}	
	

}

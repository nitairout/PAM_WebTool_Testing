package regression.DataQualityScenarios;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class DataQualityScenariosRegression extends TestBase {
	
	@Test(priority = 1)
	public void QIDM_156_DataErrorWithNoCatchUpSpike() throws Exception {
		try {
			new QIDM_156_DataErrorWithNoCatchUpSpike().dataErrorWithNoCatchUpSpike();
			Reporter.log("QIDM_156_DataErrorWithNoCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_156_DataErrorWithNoCatchUpSpike", t);
		}
	}
	@Test(priority = 2)
	public void QIDM_157_DQDataErrorWithCatchUpSpike() throws Exception {
		try {
			new QIDM_157_DQDataErrorWithCatchUpSpike().dqDataErrorWithCatchUpSpike();
			Reporter.log("QIDM_157_DQDataErrorWithCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_157_DQDataErrorWithCatchUpSpike", t);
		}
	}
	@Test(priority = 3)
	public void QIDM_158_DipWithCatchUpSpike() throws Exception {
		try {
			new QIDM_158_DipWithCatchUpSpike().dipWithCatchUpSpike();
			Reporter.log("QIDM_158_DipWithCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_158_DipWithCatchUpSpike", t);
		}
	}
	@Test(priority = 4)
	public void QIDM_159_GapWithCatchUpSpike() throws Exception {
		try {
			new QIDM_159_GapWithCatchUpSpike().gapWithCatchUpSpike();
			Reporter.log("QIDM_159_GapWithCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_159_GapWithCatchUpSpike", t);
		}
	}
	@Test(priority = 5)
	public void QIDM_160_GapWithNoCatchUpSpike() throws Exception {
		try {
			new QIDM_160_GapWithNoCatchUpSpike().gapWithNoCatchUpSpike();
			Reporter.log("QIDM_160_GapWithNoCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_160_GapWithNoCatchUpSpike", t);
		}
	}
	@Test(priority = 6)
	public void QIDM_161_NullWithCatchUpSpike() throws Exception {
		try {
			new QIDM_161_NullWithCatchUpSpike().nullWithCatchUpSpike();
			Reporter.log("QIDM_161_NullWithCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_161_NullWithCatchUpSpike", t);
		}
	}
	@Test(priority = 7)
	public void QIDM_162_NullWithNoCatchUpSpike() throws Exception {
		try {
			new QIDM_162_NullWithNoCatchUpSpike().nullWithNoCatchUpSpike();
			Reporter.log("QIDM_162_NullWithNoCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_162_NullWithNoCatchUpSpike", t);
		}
	}
	@Test(priority = 8)
	public void QIDM_163_RepeatedValueWithCatchUpSpike() throws Exception {
		try {
			new QIDM_163_RepeatedValueWithCatchUpSpike().repeatedValueWithCatchUpSpike();
			Reporter.log("QIDM_163_RepeatedValueWithCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_163_RepeatedValueWithCatchUpSpike", t);
		}
	}
	@Test(priority = 9)
	public void QIDM_164_RepeatedAfterGapWithNoCatchUpSpike() throws Exception {
		try {
			new QIDM_164_RepeatedAfterGapWithNoCatchUpSpike().repeatedAfterGapWithNoCatchUpSpike();
			Reporter.log("QIDM_164_RepeatedAfterGapWithNoCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_164_RepeatedAfterGapWithNoCatchUpSpike", t);
		}
	}
	@Test(priority = 10)
	public void QIDM_165_RepeatedValueWithNoCatchUpSpike() throws Exception {
		try {
			new QIDM_165_RepeatedValueWithNoCatchUpSpike().repeatedValueWithNoCatchUpSpike();
			Reporter.log("QIDM_165_RepeatedValueWithNoCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_165_RepeatedValueWithNoCatchUpSpike", t);
		}
	}
	@Test(priority = 11)
	public void QIDM_166_RepeatedAfterGapWithCatchUpSpike() throws Exception {
		try {
			new QIDM_166_RepeatedAfterGapWithCatchUpSpike().repeatedAfterGapWithCatchUpSpike();
			Reporter.log("QIDM_166_RepeatedAfterGapWithCatchUpSpike is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_166_RepeatedAfterGapWithCatchUpSpike", t);
		}
	}
	@Test(priority = 12)
	public void QIDM_167_RollOver() throws Exception {
		try {
			new QIDM_167_RollOver().rollOver();
			Reporter.log("QIDM_167_RollOver is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_167_RollOver", t);
		}
	}
				@Test(priority = 13)
	public void QIDM_168_WeightedAvgProfile() throws Exception {
		try {
			new QIDM_168_WeightedAvgProfile().weightedAvgProfile();
			Reporter.log("QIDM_168_WeightedAvgProfile is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_168_WeightedAvgProfile", t);
		}
	}
}

package regression.PAMAdminstrationTCs.ManageTags;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class ManageTagsRegression extends TestBase {
	
	@Test(priority = 1)
	public void QIDM_98_DefineTagsAndApplyToLocations() throws Exception {
		try {
			new QIDM_98_DefineTagsAndApplyToLocations().defineTagsAndApplyToLocations();
			Reporter.log("QIDM_98_DefineTagsAndApplyToLocations is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_98_DefineTagsAndApplyToLocations", t);
		}
	}
	
	@Test(priority = 2)
	public void QIDM_101_TagsAndFiltering() throws Exception {
		try {
			new QIDM_101_TagsAndFiltering().tagsAndFiltering();
			Reporter.log("QIDM_101_TagsAndFiltering is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_101_TagsAndFiltering", t);
		}
	}
	
	@Test(priority = 3)
	public void QIDM_102_TagBasedSubTotalMeasurements() throws Exception {
		try {
			new QIDM_102_TagBasedSubTotalMeasurements().tagBasedSubTotalMeasurements();
			Reporter.log("QIDM_102_TagBasedSubTotalMeasurements is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_102_TagBasedSubTotalMeasurements", t);
		}
	}
	
	@Test(priority = 4)
	public void QIDM_117_TagAggregationMeasurement() throws Exception {
		try {
			new QIDM_117_TagAggregationMeasurement().tagAggregationMeasurement();
			Reporter.log("QIDM_117_TagAggregationMeasurement is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_117_TagAggregationMeasurement", t);
		}
	}
	
	@Test(priority = 5)
	public void QIDM_118_TagLimitedMeasurement() throws Exception {
		try {
			new QIDM_118_TagLimitedMeasurement().tagLimitedMeasurement();
			Reporter.log("QIDM_118_TagLimitedMeasurement is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_118_TagLimitedMeasurement", t);
		}
	}

}

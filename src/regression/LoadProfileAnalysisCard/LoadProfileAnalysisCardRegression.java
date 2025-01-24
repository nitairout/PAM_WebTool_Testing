package regression.LoadProfileAnalysisCard;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class LoadProfileAnalysisCardRegression extends TestBase {
	
	@Test(priority =1)
	public void QIDM_45_LoadProfileTimeLineOptions() throws Exception {
		try {
			new QIDM_45_LoadProfileTimeLineOptions().loadProfileTimeLineOptions();
			Reporter.log("QIDM_45_LoadProfileTimeLineOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_45_LoadProfileTimeLineOptions", t);
		} 
	}	
	@Test(priority =2)
	public void QIDM_46_LoadProfileComparisonDateRanges() throws Exception {
		try {
			new QIDM_46_LoadProfileComparisonDateRanges().loadProfileComparisonDateRanges();
			Reporter.log("QIDM_46_LoadProfileComparisonDateRanges is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_46_LoadProfileComparisonDateRanges", t);
		} 
	}
	
	@Test(priority =3)
	public void QIDM_47_LoadProfileOverlayOptions() throws Exception {
		try {
			new QIDM_47_LoadProfileOverlayOptions().loadProfileOverlayOptions();
			Reporter.log("QIDM_47_LoadProfileOverlayOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_47_LoadProfileOverlayOptions", t);
		} 
	}
	
	@Test(priority =4)
	public void QIDM_48_LoadProfileStatistics() throws Exception {
		try {
			new QIDM_48_LoadProfileStatistics().loadProfileStatistics();
			Reporter.log("QIDM_48_LoadProfileStatistics is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_48_LoadProfileStatistics", t);
		} 
	}
	
	@Test(priority =5)
	public void QIDM_49_LoadProfileExportOptions() throws Exception {
		try {
			new QIDM_49_LoadProfileExportOptions().loadProfileExportOptions();
			Reporter.log("QIDM_49_LoadProfileExportOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_49_LoadProfileExportOptions", t);
		} 
	}
	
	@Test(priority =6)
	public void QIDM_82_LoadProfileRawVsCorrectedData() throws Exception {
		try {
			new QIDM_82_LoadProfileRawVsCorrectedData().loadProfileRawVsCorrectedData();
			Reporter.log("QIDM_82_LoadProfileRawVsCorrectedData is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_82_LoadProfileRawVsCorrectedData", t);
		} 
	}
	
	@Test(priority =7)
	public void QIDM_90_LoadProfileDataPointDetails() throws Exception {
		try {
			new QIDM_90_LoadProfileDataPointDetails().loadProfileDataPointDetails();
			Reporter.log("QIDM_90_LoadProfileDataPointDetails is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_90_LoadProfileDataPointDetails", t);
		} 
	}
	
	@Test(priority =8)
	public void QIDM_149_LoadProfileDataDuringDSTTransition() throws Exception {
		try {
			new QIDM_149_LoadProfileDataDuringDSTTransition().loadProfileDataDuringDSTTransition();
			Reporter.log("QIDM_149_LoadProfileDataDuringDSTTransition is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_149_LoadProfileDataDuringDSTTransition", t);
		} 
	}
	
	@Test(priority =9)
	public void QIDM_172_LoadProfileChangingDateRangeWithSlider() throws Exception {
		try {
			new QIDM_172_LoadProfileChangingDateRangeWithSlider().loadProfileChangingDateRangeWithSlider();
			Reporter.log("QIDM_172_LoadProfileChangingDateRangeWithSlider is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_172_LoadProfileChangingDateRangeWithSlider", t);
		} 
	}
	
	@Test(priority =10)
	public void QIDM_178_LoadProfileChartOptionsLegendandTable() throws Exception {
		try {
			new QIDM_178_LoadProfileChartOptionsLegendandTable().loadProfileChartOptionsLegendandTable();
			Reporter.log("QIDM_178_LoadProfileChartOptionsLegendandTable is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_178_LoadProfileChartOptionsLegendandTable", t);
		} 
	}
	
	@Test(priority =11)
	public void QIDM_179_LoadProfileChartOptionsAxes() throws Exception {
		try {
			new QIDM_179_LoadProfileChartOptionsAxes().loadProfileChartOptionsAxes();
			Reporter.log("QIDM_179_LoadProfileChartOptionsAxes is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_179_LoadProfileChartOptionsAxes", t);
		} 
	}
	
	@Test(priority =12)
	public void QIDM_181_LoadProfileAutoCalculatedDemandAndEnergy() throws Exception {
		try {
			new QIDM_181_LoadProfileAutoCalculatedDemandAndEnergy().loadProfileAutoCalculatedDemandAndEnergy();
			Reporter.log("QIDM_181_LoadProfileAutoCalculatedDemandAndEnergy is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_181_LoadProfileAutoCalculatedDemandAndEnergy", t);
		} 
	}
	
	@Test(priority =13)
	public void QIDM_182_LoadProfileChartOptionsSeriesColor() throws Exception {
		try {
			new QIDM_182_LoadProfileChartOptionsSeriesColor().loadProfileChartOptionsSeriesColor();
			Reporter.log("QIDM_182_LoadProfileChartOptionsSeriesColor is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_182_LoadProfileChartOptionsSeriesColor", t);
		} 
	}
	
	@Test(priority =14)
	public void QIDM_184_LoadProfileChartwithMixedIntervalLengths() throws Exception {
		try {
			new QIDM_184_LoadProfileChartwithMixedIntervalLengths().loadProfileChartwithMixedIntervalLengths();
			Reporter.log("QIDM_184_LoadProfileChartwithMixedIntervalLengths is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_184_LoadProfileChartwithMixedIntervalLengths", t);
		} 
	}
	
	
}

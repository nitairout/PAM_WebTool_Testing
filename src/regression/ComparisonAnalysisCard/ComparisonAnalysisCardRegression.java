package regression.ComparisonAnalysisCard;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

//Need to re-check again as the values are overlapping into legend
//QIDM_79_ComparisonAnalysisOverlayOptions

public class ComparisonAnalysisCardRegression extends TestBase {

	@Test(priority =1)
	public void QIDM_64_ComparisonAnalysisTimelineoptions() throws Exception {
		try {
			new QIDM_64_ComparisonAnalysisTimelineoptions().comparisonAnalysisTimelineoptions();
			printLog("QIDM_64_ComparisonAnalysisTimelineoptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_64_ComparisonAnalysisTimelineoptions", t);
		} 
	}
	
	@Test(priority =2)
	public void QIDM_76_ComparisonAnalysisBreakdownBySubmeters() throws Exception {
		try {
			new QIDM_76_ComparisonAnalysisBreakdownBySubmeters().comparisonAnalysisBreakdownBySubmeters();
			printLog("QIDM_76_ComparisonAnalysisBreakdownBySubmeters is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_76_ComparisonAnalysisBreakdownBySubmeters", t);
		} 
	}
	
	@Test(priority =3)
	public void QIDM_77_ComparisonAnalysisBreakdownBySiteSchedule() throws Exception {
		try {
			new QIDM_77_ComparisonAnalysisBreakdownBySiteSchedule().comparisonAnalysisBreakdownBySiteSchedule();
			printLog("QIDM_77_ComparisonAnalysisBreakdownBySiteSchedule is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_77_ComparisonAnalysisBreakdownBySiteSchedule", t);
		} 
	}
	
	@Test(priority =4)
	public void QIDM_78_ComparisonAnalysisIndexByOptions() throws Exception {
		try {
			new QIDM_78_ComparisonAnalysisIndexByOptions().comparisonAnalysisIndexByOptions();
			printLog("QIDM_78_ComparisonAnalysisIndexByOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_78_ComparisonAnalysisIndexByOptions", t);
		} 
	}
	
	
	@Test(priority =5)
	public void QIDM_79_ComparisonAnalysisOverlayOptions() throws Exception {
		try {
			new QIDM_79_ComparisonAnalysisOverlayOptions().comparisonAnalysisOverlayOptions();
			printLog("QIDM_79_ComparisonAnalysisOverlayOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_79_ComparisonAnalysisOverlayOptions", t);
		} 
	}
	
	@Test(priority =6)
	public void QIDM_80_ComparisonAnalysisExportOptions() throws Exception {
		try {
			new QIDM_80_ComparisonAnalysisExportOptions().comparisonAnalysisExportOptions();
			printLog("QIDM_80_ComparisonAnalysisExportOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_80_ComparisonAnalysisExportOptions", t);
		} 
	}
	
	@Test(priority =7)
	public void QIDM_81_ComparisonAnalysisChartOptions() throws Exception {
		try {
			new QIDM_81_ComparisonAnalysisChartOptions().comparisonAnalysisChartOptions();
			printLog("QIDM_81_ComparisonAnalysisChartOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_81_ComparisonAnalysisChartOptions", t);
		} 
	}
	
	@Test(priority =8)
	public void QIDM_170_ComparisonAnalysisRawVsCorrectedData() throws Exception {
		try {
			new QIDM_170_ComparisonAnalysisRawVsCorrectedData().comparisonAnalysisRawVsCorrectedData();
			printLog("QIDM_170_ComparisonAnalysisRawVsCorrectedData is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_170_ComparisonAnalysisRawVsCorrectedData", t);
		} 
	}
	
	@Test(priority =9)
	public void QIDM_213_ComparisonAnalysisIndexByRAMetricsWithDifferentAggregationTypes() throws Exception {
		try {
			new QIDM_213_ComparisonAnalysisIndexByRAMetricsWithDifferentAggregationTypes().comparisonAnalysisIndexByRAMetricsWithDifferentAggregationTypes();
			printLog("QIDM_213_ComparisonAnalysisIndexByRAMetricsWithDifferentAggregationTypes is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_213_ComparisonAnalysisIndexByRAMetricsWithDifferentAggregationTypes", t);
		} 
	}
	
}

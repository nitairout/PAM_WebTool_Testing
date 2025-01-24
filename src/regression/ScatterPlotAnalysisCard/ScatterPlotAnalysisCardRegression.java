package regression.ScatterPlotAnalysisCard;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class ScatterPlotAnalysisCardRegression extends TestBase {
	
	@Test(priority = 1)
	public void QIDM_68_ScatterPlotAnalysisDatabreakdown() throws Exception {
		try {
			new QIDM_68_ScatterPlotAnalysisDatabreakdown().scatterPlotAnalysisDatabreakdown();
			Reporter.log("QIDM_68_ScatterPlotAnalysisDatabreakdown is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_68_ScatterPlotAnalysisDatabreakdown", t);
		}
	}
	
	@Test(priority = 2)
	public void QIDM_69_ScatterPlotAnalysisComparisonDateRanges() throws Exception {
		try {
			new QIDM_69_ScatterPlotAnalysisComparisonDateRanges().scatterPlotAnalysisComparisonDateRanges();
			Reporter.log("QIDM_69_ScatterPlotAnalysisComparisonDateRanges is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_69_ScatterPlotAnalysisComparisonDateRanges", t);
		}
	}
	
	@Test(priority = 3)
	public void QIDM_71_ScatterPlotAnalysisTimeLineOptions() throws Exception {
		try {
			new QIDM_71_ScatterPlotAnalysisTimeLineOptions().scatterPlotAnalysisTimeLineOptions();
			Reporter.log("QIDM_71_ScatterPlotAnalysisTimeLineOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_71_ScatterPlotAnalysisTimeLineOptions", t);
		}
	}
	
	@Test(priority = 4)
	public void QIDM_93_ScatterPlotAnalysisAdvancedOptions() throws Exception {
		try {
			new QIDM_93_ScatterPlotAnalysisAdvancedOptions().scatterPlotAnalysisAdvancedOptions();
			Reporter.log("QIDM_93_ScatterPlotAnalysisAdvancedOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_93_ScatterPlotAnalysisAdvancedOptions", t);
		}
	}
	
	@Test(priority = 5)
	public void QIDM_99_ScatterPlotAnalysisDataTableAndExportChartData() throws Exception {
		try {
			new QIDM_99_ScatterPlotAnalysisDataTableAndExportChartData().scatterPlotAnalysisDataTableAndExportChartData();
			Reporter.log("QIDM_99_ScatterPlotAnalysisDataTableAndExportChartData is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_99_ScatterPlotAnalysisDataTableAndExportChartData", t);
		}
	}
	
	@Test(priority = 6)
	public void QIDM_100_ScatterPlotAnalysisUpdatingSeriesColorandStyles() throws Exception {
		try {
			new QIDM_100_ScatterPlotAnalysisUpdatingSeriesColorandStyles().scatterPlotAnalysisUpdatingSeriesColorandStyles();
			Reporter.log("QIDM_100_ScatterPlotAnalysisUpdatingSeriesColorandStyles is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_100_ScatterPlotAnalysisUpdatingSeriesColorandStyles", t);
		}
	}
	
	@Test(priority = 7)
	public void QIDM_139_ScatterPlotAnalysisChartOption() throws Exception {
		try {
			new QIDM_139_ScatterPlotAnalysisChartOption().scatterPlotAnalysisChartOption();
			Reporter.log("QIDM_139_ScatterPlotAnalysisChartOption is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_139_ScatterPlotAnalysisChartOption", t);
		}
	}

}

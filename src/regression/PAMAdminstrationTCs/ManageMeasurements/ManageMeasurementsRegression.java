package regression.PAMAdminstrationTCs.ManageMeasurements;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class ManageMeasurementsRegression extends TestBase {
	
	@Test(priority = 1)
	public void QIDM_120_CreateNewMeasurement() throws Exception {
		try {
			new QIDM_120_CreateNewMeasurement().createNewMeasurement();
			Reporter.log("QIDM_120_CreateNewMeasurement is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_120_CreateNewMeasurement", t);
		}
	}

	@Test(priority = 2)
	public void QIDM_121_EnableOrDisableMeasurement() throws Exception {
		try {
			new QIDM_121_EnableOrDisableMeasurement().enableOrDisableMeasurement();
			Reporter.log("QIDM_121_EnableOrDisableMeasurement is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_121_EnableOrDisableMeasurement", t);
		}
	}

	@Test(priority = 3)
	public void QIDM_122_ExpressionsWithDifferentAggregationMethods() throws Exception {
		try {
			new QIDM_122_ExpressionsWithDifferentAggregationMethods().expressionsWithDifferentAggregationMethods();
			Reporter.log("QIDM_122_ExpressionsWithDifferentAggregationMethods is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_122_ExpressionsWithDifferentAggregationMethods", t);
		}
	}
	
	@Test(priority = 4)
	public void QIDM_123_ExpressionsWithAggregatingDataAtDifferentIntervals() throws Exception {
		try {
			new QIDM_123_ExpressionsWithAggregatingDataAtDifferentIntervals().expressionsWithAggregatingDataAtDifferentIntervals();
			Reporter.log("QIDM_123_ExpressionsWithAggregatingDataAtDifferentIntervals is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_123_ExpressionsWithAggregatingDataAtDifferentIntervals", t);
		}
	}
	
	@Test(priority = 5)
	public void QIDM_124_ExpressionswithDifferentArithmeticOperators() throws Exception {
		try {
			new QIDM_124_ExpressionswithDifferentArithmeticOperators().expressionswithDifferentArithmeticOperators();
			Reporter.log("QIDM_124_ExpressionswithDifferentArithmeticOperators is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_124_ExpressionswithDifferentArithmeticOperators", t);
		}
	}
	
	@Test(priority = 6)
	public void QIDM_125_ExpressionswithDifferentExponentialOperators() throws Exception {
		try {
			new QIDM_125_ExpressionswithDifferentExponentialOperators().expressionswithDifferentExponentialOperators();
			Reporter.log("QIDM_125_ExpressionswithDifferentExponentialOperators is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_125_ExpressionswithDifferentExponentialOperators", t);
		}
	}
	
	@Test(priority = 7)
	public void QIDM_126_ExpressionsWithTimeAggregation() throws Exception {
		try {
			new QIDM_126_ExpressionsWithTimeAggregation().expressionsWithTimeAggregation();
			Reporter.log("QIDM_126_ExpressionsWithTimeAggregation is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_126_ExpressionsWithTimeAggregation", t);
		}
	}
	
	@Test(priority = 8)
	public void QIDM_131_AdvancedCalculatedExpressions() throws Exception {
		try {
			new QIDM_131_AdvancedCalculatedExpressions().advancedCalculatedExpressions();
			Reporter.log("QIDM_131_AdvancedCalculatedExpressions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_131_AdvancedCalculatedExpressions", t);
		}
	}
	
	@Test(priority = 9)
	public void QIDM_132_ExpressionsAccessingDifferentSiteData() throws Exception {
		try {
			new QIDM_132_ExpressionsAccessingDifferentSiteData().expressionsAccessingDifferentSiteData();
			Reporter.log("QIDM_132_ExpressionsAccessingDifferentSiteData is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_132_ExpressionsAccessingDifferentSiteData", t);
		}
	}
	
	@Test(priority = 10)
	public void QIDM_133_ExpressionsAccessingHistoricalData() throws Exception {
		try {
			new QIDM_133_ExpressionsAccessingHistoricalData().expressionsAccessingHistoricalData();
			Reporter.log("QIDM_133_ExpressionsAccessingHistoricalData is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_133_ExpressionsAccessingHistoricalData", t);
		}
	}
	
	@Test(priority = 11)
	public void QIDM_134_ExpressionsWithRAMetrics() throws Exception {
		try {
			new QIDM_134_ExpressionsWithRAMetrics().expressionsWithRAMetrics();
			Reporter.log("QIDM_134_ExpressionsWithRAMetrics is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_134_ExpressionsWithRAMetrics", t);
		}
	}
	
	@Test(priority = 12)
	public void QIDM_135_MeasurementValidDataStartandEndDates() throws Exception {
		try {
			new QIDM_135_MeasurementValidDataStartandEndDates().measurementValidDataStartandEndDates();
			Reporter.log("QIDM_135_MeasurementValidDataStartandEndDates is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_135_MeasurementValidDataStartandEndDates", t);
		}
	}
	
	@Test(priority = 13)
	public void QIDM_136_MiscellaneousCalculatedMeasurments() throws Exception {
		try {
			new QIDM_136_MiscellaneousCalculatedMeasurments().miscellaneousCalculatedMeasurments();
			Reporter.log("QIDM_136_MiscellaneousCalculatedMeasurments is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_136_MiscellaneousCalculatedMeasurments", t);
		}
	}
	
	@Test(priority = 14)
	public void QIDM_137_ExpressionsWithMixedIntervalLengths() throws Exception {
		try {
			new QIDM_137_ExpressionsWithMixedIntervalLengths().expressionsWithMixedIntervalLengths();
			Reporter.log("QIDM_137_ExpressionsWithMixedIntervalLengths is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_137_ExpressionsWithMixedIntervalLengths", t);
		}
	}
	
	@Test(priority = 15)
	public void QIDM_138_ExpressionsWithSiteSchedules() throws Exception {
		try {
			new QIDM_138_ExpressionsWithSiteSchedules().expressionsWithSiteSchedules();
			Reporter.log("QIDM_138_ExpressionsWithSiteSchedules is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_138_ExpressionsWithSiteSchedules", t);
		}
	}
	
	@Test(priority = 16)
	public void QIDM_140_MakeDataStreamUsingExpression() throws Exception {
		try {
			new QIDM_140_MakeDataStreamUsingExpression().makeDataStreamUsingExpression();
			Reporter.log("QIDM_140_MakeDataStreamUsingExpression is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_140_MakeDataStreamUsingExpression", t);
		}
	}
	
	@Test(priority = 17)
	public void QIDM_141_ExpressionUsingRABaseTemp() throws Exception {
		try {
			new QIDM_141_ExpressionUsingRABaseTemp().expressionUsingRABaseTemp();
			Reporter.log("QIDM_141_ExpressionUsingRABaseTemp is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_141_ExpressionUsingRABaseTemp", t);
		}
	}
	
	@Test(priority = 18)
	public void QIDM_142_ExpressionUsingRAWeather() throws Exception {
		try {
			new QIDM_142_ExpressionUsingRAWeather().expressionUsingRAWeather();
			Reporter.log("QIDM_142_ExpressionUsingRAWeather is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_142_ExpressionUsingRAWeather", t);
		}
	}
	
	@Test(priority = 19)
	public void QIDM_143_CreateMeasurementWithoutUnits() throws Exception {
		try {
			new QIDM_143_CreateMeasurementWithoutUnits().createMeasurementWithoutUnits();
			Reporter.log("QIDM_143_CreateMeasurementWithoutUnits is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_143_CreateMeasurementWithoutUnits", t);
		}
	}
	
	@Test(priority = 20)
	public void QIDM_218_CalculatingEnergyFromDemand() throws Exception {
		try {
			new QIDM_218_CalculatingEnergyFromDemand().calculatingEnergyFromDemand();
			Reporter.log("QIDM_218_CalculatingEnergyFromDemand is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_218_CalculatingEnergyFromDemand", t);
		}
	}
	
}

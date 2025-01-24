package regression.AllCardsCommonTCs;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class AllCardsCommonRegression extends TestBase {	 

	@Test(priority =1)
	public void QIDM_36_CardsHomePage() throws Exception {
		try {
			new QIDM_36_CardsHomePage().cardsHomePage();
			printLog("QIDM_36_CardsHomePage is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_36_CardsHomePage", t);
		} 
	}
	
	@Test(priority =2)
	public void QIDM_37_LocationsPanel() throws Exception {
		try {
			new QIDM_37_LocationsPanel().locationsPanel();
			printLog("QIDM_37_LocationsPanel is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_37_LocationsPanel", t);
		} 
	}
	
	@Test(priority =3)
	public void QIDM_42_CardToCardNavigation() throws Exception {
		try {
			new QIDM_42_CardToCardNavigation().cardToCardNavigation();
			printLog("QIDM_42_CardToCardNavigation is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_42_CardToCardNavigation", t);
		} 
	}
	
	@Test(priority =4)
	public void QIDM_57_AllCardsSaveAndDeleteAnalysisReports() throws Exception {
		try {
			new QIDM_57_AllCardsSaveAndDeleteAnalysisReports().allCardsSaveAndDeleteAnalysisReports();
			printLog("QIDM_57_AllCardsSaveAndDeleteAnalysisReports is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_57_AllCardsSaveAndDeleteAnalysisReports", t);
		} 
	}
	
	@Test(priority =5)
	public void QIDM_171_AllCardsLargeDataWarningMessage() throws Exception {
		try {
			new QIDM_171_AllCardsLargeDataWarningMessage().allCardsLargeDataWarningMessage();
			printLog("QIDM_171_AllCardsLargeDataWarningMessage is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_171_AllCardsLargeDataWarningMessage", t);
		} 
	}
	
	@Test(priority =6)
	public void QIDM_174_AllCardsDataDetailsPopUp() throws Exception {
		try {
			new QIDM_174_AllCardsDataDetailsPopUp().allCardsDataDetailsPopUp();
			printLog("QIDM_174_AllCardsDataDetailsPopUp is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_174_AllCardsDataDetailsPopUp", t);
		} 
	}
	
	@Test(priority =7)
	public void QIDM_187_PowerFactorCalculations() throws Exception {
		try {
			new QIDM_187_PowerFactorCalculations().powerFactorCalculations();
			printLog("QIDM_187_PowerFactorCalculations is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_187_PowerFactorCalculations", t);
		} 
	}
	
	@Test(priority =8)
	public void QIDM_188_MissingIntervalData() throws Exception {
		try {
			new QIDM_188_MissingIntervalData().missingIntervalData();
			printLog("QIDM_188_MissingIntervalData is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_188_MissingIntervalData", t);
		} 
	}
	
	@Test(priority =9)
	public void QIDM_214_AllCardsTimelineLastXIntervals() throws Exception {
		try {
			new QIDM_214_AllCardsTimelineLastXIntervals().allCardsTimelineLastXIntervals();
			printLog("QIDM_214_AllCardsTimelineLastXIntervals is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_214_AllCardsTimelineLastXIntervals", t);
		} 
	}
	
	@Test(priority =10)
	public void QIDM_216_AllCardsHideSeriesFromLegend() throws Exception {
		try {
			new QIDM_216_AllCardsHideSeriesFromLegend().allCardsHideSeriesFromLegend();
			printLog("QIDM_216_AllCardsHideSeriesFromLegend is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_216_AllCardsHideSeriesFromLegend", t);
		} 
	}
	
	@Test(priority =11)
	public void QIDM_219_WeatherDatawith30minutesoffsetTZ() throws Exception {
		try {
			new QIDM_219_WeatherDatawith30minutesoffsetTZ().weatherDatawith30minutesoffsetTZ();
			printLog("QIDM_219_WeatherDatawith30minutesoffsetTZ is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_219_WeatherDatawith30minutesoffsetTZ", t);
		} 
	}
	
	@Test(priority =12)
	public void QIDM_220_OneClickModeActivateSelectedLocation() throws Exception {
		try {
			new QIDM_220_OneClickModeActivateSelectedLocation().oneClickModeActivateSelectedLocation();
			printLog("QIDM_220_OneClickModeActivateSelectedLocation is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_220_OneClickModeActivateSelectedLocation", t);
		} 
	}
	
	@Test(priority =13)
	public void QIDM_221_OneClickModeActivateDescendantsMatchingCriteria() throws Exception {
		try {
			new QIDM_221_OneClickModeActivateDescendantsMatchingCriteria().oneClickModeActivateDescendantsMatchingCriteria();
			printLog("QIDM_221_OneClickModeActivateDescendantsMatchingCriteria is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_221_OneClickModeActivateDescendantsMatchingCriteria", t);
		} 
	}
	
	@Test(priority =14)
	public void QIDM_249_CommonAnalysis() throws Exception {
		try {
			new QIDM_249_CommonAnalysis().commonAnalysis();
			printLog("QIDM_249_CommonAnalysis is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_249_CommonAnalysis", t);
		} 
	}
	
	
}

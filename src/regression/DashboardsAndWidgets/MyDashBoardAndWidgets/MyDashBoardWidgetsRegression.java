package regression.DashboardsAndWidgets.MyDashBoardAndWidgets;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class MyDashBoardWidgetsRegression extends TestBase {
	
	@Test(priority =1)
	public void QIDM_207_AllCardsExistingWidgets() throws Exception {
		try {
			new QIDM_207_AllCardsExistingWidgets().allCardsExistingWidgets();
			Reporter.log("QIDM_207_AllCardsExistingWidgets is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_207_AllCardsExistingWidgets", t);
		} 
	}
	
	@Test(priority =2)
	public void QIDM_208_WidgetDownloadOptions() throws Exception {
		try {
			new QIDM_208_WidgetDownloadOptions().widgetDownloadOptions();
			Reporter.log("QIDM_208_WidgetDownloadOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_208_WidgetDownloadOptions", t);
		} 
	}
	
	@Test(priority =3)
	public void QIDM_209_CLPWidgetCreateFromWidgetLibrary() throws Exception {
		try {
			new QIDM_209_CLPWidgetCreateFromWidgetLibrary().clpWidgetCreateFromWidgetLibrary();
			Reporter.log("QIDM_209_CLPWidgetCreateFromWidgetLibrary is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_209_CLPWidgetCreateFromWidgetLibrary", t);
		} 
	}
	
	@Test(priority =4)
	public void QIDM_210_ComparisonWidgetCreateFromWidgetLibrary() throws Exception {
		try {
			new QIDM_210_ComparisonWidgetCreateFromWidgetLibrary().comparisonWidgetCreateFromWidgetLibrary();
			Reporter.log("QIDM_210_ComparisonWidgetCreateFromWidgetLibrary is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_210_ComparisonWidgetCreateFromWidgetLibrary", t);
		} 
	}
	
	@Test(priority =5)
	public void QIDM_211_ScatterPlotWidgetCreateFromWidgetLibrary() throws Exception {
		try {
			new QIDM_211_ScatterPlotWidgetCreateFromWidgetLibrary().scatterPlotWidgetCreateFromWidgetLibrary();
			Reporter.log("QIDM_211_ScatterPlotWidgetCreateFromWidgetLibrary is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_211_ScatterPlotWidgetCreateFromWidgetLibrary", t);
		} 
	}
	
	@Test(priority =6)
	public void QIDM_212_TrendWidgetCreateFromWidgetLibrary() throws Exception {
		try {
			new QIDM_212_TrendWidgetCreateFromWidgetLibrary().trendWidgetCreateFromWidgetLibrary();
			Reporter.log("QIDM_212_TrendWidgetCreateFromWidgetLibrary is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_212_TrendWidgetCreateFromWidgetLibrary", t);
		} 
	}
	
	@Test(priority =7)
	public void QIDM_215_ShareDashboard() throws Exception {
		try {
			new QIDM_215_ShareDashboard().shareDashboard();
			Reporter.log("QIDM_215_ShareDashboard is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_215_ShareDashboard", t);
		} 
	}
	
	@Test(priority =8)
	public void QIDM_222_LoadProfileWidgetCreateFromWidgetLibrary() throws Exception {
		try {
			new QIDM_222_LoadProfileWidgetCreateFromWidgetLibrary().loadProfileWidgetCreateFromWidgetLibrary();
			Reporter.log("QIDM_222_LoadProfileWidgetCreateFromWidgetLibrary is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_222_LoadProfileWidgetCreateFromWidgetLibrary", t);
		} 
	}	
}

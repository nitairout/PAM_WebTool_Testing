package regression.CalendarLoadProfileCard;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class CalendarLoadProfileCardRegression extends TestBase {
	 

	@Test(priority =1)
	public void QIDM_51_CalendarLoadProfileTimelineOverlayChartOptions() throws Exception {
		try {
			new QIDM_51_CalendarLoadProfileTimelineOverlayChartOptions().calendarLoadProfileTimelineOverlayChartOptions();
			printLog("QIDM_51_CalendarLoadProfileTimelineOverlayChartOptions is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_51_CalendarLoadProfileTimelineOverlayChartOptions", t);
		} 
	}
	
	@Test(priority =2)
	public void QIDM_58_CalendarLoadProfileCompareAndTableDataExport() throws Exception {
		try {
			new QIDM_58_CalendarLoadProfileCompareAndTableDataExport().calendarLoadProfileCompareAndTableDataExport();
			printLog("QIDM_58_CalendarLoadProfileCompareAndTableDataExport is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_58_CalendarLoadProfileCompareAndTableDataExport", t);
		} 
	}

}

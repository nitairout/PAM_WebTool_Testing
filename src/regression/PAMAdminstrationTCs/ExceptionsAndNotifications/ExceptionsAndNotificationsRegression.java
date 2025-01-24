package regression.PAMAdminstrationTCs.ExceptionsAndNotifications;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.TestBase;
import util.ErrorUtil;

public class ExceptionsAndNotificationsRegression extends TestBase {
	 

	@Test(priority =1)
	public void QIDM_88_IntervalDataAlertDefinition() throws Exception {
		try {
			new QIDM_88_IntervalDataAlertDefinition().intervalDataAlertDefinition();
			Reporter.log("QIDM_88_IntervalDataAlertDefinition is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_88_IntervalDataAlertDefinition", t);
		} 
	}
	
	@Test(priority =2)
	public void QIDM_92_IntervalDataReportDefinition() throws Exception {
		try {
			new QIDM_92_IntervalDataReportDefinition().intervalDataReportDefinition();
			Reporter.log("QIDM_92_IntervalDataReportDefinition is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_92_IntervalDataReportDefinition", t);
		} 
	}
	
	@Test(priority =3)
	public void QIDM_107_DataStreamsDownload() throws Exception {
		try {
			new QIDM_107_DataStreamsDownload().dataStreamsDownload();
			Reporter.log("QIDM_107_DataStreamsDownload is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_107_DataStreamsDownload", t);
		} 
	}
	
	@Test(priority =4)
	public void QIDM_114_SiteMapping() throws Exception {
		try {
			new QIDM_114_SiteMapping().siteMapping();
			Reporter.log("QIDM_114_SiteMapping is executed sucessfully");
		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Error in QIDM_114_SiteMapping", t);
		} 
	}

}

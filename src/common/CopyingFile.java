package common;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
public class CopyingFile{
	public void MovetoFolder() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		try {
			File afile = new File(System.getProperty("user.dir")+"\\test-output\\emailable-report.html");
			File bfile = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\PAM_Bin\\Test Output\\TestOutput "+ strDate + ".html");
			File Logs = new File(System.getProperty("user.dir")+"\\Application.log");
			File Logsmove = new File("C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\PAM_Bin\\Selenium Logs\\LogFile "+ strDate + ".log");
			FileUtils.copyFile(afile, bfile);
			FileUtils.copyFile(Logs, Logsmove);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
package common;
import java.io.File;

import org.testng.annotations.Test;

public class DeleteFilesTC extends TestBase {

	@Test
	public void testDelete() {
		File directory = new File(Constant.SRC_FOLDER);
		try {
			String files[] = directory.list();
			for (String temp : files) {
				File d = new File(Constant.SRC_FOLDER + "/" + temp);
				// recursive delete
				d.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
		}
	}
}

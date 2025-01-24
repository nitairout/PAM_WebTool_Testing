/**
 * File Name: Constant
* The PamAutoConstant interface will hold all the constants
*
* @author Cambridge Technologies.
* @contact Cambridge Technologies
* @since   29-June-2017 
* 
*/

package common;

import org.apache.log4j.Logger;

public interface Constant {
	String DOWNLOAD_PATH = "C:\\Users\\"+System.getProperty("user.name")+ "\\Downloads";
	String SRC_FOLDER = "C:\\Users\\"+ System.getProperty("user.name") + "\\Downloads";
	String SCREEN_COMPARE_FOLDER = "C:\\Users\\"+ System.getProperty("user.name") + "\\Downloads\\ScreenCompare\\";
	
	public static Logger APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");
}

package common;
import java.io.File;
import jxl.Sheet;
import jxl.Workbook;
public class ValueDTO {
	private String URl;
	private String username;
	private String password;
	private String client;
	private String chromedriverpath;
	private String geckodriver;
	private String phantomJsPath;
	private String Iepath;
	private String email;
	private String firefoxprofilepath;
	private String chromeprofilepath;
	private String compareurl1;
	private String webDriverObj;
	private String webDriverObj1;
	private String languageSelection;
	private String pageName;
	private String internalusername;
	private String internalPassword;
	private String extentReportAttachment;
	// PAM related
	private String loadProfileAnalysisTitle;
	private String startDate ;
	private String endDate ;
	public String getLanguageSelection() {
		return languageSelection;
	}

	public void setLanguageSelection(String languageSelection) {
		this.languageSelection = languageSelection;
	}

	public String getWebDriverObj() {
		return webDriverObj;
	}

	public void setWebDriverObj(String webDriverObj) {
		this.webDriverObj = webDriverObj;
	}

	public String getWebDriverObj1() {
		return webDriverObj1;
	}

	public void setWebDriverObj1(String webDriverObj1) {
		this.webDriverObj1 = webDriverObj1;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getChromedriverpath() {
		return chromedriverpath;
	}

	public void setChromedriverpath(String chromedriverpath) {
		this.chromedriverpath = chromedriverpath;
	}
	
	public String getGeckodriverpath() {
		return geckodriver;
	}

	public void setGeckodriverpath(String geckodriver) {
		this.geckodriver = geckodriver;
	}

	public String getIepath() {
		return Iepath;
	}

	public void setIepath(String Iepath) {
		this.Iepath = Iepath;
	}

	public String getFirefoxProfilePath() {
		return firefoxprofilepath;
	}

	public void setFirefoxProfilePath(String firefoxprofilepath) {
		this.firefoxprofilepath = firefoxprofilepath;
	}

	public String getChromeProfilePath() {
		return chromeprofilepath;
	}

	public void setChromeProfilePath(String chromeprofilepath) {
		this.chromeprofilepath = chromeprofilepath;
	}

	public String getURl() {
		return URl;
	}

	public void setURl(String uRl) {
		URl = uRl;
	}

	public String getcompareurl1() {
		return compareurl1;
	}

	public void setcompareurl1(String compareurl1) {
		this.compareurl1 = compareurl1;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	// Move widgets to Other Pages
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
	//Phantom JS
	public String getPhantomJsPath() {
		return phantomJsPath;
	}

	public void setPhantomJsPath(String phantomJsPath) {
		this.phantomJsPath = phantomJsPath;
	}
	
	public String getLoadProfileAnalysisTitle() {
		return loadProfileAnalysisTitle;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public String getInternalusername() {
		return internalusername;
	}

	public void setInternalusername(String internalusername) {
		this.internalusername = internalusername;
	}

	public String getInternalPassword() {
		return internalPassword;
	}

	public void setInternalPassword(String internalPassword) {
		this.internalPassword = internalPassword;
	}
	
	public void setExtentReportAttachment(String extentReportAttachment) {
		this.extentReportAttachment = extentReportAttachment;
	}
	public String getExtentReportAttachment() {
		return extentReportAttachment;
	}
	public ValueDTO() {
		try {
			Workbook wb;
			File file1 = new File("./TestData/Test Data.xls");
			Sheet sh, sh1, sh4;
			wb = Workbook.getWorkbook(file1);
			sh = wb.getSheet("Input Sheet");
			sh1 = wb.getSheet("Tech Config");
			//sh4 = wb.getSheet("PAM");
			this.URl = sh.getCell(0, 2).getContents();
			
			//This block is used to use the env value from azure
			String Execution_Env = System.getenv("Execution_Env");
			if(Execution_Env==null || Execution_Env.equals("null")) {
				//this.URl =	"https://tk1.dev.summitenergy.com";
				this.URl =	"https://core.stg1.resourceadvisor.schneider-electric.com";
				//this.URl =	"https://resourceadvisor.schneider-electric.com";
				//this.URl =	"https://tk3.dev.summitenergy.com";
			}else {
				switch (Execution_Env) {
				case "Prod":
					this.URl =	"https://resourceadvisor.schneider-electric.com";
					break;
				case "Stage":
					this.URl =	"https://core.stg1.resourceadvisor.schneider-electric.com";
					break;
				case "TK1":
					this.URl =	"https://tk1.dev.summitenergy.com";
					break;
				case "TK3":
					this.URl =	"https://tk3.dev.summitenergy.com";
					break;
				default:
					this.URl =	"https://resourceadvisor.schneider-electric.com";
					break;
				}
			}
			//this.URl =	"https://tk3.dev.summitenergy.com";
			this.username = sh.getCell(1, 2).getContents();
			this.password = sh.getCell(2, 2).getContents();
			this.internalusername = sh.getCell(1, 3).getContents();
			//this.internalPassword = sh.getCell(2, 3).getContents();
			this.internalPassword = "Autouser1!@#";
			this.webDriverObj = sh.getCell(3, 2).getContents();
			//this.webDriverObj ="firefox";
			this.webDriverObj ="chrome";
			//this.webDriverObj ="edge";
			
			this.client = sh.getCell(4, 2).getContents();
			this.email = sh.getCell(5, 2).getContents();
			this.chromedriverpath = sh1.getCell(0, 1).getContents();
			this.firefoxprofilepath = sh1.getCell(1, 1).getContents();
			this.geckodriver=sh1.getCell(1, 2).getContents();
			this.chromeprofilepath = sh1.getCell(2, 1).getContents();
			this.Iepath = sh1.getCell(3, 1).getContents();
			this.phantomJsPath = sh1.getCell(4, 1).getContents();
			this.pageName = sh.getCell(0, 6).getContents();
			this.languageSelection = sh.getCell(6, 2).getContents();
			this.extentReportAttachment = sh.getCell(7, 2).getContents();
		} catch (Exception e) {
			e.printStackTrace();
			this.URl = "http://https://tk3.dev.summitenergy.com/";
		}
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}

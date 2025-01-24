import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;
public class EmailableReportToExcelPrinting {
    //private static final String FILE_NAME = "E://MyFirstExcel.xlsx";

    public static String returnTheExcelFile(Set<String> allExecutedTCNamesFromHtml,LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allExecutedTCNamesToProcess) {
    	 String excelFileName = "C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\PAM_Bin\\EmailableReportExcel.xlsx";
       try {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Report Sheet");
        
        CellStyle cellStyleForPassed = workbook.createCellStyle(); 
        XSSFFont fontForSuccess = workbook.createFont();
	     fontForSuccess.setFontHeightInPoints((short)11);
	     fontForSuccess.setColor(IndexedColors.GREEN.getIndex());
	     cellStyleForPassed.setFont(fontForSuccess);
	     
	     XSSFCellStyle mainTCStyle = workbook.createCellStyle();
	     mainTCStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(237, 146, 35),new DefaultIndexedColorMap()));
	     mainTCStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	     XSSFFont mainTCFont = workbook.createFont();
	     mainTCFont.setBold(true);
	     mainTCStyle.setFont(mainTCFont);
	     
	     
	     XSSFCellStyle headedStyle = workbook.createCellStyle();
	     headedStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(65, 89, 130),new DefaultIndexedColorMap()));
	     headedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	     headedStyle.setAlignment(HorizontalAlignment.CENTER);
	     headedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	     XSSFFont headedFont = workbook.createFont();
	     headedFont.setColor(IndexedColors.WHITE.getIndex());
	     headedFont.setBold(true);
	     headedStyle.setFont(headedFont);
	     
	     CellStyle cellStyleForFailed = workbook.createCellStyle();        
	     XSSFFont fontForFailure = workbook.createFont();
	     fontForFailure.setFontHeightInPoints((short)11);
	     fontForFailure.setColor(IndexedColors.RED.getIndex());
	     cellStyleForFailed.setFont(fontForFailure);
	    
        String[] headerRows={"Sl #","Test Case Name","Status","Comment"};
        //Created the header row
        Row row =sheet.createRow(0);
        Cell cell =null; 
        int colNum = 0;
        for(String headRow:headerRows){
        	cell=row.createCell(colNum++);
        	cell.setCellStyle(headedStyle);
        	cell.setCellValue(headRow);
        }
        //emailReport
       // LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allExecutedTCNames=reportStatus("D:/Schnider Test Report/0812206/emailable-report.html");
        LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allExecutedTCNames=allExecutedTCNamesToProcess;
        LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> finalHM=new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
        LinkedHashMap<String, ArrayList<String>> failedHM=allExecutedTCNames.get("FailedReport");
        LinkedHashMap<String, ArrayList<String>> passedHM=allExecutedTCNames.get("PassedReport");
       // Set<String> passedTCKEy=passedHM.keySet();
        //Set<String> failedTCKEy=failedHM.keySet();
        Set<String> allTCKeySet=allExecutedTCNamesFromHtml;
        //Set<String> allTCKeySet=new HashSet<String>();
		/*
		 * for(String key:passedTCKEy){ allTCKeySet.add(key); } for(String
		 * key:failedTCKEy){ allTCKeySet.add(key); }
		 */
        LinkedHashMap<String, ArrayList<String>> allTCnames=null;
        ArrayList<String> passedArray=new ArrayList<String>();
        ArrayList<String> failededArray=new ArrayList<String>();
        for(String key:allTCKeySet){
        	allTCnames=new LinkedHashMap<String, ArrayList<String>>();
        	passedArray=passedHM.get(key);
        	failededArray=failedHM.get(key);
        	if(passedArray!=null)
        		allTCnames.put("Passed", passedArray);
        	if(failededArray!=null)
        		allTCnames.put("Failed", failededArray);
        	finalHM.put(key, allTCnames);
        	passedHM.remove(key);
        	failedHM.remove(key);
        }
        Set<String> fnalHMKeys=finalHM.keySet();
        LinkedHashMap<String, ArrayList<String>> finaltcResults=null;
        ArrayList<String> tcArray=null;
        Set<String> tcArrayKeys=null;
        int rowcounter=1,serialNum=0;
        String mainTestArr[]=null;
        String mainTestToPrint="";
        for(String mainTCName:fnalHMKeys){
        	mainTestArr=mainTCName.split("\\.");
        	mainTestToPrint=mainTestArr[mainTestArr.length-1];
        	colNum = 0;
        	finaltcResults=new LinkedHashMap<String, ArrayList<String>>();
        	//print the serial number
        	row =sheet.createRow(rowcounter);
        	sheet.setColumnWidth(colNum, (short) (9 * 256)); 
        	cell=row.createCell(colNum++);
        	cell.setCellValue(++serialNum);
        	//Print main TC name
        	sheet.setColumnWidth(colNum, (short) (50 * 256));
        	
        	cell=row.createCell(colNum++);
        	cell.setCellStyle(mainTCStyle);
        	cell.setCellValue(mainTestToPrint);
        	//not to print status for main TC
        	sheet.setColumnWidth(colNum, (short) (15 * 256)); 
        	cell=row.createCell(colNum++);
        	cell.setCellStyle(mainTCStyle);
        	cell.setCellValue("");
        	
        	sheet.setColumnWidth(colNum, (short) (50 * 256)); 
        	cell=row.createCell(colNum++);
        	cell.setCellStyle(mainTCStyle);
        	cell.setCellValue("");
        	 //System.out.println(mainTCName);
        	 finaltcResults=finalHM.get(mainTCName);
        	 //System.out.println(mainTCName+":::fnalHMKey:::"+finaltcResults);
        	 tcArrayKeys=finaltcResults.keySet();
        	 for(String statusVal:tcArrayKeys){
        		tcArray=finaltcResults.get(statusVal);
        		 for(String subClsName:tcArray){
        			 ++rowcounter;
        			 colNum = 0;
        			 row =sheet.createRow(rowcounter);
        			// System.out.println(mainTCName+"::::::::::::"+statusVal+"::::::::::::"+subClsName);
        			 //Printing the serial number
        			 cell=row.createCell(colNum++);
        			 cell.setCellValue("");
        			 //Printing the sub TC names
        			 cell=row.createCell(colNum++);
        			 cell.setCellValue(subClsName);
        	        //Printing the status value	
        			 cell=row.createCell(colNum++);
        			 // cellStyleForFailed,cellStyleForPassed
        			 if("Passed".equalsIgnoreCase(statusVal))
        				 cell.setCellStyle(cellStyleForPassed);
        			 else
        				 cell.setCellStyle(cellStyleForFailed); 
        			 cell.setCellValue(statusVal);
        			 
        			cell=row.createCell(colNum++);
        	        cell.setCellValue("");
        		 }
        	 }
        	 ++rowcounter;
        }
        
        
      /*  row =sheet.createRow(rowcounter+2);
        cell = row.createCell((short) 1);
        cell.setCellValue(new XSSFRichTextString("Total number of test cases executed is : "+(passedArray.size()+failededArray.size())));
        sheet.addMergedRegion(new CellRangeAddress((rowcounter+2), (rowcounter+2), 1, 3));
        
        row =sheet.createRow(rowcounter+3);
        cell = row.createCell((short) 1);
        cell.setCellStyle(cellStyleForPassed);
        cell.setCellValue(new XSSFRichTextString("Total number of passed test cases is : "+(passedArray.size())));
        sheet.addMergedRegion(new CellRangeAddress((rowcounter+3), (rowcounter+3), 1, 3));
        
        row =sheet.createRow(rowcounter+4);
        cell = row.createCell((short) 1);
        cell.setCellStyle(cellStyleForFailed);
        cell.setCellValue(new XSSFRichTextString("Total number of failed test cases is : "+(failededArray.size())));
        sheet.addMergedRegion(new CellRangeAddress((rowcounter+4), (rowcounter+4), 1, 3));*/
    

            FileOutputStream outputStream = new FileOutputStream(excelFileName);
            workbook.write(outputStream);
            workbook.close();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
        	//File file=new File(FILE_NAME);
        	//if(file.exists())
        		//file.delete();
        }
       return excelFileName;
    }
  
    public static LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> reportStatus(String fileName){
    	LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allTheRepostsHM=null;
	    try {
	    	File input = new File(fileName);
	    	Document html = Jsoup.parse(input, "UTF-8");
	        Element table = html.select("table").get(1);
	        Elements failedeven = table.select("tr[class=failedeven]");
	        Elements passedeven = table.select("tr[class=passedeven]");
	        LinkedHashMap<String, ArrayList<String>> passedClassAndMethod=new LinkedHashMap<String, ArrayList<String>>();
	        LinkedHashMap<String, ArrayList<String>> failedClassAndMethod=new LinkedHashMap<String, ArrayList<String>>();
	        allTheRepostsHM=new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
	        ArrayList<String> methodArray = null;
	        Element row;Elements cols;
	        String key="";
	        for (int i = 0; i <failedeven.size(); i++){
	            row = failedeven.get(i);
	            cols = row.select("td");
	            if(cols.size()==4){
	            	methodArray=new ArrayList<String>();
	            	key=cols.get(0).text();
	            	methodArray.add(cols.get(1).text());
	            }else{
	            	methodArray.add(cols.get(0).text());
	            }
	            if(!failedClassAndMethod.containsKey(key)){
	            	failedClassAndMethod.put(key, methodArray);
	            }
	        }
	        methodArray = null;
	        for (int i = 0; i <passedeven.size(); i++){
	            row = passedeven.get(i);
	            cols = row.select("td");
	            if(cols.size()==4){
	            	methodArray=new ArrayList<String>();
	            	key=cols.get(0).text();
	            	methodArray.add(cols.get(1).text());
	            }else{
	            	methodArray.add(cols.get(0).text());
	            }
	            
	            if(!passedClassAndMethod.containsKey(key)){
	            	passedClassAndMethod.put(key, methodArray);
	            }
	        }
	     allTheRepostsHM.put("PassedReport", passedClassAndMethod);
	     allTheRepostsHM.put("FailedReport", failedClassAndMethod);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }	
	    return allTheRepostsHM;
}
    
    /*
public static void sendTheExcelAttachMent(String emailReport){
	String filename=returnTheExcelFile(null,emailReport);
	final String username = "se.pamautomation";
	final String password = "34fz3y8jge3";
	String from = "resourceadvisoradmin@ems.schneider-electric.com";
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.sendgrid.net");
	props.put("mail.smtp.port", 25);
	Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
	try {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO,new InternetAddress("Lakshmi.Boggala@schneider-electric.com"));
		//This block used for single TC. If the report having single TC then it will display in subject line.
		message.setSubject("Excel report of Automated test Output file");
		BodyPart messageBodyPart = new MimeBodyPart();
		StringBuffer emailBody=new StringBuffer();
		emailBody.append("<html lang='en'><head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>");
		emailBody.append("<style>table {border-collapse: collapse;width: 90%;}</style></head>");
		emailBody.append("<body>Hi Lakshmi,<br><br>Please find the attached dumping test results of executed emailable report.<br>");
		//emailBody.append("<br>NOTE: Please do not reply to this auto-generated e-mail.");
		emailBody.append("<br><br>Warm Regards,<br>Team Automation.</body></html>");
		//messageBodyPart.setText(emailBody);
		messageBodyPart.setContent(emailBody.toString(), "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		multipart.addBodyPart(messageBodyPart);
		message.setContent(multipart);
		Transport.send(message);
		
	} catch (MessagingException mex) {
		mex.printStackTrace();
	} 
}*/
    
}
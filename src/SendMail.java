import java.io.*;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import common.*;
public class SendMail extends TestBase {
	public static void main(String args[]) throws Exception {
		String filename = "emailable-report.html";
		String systemName=InetAddress.getLocalHost().getHostAddress();
		String reportStatus="";
		String extentReport ="PamAutomationReport.html";
		String filePath=System.getProperty("user.dir") + "\\test-output\\";
		ValueDTO Dto = new ValueDTO();
		CopyingFile cf = new CopyingFile();
		cf.MovetoFolder();
		
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss");// dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
			FileUtils.copyFile(new File(filePath+"testng-failed.xml"), new File("C:\\Users\\"+System.getProperty("user.name")+"\\Desktop\\PAM_Bin\\FailedTestNG\\"+strDate+"_testng-failed.xml"));
			}catch(Exception e) {
				
			}
		
		
		
		Set<String> allExecutedTCNames=getExecutedTCNames(filePath+filename);
		//This block used for single TC. If the report having single TC then it will display in subject line.
		String singleTCexecutionRep="";
		String testCaseArr[]=null;
		if(allExecutedTCNames.size()==1){
			for (String tcName : allExecutedTCNames) {
				testCaseArr=tcName.split("\\.");
				tcName=testCaseArr[testCaseArr.length-1];
				singleTCexecutionRep=" - "+tcName;
	    	 }
		}

		int passedNumber=0;
		int failedNumber=0;
		
		LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allExecutedTCNamesToProcess=EmailableReportToExcelPrinting.reportStatus((filePath+filename));
		LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> allExecutedTCNamesToProcessInExcel=EmailableReportToExcelPrinting.reportStatus((filePath+filename));;
		Set<String> allExecutedTCNamesInExcel=getExecutedTCNames(filePath+filename);;
        LinkedHashMap<String, ArrayList<String>> failedHM=allExecutedTCNamesToProcess.get("FailedReport");
        LinkedHashMap<String, ArrayList<String>> passedHM=allExecutedTCNamesToProcess.get("PassedReport");
        LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> finalHM=new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
        
        
        //calculating the passed and failed report
        
        for (Map.Entry<String, ArrayList<String>> i :
        	failedHM.entrySet()) {
        	failedNumber=failedNumber+i.getValue().size();
       }
        
        for (Map.Entry<String, ArrayList<String>> i :
        	passedHM.entrySet()) {
        	passedNumber=passedNumber+i.getValue().size();
       }
        
      //  System.out.println(passedNumber);
       // System.out.println(failedNumber);
        
        if(failedNumber>=1)
        	reportStatus="Failed";
        else
        	reportStatus="Passed";
        
        
        
        //Added logic to print the test cases name into email
        Set<String> allTCKeySet=allExecutedTCNames;
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
        //Logic end to add the test cases names
        
		String hostName="devmailrelay.ses.int";
		String fromAddress="no-reply@ems.schneider-electric.com";
		String envName="";
		if("https://tk4.dev.summitenergy.com".contains(Dto.getURl()))
			envName="TK4 ";
		else if("https://tk1.dev.summitenergy.com".contains(Dto.getURl()))
			envName="TK1 ";
		else if("https://core.stg1.resourceadvisor.schneider-electric.com/login.aspx".contains(Dto.getURl()))
			envName="Stage ";
		else if("https://resourceadvisor.schneider-electric.com".contains(Dto.getURl()))
			envName="Prod ";
		
		File drFile = new File(System.getProperty("user.dir") + "\\src\\config\\TestData.properties");
		FileInputStream drFileInput = new FileInputStream(drFile);
		Properties drProp = new Properties();
		drProp.load(drFileInput);
		drFileInput.close();
		//String to =drProp.getProperty("Emailids");
		String to = Dto.getEmail();
		//String to = "nrout@ctepl.com";
		String Email[] = to.split(",");
		Properties props = new Properties();
		if(systemName.equalsIgnoreCase("10.174.86.50")) {
			hostName="devmailrelay.ses.int";
			props.put("mail.smtp.starttls.enable", "true");
		}else {
			hostName="mailrelay.ses.int";
			props.put("mail.smtp.starttls.enable", "false");
		}
		props.put("mail.smtp.host", hostName);
		props.put("mail.smtp.port", 25);
		Session session = Session.getInstance(props);
				
		try {
			MimeMessage message = new MimeMessage(session);
			message.addHeader("Content-type", "text/HTML; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8bit");
			message.setFrom(new InternetAddress(fromAddress));
			for (int i = 0; i < Email.length; i++) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(Email[i]));
			}
			
			BodyPart messageBodyPart = new MimeBodyPart();
			StringBuffer emailBody=new StringBuffer();
			
			String subject = fnalHMKeys.iterator().next();

					
			//Adding subject to the email
			if(subject.contains("Sanity"))
				message.setSubject(envName+"| PAM Smoke Test Results | "+reportStatus);
			else if(subject.contains("Regression"))
				message.setSubject(envName+"| PAM Regression Test Results | "+reportStatus);
			else if(subject.contains("PAMNewWidgets"))
				message.setSubject(envName+"| PAM New Widget Test Results | "+reportStatus);
			else if(subject.contains("IMServ_DailyAllScheduleReport"))
				message.setSubject("IMserv Daily Scheduled Reports | "+reportStatus);
			else if(subject.contains("OffLine_DailyAllScheduleReport"))
				message.setSubject("Offline Daily Scheduled Reports | "+reportStatus);
			else if(subject.contains("PAM_DailyAllScheduleReport"))
				message.setSubject("PAM Daily Scheduled Reports | "+reportStatus);
			else
				message.setSubject(envName+"| PAM Test Results | "+singleTCexecutionRep+" | "+reportStatus);
			//Preparing body for the email
			emailBody.append("<html lang='en'><head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>");
			emailBody.append("<style>table {border-collapse: collapse;width: 90%;}</style></head>");
			emailBody.append("<body>Hello,<br>&nbsp;&nbsp;Please find the attached Automated test report generated for the below criteria from "+systemName+". <br><br>");
			if(singleTCexecutionRep.contains("AllDailyScheduleReport")){
				emailBody.append("<table><tr><td>Environment</td><td><b>Prod & Stage</b></td>");
			}else{
				emailBody.append("<table><tr><td>Environment</td><td><b>"+Dto.getURl()+"</b></td>");
			}
			emailBody.append("</tr><tr><td>Browser Name</td><td><b>"+Dto.getWebDriverObj()+"</b></td></tr><tr>");
			emailBody.append("<td>Selected Language</td><td><b>"+Dto.getLanguageSelection()+"</b></td>");
			emailBody.append("</tr><tr><td>User Name</td><td><b>pautointernal</b></td>");
			emailBody.append("</tr><tr><td>Client</td><td><b>PAM Automation Client</b></td>");
			emailBody.append("</tr><tr><td>Date</td><td><b>"+new Date()+"</b></td></tr></table>");
			//Used >1 because if the report having single TC then it will not display in email body.
			//if(allExecutedTCNames.size()>1){
				emailBody.append("<br>Below are the list of TCs executed in the attachments with <b>"+passedNumber+"</b> Passed and <b>"+failedNumber+"</b> Failed.");
				
				String mainTestRemovedPackage[]=null;
				String MainTestToPrint=null;
				  for(String mainTCName:fnalHMKeys){
					  MainTestToPrint=mainTCName;
					  mainTestRemovedPackage=MainTestToPrint.split("\\.");
					  MainTestToPrint=mainTestRemovedPackage[mainTestRemovedPackage.length-1];
					  emailBody.append("<ul>");
					  emailBody.append("<li><b>"+MainTestToPrint+"</b></li>");
			        	  finaltcResults=new LinkedHashMap<String, ArrayList<String>>();
			        	  finaltcResults=finalHM.get(mainTCName);
			        	  tcArrayKeys=finaltcResults.keySet();
			        		 for(String statusVal:tcArrayKeys){
			        			 
			             		tcArray=finaltcResults.get(statusVal);
			             		 for(String subClsName:tcArray){
			             			 emailBody.append("<ul>");
			             			 emailBody.append("<li>");
			             			 //emailBody.append("<b>"+subClsName+"</b>&#9;&#9;&#9;"+statusVal+"</li>");
			             			 if(statusVal.equalsIgnoreCase("Passed"))
		             					 emailBody.append(subClsName+"&#9;&#9;&#9;<font color='#00b050'>"+statusVal+"</font></li>");
			             			 else
		             					emailBody.append(subClsName+"&#9;&#9;&#9;<font color='#a85232'>"+statusVal+"</font></li>");
			             			 emailBody.append("</ul>");
			             		 }
			             		 
			             	}
			        		 emailBody.append("</ul>");
			        }			
				
				
			/*	emailBody.append("<ul>");
				for(String tcName:allExecutedTCNames){
					emailBody.append("<li><b>"+tcName+"</b></li>");
				}
				emailBody.append("</ul>");*/
		//	}
			//}
			emailBody.append("<br>NOTE: Please do not reply to this auto-generated e-mail.");
			emailBody.append("<br><br>Thank You.</body></html>");
			messageBodyPart.setContent(emailBody.toString(), "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			//This is for html report
			addAttachment(messageBodyPart,multipart,filePath,filename);
			
			//This block is used for excel report  com.cte.AutoClient.PAMScheduleReportsEmailValidationTC
			if(!(allExecutedTCNames.contains("miscellaneous.ScheduleReport.IMServ_DailyAllScheduleReport") || allExecutedTCNames.contains("miscellaneous.ScheduleReport.PAM_DailyAllScheduleReport") || allExecutedTCNames.contains("miscellaneous.ScheduleReport.OffLine_DailyAllScheduleReport"))){
				String excelReportName=EmailableReportToExcelPrinting.returnTheExcelFile(allExecutedTCNamesInExcel,allExecutedTCNamesToProcessInExcel);
				//String excelfilePath=excelReportName.split("//")[0]+"/";
				Path path = Paths.get(excelReportName);
		        String excelfilePath=path.getParent().toString()+"/";
		        String excelfile = path.getFileName().toString();
				//String excelfile=excelReportName.split("//")[1];
				addAttachment(messageBodyPart,multipart,excelfilePath,excelfile);
				
				/*This No,All,Smoke values are selected in Test Data.xls. Based on the selecting the extent report will attach in email
				 * If the selected value is no then it won't attach for any execution
				 * If the selected value is All then it will attach for all execution
				 * It the selected value is Smoke then it will attach only for smoke TCs execution
				 * */
				String extentReportAttachemnt= Dto.getExtentReportAttachment();
				if(!"No".equalsIgnoreCase(extentReportAttachemnt)){
					if("All".equalsIgnoreCase(extentReportAttachemnt)){
						addAttachment(messageBodyPart,multipart,filePath,extentReport);
					}else if("Smoke".equalsIgnoreCase(extentReportAttachemnt) && addAttached(allExecutedTCNames)){
						addAttachment(messageBodyPart,multipart,filePath,extentReport);
					}
				}
			}
			
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("sent");
			//This below method is used to sent the excel report to the respective email id
			//if(!allExecutedTCNames.contains("PAMEmailValidationTC"))
				//EmailableReportToExcelPrinting.sendTheExcelAttachMent(filePath+filename);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		} 
	}
	/**
	 * This method will return the executed TC names. It will take file name as parameter.* */
	private static Set<String> getExecutedTCNames(String fileName){
		 Set<String> methodArray = null;
	    try {
	    	methodArray = new LinkedHashSet<String>();
	    	File input = new File(fileName);
	    	Document html = Jsoup.parse(input, "UTF-8");
	        Element table = html.select("table").get(1);
	        Elements passFailTableRows = table.select("tr[class=failedeven],tr[class=passedeven]");
	        Element row;Elements cols;
	        for (int i = 0; i <passFailTableRows.size(); i++){
	            row = passFailTableRows.get(i);
	            cols = row.select("td");
	            if(cols.size()==4){
	            	methodArray.add(cols.get(0).text());
	            }
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }	
	    return methodArray;
	}
	private static void addAttachment(BodyPart messageBodyPart,Multipart multipart,String filePath,String filename)
	{
		try{
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath+filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
		}catch(Exception e){e.printStackTrace();System.out.println("No files to attached");}
	}
	
	private static boolean addAttached(Set<String> allExecutedTCNames){
		boolean flag=false;
		for(String reportNm:allExecutedTCNames){
			if(reportNm.contains("Smoke")){
					flag=true;
					break;
			}
		}
		return flag;
	}
}
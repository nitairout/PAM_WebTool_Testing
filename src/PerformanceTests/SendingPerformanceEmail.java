package PerformanceTests;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import common.TestBase;
import common.ValueDTO;

public class SendingPerformanceEmail extends TestBase {
	
	
	public void sendEmail(String filename) throws IOException{
		String systemName=InetAddress.getLocalHost().getHostAddress();
		String filePath=System.getProperty("user.dir") + "\\Screenshots\\";
		ValueDTO Dto = new ValueDTO();
		
		String hostName="devmailrelay.ses.int";
		String fromAddress="no-reply@ems.schneider-electric.com";
		String envName="";
		if("https://tk4.dev.summitenergy.com".contains(Dto.getURl())) {
			envName="TK4 ";
		}
		else if("https://tk1.dev.summitenergy.com".contains(Dto.getURl())) {
			envName="TK1 ";
		}
		else if("https://core.stg1.resourceadvisor.schneider-electric.com/login.aspx".contains(Dto.getURl())) {
			envName="Stage ";
		}
		else if("https://resourceadvisor.schneider-electric.com".contains(Dto.getURl())) {
			envName="Prod ";
		}
		
		File drFile = new File(System.getProperty("user.dir") + "\\src\\config\\TestData.properties");
		FileInputStream drFileInput = new FileInputStream(drFile);
		Properties drProp = new Properties();
		drProp.load(drFileInput);
		drFileInput.close();
		//String to =drProp.getProperty("Emailids");
		//String to = Dto.getEmail();
		String to = "nannamdevula@ctepl.com";
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
			
			message.setSubject(envName+"| PAM PerformanceTest Results");
					
			//Preparing body for the email
			emailBody.append("<html lang='en'><head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'>");
			emailBody.append("<style>table {border-collapse: collapse;width: 90%;}</style></head>");
			emailBody.append("<body>Hello,<br>&nbsp;&nbsp;Please find the attached Automated test report generated for the below criteria from "+systemName+". <br><br>");
			
			emailBody.append("<table><tr><td>Environment</td><td><b>"+Dto.getURl()+"</b></td>");
			emailBody.append("</tr><tr><td>Browser Name</td><td><b>"+Dto.getWebDriverObj()+"</b></td></tr><tr>");
			emailBody.append("<td>Selected Language</td><td><b>"+Dto.getLanguageSelection()+"</b></td>");
			emailBody.append("</tr><tr><td>User Name</td><td><b>pautouser101</b></td>");
			emailBody.append("</tr><tr><td>Client</td><td><b>"+Dto.getClient()+"</b></td>");
			emailBody.append("</tr><tr><td>Date</td><td><b>"+new Date()+"</b></td></tr></table>");

			emailBody.append("<br>NOTE: Please do not reply to this auto-generated e-mail.");
			emailBody.append("<br><br>Thank You.</body></html>");
			messageBodyPart.setContent(emailBody.toString(), "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			//This is for attached xls report
			addAttachment(messageBodyPart,multipart,filePath,filename);
			
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("sent");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		} 
	}
	private static void addAttachment(BodyPart messageBodyPart,Multipart multipart,String filePath,String filename)
	{
		try{
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath+filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
		}catch(Exception e){
			e.printStackTrace();System.out.println("No files to attached");
			}
	}
	
	
}




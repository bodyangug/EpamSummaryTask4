package ua.nure.aseev.SummaryTask4.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * MailNotification class that can send mail to the client.
 * 
 * 
 */
public class MailNotification {
	private static final Logger LOG = Logger.getLogger(MailNotification.class);

	private String username="forwork869@gmail.com";
	private String password="epamqwerty";
	private Properties props;

	public MailNotification() {
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}


	public boolean send(String subject, String text, String toEmail) {
		boolean result = false;
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);
			result = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return result;
	}
}

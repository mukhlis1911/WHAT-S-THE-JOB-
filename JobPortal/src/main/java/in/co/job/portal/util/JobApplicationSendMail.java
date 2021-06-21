package in.co.job.portal.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import in.co.job.portal.exception.ApplicationException;


public class JobApplicationSendMail {

	static ResourceBundle rb = ResourceBundle.getBundle("in.co.job.portal.bundle.system");

	private static final String SMTP_HOST_NAME = rb.getString("smtp.server");

	private static final String SMTP_PORT = rb.getString("mail.smtp.port");

	private static final String emailFromAddress = rb.getString("send.email.login");

	private static final String emailPassword = rb.getString("send.email.pwd");

	private static Properties props = new Properties();

	static {

		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.ssl.trust", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", true);

	}

	public static void sendMail(EmailMessage emailMessageDTO) throws ApplicationException {

		try {

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(emailFromAddress, emailPassword);
				}
			});

			session.setDebug(true);

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(emailFromAddress));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailMessageDTO.getTo()));
			msg.setSubject(emailMessageDTO.getSubject());

			msg.setContent(emailMessageDTO.getMessage(), "text/html");

			Transport.send(msg);

		} catch (Exception ex) {
			throw new ApplicationException("Email " + ex.getMessage());
		}
	}

}

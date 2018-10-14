package pl.dragdrop.luxmedlogger.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
public class MailSenderSSL {

    private static final String[] emails = new String[]{"reciver@gmail.com"
            , "reeciver2@gmail.com"
    };
    private static final String username = "type-email@gmail.com";
    private static final String password = "type-password";

    public void sendMails(String subject, String content) {
        Session session = getSession();
        try {
            for (String email : emails) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(email));
                message.setSubject(subject);
                message.setText(content);
                Transport.send(message);
            }
        } catch (MessagingException e) {
            log.error(e.toString(), e);
        }
    }

    private Session getSession() {
        return Session.getDefaultInstance(getProperties(),
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private Properties getProperties() {
        return new Properties() {
            {
                put("mail.smtp.host", "smtp.gmail.com");
                put("mail.smtp.socketFactory.port", "465");
                put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                put("mail.smtp.ssl.checkserveridentity", true);
                put("mail.smtp.auth", "true");
                put("mail.smtp.port", "465");
            }
        };
    }
}
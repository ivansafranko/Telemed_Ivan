package com.telemed.tools;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class EmailSender {

    private Session session;
    private String fromEmailAddress;

    public EmailSender() {
        setup();
    }

    public void sendEmail(String toEmail, String subject, String text) {
        try {
            Message message = new MimeMessage(session);

            // Set the sender and recipient email addresses
            message.setFrom(new InternetAddress(fromEmailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // Set the email subject and content
            message.setSubject(subject);
            message.setText(text);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        String username = "espana.dsr@gmail.com";
        String password = "qnzl zesu vphr xcrw";
        this.fromEmailAddress = username;

        // Set the properties for the email server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        this.session = session;
    }
}

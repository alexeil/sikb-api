package com.boschat.sikb.utils;

import com.boschat.sikb.exceptions.TechnicalException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.boschat.sikb.api.ResponseCode.EMAIL_ERROR;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_DEBUG;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_DEFAULT_RECIPIENT;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_HOST;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_LOGIN;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_PASSWORD;
import static com.boschat.sikb.configuration.ApplicationProperties.SMTP_PORT;

public class MailUtils {

    private static final Logger LOGGER = LogManager.getLogger(MailUtils.class);

    public static final String EMAIL_TITLE_CREATE_USER = "Welcome, ready to set your account ?";

    private static MailUtils instance;

    private static Session session;

    private MailUtils() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST.getValue());
        props.put("mail.smtp.port", SMTP_PORT.getValue());
        props.put("mail.smtp.socketFactory.port", SMTP_PORT.getValue());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        session = Session.getInstance(props,
            new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_LOGIN.getValue(), SMTP_PASSWORD.getValue());
                }
            });

        if (SMTP_DEBUG.getBooleanValue()) {
            session.setDebug(SMTP_DEBUG.getBooleanValue());
        }
    }

    public static void reset() {
        instance = null;
    }

    public static MailUtils getInstance() {
        if (instance == null) {
            instance = new MailUtils();
        }
        return instance;
    }

    //Method to read HTML file as a String 
    private static String readContentFromFile(String fileName) throws IOException {
        StringBuilder contents = new StringBuilder();

        //use buffering, reading one line at a time
        try (BufferedReader reader = new BufferedReader(new FileReader(MailUtils.class.getClassLoader().getResource(fileName).getFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        }
        return contents.toString();
    }

    public void sendCreateUserEmail(String email, String token) {
        String link = "test/link?" + token;
        sendEmail("templates/createUser.html", EMAIL_TITLE_CREATE_USER, email, link);
    }

    private void sendEmail(String template, String title, String recipient, String link) {
        LOGGER.trace("Sending an email with template \"{}\", title \"{}\" TO \"{}\" and with link \"{}\"", template, title, recipient, link);

        Map<String, String> input = new HashMap<>();
        input.put("link", link);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_LOGIN.getValue()));

            Address[] addresses;
            if (StringUtils.isNotEmpty(SMTP_DEFAULT_RECIPIENT.getValue())) {
                addresses = InternetAddress.parse(SMTP_DEFAULT_RECIPIENT.getValue());
            } else {
                addresses = InternetAddress.parse(recipient);
            }
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(title);

            //HTML mail content
            MimeMultipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = readEmailFromHtml(template, input);
            messageBodyPart.setContent(htmlText, "text/html");

            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            Transport.send(message);
            LOGGER.trace("email sent !");
        } catch (Exception e) {
            LOGGER.trace("email not sent an error occurred ! ");
            throw new TechnicalException(EMAIL_ERROR, e);
        }
    }

    private String readEmailFromHtml(String filePath, Map<String, String> input) throws IOException {
        String msg = readContentFromFile(filePath);
        Set<Map.Entry<String, String>> entries = input.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
        }
        return msg;
    }
}
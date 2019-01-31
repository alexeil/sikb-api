package com.boschat.sikb.utils;

import com.boschat.sikb.common.exceptions.TechnicalException;
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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_DEBUG;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_DEFAULT_RECIPIENT;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_HOST;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_LOGIN;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_PASSWORD;
import static com.boschat.sikb.common.configuration.ApplicationProperties.SMTP_PORT;
import static com.boschat.sikb.common.configuration.ApplicationProperties.TEMPLATE_CREATE_USER_NAME;
import static com.boschat.sikb.common.configuration.ApplicationProperties.TEMPLATE_CREATE_USER_TITLE;
import static com.boschat.sikb.common.configuration.ApplicationProperties.TEMPLATE_PATH;
import static com.boschat.sikb.common.configuration.ApplicationProperties.TEMPLATE_RESET_USER_PASSWORD_NAME;
import static com.boschat.sikb.common.configuration.ApplicationProperties.TEMPLATE_RESET_USER_PASSWORD_TITLE;
import static com.boschat.sikb.common.configuration.ResponseCode.EMAIL_ERROR;

public class MailUtils {

    private static final Logger LOGGER = LogManager.getLogger(MailUtils.class);

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
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(TEMPLATE_PATH.getValue(), fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        }
        return contents.toString();
    }

    public void sendResetPasswordEmail(String email, String token) {
        Map<String, String> values = new HashMap<>();
        values.put("link", "test/link?" + token);
        sendEmail(TEMPLATE_RESET_USER_PASSWORD_NAME.getValue(), TEMPLATE_RESET_USER_PASSWORD_TITLE.getValue(), email, values);
    }

    public void sendCreateUserEmail(String email, String token) {
        Map<String, String> values = new HashMap<>();
        values.put("link", "test/link?" + token);
        sendEmail(TEMPLATE_CREATE_USER_NAME.getValue(), TEMPLATE_CREATE_USER_TITLE.getValue(), email, values);
    }

    private void sendEmail(String template, String title, String recipient, Map<String, String> values) {
        LOGGER.trace("Sending an email with template \"{}\", title \"{}\" TO \"{}\" and with link \"{}\"", template, title, recipient,
            values.entrySet().stream().map(s -> s.getKey() + " " + s.getValue()).collect(Collectors.joining()));
        
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
            String htmlText = readEmailFromHtml(template, values);
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
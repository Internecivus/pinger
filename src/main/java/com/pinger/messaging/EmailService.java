package com.pinger.messaging;

import com.pinger.model.SiteStatusResults;
import com.pinger.template.Template;
import com.pinger.template.TemplateService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;

public class EmailService {

    private static final Properties EMAIL_SETTINGS = new Properties();

    private static final String CHARSET_DEFAULT = "UTF-8";
    private static final String MIME_HTML = "html";

    public final static String USERNAME = SettingsProvider.getInstance().getString("email.authentication.username");
    public final static String PASSWORD = SettingsProvider.getInstance().getString("email.authentication.password");
    public final static String SMTP_HOST = SettingsProvider.getInstance().getString("email.connection.smtp.host");
    public final static Integer SMPT_PORT = SettingsProvider.getInstance().getInteger("email.connection.smtp.port");

    public final static String EMAIL_SITE_STATUS_TO = SettingsProvider.getInstance().getString("email.site_status.to");

    static {
        EMAIL_SETTINGS.put("mail.smtp.auth", true);
        EMAIL_SETTINGS.put("mail.smtp.starttls.enable", "true");
        EMAIL_SETTINGS.put("mail.smtp.host", SMTP_HOST);
        EMAIL_SETTINGS.put("mail.smtp.port", SMPT_PORT);
        EMAIL_SETTINGS.put("mail.smtp.ssl.trust", SMTP_HOST);
    }

    private static void sendMail(final String to, final String subject, final Object content) {
        try {
            final Session session = Session.getInstance(EMAIL_SETTINGS, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });
            final Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText(content.toString(), CHARSET_DEFAULT, MIME_HTML);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            MessageFormat.format(LabelsProvider.getInstance().getMessage("email_sent_success"), to);
        }
        catch (final MessagingException e) {
            MessageFormat.format(LabelsProvider.getInstance().getMessage("email_sent_failure"), to);
            throw new RuntimeException(e);
        }
    }

    public static void notifySitesStatus(final SiteStatusResults siteStatusResults) {
        final String subjectRaw = LabelsProvider.getInstance().getString("site_status.subject");
        final String subject = MessageFormat.format(
                subjectRaw,
                siteStatusResults.getAllDownLinks().size(),
                SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(siteStatusResults.getTimestamp()));

        final HashMap<String, Object> model = new HashMap<>();
        model.put("results", siteStatusResults);
        final String content = TemplateService.process(Template.SITE_STATUS_MAIL, model);

        sendMail(EMAIL_SITE_STATUS_TO, subject, content);
    }

    public static void notifyError(final Exception exception) {
        final String subject = LabelsProvider.getInstance().getString("error.subject");

        final HashMap<String, Object> model = new HashMap<>();
        model.put("exception", exception);
        final String content = TemplateService.process(Template.ERROR_MAIL, model);

        sendMail(EMAIL_SITE_STATUS_TO, subject, content);
    }

}

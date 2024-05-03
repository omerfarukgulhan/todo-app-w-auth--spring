package com.todo.email;

import java.util.Properties;

import com.todo.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private JavaMailSenderImpl mailSender;

    private final AppProperties appProperties;

    private final MessageSource messageSource;

    private final String activationEmail = """
            <html>
                <body>
                    <h1>${title}</h1>
                    <a href="${url}">${clickHere}</a>
                </body>
            </html>
            """;

    @Autowired
    public EmailService(AppProperties appProperties, MessageSource messageSource) {
        this.appProperties = appProperties;
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void initialize() {
        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(appProperties.getEmail().host());
        mailSender.setPort(appProperties.getEmail().port());
        mailSender.setUsername(appProperties.getEmail().username());
        mailSender.setPassword(appProperties.getEmail().password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");
    }

    public void sendActivationEmail(String email, String activationToken) {
        var activationUrl = appProperties.getClient().host()+"/api/v1/users/"  + activationToken+ "/activate";
        var title = "Activate user";
        var clickHere = "Click here";

        var mailBody = activationEmail
                .replace("${url}", activationUrl)
                .replace("${title}", title)
                .replace("${clickHere}", clickHere);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            message.setFrom(appProperties.getEmail().from());
            message.setTo(email);
            message.setSubject(title);
            message.setText(mailBody, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }
}

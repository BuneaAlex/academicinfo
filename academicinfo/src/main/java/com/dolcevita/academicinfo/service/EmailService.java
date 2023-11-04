package com.dolcevita.academicinfo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("confirmation.abc.zzz@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Async
    public void sendHtmlEmail(String to, String name, String subject, String body, String link, String buttonMessage, String end) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("subject", subject);
            context.setVariable("body", body);
            context.setVariable("link", link);
            context.setVariable("buttonMessage", buttonMessage);
            context.setVariable("end", end);

            String emailContent = templateEngine.process("email-template", context);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException ignored) {
        }
    }
}

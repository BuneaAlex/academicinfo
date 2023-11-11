package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.ExamDto;
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
    public void sendTextEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("confirmation.abc.zzz@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Async
    public void sendConfirmationEmail(String to, String name, String subject, String body, String link, String buttonMessage, String end) {
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

    @Async
    public void sendExamNotification(String to, ExamDto examDto) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());


            Context context = new Context();
            context.setVariable("examDto", examDto);

            String emailContent = templateEngine.process("exam-template", context);

            helper.setTo(to);
            helper.setSubject("Exam notification");
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException ignored) {
        }
    }

    @Async
    public void sendExamReminder(String to, ExamDto examDto) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());


            Context context = new Context();
            context.setVariable("examDto", examDto);

            String emailContent = templateEngine.process("reminder-template", context);

            helper.setTo(to);
            helper.setSubject("Exam notification");
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException ignored) {
        }
    }
}

package com.earth_chat.common.sender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * 템플릿 기반 이메일 발송 처리.
     * @param templatePath 템플릿 경로
     * @param context Context
     * @param email 이메일 주소
     * @param subject 메일 제목
     */
    public void sendMail(String templatePath, Context context, String email, String subject) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            String html = templateEngine.process(templatePath, context);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("메일 발송 도중 MessagingException 발생 : ", e);
        } catch (Exception e) {
            log.error("메일 발송 도중 예외 발생 : ", e);
        }
    }
}

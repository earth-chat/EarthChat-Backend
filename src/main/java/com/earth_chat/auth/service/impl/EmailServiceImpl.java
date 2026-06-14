package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.service.EmailService;
import com.earth_chat.auth.vo.EmailAuthInfoVo;
import com.earth_chat.common.exception.FailSendingMailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * {@inheritDoc}
     */
    @Async("taskExecutor")
    @Override
    public void sendAuthCodeMail(EmailAuthInfoVo emailAuthInfoVo) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            Context context = new Context();
            context.setVariable("authCode", emailAuthInfoVo.getAuthNum());
            String html = templateEngine.process("email/AuthCodeEmail", context);

            helper.setTo(emailAuthInfoVo.getEmail());
            helper.setSubject("EarthChat - 회원가입 인증번호를 확인해주세요");
            helper.setText(html, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("메일 발송 도중 MessagingException 발생 : ", e);
        } catch (Exception e) {
            log.error("메일 발송 도중 예외 발생 : ", e);
        }
    }
}

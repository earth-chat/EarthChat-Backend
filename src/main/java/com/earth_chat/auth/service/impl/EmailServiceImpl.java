package com.earth_chat.auth.service.impl;

import com.earth_chat.auth.service.EmailService;
import com.earth_chat.auth.vo.EmailAuthInfoVo;
import com.earth_chat.auth.vo.PasswordFindKeyVo;
import com.earth_chat.common.sender.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailSender emailSender;

    @Value("${find-password-page.url:#{null}}")
    private String findPasswordUrl;

    /**
     * {@inheritDoc}
     */
    @Async("registerEmailTaskExecutor")
    @Override
    public void sendAuthCodeMail(EmailAuthInfoVo emailAuthInfoVo) {
        Context context = new Context();
        context.setVariable("authCode", emailAuthInfoVo.getAuthNum());

        String templatePath = "email/AuthCodeEmail";
        String subject = "EarthChat - 회원가입을 위해 본인 인증을 완료해주세요";

        emailSender.sendMail(templatePath, context, emailAuthInfoVo.getEmail(), subject);
    }

    /**
     * {@inheritDoc}
     */
    @Async("findPasswordEmailTaskExecutor")
    @Override
    public void sendPasswordFindKeyMail(PasswordFindKeyVo passwordFindKeyVo) {
        Context context = new Context();
        String pageUrl = findPasswordUrl + "?key=" + passwordFindKeyVo.getKeyValue();
        log.debug("pageUrl: {}", pageUrl);

        context.setVariable("findPasswordUrl", pageUrl);

        String templatePath = "email/PasswordFindEmail";
        String subject = "EarthChat - 비밀번호를 재설정하세요";

        emailSender.sendMail(templatePath, context, passwordFindKeyVo.getEmail(), subject);
    }
}

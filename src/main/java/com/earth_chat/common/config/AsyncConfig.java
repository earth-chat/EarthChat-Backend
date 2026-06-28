package com.earth_chat.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 인증 코드 메일 발송 비동기 처리를 위한 Executor Bean 등록.
     * @return Executor
     */
    @Bean("registerEmailTaskExecutor")
    public Executor registerEmailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Register-Email-Async-");
        executor.initialize();

        return executor;
    }

    /**
     * 비밀번호 메일 발송 비동기 처리를 위한 Executor Bean 등록.
     * @return Executor
     */
    @Bean("findPasswordEmailTaskExecutor")
    public Executor findPasswordEmailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Find-Password-Async-");
        executor.initialize();

        return executor;
    }
}

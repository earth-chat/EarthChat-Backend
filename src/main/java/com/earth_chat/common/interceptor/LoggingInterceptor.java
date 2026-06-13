package com.earth_chat.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "START_TIME";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        log.debug("""
                
                ┌─────────────────────────────────────────────────────────────
                │ REQUEST START
                ├─────────────────────────────────────────────────────────────
                │ Method           : {}
                │ URI              : {}
                │ Query            : {}
                │ Client IP        : {}
                │ Accept-Language  : {}
                │ Time             : {}
                └─────────────────────────────────────────────────────────────
                """,
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                request.getRemoteAddr(),
                request.getHeader("Accept-Language"),
                LocalDateTime.now()
        );

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Long startTime = (Long) request.getAttribute(START_TIME);

        long elapsedTime = startTime == null
                ? 0
                : System.currentTimeMillis() - startTime;

        if (ex == null) {

            log.debug("""
                    
                    ┌─────────────────────────────────────────────────────────────
                    │ REQUEST END
                    ├─────────────────────────────────────────────────────────────
                    │ Status      : {}
                    │ Method      : {}
                    │ URI         : {}
                    │ Elapsed(ms) : {}
                    └─────────────────────────────────────────────────────────────
                    """,
                    response.getStatus(),
                    request.getMethod(),
                    request.getRequestURI(),
                    elapsedTime
            );

        } else {

            log.debug("""
                    
                    ┌─────────────────────────────────────────────────────────────
                    │ REQUEST ERROR
                    ├─────────────────────────────────────────────────────────────
                    │ Status      : {}
                    │ Method      : {}
                    │ URI         : {}
                    │ Elapsed(ms) : {}
                    │ Exception   : {}
                    │ Message     : {}
                    └─────────────────────────────────────────────────────────────
                    """,
                    response.getStatus(),
                    request.getMethod(),
                    request.getRequestURI(),
                    elapsedTime,
                    ex.getClass().getSimpleName(),
                    ex.getMessage(),
                    ex
            );
        }

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

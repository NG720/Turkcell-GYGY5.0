package com.library.pipeline;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Order(2)
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log          = LoggerFactory.getLogger(LoggingFilter.class);
    private static final int    MAX_BODY     = 500;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        var wrappedReq = new ContentCachingRequestWrapper(req);
        var wrappedRes = new ContentCachingResponseWrapper(res);
        try {
            logRequest(wrappedReq);
            chain.doFilter(wrappedReq, wrappedRes);
        } finally {
            logResponse(wrappedRes);
            wrappedRes.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper req) {
        String headers = Collections.list(req.getHeaderNames()).stream()
                .map(n -> n + ": " + (n.equalsIgnoreCase("authorization") ? "Bearer ***" : req.getHeader(n)))
                .collect(Collectors.joining(" | "));
        log.info("► REQUEST  {} {}{} | {}",
                req.getMethod(), req.getRequestURI(),
                req.getQueryString() != null ? "?" + req.getQueryString() : "",
                headers);
    }

    private void logResponse(ContentCachingResponseWrapper res) {
        byte[] body = res.getContentAsByteArray();
        String bodyStr = body.length > 0 ? new String(body, StandardCharsets.UTF_8) : "(boş)";
        log.info("◄ RESPONSE {} | {}", res.getStatus(), truncate(bodyStr));
    }

    private String truncate(String s) {
        return s.length() > MAX_BODY ? s.substring(0, MAX_BODY) + "...[+" + (s.length() - MAX_BODY) + "]" : s;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return req.getRequestURI().startsWith("/h2-console");
    }
}

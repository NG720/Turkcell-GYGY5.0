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

import java.io.IOException;

@Component
@Order(1)
public class PerformanceMonitoringFilter extends OncePerRequestFilter {

    private static final Logger log              = LoggerFactory.getLogger(PerformanceMonitoringFilter.class);
    private static final long   THRESHOLD_MS     = 3_000;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        try {
            chain.doFilter(req, res);
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed > THRESHOLD_MS) {
                log.warn("⚠ YAVAŞ İSTEK! {} {} → {} | {} ms (eşik: {} ms)",
                        req.getMethod(), req.getRequestURI(), res.getStatus(), elapsed, THRESHOLD_MS);
            } else {
                log.info("✔ {} {} → {} | {} ms",
                        req.getMethod(), req.getRequestURI(), res.getStatus(), elapsed);
            }
        }
    }
}

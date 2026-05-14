package com.library.pipeline;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class TransactionBehavior {

    private static final Logger log = LoggerFactory.getLogger(TransactionBehavior.class);

    @Pointcut("execution(public * com.library.service.*.*(..))")
    public void serviceLayer() {}

    @Around("serviceLayer()")
    @Transactional(rollbackFor = Exception.class)
    public Object manage(ProceedingJoinPoint jp) throws Throwable {
        String name = jp.getTarget().getClass().getSimpleName() + "." + jp.getSignature().getName() + "()";
        log.debug("⟳ TX BAŞLADI  → {}", name);
        try {
            Object result = jp.proceed();
            log.debug("✔ TX COMMIT   → {}", name);
            return result;
        } catch (Exception ex) {
            log.error("✘ TX ROLLBACK → {} | {} – {}", name, ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }
}

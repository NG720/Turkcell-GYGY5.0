package com.library.cqrs.core.pipeline;

import com.library.cqrs.core.mediator.pipeline.PipelineBehavior;
import com.library.cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Pipeline 2 — Performance Monitoring
 *
 * Her Command/Query'nin handler süresini ölçer.
 * 3000ms'i aşarsa konsola WARN düşer.
 *
 * Konsol çıktısı:
 *   [PERF] GetAllKitapQuery tamamlandı: 45 ms
 *   [PERF][WARN] CreateOduncAlmaCommand YAVAŞ: 3521 ms (eşik: 3000 ms)
 */
@Component
@Order(20)
public class PerformanceBehavior implements PipelineBehavior {

    private static final long THRESHOLD_MS = 3000L;

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        String requestName = request.getClass().getSimpleName();
        long start = System.currentTimeMillis();

        try {
            return next.invoke();
        } finally {
            long elapsed = System.currentTimeMillis() - start;

            if (elapsed > THRESHOLD_MS) {
                System.out.println("[PERF][WARN] " + requestName
                        + " YAVAŞ: " + elapsed + " ms (eşik: " + THRESHOLD_MS + " ms)");
            } else {
                System.out.println("[PERF] " + requestName + " tamamlandı: " + elapsed + " ms");
            }
        }
    }
}

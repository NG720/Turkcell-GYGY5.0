package com.library.cqrs.core.pipeline;

import com.library.cqrs.core.mediator.pipeline.PipelineBehavior;
import com.library.cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Pipeline 1 — Logging
 *
 * Her Command/Query için gelen istek ve dönen cevabı ayrı ayrı loglar.
 *
 * Konsol çıktısı:
 *   [LOG][REQUEST]  CreateKategoriCommand -> CreateKategoriCommand(ad=Roman, aciklama=...)
 *   [LOG][RESPONSE] CreateKategoriCommand -> Kategori(id=1, ad=Roman)
 */
@Component
@Order(10)
public class LoggingBehavior implements PipelineBehavior {

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        String requestName = request.getClass().getSimpleName();

        System.out.println("[LOG][REQUEST]  " + requestName + " -> " + request);

        R response = next.invoke();

        System.out.println("[LOG][RESPONSE] " + requestName + " -> " + response);

        return response;
    }
}

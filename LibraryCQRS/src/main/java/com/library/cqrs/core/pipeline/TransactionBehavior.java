package com.library.cqrs.core.pipeline;

import com.library.cqrs.core.mediator.cqrs.Command;
import com.library.cqrs.core.mediator.pipeline.PipelineBehavior;
import com.library.cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Pipeline 3 — Transaction (Bütünlük)
 *
 * Bir Command birden fazla DB değişikliği yapabilir.
 * Hepsi başarılıysa → COMMIT, herhangi biri hata fırlatırsa → ROLLBACK.
 * "Ya hep ya hiç" — ACID'in Atomicity kuralı.
 *
 * Sadece Command'lere uygulanır; Query'ler okuma yaptığı için transaction gerekmez.
 *
 * Konsol çıktısı:
 *   [TX] BEGIN   CreateKategoriCommand
 *   [TX] COMMIT  CreateKategoriCommand
 *
 *   [TX] BEGIN    CreateOduncAlmaCommand
 *   [TX] ROLLBACK CreateOduncAlmaCommand -> Kitap kopyası yok
 */
@Component
@Order(30)
public class TransactionBehavior implements PipelineBehavior {

    private final TransactionTemplate transactionTemplate;

    public TransactionBehavior(PlatformTransactionManager transactionManager) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    // Sadece Command'leri sarar, Query'lere dokunmaz
    @Override
    public boolean supports(Object request) {
        return request instanceof Command<?>;
    }

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        String requestName = request.getClass().getSimpleName();
        System.out.println("[TX] BEGIN   " + requestName);

        try {
            R result = transactionTemplate.execute(status -> next.invoke());
            System.out.println("[TX] COMMIT  " + requestName);
            return result;
        } catch (RuntimeException ex) {
            System.out.println("[TX] ROLLBACK " + requestName + " -> " + ex.getMessage());
            throw ex;
        }
    }
}

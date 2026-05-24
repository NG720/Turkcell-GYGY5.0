package com.library.cqrs.core.security.authorization;

import com.library.cqrs.core.mediator.pipeline.PipelineBehavior;
import com.library.cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import com.library.cqrs.core.security.context.UserContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Pipeline Behavior 4 — Authorization
 *
 * Her request için:
 * 1. UserContext boşsa (token yok/geçersiz) → AuthenticatedException (401)
 * 2. Request AuthorizableRequest ise ve rol yetersizse → AuthorizationException (403)
 * 3. Her şey tamam → devam
 *
 * Logging ve Performance'dan önce çalışır (@Order(5)).
 */
@Component
@Order(5)
public class AuthorizationBehavior implements PipelineBehavior {

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        UserContext ctx = UserContext.get();

        // 1. Kimlik doğrulama — token yoksa 401
        if (ctx == null) {
            throw new AuthenticatedException("Bu işlem için giriş yapmanız gerekiyor.");
        }

        // 2. Yetki kontrolü — rol listesi doluysa kontrol et
        if (request instanceof AuthorizableRequest authReq) {
            var required = authReq.getRequiredRoles();
            if (required != null && !required.isEmpty() && !ctx.hasAnyRole(required)) {
                throw new AuthorizationException(
                    "Bu işlem için yetkiniz yok. Gerekli roller: " + required);
            }
        }

        return next.invoke();
    }
}

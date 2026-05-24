package com.library.cqrs.core.mediator;

import com.library.cqrs.core.mediator.cqrs.Command;
import com.library.cqrs.core.mediator.cqrs.CommandHandler;
import com.library.cqrs.core.mediator.cqrs.Query;
import com.library.cqrs.core.mediator.cqrs.QueryHandler;
import com.library.cqrs.core.mediator.pipeline.PipelineBehavior;
import com.library.cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpringMediator implements Mediator {

    private final List<PipelineBehavior> behaviors;
    private final Map<Class<?>, CommandHandler<?, ?>> commandHandlers = new HashMap<>();
    private final Map<Class<?>, QueryHandler<?, ?>> queryHandlers = new HashMap<>();
    private final ApplicationContext context;

    public SpringMediator(ApplicationContext context, List<PipelineBehavior> behaviors) {
        this.context = context;
        this.behaviors = behaviors.stream()
                .sorted(AnnotationAwareOrderComparator.INSTANCE)
                .toList();
    }

    @PostConstruct
    @SuppressWarnings("rawtypes")
    private void registerHandlers() {
        context.getBeansOfType(CommandHandler.class).values().forEach(handler -> {
            Class<?> requestType = resolveRequestType(handler.getClass(), CommandHandler.class);
            commandHandlers.putIfAbsent(requestType, handler);
        });

        context.getBeansOfType(QueryHandler.class).values().forEach(handler -> {
            Class<?> requestType = resolveRequestType(handler.getClass(), QueryHandler.class);
            queryHandlers.putIfAbsent(requestType, handler);
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R send(Command<R> command) {
        CommandHandler<Command<R>, R> handler =
                (CommandHandler<Command<R>, R>) commandHandlers.get(command.getClass());

        if (handler == null)
            throw new IllegalStateException("Handler bulunamadı: " + command.getClass().getSimpleName());

        return invokePipeline(command, () -> handler.handle(command));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R send(Query<R> query) {
        QueryHandler<Query<R>, R> handler =
                (QueryHandler<Query<R>, R>) queryHandlers.get(query.getClass());

        if (handler == null)
            throw new IllegalStateException("Handler bulunamadı: " + query.getClass().getSimpleName());

        return invokePipeline(query, () -> handler.handle(query));
    }

    private Class<?> resolveRequestType(Class<?> handlerClass, Class<?> handlerInterface) {
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, handlerInterface);
        if (generics == null || generics.length == 0)
            throw new IllegalStateException("Generic tip çözümlenemedi: " + handlerClass.getName());
        return generics[0];
    }

    // Pipeline zincirini tersten kurar: Logging → Performance → Transaction → Handler
    private <R> R invokePipeline(Object request, RequestHandlerDelegate<R> handlerInvocation) {
        RequestHandlerDelegate<R> next = handlerInvocation;

        for (int i = behaviors.size() - 1; i >= 0; i--) {
            PipelineBehavior behavior = behaviors.get(i);
            if (!behavior.supports(request)) continue;

            RequestHandlerDelegate<R> current = next;
            next = () -> behavior.handle(request, current);
        }

        return next.invoke();
    }
}

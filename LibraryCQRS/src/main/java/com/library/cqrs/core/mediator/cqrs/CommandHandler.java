package com.library.cqrs.core.mediator.cqrs;

public interface CommandHandler<C extends Command<R>, R> {
    R handle(C command);
}

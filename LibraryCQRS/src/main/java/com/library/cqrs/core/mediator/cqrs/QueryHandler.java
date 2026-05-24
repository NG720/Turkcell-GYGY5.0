package com.library.cqrs.core.mediator.cqrs;

public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}

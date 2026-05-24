package com.library.cqrs.core.mediator;

import com.library.cqrs.core.mediator.cqrs.Command;
import com.library.cqrs.core.mediator.cqrs.Query;

public interface Mediator {
    <R> R send(Command<R> command);
    <R> R send(Query<R> query);
}

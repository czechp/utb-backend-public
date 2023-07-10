package app.web.utb.domainDrivenDesign.interfaces;

import app.web.utb.domainDrivenDesign.event.DomainEvent;

import java.util.List;

public interface Aggregate<T> {
    List<DomainEvent> getDomainEvents();

    void flushEvents();

    T getSnapshot();
}

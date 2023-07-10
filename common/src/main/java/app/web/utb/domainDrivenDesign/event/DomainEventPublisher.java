package app.web.utb.domainDrivenDesign.event;

import app.web.utb.domainDrivenDesign.interfaces.Aggregate;
import app.web.utb.domainDrivenDesign.superClassEntity.AggregateRoot;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DomainEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(DomainEvent domainEvent) {
        this.publisher.publishEvent(domainEvent);
    }

    public void publish(AggregateRoot aggregateRoot) {
        aggregateRoot.getDomainEvents()
                .forEach(publisher::publishEvent);
        aggregateRoot.flushEvents();
    }

    public <T> void publish(Aggregate<T> aggregate) {
        aggregate.getDomainEvents().forEach(publisher::publishEvent);
        aggregate.flushEvents();
    }

}

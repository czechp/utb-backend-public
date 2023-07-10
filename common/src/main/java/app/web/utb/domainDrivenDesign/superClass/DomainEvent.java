package app.web.utb.domainDrivenDesign.superClass;

import java.time.LocalDateTime;

public abstract class DomainEvent {
    private final LocalDateTime createdAt;

    protected DomainEvent() {
        this.createdAt = LocalDateTime.now();
    }
}

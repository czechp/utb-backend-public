package app.web.utb.domainDrivenDesign.superClassEntity;

import app.web.utb.domainDrivenDesign.event.DomainEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@MappedSuperclass
@Getter(AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
abstract public class AggregateRoot {
    @Transient
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    @Version
    private long version;
    private final String uuid;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;

    protected AggregateRoot() {
        this.id = 0L;
        this.version = 0L;
        this.uuid = UUID.randomUUID().toString();
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(this.domainEvents);
    }

    public void flushEvents() {
        this.domainEvents.clear();
    }

    protected void addDomainEvent(DomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }

    protected void increaseVersion() {
        this.version++;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateRoot that = (AggregateRoot) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

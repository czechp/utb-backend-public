package app.web.utb.domainDrivenDesign.superClass;

import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter(AccessLevel.PUBLIC)
public abstract class AggregateRoot<T> {
    private final long id;
    private final long version;
    private final String uuid;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public AggregateRoot() {
        this.id = 0L;
        this.version = 0L;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.uuid = UUID.randomUUID().toString();
    }

    protected AggregateRoot(long id, long version, String uuid, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.uuid = uuid;
    }

    public abstract T getSnapshot();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateRoot<?> that = (AggregateRoot<?>) o;
        return id == that.id && uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid);
    }
}

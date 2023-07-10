package app.web.utb.notifiaction.domain;

import app.web.utb.domainDrivenDesign.superClassEntity.AggregateRoot;
import app.web.utb.notifiaction.domain.event.NotificationAddressCreated;
import app.web.utb.notifiaction.domain.event.NotificationAddressRemoved;
import app.web.utb.notifiaction.infrastructure.persistence.NotificationAddressTableName;
import app.web.utb.validator.CommonValidators;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = NotificationAddressTableName.TABLE_NAME)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationAddress extends AggregateRoot {
    private long chargingSystemId;
    @Email
    private String email;

    NotificationAddress(long chargingSystemId, String email) {
        this.chargingSystemId = chargingSystemId;
        this.email = email;
        validate();
        super.addDomainEvent(new NotificationAddressCreated(super.getUuid(), chargingSystemId, email));
    }

    private void validate() {
        CommonValidators.validateEmail(this.email);
    }

    public void remove() {
        super.addDomainEvent(new NotificationAddressRemoved(super.getId()));
    }

    public NotificationAddressSnapshot getSnapshot() {
        return new NotificationAddressSnapshot(this.email);
    }
}

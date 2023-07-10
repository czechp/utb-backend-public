package app.web.utb.alarm.domain;

import app.web.utb.alarm.domain.event.AlarmConfirmed;
import app.web.utb.alarm.domain.event.AlarmCreated;
import app.web.utb.alarm.domain.event.AlarmDescriptionAdded;
import app.web.utb.alarm.infrastructure.persistence.AlarmTableName;
import app.web.utb.domainDrivenDesign.superClassEntity.AggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = AlarmTableName.TABLE_NAME)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Alarm extends AggregateRoot {
    private long chargingSystemId;
    private int chargerPosition;
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;
    private boolean confirmed;
    private String description;

    Alarm(long chargingSystemId, int chargerPosition, AlarmType alarmType) {
        this.confirmed = false;
        this.chargingSystemId = chargingSystemId;
        this.chargerPosition = chargerPosition;
        this.alarmType = alarmType;
        super.addDomainEvent(new AlarmCreated(super.getUuid(), chargingSystemId, chargerPosition, alarmType));
    }

    public void confirm() {
        this.confirmed = true;
        super.addDomainEvent(new AlarmConfirmed(super.getId(), this.chargingSystemId, this.chargerPosition, this.alarmType));
    }

    public void addDescription(String description) {
        this.description = description;
        super.addDomainEvent(new AlarmDescriptionAdded(super.getId(), this.description));
    }

    public AlarmSnapshot getSnapshot() {
        return new AlarmSnapshot(this.chargingSystemId, this.chargerPosition);
    }

}

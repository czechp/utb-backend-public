package app.web.utb.alarm.view;

import app.web.utb.alarm.domain.AlarmType;
import app.web.utb.alarm.infrastructure.persistence.AlarmTableName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = AlarmTableName.TABLE_NAME)
@NoArgsConstructor
@Getter
class AlarmView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime createdAt;
    private long chargingSystemId;
    private int chargerPosition;
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;
    private boolean confirmed;
    private String description;
}

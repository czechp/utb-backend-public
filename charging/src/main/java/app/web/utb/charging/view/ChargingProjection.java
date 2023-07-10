package app.web.utb.charging.view;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
class ChargingProjection {
    private final long id;
    private final String chargingSystemName;
    private final int chargerPosition;
    private final String umupNumber;
    private final LocalDateTime createdAt;
    private final LocalDateTime finishedAt;
    private final boolean failed;
    private final long duration;
    private final boolean charging;

    ChargingProjection(long id, String chargingSystemName, int chargerPosition, String umupNumber, LocalDateTime createdAt, LocalDateTime finishedAt, boolean failed) {
        this.id = id;
        this.chargingSystemName = chargingSystemName;
        this.chargerPosition = chargerPosition;
        this.umupNumber = umupNumber;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.failed = failed;
        this.duration = Optional.ofNullable(this.finishedAt)
                .map(finish -> Duration.between(createdAt, finish).toMinutes())
                .orElse(Duration.between(createdAt, LocalDateTime.now()).toMinutes());
        this.charging = this.finishedAt == null;
    }
}

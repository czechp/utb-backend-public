package app.web.utb.charging.view;

import app.web.utb.charging.infrastructure.persistence.ChargingTableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = ChargingTableName.TABLE_NAME)
@Getter
class ChargingView {
    @Id
    private long id;
    private long chargingSystemId;
    private int chargerPosition;
    private long cartId;
    private boolean failed;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

}

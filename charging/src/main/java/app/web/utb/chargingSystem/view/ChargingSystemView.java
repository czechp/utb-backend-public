package app.web.utb.chargingSystem.view;

import app.web.utb.chargingSystem.infrastructure.persistence.ChargingSystemTableName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = ChargingSystemTableName.CHARGING_SYSTEM_TABLE_NAME)
@NoArgsConstructor
@Getter
@Immutable
class ChargingSystemView {

    @Id
    private long id;
    private String name;
    private String address;
    private String networkMask;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "charging_system_id")
    @OrderBy("position ASC")
    private final Set<ChargerView> chargers = new HashSet<>();
    @Transient
    private boolean error;

    @PostLoad
    void afterLoad() {
        this.error = chargers.stream().anyMatch(chargerView -> chargerView.isChargingInterruptionError() || chargerView.isCartNotMatchError());
    }
}

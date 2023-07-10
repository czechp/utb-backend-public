package app.web.utb.chargingSystem.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChargingSystemRemoveChargerCmd {
    private final long chargingSystemId;
    private final int chargerPosition;
}

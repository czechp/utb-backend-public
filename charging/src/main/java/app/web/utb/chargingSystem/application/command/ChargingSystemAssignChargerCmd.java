package app.web.utb.chargingSystem.application.command;

import lombok.Data;

@Data
public class ChargingSystemAssignChargerCmd {
    private long chargingSystemId;
    private int chargerPosition;
}

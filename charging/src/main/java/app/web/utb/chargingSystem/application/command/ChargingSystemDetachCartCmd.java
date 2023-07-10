package app.web.utb.chargingSystem.application.command;

import lombok.Data;

@Data
public class ChargingSystemDetachCartCmd {
    private long systemId;
    private int chargerPosition;
    private long cartId;
}

package app.web.utb.chargingSystem.application.command;

import lombok.Value;

@Value
public class ChargingSystemCartRemovedCmd {
    long cartId;
}

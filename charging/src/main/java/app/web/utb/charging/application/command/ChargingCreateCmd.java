package app.web.utb.charging.application.command;

import lombok.Value;

@Value
public class ChargingCreateCmd {
    long systemId;
    int chargerPosition;
    long cartId;
}

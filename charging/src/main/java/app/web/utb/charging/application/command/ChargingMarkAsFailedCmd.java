package app.web.utb.charging.application.command;

import lombok.Value;

@Value
public class ChargingMarkAsFailedCmd {
    long cartId;
}

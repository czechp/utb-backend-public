package app.web.utb.chargingSystem.domain;

import lombok.Value;

@Value
public class ChargerDataFromController {
    long systemId;
    int chargerPosition;
    long cartId;
    float current;
    boolean charging;
    boolean chargingInterrupted;
}

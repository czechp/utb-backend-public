package app.web.utb.chargingSystem.infrastructure.plc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class PlcChargerData {
    public boolean charging;
    public float current;
    public boolean chargingError;
    public long cartId;
}

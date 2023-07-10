package app.web.utb.chargingSystem.domain;

public class ChargingSystemTestFactory {
    public static ChargingSystem createWithChargers() {
        ChargingSystem chargingSystem
                = ChargingSystemFactory.create("Example name", "192.168.0.1", "255.255.255.0");
        chargingSystem.assignCharger(1);
        chargingSystem.assignCharger(2);
        chargingSystem.assignCharger(3);
        return chargingSystem;
    }
}

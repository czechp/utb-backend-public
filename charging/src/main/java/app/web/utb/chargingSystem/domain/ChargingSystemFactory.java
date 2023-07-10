package app.web.utb.chargingSystem.domain;

public class ChargingSystemFactory {
    public static ChargingSystem create(String name, String plcAddress, String networkMask) {
        PlcAddress address = new PlcAddress(plcAddress, networkMask);
        return new ChargingSystemImpl(name, address);
    }
}

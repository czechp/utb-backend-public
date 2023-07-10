package app.web.utb.charging.domain;

public class ChargingFactory {
    public static Charging create(long systemId, int chargerPosition, long cartId) {
        return new Charging(systemId, chargerPosition, cartId);
    }
}

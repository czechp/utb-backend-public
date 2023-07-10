package app.web.utb.notifiaction.domain;

public class NotificationAddressFactory {
    public static NotificationAddress of(long chargingSystemId, String email) {
        return new NotificationAddress(chargingSystemId, email);
    }
}

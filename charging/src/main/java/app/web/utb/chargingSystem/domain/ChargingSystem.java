package app.web.utb.chargingSystem.domain;

import app.web.utb.alarm.domain.AlarmType;
import app.web.utb.domainDrivenDesign.interfaces.Aggregate;

import java.util.List;

public interface ChargingSystem extends Aggregate<ChargingSystemSnapshot> {
    void assignCharger(int chargerPosition);

    void assignCart(int chargerPosition, long cartId);

    void detachCart(int chargerPosition, long cartId);

    void updateData(List<ChargerDataFromController> dataFromController);

    void removeCharger(int chargerPosition);

    void cartRemoved(long cartId);

    void remove();

    void confirmError(int chargerPosition, AlarmType alarmType);
}

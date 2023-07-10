package app.web.utb.alarm.application.service;

public interface UtbPlcErrorHandler {
    void writeError(long chargingSystemId, int chargerPosition);

    void writeConfirm(long chargingSystemId, int chargerPosition);
}

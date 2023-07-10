package app.web.utb.alarm.infrastructure.plc;

import app.web.utb.alarm.application.service.UtbPlcErrorHandler;
import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
class UtbPlcErrorHandlerImpl implements UtbPlcErrorHandler {
    private final ChargingSystemRepository chargingSystemRepository;

    @Override
    public void writeError(long chargingSystemId, int chargerPosition) {
        ChargingSystem chargingSystem = chargingSystemRepository.findByIdOrThrowException(chargingSystemId);
        try {
            updateError(chargingSystem.getSnapshot().getPlcAddress(), calculateDbNumber(chargerPosition), true);
        } catch (IOException e) {
            throw new RuntimeException("Cannot connect to " + chargingSystem.getSnapshot().getPlcAddress());
        }
    }

    @Override
    public void writeConfirm(long chargingSystemId, int chargerPosition) {
        ChargingSystem chargingSystem = chargingSystemRepository.findByIdOrThrowException(chargingSystemId);
        try {
            updateError(chargingSystem.getSnapshot().getPlcAddress(), calculateDbNumber(chargerPosition), false);
        } catch (IOException e) {
            throw new RuntimeException("Cannot connect to " + chargingSystem.getSnapshot().getPlcAddress());
        }
    }

    private int calculateDbNumber(int chargerPosition) {
        return 50 + chargerPosition;
    }

    private void updateError(String plcAddress, int dbNumber, boolean errorState) throws IOException {
        S7Connector s7Connector = S7ConnectorFactory
                .buildTCPConnector()
                .withTimeout(300)
                .withHost(plcAddress)
                .withRack(0) //optional
                .withSlot(1) //optional
                .build();
        byte[] state = new byte[]{(byte) (errorState ? 1 : 0)};
        s7Connector.write(DaveArea.DB, dbNumber, 12, state);

        s7Connector.close();
    }
}

package app.web.utb.chargingSystem.infrastructure.plc;

import app.web.utb.chargingSystem.application.command.ChargingSystemUpdateDataCmd;
import app.web.utb.chargingSystem.domain.ChargerDataFromController;
import app.web.utb.chargingSystem.domain.ChargingSystemSnapshot;
import com.github.s7connector.api.DaveArea;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.factory.S7ConnectorFactory;
import com.github.s7connector.exception.S7Exception;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

@Service
public class UtbPlcReader {
    private S7Connector s7Connector;

    private void startSession(String plcAddress) {
        this.s7Connector = S7ConnectorFactory
                .buildTCPConnector()
                .withTimeout(300)
                .withHost(plcAddress)
                .withRack(0) //optional
                .withSlot(1) //optional
                .build();
    }

    private void closeSession() throws IOException {
        this.s7Connector.close();
    }

    private float readCurrent(int dbNumber) {
        byte[] read = s7Connector.read(DaveArea.DB, dbNumber, 4, 2);
        return ByteBuffer.wrap(read).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    private long readCart(int dbNumber) {
        byte[] read = s7Connector.read(DaveArea.DB, dbNumber, 4, 8);
        return ByteBuffer.wrap(read).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    private boolean readCharging(int dbNumber) {
        byte[] read = s7Connector.read(DaveArea.DB, dbNumber, 1, 0);
        return read[0] == 1;
    }

    private boolean readError(int dbNumber) {
        byte[] read = s7Connector.read(DaveArea.DB, dbNumber, 1, 6);
        return read[0] == 1;
    }

    public ChargingSystemUpdateDataCmd readSystemInfo(ChargingSystemSnapshot snapshot) throws IOException, S7Exception {
        this.startSession(snapshot.getPlcAddress());
        List<ChargerDataFromController> chargersInfo = snapshot.getChargersPosition()
                .stream()
                .map(position -> this.readChargerInfo(snapshot.getSystemId(), position))
                .toList();
        this.closeSession();
        return new ChargingSystemUpdateDataCmd(snapshot.getSystemId(), chargersInfo);
    }

    private ChargerDataFromController readChargerInfo(long systemId, Integer position) {
        int dbNumber = computeDbNumber(position);
        boolean charging = this.readCharging(dbNumber);
        float current = this.readCurrent(dbNumber);
        boolean error = this.readError(dbNumber);
        long cart = this.readCart(dbNumber);
        return new ChargerDataFromController(systemId, position, cart, current, charging, error);
    }

    private int computeDbNumber(Integer position) {
        return position + 50;
    }
}

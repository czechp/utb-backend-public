package app.web.utb.chargingSystem.application.command;

import app.web.utb.chargingSystem.domain.ChargerDataFromController;
import lombok.Value;

import java.util.List;

@Value
public class ChargingSystemUpdateDataCmd {
    long systemId;
    List<ChargerDataFromController> chargersInfo;
}

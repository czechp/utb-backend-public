package app.web.utb.chargingSystem.domain;

import lombok.Value;

import java.util.List;

@Value
public class ChargingSystemSnapshot {
    long systemId;
    String plcAddress;
    String name;
    List<Integer> chargersPosition;

}

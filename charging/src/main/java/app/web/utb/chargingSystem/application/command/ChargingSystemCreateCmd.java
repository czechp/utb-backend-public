package app.web.utb.chargingSystem.application.command;

import app.web.utb.chargingSystem.domain.ChargingSystemConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChargingSystemCreateCmd {
    @Length(min = ChargingSystemConstraint.NAME_MIN_LENGTH)
    private String name;
    @Length(min = ChargingSystemConstraint.ADDRESS_MIN_LENGTH)
    private String plcAddress;
    @Length(min = ChargingSystemConstraint.ADDRESS_MIN_LENGTH)
    private String networkMask;
}

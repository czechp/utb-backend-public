package app.web.utb.chargingSystem.domain;

import app.web.utb.validator.CommonValidators;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class PlcAddress {
    @Length(min = ChargingSystemConstraint.ADDRESS_MIN_LENGTH)
    @Column(unique = true)
    String address;
    @Length(min = ChargingSystemConstraint.ADDRESS_MIN_LENGTH)
    String networkMask;

    PlcAddress(String address, String networkMask) {
        this.address = address;
        this.networkMask = networkMask;
        validate();
    }

    private void validate() {
        CommonValidators.validateMinLength(this.address, ChargingSystemConstraint.ADDRESS_MIN_LENGTH, "adres sterownika");
        CommonValidators.validateMinLength(this.networkMask, ChargingSystemConstraint.ADDRESS_MIN_LENGTH, "maska podsieci");
    }
}

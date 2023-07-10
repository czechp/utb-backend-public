package app.web.utb.chargingSystem.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class ChargerState {
    private boolean chargingInterruptionError;
    private boolean cartNotMatchError;
    private float current;
    private boolean charging;
    private boolean chargingWithoutCartError;


    ChargerState assignInterruptionError() {
        return new ChargerState(true, this.cartNotMatchError, this.current, this.charging, this.chargingWithoutCartError);
    }

    ChargerState assignCurrent(float current) {
        return new ChargerState(this.chargingInterruptionError, this.cartNotMatchError, current, this.charging, this.chargingWithoutCartError);
    }

    ChargerState assignCharging(boolean chargingState) {
        return new ChargerState(this.chargingInterruptionError, this.cartNotMatchError, this.current, chargingState, this.chargingWithoutCartError);
    }

    ChargerState assignCartNotMatchError() {
        return new ChargerState(this.chargingInterruptionError, true, this.current, this.charging, this.chargingWithoutCartError);
    }

    boolean chargingStarted(boolean chargingState) {
        return !this.charging && chargingState;
    }

    boolean chargingStopped(boolean chargingState) {
        return this.charging & !chargingState;
    }


    ChargerState assignChargingWithoutCartError() {
        return new ChargerState(this.chargingInterruptionError, this.cartNotMatchError, this.current, this.charging, true);
    }

    ChargerState confirmCartNotMatchError() {
        return new ChargerState(this.chargingInterruptionError, false, this.current, this.charging, this.chargingWithoutCartError);
    }

    ChargerState confirmChargingWithoutCartError() {
        return new ChargerState(this.chargingInterruptionError, this.cartNotMatchError, this.current, this.charging, false);
    }

    ChargerState confirmChargingInterruptionError() {
        return new ChargerState(false, this.cartNotMatchError, this.current, this.charging, this.chargingWithoutCartError);
    }
}

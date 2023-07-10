package app.web.utb.chargingSystem.infrastructure.rest;

import app.web.utb.chargingSystem.application.command.ChargingSystemAssignCartToChargerCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemAssignCartToChargerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ChargingSystemAssignCartToChargerController.URL)
@AllArgsConstructor
class ChargingSystemAssignCartToChargerController {
    static final String URL = "/api/charging-systems/charger/cart";
    private final ChargingSystemAssignCartToChargerHandler handler;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_MANAGEMENT"})
    void assignCartToCharger(@RequestBody ChargingSystemAssignCartToChargerCmd command) {
        handler.handle(command);
    }
}

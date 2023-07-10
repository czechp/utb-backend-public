package app.web.utb.chargingSystem.infrastructure.rest;

import app.web.utb.chargingSystem.application.command.ChargingSystemAssignChargerCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemAssignChargerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ChargingSystemAssignChargerController.URL)
@AllArgsConstructor
class ChargingSystemAssignChargerController {
    static final String URL = "/api/charging-systems/charger";
    private final ChargingSystemAssignChargerHandler handler;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN", "ROLE_MANAGEMENT"})
    void assignCharger(@RequestBody ChargingSystemAssignChargerCmd command) {
        this.handler.handle(command);
    }
}

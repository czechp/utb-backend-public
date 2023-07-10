package app.web.utb.chargingSystem.infrastructure.rest;

import app.web.utb.chargingSystem.application.command.ChargingSystemRemoveChargerCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemRemoveChargerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ChargingSystemRemoveChargerController.URL)
@AllArgsConstructor
class ChargingSystemRemoveChargerController {
    static final String URL = "/api/charging-systems/charger";
    private final ChargingSystemRemoveChargerHandler handler;

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeCharger(@RequestBody ChargingSystemRemoveChargerCmd command) {
        handler.handle(command);
    }
}

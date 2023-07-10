package app.web.utb.chargingSystem.infrastructure.rest;

import app.web.utb.chargingSystem.application.command.ChargingSystemDetachCartCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemDetachCartHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ChargingSystemDetachCartFromChargerController.URL)
@AllArgsConstructor
class ChargingSystemDetachCartFromChargerController {
    static final String URL = "/api/charging-systems/charger/cart";
    private final ChargingSystemDetachCartHandler handler;

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_MANAGEMENT"})
    void detachCartFromCharger(@RequestBody ChargingSystemDetachCartCmd command) {
        handler.handle(command);
    }
}

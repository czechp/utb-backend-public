package app.web.utb.chargingSystem.infrastructure.rest;

import app.web.utb.chargingSystem.application.command.ChargingSystemRemoveCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemRemoveHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static app.web.utb.chargingSystem.infrastructure.rest.ChargingSystemRemoveController.URL;

@RestController
@RequestMapping(URL)
@AllArgsConstructor
class ChargingSystemRemoveController {
    static final String URL = "/api/charging-systems";
    private final ChargingSystemRemoveHandler handler;

    @DeleteMapping("/{chargingSystemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_MANAGEMENT"})
    void removeChargingSystem(@PathVariable(name = "chargingSystemId") long chargingSystemId) {
        this.handler.handle(new ChargingSystemRemoveCmd(chargingSystemId));
    }
}

package app.web.utb.chargingSystem.infrastructure.rest;

import app.web.utb.chargingSystem.application.command.ChargingSystemCreateCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemCreateHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ChargingSystemCreateController.URL)
@Validated
@AllArgsConstructor
class ChargingSystemCreateController {
    public static final String URL = "/api/charging-systems";
    private final ChargingSystemCreateHandler handler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN"})
    void createChargingSystem(@RequestBody @Valid ChargingSystemCreateCmd command) {
        handler.handle(command);
    }
}

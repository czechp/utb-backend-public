package app.web.utb.charging.infrastructure.rest;

import app.web.utb.charging.application.command.ChargingRemoveHandler;
import app.web.utb.charging.application.command.CharingRemoveCmd;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CharingRemoveController.URL)
@AllArgsConstructor
class CharingRemoveController {
    static final String URL = "/api/chargings";

    private final ChargingRemoveHandler chargingRemoveHandler;

    @DeleteMapping("/{chargingId}")
    @Secured({"ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCharging(@PathVariable(name = "chargingId") long chargingId) {
        this.chargingRemoveHandler.handle(new CharingRemoveCmd(chargingId));
    }
}

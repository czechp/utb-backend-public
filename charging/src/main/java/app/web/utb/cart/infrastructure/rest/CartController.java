package app.web.utb.cart.infrastructure.rest;

import app.web.utb.cart.application.command.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
@Validated
class CartController {
    private final CartCreateHandler cartCreateHandler;
    private final CartRemoveHandler cartRemoveHandler;
    private final CartAssignDescriptionHandler cartAssignDescriptionHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN", "ROLE_MANAGEMENT"})
    void createCart(@RequestBody @Valid CartCreateCmd cartCreateCmd) {
        cartCreateHandler.handle(cartCreateCmd);
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_MANAGEMENT"})
    void deleteCartById(@PathVariable(name = "cartId") long cartId) {
        cartRemoveHandler.handle(new CartRemoveCmd(cartId));
    }

    @PatchMapping("/description")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"ROLE_ADMIN", "ROLE_MANAGEMENT"})
    void assignDescription(@RequestBody CartAssignDescriptionCmd assignDescriptionCmd) {
        this.cartAssignDescriptionHandler.handle(assignDescriptionCmd);
    }
}

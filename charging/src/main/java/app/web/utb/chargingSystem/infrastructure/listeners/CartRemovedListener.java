package app.web.utb.chargingSystem.infrastructure.listeners;

import app.web.utb.cart.domain.event.CartRemoved;
import app.web.utb.chargingSystem.application.command.ChargingSystemCartRemovedCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemCartRemovedHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
class CartRemovedListener {
    private final ChargingSystemCartRemovedHandler handler;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void cartRemoved(CartRemoved cartRemoved) {
        handler.handle(new ChargingSystemCartRemovedCmd(cartRemoved.getCartId()));
    }
}

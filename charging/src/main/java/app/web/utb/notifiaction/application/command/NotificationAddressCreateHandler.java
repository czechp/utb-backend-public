package app.web.utb.notifiaction.application.command;

import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import app.web.utb.notifiaction.domain.NotificationAddress;
import app.web.utb.notifiaction.domain.NotificationAddressFactory;
import app.web.utb.notifiaction.domain.NotificationAddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationAddressCreateHandler implements CmdHandler<NotificationAddressCreateCmd> {
    private final NotificationAddressRepository notificationAddressRepository;
    private final ChargingSystemRepository chargingSystemRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(NotificationAddressCreateCmd command) {
        this.chargingSystemRepository.findByIdOrThrowException(command.getChargingSystemId());
        NotificationAddress notificationAddress = NotificationAddressFactory.of(command.getChargingSystemId(), command.getEmail());
        this.notificationAddressRepository.save(notificationAddress);
        this.domainEventPublisher.publish(notificationAddress);
    }
}

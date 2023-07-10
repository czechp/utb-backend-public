package app.web.utb.notifiaction.application.command;

import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import app.web.utb.notifiaction.domain.NotificationAddress;
import app.web.utb.notifiaction.domain.NotificationAddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class NotificationAddressDeleteHandler implements CmdHandler<NotificationAddressDeleteCmd> {
    private final NotificationAddressRepository notificationAddressRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(NotificationAddressDeleteCmd command) {
        NotificationAddress addressToRemove = this.notificationAddressRepository.findByIdOrThrowException(command.getNotificationId());
        addressToRemove.remove();
        notificationAddressRepository.delete(addressToRemove);
        this.domainEventPublisher.publish(addressToRemove);
    }
}

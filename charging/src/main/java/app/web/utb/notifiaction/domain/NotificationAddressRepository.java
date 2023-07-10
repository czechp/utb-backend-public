package app.web.utb.notifiaction.domain;

import app.web.utb.exception.ElementNotFoundException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationAddressRepository extends CrudRepository<NotificationAddress, Long> {
    default NotificationAddress findByIdOrThrowException(long notificationId) {
        return findById(notificationId)
                .orElseThrow(() -> new ElementNotFoundException(String.format("Adres z id: %d nie istnieje", notificationId)));
    }

    List<NotificationAddress> findByChargingSystemId(long chargingSystemId);

}

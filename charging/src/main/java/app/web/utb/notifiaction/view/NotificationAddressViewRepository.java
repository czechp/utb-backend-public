package app.web.utb.notifiaction.view;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface NotificationAddressViewRepository extends JpaRepository<NotificationAddressView, Long> {

    List<NotificationAddressView> findByChargingSystemId(long chargingSystemId, Sort sort);
}

package app.web.utb.notifiaction.view;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(NotificationAddressViewController.URL)
@AllArgsConstructor
class NotificationAddressViewController {
    static final String URL = "/api/notifications/system";
    private final NotificationAddressViewRepository repository;

    @GetMapping("/{chargingSystemId}")
    List<NotificationAddressView> findAllNotificationAddresses(@PathVariable(name = "chargingSystemId") long chargingSystemId,
                                                               @SortDefault(sort = "email", direction = Sort.Direction.ASC) Sort sort) {
        return repository.findByChargingSystemId(chargingSystemId, sort);
    }


}

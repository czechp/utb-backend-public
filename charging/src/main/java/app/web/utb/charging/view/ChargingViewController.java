package app.web.utb.charging.view;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ChargingViewController.URL)
@AllArgsConstructor
class ChargingViewController {
    static final String URL = "/api/chargings/cart";
    private final ChargingViewRepository chargingViewRepository;

    @GetMapping("/{cartId}")
    List<ChargingProjection> findAllChargings(@PathVariable(name = "cartId") long cartId, @SortDefault(value = "id", direction = Sort.Direction.DESC) Sort sort) {
        return chargingViewRepository.findChargingsByCartId(cartId, sort);
    }
}

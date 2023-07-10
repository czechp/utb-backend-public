package app.web.utb.chargingSystem.view;

import app.web.utb.exception.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ChargingSystemViewController.URL)
@AllArgsConstructor
class ChargingSystemViewController {
    static final String URL = "/api/charging-systems";
    private final ChargingSystemViewRepository chargingSystemViewRepository;
    private final ChargerWithCartsRepository chargerWithCartsRepository;

    @GetMapping
    List<ChargingSystemView> findAllChargingSystems(@SortDefault(value = "id", direction = Sort.Direction.ASC) Sort sort) {
        return chargingSystemViewRepository.findAllChargingSystems(sort);
    }

    @GetMapping("/{systemId}")
    ChargingSystemView findChargingSystemById(@PathVariable(name = "systemId") long systemId) {
        return chargingSystemViewRepository.findChargingSystemsById(systemId)
                .orElseThrow(() -> new ElementNotFoundException(String.format("System z id: %s nie istnieje", systemId)));
    }

    @GetMapping("/charger/{id}")
    ChargerWithCarts findChargerById(@PathVariable(name = "id") long chargerId) {
        return chargerWithCartsRepository.findChargerById(chargerId)
                .orElseThrow(() -> new ElementNotFoundException(String.format("Prostownik z id: %d nie istnieje", chargerId)));
    }
}

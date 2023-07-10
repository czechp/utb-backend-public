package app.web.utb.charging.domain;

import app.web.utb.exception.ElementNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargingRepository extends CrudRepository<Charging, Long> {
    List<Charging> findByCartIdAndFinishedAt(long cartId, Object o);

    default List<Charging> findUnfinishedChargings(long cartId) {
        return findByCartIdAndFinishedAt(cartId, null);
    }

    default Charging findByIdOrThrowException(long chargingId) {
        return findById(chargingId)
                .orElseThrow(() -> new ElementNotFoundException(String.format("Ładowanie z id: %d nie istnieje", chargingId)));
    }
}

package app.web.utb.chargingSystem.domain;

import app.web.utb.exception.ElementNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargingSystemRepository extends JpaRepository<ChargingSystemImpl, Long> {
    List<ChargingSystem> findByChargersAssignedCarts(long cartId);

    @Query("select system from ChargingSystemImpl system left join fetch system.chargers charger left join fetch charger.assignedCarts order by system.id asc")
    List<ChargingSystem> findByChargingSystems();

    boolean existsByName(String name);

    boolean existsByPlcAddressAddress(String plcAddress);

    default ChargingSystem findByIdOrThrowException(long chargingSystemId) {
        return findById(chargingSystemId)
                .orElseThrow(() -> new ElementNotFoundException(String.format("System z id: %s nie istnieje", chargingSystemId)));
    }

    default long saveAngGetId(ChargingSystem chargingSystem) {
        ChargingSystemImpl persistedChargingSystem = save((ChargingSystemImpl) chargingSystem);
        return persistedChargingSystem.getId();
    }

    void delete(ChargingSystem chargingSystem);
}

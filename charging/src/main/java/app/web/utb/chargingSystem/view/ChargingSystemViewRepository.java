package app.web.utb.chargingSystem.view;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface ChargingSystemViewRepository extends JpaRepository<ChargingSystemView, Long> {
    @Query("select system from ChargingSystemView system left join fetch system.chargers charger")
    List<ChargingSystemView> findAllChargingSystems(Sort sort);

    @Query("select system from ChargingSystemView system left join fetch system.chargers charger where system.id=:id")
    Optional<ChargingSystemView> findChargingSystemsById(@Param("id") long id);
}

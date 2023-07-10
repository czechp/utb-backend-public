package app.web.utb.chargingSystem.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ChargerWithCartsRepository extends JpaRepository<ChargerWithCarts, Long> {
    @Query("select charger from ChargerWithCarts charger left join fetch charger.carts cart where charger.id=:id")
    Optional<ChargerWithCarts> findChargerById(@Param("id") long chargerId);
}

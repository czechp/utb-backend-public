package app.web.utb.charging.view;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ChargingViewRepository extends JpaRepository<ChargingView, Long> {

    @Query("""
            select new app.web.utb.charging.view.ChargingProjection(
            charging.id as id,
            system.name as chargingSystemName,
            charging.chargerPosition as chargerPosition,
            cart.umupNumber as umupNumber,
            charging.createdAt as createdAt,
            charging.finishedAt as finishedAt,
            charging.failed as failed
            )
            from ChargingView charging
             left join ChargingSystemView system on charging.chargingSystemId=system.id
             left join CartView cart on charging.cartId=cart.id
             where charging.cartId=:cartId
            """)
    List<ChargingProjection> findChargingsByCartId(@Param("cartId") long cartId, Sort sort);

}


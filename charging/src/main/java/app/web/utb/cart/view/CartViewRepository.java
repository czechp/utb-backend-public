package app.web.utb.cart.view;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface CartViewRepository extends JpaRepository<CartView, Long> {
    @Query("""
            select
            cart.id as id,
            cart.umupNumber as umupNumber,
            cart.description as description,
            (select count(charging.id) from ChargingView charging where charging.cartId=cart.id and charging.failed=false) as correctChargings,
            (select count(charging.id) from ChargingView charging where charging.cartId=cart.id and charging.failed=true) as failedChargings
            from CartView cart
            """)
    List<CartProjection> findAllProjections(Sort sort);
}

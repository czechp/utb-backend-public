package app.web.utb.cart.view;

import app.web.utb.cart.infrastructure.exception.CartExceptionFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@AllArgsConstructor
class CartViewController {
    private final CartViewRepository cartViewRepository;

    @GetMapping
    List<CartProjection> findAllCarts(@SortDefault(value = "umupNumber", direction = Sort.Direction.ASC) Sort sort) {
        return cartViewRepository.findAllProjections(sort);
    }

    @GetMapping("/{cartId}")
    CartView findCartById(@PathVariable(name = "cartId") long cartId) {
        return cartViewRepository.findById(cartId)
                .orElseThrow(() -> CartExceptionFactory.notExists(cartId));
    }
}

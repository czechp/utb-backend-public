package app.web.utb.cart.view;

import app.web.utb.cart.infrastructure.persistence.CartTableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Immutable;

@Entity
@Table(name = CartTableName.TABLE_NAME)
@Immutable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class CartView {
    @Id
    private long id;
    private String umupNumber;
    private String description;
}

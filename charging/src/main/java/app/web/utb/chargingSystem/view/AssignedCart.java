package app.web.utb.chargingSystem.view;

import app.web.utb.cart.infrastructure.persistence.CartTableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

@Entity
@Table(name = CartTableName.TABLE_NAME)
@NoArgsConstructor
@Getter
@Immutable
class AssignedCart {
    @Id
    private long id;
    private String umupNumber;
}

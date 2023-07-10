package app.web.utb.chargingSystem.view;

import app.web.utb.chargingSystem.infrastructure.persistence.ChargerTableName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = ChargerTableName.CHARGER_TABLE_NAME)
@NoArgsConstructor
@Getter
@Immutable
class ChargerWithCarts {
    @Id
    private long id;
    private int position;
    @Column(name = "charging_system_id")
    private long systemId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "assigned_carts",
            joinColumns = @JoinColumn(name = "charger_id"),
            inverseJoinColumns = @JoinColumn(name = "cart_id"))
    @OrderBy("umup_number ASC")
    private final Set<AssignedCart> carts = new HashSet<>();

}

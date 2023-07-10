package app.web.utb.chargingSystem.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

import java.util.Optional;

@Entity
@Table(name = "chargers_view")
@NoArgsConstructor
@Getter
@Immutable
class ChargerView {
    @Id
    private long id;
    private int position;
    private String currentCart;
    private float current;
    private boolean charging;
    @JsonIgnore
    private boolean chargingInterruptionError;
    @JsonIgnore
    private boolean cartNotMatchError;
    @Transient
    private boolean error;

    @PostLoad
    void afterLoad() {
        this.error = this.chargingInterruptionError || this.cartNotMatchError;
        this.currentCart = Optional.ofNullable(this.currentCart).orElse("Brak wózka");
    }
}

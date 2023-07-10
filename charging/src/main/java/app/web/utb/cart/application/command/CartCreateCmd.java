package app.web.utb.cart.application.command;

import app.web.utb.cart.domain.CartConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class CartCreateCmd {
    @NotBlank
    @Length(min = CartConstraint.UMUP_NUMBER_MIN_LENGTH)
    String umupNumber;
    String description;
}

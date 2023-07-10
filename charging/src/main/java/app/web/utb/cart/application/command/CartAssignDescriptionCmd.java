package app.web.utb.cart.application.command;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartAssignDescriptionCmd {
    private long cartId;
    private String description;
}

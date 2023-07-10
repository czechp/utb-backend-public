package app.web.utb.notifiaction.application.command;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class NotificationAddressCreateCmd {
    private long chargingSystemId;
    @Email
    private String email;
}

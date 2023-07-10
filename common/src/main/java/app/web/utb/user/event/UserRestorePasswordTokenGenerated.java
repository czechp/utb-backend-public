package app.web.utb.user.event;

import app.web.utb.domainDrivenDesign.annotation.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;


@DomainEvent
@AllArgsConstructor
@Getter
public class UserRestorePasswordTokenGenerated {
    private final String restoreToken;
}

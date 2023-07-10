package app.web.utb.user.event;

import app.web.utb.domainDrivenDesign.annotation.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@DomainEvent
@Getter
@AllArgsConstructor
public class UserConfirmed {
    private String confirmationToken;

}

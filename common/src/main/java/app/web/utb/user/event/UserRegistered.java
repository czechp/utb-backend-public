package app.web.utb.user.event;

import app.web.utb.domainDrivenDesign.annotation.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@DomainEvent
@Getter
@AllArgsConstructor
public class UserRegistered extends app.web.utb.domainDrivenDesign.superClass.DomainEvent {
    private long id;
    private String username;
    private String email;
    private String confirmationToken;


}

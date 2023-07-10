package app.web.utb.user.domain;

import app.web.utb.domainDrivenDesign.annotation.ValueObject;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.PersistenceConstructor;

@ValueObject
@Getter(AccessLevel.PACKAGE)
@Builder(access = AccessLevel.PACKAGE, setterPrefix = "with")
class UserActive {
    private boolean confirmed;
    private boolean confirmedByAdmin;

    UserActive() {
    }

    @PersistenceConstructor
    UserActive(boolean confirmed, boolean confirmedByAdmin) {
        this.confirmed = confirmed;
        this.confirmedByAdmin = confirmedByAdmin;
    }


    UserActive confirmUser() {
        UserActive userActive = new UserActive();
        userActive.confirmed = true;
        return userActive;
    }

    public boolean isActive() {
        return confirmed && confirmedByAdmin;
    }

    public UserActive confirmByAdmin(boolean targetState) {
        this.confirmedByAdmin = targetState;
        return new UserActive.UserActiveBuilder()
                .withConfirmed(confirmed)
                .withConfirmedByAdmin(confirmedByAdmin)
                .build();
    }
}

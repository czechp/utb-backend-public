package app.web.utb.notifiaction.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class NotificationAddressSnapshot {
    private final String email;
}

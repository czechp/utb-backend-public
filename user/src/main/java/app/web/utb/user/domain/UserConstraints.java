package app.web.utb.user.domain;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

public class UserConstraints {
    public static final int PASSWORD_MIN_LENGTH = 3;
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final TemporalAmount EXPIRATION_TOKEN_TIME = Duration.ofMinutes(30);
    private static final TemporalAmount EXPIRATION_RESTORE_TOKEN_TIME = Duration.ofMinutes(30);
}

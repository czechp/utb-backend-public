package app.web.utb.user.domain;

import app.web.utb.domainDrivenDesign.superClass.DomainSnapshot;

import java.time.LocalDateTime;

public interface UserSnapshot extends DomainSnapshot {
    String getUsername();

    String getEmail();

    String getPassword();

    String getConfirmToken();

    LocalDateTime getConfirmTokenExpiredAt();

    boolean isConfirmed();

    UserRole getUserRole();

    boolean isConfirmedByAdmin();

    String getRestorePasswordToken();

    LocalDateTime getRestorePasswordTokenExpiration();
}

package app.web.utb.user.infrastructure.persistence;

import app.web.utb.user.domain.UserConstraints;
import app.web.utb.user.domain.UserRole;
import app.web.utb.user.domain.UserSnapshot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserEntity implements UserSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    private String uuid;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Length(min = UserConstraints.USERNAME_MIN_LENGTH)
    @NotNull
    @NotBlank
    private String username;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String confirmToken;

    @NotNull
    private LocalDateTime confirmTokenExpiredAt;

    @NotNull
    private boolean confirmed;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @NotNull
    private boolean confirmedByAdmin;

    @NotNull
    @NotBlank
    private String restorePasswordToken;

    @NotNull
    private LocalDateTime restorePasswordTokenExpiration;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getConfirmToken() {
        return confirmToken;
    }

    @Override
    public LocalDateTime getConfirmTokenExpiredAt() {
        return confirmTokenExpiredAt;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean isConfirmed() {
        return confirmed;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public UserRole getUserRole() {
        return userRole;
    }

    @Override
    public boolean isConfirmedByAdmin() {
        return confirmedByAdmin;
    }

    @Override
    public String getRestorePasswordToken() {
        return restorePasswordToken;
    }

    @Override
    public LocalDateTime getRestorePasswordTokenExpiration() {
        return restorePasswordTokenExpiration;
    }
}

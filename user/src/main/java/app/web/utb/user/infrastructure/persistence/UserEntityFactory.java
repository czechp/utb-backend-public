package app.web.utb.user.infrastructure.persistence;

import app.web.utb.user.domain.UserSnapshot;

class UserEntityFactory {

    public static UserEntity createUserEntity(UserSnapshot userSnapshot) {
        return new UserEntity(
                userSnapshot.getId(),
                userSnapshot.getVersion(),
                userSnapshot.getUuid(),
                userSnapshot.getCreatedAt(),
                userSnapshot.getUpdatedAt(),
                userSnapshot.getUsername(),
                userSnapshot.getEmail(),
                userSnapshot.getPassword(),
                userSnapshot.getConfirmToken(),
                userSnapshot.getConfirmTokenExpiredAt(),
                userSnapshot.isConfirmed(),
                userSnapshot.getUserRole(),
                userSnapshot.isConfirmedByAdmin(),
                userSnapshot.getRestorePasswordToken(),
                userSnapshot.getRestorePasswordTokenExpiration()
        );
    }
}

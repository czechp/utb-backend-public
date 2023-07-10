package app.web.utb.notifiaction.view;

import app.web.utb.notifiaction.infrastructure.persistence.NotificationAddressTableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = NotificationAddressTableName.TABLE_NAME)
@Immutable
@Getter
class NotificationAddressView {
    @Id
    private long id;
    private long chargingSystemId;
    private String email;
}

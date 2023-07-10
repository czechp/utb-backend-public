package app.web.utb.user.presentation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
class UserView {
    @Id
    private long id;
    private String username;
    private String email;
    private String userRole;
    private boolean confirmed;
    private LocalDateTime createdAt;
    private boolean confirmedByAdmin;

}

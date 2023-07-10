package app.web.utb.view;

import java.time.LocalDateTime;

public interface BasicView {
    long getId();
    long getVersion();
    String getUuid();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
}

package app.web.utb.alarm.infrastructure.email;

import app.web.utb.alarm.application.service.AlarmEmailNotifier;
import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Profile({"development", "test"})
@Slf4j
class AlarmEmailNotifierDevImpl implements AlarmEmailNotifier {
    private final ChargingSystemRepository chargingSystemRepository;

    @Override
    public void notifyAboutNewAlarm(long chargingSystemId, int chargerPosition) {
        ChargingSystem chargingSystem = chargingSystemRepository.findByIdOrThrowException(chargingSystemId);
        log.info("Sent notification about alarm in: {} on charger: {}", chargingSystem.getSnapshot().getName(), chargerPosition);
    }
}

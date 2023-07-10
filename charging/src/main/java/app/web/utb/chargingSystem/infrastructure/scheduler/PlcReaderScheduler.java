package app.web.utb.chargingSystem.infrastructure.scheduler;

import app.web.utb.chargingSystem.application.command.ChargingSystemUpdateDataCmd;
import app.web.utb.chargingSystem.application.command.ChargingSystemUpdateDataHandler;
import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import app.web.utb.chargingSystem.domain.ChargingSystemSnapshot;
import app.web.utb.chargingSystem.infrastructure.plc.UtbPlcReader;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class PlcReaderScheduler {
    private final ChargingSystemRepository chargingSystemRepository;
    private final UtbPlcReader utbPlcReader;
    private final ChargingSystemUpdateDataHandler handler;

    @Scheduled(fixedDelay = 10_000)
    public void readingScheduler() {
        List<ChargingSystemUpdateDataCmd> result = chargingSystemRepository.findByChargingSystems()
                .stream()
                .map(ChargingSystem::getSnapshot)
                .map(this::readDataFromPlcForSystem)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        result.forEach(handler::handle);
    }

    private ChargingSystemUpdateDataCmd readDataFromPlcForSystem(ChargingSystemSnapshot chargingSystemSnap) {
        try {
            return utbPlcReader.readSystemInfo(chargingSystemSnap);
        } catch (Exception e) {
            String message = "Cannot read data for PLC: " + chargingSystemSnap.getPlcAddress();
            log.error(message);
            return null;
        }

    }
}

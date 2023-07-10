package app.web.utb.chargingSystem.application.command;

import app.web.utb.chargingSystem.domain.ChargingSystem;
import app.web.utb.chargingSystem.domain.ChargingSystemRepository;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ChargingSystemAlarmConfirmedHandler implements CmdHandler<ChargingSystemAlarmConfirmedCmd> {
    private final ChargingSystemRepository repository;

    @Override
    public void handle(ChargingSystemAlarmConfirmedCmd command) {
        ChargingSystem chargingSystem = repository.findByIdOrThrowException(command.getChargingSystemId());
        chargingSystem.confirmError(command.getChargerPosition(), command.getAlarmType());
    }
}

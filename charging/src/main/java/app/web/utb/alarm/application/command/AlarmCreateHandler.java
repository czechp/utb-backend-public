package app.web.utb.alarm.application.command;

import app.web.utb.alarm.application.service.AlarmEmailNotifier;
import app.web.utb.alarm.application.service.UtbPlcErrorHandler;
import app.web.utb.alarm.domain.Alarm;
import app.web.utb.alarm.domain.AlarmFactory;
import app.web.utb.alarm.domain.AlarmRepository;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AlarmCreateHandler implements CmdHandler<AlarmCreateCmd> {
    private final AlarmRepository alarmRepository;
    private final UtbPlcErrorHandler utbPlcErrorHandler;
    private final AlarmEmailNotifier alarmEmailNotifier;

    @Override
    public void handle(AlarmCreateCmd command) {
        Alarm alarm = AlarmFactory.alarm(command.getChargingSystemId(), command.getChargerPosition(), command.getAlarmType());
        alarmRepository.save(alarm);
        utbPlcErrorHandler.writeError(command.getChargingSystemId(), command.getChargerPosition());
        alarmEmailNotifier.notifyAboutNewAlarm(command.getChargingSystemId(), command.getChargerPosition());
    }
}

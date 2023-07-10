package app.web.utb.alarm.application.command;

import app.web.utb.alarm.application.service.UtbPlcErrorHandler;
import app.web.utb.alarm.domain.Alarm;
import app.web.utb.alarm.domain.AlarmRepository;
import app.web.utb.alarm.domain.AlarmSnapshot;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AlarmConfirmHandler implements CmdHandler<AlarmConfirmCmd> {
    private final AlarmRepository alarmRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final UtbPlcErrorHandler utbPlcErrorHandler;

    @Override
    public void handle(AlarmConfirmCmd command) {
        Alarm alarmToConfirm = this.alarmRepository.findByIdOrThrowException(command.getAlarmId());
        alarmToConfirm.confirm();
        AlarmSnapshot alarmSnapshot = alarmToConfirm.getSnapshot();
        this.utbPlcErrorHandler.writeConfirm(alarmSnapshot.getChargingSystemId(), alarmSnapshot.getChargerPosition());
        this.domainEventPublisher.publish(alarmToConfirm);
    }
}

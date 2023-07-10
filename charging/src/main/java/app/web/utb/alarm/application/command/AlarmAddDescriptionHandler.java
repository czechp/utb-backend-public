package app.web.utb.alarm.application.command;

import app.web.utb.alarm.domain.Alarm;
import app.web.utb.alarm.domain.AlarmRepository;
import app.web.utb.domainDrivenDesign.event.DomainEventPublisher;
import app.web.utb.handler.CmdHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AlarmAddDescriptionHandler implements CmdHandler<AlarmAddDescriptionCmd> {
    private final AlarmRepository alarmRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public void handle(AlarmAddDescriptionCmd command) {
        Alarm alarm = this.alarmRepository.findByIdOrThrowException(command.getAlarmId());
        alarm.addDescription(command.getDescription());
        this.domainEventPublisher.publish(alarm);
    }
}

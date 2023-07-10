package app.web.utb.alarm.view;

import app.web.utb.exception.ElementNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(AlarmViewController.URL)
@AllArgsConstructor
class AlarmViewController {
    static final String URL = "/api/alarms";
    private final AlarmViewRepository alarmViewRepository;

    @GetMapping
    List<AlarmProjection> findAllAlarms() {
        return alarmViewRepository.findAllProjections();
    }

    @GetMapping("/{alarmId}")
    AlarmProjection findAlarmById(@PathVariable(name = "alarmId") long alarmId) {
        return alarmViewRepository.findProjectionById(alarmId)
                .orElseThrow(() -> new ElementNotFoundException(String.format("Alarm z id: %d nie istnieje", alarmId)));
    }
}

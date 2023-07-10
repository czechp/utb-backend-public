package app.web.utb.alarm.domain;

import app.web.utb.exception.ElementNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, Long> {
    default Alarm findByIdOrThrowException(long alarmId) {
        return findById(alarmId).orElseThrow(() -> new ElementNotFoundException(String.format("Alarm z id: %d nie istnieje", alarmId)));
    }
}

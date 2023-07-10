package app.web.utb.alarm.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface AlarmViewRepository extends JpaRepository<AlarmView, Long> {
    @Query(value = """
            select alarm.id as id,
             alarm.created_at as createdAt,
             alarm.charger_position as chargerPosition,
             alarm.alarm_type as alarmType,
             alarm.confirmed as confirmed,
             alarm.description as description,
             system.name as chargingSystemName
             from alarms alarm
             left join charging_systems system on alarm.charging_system_id=system.id
             order by alarm.confirmed asc, alarm.created_at desc
            """, nativeQuery = true)
    List<AlarmProjection> findAllProjections();

    @Query(value = """
            select alarm.id as id,
             alarm.created_at as createdAt,
             alarm.charger_position as chargerPosition,
             alarm.alarm_type as alarmType,
             alarm.confirmed as confirmed,
             alarm.description as description,
             system.name as chargingSystemName
             from alarms alarm
             left join charging_systems system on alarm.charging_system_id=system.id
             where alarm.id=:id
            """, nativeQuery = true)
    Optional<AlarmProjection> findProjectionById(@Param("id") long id);
}

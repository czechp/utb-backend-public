package app.web.utb.chargingSystem.domain

import app.web.utb.chargingSystem.domain.event.ChargerAssigned
import app.web.utb.domainDrivenDesign.event.DomainEvent
import spock.lang.Specification

class ChargingSystemAssignChargerTest extends Specification {
    def "assign charger at position #chargerPosition"() {
        given: "Admin create charger"
        ChargingSystem chargingSystem = ChargingSystemFactory.create(name, plcAddres, networkSubmask)

        when: "Admin add a new charger at given position"
        chargingSystem.assignCharger(chargerPosition)

        then: "In system should be a new charger"
        List<DomainEvent> events = chargingSystem.getDomainEvents()
        boolean chargerAssigned = events.stream()
                .anyMatch(event -> event instanceof ChargerAssigned)

        chargerAssigned
        where:
        name << ["Test system name"]
        plcAddres << ["192.168.0.1"]
        networkSubmask << ["255.255.255.0"]
        chargerPosition << [1]
    }

    def "assign charger at already used position"() {
        given: "Admin create charger"
        ChargingSystem chargingSystem = ChargingSystemFactory.create(name, plcAddres, networkSubmask)

        when: "Admin add a new charger at given position"
        chargingSystem.assignCharger(chargerPosition)
        and: "Add next charger at the same position"
        chargingSystem.assignCharger(chargerPosition)

        then: "In system should be a new charger"
        thrown IllegalStateException

        where:
        name << ["Test system name"]
        plcAddres << ["192.168.0.1"]
        networkSubmask << ["255.255.255.0"]
        chargerPosition << [1]
    }

    def "charger position is too large"() {
        given: "Admin create charger"
        ChargingSystem chargingSystem = ChargingSystemFactory.create(name, plcAddres, networkSubmask)

        when: "Admin add a new charger at given position"
        chargingSystem.assignCharger(chargerPosition)

        then: "Cannot add charger position to large"
        thrown IllegalArgumentException

        where:
        name << ["Test system name"]
        plcAddres << ["192.168.0.1"]
        networkSubmask << ["255.255.255.0"]
        chargerPosition << [ChargingSystemConstraint.AMOUNT_CHARGERS_IN_SYSTEM + 10]
    }
}

package app.web.utb.chargingSystem.domain

import app.web.utb.chargingSystem.domain.event.*
import spock.lang.Specification

class ChargingSystemUpdateDataCmdTest extends Specification {
    def "Update system data"() {
        given: "Management/Admin create system and add three chargers"
        ChargingSystem chargingSystem = ChargingSystemTestFactory.createWithChargers()
        chargingSystem.assignCart(1, 1)
        chargingSystem.flushEvents()
        when: "System read data from plc controller"
        List<ChargerDataFromController> dataFromController = new ArrayList<ChargerDataFromController>()
        dataFromController.add(new ChargerDataFromController(1, 1, 1, 1.23f, false, false))
        chargingSystem.updateData(dataFromController)

        then: "System not detect problems"
        chargingSystem.getDomainEvents().size() == 0
    }

    def "Detect charging interruption"() {
        given: "Management/Admin create system with three chargers"
        ChargingSystem chargingSystem = ChargingSystemTestFactory.createWithChargers()

        when: "Update data with info about charging interruption"
        var updateData = List.of(new ChargerDataFromController(1, 1, 1, 1.23f, false, true))
        chargingSystem.updateData(updateData)

        then: "System publish event about charging"
        var domainEvents = chargingSystem.getDomainEvents()
        var eventOptional = domainEvents.stream()
                .filter(event -> event instanceof ChargingInterrupted)
                .map(event -> (ChargingInterrupted) event)
                .findAny()

        eventOptional.isPresent()
    }

    def "Check permission for cart - success"() {
        given: "Management/Admin create system with three chargers"
        ChargingSystem chargingSystem = ChargingSystemTestFactory.createWithChargers()
        and: "Assign cart to charger"
        chargingSystem.assignCart(1, 1)
        chargingSystem.flushEvents()

        when: "Data update with cart id"
        var updateData = List.of(new ChargerDataFromController(1, 1, 1, 1.23f, false, false))
        chargingSystem.updateData(updateData)

        then: "Nothing happen"
        chargingSystem.getDomainEvents().size() == 0
    }

    def "Check permission for cart - cart not match"() {
        given: "Management/Admin create system with three chargers"
        ChargingSystem chargingSystem = ChargingSystemTestFactory.createWithChargers()
        chargingSystem.flushEvents()
        when: "Update data but cart not pass to charger"
        var updateData = List.of(new ChargerDataFromController(1, 1, 100, 1.23f, false, false))
        chargingSystem.updateData(updateData)
        then: "System publish event about error"
        var events = chargingSystem.getDomainEvents()
        boolean cartNotMatchToCharger = events.stream()
                .anyMatch(event -> event instanceof CartNotMatchedToCharger)

        cartNotMatchToCharger
    }

    def "Start charging"() {
        given: "Management/Admin create system with three chargers"
        ChargingSystem chargingSystem = ChargingSystemTestFactory.createWithChargers()
        and: "Assign cart to charger"
        chargingSystem.assignCart(1, 1)
        chargingSystem.flushEvents()

        when: "Data update with start charging"
        var updateData = List.of(new ChargerDataFromController(1, 1, 1, 1.23f, true, false))
        chargingSystem.updateData(updateData)

        then: "System detect charging"
        var events = chargingSystem.getDomainEvents()
        boolean chargingStarted = events.stream()
                .anyMatch(event -> event instanceof ChargingStarted)

        chargingStarted
    }

    def "Stop charging"() {
        given: "Management/Admin create system with three chargers"
        ChargingSystem chargingSystem = ChargingSystemTestFactory.createWithChargers()
        and: "Assign cart to charger"
        chargingSystem.assignCart(1, 1)
        and: "Start charging"
        var updateForStartCharging = List.of(new ChargerDataFromController(1, 1, 1, 1.23f, true, false))
        chargingSystem.updateData(updateForStartCharging)
        chargingSystem.flushEvents()

        when: "Get data with stop charging"
        var updateData = List.of(new ChargerDataFromController(1, 1, 1, 32.1f, false, false))
        chargingSystem.updateData(updateData)

        then: "Detect stop charging situation"
        var events = chargingSystem.getDomainEvents()
        events.stream().anyMatch(event -> event instanceof ChargingStopped)
    }

    def "Charging without cart"() {
        given: "Management/Admin create system with three chargers"
        ChargingSystem chargingSystem = ChargingSystemTestFactory.createWithChargers()
        and: "Assign cart to charger"
        chargingSystem.assignCart(1, 1)
        when: "Start charging with empty cart"
        var updateForStartCharging = List.of(new ChargerDataFromController(1, 1, 0, 1.23f, true, false))
        chargingSystem.updateData(updateForStartCharging)
        then: "Detect - charging without cart alarm"
        var events = chargingSystem.getDomainEvents()
        events.stream().anyMatch(event -> event instanceof ChargingWithoutCartDetected)
    }

}

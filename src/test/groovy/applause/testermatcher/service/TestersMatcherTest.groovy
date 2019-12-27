package applause.testermatcher.service

import applause.testersmatcher.dto.Match
import applause.testersmatcher.model.Bug
import applause.testersmatcher.model.Device
import applause.testersmatcher.model.Tester
import applause.testersmatcher.model.TesterDeviceJunction
import applause.testersmatcher.repository.BugRepository
import applause.testersmatcher.repository.DeviceRepository
import applause.testersmatcher.repository.TesterDeviceJunctionRepository
import applause.testersmatcher.repository.TesterRepository
import applause.testersmatcher.service.TestersMatcher
import applause.testersmatcher.util.DateTimeParser
import java.time.LocalDateTime
import java.util.stream.Collectors
import spock.lang.Specification

class TestersMatcherTest extends Specification {
    def 'finds match for ALL countries and one device'() {
        given:
            def bugRepository = new BugRepository("bugs.csv", new DateTimeParser())
            def deviceRepository = new DeviceRepository("devices.csv", new DateTimeParser())
            def testerRepository = new TesterRepository("testers.csv", new DateTimeParser())
            def testerDeviceJunctionRepository = new TesterDeviceJunctionRepository("tester_device.csv", new DateTimeParser())
            def testersMatcher = new TestersMatcher(
                    bugRepository,
                    deviceRepository,
                    testerRepository,
                    testerDeviceJunctionRepository
            )
            def tester1 = testerRepository.save(
                    new Tester(1, "User1", "User1", "US", LocalDateTime.now().minusDays(1))
            )
            def tester2 = testerRepository.save(
                    new Tester(2, "User2", "User2", "GB", LocalDateTime.now().minusDays(2))
            )
            def iPhone4Name = "iPhone 4"
            def iPhone4 = deviceRepository.save(new Device(1, iPhone4Name))
            testerDeviceJunctionRepository.save(Set.of(
                    new TesterDeviceJunction(tester1.id, iPhone4.id),
                    new TesterDeviceJunction(tester2.id, iPhone4.id)
            ))
            def bugs = new ArrayList<Bug>()
            for (int i = 1; i < 5; i++) {
                bugs << bugRepository.save(new Bug(i, iPhone4.id, tester1.id))
            }
            for (int i = 5; i < 15; i++) {
                bugs << bugRepository.save(new Bug(i, iPhone4.id, tester2.id))
            }
        when:
            def matches = testersMatcher.getMatch(Set.of(TesterRepository.ALL_OPTION), Set.of(iPhone4Name))
        then:
            matches.size() == 2
            matches.contains(
                    new Match(
                            tester1,
                            bugs.stream().filter { bug -> bug.testerId == tester1.id }.collect(Collectors.toSet())
                    )
            )
            matches.contains(
                    new Match(
                            tester2,
                            bugs.stream().filter { bug -> bug.testerId == tester2.id }.collect(Collectors.toSet())
                    )
            )
    }

    def 'finds match for ALL countries and two devices'() {
        given:
            def bugRepository = new BugRepository("bugs.csv", new DateTimeParser())
            def deviceRepository = new DeviceRepository("devices.csv", new DateTimeParser())
            def testerRepository = new TesterRepository("testers.csv", new DateTimeParser())
            def testerDeviceJunctionRepository = new TesterDeviceJunctionRepository("tester_device.csv", new DateTimeParser())
            def testersMatcher = new TestersMatcher(
                    bugRepository,
                    deviceRepository,
                    testerRepository,
                    testerDeviceJunctionRepository
            )
            def tester1 = testerRepository.save(
                    new Tester(1, "User1", "User1", "US", LocalDateTime.now().minusDays(1))
            )
            def tester2 = testerRepository.save(
                    new Tester(2, "User2", "User2", "GB", LocalDateTime.now().minusDays(2))
            )
            def iPhone4Name = "iPhone 4"
            def iPhone5Name = "iPhone 5"
            def iPhone4 = deviceRepository.save(new Device(1, iPhone4Name))
            def iPhone5 = deviceRepository.save(new Device(2, iPhone5Name))
            def bugs = new ArrayList<Bug>()
            for (int i = 1; i < 5; i++) {
                bugs << bugRepository.save(new Bug(i, iPhone4.id, tester1.id))
            }
            for (int i = 5; i < 21; i++) {
                bugs << bugRepository.save(new Bug(i, iPhone5.id, tester1.id))
            }
            for (int i = 21; i < 32; i++) {
                bugs << bugRepository.save(new Bug(i, iPhone4.id, tester2.id))
            }
            testerDeviceJunctionRepository.save(Set.of(
                    new TesterDeviceJunction(tester1.id, iPhone4.id),
                    new TesterDeviceJunction(tester1.id, iPhone5.id),
                    new TesterDeviceJunction(tester2.id, iPhone4.id)
            ))
        when:
            def matches = testersMatcher.getMatch(Set.of(TesterRepository.ALL_OPTION), Set.of(iPhone4Name, iPhone5Name))
        then:
            matches.size() == 2
            matches.contains(
                    new Match(
                            tester1,
                            bugs.stream().filter { bug -> bug.testerId == tester1.id }.collect(Collectors.toSet())
                    )
            )
            matches.contains(
                    new Match(
                            tester2,
                            bugs.stream().filter { bug -> bug.testerId == tester2.id }.collect(Collectors.toSet())
                    )
            )
    }

    def 'finds match for US country and ALL devices'() {
        given:
            def bugRepository = new BugRepository("bugs.csv", new DateTimeParser())
            def deviceRepository = new DeviceRepository("devices.csv", new DateTimeParser())
            def testerRepository = new TesterRepository("testers.csv", new DateTimeParser())
            def testerDeviceJunctionRepository = new TesterDeviceJunctionRepository("tester_device.csv", new DateTimeParser())
            def testersMatcher = new TestersMatcher(
                    bugRepository,
                    deviceRepository,
                    testerRepository,
                    testerDeviceJunctionRepository
            )
            def usCountry = "US"
            def tester1 = testerRepository.save(
                    new Tester(1, "User1", "User1", usCountry, LocalDateTime.now().minusDays(1))
            )
            def tester2 = testerRepository.save(
                    new Tester(2, "User2", "User2", usCountry, LocalDateTime.now().minusDays(2))
            )
            def iPhone6Name = "iPhone 6"
            def iPhone6 = deviceRepository.save(new Device(1, iPhone6Name))
            def bugs = new ArrayList<Bug>()
            for (int i = 1; i < 5; i++) {
                bugs << bugRepository.save(new Bug(i, iPhone6.id, tester1.id))
            }
            testerDeviceJunctionRepository.save(Set.of(
                    new TesterDeviceJunction(tester1.id, iPhone6.id),
                    new TesterDeviceJunction(tester2.id, iPhone6.id)
            ))
        when:
            def matches = testersMatcher.getMatch(Set.of(usCountry), Set.of(iPhone6Name))
        then:
            matches.size() == 1
            matches.contains(
                    new Match(
                            tester1,
                            bugs.stream().filter { bug -> bug.testerId == tester1.id }.collect(Collectors.toSet())
                    )
            )
    }

}

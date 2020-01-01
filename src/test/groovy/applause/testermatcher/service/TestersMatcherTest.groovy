package applause.testermatcher.service

import applause.testersmatcher.Application
import applause.testersmatcher.dto.Match
import applause.testersmatcher.model.Bug
import applause.testersmatcher.model.Device
import applause.testersmatcher.model.Tester
import applause.testersmatcher.repository.BugRepository
import applause.testersmatcher.repository.DeviceRepository
import applause.testersmatcher.repository.TesterRepository
import applause.testersmatcher.service.TestersMatcher
import java.time.LocalDateTime
import java.util.stream.Collectors
import javax.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(classes = [Application.class], webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
class TestersMatcherTest extends Specification {
    @Autowired
    private BugRepository bugRepository

    @Autowired
    private DeviceRepository deviceRepository

    @Autowired
    private TesterRepository testerRepository

    @Autowired
    private TestersMatcher testersMatcher

    @Transactional
    def 'finds match for ALL countries and one device'() {
        given:
            def iPhone4Name = "iPhone 4"
            def iPhone4 = deviceRepository.save(new Device(iPhone4Name))
            def tester1 = testerRepository.save(
                    new Tester("User1", "User1", "US", LocalDateTime.now().minusDays(1), Set.of(iPhone4))
            )
            def tester2 = testerRepository.save(
                    new Tester("User2", "User2", "GB", LocalDateTime.now().minusDays(2), Set.of(iPhone4))
            )
            def bugs = new ArrayList<Bug>()
            for (int i = 1; i < 5; i++) {
                bugs << bugRepository.save(new Bug(Set.of(iPhone4), tester1))
            }
            for (int i = 5; i < 15; i++) {
                bugs << bugRepository.save(new Bug(Set.of(iPhone4), tester2))
            }
        when:
            def matches = testersMatcher.getMatch(Set.of("ALL"), Set.of(iPhone4Name))
        then:
            matches.size() == 2
            matches.contains(
                    new Match(
                            tester1,
                            bugs.stream().filter { bug -> bug.tester == tester1 }.collect(Collectors.toSet())
                    )
            )
            matches.contains(
                    new Match(
                            tester2,
                            bugs.stream().filter { bug -> bug.tester == tester2 }.collect(Collectors.toSet())
                    )
            )
    }

    @Transactional
    def 'finds match for ALL countries and two devices'() {
        given:
            def iPhone4Name = "iPhone 4"
            def iPhone5Name = "iPhone 5"
            def iPhone4 = deviceRepository.save(new Device(iPhone4Name))
            def iPhone5 = deviceRepository.save(new Device(iPhone5Name))
            def tester1 = testerRepository.save(
                    new Tester("User1", "User1", "US", LocalDateTime.now().minusDays(1), Set.of(iPhone4, iPhone5))
            )
            def tester2 = testerRepository.save(
                    new Tester("User2", "User2", "GB", LocalDateTime.now().minusDays(2), Set.of(iPhone5))
            )
            def bugs = new ArrayList<Bug>()
            for (int i = 1; i < 5; i++) {
                bugs << bugRepository.save(new Bug(Set.of(iPhone4), tester1))
            }
            for (int i = 5; i < 21; i++) {
                bugs << bugRepository.save(new Bug(Set.of(iPhone5), tester1))
            }
            for (int i = 21; i < 32; i++) {
                bugs << bugRepository.save(new Bug(Set.of(iPhone4), tester2))
            }
        when:
            def matches = testersMatcher.getMatch(Set.of("ALL"), Set.of(iPhone4Name, iPhone5Name))
        then:
            matches.size() == 2
            matches.contains(
                    new Match(
                            tester1,
                            bugs.stream().filter { bug -> bug.tester == tester1 }.collect(Collectors.toSet())
                    )
            )
            matches.contains(
                    new Match(
                            tester2,
                            bugs.stream().filter { bug -> bug.tester == tester2 }.collect(Collectors.toSet())
                    )
            )
    }

    @Transactional
    def 'finds match for US country and ALL devices'() {
        given:
            def iPhone6Name = "iPhone 6"
            def iPhone6 = deviceRepository.save(new Device(iPhone6Name))
            def usCountry = "US"
            def tester1 = testerRepository.save(
                    new Tester("User1", "User1", usCountry, LocalDateTime.now().minusDays(1), Set.of(iPhone6))
            )
            def tester2 = testerRepository.save(
                    new Tester("User2", "User2", usCountry, LocalDateTime.now().minusDays(2), Set.of(iPhone6))
            )
            def bugs = new ArrayList<Bug>()
            for (int i = 1; i < 5; i++) {
                bugs << bugRepository.save(new Bug(Set.of(iPhone6), tester1))
            }
        when:
            def matches = testersMatcher.getMatch(Set.of(usCountry), Set.of(iPhone6Name))
        then:
            matches.size() == 1
            matches.contains(
                    new Match(
                            tester1,
                            bugs.stream().filter { bug -> bug.tester == tester1 }.collect(Collectors.toSet())
                    )
            )
    }

}

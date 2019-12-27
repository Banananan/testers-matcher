package applause.testermatcher.repository

import applause.testersmatcher.model.Tester
import applause.testersmatcher.repository.TesterRepository
import applause.testersmatcher.util.DateTimeParser
import java.time.LocalDateTime
import spock.lang.Specification

class TesterRepositoryTest extends Specification {
    def 'saves new tester'() {
        given:
            def testerRepository = new TesterRepository("testers.csv", new DateTimeParser())
            def tester = new Tester(null, "Tester", "Testing", "US", LocalDateTime.now())
        when:
            def newTester = testerRepository.save(tester)
        then:
            newTester.id != null
            def testerFromDb = testerRepository.getById(newTester.id)
            testerFromDb != null
            testerFromDb == newTester
    }

    def 'updates tester'() {
        given:
            def testerRepository = new TesterRepository("testers.csv", new DateTimeParser())
            def tester = new Tester(null, "Tester", "Testing", "US", LocalDateTime.now())
            def newTester = testerRepository.save(tester)
        when:
            tester = new Tester(newTester.id, "TesterUpdated", "TestingUpdated", "GB", LocalDateTime.now())
            def updatedTester = testerRepository.update(tester)
        then:
            updatedTester.id == newTester.id
            def updatedTesterFromDb = testerRepository.getById(newTester.id)
            updatedTesterFromDb != null
            updatedTesterFromDb == tester
    }

    def 'deletes tester'() {
        given:
            def testerRepository = new TesterRepository("testers.csv", new DateTimeParser())
            def tester = new Tester(null, "Tester", "Testing", "US", LocalDateTime.now())
            def newTester = testerRepository.save(tester)
        when:
            testerRepository.delete(newTester)
        then:
            def testerFromDb = testerRepository.getById(newTester.id)
            testerFromDb == null
    }
}

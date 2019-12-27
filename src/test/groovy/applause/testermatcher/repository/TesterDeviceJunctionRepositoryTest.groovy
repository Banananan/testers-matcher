package applause.testermatcher.repository

import applause.testersmatcher.model.TesterDeviceJunction
import applause.testersmatcher.repository.TesterDeviceJunctionRepository
import applause.testersmatcher.util.DateTimeParser
import spock.lang.Specification

class TesterDeviceJunctionRepositoryTest extends Specification {
    def 'saves new tester device junction'() {
        given:
            def testerDeviceJunctionRepository = new TesterDeviceJunctionRepository(
                    "tester_device.csv",
                    new DateTimeParser()
            )
            def testerDeviceJunction = new TesterDeviceJunction(1, 1)
        when:
            def newTesterDeviceJunction = testerDeviceJunctionRepository.save(testerDeviceJunction)
        then:
            newTesterDeviceJunction.testerId != null
            def testerDeviceJunctionFromDb = testerDeviceJunctionRepository.getByTesterId(
                    newTesterDeviceJunction.testerId
            )
            testerDeviceJunctionFromDb != null
            testerDeviceJunctionFromDb.contains(newTesterDeviceJunction)
    }

    def 'updates tester device junction'() {
        given:
            def testerDeviceJunctionRepository = new TesterDeviceJunctionRepository(
                    "tester_device.csv",
                    new DateTimeParser()
            )
            def testerDeviceJunction = new TesterDeviceJunction(1, 1)
            def newTesterDeviceJunction = testerDeviceJunctionRepository.save(testerDeviceJunction)
        when:
            testerDeviceJunction = new TesterDeviceJunction(1, 2)
            def updatedTesterDeviceJunction = testerDeviceJunctionRepository.update(testerDeviceJunction)
        then:
            updatedTesterDeviceJunction.testerId == newTesterDeviceJunction.testerId
            def updatedTesterDeviceJunctionsFromDb = testerDeviceJunctionRepository.getByTesterId(
                    newTesterDeviceJunction.testerId
            )
            updatedTesterDeviceJunctionsFromDb != null
            updatedTesterDeviceJunctionsFromDb.contains(testerDeviceJunction)
    }

    def 'updates tester device junctions'() {
        given:
            def testerDeviceJunctionRepository = new TesterDeviceJunctionRepository(
                    "tester_device.csv",
                    new DateTimeParser()
            )
            def testerDeviceJunctions = new HashSet<>(Set.of(
                    new TesterDeviceJunction(1, 1),
                    new TesterDeviceJunction(1, 2)
            ))
            def newTesterDeviceJunctions = testerDeviceJunctionRepository.save(testerDeviceJunctions)
        when:
            testerDeviceJunctions = new HashSet<>(Set.of(
                    new TesterDeviceJunction(1, 3),
                    new TesterDeviceJunction(1, 4)
            ))
            def updatedTesterDeviceJunctions = testerDeviceJunctionRepository.update(testerDeviceJunctions)
        then:
            def updatedTesterDeviceJunctionsFromDb = testerDeviceJunctionRepository.getByTesterId(
                    newTesterDeviceJunctions.stream().findFirst().get().testerId
            )
            updatedTesterDeviceJunctionsFromDb != null
            updatedTesterDeviceJunctionsFromDb.containsAll(updatedTesterDeviceJunctions)
    }

    def 'deletes tester device junction'() {
        given:
            def testerDeviceJunctionRepository = new TesterDeviceJunctionRepository(
                    "tester_device.csv",
                    new DateTimeParser()
            )
            def testerDeviceJunction = new TesterDeviceJunction(1, 1)
            def newTesterDeviceJunction = testerDeviceJunctionRepository.save(testerDeviceJunction)
        when:
            testerDeviceJunctionRepository.delete(newTesterDeviceJunction)
        then:
            def testerDeviceJunctionsFromDb = testerDeviceJunctionRepository.getByTesterId(
                    newTesterDeviceJunction.testerId
            )
            testerDeviceJunctionsFromDb == null
    }
}

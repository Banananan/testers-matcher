package applause.testermatcher.repository

import applause.testersmatcher.model.Bug
import applause.testersmatcher.repository.BugRepository
import applause.testersmatcher.util.DateTimeParser
import spock.lang.Shared
import spock.lang.Specification

class BugRepositoryTest extends Specification {
    def 'saves new bug'() {
        given:
            def bugRepository = new BugRepository("bugs.csv", new DateTimeParser())
            def bug = new Bug(null, 1, 1)
        when:
            def newBug = bugRepository.save(bug)
        then:
            newBug.id != null
            def bugFromDb = bugRepository.getById(newBug.id)
            bugFromDb != null
            bugFromDb == newBug
    }

    def 'updates bug'() {
        given:
            def bugRepository = new BugRepository("bugs.csv", new DateTimeParser())
            def bug = new Bug(null, 1, 1)
            def newBug = bugRepository.save(bug)
        when:
            bug = new Bug(newBug.id, 2, 2)
            def updatedBug = bugRepository.update(bug)
        then:
            updatedBug.id == newBug.id
            def updatedBugFromDb = bugRepository.getById(newBug.id)
            updatedBugFromDb != null
            updatedBugFromDb == bug
    }

    def 'deletes bug'() {
        given:
            def bugRepository = new BugRepository("bugs.csv", new DateTimeParser())
            def bug = new Bug(null, 1, 1)
            def newBug = bugRepository.save(bug)
        when:
            bugRepository.delete(newBug)
        then:
            def bugFromDb = bugRepository.getById(newBug.id)
            bugFromDb == null
    }
}

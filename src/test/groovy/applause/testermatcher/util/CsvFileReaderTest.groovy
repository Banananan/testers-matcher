package applause.testermatcher.util

import applause.testermatcher.model.Test
import applause.testersmatcher.util.CsvFileReader
import java.util.stream.Collectors
import spock.lang.Shared
import spock.lang.Specification

class CsvFileReaderTest extends Specification {
    @Shared
    def csvFileReader = new CsvFileReader<Test>() {
        @Override
        protected List<Test> mapToModel(List<List<String>> tests) {
            return tests.stream().map { test -> new Test(test.get(0), test.get(1)) }
                    .collect(Collectors.toUnmodifiableList());
        }
    }

    def "reads file"() {
        when:
            def file = csvFileReader.readFile("testFile.csv")
        then:
            file.get(0).testColumn1 == "testValue1Row1"
            file.get(1).testColumn2 == "testValue2Row2"
    }
}

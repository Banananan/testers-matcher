package applause.testermatcher.util


import applause.testersmatcher.util.DateTimeParser
import java.time.temporal.ChronoField
import spock.lang.Shared
import spock.lang.Specification

class DateTimeParserTest extends Specification {
    @Shared
    def dateTimeParser = new DateTimeParser()

    def 'parses valid date time'() {
        given:
            def dateTime = "2019-12-26 23:18:15"
        when:
            def parsedDateTime = dateTimeParser.parse(dateTime)
        then:
            parsedDateTime.get(ChronoField.YEAR) == 2019
            parsedDateTime.get(ChronoField.MONTH_OF_YEAR) == 12
            parsedDateTime.get(ChronoField.DAY_OF_MONTH) == 26
            parsedDateTime.get(ChronoField.HOUR_OF_DAY) == 23
            parsedDateTime.get(ChronoField.MINUTE_OF_HOUR) == 18
            parsedDateTime.get(ChronoField.SECOND_OF_MINUTE) == 15
    }

    def 'throws exception when invalid format'() {
        given:
            def dateTime = "23:18:15 2019-DEC-26"
        when:
            dateTimeParser.parse(dateTime)
        then:
            thrown(IllegalArgumentException)
    }
}

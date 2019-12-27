package applause.testersmatcher.util;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeParser {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern(PATTERN);

    public TemporalAccessor parse(String text) {
        try {
            return FORMAT.parse(text);
        } catch (Exception exception) {
            throw new IllegalArgumentException(
                    "Date " + text + " is written in invalid format; expected " + PATTERN);
        }
    }
}

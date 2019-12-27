package applause.testersmatcher;

import applause.testersmatcher.configuration.ApplicationConfiguration;
import applause.testersmatcher.dto.Match;
import applause.testersmatcher.service.TestersMatcher;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private static final ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();

    private static final TestersMatcher testersMatcher;

    static {
        testersMatcher = applicationConfiguration.getTestersMatcher();
    }

    public static void main(String... arguments) {
        try {
            validateArguments(arguments);
            var matches = testersMatcher.getMatch(parseArgument(arguments[0]), parseArgument(arguments[1]));
            System.out.println(matches.stream().collect(Collectors.toMap(
                    match -> match.getTester().getName(),
                    match -> match.getBugs().size(),
                    Integer::sum
            )));
        } catch (Exception exception) {
            logger.log(Level.SEVERE, exception.getLocalizedMessage());
        }
    }

    private static void validateArguments(String[] arguments) {
        if (arguments == null || arguments.length != 2) {
            throw new IllegalArgumentException("Invalid arguments passed - must be two arguments: " +
                    "first - country name (GB, US or ALL for any); second - device name (iPhone 4, Galasy S3 or ALL " +
                    "for any)");
        }
    }

    private static Set<String> parseArgument(String argument) {
        return Set.of(argument.split(","));
    }
}

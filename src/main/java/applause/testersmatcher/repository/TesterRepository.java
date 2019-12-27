package applause.testersmatcher.repository;

import applause.testersmatcher.model.Device;
import applause.testersmatcher.model.Tester;
import applause.testersmatcher.util.CsvFileReader;
import applause.testersmatcher.util.DateTimeParser;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TesterRepository extends AbstractRepository<Long, Tester> {
    public static final String ALL_OPTION = "ALL";

    private final DateTimeParser dateTimeParser;

    private final Map<Long, Tester> values;

    public TesterRepository(String fileName, DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
        CsvFileReader<Tester> csvFileReader = new CsvFileReader<>() {
            @Override
            protected List<Tester> mapToModel(List<List<String>> testers) {
                return testers.stream().map(tester -> new Tester(
                        Long.parseLong(tester.get(0)),
                        tester.get(1),
                        tester.get(2),
                        tester.get(3),
                        LocalDateTime.from(dateTimeParser.parse(tester.get(4)))
                )).collect(Collectors.toUnmodifiableList());
            }
        };
        values = csvFileReader.readFile(fileName).stream().collect(Collectors.toMap(Tester::getId, Function.identity()));
    }

    @Override
    public Tester getById(Long id) {
        return values.get(id);
    }

    @Override
    protected Tester saveInternal(Tester element) {
        values.put(element.getId(), element);
        return element;
    }

    @Override
    public Tester update(Tester element) {
        return save(element);
    }

    @Override
    public void delete(Tester element) {
        values.remove(element.getId());
    }

    public List<Tester> getByCountry(Set<String> country) {
        if (country == null) {
            return List.of();
        }
        if (country.contains(ALL_OPTION)) {
            return List.copyOf(values.values());
        }
        return values.values().stream().filter(tester -> country.contains(tester.getCountry()))
                .collect(Collectors.toList());
    }

    @Override
    protected Long calculateId(Tester element) {
        return (long) values.size();
    }
}

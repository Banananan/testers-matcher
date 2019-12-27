package applause.testersmatcher.repository;

import applause.testersmatcher.model.Bug;
import applause.testersmatcher.model.TesterDeviceJunction;
import applause.testersmatcher.util.CsvFileReader;
import applause.testersmatcher.util.DateTimeParser;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BugRepository extends AbstractRepository<Long, Bug> {
    private final Map<Long, Bug> values;

    private final DateTimeParser dateTimeParser;

    public BugRepository(String fileName, DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
        CsvFileReader<Bug> csvFileReader = new CsvFileReader<>() {
            @Override
            protected List<Bug> mapToModel(List<List<String>> bugs) {
                return bugs.stream().map(bug -> new Bug(
                        Long.parseLong(bug.get(0)),
                        Long.parseLong(bug.get(1)),
                        Long.parseLong(bug.get(2))
                )).collect(Collectors.toUnmodifiableList());
            }
        };
        values = csvFileReader.readFile(fileName).stream()
                .collect(Collectors.toConcurrentMap(Bug::getId, Function.identity()));
    }

    @Override
    public Bug getById(Long id) {
        return values.get(id);
    }

    @Override
    protected Bug saveInternal(Bug element) {
        values.put(element.getId(), element);
        return element;
    }

    @Override
    public Bug update(Bug element) {
        return save(element);
    }

    @Override
    public void delete(Bug element) {
        values.remove(element.getId());
    }

    public List<Bug> getByDeviceIdAndTesterId(Set<Long> devices, Set<Long> testers) {
        if (devices == null || testers == null) {
            return List.of();
        }
        return values.values().stream()
                .filter(bug -> devices.contains(bug.getDeviceId()) && testers.contains(bug.getTesterId()))
                .collect(Collectors.toList());
    }

    public List<Bug> getByDeviceIdAndTesterId(Set<TesterDeviceJunction> testerDeviceJunctions) {
        return getByDeviceIdAndTesterId(
                testerDeviceJunctions.stream().map(TesterDeviceJunction::getDeviceId).collect(Collectors.toSet()),
                testerDeviceJunctions.stream().map(TesterDeviceJunction::getTesterId).collect(Collectors.toSet())
        );
    }

    @Override
    protected Long calculateId(Bug element) {
        long id;
        do {
            id = values.size() + 1;
        } while (values.containsKey(id));
        return id;
    }
}

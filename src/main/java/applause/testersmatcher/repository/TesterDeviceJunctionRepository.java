package applause.testersmatcher.repository;

import applause.testersmatcher.model.TesterDeviceJunction;
import applause.testersmatcher.util.CsvFileReader;
import applause.testersmatcher.util.DateTimeParser;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TesterDeviceJunctionRepository {
    private final DateTimeParser dateTimeParser;

    private final Map<Long, Set<TesterDeviceJunction>> testersToDevices;

    private final Map<Long, Set<TesterDeviceJunction>> devicesToTesters;

    public TesterDeviceJunctionRepository(String fileName, DateTimeParser dateTimeParser) {
        CsvFileReader<TesterDeviceJunction> csvFileReader = new CsvFileReader<>() {
            @Override
            protected List<TesterDeviceJunction> mapToModel(List<List<String>> junctions) {
                return junctions.stream().map(junction -> new TesterDeviceJunction(
                        Long.parseLong(junction.get(0)),
                        Long.parseLong(junction.get(1))
                )).collect(Collectors.toUnmodifiableList());
            }
        };
        testersToDevices = csvFileReader.readFile(fileName).stream().collect(Collectors.groupingByConcurrent(
                TesterDeviceJunction::getTesterId,
                Collectors.toSet()
        ));
        devicesToTesters = csvFileReader.readFile(fileName).stream().collect(Collectors.groupingByConcurrent(
                TesterDeviceJunction::getDeviceId,
                Collectors.toSet()
        ));
        this.dateTimeParser = dateTimeParser;
    }

    public synchronized TesterDeviceJunction save(TesterDeviceJunction element) {
        testersToDevices.computeIfAbsent(element.getTesterId(), junction -> new HashSet<>()).add(element);
        devicesToTesters.computeIfAbsent(element.getDeviceId(), junction -> new HashSet<>()).add(element);
        return element;
    }

    public synchronized Set<TesterDeviceJunction> save(Set<TesterDeviceJunction> elements) {
        elements.forEach(this::save);
        return elements;
    }

    public TesterDeviceJunction update(TesterDeviceJunction element) {
        testersToDevices.computeIfPresent(element.getTesterId(), (key, junctions) -> {
            junctions.add(element);
            return junctions;
        });
        testersToDevices.computeIfAbsent(element.getTesterId(), junction -> new HashSet<>()).add(element);
        devicesToTesters.computeIfPresent(element.getDeviceId(), (key, junctions) -> {
            junctions.add(element);
            return junctions;
        });
        devicesToTesters.computeIfAbsent(element.getDeviceId(), junction -> new HashSet<>()).add(element);
        return element;
    }

    public Set<TesterDeviceJunction> update(Set<TesterDeviceJunction> elements) {
        elements.forEach(this::update);
        return elements;
    }

    public synchronized void delete(TesterDeviceJunction element) {
        testersToDevices.computeIfPresent(element.getTesterId(), (key, oldTesterDeviceJunctions) -> {
            var removedTesterToDevice = oldTesterDeviceJunctions.remove(element);
            var newTesterToDeviceJunctions = testersToDevices.get(element.getTesterId());
            if (removedTesterToDevice && newTesterToDeviceJunctions.isEmpty()) {
                testersToDevices.remove(key);
            }
            return newTesterToDeviceJunctions;
        });
        devicesToTesters.computeIfPresent(element.getTesterId(), (key, oldDeviceToTesterJunctions) -> {
            var removedDeviceToTester = oldDeviceToTesterJunctions.remove(element);
            var newDeviceToTesterJunctions = devicesToTesters.get(element.getDeviceId());
            if (removedDeviceToTester && newDeviceToTesterJunctions.isEmpty()) {
                testersToDevices.remove(key);
            }
            return newDeviceToTesterJunctions;
        });
    }

    public synchronized void delete(Set<TesterDeviceJunction> elements) {
        elements.forEach(this::delete);
    }

    public Set<TesterDeviceJunction> getByTesterId(Long testerId) {
        return testersToDevices.get(testerId);
    }

    public Set<TesterDeviceJunction> getByDeviceId(Long deviceId) {
        return devicesToTesters.get(deviceId);
    }

    public Set<TesterDeviceJunction> getByTesterIdAndDeviceId(Set<Long> testers, Set<Long> devices) {
        if (testers == null || devices == null) {
            return Set.of();
        }
        return testersToDevices.entrySet().stream()
                .filter(testerToDevice -> {
                            var intersection = new HashSet<>(devices);
                            intersection.retainAll(testerToDevice.getValue().stream()
                                    .map(TesterDeviceJunction::getDeviceId).collect(Collectors.toSet()));
                            return testers.contains(testerToDevice.getKey()) && !intersection.isEmpty();
                        }
                )
                .flatMap(testerToDevice -> testerToDevice.getValue().stream())
                .filter(testerToDevice -> devices.contains(testerToDevice.getDeviceId()))
                .collect(Collectors.toSet());
    }
}

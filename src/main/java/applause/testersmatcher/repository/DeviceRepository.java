package applause.testersmatcher.repository;

import applause.testersmatcher.model.Bug;
import applause.testersmatcher.model.Device;
import applause.testersmatcher.util.CsvFileReader;
import applause.testersmatcher.util.DateTimeParser;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DeviceRepository extends AbstractRepository<Long, Device> {
    public static final String ALL_OPTION = "ALL";

    private final DateTimeParser dateTimeParser;

    private final Map<Long, Device> values;

    public DeviceRepository(String fileName, DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
        CsvFileReader<Device> csvFileReader = new CsvFileReader<>() {
            @Override
            protected List<Device> mapToModel(List<List<String>> devices) {
                return devices.stream().map(device -> new Device(
                        Long.parseLong(device.get(0)),
                        device.get(1)
                )).collect(Collectors.toUnmodifiableList());
            }
        };
        values = csvFileReader.readFile(fileName).stream()
                .collect(Collectors.toConcurrentMap(Device::getId, Function.identity()));
    }

    @Override
    public Device getById(Long id) {
        return values.get(id);
    }

    @Override
    protected Device saveInternal(Device element) {
        values.put(element.getId(), element);
        return element;
    }

    @Override
    public Device update(Device element) {
        return save(element);
    }

    @Override
    public void delete(Device element) {
        values.remove(element.getId());
    }

    public List<Device> getByDescription(Set<String> description) {
        if (description == null) {
            return List.of();
        }
        if (description.contains(ALL_OPTION)) {
            return List.copyOf(values.values());
        }
        return values.values().stream().filter(device -> description.contains(device.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    protected Long calculateId(Device element) {
        return (long) values.size();
    }
}

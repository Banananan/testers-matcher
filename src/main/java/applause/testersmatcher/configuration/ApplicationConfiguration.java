package applause.testersmatcher.configuration;

import applause.testersmatcher.repository.BugRepository;
import applause.testersmatcher.repository.DeviceRepository;
import applause.testersmatcher.repository.TesterDeviceJunctionRepository;
import applause.testersmatcher.repository.TesterRepository;
import applause.testersmatcher.service.TestersMatcher;
import applause.testersmatcher.util.DateTimeParser;

public class ApplicationConfiguration {
    private final DateTimeParser dateTimeParser = new DateTimeParser();

    private final BugRepository bugRepository = new BugRepository("bugs.csv", dateTimeParser);

    private final DeviceRepository deviceRepository = new DeviceRepository("devices.csv", dateTimeParser);

    private final TesterRepository testerRepository = new TesterRepository("testers.csv", dateTimeParser);

    private final TesterDeviceJunctionRepository testerDeviceJunctionRepository =
            new TesterDeviceJunctionRepository("tester_device.csv", dateTimeParser);

    private final TestersMatcher testersMatcher = new TestersMatcher(
            bugRepository,
            deviceRepository,
            testerRepository,
            testerDeviceJunctionRepository
    );

    public BugRepository getBugRepository() {
        return bugRepository;
    }

    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }

    public TesterRepository getTesterRepository() {
        return testerRepository;
    }

    public TesterDeviceJunctionRepository getTesterDeviceJunctionRepository() {
        return testerDeviceJunctionRepository;
    }

    public TestersMatcher getTestersMatcher() {
        return testersMatcher;
    }
}

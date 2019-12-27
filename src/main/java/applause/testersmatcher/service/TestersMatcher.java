package applause.testersmatcher.service;

import applause.testersmatcher.model.Bug;
import applause.testersmatcher.model.Device;
import applause.testersmatcher.dto.Match;
import applause.testersmatcher.model.Tester;
import applause.testersmatcher.repository.BugRepository;
import applause.testersmatcher.repository.DeviceRepository;
import applause.testersmatcher.repository.TesterDeviceJunctionRepository;
import applause.testersmatcher.repository.TesterRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestersMatcher {
    private final BugRepository bugRepository;

    private final DeviceRepository deviceRepository;

    private final TesterRepository testerRepository;

    private final TesterDeviceJunctionRepository testerDeviceJunctionRepository;

    public TestersMatcher(
            BugRepository bugRepository,
            DeviceRepository deviceRepository,
            TesterRepository testerRepository,
            TesterDeviceJunctionRepository testerDeviceJunctionRepository
    ) {
        this.bugRepository = bugRepository;
        this.deviceRepository = deviceRepository;
        this.testerRepository = testerRepository;
        this.testerDeviceJunctionRepository = testerDeviceJunctionRepository;
    }

    public List<Match> getMatch(Set<String> countries, Set<String> deviceDescriptions) {
        var testers = testerRepository.getByCountry(countries);
        var devices = deviceRepository.getByDescription(deviceDescriptions);
        var testersToDevices = testerDeviceJunctionRepository.getByTesterIdAndDeviceId(
                testers.stream().map(Tester::getId).collect(Collectors.toSet()),
                devices.stream().map(Device::getId).collect(Collectors.toSet())
        );
        var bugs = bugRepository.getByDeviceIdAndTesterId(Set.copyOf(testersToDevices));
        var bugsByTesters = bugs.stream().collect(Collectors.groupingBy(Bug::getTesterId));
        return bugsByTesters.entrySet().stream().map(bugsByTester -> new Match(
                testers.stream().filter(tester -> bugsByTester.getKey().equals(tester.getId())).findFirst().get(),
                Set.copyOf(bugsByTester.getValue())
        )).collect(Collectors.toList());
    }
}

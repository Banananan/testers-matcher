package applause.testersmatcher.service;

import applause.testersmatcher.dto.Match;
import applause.testersmatcher.model.Bug;
import applause.testersmatcher.model.Device;
import applause.testersmatcher.model.Tester;
import applause.testersmatcher.repository.BugRepository;
import applause.testersmatcher.repository.DeviceRepository;
import applause.testersmatcher.repository.TesterRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestersMatcher {
    private final BugRepository bugRepository;

    private final DeviceRepository deviceRepository;

    private final TesterRepository testerRepository;

    @Autowired
    public TestersMatcher(BugRepository bugRepository, DeviceRepository deviceRepository, TesterRepository testerRepository) {
        this.bugRepository = bugRepository;
        this.deviceRepository = deviceRepository;
        this.testerRepository = testerRepository;
    }

    @Transactional
    public List<Match> getMatch(Set<String> countries, Set<String> deviceDescriptions) {
        List<Tester> testers;
        if (countries.contains("ALL")) {
            testers = StreamSupport.stream(testerRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());
        } else {
            testers = testerRepository.getByCountryIn(countries);
        }
        List<Device> devices;
        if (deviceDescriptions.contains("ALL")) {
            devices = StreamSupport.stream(deviceRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());
        } else {
            devices = deviceRepository.getByDescriptionIn(deviceDescriptions);
        }
        var bugs = bugRepository.getByDevicesInAndTesterIn(new HashSet<>(devices), new HashSet<>(testers));
        var bugsByTesters = bugs.stream().collect(Collectors.groupingBy(Bug::getTester));
        return bugsByTesters.entrySet().stream().map(bugsByTester -> new Match(
                testers.stream().filter(tester -> bugsByTester.getKey().equals(tester)).findFirst().get(),
                Set.copyOf(bugsByTester.getValue())
        )).collect(Collectors.toList());
    }
}

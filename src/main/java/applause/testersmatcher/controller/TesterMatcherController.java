package applause.testersmatcher.controller;

import applause.testersmatcher.service.TestersMatcher;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/match")
class TesterMatcherController {
    private final TestersMatcher testersMatcher;

    @Autowired
    TesterMatcherController(TestersMatcher testersMatcher) {
        this.testersMatcher = testersMatcher;
    }

    @GetMapping
    Map<String, Integer> getMatch(@RequestParam Set<String> countries, @RequestParam Set<String> devices) {
        var matches = testersMatcher.getMatch(countries, devices);
        return matches.stream().collect(Collectors.toMap(
                match -> match.getTester().getName(),
                match -> match.getBugs().size(),
                Integer::sum
        ));
    }
}

package applause.testersmatcher.controller;

import applause.testersmatcher.dto.Match;
import applause.testersmatcher.service.TestersMatcher;
import java.util.HashSet;
import java.util.Set;
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
    Set<Match> getMatch(@RequestParam Set<String> countries, @RequestParam Set<String> devices) {
        var matches = testersMatcher.getMatch(countries, devices);
        return new HashSet<>(matches);
    }
}

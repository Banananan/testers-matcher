package applause.testersmatcher.dto;

import applause.testersmatcher.model.Bug;
import applause.testersmatcher.model.Tester;
import java.util.Objects;
import java.util.Set;

public final class Match {
    private final Tester tester;

    private final Set<Bug> bugs;

    public Match(Tester tester, Set<Bug> bugs) {
        this.tester = tester;
        this.bugs = bugs;
    }

    public Tester getTester() {
        return tester;
    }

    public Set<Bug> getBugs() {
        return bugs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return tester.equals(match.tester) &&
                Objects.equals(bugs, match.bugs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tester, bugs);
    }

    @Override
    public String toString() {
        return "Match{" +
                "tester=" + tester +
                ", bugs=" + bugs +
                '}';
    }
}

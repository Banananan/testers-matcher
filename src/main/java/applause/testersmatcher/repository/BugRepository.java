package applause.testersmatcher.repository;

import applause.testersmatcher.model.Bug;
import applause.testersmatcher.model.Device;
import applause.testersmatcher.model.Tester;
import java.util.List;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends CrudRepository<Bug, Long> {
    List<Bug> getByDevicesInAndTesterIn(Set<Device> devices, Set<Tester> testers);
}

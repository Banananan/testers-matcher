package applause.testersmatcher.repository;

import applause.testersmatcher.model.Device;
import java.util.List;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
    List<Device> getByDescriptionIn(Set<String> description);
}

package applause.testersmatcher.repository;

import applause.testersmatcher.model.Tester;
import java.util.List;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesterRepository extends CrudRepository<Tester, Long> {
    List<Tester> getByCountryIn(Set<String> country);
}

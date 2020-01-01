package applause.testersmatcher.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "applause.testersmatcher.repository")
@EnableTransactionManagement
public class ApplicationConfiguration {
}

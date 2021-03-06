package img.imaginary.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@PropertySource("daoH2.properties")
public class TestDataSourceConfig {

    @Value("${db.class}")
    private String className;

    @Value("${db.url}")
    private String url;

    @Value("${db.login}")
    private String login;

    @Value("${db.password}")
    private String password;

    @Bean(destroyMethod = "close")
    @Primary
    public BasicDataSource basicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(className);
        dataSource.setUrl(url);
        dataSource.setUsername(login);
        dataSource.setPassword(password);
        Resource schemeDefaultResource = new ClassPathResource("/testTablesCreation.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(schemeDefaultResource) {
        };
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        return dataSource;
    }
    
    @Bean(destroyMethod = "close")
    public BasicDataSource noConnectionDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        return dataSource;
    }
}
package img.imaginary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DataSourceConfig.class,
         DaoConfig.class,
         ServiceConfig.class,
         AspectConfig.class,
         MvcConfig.class})
public class UniversityStarterConfig {

}

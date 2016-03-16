package org.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({SpringDatabaseConfig.class})
@PropertySource("classpath:/org/app/properties/application.properties")
public class SpringApplicationConfig
{
}

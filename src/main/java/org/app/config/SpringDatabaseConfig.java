package org.app.config;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories
@PropertySource("classpath:/org/app/properties/database.properties")
public class SpringDatabaseConfig extends Neo4jConfiguration
{
	private static final String DEFAULT_URL = "http://localhost:7474";

	@Autowired
	private Environment env;

	@Override
	public Neo4jServer neo4jServer()
	{
		String url = env.getProperty("neo4j.url") != null ? env.getProperty("neo4j.url") : DEFAULT_URL;
		String username = env.getProperty("neo4j.username");
		String password = env.getProperty("neo4j.password");
		return new RemoteServer(url, username, password);
	}

	@Override
	public SessionFactory getSessionFactory()
	{
		return new SessionFactory("org.app.domain");
	}

	@Override
	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Session getSession() throws Exception
	{
		return super.getSession();
	}
}

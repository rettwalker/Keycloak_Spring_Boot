package com.walker.loginservice.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * Created by rettwalker on 2/9/17.
 */
@Configuration
public class DatabaseConfig implements EnvironmentAware {

    private Environment environment;



    @Bean
    public DataSource getDataSource() {
        final String DATASOURCE_ROOT = "spring.datasource.";
        DataSource dataSource = DataSourceBuilder.create()
                .url(environment.getProperty(DATASOURCE_ROOT +"url"))
                .username(environment.getProperty(DATASOURCE_ROOT+"username"))
                .password(environment.getProperty(DATASOURCE_ROOT+"password"))
                .build();
        return dataSource;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(getDataSource());
        return tokenRepository;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;

    }
}

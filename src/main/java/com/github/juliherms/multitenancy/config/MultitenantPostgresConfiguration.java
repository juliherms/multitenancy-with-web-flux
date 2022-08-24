package com.github.juliherms.multitenancy.config;

import com.github.juliherms.multitenancy.routing.PostgresTenantConnectionFactory;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;
import static java.util.Map.entry;

/**
 * Classe responsavel por configurar a convivencia com mais de um banco de dados
 */
@Configuration
@EnableTransactionManagement
@EnableR2dbcRepositories
public class MultitenantPostgresConfiguration extends AbstractR2dbcConfiguration {

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        var connectionFactory = postgresConnectionFactory();

        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }

    private AbstractRoutingConnectionFactory postgresConnectionFactory() {
        var routingConnectionFactory = new PostgresTenantConnectionFactory();

        routingConnectionFactory.setLenientFallback(false);
        routingConnectionFactory.setTargetConnectionFactories(tenants());

        return routingConnectionFactory;
    }

    /**
     * Inicializa e retorna as instancias do postgre
     * @return
     */
    private Map<String, ConnectionFactory> tenants() {
        return Map.ofEntries(
                entry("TenantOne", tenantOne()),
                entry("TenantTwo", tenantTwo())
        );
    }

    /**
     * Metodo responsavel por configurar a primeira instancia do postgres
     * @return
     */
    private ConnectionFactory tenantOne() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("sampledb")
                .username("philipp")
                .password("test_pwd").build());
    }

    /**
     * Metodo resonsavel por configurar a segunda instancia do postgre
     * @return
     */
    private ConnectionFactory tenantTwo() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)
                .database("sampledb2")
                .username("philipp")
                .password("test_pwd").build());
    }
}

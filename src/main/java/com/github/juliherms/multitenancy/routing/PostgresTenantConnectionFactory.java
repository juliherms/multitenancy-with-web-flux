package com.github.juliherms.multitenancy.routing;

import com.github.juliherms.multitenancy.constants.ApplicationConstants;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory;
import reactor.core.publisher.Mono;

import static com.github.juliherms.multitenancy.utils.ReactorUtils.errorIfEmpty;


public class PostgresTenantConnectionFactory extends AbstractRoutingConnectionFactory {

    static final class PostgresqlConnectionFactoryMetadata implements ConnectionFactoryMetadata {

        static final PostgresqlConnectionFactoryMetadata INSTANCE = new PostgresqlConnectionFactoryMetadata();

        public static final String NAME = "PostgreSQL";

        private PostgresqlConnectionFactoryMetadata() {
        }

        @Override
        public String getName() {
            return NAME;
        }
    }

    /**
     * Metodo responsavel por verificar se o tenant key consta no contexto da requisicao
     * @return
     */
    @Override
    protected Mono<Object> determineCurrentLookupKey() {
        return Mono
                .deferContextual(Mono::just)
                .filter(it -> it.hasKey(ApplicationConstants.TenantKey))
                .map(it -> it.get(ApplicationConstants.TenantKey))
                .transform(m -> errorIfEmpty(m, () -> new RuntimeException(String.format("ContextView does not contain the Lookup Key '%s'", ApplicationConstants.TenantKey))));
    }

    @Override
    public ConnectionFactoryMetadata getMetadata() {
        // If we don't override this method, it will try to determine the Dialect from the default
        // ConnectionFactory. This is a problem, because you don't want a "Default ConnectionFactory"
        // when you cannot resolve the Tenant.
        //
        // That's why we explicitly return a fixed PostgresqlConnectionFactoryMetadata. This class
        // is also defined within the r2dbc library, but it isn't exposed to public.
        return PostgresqlConnectionFactoryMetadata.INSTANCE;
    }
}

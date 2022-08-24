package com.github.juliherms.multitenancy.filters;

import com.github.juliherms.multitenancy.constants.ApplicationConstants;
import com.github.juliherms.multitenancy.constants.HeaderNames;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Filtro responsavel por interceptar a requisicao e verificar qual o tentant setado
 * Dado que tenha tentant, será injetado no contexto para que o repositorio consiga direcionar
 * para a instnncia do postgre correta
 */
@Component
public class TenantIdWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             WebFilterChain webFilterChain) {

        var headerValues = serverWebExchange.getRequest().getHeaders().get(HeaderNames.TenantId);

        if(headerValues == null || headerValues.size() == 0) {
            return webFilterChain.filter(serverWebExchange);
        }

        // Make a guess. Just get the first Key, if we have multiple Tenant Headers:
        String tenantKey = headerValues.get(0);

        return webFilterChain
                .filter(serverWebExchange)
                .contextWrite(ctx -> ctx.put(ApplicationConstants.TenantKey, tenantKey));
    }
}

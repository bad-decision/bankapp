package ru.azmeev.bank.transfer.configuration;

import brave.Tracing;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Profile("!test")
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/actuator/**").permitAll()
                                .anyRequest().authenticated())
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }

    @Bean
    @LoadBalanced
    @Profile("!k8s")
    public RestClient.Builder loadBalancedRestClientBuilder(OAuth2AuthorizedClientManager authorizedClientManager,
                                                            Tracing tracing) {
        return createBuilderWithInterceptors(authorizedClientManager, tracing);
    }

    @Bean
    @Profile("k8s")
    public RestClient.Builder restClientBuilder(OAuth2AuthorizedClientManager authorizedClientManager,
                                                Tracing tracing) {
        return createBuilderWithInterceptors(authorizedClientManager, tracing);
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .refreshToken()
                        .build();
        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    private RestClient.Builder createBuilderWithInterceptors(OAuth2AuthorizedClientManager authorizedClientManager,
                                                             Tracing tracing) {
        OAuth2ClientHttpRequestInterceptor oauthInterceptor =
                new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);

        Propagation<String> propagation = tracing.propagation();
        TraceContext.Injector<org.springframework.http.HttpHeaders> injector =
                propagation.injector(HttpHeaders::add);

        ClientHttpRequestInterceptor zipkinInterceptor = (request, body, execution) -> {
            TraceContext traceContext = tracing.currentTraceContext().get();
            if (traceContext != null) {
                injector.inject(traceContext, request.getHeaders());
            }
            return execution.execute(request, body);
        };

        return RestClient.builder()
                .requestInterceptor(oauthInterceptor)
                .requestInterceptor(zipkinInterceptor);
    }
}

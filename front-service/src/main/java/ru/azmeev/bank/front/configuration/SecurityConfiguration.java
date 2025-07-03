package ru.azmeev.bank.front.configuration;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import ru.azmeev.bank.front.monitoring.LoginMetrics;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginMetrics loginMetrics) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/signup", "/actuator/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .defaultSuccessUrl("/main", true)
                        .successHandler((request, response, authentication) -> {
                            String username = authentication.getName();
                            loginMetrics.incrementSuccess(username);
                            response.sendRedirect("/main");
                        })
                        .failureHandler((request, response, exception) -> {
                            String username = request.getParameter("username");
                            loginMetrics.incrementFailure(username != null ? username : "unknown");
                            response.sendRedirect("/login?error");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
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

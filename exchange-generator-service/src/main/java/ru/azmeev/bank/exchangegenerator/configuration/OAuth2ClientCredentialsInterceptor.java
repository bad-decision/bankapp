//package ru.azmeev.bank.exchangegenerator.configuration;
//
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class OAuth2ClientCredentialsInterceptor implements ClientHttpRequestInterceptor {
//
//    private final OAuth2AuthorizedClientManager clientManager;
//    private static final String PRINCIPAL_NAME = "service-account-exchange-service";
//
//    public OAuth2ClientCredentialsInterceptor(OAuth2AuthorizedClientManager clientManager) {
//        this.clientManager = clientManager;
//    }
//
//    @Override
//    public ClientHttpResponse intercept(
//            HttpRequest request,
//            byte[] body,
//            ClientHttpRequestExecution execution) throws IOException {
//
//        OAuth2AuthorizedClient authorizedClient = clientManager.authorize(
//                OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
//                        .principal(PRINCIPAL_NAME)
//                        .build());
//
//        if (authorizedClient == null) {
//            throw new IllegalStateException("Failed to obtain OAuth2 token");
//        }
//        request.getHeaders().setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
//        return execution.execute(request, body);
//    }
//}

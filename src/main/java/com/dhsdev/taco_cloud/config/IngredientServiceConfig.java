package com.dhsdev.taco_cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;

import com.dhsdev.taco_cloud.repositories.IngredientService;
import com.dhsdev.taco_cloud.services.RestIngredientService;

@Configuration
public class IngredientServiceConfig {

    @Bean
    @RequestScope
    public IngredientService ingredientService(
            OAuth2AuthorizedClientService clientService) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            if ("taco-admin-client".equals(clientRegistrationId)) {
                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                        clientRegistrationId, oauthToken.getName());
                if (client != null && client.getAccessToken() != null) {
                    String accessToken = client.getAccessToken().getTokenValue();
                    return new RestIngredientService(accessToken);
                }
            }
        }

        // Fallback sin token → para evitar error en el constructor
        return new RestIngredientService(null);
    }
}

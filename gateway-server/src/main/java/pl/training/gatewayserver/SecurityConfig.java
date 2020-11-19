package pl.training.gatewayserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public OidcClientInitiatedServerLogoutSuccessHandler logoutSuccessHandler(@Value("${postLogoutRedirectUrl}") String postLogoutRedirectUrl, ReactiveClientRegistrationRepository clientRegistrationRepository) {
		OidcClientInitiatedServerLogoutSuccessHandler logoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
		logoutSuccessHandler.setPostLogoutRedirectUri(postLogoutRedirectUrl);
		return logoutSuccessHandler;
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository,
															OidcClientInitiatedServerLogoutSuccessHandler logoutSuccessHandler) {
		http.oauth2Login();
		http.logout(logout -> logout.logoutSuccessHandler(new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));
		http.authorizeExchange().anyExchange().authenticated();
		http.logout().logoutSuccessHandler(logoutSuccessHandler);
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler());
		http.csrf().disable();
		http.headers().disable();
		return http.build();
	}

	@Bean
	public OidcReactiveOAuth2UserService customUserService() {
		return new OAuth2UserService();
	}

}

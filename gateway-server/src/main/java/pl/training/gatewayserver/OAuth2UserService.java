package pl.training.gatewayserver;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

public class OAuth2UserService extends OidcReactiveOAuth2UserService {

    public static final String GROUPS_KEY = "groups";
    public static final String ROLE_PREFIX = "ROLE_";

    @Override
    public Mono<OidcUser> loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcIdToken idToken = userRequest.getIdToken();
        Mono<OidcUser> userMono = super.loadUser(userRequest);
        return userMono.map(oidcUser -> {
            final Set<GrantedAuthority> augmentedAuthorities = new HashSet<>(oidcUser.getAuthorities());
            idToken.getClaimAsStringList(GROUPS_KEY).stream()
                    .map(String::toUpperCase)
                    .map(roleName -> ROLE_PREFIX + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .forEach(augmentedAuthorities::add);
            return new CustomOidcUser(augmentedAuthorities, idToken, oidcUser);
        });
    }

}

package pl.training.gatewayserver;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;

public class CustomOidcUser extends DefaultOidcUser {

    private String userName;

    public CustomOidcUser(Collection<? extends GrantedAuthority> authorities, OidcIdToken idToken, OidcUser oidcUser) {
        super(authorities, idToken, oidcUser.getUserInfo());
        userName = oidcUser.getName();
    }

    @Override
    public String getName() {
        return userName;
    }

}

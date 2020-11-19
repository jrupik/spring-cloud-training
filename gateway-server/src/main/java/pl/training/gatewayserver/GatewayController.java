package pl.training.gatewayserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class GatewayController {

    public static final String ANONYMOUS_USER = "anonymous";

    @GetMapping("api/users/active")
    public Map<String, String> activeUser(Principal principal) {
        String userName = ANONYMOUS_USER;
        if (principal != null) {
            userName = principal.getName();
        }
        return Map.of("username", userName);
    }

}

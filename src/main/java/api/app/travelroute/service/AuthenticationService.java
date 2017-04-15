package api.app.travelroute.service;

import api.app.travelroute.model.UserAuth;
import api.base.service.AuthenticationFacadeService;
import org.springframework.stereotype.Component;

/**
 * Created by cc on 2017/4/12.
 */
@Component
public class AuthenticationService extends AuthenticationFacadeService {

    public UserAuth getUserAuth() {

        return ((UserAuth)getPrincipal());
    }
}

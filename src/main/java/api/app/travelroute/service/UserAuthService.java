package api.app.travelroute.service;

import api.app.travelroute.entity.UserEntity;
import api.app.travelroute.model.UserAuth;
import api.app.travelroute.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cc on 2017/4/10.
 */
@Service
@Transactional
public class UserAuthService  implements UserDetailsService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(UserAuthService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("[use auth]: " + username);
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info("[use auth]: username not found");
            throw new UsernameNotFoundException("username not found");
        }
        logger.info("[use auth]: " + user.toString());

        return new UserAuth(user);
    }

}

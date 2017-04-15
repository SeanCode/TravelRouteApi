package api.app.travelroute.model;

import api.app.travelroute.entity.UserEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Created by cc on 2017/4/10.
 */
public class UserAuth  extends User {

    private UserEntity userEntity;

    public UserAuth(UserEntity userEntity) {
        super(userEntity.getUsername(), userEntity.getPassword(), AuthorityUtils.createAuthorityList(String.valueOf(userEntity.getRole())));
        this.userEntity = userEntity;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public Long getUserId() {
        return userEntity.getId();
    }
}

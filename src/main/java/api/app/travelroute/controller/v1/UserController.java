package api.app.travelroute.controller.v1;

import api.app.travelroute.entity.Role;
import api.app.travelroute.entity.UserEntity;
import api.app.travelroute.service.AuthenticationService;
import api.app.travelroute.service.UserService;
import api.base.common.DataResponse;
import api.base.common.OutputEntityJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by cc on 2017/4/10.
 */
@RestController("V1.UserController")
@RequestMapping("/api/v1/user")
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse register(@RequestParam("username") String username,
                                 @RequestParam("name") String name,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("password") String password,
                                 @RequestParam(value = "avatar", required = false, defaultValue = "") String avatar,
                                 @RequestParam(value = "email", required = false, defaultValue = "") String email) throws InvalidKeySpecException, NoSuchAlgorithmException {

        return DataResponse.create().put("user", userService.addUser(username, name, phone, password, avatar, email, "", Role.ROLE_USER));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add-admin", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse register(@RequestParam("username") String username,
                                 @RequestParam("name") String name,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("password") String password,
                                 @RequestParam(value = "intro", required = false, defaultValue = "") String intro,
                                 @RequestParam(value = "avatar", required = false, defaultValue = "") String avatar,
                                 @RequestParam(value = "role", required = false, defaultValue = "ROLE_ADMIN") Role role,
                                 @RequestParam(value = "email", required = false, defaultValue = "") String email) throws InvalidKeySpecException, NoSuchAlgorithmException {

        return DataResponse.create().put("user", userService.addUser(username, name, phone, password, avatar, email, intro, role));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse adminList(
            @Spec(path = "role", spec = Equal.class) Specification<UserEntity> specification,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return DataResponse.create().putPage("user_list", userService.getUserList(specification, pageable));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse login() {

        return DataResponse.create().put("user", authenticationService.getUserAuth().getUser());
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @JsonView(OutputEntityJsonView.Detail.class)
    public DataResponse update(@RequestParam("username") String username,
                               @RequestParam(value = "password", required = false, defaultValue = "null") String password,
                               @RequestParam(value = "phone", required = false, defaultValue = "null") String phone,
                               @RequestParam(value = "email", required = false, defaultValue = "null") String email,
                               @RequestParam(value = "avatar", required = false, defaultValue = "null") String avatar,
                               @RequestParam(value = "intro", required = false, defaultValue = "null") String intro) throws InvalidKeySpecException, NoSuchAlgorithmException {

        return DataResponse.create().put("user", userService.update(username, password, phone, email, avatar, intro));
    }

}

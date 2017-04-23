package api.app.travelroute.service;

import api.app.travelroute.entity.Role;
import api.app.travelroute.entity.UserEntity;
import api.app.travelroute.repository.UserRepository;
import api.base.common.PasswordHash;
import api.base.common.Util;
import api.base.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by cc on 2017/4/10.
 */
@Service
@Transactional
public class UserService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepo;

    public UserEntity addUser(String username, String name, String phone, String password, String avatar, String email, String intro, Role role) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(phone) || StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            throw new InvalidParamsException("params can not be blank");
        }
        UserEntity userEntity = userRepo.findByUsername(username);
        if (userEntity != null) {
            throw new ExistsException("username exists.");
        }
        userEntity = userRepo.findByPhone(phone);
        if (userEntity != null) {
            throw new ExistsException("phone has been taken.");
        }
        userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setPhone(phone);
        userEntity.setUsername(username);
        userEntity.setEmail(email);
        userEntity.setAvatar(avatar);
        userEntity.setPassword(PasswordHash.createHash(password));
        userEntity.setIntro(intro);
        userEntity.setRole(role);
        userEntity.setCreateTime(Util.time());
        userEntity.setUpdateTime(userEntity.getCreateTime());

        return userRepo.save(userEntity);
    }

    public Page<UserEntity> getUserList(Specification<UserEntity> specification, Pageable pageable) {

        return userRepo.findAll(specification, pageable);
    }

    public UserEntity validateByUsername(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        UserEntity user = userRepo.findByUsername(username);
        if (user == null || !PasswordHash.validatePassword(password, user.getPassword())) {
            throw new LoginFailException();
        }
        return user;
    }

    public UserEntity findByUsername(String username) {
        UserEntity user = userRepo.findByUsername(username);
        if (user == null) {
            throw new NotExistsException();
        }
        return user;
    }

    public void delete(String username) {
        UserEntity user = userRepo.findByUsername(username);
        if (user == null) {
            throw new NotExistsException();
        }
        userRepo.delete(user);
    }

    public UserEntity update(String username, String name, String password, String phone, String email, String avatar, String intro) throws InvalidKeySpecException, NoSuchAlgorithmException {
        UserEntity userEntity = userRepo.findByUsername(username);
        if (userEntity == null) {
            throw new NotExistsException();
        }
        boolean hasChanged = false;
        if (StringUtils.isNotBlank(password)) {
            hasChanged = true;
            userEntity.setPassword(PasswordHash.createHash(password));
        }
        if (StringUtils.isNotBlank(name)) {
            hasChanged = true;
            userEntity.setName(name);
        }
        if (StringUtils.isNotBlank(phone)) {
            hasChanged = true;
            UserEntity other = userRepo.findByPhone(phone);
            if (other != null) {
                throw new ExistsException("此电话号码已被占用");
            }
            userEntity.setPhone(phone);
        }
        if (StringUtils.isNotBlank(email)) {
            hasChanged = true;
            userEntity.setEmail(email);
        }
        if (StringUtils.isNotBlank(avatar)) {
            hasChanged = true;
            userEntity.setAvatar(avatar);
        }
        if (StringUtils.isNotBlank(intro)) {
            hasChanged = true;
            userEntity.setIntro(intro);
        }
        if (hasChanged) {
            userEntity.setUpdateTime(Util.time());
            return userRepo.save(userEntity);
        }
        throw new InvalidParamsException();
    }
}

package com.maxvi.lifeblog.service.user.impl;

import com.maxvi.lifeblog.model.ProfileEntity;
import com.maxvi.lifeblog.model.UserEntity;
import com.maxvi.lifeblog.model.UserRole;
import com.maxvi.lifeblog.repository.ProfileRepository;
import com.maxvi.lifeblog.repository.UserRepository;
import com.maxvi.lifeblog.service.dto.CurrentUserDto;
import com.maxvi.lifeblog.service.exception.UserAlreadyExistsException;
import com.maxvi.lifeblog.service.security.SecurityService;
import com.maxvi.lifeblog.service.user.UserService;
import com.maxvi.lifeblog.service.user.dto.SignupDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service("userService")
public class UserServiceImpl implements UserService
{
    private static final String USER_EXISTS_MESSAGE = "User already exists";

    @Resource(name = "securityService")
    private SecurityService securityService;

    @Resource(name = "conversionService")
    private ConversionService conversionService;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "profileRepository")
    private ProfileRepository profileRepository;

    @Resource(name = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    public CurrentUserDto getCurrentUser()
    {
        UserEntity userEntity = securityService.getCurrentUserEntity();
        if (userEntity != null)
        {
            return conversionService.convert(userEntity, CurrentUserDto.class);
        }
        return null;
    }

    @Override
    public boolean isCurrentUserOrAdmin(Long userId)
    {
        UserEntity currentUser = securityService.getCurrentUserEntity();
        return currentUser != null && (currentUser.getId().equals(userId) || UserRole.ADMIN.toString().equals(currentUser.getRole()));
    }

    @Override
    @Transactional
    public CurrentUserDto signUp(SignupDto signupDto) throws UserAlreadyExistsException
    {
        UserEntity existingUserEntity = userRepository.findByLogin(signupDto.getEmail());
        if (existingUserEntity != null)
        {
            throw new UserAlreadyExistsException(USER_EXISTS_MESSAGE);
        }
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setActivated(true);
        newUserEntity.setLogin(signupDto.getEmail());
        newUserEntity.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        newUserEntity.setRole(UserRole.USER.name());
        newUserEntity = userRepository.save(newUserEntity);

        ProfileEntity profileEntity = createDummyProfileEntity(newUserEntity);
        profileRepository.save(profileEntity);

        CurrentUserDto currentUserDto = new CurrentUserDto();
        currentUserDto.setActivated(newUserEntity.isActivated());
        currentUserDto.setLogin(newUserEntity.getLogin());
        currentUserDto.setId(newUserEntity.getId());
        currentUserDto.setRole(newUserEntity.getRole());
        return currentUserDto;
    }

    private ProfileEntity createDummyProfileEntity(UserEntity userEntity)
    {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setBirthday(null);
        profileEntity.setDescription(null);
        profileEntity.setFirstName(null);
        profileEntity.setLastName(null);
        profileEntity.setPhoneNumber(null);
        profileEntity.setUser(userEntity);
        profileEntity.setBlogPostEntities(new ArrayList<>());
        return profileEntity;
    }
}

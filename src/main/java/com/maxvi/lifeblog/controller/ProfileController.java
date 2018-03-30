package com.maxvi.lifeblog.controller;

import com.maxvi.lifeblog.service.user.dto.ProfileDto;
import com.maxvi.lifeblog.service.user.ProfileService;
import com.maxvi.lifeblog.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/profile")
public class ProfileController
{
    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "profileService")
    private ProfileService profileService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable("userId") Long userId)
    {
        boolean canGetProfile = userService.isCurrentUserOrAdmin(userId);
        if (canGetProfile)
        {
            return ResponseEntity.ok(profileService.getProfileById(userId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("userId") @NotNull Long userId,
                                                    @RequestBody @Valid ProfileDto profileDto)
    {
        boolean canUpdateProfile = userService.isCurrentUserOrAdmin(userId);
        if (canUpdateProfile)
        {
            ProfileDto updatedProfile = profileService.updateProfileById(userId, profileDto);
            return ResponseEntity.ok(updatedProfile);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}

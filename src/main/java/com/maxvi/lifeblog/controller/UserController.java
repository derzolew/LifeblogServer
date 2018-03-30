package com.maxvi.lifeblog.controller;

import com.maxvi.lifeblog.service.dto.CurrentUserDto;
import com.maxvi.lifeblog.service.exception.UserAlreadyExistsException;
import com.maxvi.lifeblog.service.user.UserService;
import com.maxvi.lifeblog.service.user.dto.SignupDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/current")
    public ResponseEntity<CurrentUserDto> getCurrentUser()
    {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PostMapping("/signup")
    public ResponseEntity<CurrentUserDto> signUp(@RequestBody SignupDto signupDto)
    {
        try
        {
            CurrentUserDto currentUserDto = userService.signUp(signupDto);
            return ResponseEntity.ok(currentUserDto);
        } catch (UserAlreadyExistsException e)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}

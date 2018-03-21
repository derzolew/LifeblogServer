package com.maxvi.lifeblog.controller;

import com.maxvi.lifeblog.service.dto.CurrentUserDto;
import com.maxvi.lifeblog.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

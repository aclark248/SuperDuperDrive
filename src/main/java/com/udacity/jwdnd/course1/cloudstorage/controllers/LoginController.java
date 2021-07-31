package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import com.udacity.jwdnd.course1.cloudstorage.services.SDDUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final SDDUserService sddUserService;


    public LoginController(SDDUserService sddUserService){
        this.sddUserService = sddUserService;

    }

    @GetMapping()
    public String loginView() {
        var y = 12;
        SDDUser user = sddUserService.getUser("aclark2");
        var x = 12;
        return "login";
    }

}

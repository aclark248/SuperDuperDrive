package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import com.udacity.jwdnd.course1.cloudstorage.services.SDDUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private SDDUserService sddUserService;

    public SignUpController(SDDUserService sddUserService)
    {
        this.sddUserService = sddUserService;
    }

    @GetMapping()
    public String signUpView(Model model) {
        model.addAttribute("userForm", new SDDUser());
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute SDDUser user){
        var doesUserExists = sddUserService.getUser(user.getUsername());
        if (doesUserExists == null)
        {
            var createUserResult = sddUserService.createUser(user);
        }

        return "login";
    }


}

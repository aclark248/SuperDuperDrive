package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private FileService fileService;


    public HomeController(FileService fileService)
    {
        this.fileService = fileService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model)
    {
        var userName = authentication.getPrincipal().toString();
        var allUserFiles = fileService.getAllFiles(userName);
        model.addAttribute("allUserFiles", allUserFiles);

        return "home";
    }



}


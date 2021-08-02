package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file-upload")
public class FilesController {

    private FileService fileService;

    public FilesController(FileService fileService)
    {
        this.fileService = fileService;
    }

    @PostMapping()
    public String fileUpload(Authentication authentication, MultipartFile fileUpload) throws IOException {
        var userName = authentication.getPrincipal().toString();
        fileService.addFile(fileUpload, userName);
        var x = 12;
        return "home";
    }



}

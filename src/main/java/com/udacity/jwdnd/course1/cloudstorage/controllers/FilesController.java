package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FilesController {

    private FileService fileService;

    public FilesController(FileService fileService)
    {
        this.fileService = fileService;
    }

    @PostMapping("/file-upload")
    public String fileUpload(Authentication authentication, MultipartFile fileUpload) throws IOException {
        var userName = authentication.getPrincipal().toString();
        fileService.addFile(fileUpload, userName);
        var x = 12;
        return "home";
    }

    @GetMapping("/get-file/{id}")
    public @ResponseBody byte[] getFile(@PathVariable("id") int fileId)
    {
        var file = fileService.getFile(fileId).getFiledata();
        return file;
    }

    @GetMapping("/delete-file/{id}")
    public String deleteFile(@PathVariable("id") int fileid)
    {
        fileService.deleteFile(fileid);
        return "home";
    }



}

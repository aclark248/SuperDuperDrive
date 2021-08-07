package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FilesController {

    private FileService fileService;

    public FilesController(FileService fileService)
    {
        this.fileService = fileService;
    }

    @GetMapping("/get-file/{id}")
    public @ResponseBody byte[] getFile(@PathVariable("id") int fileId)
    {
        var file = fileService.getFile(fileId).getFiledata();
        return file;
    }

    @PostMapping("/file-upload")
    public String fileUpload(Authentication authentication, MultipartFile fileUpload, RedirectAttributes redirectAttributes) throws IOException {
        var userName = authentication.getPrincipal().toString();
        var result = fileService.addFile(fileUpload, userName);
        if(result == 1) {
            redirectAttributes.addFlashAttribute("success", "The file was successfully deleted.");
        } else if (result == 0)
        {
            redirectAttributes.addFlashAttribute("success", "The file was successfully deleted.");
        }
        var x = 12;
        return "redirect:/home";
    }

    @GetMapping("/delete-file/{id}")
    public String deleteFile(@PathVariable("id") int fileid, Model model, RedirectAttributes redirectAttributes)
    {
        var result = fileService.deleteFile(fileid);
        if(result == 1) {
            redirectAttributes.addFlashAttribute("success", "The file was successfully deleted.");
        } else if (result == 0)
        {
            redirectAttributes.addFlashAttribute("success", "The file was successfully deleted.");
        }
        return "redirect:/home";
    }



}

package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
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

    private NotesService notesService;


    public HomeController(FileService fileService, NotesService notesService)
    {
        this.fileService = fileService;
        this.notesService = notesService;
    }

    @GetMapping("/home")
    public String home(Authentication authentication, Model model)
    {
        var userName = authentication.getPrincipal().toString();

        //get alluser files
        var allUserFiles = fileService.getAllFiles(userName);
        model.addAttribute("allUserFiles", allUserFiles);
        model.addAttribute("noteForm", new Note());

        //get all notes
        var allUserNotes = notesService.getUserNotes(userName);
        model.addAttribute("allUserNotes", allUserNotes);

        return "home";
    }




}


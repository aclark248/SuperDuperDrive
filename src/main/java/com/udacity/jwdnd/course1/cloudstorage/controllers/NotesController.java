package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

@Controller
public class NotesController {

    private NotesService notesService;

    public NotesController(NotesService notesService)
    {
        this.notesService = notesService;
    }

    //create-note
    @PostMapping("/create-note")
    public String createNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirectAttributes) {
        var userName = authentication.getPrincipal().toString();
        int result = -1;
        var updateNote = StringUtils.isEmpty(String.valueOf(note.getNoteid()));
        if (updateNote) {
            result = notesService.updateNote(note);
        }
        else {
            result = notesService.addNote(note, userName);
        }
        if(result == 1) {
            redirectAttributes.addFlashAttribute("success", "The note was successfully created.");
        } else if (result == 0)
        {
            redirectAttributes.addFlashAttribute("success", "An error occurred while creating the note.");
        }

        return "redirect:/home";
    }

    @GetMapping("/delete-note/{id}")
    public String deleteNote(@PathVariable("id") int noteid, Model model, RedirectAttributes redirectAttributes)
    {
        var result = notesService.deleteNote(noteid);
        if(result == 1) {
            redirectAttributes.addFlashAttribute("success", "The note was successfully deleted.");
        } else if (result == 0)
        {
            redirectAttributes.addFlashAttribute("success", "An error occured while deleting the note.");
        }
        return "redirect:/home";
    }


}

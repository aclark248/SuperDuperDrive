package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.SDDUserService;
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
public class CredentialsController {

    private final SDDUserService userService;

    private final CredentialService credentialService;

    public CredentialsController(SDDUserService userService, CredentialService credentialService)
    {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/create-credential")
    public String createCredential(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        var userName = authentication.getPrincipal().toString();
        int result = -1;
        var updateCredential = StringUtils.isEmpty(String.valueOf(credential.getCredentialid()));
        if (updateCredential) {
            result = credentialService.updateCredential(credential);
        }
        else {
            result = credentialService.addCredential(credential, userName);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("success", "The credential was successfully created.");
        } else if (result == 0)
        {
            redirectAttributes.addFlashAttribute("success", "An error occurred while creating the credential.");
        }

        return "redirect:/home";
    }

    @GetMapping("/delete-credential/{id}")
    public String deleteCredential(@PathVariable("id") int credentialid, Model model, RedirectAttributes redirectAttributes)
    {
        var result = credentialService.deleteCredential(credentialid);
        if(result == 1) {
            redirectAttributes.addFlashAttribute("success", "The note was successfully deleted.");
        } else if (result == 0)
        {
            redirectAttributes.addFlashAttribute("success", "An error occured while deleting the note.");
        }
        return "redirect:/home";
    }


}

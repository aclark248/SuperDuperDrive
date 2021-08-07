package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

public class NotesController {

    private FileService fileService;

    public NotesController(FileService fileService)
    {
        this.fileService = fileService;
    }


}

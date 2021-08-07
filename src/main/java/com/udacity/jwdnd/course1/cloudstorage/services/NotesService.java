package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import org.springframework.stereotype.Service;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    private SDDUserService sddUserService;

    public NotesService(NotesMapper notesMapper, SDDUserService sddUserService)
    {
        this.sddUserService = sddUserService;
        this.notesMapper = notesMapper;
    }

    public int addNote(Note note, String userName)
    {
        SDDUser sddUser = sddUserService.getUser(userName);
        var userId = sddUser.getUserId();
        var result = notesMapper.insertNote(note, userId);
        return result;
    }

}

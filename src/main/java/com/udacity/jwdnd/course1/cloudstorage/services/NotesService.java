package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import org.springframework.stereotype.Service;

import java.util.List;

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
        var userId = sddUser.getUserid();
        var result = notesMapper.insertNote(note, userId);
        return result;
    }

    public List<Note> getUserNotes(String username)
    {
        SDDUser sddUser = sddUserService.getUser(username);
        var userid = sddUser.getUserid();
        var userNotes = notesMapper.findUserNotes(userid);
        return userNotes;
    }

    public int deleteNote(int noteid)
    {
        var result = notesMapper.deleteNote(noteid);
        return result;
    }

    public int updateNote(Note note)
    {
        var result = notesMapper.updateNote(note);
        return result;
    }

}

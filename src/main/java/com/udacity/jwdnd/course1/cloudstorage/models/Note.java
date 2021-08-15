package com.udacity.jwdnd.course1.cloudstorage.models;

import org.springframework.lang.Nullable;

import java.util.Optional;

public class Note {

    private String noteid;
    private String notetitle;
    private String notedescription;

    public Note(String noteid, String notetitle, String notedescription)
    {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
    }

    public Note() {}


    public String getNoteid() {
        return noteid;
    }

    public void setNoteid(String noteid) {
        this.noteid = noteid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

}

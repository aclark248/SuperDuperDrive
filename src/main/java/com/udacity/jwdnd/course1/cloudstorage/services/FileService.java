package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    private FileMapper fileMapper;

    private SDDUserService sddUserService;

    public FileService(FileMapper fileMapper, SDDUserService sddUserService)
    {
        this.sddUserService = sddUserService;
        this.fileMapper = fileMapper;
    }

    public int addFile(MultipartFile file, String userName) throws IOException {
        File newFile = new File();
        newFile.setContenttype(file.getContentType());
        newFile.setFiledata(file.getBytes());
        newFile.setFilename(file.getOriginalFilename());
        newFile.setFilesize(Long.toString(file.getSize()));
        SDDUser sddUser = sddUserService.getUser(userName);
        var userId = sddUser.getUserId();
        newFile.setUserid(userId);
        var result = fileMapper.insertFile(newFile);
        //var result = fileMapper.insertFile(newFile.getFilename(), newFile.getContenttype(), newFile.getFilesize(), userId, newFile.getFiledata());
        var x = 12;
        return result;
    }


}

package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
        return result;
    }

    public List<File> getAllFiles(String userName)
    {
        SDDUser sddUser = sddUserService.getUser(userName);
        var userId = sddUser.getUserId();
        List<File> allUserFiles = fileMapper.getAllFiles(userId);
        return allUserFiles;
    }

    public File getFile(int fileid)
    {
        File userFile = fileMapper.getFile(fileid);
        return userFile;
    }

    public int deleteFile(int fileid)
    {
        var result = fileMapper.deleteFile(fileid);
        return result;
    }




}

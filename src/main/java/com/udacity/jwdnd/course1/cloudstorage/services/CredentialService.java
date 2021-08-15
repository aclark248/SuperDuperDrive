package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.SDDUserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CredentialService {

    private final SDDUserService sddUserService;

    private final CredentialMapper credentialMapper;

    public CredentialService(SDDUserService sddUserService, CredentialMapper credentialMapper) {
        this.sddUserService = sddUserService;
        this.credentialMapper = credentialMapper;
    }

    public int addCredential(Credential credential, String userName) {
        SDDUser sddUser = sddUserService.getUser(userName);

        Credential newCredential = new Credential();
        newCredential.setUrl(credential.getUrl());
        newCredential.setUserName(credential.getUserName());
        newCredential.setUserid(sddUser.getUserid());
        newCredential.setKey(null);


        var result = credentialMapper.addCredential(newCredential);

        return result;
    }

    public int updateCredential(Credential credential)
    {
        var result = credentialMapper.updateCredential(credential);
        return result;
    }

    public List<Credential> getAllCredentials(String userName)
    {
        SDDUser sddUser = sddUserService.getUser(userName);
        var userId = sddUser.getUserid();
        List<Credential> allUserCredentials = credentialMapper.getAllUserCredentials(userId);
        return allUserCredentials;
    }

    public int deleteCredential(int credentialid)
    {
        var result = credentialMapper.deleteCredential(credentialid);
        return result;
    }

}

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
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final SDDUserService sddUserService;

    private final CredentialMapper credentialMapper;

    private final EncryptionService encryptionService;

    private final String key = "thisismykey";

    public CredentialService(SDDUserService sddUserService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.sddUserService = sddUserService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential, String userName) {
        SDDUser sddUser = sddUserService.getUser(userName);


        Credential newCredential = new Credential();
        newCredential.setUrl(credential.getUrl());
        newCredential.setUsername(credential.getUsername());
        newCredential.setUserid(sddUser.getUserid());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        newCredential.setKey(encodedKey);
        newCredential.setPassword(encryptedPassword);

        var result = credentialMapper.addCredential(newCredential);

        return result;
    }

    public int updateCredential(Credential credential)
    {

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

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

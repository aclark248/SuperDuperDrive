package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.SDDUserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class SDDUserService {

    private final SDDUserMapper sddUserMapper;
    private final HashService hashService;


    public SDDUserService(SDDUserMapper sddUserMapper, HashService hashService) {
        this.sddUserMapper = sddUserMapper;
        this.hashService = hashService;
    }


    public SDDUser getUser(String username) {
        return sddUserMapper.getUser(username);
    }

    public int createUser(SDDUser user)
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        var newSDDUser = new SDDUser();
        newSDDUser.setUserId(null);
        newSDDUser.setUsername(user.getUsername());
        newSDDUser.setPassword(hashedPassword);
        newSDDUser.setFirstName(user.getFirstName());
        newSDDUser.setLastName(user.getLastName());
        newSDDUser.setSalt(encodedSalt);
        return sddUserMapper.createUser(newSDDUser);
    }

}


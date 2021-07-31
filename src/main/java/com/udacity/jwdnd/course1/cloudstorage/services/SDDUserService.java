package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.SDDUserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import org.springframework.stereotype.Service;

@Service
public class SDDUserService {

    private final SDDUserMapper sddUserMapper;


    public SDDUserService(SDDUserMapper sddUserMapper) {
        this.sddUserMapper = sddUserMapper;
    }


    public SDDUser getUser(String username) {
        return sddUserMapper.getUser(username);
    }

}


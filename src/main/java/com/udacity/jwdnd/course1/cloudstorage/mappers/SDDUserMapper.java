package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SDDUserMapper {

    @Select("SELECT * FROM SDDUSER WHERE USERNAME = #{username}")
    SDDUser getUser(String username);

    @Select("SELECT * FROM SDDUser WHERE userid = #{userId}")
    SDDUser getUserById(Integer userId);

    @Insert("INSERT INTO SDDUser (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(SDDUser user);

}

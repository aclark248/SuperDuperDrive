package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    //int insertFile(@Param("filename") String filename, @Param("contenttype") String contenttype, @Param("filesize") String filesize, @Param("userid") int userid, @Param("filedata") byte[] filedata);
    int insertFile(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getAllFiles(int userid);



}

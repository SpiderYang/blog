package com.shine.community.mapper;

import com.shine.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface userMapper {
    @Insert("insert into user(account_id, name, token,gmt_create, gmt_modified, bio, avatar) " +
            "values(#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified}, #{bio}, #{avatar})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);
}

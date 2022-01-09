package com.shine.community.mapper;

import com.shine.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Hikari
 * @Date: 2022/1/9 17:49
 */

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title, description, gmt_create, gmt_modified, creator, tag) " +
            "values(#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);
}

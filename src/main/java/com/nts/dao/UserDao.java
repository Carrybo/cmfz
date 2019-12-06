package com.nts.dao;

import com.nts.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface UserDao extends Mapper<User>, InsertListMapper<User> {
    Integer countUserRegist(@Param("sex") String sex, @Param("day") Integer day);
}

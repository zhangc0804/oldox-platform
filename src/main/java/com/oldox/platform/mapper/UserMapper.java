package com.oldox.platform.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.oldox.platform.entity.User;

@Mapper
public interface UserMapper {
	
	@Select("select * from user where id= #{id}")
	public User queryById(@Param("id") Long id);
	
	@Select("select * from user where username= #{username}")
	public User queryByUsername(@Param("username") String username);
	
	@Select("select * from user where username= #{username} and password= #{password}")
	public User queryByUsernamePassword(@Param("username") String username,@Param("password") String password);

}

package com.song.mybatis.mapper;


import com.song.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SmbmsUserMapper {
	//添加用户
    int insert(User record);
    //添加用户方式二
    int insertSelective(User record);
    //登录
    User getLoginUser(@Param("usercode")String usercode,@Param("userpassword")String userpassword);
    //通过条件查询
    List<User> getUserList(@Param("userName")String userName,@Param("userRole") Integer userRole,@Param("currentPageNo")Integer currentPageNo,@Param("pageSize") Integer pageSize);
   //通过添加查询总条数
    int getUserCount(@Param("userName")String userName,@Param("userRole") Integer userRole);
    //通过id查询用户信息
    User selectByPrimaryKey(Integer id);
    //删除
    int deleteUserById(Integer delId);
    //修改用户信息
    int updateByPrimaryKey(User user);
    //修改密码
    int updatePwd(@Param("id")Integer id,@Param("userpassword")String userpassword,@Param("newUserpassword")String newUserpassword);
    
    //通过用户编码查询用户信息
    User getUserByUserCode(String userCode);
	
	
}
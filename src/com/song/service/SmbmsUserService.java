package com.song.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.song.entity.User;

public interface SmbmsUserService {
	int insert(User record);
    //添加用户方式二
    int insertSelective(User record);
    //登录
    User getLoginUser(String usercode,String userpassword);
    //通过条件查询
    List<User> getUserList(String userName,Integer userRole,int currentPageNo,int pageSize);
   //通过添加查询总条数
    int getUserCount(String userName,Integer userRole);
    //通过id查询用户信息
    User selectByPrimaryKey(Integer id);
    //删除
    int deleteUserById(Integer delId);
    //修改用户信息
    int updateByPrimaryKey(User user);
    //修改密码
    int updatePwd(Integer id,String userpassword,String newUserpassword);
    //通过用户编码查询用户信息
    User getUserByUserCode(String userCode);

}

package com.song.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.song.entity.User;
import com.song.mybatis.mapper.SmbmsUserMapper;
import com.song.service.SmbmsUserService;

@Service
public class SmbmsUserServiceImpl implements SmbmsUserService {
	
	@Resource
	private SmbmsUserMapper smbmsUserMapper;

	@Override
	public int insert(User record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getLoginUser(String usercode, String userpassword) {
		// TODO Auto-generated method stub
		return smbmsUserMapper.getLoginUser(usercode, userpassword);
	}

	@Override
	public List<User> getUserList(String userName, Integer userRole,
			int currentPageNo, int pageSize) {
		// TODO Auto-generated method stub
		currentPageNo=(currentPageNo-1)*pageSize;
		return smbmsUserMapper.getUserList(userName, userRole, currentPageNo, pageSize);
	}

	@Override
	public int getUserCount(String userName, Integer userRole) {
		// TODO Auto-generated method stub
		return smbmsUserMapper.getUserCount(userName, userRole);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return smbmsUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteUserById(Integer delId) {
		// TODO Auto-generated method stub
		return smbmsUserMapper.deleteUserById(delId);
	}

	@Override
	public int updateByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return smbmsUserMapper.updateByPrimaryKey(user);
	}

	@Override
	public int updatePwd(Integer id, String userpassword, String newUserpassword) {
		// TODO Auto-generated method stub
		return smbmsUserMapper.updatePwd(id, userpassword, newUserpassword);
	}

	@Override
	public User getUserByUserCode(String userCode) {
		// TODO Auto-generated method stub
		return smbmsUserMapper.getUserByUserCode(userCode);
	}

	@Override
	public int insertSelective(User record) {
		// TODO Auto-generated method stub
		return smbmsUserMapper.insertSelective(record);
	}

}

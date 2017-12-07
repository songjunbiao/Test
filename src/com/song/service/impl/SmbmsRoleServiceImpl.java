package com.song.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.song.entity.Role;
import com.song.mybatis.mapper.SmbmsRoleMapper;
import com.song.service.SmbmsRoleService;

@Service
public class SmbmsRoleServiceImpl implements SmbmsRoleService {
	@Resource
	private SmbmsRoleMapper smbmsRoleMapper;

	@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		return smbmsRoleMapper.getRoleList();
	}

	@Override
	public List<Role> getRoles(Role role) {
		// TODO Auto-generated method stub
		return smbmsRoleMapper.getRoles(role);
	}

	@Override
	public int insert(Role role) {
		// TODO Auto-generated method stub
		return smbmsRoleMapper.insert(role);
	}

	@Override
	public int updateByRole(Role record) {
		// TODO Auto-generated method stub
		return smbmsRoleMapper.updateByRole(record);
	}

	@Override
	public Role selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return smbmsRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteByRole(Integer id) {
		// TODO Auto-generated method stub
		return smbmsRoleMapper.deleteByRole(id);
	}

	
}

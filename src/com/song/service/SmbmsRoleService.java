package com.song.service;

import java.util.List;

import com.song.entity.Role;

public interface SmbmsRoleService {
	
    List<Role> getRoleList();
    List<Role> getRoles(Role role);
    int insert(Role role);
    int updateByRole(Role record);
    int deleteByRole(Integer id);
    Role selectByPrimaryKey(Integer id);

}

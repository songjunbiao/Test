package com.song.mybatis.mapper;

import com.song.entity.Role;
import java.util.List;

public interface SmbmsRoleMapper {

    int deleteByRole(Integer id);
    int insert(Role record);
    int insertSelective(Role record);
    Role selectByPrimaryKey(Integer id);
    int updateByRole(Role record);
    List<Role> getRoleList();
    List<Role> getRoles(Role role);
}
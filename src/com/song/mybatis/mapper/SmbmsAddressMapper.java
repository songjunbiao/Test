package com.song.mybatis.mapper;

import com.song.entity.SmbmsAddress;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmbmsAddressMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SmbmsAddress record);

    int insertSelective(SmbmsAddress record);


    SmbmsAddress selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(SmbmsAddress record);

    int updateByPrimaryKey(SmbmsAddress record);
}
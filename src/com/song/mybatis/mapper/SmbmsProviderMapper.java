package com.song.mybatis.mapper;

import java.util.List;


import com.song.entity.Provider;
import com.song.entity.ProviderPack;

public interface SmbmsProviderMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Provider record);

    int insertSelective(Provider record);


    Provider selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Provider record);

    int updateByPrimaryKey(Provider record);
    
    List<Provider> getProviders(Provider provider);
   /* List<Provider> getProviderPage(@Param("provider") Provider provider,@Param("currentPageNo")Integer currentPageNo,@Param("pageSize") Integer pageSize);*/
    List<Provider> getProviderPage(ProviderPack providerPack);

}
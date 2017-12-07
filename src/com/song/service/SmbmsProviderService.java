package com.song.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.song.entity.Provider;
import com.song.entity.ProviderPack;

public interface SmbmsProviderService {

	int insertSelective(Provider record);

	List<Provider> getProviders(Provider provider);

	Provider selectByPrimaryKey(Integer id);
	
    int updateByPrimaryKeySelective(Provider record);
    
    int deleteByPrimaryKey(Integer id);
    
   /* List<Provider> getProviderPage(Provider provider,Integer currentPageNo,Integer pageSize);*/
     List<Provider> getProviderPage(ProviderPack providerPack);

}

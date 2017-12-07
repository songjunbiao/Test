package com.song.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.song.entity.Provider;
import com.song.entity.ProviderPack;
import com.song.mybatis.mapper.SmbmsProviderMapper;
import com.song.service.SmbmsProviderService;

@Service
public class SmbmsProviderServiceImpl implements SmbmsProviderService {

	@Resource
	private SmbmsProviderMapper smbmsProviderMapper;
	@Override
	public List<Provider> getProviders(Provider provider) {
		// TODO Auto-generated method stub
		return smbmsProviderMapper.getProviders(provider);
	}
	@Override
	public int insertSelective(Provider record) {
		// TODO Auto-generated method stub
		return smbmsProviderMapper.insertSelective(record);
	}
	@Override
	public Provider selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return smbmsProviderMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateByPrimaryKeySelective(Provider record) {
		// TODO Auto-generated method stub
		return smbmsProviderMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return smbmsProviderMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<Provider> getProviderPage(ProviderPack providerPack) {
		// TODO Auto-generated method stub
		/*Integer currentPageNo=providerPack.getCurrentPageNo();
		Integer pageSize=providerPack.getPageSize();
		currentPageNo=(currentPageNo-1)*pageSize;*/
		return smbmsProviderMapper.getProviderPage(providerPack);
	}

}

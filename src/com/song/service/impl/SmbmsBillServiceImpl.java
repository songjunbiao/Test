package com.song.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.song.entity.Bill;
import com.song.mybatis.mapper.SmbmsBillMapper;
import com.song.service.SmbmsBillService;

@Service
public class SmbmsBillServiceImpl implements SmbmsBillService {

	@Resource
	private SmbmsBillMapper smbmsBillMapper;
	
	@Override
	public List<Bill> getBills(Bill bill) {
		// TODO Auto-generated method stub
		return smbmsBillMapper.getBills(bill);
	}


	@Override
	public int getBillCount(String productName, Integer providerId,
			Integer isPayment) {
		// TODO Auto-generated method stub
		return smbmsBillMapper.getBillCount(productName,providerId,isPayment);
	}

	@Override
	public List<Bill> getBillsPage(String productName, Integer providerId,
			Integer isPayment, Integer currentPageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		currentPageNo=(currentPageNo-1)*pageSize;
		return smbmsBillMapper.getBillsPage(productName, providerId,isPayment,currentPageNo, pageSize);
	}


	@Override
	public int insert(Bill bill) {
		// TODO Auto-generated method stub
		return smbmsBillMapper.insertSelective(bill);
	}


	@Override
	public Bill selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return smbmsBillMapper.selectByPrimaryKey(id);
	}


	@Override
	public int updateByBill(Bill record) {
		// TODO Auto-generated method stub
		return smbmsBillMapper.updateByBill(record);
	}


	@Override
	public int deleteByBill(Integer id) {
		// TODO Auto-generated method stub
		return smbmsBillMapper.deleteByBill(id);
	}

}

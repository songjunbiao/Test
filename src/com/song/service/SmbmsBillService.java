package com.song.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.song.entity.Bill;

public interface SmbmsBillService {
	List<Bill> getBills(Bill bill);
	List<Bill> getBillsPage(String productName,Integer providerId,Integer isPayment,Integer currentPageNo, Integer pageSize);
	int getBillCount(String productName,Integer providerId,Integer isPayment);
	int insert(Bill bill);
	Bill selectByPrimaryKey(Integer id);
	int updateByBill(Bill record);
	int deleteByBill(Integer id);
}

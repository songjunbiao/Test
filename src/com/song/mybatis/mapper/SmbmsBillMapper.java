package com.song.mybatis.mapper;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.apache.ibatis.annotations.Param;

import com.song.entity.Bill;

public interface SmbmsBillMapper {

	int deleteByBill(Integer id);

	int insert(Bill record);

	int insertSelective(Bill record);

	Bill selectByPrimaryKey(Integer id);

	int updateByBill(Bill record);
	
	List<Bill> getBills(Bill bill);
	
	int getBillCount(@Param("productName")String productName,
					@Param("providerId")Integer providerId,
					@Param("isPayment") Integer isPayment);
	List<Bill> getBillsPage(@Param("productName")String productName,
							@Param("providerId")Integer providerId,
							@Param("isPayment") Integer isPayment,
							@Param("currentPageNo")Integer currentPageNo,@Param("pageSize") Integer pageSize);
}
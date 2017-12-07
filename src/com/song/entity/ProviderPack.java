package com.song.entity;

import com.song.entity.Provider;

public class ProviderPack extends Provider {
	private Integer currentPageNo;
	private Integer pageSize;
	public Integer getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = (currentPageNo-1)*pageSize;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}

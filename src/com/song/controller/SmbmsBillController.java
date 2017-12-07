package com.song.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.song.entity.Bill;
import com.song.entity.Provider;
import com.song.entity.User;
import com.song.service.SmbmsBillService;
import com.song.service.SmbmsProviderService;


@Controller
@RequestMapping("bill")
public class SmbmsBillController {
	@Resource
	private SmbmsBillService smbmsBillService;
	@Resource
	private SmbmsProviderService smbmsProviderService;
	
	@RequestMapping("list.do")
	public String getBills(@RequestParam(value="productName", required = false) String productName,
			@RequestParam(value = "providerId", required = false) Integer providerId,
			@RequestParam(value="isPayment",required=false) Integer isPayment,
			@RequestParam(value = "currentPageNo", defaultValue = "1") Integer currentPageNo,
			Model model){
		List<Provider> providerList = smbmsProviderService.getProviders(new Provider());
		int pageSize=5;
		int totalCount = smbmsBillService.getBillCount(productName, providerId,isPayment); //总记录数
		int totalPageCount = (int) Math.ceil(totalCount * 1.0 / pageSize); //总页数
		List<Bill> billList = smbmsBillService.getBillsPage(productName, providerId,isPayment,currentPageNo, pageSize);
		
		model.addAttribute("providerList", providerList);
		model.addAttribute("billList", billList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPageNo", currentPageNo);
		model.addAttribute("productName", productName);
		model.addAttribute("providerId", providerId);
		model.addAttribute("isPayment", isPayment);
	
		
		return "billlist";
	}
	/**
	 * 通过ajax获取所有供应商
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="add.do",method=RequestMethod.GET)
	public List<Provider> add(Model model){	
		
		return  smbmsProviderService.getProviders(new Provider());
	}
	
	/*@ResponseBody
	@RequestMapping(value="add.do",method=RequestMethod.GET)
	public String add(Model model){	
		
		return JSONArray.toJSONString(smbmsProviderService.getProviders(new Provider()));
	}
	*/
	
	
	@RequestMapping(value="save.do",method=RequestMethod.POST)
	public String save(@ModelAttribute Bill bill,HttpSession session){
		User user= (User) session.getAttribute("userSession");
		bill.setCreationDate(new Date());
		bill.setCreatedBy(user.getId());
		int i = smbmsBillService.insert(bill);
		if(i>0){
			return "redirect:list.do";
		}
		return "billadd";
	}
	
	@RequestMapping("get/{id}")
	public String getBill(@PathVariable Integer id, Model model){
		Bill bill = smbmsBillService.selectByPrimaryKey(id);
		model.addAttribute(bill);
		return "billview";
	}
	
	@RequestMapping("update/{id}")
	public String update(@PathVariable Integer id, Model model){
		Bill bill = smbmsBillService.selectByPrimaryKey(id);
		model.addAttribute(bill);
		
		return "billmodify";
		
	}
	
	@RequestMapping("modify.do")
	public String modify(@ModelAttribute Bill bill,Model model){
		int i= smbmsBillService.updateByBill(bill);
		if(i>0){
			return "redirect:list.do";
		}
		model.addAttribute("error", "更新失败");
		return "redirect:update/"+bill.getId();
	}
	
	@RequestMapping("deleteById")
	public void deleteBill(Integer id, HttpServletResponse response) {
		
			String json = null;
			int deleteByPrimaryKey = smbmsBillService.deleteByBill(id);
			if (deleteByPrimaryKey >0) {
				json = "{\"success\":true}";
			} else {
				json = "{\"success\":false}";
			}
	
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
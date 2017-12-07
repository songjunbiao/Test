package com.song.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.config.ProviderCreatingFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.song.entity.Bill;
import com.song.entity.Provider;
import com.song.entity.ProviderPack;
import com.song.entity.User;
import com.song.service.SmbmsBillService;
import com.song.service.SmbmsProviderService;

@Controller
@RequestMapping("/provider")
public class SmbmsProviderController {

	@Resource
	private SmbmsProviderService smbmsProviderService;

	@Resource
	private SmbmsBillService smbmsBillService;

	@RequestMapping("/list.do")
	public String getProviderList(@ModelAttribute Provider provider, Model model,@RequestParam(value = "currentPageNo", defaultValue = "1") Integer currentPageNo) {
		int pageSize=5;
		ProviderPack providerPack=new ProviderPack();
		providerPack.setPageSize(5);
		providerPack.setCurrentPageNo(currentPageNo);
		providerPack.setProCode(provider.getProCode());
		providerPack.setProName(provider.getProName());
		List<Provider> providerList=smbmsProviderService.getProviderPage(providerPack);
		List<Provider> providerCount = smbmsProviderService.getProviders(providerPack);
		int totalCount=0;
		int totalPageCount=0;
		if(providerCount.size()>0){
			 totalCount=providerCount.size();
			 totalPageCount = (int) Math.ceil(totalCount * 1.0 / pageSize);
		}
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPageNo", currentPageNo);
		model.addAttribute("providerList", providerList);

		return "providerlist";
	}

	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String add(@ModelAttribute("provider") Provider provider) {

		return "provideradd";
	}

	@RequestMapping(value = "add.do", method = RequestMethod.POST)
	public String addSave(@ModelAttribute("provider") @Valid Provider provider,
			BindingResult bindingResult,@RequestParam("attachs") MultipartFile[] attachs,HttpSession session,HttpServletRequest request,Model model) {
		if (bindingResult.hasErrors()) {
			return "provideradd";
		}

		
		String path = session.getServletContext().getRealPath("photos");
		System.out.println("fileUpload:=============================================");
		List<String> errors = new ArrayList<String>();
		List<String> filenames = new ArrayList<String>();
		//上传图片
		for(MultipartFile attach:attachs){
			String error = checkFile(attach);
			errors.add(error);
			if(error == null)
				filenames.add(upload(attach, path));
		}
		

		
		if(filenames.size() > 0){
			//保存
			/*user.setPhotoPath("photos"+File.separator+filenames.get(0));*/
			provider.setCompanyLicPicPath("photos"+File.separator+filenames.get(0));
			provider.setOrgCodePicPath("photos"+File.separator+filenames.get(1));
			if(smbmsProviderService.insertSelective(provider)>0){
				return "redirect:/provider/list.do";
			}
		}
		model.addAttribute("errors", errors);
		return "provideradd";
	}
	
	/**
	 * 
	 * @param photo
	 * @param path
	 * @return  文件名，null代表上传失败
	 */
	public String upload(MultipartFile photo,String path){
		String filename = System.currentTimeMillis()+photo.getOriginalFilename();
		File dest = new File(path,filename);
		//如果文件夹不存在就新创建一个
		if(!dest.exists()){
			
			dest.mkdirs();
		}
		
		try {
			photo.transferTo(dest);
			return filename;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private static List<String> formats = new ArrayList<String>();
	static{
		formats.add("jpg");
		formats.add("png");
		formats.add("jpeg");
		formats.add("pneg");
	}
	/**
	 * 	//1.是否为Null
		//2.大小的限制
		//3.格式的限制
	 * @param photo
	 * @return null is ok.
	 */
	public String checkFile(MultipartFile photo){
		String fileName = photo.getOriginalFilename();  //获取原文件名
		String sufName = FilenameUtils.getExtension(fileName); //获取原文件后缀名
		if(photo.isEmpty()){
			return String.format("%s:文件不能为null", fileName);
		}else{
			if(photo.getSize()>500000){
				return String.format("%s:文件大小不能超过500kB", fileName);
			}else if(!formats.contains(sufName.toLowerCase())){
				return String.format("%s:上传的图片格式不正确", fileName);
			}
		}
		return null;
	}

	@RequestMapping("/getById/{id}")
	public String getProviderById(@PathVariable Integer id, Model model) {
		Provider provider = smbmsProviderService.selectByPrimaryKey(id);
		model.addAttribute("provider", provider);
		return "providerview";
	}

	@RequestMapping("/updateById/{id}")
	public String getProviderByid(@PathVariable Integer id, Model model) {
		Provider provider = smbmsProviderService.selectByPrimaryKey(id);
		model.addAttribute("provider", provider);
		return "providermodify";
	}

	@RequestMapping("/updateSaveById.do")
	public String modifProviderSave(@ModelAttribute Provider provider,
			Model model) {
		int updateByPrimaryKeySelective = smbmsProviderService
				.updateByPrimaryKeySelective(provider);
		if (updateByPrimaryKeySelective >= 1) {
			return "redirect:/provider/list.do";
		}
		return "providermodify";
	}

	@RequestMapping("deleteById")
	public void deleteProvider(Integer id, HttpServletResponse response) {
		Bill bill = new Bill();
		bill.setProviderId(id);
		List<Bill> bills = smbmsBillService.getBills(bill);
		String json = null;
		if (bills.size() > 0) {
			json = "{\"success\":" + String.valueOf(bills.size()) + "}";
			System.out.println(json);
		
		} else {

			int deleteByPrimaryKey = smbmsProviderService
					.deleteByPrimaryKey(id);
			if (deleteByPrimaryKey >= 1) {
				json = "{\"success\":true}";
			} else {
				json = "{\"success\":false}";
			}
		}
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

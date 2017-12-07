package com.song.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.song.entity.Role;
import com.song.service.SmbmsRoleService;

@Controller
@RequestMapping("role")
public class SmbmsRoleController {
	@Resource
	private SmbmsRoleService smbmsRoleService;

	@RequestMapping("list.do")
	public String getRoles(Model model){
		List<Role> roles = smbmsRoleService.getRoleList();
		model.addAttribute("roles",roles);
		return "rolelist";
	}
	
	@ResponseBody
	@RequestMapping("rcexist.do")
	public Role getByCode(String roleCode,HttpServletResponse response){
		Role role=new Role();
		role.setRoleCode(roleCode);
		List<Role> roles = smbmsRoleService.getRoles(role);
		return roles.size()>0?roles.get(0):null;
	}
	
	@RequestMapping("save.do")
	public String save(@ModelAttribute Role role){
		role.setCreationDate(new Date());
		int i = smbmsRoleService.insert(role);
		if(i>0){
			return "redirect:/role/list.do";
		}
		return "roleadd";
		
	}
	
	@RequestMapping("update/{id}")
	public String update(@PathVariable Integer id,Model model){
		Role role = smbmsRoleService.selectByPrimaryKey(id);
		model.addAttribute("role", role); 
		return "rolemodify";
	}
	
	@RequestMapping("modify.do")
	public String modify(@ModelAttribute Role record){
		int i = smbmsRoleService.updateByRole(record);
		if(i>0){
			
			return "redirect:/role/list.do";
		}
		return "redirect:update/"+record.getId();
	}
	
	@RequestMapping("deleteById")
	public void deleteBill(Integer id, HttpServletResponse response) {
		
			String json = null;
			int deleteByPrimaryKey = smbmsRoleService.deleteByRole(id);
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

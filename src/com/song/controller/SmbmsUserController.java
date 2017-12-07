package com.song.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.song.entity.Role;
import com.song.entity.User;
import com.song.service.SmbmsRoleService;
import com.song.service.SmbmsUserService;

@Controller
@RequestMapping("/user")
public class SmbmsUserController {

	@Resource
	private SmbmsUserService smbmsUserService;
	@Resource
	private SmbmsRoleService smbmsRoleService;

	@RequestMapping("/login.do")
	public String login(String userCode, String userPassword,
			HttpServletRequest request) {
		User loginUser = smbmsUserService.getLoginUser(userCode, userPassword);
		if (loginUser != null) {
			request.getSession().setAttribute("userSession", loginUser);

			return "frame";
		}
		request.getSession().setAttribute("error", "用户名或密码错误");

		return "to";
	}

	@RequestMapping("loginOut.do")
	public String loginOut(HttpServletRequest request) {

		request.getSession().removeAttribute("userSession");
		request.getSession().removeAttribute("error");

		return "to";
	}

	@RequestMapping("/list.do")
	public String userList(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "userRole", required = false) Integer userRole,
			@RequestParam(value = "currentPageNo", defaultValue = "1") Integer currentPageNo,
			Model model) {
		int pageSize = 5;

		int totalCount = smbmsUserService.getUserCount(userName, userRole);
		System.out.println(totalCount);
		List<User> userList = smbmsUserService.getUserList(userName, userRole,
				currentPageNo, pageSize);
		System.out.println(userList);

		int totalPageCount = (int) Math.ceil(totalCount * 1.0 / pageSize);
		model.addAttribute("userList", userList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("currentPageNo", currentPageNo);
		model.addAttribute("roleList", smbmsRoleService.getRoleList());
		return "userlist";
	}

	@RequestMapping(value="/addUser.do",method=RequestMethod.GET)
	public String addUser(@ModelAttribute User user){
		
		return "useradd";
	}
	
	@RequestMapping(value="/save.do",method=RequestMethod.POST)
	public String saveUser(@ModelAttribute User user){
		user.setCreationDate(new Date());
		int insertSelective = smbmsUserService.insertSelective(user);
		if(insertSelective==1){
			return "redirect:list.do";
		}
		return "forward：addUser.do";
	}
	
	@RequestMapping("ucexist.do")
	@ResponseBody
	public User userCodeIsExit(@RequestParam("userCode") String userCode) {
		
		User code = smbmsUserService.getUserByUserCode(userCode);
		return code;
	}
	/**
	 * 通过ajax获取所有角色
	 * @return
	 */
	@RequestMapping("role.do")
	@ResponseBody
	public List<Role> getRoleAll(){
		return  smbmsRoleService.getRoleList();
	}
	
	@RequestMapping("userById/{id}")
	public String userById(@PathVariable Integer id,Model model){
		User user = smbmsUserService.selectByPrimaryKey(id);
		model.addAttribute("user", user);
		return "userview";
	}

	@ResponseBody
	@RequestMapping("getById.do")
	public Object getById(Integer id){
		System.out.println("=============================");
		User user = smbmsUserService.selectByPrimaryKey(id);
		System.out.println(user);
		return user;
	}
	
	/*@ResponseBody///,produces={"application/json;charset=UTF-8"}
	@RequestMapping(value="getById.do")
	public String getById(Integer id){
		System.out.println("=============================");
		User user = smbmsUserService.selectByPrimaryKey(id);
		System.out.println(user);
		return JSONObject.toJSONString(user,SerializerFeature.WriteDateUseDateFormat);
	}*/
	
	@RequestMapping("selectUpdateById/{id}")
	public String selectUpdateById(@PathVariable Integer id,Model model){
		User user = smbmsUserService.selectByPrimaryKey(id);
		model.addAttribute("user", user);
		return "usermodify";
	}
	
	@RequestMapping("/updateUser")
	public String updateUser(@ModelAttribute User user){
		int updateByPrimaryKey = smbmsUserService.updateByPrimaryKey(user);
		if(updateByPrimaryKey==1){
			return "redirect:list.do";
		}
		return "forward：addUser/"+user.getId();
	}
	
	@RequestMapping("/deleteUserById")
	public void deleteUserById( Integer id,Model model,HttpServletResponse response){
		System.out.println(id+"测试");
		int deleteUserById = smbmsUserService.deleteUserById(id);
		/*if(deleteUserById>=1){
			model.addAttribute("tishi", "删除成功");
		}else{
			model.addAttribute("tishi", "删除失败");
		}
		return "redirect:list.do";*/
		String json=null;
		if(deleteUserById>=1){
			 json="{\"success\":true}";
		}else{
			json="{\"success\":false}";
		}
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/updatePwd")
	public String updatePwd(String userpassword,String newUserpassword,HttpSession session,Model model){
		User user = (User) session.getAttribute("userSession");
		if(!userpassword.equals(user.getUserPassword())){
			model.addAttribute("error", "原密码错误请重新输入");
			return "pwdmodify";
		}
		
		int updatePwd = smbmsUserService.updatePwd(user.getId(), userpassword, newUserpassword);
		if(updatePwd>=1){
			model.addAttribute("ok", "修改密码成功,重新登录生效");
			
		}else{
			model.addAttribute("error", "修改密码失败");
		}
		return "pwdmodify";
	}
	/*
	 * 转换日期格式
	 */
	@InitBinder
	public void initBinder(HttpServletRequest request,WebDataBinder binder){
		String str=request.getParameter("birthday");
		if(str!=null){
			if(str.contains("/")){
				binder.registerCustomEditor(Date.class, new CustomDateEditor(
						new SimpleDateFormat("yyyy/MM/dd"), true));
			}else{
				binder.registerCustomEditor(Date.class, new CustomDateEditor(
						new SimpleDateFormat("yyyy-MM-dd"), true));
			}
		}
		
	}

}

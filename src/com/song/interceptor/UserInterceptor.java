package com.song.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.song.entity.User;

public class UserInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		String name = uri.substring(uri.lastIndexOf("/")+1);//login.do
		String path = request.getContextPath();
		
		User user = (User) request.getSession().getAttribute("userSession");
		if(user == null && !checkIntercept(name)){
			response.sendRedirect(path+"/login.jsp");
			return false;
		}
		return true;
	}
	private static List<String> actions = new ArrayList<String>();
	static{
		actions.add("login");
		actions.add("register");
	}
	public boolean checkIntercept(String actionName){
		for(String action:actions){
			if(actionName.toLowerCase().contains(action)){
				return true;
			}
		}
		return false;
	}

}

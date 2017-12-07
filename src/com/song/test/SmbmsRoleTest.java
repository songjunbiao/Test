package com.song.test;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.song.service.SmbmsUserService;

public class SmbmsRoleTest {
	
	@Test
	public void insert() {
	

		try {
			ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
			  SmbmsUserService d= (SmbmsUserService) applicationContext.getBean("smbmsRoleServiceImpl");
			  d.getUserList(null,null, 1, 5);
			  int userCount = d.getUserCount("系统管理员", 1);
			  System.out.println(userCount);
			  System.out.println(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}

	}

	
	
}

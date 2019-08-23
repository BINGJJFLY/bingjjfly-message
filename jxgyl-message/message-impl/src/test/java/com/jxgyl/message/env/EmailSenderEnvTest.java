package com.jxgyl.message.env;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxgyl.message.email.EmailSenderEnv;

/**
 * 配置信息映射测试
 * 
 * @author iss002
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class EmailSenderEnvTest {

	@Autowired
	private EmailSenderEnv env;
	
	@Test
	public void env() {
		System.out.println(env.dir());
		System.out.println(env.from());
	}
}

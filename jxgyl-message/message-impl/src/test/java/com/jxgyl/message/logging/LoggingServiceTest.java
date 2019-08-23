package com.jxgyl.message.logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jxgyl.message.Message;

/**
 * 日志记录测试
 * 
 * @author iss002
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class LoggingServiceTest {
	
	@Autowired
	private Slf4jLoggingImpl loggingService;
	
	String from = "BINGJJFLY@163.com";
	String[] to = { "wangjz@shougangfund.cn" };
	String subject = "测试Email生产者";
	String text = "RedisMQ方式测试Email生产者";
	
	@Test
	public void log() {
		loggingService.log(Message.createEmail(from, to, subject, text, null, null), true);
	}

}

package com.jxgyl.message.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * email的配置信息
 * 
 * @author iss002
 *
 */
@Component
@Validated
@PropertySource("classpath:conf/email.properties")
public class EmailSenderEnv {

	private static final String EMAIL_FROM = "email.from";
	private static final String EMAIL_DIR = "email.dir";

	@Autowired
	private Environment env;

	public String from() {
		return env.getProperty(EMAIL_FROM);
	}

	public String dir() {
		return env.getProperty(EMAIL_DIR);
	}

}

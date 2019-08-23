package com.jxgyl.message.email;

import java.io.File;

import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.jxgyl.message.Attachment;
import com.jxgyl.message.Message;
import com.jxgyl.message.MessageSender;
import com.jxgyl.message.logging.Slf4jLoggingImpl;
import com.jxgyl.message.thymeleaf.ThymeleafHelper;

/**
 * Email组件
 * 
 * @author iss002
 *
 */
@Service("emailSender")
public class EmailSender implements MessageSender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private EmailSenderEnv env;
	@Autowired
	private ThymeleafHelper thymeleafHelper;
	@Autowired
	private Slf4jLoggingImpl loggingService;
	@Autowired
	private ThreadPoolTaskExecutor executor;

	@Override
	public void send(Message... msgs) {
		if (msgs != null && msgs.length > 0) {
			for (int i = 0; i < msgs.length; i++) {
				executor.execute(new SendEmailTask(msgs[i]));
			}
		}
	}

	/**
	 * 发送Email任务
	 * 
	 * @author iss002
	 *
	 */
	class SendEmailTask implements Runnable {
		Message msg;

		SendEmailTask(Message msg) {
			this.msg = msg;
		}

		@Override
		public void run() {
			if (msg != null) {
				try {
					mailSender.send(mimeMessage(msg));
					loggingService.log(msg, true);
				} catch (Exception e) {
					LOGGER.error("【消费者消费Email信息时异常】\r\n", e);
					loggingService.log(msg, false);
				}
			}
		}

		private MimeMessageHelper mimeMessageHelper(MimeMessage mimeMessage, Message msg) throws Exception {
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom(msg.getFrom() == null ? env.from() : msg.getFrom());
			helper.setTo(msg.getTo());
			helper.setSubject(msg.getSubject());
			Attachment[] attachments = msg.getAttachments();
			if (attachments != null) {
				for (int j = 0; j < attachments.length; j++) {
					Attachment attachment = attachments[j];
					// TODO 附件实现类
					helper.addAttachment(attachment.getName(), new File(env.dir() + attachment.getPath()));
				}
			}
			return helper;
		}

		private MimeMessage mimeMessage(Message msg) throws Exception {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			mimeMessageHelper(mimeMessage, msg);
			String text = thymeleafHelper.process(msg.getBusiness().getTemplate(), msg.getVars());
			mimeMessage.setContent(multipart(text == null ? msg.getText() : text));
			return mimeMessage;
		}

		private MimeMultipart multipart(String content) throws Exception {
			final MimeMultipart multipart = new MimeMultipart();
			final BodyPart body = new MimeBodyPart();
			body.setContent(content, "text/html;charset=utf-8");
			multipart.addBodyPart(body);
			return multipart;
		}
	}
}

package com.jxgyl.message.email;

import java.util.Arrays;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jxgyl.message.Attachment;
import com.jxgyl.message.Message;
import com.jxgyl.message.MessageSender;
import com.jxgyl.message.logging.Slf4jLoggingImpl;
import com.jxgyl.message.resolver.DataSourceResolver;
import com.jxgyl.message.resolver.FileDataSourceResolver;
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

	private DataSourceResolver resolver = new FileDataSourceResolver();

	@Override
	public void send(Message... msgs) {
		if (msgs != null) {
			Arrays.stream(msgs).forEach(msg -> executor.execute(new SendEmailTask(msg)));
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
				LOGGER.trace("【消费者接收到Email信息准备消费】\r\n", msg);
				try {
					mailSender.send(mimeMessage());
					loggingService.log(msg, true);
				} catch (Exception e) {
					LOGGER.error("【消费者消费Email信息时异常】\r\n", e);
					loggingService.log(msg, false);
				}
			}
		}

		private void mimeMessageHelper(MimeMessage mimeMessage) throws Exception {
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(msg.getFrom() == null ? env.from() : msg.getFrom());
			helper.setTo(msg.getTo());
			helper.setSubject(msg.getSubject() == null ? msg.getBusiness().getSubject() : msg.getSubject());
			// 添加内容
			text(helper);
			// 添加嵌入式附件
			embeddedAttach(helper);
			// 添加附件
			attach(helper);
		}

		private void attach(MimeMessageHelper helper) throws Exception {
			Attachment[] attachments = msg.getAttachments();
			if (attachments != null) {
				for (int j = 0; j < attachments.length; j++) {
					Attachment attachment = attachments[j];
					helper.addAttachment(MimeUtility.encodeText(attachment.getName()),
							resolver.resolve(env.dir(), attachment.getPath()));
				}
			}
		}

		private void embeddedAttach(MimeMessageHelper helper) throws Exception {
			Map<String, String> embedded = msg.getBusiness().embedded();
			if (!CollectionUtils.isEmpty(embedded)) {
				for (Map.Entry<String, String> entry : embedded.entrySet()) {
					helper.addInline(entry.getKey(), new ClassPathResource(entry.getValue()));
				}
			}
		}

		private void text(MimeMessageHelper helper) throws Exception {
			String content = thymeleafHelper.process(msg.getBusiness().getTemplate(), msg.getVars());
			helper.setText(content == null ? msg.getText() : content, true);
		}

		private MimeMessage mimeMessage() throws Exception {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			mimeMessageHelper(mimeMessage);
			return mimeMessage;
		}
	}
}

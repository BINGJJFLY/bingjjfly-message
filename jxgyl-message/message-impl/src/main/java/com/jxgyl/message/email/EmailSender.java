package com.jxgyl.message.email;

import java.util.Arrays;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

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
				try {
					mailSender.send(mimeMessage());
					loggingService.log(msg, true);
				} catch (Exception e) {
					LOGGER.error("【消费者消费Email信息时异常】\r\n", e);
					loggingService.log(msg, false);
				}
			}
		}

		private MimeMessageHelper mimeMessageHelper(MimeMessage mimeMessage) throws Exception {
			final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(msg.getFrom() == null ? env.from() : msg.getFrom());
			helper.setTo(msg.getTo());
			helper.setSubject(msg.getSubject());
			return helper;
		}

		private MimeMessage mimeMessage() throws Exception {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			mimeMessageHelper(mimeMessage);
			mimeMessage.setContent(multipart());
			return mimeMessage;
		}

		private MimeMultipart multipart() throws Exception {
			final MimeMultipart multipart = new MimeMultipart();
			final BodyPart body = new MimeBodyPart();
			String content = thymeleafHelper.process(msg.getBusiness().getTemplate(), msg.getVars());
			body.setContent(content == null ? msg.getText() : content, "text/html;charset=utf-8");
			multipart.addBodyPart(body);
			Attachment[] attachments = msg.getAttachments();
			if (attachments != null) {
				for (int j = 0; j < attachments.length; j++) {
					Attachment attachment = attachments[j];
					final BodyPart attchBody = new MimeBodyPart();
					attchBody.setFileName(MimeUtility.encodeText(attachment.getName()));
					attchBody.setDataHandler(new DataHandler(resolver.resolve(env.dir(), attachment.getPath())));
					multipart.addBodyPart(attchBody);
				}
			}
			return multipart;
		}
	}
}

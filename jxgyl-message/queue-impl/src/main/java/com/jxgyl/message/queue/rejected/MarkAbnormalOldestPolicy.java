package com.jxgyl.message.queue.rejected;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jxgyl.message.Message;
import com.jxgyl.message.queue.producer.MessageRedisProducer.PushEmailTask;
import com.jxgyl.message.service.MessageService;

public class MarkAbnormalOldestPolicy implements RejectedExecutionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MarkAbnormalOldestPolicy.class);
	
	@Autowired
	private MessageService messageService;
	
	private Executor executor = Executors.newSingleThreadExecutor();
	
	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		if (!e.isShutdown()) {
			Runnable t = e.getQueue().poll();
			if (t instanceof PushEmailTask) {
				Message[] msgs = ((PushEmailTask) t).getEmails();
				LOGGER.info("【线程池拒绝任务标记异常】\r\n" + Arrays.toString(msgs));
				executor.execute(new Runnable() {
					@Override
					public void run() {
						messageService.markAbnormal(msgs);
					}
				});
			}
		}
	}

}
